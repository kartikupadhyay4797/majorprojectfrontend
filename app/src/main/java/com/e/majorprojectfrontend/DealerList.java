package com.e.majorprojectfrontend;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
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

public class DealerList extends AppCompatActivity {

    public static ArrayList<Dealer> dealerlist;
    JSONObject dealer;
    Dealer dealerobject;
    OkHttpClient client;
    ProgressBar progressBar;
    RecyclerView recyclerView;
    DealerAdapter dealerAdapter;
    TextView logout,orderhistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dealer_list);
        client=new OkHttpClient();
        progressBar=findViewById(R.id.progress_in_dealerlist);
        recyclerView=findViewById(R.id.recyclerViewDealerList);
        logout=(TextView)findViewById(R.id.logout);
        orderhistory=(TextView)findViewById(R.id.orderhistory);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Session(getApplicationContext()).logoutUser();
            }
        });
        orderhistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DealerList.this,OrderList.class));
            }
        });

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


        dealerlist=new ArrayList<Dealer>();

        Request request = new Request.Builder()
                .url("http://kartik4797.pythonanywhere.com/dealer/")
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
                        Toast.makeText(DealerList.this, "sorry we can't process right now, please try again later...", Toast.LENGTH_LONG).show();
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
//                        dealerobject=new Dealer(dealer.get("name").toString(),dealer.get("address").toString(),dealer.get("imageURL").toString(),dealer.get("id").toString(),dealer.get("email").toString());
                        dealerobject=new Dealer();
                        dealerobject.setName(dealer.get("name").toString());
                        dealerobject.setAddress(dealer.get("address").toString());
                        dealerobject.setImageURL(dealer.get("imageURL").toString());
                        dealerobject.setId(dealer.get("id").toString());
                        dealerobject.setEmail(dealer.get("email").toString());
                        dealerlist.add(dealerobject);
                        Log.e("response: ",dealerlist.get(i).toString());
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            dealerAdapter = new DealerAdapter(dealerlist);
                            recyclerView.setAdapter(dealerAdapter);
                            recyclerView.setLayoutManager(new LinearLayoutManager(DealerList.this));
                            recyclerView.setHasFixedSize(true);
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
                            Toast.makeText(DealerList.this, "we can't process right now please try again later...", Toast.LENGTH_LONG).show();
                        }
                        //Register.startActivity(home);
                        //Register.finish();
                    }
                });
            }
        });
            Log.e("lastline",dealerlist.toString());
    }
}
