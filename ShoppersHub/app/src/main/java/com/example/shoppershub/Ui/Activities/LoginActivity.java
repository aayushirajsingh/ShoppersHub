package com.example.shoppershub.Ui.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.shoppershub.Model.LoginModel;
import com.example.shoppershub.R;
import com.example.shoppershub.Retrofit.ApiInterface;
import com.example.shoppershub.Retrofit.RetrofitClient;
import com.example.shoppershub.databinding.ActivityLoginBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    ActivityLoginBinding binding;
    SharedPreferences sharedPreferences;
    Dialog progressbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // ViewBinding
        binding=ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        sharedPreferences = getSharedPreferences("login",MODE_PRIVATE);

        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String username = binding.etUsername.getText().toString();
                String password = binding.etPassword.getText().toString();

                if(username.isEmpty() || password.isEmpty()){
                    Toast.makeText(LoginActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    progressbar.show();
                    checkLoginDetails(username, password);
                }
            }
        });

        //dialog box
        progressbar = new Dialog(this);
        progressbar.setContentView(R.layout.progressbarlayout);
        progressbar.setCancelable(false);

        binding.forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent forgotPassword = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
                startActivity(forgotPassword);
            }
        });
        binding.tvSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signup = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(signup);
            }
        });
    }

    public void checkLoginDetails(String username, String password){

        ApiInterface apiInterface = RetrofitClient.getInstance().getApi();
        Call<LoginModel> call = apiInterface.login_verification(username, password);

        call.enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                if(response.isSuccessful() && response.body()!=null) {

                    LoginModel loginModel = response.body();
                    String message = loginModel.getMessage();
                    progressbar.dismiss();

                    if ("Success".equals(message)) {

                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putInt("user_id",loginModel.getData().get(0).getUser_id());
                        editor.putString("username",loginModel.getData().get(0).getUsername());
                        editor.apply();

                        Toast.makeText(LoginActivity.this, "Logged In Successfully", Toast.LENGTH_SHORT).show();

                        Intent dashboard = new Intent(LoginActivity.this, DashboardActivity.class);
                        startActivity(dashboard);
                        finish();
                    }
                    else{
                        Toast.makeText(LoginActivity.this, "Login Failed: " + message, Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(LoginActivity.this, "API Error: " + response.message(), Toast.LENGTH_SHORT).show();
                }
                binding.etUsername.setText("");
                binding.etPassword.setText("");
            }

            @Override
            public void onFailure(Call<LoginModel> call, Throwable t) {
                progressbar.dismiss();
                Toast.makeText(LoginActivity.this, "Network Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}