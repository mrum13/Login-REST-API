package com.example.login_restapi.activities.Register;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.login_restapi.activities.Login.LoginActivity;
import com.example.login_restapi.activities.ProfileActivity;
import com.example.login_restapi.models.DefaultResponse;
import com.example.login_restapi.R;
import com.example.login_restapi.api.RetrofitClient;
import com.example.login_restapi.storage.SharedPrefManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText editTextEmail,editTextPassword,editTextName,editTextSchool;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        editTextEmail=findViewById(R.id.editTextEmail);
        editTextPassword=findViewById(R.id.editTextPassword);
        editTextName=findViewById(R.id.editTextName);
        editTextSchool=findViewById(R.id.editTextSchool);

        findViewById(R.id.buttonSignUp).setOnClickListener(this);
        findViewById(R.id.textViewLogin).setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(SharedPrefManager.getInstance(this).isLoggedIn()){
            Intent intent = new Intent(this, ProfileActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }

    private void userSignUp(){
        String email=editTextEmail.getText().toString().trim();
        String password=editTextPassword.getText().toString().trim();
        String name=editTextName.getText().toString().trim();
        String school=editTextSchool.getText().toString().trim();

        if(email.isEmpty()){
            editTextEmail.setError("Email is required");
            editTextEmail.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editTextEmail.setError("Enter a valid email");
            editTextEmail.requestFocus();
            return;
        }

        if(password.isEmpty()){
            editTextPassword.setError("Password required");
            editTextPassword.requestFocus();
            return;
        }

        if(password.length()<6){
            editTextPassword.setError("Password should be atleast 6 character long");
            editTextPassword.requestFocus();
            return;
        }

        if(name.isEmpty()){
            editTextName.setError("Name Required");
            editTextName.requestFocus();
            return;
        }

        if(school.isEmpty()){
            editTextSchool.setError("School Required");
            editTextSchool.requestFocus();
            return;
        }

        Call<DefaultResponse> call= RetrofitClient
                .getInstance()
                .getApi()
                .createUser(email,password,name,school);

        call.enqueue(new Callback<DefaultResponse>() {
            @Override
            public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                if(response.code()==201){
                    DefaultResponse dr = response.body();
                    Toast.makeText(RegisterActivity.this, dr.getMsg(),Toast.LENGTH_LONG).show();
                }
                else if (response.code()==422){
                    Toast.makeText(RegisterActivity.this,"Pengguna sudah ada",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<DefaultResponse> call, Throwable t) {
                Toast.makeText(RegisterActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.buttonSignUp:
                userSignUp();
                break;
            case R.id.textViewLogin:
                startActivity(new Intent(this, LoginActivity.class));
                break;
        }
    }
}