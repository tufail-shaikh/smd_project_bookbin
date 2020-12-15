package com.tufailshaikh.bookstore1;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Account extends AppCompatActivity {

    TextView profile, about_us, view_order, help, logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        profile =findViewById(R.id.account_profile);
        about_us =findViewById(R.id.account_aboutus);
        view_order =findViewById(R.id.account_orders);
        help =findViewById(R.id.account_help);
        logout =findViewById(R.id.account_logout);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.nav_setting);



        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
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
                    startActivity(new Intent(getApplicationContext(),Cart.class));
                    overridePendingTransition(0,0);
                    return true;
                case R.id.nav_setting:
                    return true;
            }
            return false;
        });


    }

    public void view_profile(View view) {

        startActivity(new Intent(getApplicationContext(),Profile.class));

    }


    public void logout(View view) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Logout");
        builder.setMessage("Are you sure you want to Logout ? ");
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(getApplicationContext(),SignIn.class));

            }
        });
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();

    }

    public void aboutus(View view) {

        startActivity(new Intent(getApplicationContext(),AboutUs.class));

    }

    public void help(View view) {

        startActivity(new Intent(getApplicationContext(),Help.class));

    }

    public void view_orders(View view) {

        startActivity(new Intent(getApplicationContext(),ViewOrder.class));

    }

}