package com.e.majorprojectfrontend;


import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.ArrayList;

public class OrderList extends AppCompatActivity {
    ArrayList<OrderHistoryItem> dealerlist;
    JSONObject dealer;
    OrderHistoryItem dealerobject;
    OkHttpClient client;
    ProgressBar progressBar;
    RecyclerView recyclerView;
    OrderHistoryAdapter dealerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list);
        client=new OkHttpClient();
        progressBar=findViewById(R.id.progress_in_OrderList);
        recyclerView=findViewById(R.id.recyclerViewOrderList);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        //carttotalitems counting
//        dbc=new DatabaseCreation(getApplicationContext());
//        dbw=dbc.getWritableDatabase();
//        dbr=dbc.getReadableDatabase();
//        cv=new ContentValues();
//        cartCount=0;
//        try {
//            cursor = dbr.rawQuery("select * from " + FeedReader.FeedEntry.TABLE_NAME, null);
//
//            if (cursor.moveToFirst()) {
//                while (!cursor.isAfterLast()) {
//                    cartCount++;
////                    objlist.add(new MyObj(cursor.getString(cursor.getColumnIndex(FeedReader.FeedEntry.STATUS)), cursor.getString(cursor.getColumnIndex(FeedReader.FeedEntry.DETAIL)),
////                            cursor.getString(cursor.getColumnIndex(FeedReader.FeedEntry.DATE_ASSIGNED)), cursor.getString(cursor.getColumnIndex(FeedReader.FeedEntry.ASSIGNED_TO))));
//                    cursor.moveToNext();
//                }
//            }
//        }catch (SQLiteException e){}
//        cart_top = (TextView) findViewById(R.id.cart_count);
////            if (cart_top.getText().toString().equals(""))
//        cart_top.setText(String.valueOf(cartCount));


        dealerlist=new ArrayList<>();

        Request request = new Request.Builder()
                .url("http://kartik4797.pythonanywhere.com/orders/")
                .get().build();

//                        new Request.Builder()
//                        .url("http://kartik4797.pythonanywhere.com/dealer/")
//                        .post(body)
//                        .build();
//                try {
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                Log.e("on failure : ","exception " + e.getMessage());
                final String message = e.getMessage();

                runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(OrderList.this, "sorry we can't process right now, please try again later...", Toast.LENGTH_LONG).show();
                        progressBar.setVisibility(View.GONE);
                    }
                });

            }

            @Override
            public void onResponse(Response response) throws IOException {

                String responseBody=response.body().string();
                Log.e("responsebody",responseBody);
                JSONParser parser=new JSONParser();
                try {
                    //org.json.JSONArray array=(JSONArray) parser.parse(res);
//                            JSONArray array=new JSONArray(responseBody);
                    JSONArray array=(JSONArray)parser.parse(responseBody);
                    Log.e("response: ",array.toString());
                    for(int i=0;i<array.size();i++){
                        dealer=(org.json.simple.JSONObject)parser.parse(array.get(i).toString());
                        if(dealer.get("orderByUser").toString().equals(new Session(OrderList.this).getUserId()))
                            dealerlist.add(new OrderHistoryItem(dealer.get("id").toString(),dealer.get("orderItems").toString(),dealer.get("orderItemsPrice").toString(),dealer.get("orderTotalPrice").toString(),dealer.get("orderCreatedDate").toString(),DealerList.dealerlist.get(Integer.parseInt(dealer.get("orderToDealer").toString())).getName(),dealer.get("orderStatus").toString()));
                    }
//                    "id": 3,
//                            "orderCreatedDate": "2020-06-07T09:02:42.431312Z",
//                            "orderItems": "super chill",
//                            "orderItemsQuantity": "1",
//                            "orderItemsPrice": "30rs",
//                            "orderItemsTotalPrice": "30rs",
//                            "orderTotalPrice": "30rs",
//                            "orderStatus": "Not Confirmed By Dealer",
//                            "orderByUser": 33,
//                            "orderToDealer": 1,
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if(dealerlist.size()>0) {
                                dealerAdapter = new OrderHistoryAdapter(dealerlist);
                                recyclerView.setAdapter(dealerAdapter);
                                recyclerView.setLayoutManager(new LinearLayoutManager(OrderList.this));
                                recyclerView.setHasFixedSize(true);
                            }
                        }
                    });
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
//                                Intent home = new Intent(Register, Login.class);
//                                try {
//                                    home.putExtra("dealer_id", jsonObject.getJSONArray("result").getJSONObject(0).getString("id"));
//                                } catch (JSONException e) {
//                                    e.printStackTrace();
//                                }
                        progressBar.setVisibility(View.GONE);
                        if(dealerlist.size()==0) {
                            Toast.makeText(OrderList.this, "you didn't order anything yet.", Toast.LENGTH_LONG).show();
                        }
                        //Register.startActivity(home);
                        //Register.finish();
                    }
                });
            }
        });
        Log.e("lastline",dealerlist.toString());
    }
    public boolean onOptionsItemSelected(MenuItem item){
        this.finish();
        return true;
    }
}
