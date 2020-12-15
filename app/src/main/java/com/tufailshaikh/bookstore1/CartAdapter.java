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

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyViewHolder>
{
    static List<CartBook> cartList;
    Context context;

    public CartAdapter(Context context, List<CartBook> cartList)
    {
        this.context = context;
        this.cartList = cartList;
    }

    @NonNull
    @Override
    public CartAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(context).inflate(R.layout.cart_row, null, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.MyViewHolder holder, int position)
    {
        holder.title.setText(cartList.get(position).getTitle());
//        holder.author.setText(cartList.get(position).getAuthor());
        holder.price.setText("$" + String.valueOf(cartList.get(position).getPrice()));

        Glide.with(context)
                .asBitmap()
                .load(cartList.get(position).getBookCover())
                .into(holder.cover);

        holder.bookQuantity.setText(String.valueOf(cartList.get(position).quantity));

        holder.ll.setOnLongClickListener(new View.OnLongClickListener()
        {
            @Override
            public boolean onLongClick(View v)
            {

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Delete");
                builder.setMessage("Are you sure you want to delete the item from cart ? ");
                builder.setPositiveButton("YES", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        dialog.dismiss();

                    }
                });
                builder.setNegativeButton("NO", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        dialog.dismiss();
                    }
                });
                builder.show();

                return false;
            }
        });

    }

    @Override
    public int getItemCount()
    {
        return cartList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder
    {

        LinearLayout ll;
        ImageView cover;
        TextView title, author, price, bookQuantity;

        public MyViewHolder(@NonNull View itemView)
        {
            super(itemView);
            cover = itemView.findViewById(R.id.cart_bookCover);
            title = itemView.findViewById(R.id.cart_bookTitle);
            price = itemView.findViewById(R.id.cart_price);
            ll = itemView.findViewById(R.id.cart_row_layout);
            bookQuantity = itemView.findViewById(R.id.book_quantity);
        }
    }
}
