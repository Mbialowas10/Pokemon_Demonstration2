package com.example.pokemon_demonstration2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LogIn extends AppCompatActivity {
    EditText email;
    EditText password;
    String email_str;
    String pw_str;
    Button btn_sign_in, btn_register;
    private FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        btn_sign_in = findViewById(R.id.btn_sign_in);
        btn_register = findViewById(R.id.btn_register);
        auth = FirebaseAuth.getInstance();



        btn_sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email_str  = email.getText().toString();
                pw_str = password.getText().toString();
                email_str = email_str.trim();
                pw_str = pw_str.trim();
                signIn(email_str, pw_str);

            }
        });
        // create a new user account in FB
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(LogIn.this, Register.class);
                startActivity(i);
                //finish();
            }
        });
    }
    public void signIn(String email, String pw){
        Log.i("email", email);
        Log.i("password",pw);
        auth.signInWithEmailAndPassword(email, pw)
                .addOnCompleteListener(LogIn.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(LogIn.this, "User has logged in", Toast.LENGTH_LONG).show();
                            Intent i = new Intent(LogIn.this, MainActivity.class);
                            startActivity(i);
                            finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(LogIn.this, "Please ensure your login credentials are correct. Please try again.", Toast.LENGTH_LONG).show();
                            try {
                                throw task.getException();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
    }


    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = auth.getCurrentUser();
        if (currentUser != null) {
           Intent i  = new Intent(LogIn.this, MainActivity.class);
           startActivity(i);
           finish();
        }
    }





}