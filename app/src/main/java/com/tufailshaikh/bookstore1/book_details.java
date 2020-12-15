package com.tufailshaikh.bookstore1;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class book_details extends AppCompatActivity
{

    TextView title, author, description, price;
    ImageView bookCover;
    String Uidd;
    String BookUri;
    Uri bookCoverBook;
    Book book;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_details);

        title = findViewById(R.id.bookTitleDisplay);
        author = findViewById(R.id.bookAuthorDisplay);
        price = findViewById(R.id.bookPriceDisplay);
        description = findViewById(R.id.bookDescriptionDisplay);
        bookCover = findViewById(R.id.bookcoverdisplay);

        mAuth = FirebaseAuth.getInstance();
        Uidd = getIntent().getStringExtra("Uid");
        System.out.println(Uidd);

        Intent intent = getIntent();

        title.setText(intent.getStringExtra("Title"));
        author.setText(intent.getStringExtra("Author"));
        price.setText(intent.getStringExtra("Price"));
        description.setText(intent.getStringExtra("Description"));

        book = new Book(title.getText().toString(), author.getText().toString(), description.getText().toString(),
                Integer.parseInt(price.getText().toString()), intent.getStringExtra("IMG_URI"), Uidd);

//        name.setText(intent.getStringExtra("NAME"));
//        phone.setText(intent.getStringExtra("PHONE"));
//        email.setText(intent.getStringExtra("EMAIL"));
//        address.setText(intent.getStringExtra("ADDRESS"));

        Uri imageUri = Uri.parse(intent.getStringExtra("IMG_URI"));
        BookUri=intent.getStringExtra("IMG_URI");

        Glide.with(this)
                .asBitmap()
                .load(imageUri)
                .into(bookCover);
    }

    public void addToCart(View view)
    {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("cart").child(Uidd);

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                if (snapshot.exists())
                {
                    System.out.println("Adding item to cart again...");
                    Long currentQuantity = (Long) snapshot.child("quantity").getValue();
                    databaseReference.child("quantity").setValue(currentQuantity + 1);
                }
                else
                {
                    System.out.println("Adding item to cart for first time...");
                    databaseReference.setValue(book);
                    databaseReference.child("quantity").setValue(1);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {

            }
        });

        Toast.makeText(this, "Added to Cart", Toast.LENGTH_SHORT).show();
    }

    public void addToWishlist(View view)
    {
        //by firebase Commeneted below
//        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("wishlist");
//        databaseReference.child(Uid).setValue(book);
//
//        Toast.makeText(this, "Added to Wishlist", Toast.LENGTH_SHORT).show();  a
        //by sqlite here
        //Book bookObj=new Book(titleBook,authorBook,descriptionBook,50,bookCoverBook,FirebaseAuth.getInstance().getCurrentUser().getUid());
        //if(!viewBooks.internetConnectivity){
        wishListHelper wishListHelper = new wishListHelper(book_details.this);
        SQLiteDatabase wishListDb = wishListHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(MyWishListContract.WishList.userEmail,mAuth.getUid());
        contentValues.put(MyWishListContract.WishList.BookTitle, title.getText().toString());
        contentValues.put(MyWishListContract.WishList.BookAuthor,author.getText().toString());
        contentValues.put(MyWishListContract.WishList.BookDescription,description.getText().toString());
        contentValues.put(MyWishListContract.WishList.BookPrice,price.getText().toString());
        contentValues.put(MyWishListContract.WishList.BookUri, BookUri);
        //contentValues.put(MyWishListContract.WishList.Uid, Uidd);

        double result = wishListDb.insert(MyWishListContract.WishList.TABLE_NAME, null, contentValues);
        if (result >= 1.0) {
            Toast.makeText(book_details.this, "SQLite=wishList", Toast.LENGTH_SHORT).show();
        }
        wishListDb.close();
        wishListHelper.close();
        //}
    }
}