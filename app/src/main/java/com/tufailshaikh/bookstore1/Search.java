package com.tufailshaikh.bookstore1;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.widget.EditText;

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

public class Search extends AppCompatActivity
{
    RecyclerView rv;
    //List<String> searchList;
    List<Book> searchList;
    //SearchAdapter adapter;
    SearchAdapter2 adapter;
    Uri imageURI1;

    EditText search_EditText;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        searchList = new ArrayList<Book>();
        rv = findViewById(R.id.rv);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.nav_search);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener()
        {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item)
            {
                switch (item.getItemId())
                {
                    case R.id.nav_home:
                        startActivity(new Intent(getApplicationContext(), viewBooks.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.nav_wishlist:
                        startActivity(new Intent(getApplicationContext(), Wishlist.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.nav_search:
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


        RecyclerView.LayoutManager manager = new LinearLayoutManager(Search.this);
        rv.setLayoutManager(manager);

        adapter = new SearchAdapter2(Search.this, searchList);
        rv.setAdapter(adapter);

        search_EditText = findViewById(R.id.search_EditText);
        search_EditText.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {

            }

            @Override
            public void afterTextChanged(Editable s)
            {
                filter(s.toString());
            }
        });

        loadBooksFromFirebase();
    }

    private void loadBooksFromFirebase()
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

                        searchList.add(new Book(title, author, description, Integer.parseInt(price), bookCoverUriString, snapshot.getKey()));
                        adapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {

            }
        });
    }

    private void filter(String toString)
    {
        List<Book> filteredBooks = new ArrayList<>();

        for (Book book : searchList)
        {
            if (book.getTitle().toLowerCase().contains(toString.toLowerCase())
                    || book.getAuthor().toLowerCase().contains(toString.toLowerCase()))
            {
                filteredBooks.add(book);
            }
        }

        adapter.filterList(filteredBooks);
    }
}