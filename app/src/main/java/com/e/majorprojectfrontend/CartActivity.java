package com.e.majorprojectfrontend;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {
    public static RecyclerView recyclerViewincart;
    public static TextView totalamoutofcart;
    Button placeorder,continueshopping;
    public static ArrayList<ItemStock> cartlist;
    ProgressBar progressBarincart;
    static DatabaseCreation dbc;
    static SQLiteDatabase dbw,dbr;
    static ContentValues cv;
    static Cursor cursor;
    public static CartItemAdapter cartItemAdapter;
    static Activity cartactivity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        recyclerViewincart=(RecyclerView)findViewById(R.id.recyclerView_in_cart);
        totalamoutofcart=(TextView)findViewById(R.id.total_amt);
        placeorder=(Button)findViewById(R.id.place_order);
        progressBarincart=(ProgressBar)findViewById(R.id.progress_in_cart);
        cartlist=new ArrayList<>();
        continueshopping=(Button)findViewById(R.id.continue_shopping);
        cartactivity=CartActivity.this;

        dbc=new DatabaseCreation(getApplicationContext());
        dbw=dbc.getWritableDatabase();
        dbr=dbc.getReadableDatabase();
        cv=new ContentValues();

        try {
            cursor = dbr.rawQuery("select * from " + FeedReader.FeedEntry.TABLE_NAME, null);

            if (cursor.moveToFirst()) {
                while (!cursor.isAfterLast()) {
                    cartlist.add(new ItemStock(cursor.getString(cursor.getColumnIndex(FeedReader.FeedEntry.ITEM_ID)),cursor.getString(cursor.getColumnIndex(FeedReader.FeedEntry.ITEM_NAME)),cursor.getString(cursor.getColumnIndex(FeedReader.FeedEntry.ITEM_CAPACITY)),cursor.getString(cursor.getColumnIndex(FeedReader.FeedEntry.ITEM_PRICE)),cursor.getString(cursor.getColumnIndex(FeedReader.FeedEntry.ITEM_TYPE)),cursor.getString(cursor.getColumnIndex(FeedReader.FeedEntry.ITEM_QLTY)),cursor.getString(cursor.getColumnIndex(FeedReader.FeedEntry.ITEM_DEALER_ID))));
                    cursor.moveToNext();
                }
            }
        }catch (SQLiteException e){}
        totalamoutofcart.setText(getTotalAmtOfCart(cartlist));

        LinearLayoutManager linearLayoutManagerCartItems = new LinearLayoutManager(this);
        recyclerViewincart.setLayoutManager(linearLayoutManagerCartItems);
        recyclerViewincart.setHasFixedSize(true);

        cartItemAdapter = new CartItemAdapter(cartlist);
        recyclerViewincart.setAdapter(cartItemAdapter);

        Button continueShopping=(Button)findViewById(R.id.continue_shopping);
        continueShopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CartActivity.this.finish();
            }
        });
        placeorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cartlist.size()>0) {
                    CartActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            progressBarincart.setVisibility(View.VISIBLE);
// To dismiss the dialog
                        }
                    });
                    String orderItems = "", orderItemsPrice = "";
                    for (ItemStock itemStock : cartlist) {
                        orderItems += itemStock.getName() + ",";
                        orderItemsPrice += itemStock.getPrice() + ",";
                    }
                    OkHttpClient client = new OkHttpClient();
                    JSONObject user;
                    user = new JSONObject();
                    try {
                        user.put("orderItems", orderItems.substring(0, orderItems.length() - 1));
                        user.put("orderItemsQuantity", 0);
                        user.put("orderItemsPrice", orderItemsPrice.substring(0, orderItemsPrice.length() - 1));
                        user.put("orderItemsTotalPrice", 0);
                        user.put("orderTotalPrice", getTotalAmtOfCart(cartlist));
                        user.put("orderByUser", Integer.parseInt(new Session(CartActivity.this).getUserId()));
                        user.put("orderToDealer",Integer.parseInt(cartlist.get(0).getDealerId()));
                        user.put("orderPaymentMethod","cash");
                        user.put("orderPaymentStatus","pending");
                        user.put("orderStatus","pending");


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Log.d("sendData", user.toString());
                    RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), user.toString());
                    Request request = new Request.Builder()
                            .url("http://kartik4797.pythonanywhere.com/orders/")
                            .post(body)
                            .build();
                    Log.e("request: ",body.toString());
//                try {
//                client.newCall(request).enqueue(new Callback() {
                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Request request, IOException e) {
                            CartActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    progressBarincart.setVisibility(View.GONE);
// To dismiss the dialog
                                }
                            });
                            Toast.makeText(CartActivity.this,"Sorry we can't process right now!!!",Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onResponse(Response response) throws IOException {
                            CartActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    progressBarincart.setVisibility(View.GONE);
// To dismiss the dialog
                                    Toast.makeText(CartActivity.this,"Your order is placed.",Toast.LENGTH_LONG).show();
                                    startActivity(new Intent(CartActivity.this,OrderList.class));
                                    dbw.execSQL("delete from "+ FeedReader.FeedEntry.TABLE_NAME);
                                    CartActivity.this.finish();
                                }
                            });
                            }
                    });
                }
                else {
                    CartActivity.cartactivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(CartActivity.this, "Please add some items to cart.", Toast.LENGTH_LONG).show();
                        }
                    });
                    }
                }
        });

    }
    public static String getTotalAmtOfCart(ArrayList<ItemStock> list){
        String total="₹ ";
        int res=0;
        for(ItemStock item:list)
            res+=Integer.parseInt(item.getPrice().substring(0,2));
        return total+res;
    }
}
