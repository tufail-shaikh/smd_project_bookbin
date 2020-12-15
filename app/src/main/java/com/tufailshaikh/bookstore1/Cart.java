package com.tufailshaikh.bookstore1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Cart extends AppCompatActivity {

    RecyclerView rv;

    List<CartBook> cartList;
    CartAdapter adapter;
    Uri imageURI1;
    Uri imageURI2;

    TextView total_price_et;
    int totalPrice = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        cartList=new ArrayList<>();
        rv=findViewById(R.id.cart_rv);
        total_price_et = findViewById(R.id.total_price);

        imageURI1= Uri.parse("android.resource://" + getPackageName() + "/" + R.drawable._1);
//        cartList.add(new Book("The great disruption","Adrian Wooldridge","The Great Disruption is a collection drawn from Adrian Wooldridge's influential Schumpeter columns in The Economist addressing the causes and profound consequences of the unprecedented disruption of business over the past five years. The Great Disruption has many causes.",50,imageURI1));

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.nav_cart);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_home:
                        startActivity(new Intent(getApplicationContext(),viewBooks.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.nav_wishlist:
                        startActivity(new Intent(getApplicationContext(),Wishlist.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.nav_search:
                        startActivity(new Intent(getApplicationContext(),Search.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.nav_cart:
                        return true;
                    case R.id.nav_setting:
                        startActivity(new Intent(getApplicationContext(),Account.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });

        RecyclerView.LayoutManager manager=new LinearLayoutManager(Cart.this);
        rv.setLayoutManager(manager);

        adapter = new CartAdapter(Cart.this, cartList);
        rv.setAdapter(adapter);

        loadCartFromFirebase();
    }

    private void loadCartFromFirebase()
    {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("cart");
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

                        String quantity = snapshot.child("quantity").getValue().toString();

                        cartList.add(new CartBook(title, author, description, Integer.parseInt(price), bookCoverUriString, snapshot.getKey(), Integer.parseInt(quantity)));
                        adapter.notifyDataSetChanged();
                        totalPrice += Integer.parseInt(price);
                    }
                }
                total_price_et.setText(String.valueOf(totalPrice));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {

            }
        });
    }

    public void checkOut(View view) {
        Intent intent = new Intent(this,Checkout.class);
        intent.putExtra("price", totalPrice);
        startActivity(intent);
    }
}