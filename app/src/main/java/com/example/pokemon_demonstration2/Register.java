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

public class Register extends AppCompatActivity {

    EditText etb_email;
    EditText etb_password;
    Button btn_save;
    FirebaseAuth auth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        etb_email = findViewById(R.id.etb_email);
        etb_password = findViewById(R.id.etb_password);
        btn_save = findViewById(R.id.btn_save);
        auth = FirebaseAuth.getInstance();

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = etb_email.getText().toString();
                String password = etb_password.getText().toString();

                createUserAccount(email, password);
            }
        });

    }
    public void createUserAccount(String email_str, String pw_str){
        Log.i("mjb", email_str);
        Log.i("pw_str", pw_str);
        auth.createUserWithEmailAndPassword(email_str, pw_str)
                .addOnCompleteListener(Register.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(Register.this, "user has been created", Toast.LENGTH_LONG).show();
                            Intent i = new Intent(Register.this, LogIn.class);
                            startActivity(i);
                            finish();

                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(Register.this, "Error while authenticating", Toast.LENGTH_LONG).show();

                        }
                    }
                });
    }
}