package com.example.shoppershub.Ui.Activities;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.shoppershub.Model.SignupModel;
import com.example.shoppershub.R;
import com.example.shoppershub.Retrofit.ApiInterface;
import com.example.shoppershub.Retrofit.RetrofitClient;
import com.example.shoppershub.databinding.ActivitySignupBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignupActivity extends AppCompatActivity {

    ActivitySignupBinding binding;
    Dialog progressbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding=binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = binding.etEmail.getText().toString();
                String username = binding.etUsername.getText().toString();
                String password = binding.etPassword.getText().toString();

                if(email.isEmpty() || username.isEmpty() || password.isEmpty()){
                    Toast.makeText(SignupActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    progressbar.show();
                    sendSignupDetails(email, username, password);
                }
            }
        });

        //dialog box
        progressbar = new Dialog(this);
        progressbar.setContentView(R.layout.progressbarlayout);
        progressbar.setCancelable(false);
    }

    public void sendSignupDetails(String email, String username, String password) {

        ApiInterface apiInterface = RetrofitClient.getInstance().getApi();
        Call<SignupModel> call = apiInterface.send_signup_data(email, username, password);

        call.enqueue(new Callback<SignupModel>() {
            @Override
            public void onResponse(Call<SignupModel> call, Response<SignupModel> response) {
                if (response.isSuccessful()) {
                    SignupModel model = response.body();
                    String message = model.getMessage();
                    progressbar.dismiss();
                    Toast.makeText(SignupActivity.this, message, Toast.LENGTH_SHORT).show();

                    if ("Success".equals(message)) {
                        Intent login = new Intent(SignupActivity.this, LoginActivity.class);
                        startActivity(login);
                        finish();
                    }

                } else {
                    Toast.makeText(SignupActivity.this, "API Error: " + response.message(), Toast.LENGTH_SHORT).show();
                }
                binding.etEmail.setText("");
                binding.etUsername.setText("");
                binding.etPassword.setText("");
            }

            @Override
            public void onFailure(Call<SignupModel> call, Throwable t) {
                progressbar.dismiss();
                Toast.makeText(SignupActivity.this, "Network Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();

                binding.etEmail.setText("");
                binding.etUsername.setText("");
                binding.etPassword.setText("");
            }
        });
    }
}