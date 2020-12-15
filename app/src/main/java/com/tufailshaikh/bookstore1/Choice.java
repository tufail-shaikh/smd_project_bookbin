package com.tufailshaikh.bookstore1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Choice extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice);
    }

    public void SignIn(View view) {
        startActivity(new Intent(this,SignIn.class));
    }

    public void SignUp(View view) {
        startActivity(new Intent(this,SignUp.class));
    }
}