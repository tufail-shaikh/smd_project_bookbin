package com.tufailshaikh.bookstore1;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignIn extends AppCompatActivity
{
    ImageButton back;
    TextView SignIn, forgotPassword;
    EditText email, password;
    CheckBox checkBox;
    Button signIn;

    boolean valid = false, validemail = false;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        signIn = findViewById(R.id.signIn);

        if (getIntent().getStringExtra("userEmail") != null)
            email.setText(getIntent().getStringExtra("userEmail"));
        if (getIntent().getStringExtra("userPassword") != null)
            password.setText(getIntent().getStringExtra("userPassword"));

        firebaseAuth = FirebaseAuth.getInstance();

//        signIn.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View v)
//            {
////                Toast.makeText(SignIn.this, "SignIn successfully", Toast.LENGTH_SHORT).show();
////                startActivity(new Intent(SignIn.this, viewBooks.class));
//                signIn();
//            }
//        });

        password.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {

                // TODO Auto-generated method stub
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {

                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s)
            {
                boolean isReady = password.getText().toString().length() > 1;

                if (isReady)
                {
                    signIn.setBackground(getResources().getDrawable(R.drawable.signtrue));
                    signIn.setEnabled(true);
                }
                else
                {
                    signIn.setEnabled(false);
                }
            }


        });
    }

//    public void signIn(View view)
//    {
//        startActivity(new Intent(this, viewBooks.class));
//    }

    public void signInBack(View view)
    {
        startActivity(new Intent(this, Choice.class));
    }

    public boolean checkemail(EditText editText)
    {
        if (editText.getText().toString().contains("@"))
        {
            validemail = true;
        }
        else
        {
            validemail = false;
        }
        return validemail;
    }

    public void signIn(View view)
    {
        if (firebaseAuth.getCurrentUser() != null) /* Sign out the previous user */
        {
            firebaseAuth.signOut();
        }

        final String userEmail = email.getText().toString();
        String userPassword = password.getText().toString();

        if (userEmail.isEmpty())
        {
            email.setError("Email is required!");
        }
        else if (userPassword.isEmpty())
        {
            password.setError("Password is missing!");
        }
        else
        {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Authenticating with Firebase...");
            progressDialog.show();

            firebaseAuth.signInWithEmailAndPassword(userEmail, userPassword)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>()
                    {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task)
                        {
                            if (task.isSuccessful())
                            {
                                // Sign in success, update UI with the signed-in user's information

                                progressDialog.dismiss();

                                Intent intent = new Intent(SignIn.this, viewBooks.class);

                                startActivity(intent);

                                finish();
                            }
                            else
                            {
                                // If sign in fails, display a message to the user.

                                progressDialog.dismiss();
                                Toast.makeText(SignIn.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            }

                            // ...
                        }
                    });
        }
    }
}