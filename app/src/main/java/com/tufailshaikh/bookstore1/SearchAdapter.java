package com.tufailshaikh.bookstore1;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class SearchAdapter extends  RecyclerView.Adapter<SearchAdapter.MyViewHolder> {
    static List<String> searchList;
    Context context;

    public SearchAdapter(Context context, List<String> searchList) {
        this.context = context;
        this.searchList = searchList;
    }

    @NonNull
    @Override
    public SearchAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(context).inflate(R.layout.search_row, null,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchAdapter.MyViewHolder holder, int position) {
        holder.name.setText((CharSequence) searchList.get(position));
        holder.ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent i=new Intent(view.getContext(),ProductDetailsActivity.class);
                //c.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return searchList.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name,phone;
        LinearLayout ll;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.name);
            ll=itemView.findViewById(R.id.ll);
        }
    }
}
