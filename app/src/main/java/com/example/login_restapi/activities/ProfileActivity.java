package com.example.login_restapi.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.login_restapi.R;
import com.example.login_restapi.activities.Register.RegisterActivity;
import com.example.login_restapi.models.User;
import com.example.login_restapi.storage.SharedPrefManager;

public class ProfileActivity extends AppCompatActivity {

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        textView=findViewById(R.id.textView);
        User user = SharedPrefManager.getInstance(this).getUser();
        textView.setText("Welcome back "+user.getName());
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(!SharedPrefManager.getInstance(this).isLoggedIn()){
            Intent intent = new Intent(this, RegisterActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }
}