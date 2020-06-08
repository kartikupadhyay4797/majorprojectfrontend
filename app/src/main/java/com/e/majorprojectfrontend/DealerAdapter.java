package com.e.majorprojectfrontend;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

public class DealerAdapter extends RecyclerView.Adapter<DealerAdapter.DealerViewHolder> implements Filterable {


    ArrayList<Dealer> list,filteredList;
    ArrayFilter mFilter;

    public DealerAdapter(ArrayList<Dealer> list) {
        this.list=list;
        filteredList=list;
    }


    public void setList(ArrayList<Dealer> list){
        this.list=list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public DealerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context=viewGroup.getContext();
        int layoutIdForListDealer= R.layout.dealer_view;
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        boolean attachToParentImm=false;

        View view=layoutInflater.inflate(layoutIdForListDealer,viewGroup,attachToParentImm);
        DealerViewHolder DealerViewHolder=new DealerViewHolder(view);
        return DealerViewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull DealerViewHolder DealerViewHolder, int i) {
        DealerViewHolder.bind(list.get(i));
    }
/*@Override
    public void onBindViewHolder(@NonNull DealerViewHolder DealerViewHolder,int i){
        DealerViewHolder.bind(list.get(i));
    }*/

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public Filter getFilter() {
        if (mFilter == null) {
            mFilter = new ArrayFilter();
        }
        return mFilter;

    }

    private class ArrayFilter extends Filter {
        private Object lock=new Object();


        @Override
        protected FilterResults performFiltering(CharSequence prefix) {
            FilterResults results = new FilterResults();

            if (list == null) {
                synchronized (lock) {
                    list = new ArrayList<>();
                }
            }

            if (prefix == null || prefix.length() == 0) {
                synchronized (lock) {
                    results.values = filteredList;
                    results.count = filteredList.size();
                }
            } else {
                final String prefixString = prefix.toString().toLowerCase();

                ArrayList<Dealer> values = filteredList;
                int count = filteredList.size();

                ArrayList<Dealer> newValues = new ArrayList<Dealer>(count);

                for (int i = 0; i < count; i++) {
                    Dealer Dealer = values.get(i);
                    if (!newValues.contains(Dealer)) {
                        if (Dealer.getName().toLowerCase().contains(prefixString) || Dealer.getAddress().equals(prefixString)) {
                            newValues.add(Dealer);
                        }
                    }
                }

                results.values = newValues;
                results.count = newValues.size();
            }

            return results;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

            if(results.values!=null){
                list = (ArrayList<Dealer>) results.values;
            }else{
                list = new ArrayList<Dealer>();

            }
            if (results.count > 0) {
                notifyDataSetChanged();
            } else {

            }
        }
    }

    class DealerViewHolder extends RecyclerView.ViewHolder{
        TextView name,address,email;
        ImageView dealerImage;
        ProgressBar progressBar;
        Context context;
        CardView cardView;

        public DealerViewHolder(@NonNull View DealerView) {
            super(DealerView);

            name=(TextView)DealerView.findViewById(R.id.dealername);
            dealerImage=(ImageView)DealerView.findViewById(R.id.dealer_imageview);
            progressBar=DealerView.findViewById(R.id.progress_bar_in_dealerview);
            email=(TextView)DealerView.findViewById(R.id.dealeremail);
            address=(TextView)DealerView.findViewById(R.id.dealeraddress);
            context=DealerView.getContext();
            cardView=(CardView)DealerView.findViewById(R.id.cardview_in_dealerview);
        }
        public void bind(final Dealer Dealer){
            new DownloadImageTask().fitImage(dealerImage,Dealer.getImageURL(),progressBar);
            name.setText(Dealer.getName());
            address.setText(Dealer.getAddress());
            email.setText(Dealer.getEmail());
            dealerImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(context,ShowImage.class);
                    intent.putExtra("mainImage",Dealer.getImageURL());
                    context.startActivity(intent);
                }
            });
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent1=new Intent(context,ItemStockList.class);
                    intent1.putExtra("dealer_id",Dealer.getId());
                    context.startActivity(intent1);
                }
            });

        }
    }
}
