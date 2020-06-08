package com.e.majorprojectfrontend;

import android.content.ClipData;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class OrderHistoryAdapter extends RecyclerView.Adapter<OrderHistoryAdapter.OrderHistoryItemViewHolder> {
    ArrayList<OrderHistoryItem> list;

    public OrderHistoryAdapter(ArrayList<OrderHistoryItem> list) {
        this.list=list;
    }


    public void setList(ArrayList<OrderHistoryItem> list){
        this.list=list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public OrderHistoryAdapter.OrderHistoryItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context=viewGroup.getContext();
        int layoutIdForListItemStock= R.layout.orderhistoryitem_view;
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        boolean attachToParentImm=false;

        View view=layoutInflater.inflate(layoutIdForListItemStock,viewGroup,attachToParentImm);
        OrderHistoryAdapter.OrderHistoryItemViewHolder orderHistoryItemViewHolder=new OrderHistoryAdapter.OrderHistoryItemViewHolder(view);
        return orderHistoryItemViewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull OrderHistoryItemViewHolder holder, int position) {
        holder.bind(list.get(position));
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    class OrderHistoryItemViewHolder extends RecyclerView.ViewHolder{
        TextView orderid,datacreated,itemnames,itemprices,totalprice,orderstatus,orderdealer;
        Context context;

        public OrderHistoryItemViewHolder(@NonNull View ItemStockView) {
            super(ItemStockView);

            orderid=(TextView)ItemStockView.findViewById(R.id.ordernumber);
            itemnames=(TextView)ItemStockView.findViewById(R.id.items);
            itemprices=(TextView)ItemStockView.findViewById(R.id.prices);
            totalprice=(TextView)ItemStockView.findViewById(R.id.total);
            datacreated=(TextView)ItemStockView.findViewById(R.id.datecreated);
            orderstatus=(TextView)ItemStockView.findViewById(R.id.orderstatus);
            orderdealer=(TextView) ItemStockView.findViewById(R.id.orderdealer);
            context=ItemStockView.getContext();
        }
        public void bind(final OrderHistoryItem ItemStock){
            orderid.setText("#"+ItemStock.getOrderid());
            itemnames.setText(ItemStock.getItemnames());
            itemprices.setText(ItemStock.getItemprices());
            totalprice.setText(ItemStock.getTotalprice());
            datacreated.setText(ItemStock.getDatecreated().substring(0,10)+" "+ItemStock.getDatecreated().substring(11,19));
            orderstatus.setText(ItemStock.getOrderstatus());
            orderdealer.setText(ItemStock.getDealer());
        }
    }

}
