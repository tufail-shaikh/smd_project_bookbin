package com.tufailshaikh.bookstore1;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class viewBooks extends AppCompatActivity
{
    public static boolean internetConnectivity=false;
    RecyclerView recyclerViewTopPicks;
    RecyclerView recyclerViewBestSelling;
    RecyclerView recyclerViewGenres;
    TopPicksAdapter topPicksAdapter;
    BestSellingAdapter bestSellingAdapter;
    GenresAdapter genresAdapter;

    MyBroadcastReciever broadCastReciever;
    static List<Book> topPicksBooks;
    static List<Book> bestSellingBooks;
    static List<Book> genresBooks;

    Uri imageURI1 = null;
    Uri imageURI2 = null;
    Uri imageURI3 = null;
    Uri imageURI4 = null;
    Uri imageURI5 = null;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_books);
        topPicksBooks = new ArrayList<>();
        bestSellingBooks = new ArrayList<>();
        genresBooks = new ArrayList<>();

        broadCastReciever=new MyBroadcastReciever();
        IntentFilter intentFilter=new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(broadCastReciever,intentFilter);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.nav_home);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener()
        {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item)
            {
                switch (item.getItemId())
                {
                    case R.id.nav_home:
                        return true;
                    case R.id.nav_wishlist:
                        startActivity(new Intent(getApplicationContext(), Wishlist.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.nav_search:
                        startActivity(new Intent(getApplicationContext(), Search.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.nav_cart:
                        startActivity(new Intent(getApplicationContext(), Cart.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.nav_setting:
                        startActivity(new Intent(getApplicationContext(), Account.class));
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });

        recyclerViewTopPicks = findViewById(R.id.recyclerviewTopPicks);
        RecyclerView.LayoutManager layoutManager1 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewTopPicks.setLayoutManager(layoutManager1);



        topPicksAdapter = new TopPicksAdapter(topPicksBooks, this);
        recyclerViewTopPicks.setAdapter(topPicksAdapter);





        recyclerViewBestSelling = findViewById(R.id.recyclerviewBestSelling);
        RecyclerView.LayoutManager layoutManager2 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewBestSelling.setLayoutManager(layoutManager2);
        bestSellingAdapter = new BestSellingAdapter(bestSellingBooks, this);
        recyclerViewBestSelling.setAdapter(bestSellingAdapter);

        recyclerViewGenres = findViewById(R.id.recyclerviewGenres);
        RecyclerView.LayoutManager layoutManager3 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewGenres.setLayoutManager(layoutManager3);
        genresAdapter = new GenresAdapter(genresBooks, this);
        recyclerViewGenres.setAdapter(genresAdapter);

        loadCatalogueFromFirebase();
    }

    private void loadCatalogueFromFirebase()
    {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("catalogue");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                if (dataSnapshot.exists())
                {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren())
                    {
                        String title = snapshot.child("title").getValue().toString();
                        String author = snapshot.child("author").getValue().toString();
                        String description = snapshot.child("description").getValue().toString();
                        String price = snapshot.child("price").getValue().toString();

                        String bookCoverUriString = snapshot.child("bookCover").getValue().toString();

                        bestSellingBooks.add(new Book(title, author, description, Integer.parseInt(price), bookCoverUriString, snapshot.getKey()));
                        topPicksBooks.add(new Book(title, author, description, Integer.parseInt(price), bookCoverUriString, snapshot.getKey()));
                        genresBooks.add(new Book(title, author, description, Integer.parseInt(price), bookCoverUriString, snapshot.getKey()));
                        genresAdapter.notifyDataSetChanged();
                        topPicksAdapter.notifyDataSetChanged();
                        bestSellingAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {

            }
        });
    }

}