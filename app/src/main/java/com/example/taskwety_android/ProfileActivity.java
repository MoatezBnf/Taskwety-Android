package com.example.taskwety_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import java.util.Objects;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {
    private FirebaseAuth mAuth;
    EditText new_name;
    TextView email,name;
    Button update,logout,task;
    String TAG = "Android Test";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        mAuth = FirebaseAuth.getInstance();
        update=findViewById(R.id.updateProfileButton);
        update.setOnClickListener(this);
        new_name=findViewById(R.id.displayNameEditText);
        name=findViewById(R.id.displayNameTextView);
        email=findViewById(R.id.emailTextView);
        logout = findViewById(R.id.logoutButton);
        logout.setOnClickListener(this);
        task = findViewById(R.id.taskButton);
        task.setOnClickListener(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            FirebaseUser user = mAuth.getCurrentUser();
            if (user != null) {
                Log.d(TAG, Objects.requireNonNull(user.getEmail()));
                Log.d(TAG, user.getUid());
                if (user.getDisplayName() != null) {
                    Log.d(TAG, user.getDisplayName());
                    name.setText(user.getDisplayName());
                } else {
                    Log.d(TAG, "Display name is null");
                }
                email.setText(user.getEmail());
            } else {
                Log.d(TAG, "User is null.");
                Toast.makeText(ProfileActivity.this, "User information is not available.", Toast.LENGTH_SHORT).show();
            }
        } else {
            Log.d(TAG, "Not Logged in.");
            Toast.makeText(ProfileActivity.this, "Not Logged in.", Toast.LENGTH_SHORT).show();
            Intent myIntent = new Intent(ProfileActivity.this, LoginActivity.class);
            startActivity(myIntent);
            finish();
        }
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.updateProfileButton){
            Update();
        }else if (v.getId() == R.id.logoutButton) {
            Logout();
        } else if (v.getId()==R.id.taskButton) {
            Intent intent = new Intent(ProfileActivity.this, TaskActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onRestart(){super.onRestart();}

    public void Update(){
        String newDisplayName = new_name.getText().toString().trim();

        if (newDisplayName.isEmpty()) {
            Toast.makeText(ProfileActivity.this, "Display name cannot be empty.", Toast.LENGTH_SHORT).show();
            return;
        }
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        UserProfileChangeRequest profileUpdates=new UserProfileChangeRequest.Builder()
                .setDisplayName(new_name.getText().toString())
                .build();
        assert user != null;
        user.updateProfile(profileUpdates)
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        Log.d(TAG,"User profile updated.");
                        Toast.makeText(ProfileActivity.this, "User Profile Updated Successfully.",
                                Toast.LENGTH_SHORT).show();
                        name.setText(newDisplayName);
                    }
                });
    }

    private void Logout() {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

}