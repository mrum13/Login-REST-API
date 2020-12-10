package com.example.login_restapi.activities.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.login_restapi.R;
import com.example.login_restapi.activities.ProfileActivity;
import com.example.login_restapi.activities.Register.RegisterActivity;
import com.example.login_restapi.api.RetrofitClient;
import com.example.login_restapi.storage.SharedPrefManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText editTextEmail,editTextPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextEmail=findViewById(R.id.editTextEmail);
        editTextPassword=findViewById(R.id.editTextPassword);

        findViewById(R.id.buttonLogin).setOnClickListener(this);
        findViewById(R.id.textViewRegister).setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(SharedPrefManager.getInstance(this).isLoggedIn()){
            Intent intent = new Intent(this,ProfileActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }

    private void userLogin(){
        String email=editTextEmail.getText().toString().trim();
        String password=editTextPassword.getText().toString().trim();

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

        Call<LoginResponse> call= RetrofitClient.getInstance().getApi().userLogin(email,password);

        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                LoginResponse loginResponse = response.body();

                if(!loginResponse.isError()){
                    SharedPrefManager.getInstance(LoginActivity.this).saveUser(loginResponse.getUser());
                    Intent intent = new Intent(LoginActivity.this, ProfileActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }else {
                    Toast.makeText(LoginActivity.this, loginResponse.getMessage(),Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Gagal Koneksi",Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.buttonLogin:
                userLogin();
                break;
            case R.id.textViewRegister:
                startActivity(new Intent(this, RegisterActivity.class));
                break;
        }
    }
}