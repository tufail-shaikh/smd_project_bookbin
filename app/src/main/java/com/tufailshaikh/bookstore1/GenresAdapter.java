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



public class GenresAdapter extends RecyclerView.Adapter<GenresAdapter.MyViewHolder> {
    static List<Book> genresList;
    Context context;
    public GenresAdapter(List<Book> contactList , Context context) {
        this.context= context;
        this.genresList=contactList;
    }

    @NonNull
    @Override
    public GenresAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemRow = LayoutInflater.from(context).inflate(R.layout.row,parent,false);
        return new MyViewHolder(itemRow);
    }

    @Override
    public void onBindViewHolder(@NonNull GenresAdapter.MyViewHolder holder,final int position) {

        holder.title.setText(genresList.get(position).getTitle());
        holder.author.setText(genresList.get(position).getAuthor());

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
                .load(genresList.get(position).getBookCover())
                .into(holder.bookCover);

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent displayBook = new Intent (context, book_details.class);
                displayBook.putExtra("Title", genresList.get(position).getTitle());
                displayBook.putExtra("Author", genresList.get(position).getAuthor());
                displayBook.putExtra("Description", genresList.get(position).getDescription());
                displayBook.putExtra("Price", Integer.toString(genresList.get(position).getPrice()));
                displayBook.putExtra("IMG_URI", genresList.get(position).getBookCover().toString());
                displayBook.putExtra("Uid", genresList.get(position).getUid());
                context.startActivity(displayBook);
            }
        });
    }



    @Override
    public int getItemCount() {
        return genresList.size();
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

