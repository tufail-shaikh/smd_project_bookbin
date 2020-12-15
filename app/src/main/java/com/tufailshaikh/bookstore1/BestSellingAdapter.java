package com.tufailshaikh.bookstore1;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;

import java.util.List;



public class BestSellingAdapter extends RecyclerView.Adapter<BestSellingAdapter.MyViewHolder> {
    static List<Book> bestSellingList;
    Context context;
    public BestSellingAdapter(List<Book> contactList , Context context) {
        this.context= context;
        this.bestSellingList=contactList;
    }

    @NonNull
    @Override
    public BestSellingAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemRow = LayoutInflater.from(context).inflate(R.layout.row,parent,false);
        return new MyViewHolder(itemRow);
    }

    @Override
    public void onBindViewHolder(@NonNull BestSellingAdapter.MyViewHolder holder,final int position) {

        holder.title.setText(bestSellingList.get(position).getTitle());
        holder.author.setText(bestSellingList.get(position).getAuthor());

        //holder.email.setText(contactList.get(position).getEmail());

//        holder.author.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i=new Intent(view.getContext(),ProductDetailsActivity.class);
//                context.startActivity(i);
//            }
//        });
        Glide.with(context)
                .asBitmap()
                .load(bestSellingList.get(position).getBookCover())
                .into(holder.bookCover);

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent displayBook = new Intent (context, book_details.class);
                displayBook.putExtra("Title", bestSellingList.get(position).getTitle());
                displayBook.putExtra("Author", bestSellingList.get(position).getAuthor());
                displayBook.putExtra("Description", bestSellingList.get(position).getDescription());
                displayBook.putExtra("Price", Integer.toString(bestSellingList.get(position).getPrice()));
                displayBook.putExtra("IMG_URI", bestSellingList.get(position).getBookCover().toString());
                context.startActivity(displayBook);

            }
        });


    }

    @Override
    public int getItemCount() {
        return bestSellingList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title,author,description,price;
        ImageView bookCover;
        LinearLayout linearLayout;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.bookTitle);
            author = itemView.findViewById(R.id.bookAuthor);
            bookCover = itemView.findViewById(R.id.bookCover);
            linearLayout = itemView.findViewById(R.id.rowlayout);


        }
    }

}

