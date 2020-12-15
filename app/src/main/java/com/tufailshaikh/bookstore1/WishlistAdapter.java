package com.tufailshaikh.bookstore1;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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

public class WishlistAdapter extends  RecyclerView.Adapter<WishlistAdapter.MyViewHolder> {
    static List<Book> wishlist;
    Context context;

    public WishlistAdapter(Context context, List<Book> cartList) {
        this.context = context;
        this.wishlist = cartList;
    }

    @NonNull
    @Override
    public WishlistAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(context).inflate(R.layout.wishlist_row, null,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull WishlistAdapter.MyViewHolder holder, int position) {
        holder.title.setText(wishlist.get(position).getTitle());
        //       holder.author.setText(searchList.get(position).getAuthor());
//        holder.price.setText( searchList.get(position).getPrice());

        Glide.with(context)
                .asBitmap()
                .load(wishlist.get(position).getBookCover())
                .into(holder.cover);
//
//        holder.ll.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//
//                AlertDialog.Builder builder = new AlertDialog.Builder(context);
//                builder.setTitle("Delete");
//                builder.setMessage("Are you sure you want to delete the item from cart ? ");
//                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.dismiss();
//
//                    }
//                });
//                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.dismiss();
//                    }
//                });
//                builder.show();
//
//                return false;
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return wishlist.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {

        LinearLayout ll;
        ImageView cover;
        TextView title,author,price;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            cover=itemView.findViewById(R.id.wishlist_bookCover);
            title=itemView.findViewById(R.id.wishlist_bookTitle);
            ll=itemView.findViewById(R.id.wishlist_row_layout);
        }
    }
}
