package com.e.majorprojectfrontend;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CartItemAdapter extends RecyclerView.Adapter<CartItemAdapter.CartItemViewHolder> {


    ArrayList<ItemStock> list;

    public CartItemAdapter(ArrayList<ItemStock> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public CartItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context=parent.getContext();
        int layoutIdForListItem= R.layout.cartitem_view;
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        boolean attachToParentImm=false;

        View view=layoutInflater.inflate(layoutIdForListItem,parent,attachToParentImm);
        CartItemAdapter.CartItemViewHolder itemViewHolder=new CartItemAdapter.CartItemViewHolder(view);
        return itemViewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull CartItemViewHolder holder, int position) {
        holder.bind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class CartItemViewHolder extends RecyclerView.ViewHolder {
        TextView cartitemname,cartitemspecs,cartitemamount,deleteitemcart;
        Context context;
        DatabaseCreation dbc;
        SQLiteDatabase dbw,dbr;
        ContentValues cv;
        Cursor cursor;


        public CartItemViewHolder(@NonNull View itemView) {
            super(itemView);
            cartitemamount=(TextView)itemView.findViewById(R.id.cart_item_amt);
            cartitemname=(TextView)itemView.findViewById(R.id.cart_item_name);
            cartitemspecs=(TextView)itemView.findViewById(R.id.cart_item_specs);
            deleteitemcart=(TextView)itemView.findViewById(R.id.delete_item_cart);
            dbc=new DatabaseCreation(itemView.getContext());
            dbw=dbc.getWritableDatabase();
            dbr=dbc.getReadableDatabase();
            cv=new ContentValues();
            context=itemView.getContext();
        }
        public void bind(final ItemStock itemStock){
            cartitemname.setText(itemStock.getName());
            cartitemspecs.setText(itemStock.getCapacity()+", "+itemStock.getTemperature()+", "+itemStock.getQuality());
            cartitemamount.setText("₹ "+itemStock.getPrice());
            deleteitemcart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new AlertDialog.Builder(context)
                            .setTitle("Ooops!!!")
                            .setMessage("Do you want to delete this item from your cart?")

                            // Specifying a listener allows you to take an action before dismissing the dialog.
                            // The dialog is automatically dismissed when a dialog button is clicked.
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dbw.delete(FeedReader.FeedEntry.TABLE_NAME,FeedReader.FeedEntry.ITEM_ID+"="+itemStock.getId(),null);
                                    CartActivity.cartlist.remove(itemStock);
                                    notifyItemRemoved(getAdapterPosition());
                                    notifyItemRangeChanged(getAdapterPosition(),CartActivity.cartlist.size());
//                                        notify();
                                    CartActivity.recyclerViewincart.setAdapter(CartItemAdapter.this);
                                    CartActivity.totalamoutofcart.setText(CartActivity.getTotalAmtOfCart(CartActivity.cartlist));
                                    dialog.dismiss();

                                    // Continue with delete operation
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            })
                            .setCancelable(false)

                            // A null listener allows the button to dismiss the dialog and take no further action.
                            .setIcon(R.drawable.canimage)
                            .show();


                }
            });
        }
    }
}
