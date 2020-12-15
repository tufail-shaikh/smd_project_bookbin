package com.tufailshaikh.bookstore1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class SearchAdapter2 extends RecyclerView.Adapter<SearchAdapter2.MyViewHolder>
{
    static List<Book> searchList;
    Context context;

    public SearchAdapter2(Context context, List<Book> searchList)
    {
        this.context = context;
        this.searchList = searchList;
    }

    @NonNull
    @Override
    public SearchAdapter2.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(context).inflate(R.layout.search_row1, null, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchAdapter2.MyViewHolder holder, int position)
    {
        holder.title.setText(searchList.get(position).getTitle());
        holder.author.setText(searchList.get(position).getAuthor());
        holder.price.setText("$" + searchList.get(position).getPrice());

        Glide.with(context)
                .asBitmap()
                .load(searchList.get(position).getBookCover())
                .into(holder.cover);

    }

    @Override
    public int getItemCount()
    {
        return searchList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder
    {

        ImageView cover;
        TextView title, author, price;

        public MyViewHolder(@NonNull View itemView)
        {
            super(itemView);
            cover = itemView.findViewById(R.id.bookCoverRow);
            title = itemView.findViewById(R.id.bookTitleRow);
            author = itemView.findViewById(R.id.bookAuthorRow);
            price = itemView.findViewById(R.id.bookPriceRow);
        }
    }

    public void filterList(List<Book> filteredBooks)
    {
        searchList = filteredBooks;
        notifyDataSetChanged();
    }
}
