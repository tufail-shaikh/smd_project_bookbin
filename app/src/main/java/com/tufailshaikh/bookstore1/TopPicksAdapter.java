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

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;

import java.util.List;

public class TopPicksAdapter extends RecyclerView.Adapter<TopPicksAdapter.MyViewHolder> {
    static List<Book> bookList;
    Context context;
    public TopPicksAdapter(List<Book> contactList , Context context) {
        this.context= context;
        this.bookList=contactList;
    }

    @NonNull
    @Override
    public TopPicksAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemRow = LayoutInflater.from(context).inflate(R.layout.row,parent,false);
        return new MyViewHolder(itemRow);
    }

    @Override
    public void onBindViewHolder(@NonNull TopPicksAdapter.MyViewHolder holder, final int position) {
        holder.title.setText(bookList.get(position).getTitle());
        holder.author.setText(bookList.get(position).getAuthor());
//        holder.author.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i=new Intent(view.getContext(),ProductDetailsActivity.class);
//                context.startActivity(i);
//            }
//        });

        //holder.email.setText(contactList.get(position).getEmail());
        Glide.with(context)
                .asBitmap()
                .load(bookList.get(position).getBookCover())
                .into(holder.bookCover);

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent displayBook = new Intent (context, book_details.class);
                displayBook.putExtra("Title", bookList.get(position).getTitle());
                displayBook.putExtra("Author", bookList.get(position).getAuthor());
                displayBook.putExtra("Description", bookList.get(position).getDescription());
                displayBook.putExtra("Price", Integer.toString(bookList.get(position).getPrice()));
                displayBook.putExtra("IMG_URI", bookList.get(position).getBookCover().toString());
                context.startActivity(displayBook);

            }
        });

    }







    @Override
    public int getItemCount() {
        return bookList.size();
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
