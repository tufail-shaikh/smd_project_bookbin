package com.tufailshaikh.bookstore1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Wishlist extends AppCompatActivity {

    RecyclerView rv;
    List<Book> wishlist;
    WishlistAdapter adapter;
    Uri imageURI1;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wishlist);

        wishlist=new ArrayList<Book>();
        rv=findViewById(R.id.wishlist_rv);
        mAuth = FirebaseAuth.getInstance();
//        imageURI1= Uri.parse("android.resource://" + getPackageName() + "/" + R.drawable._1);
//        wishlist.add(new Book("The great disruption","Adrian Wooldridge","The Great Disruption is a collection drawn from Adrian Wooldridge's influential Schumpeter columns in The Economist addressing the causes and profound consequences of the unprecedented disruption of business over the past five years. The Great Disruption has many causes.",50,imageURI1));

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.nav_wishlist);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_home:
                        startActivity(new Intent(getApplicationContext(),viewBooks.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.nav_wishlist:
                        return true;
                    case R.id.nav_search:
                        startActivity(new Intent(getApplicationContext(),Search.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.nav_cart:
                        startActivity(new Intent(getApplicationContext(),Cart.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.nav_setting:
                        startActivity(new Intent(getApplicationContext(),Account.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });

        RecyclerView.LayoutManager manager=new LinearLayoutManager(Wishlist.this);
        rv.setLayoutManager(manager);

        adapter = new WishlistAdapter(Wishlist.this, wishlist);
        rv.setAdapter(adapter);

        fetchWishlist();
    }

    private void fetchWishlist() {
//    {
//        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("wishlist");
//        databaseReference.addListenerForSingleValueEvent(new ValueEventListener()
//        {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
//            {
//                if (dataSnapshot.exists())
//                {
//                    for (DataSnapshot snapshot : dataSnapshot.getChildren())
//                    {
//                        String title = snapshot.child("title").getValue().toString();
//                        String author = snapshot.child("author").getValue().toString();
//                        String description = snapshot.child("description").getValue().toString();
//                        String price = snapshot.child("price").getValue().toString();
//                        String bookCoverUriString = snapshot.child("bookCover").getValue().toString();
//
//                        wishlist.add(new Book(title, author, description, Integer.parseInt(price), bookCoverUriString, snapshot.getValue().toString()));
//                        adapter.notifyDataSetChanged();
//                    }
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError)
//            {
//
//            }
//        });
        wishListHelper wishListHelper=new wishListHelper(Wishlist.this);
        SQLiteDatabase database=wishListHelper.getReadableDatabase();
        String[] projectionSlave=new String[]{
                MyWishListContract.WishList.userEmail,
                MyWishListContract.WishList.BookTitle,
                MyWishListContract.WishList.BookAuthor,
                MyWishListContract.WishList.BookDescription,
                MyWishListContract.WishList.BookPrice,
                MyWishListContract.WishList.BookUri

        };
        Cursor cursor=
                database.query(MyWishListContract.WishList.TABLE_NAME,
                        projectionSlave,
                        null,
                        null,
                        null,
                        null,
                        null);
        while (cursor.moveToNext()) {
            //sqLiteCounter+=1;
            String usergetId = cursor.getString(cursor.getColumnIndex(MyWishListContract.WishList.userEmail));
            String BookTitle = cursor.getString(cursor.getColumnIndex(MyWishListContract.WishList.BookTitle));
            String BookAuthor = cursor.getString(cursor.getColumnIndex(MyWishListContract.WishList.BookAuthor));
            String BookDescription = cursor.getString(cursor.getColumnIndex(MyWishListContract.WishList.BookDescription));
            String BookPrice = cursor.getString(cursor.getColumnIndex(MyWishListContract.WishList.BookPrice));
            String BookUri = cursor.getString(cursor.getColumnIndex(MyWishListContract.WishList.BookUri));
            //String Uid = cursor.getString(cursor.getColumnIndex(MyWishListContract.WishList.Uid));
            if(usergetId.equals((mAuth.getUid()))){
                Book bookObj=new Book(BookTitle,BookAuthor,BookDescription,
                        Integer.parseInt(BookPrice),BookUri,usergetId);

                wishlist.add(bookObj);

            }
        }
    }

}