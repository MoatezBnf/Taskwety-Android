package com.example.taskwety_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private FirebaseAuth mAuth;
    EditText email,password;
    Button signUpBtn,loginBtn;
    String TAG = "Android Test";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        email=findViewById(R.id.emailEditText);
        password=findViewById(R.id.passwordEditText);
        signUpBtn=findViewById(R.id.signUpButton);
        signUpBtn.setOnClickListener(this);
        loginBtn=findViewById(R.id.loginButton);
        loginBtn.setOnClickListener(this);
    }

    @Override
    public void onStart(){
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Log.d(TAG,"Already Logged in!");
            Toast.makeText(MainActivity.this, "Already Logged in!",
                    Toast.LENGTH_SHORT).show();
            Intent myIntent = new Intent(MainActivity.this, ProfileActivity.class);
            startActivity(myIntent);
            finish();
        }
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.signUpButton){
            signUp();
        } else if (v.getId()==R.id.loginButton) {
            Intent myIntent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(myIntent);
            finish();
        }
    }

    public void signUp(){
        mAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "createUserWithEmail:success");
                        Toast.makeText(MainActivity.this, "Account Created Successfully.",
                                Toast.LENGTH_SHORT).show();
                        Intent myIntent = new Intent(MainActivity.this, ProfileActivity.class);
                        startActivity(myIntent);
                        finish();
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "createUserWithEmail:failure", task.getException());
                        Toast.makeText(MainActivity.this, "Authentication failed, please Try again.",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }
}