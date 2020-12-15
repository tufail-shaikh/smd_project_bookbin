package com.tufailshaikh.bookstore1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Checkout extends AppCompatActivity {

    TextView name, phone, email, total_price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        name = findViewById(R.id.checkout_username);
        phone = findViewById(R.id.checkout_phone);
        email = findViewById(R.id.checkout_email);
        total_price = findViewById(R.id.checkout_price);

        total_price.setText("$" + String.valueOf(getIntent().getIntExtra("price", 0)));

        loadUserData();
    }

    private void loadUserData()
    {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                if (snapshot.exists())
                {
                    name.setText(snapshot.child("firstName").getValue().toString() + " " + snapshot.child("lastName").getValue().toString());
                    phone.setText(snapshot.child("phone").getValue().toString());
                    email.setText(snapshot.child("email").getValue().toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {

            }
        });
    }

    public void confirmOrder(View view) {
        startActivity(new Intent(this , ConfirmOrder.class));
        finish();
        return;
    }
}