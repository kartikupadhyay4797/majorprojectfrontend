package com.e.majorprojectfrontend;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ItemStockAdapter extends RecyclerView.Adapter<ItemStockAdapter.ItemStockViewHolder> {

    ArrayList<ItemStock> list;
    static boolean writeToList;

    public ItemStockAdapter(ArrayList<ItemStock> list) {
        this.list=list;
    }


    public void setList(ArrayList<ItemStock> list){
        this.list=list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ItemStockAdapter.ItemStockViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context=viewGroup.getContext();
        int layoutIdForListItemStock= R.layout.itemstock_view;
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        boolean attachToParentImm=false;

        View view=layoutInflater.inflate(layoutIdForListItemStock,viewGroup,attachToParentImm);
        ItemStockAdapter.ItemStockViewHolder ItemStockViewHolder=new ItemStockAdapter.ItemStockViewHolder(view);
        return ItemStockViewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull ItemStockAdapter.ItemStockViewHolder ItemStockViewHolder, int i) {
        ItemStockViewHolder.bind(list.get(i));
    }
/*@Override
    public void onBindViewHolder(@NonNull ItemStockViewHolder ItemStockViewHolder,int i){
        ItemStockViewHolder.bind(list.get(i));
    }*/

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ItemStockViewHolder extends RecyclerView.ViewHolder{
        TextView name,capacity,price,temperature,quality;
        LinearLayout addtocart;
        Context context;
        DatabaseCreation dbc;
        SQLiteDatabase dbw,dbr;
        ContentValues cv;
        Cursor cursor;

        public ItemStockViewHolder(@NonNull View ItemStockView) {
            super(ItemStockView);

            name=(TextView)ItemStockView.findViewById(R.id.name);
            capacity=(TextView)ItemStockView.findViewById(R.id.capacity);
            price=(TextView)ItemStockView.findViewById(R.id.price);
            temperature=(TextView)ItemStockView.findViewById(R.id.temperature);
            quality=(TextView)ItemStockView.findViewById(R.id.quality);
            addtocart=(LinearLayout)ItemStockView.findViewById(R.id.add_to_cart);
            context=ItemStockView.getContext();
            dbc=new DatabaseCreation(ItemStockView.getContext());
            dbw=dbc.getWritableDatabase();
            dbr=dbc.getReadableDatabase();
            cv=new ContentValues();
        }
        public void bind(final ItemStock itemStock){
            name.setText(itemStock.getName());
            capacity.setText(itemStock.getCapacity());
            price.setText(itemStock.getPrice());
            temperature.setText(itemStock.getTemperature());
            quality.setText(itemStock.getQuality());
            addtocart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    writeToList=true;
                    try {
                        cursor = dbr.rawQuery("select * from " + FeedReader.FeedEntry.TABLE_NAME, null);

                        if (cursor.moveToFirst()) {
                            while (!cursor.isAfterLast()) {
                                if (!(cursor.getString(cursor.getColumnIndex(FeedReader.FeedEntry.ITEM_DEALER_ID)).equalsIgnoreCase(itemStock.getDealerId()))) {
//                                    dbw.delete(FeedReader.FeedEntry.TABLE_NAME, FeedReader.FeedEntry.ITEM_ID+"="+item.getMenu_Id(),null);
//                                    writeToList = false;dialoguebox to clear cart
                                    new AlertDialog.Builder(context)
                                            .setTitle("Ooops!!!")
                                            .setMessage("There are already items in your cart from another dealer. Do you want to make new order from this dealer?")

                                            // Specifying a listener allows you to take an action before dismissing the dialog.
                                            // The dialog is automatically dismissed when a dialog button is clicked.
                                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int which) {

                                                    dbw.execSQL("delete from "+ FeedReader.FeedEntry.TABLE_NAME);
                                                    writeTocart(itemStock);
                                                    writeToList=false;
                                                    dialog.dismiss();

                                                    // Continue with delete operation
                                                }
                                            })
                                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    writeToList=false;
                                                    dbw.delete(FeedReader.FeedEntry.TABLE_NAME,FeedReader.FeedEntry.ITEM_ID+"="+itemStock.getId(),null);
                                                    dialog.dismiss();
                                                    Log.e("dissmissed: ",writeToList+"");
                                                }
                                            })
                                            .setCancelable(false)

                                            // A null listener allows the button to dismiss the dialog and take no further action.
                                            .setIcon(R.drawable.canimage)
                                            .show();

                                }else if(cursor.getString(cursor.getColumnIndex(FeedReader.FeedEntry.ITEM_ID)).equalsIgnoreCase(itemStock.getId())) {
                                    Toast.makeText(context, "This item is already in cart.", Toast.LENGTH_LONG).show();
                                    writeToList=false;
                                }
                                Log.e("each iteration: ",writeToList+"");
                                cursor.moveToNext();
                            }
                        }


                    }catch (SQLiteException e){}
                        if(writeToList)
                            writeTocart(itemStock);
                }
            });

        }
        public void writeTocart(ItemStock ItemStock){
            cv.put(FeedReader.FeedEntry.ITEM_ID, ItemStock.getId());
            cv.put(FeedReader.FeedEntry.ITEM_NAME, ItemStock.getName());
            cv.put(FeedReader.FeedEntry.ITEM_PRICE, ItemStock.getPrice());
            cv.put(FeedReader.FeedEntry.ITEM_QTY, "1");
            cv.put(FeedReader.FeedEntry.ITEM_TYPE, ItemStock.getTemperature());
            cv.put(FeedReader.FeedEntry.ITEM_CAPACITY, ItemStock.getCapacity());
            cv.put(FeedReader.FeedEntry.ITEM_QLTY, ItemStock.getQuality());
            cv.put(FeedReader.FeedEntry.ITEM_DEALER_ID, ItemStock.getDealerId());

            long newRowId = dbw.insert(FeedReader.FeedEntry.TABLE_NAME, null, cv);

        }
    }

}
