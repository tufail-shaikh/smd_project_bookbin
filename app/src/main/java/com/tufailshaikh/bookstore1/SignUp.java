package com.tufailshaikh.bookstore1;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity
{
    private static String str;
    EditText firstName, lastName, email, password, mobile;
    Button signUp;
    TextView signUpTextView;
    private FirebaseAuth firebaseAuth;
    boolean valid = false, validemail = false;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        firebaseAuth = FirebaseAuth.getInstance();
        firstName = findViewById(R.id.firstName);
        lastName = findViewById(R.id.lastName);
        email = findViewById(R.id.Email);
        password = findViewById(R.id.password);
        mobile = findViewById(R.id.mobile);

        signUp = findViewById(R.id.signUp);
        str = firstName.getText().toString();

        signUp.setOnClickListener(v -> {
            if (firstName.getText().toString().isEmpty())
                firstName.setError("This is a required field!");
            else if (lastName.getText().toString().isEmpty())
                lastName.setError("This is a required field!");
            else if (email.getText().toString().isEmpty())
                email.setError("This is a required field!");
            else if (password.getText().toString().isEmpty())
                password.setError("This is a required field!");
            else if (mobile.getText().toString().isEmpty())
                mobile.setError("This is a required field!");
            else
            {
                final ProgressDialog dialog = new ProgressDialog(this);
                dialog.setMessage("Signing up...");
                dialog.show();

                final String userEmail = email.getText().toString();
                final String userPassword = password.getText().toString();

                if (firebaseAuth.getCurrentUser() != null)
                    firebaseAuth.signOut();

                firebaseAuth.createUserWithEmailAndPassword(userEmail, userPassword)

                        .addOnCompleteListener(new OnCompleteListener<AuthResult>()
                        {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task)
                            {
                                if (task.isSuccessful())
                                {
                                    final Intent registrationIntent = new Intent(SignUp.this, SignIn.class);

                                    registrationIntent.putExtra("userEmail", userEmail);
                                    registrationIntent.putExtra("userPassword", userPassword);

                                    FirebaseUser user = firebaseAuth.getCurrentUser();

                                    registrationIntent.putExtra("userUid", user.getUid());

                                    User userObject = new User(user.getUid(), firstName.getText().toString(), lastName.getText().toString(), email.getText().toString(), password.getText().toString(), mobile.getText().toString());

                                    FirebaseDatabase.getInstance().getReference().child("users").child(user.getUid()).setValue(userObject);

                                    dialog.dismiss();

                                    startActivity(registrationIntent);
                                    finish();
                                    return;
                                }
                                else
                                {
                                    dialog.dismiss();
                                    Toast.makeText(SignUp.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                    return;
                                }
                            }
                        })

                        .addOnFailureListener(new OnFailureListener()
                        {
                            @Override
                            public void onFailure(@NonNull Exception e)
                            {
                                dialog.dismiss();
                                Toast.makeText(SignUp.this, e.getMessage(), Toast.LENGTH_LONG).show();
                                return;
                            }
                        });
            }
        });

        firstName.addTextChangedListener(new TextWatcher()
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


                boolean isReady = firstName.getText().toString().length() > 1;

                if (isReady)
                {
                    signUp.setBackground(getResources().getDrawable(R.drawable.signtrue));
                    signUp.setEnabled(true);
                }
                else
                {
                    signUp.setEnabled(false);
                }
            }


        });

    }


    public void signUp(View view)
    {
        startActivity(new Intent(this, SignIn.class));
    }

    public void signUpBack(View view)
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
}