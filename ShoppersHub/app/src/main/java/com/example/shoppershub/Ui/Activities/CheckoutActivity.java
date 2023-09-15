package com.example.shoppershub.Ui.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.shoppershub.databinding.ActivityCheckoutBinding;

public class CheckoutActivity extends AppCompatActivity {

    ActivityCheckoutBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding= ActivityCheckoutBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        View.OnClickListener cardClickListener = v -> {
            String selectedCartText = null;

            binding.cvMastercard.setSelected(v == binding.cvMastercard);
            binding.cvRupay.setSelected(v == binding.cvRupay);
            binding.cvVisa.setSelected(v == binding.cvVisa);
            binding.cvCod.setSelected(v == binding.cvCod);

            if (binding.cvMastercard.isSelected()) {
                selectedCartText = "MasterCard";
                Toast.makeText(this,selectedCartText, Toast.LENGTH_SHORT).show();

            } else if (binding.cvRupay.isSelected()) {
                selectedCartText = "RuPay";
                Toast.makeText(this,selectedCartText, Toast.LENGTH_SHORT).show();

            } else if (binding.cvVisa.isSelected()) {
                selectedCartText = "VISA";
                Toast.makeText(this,selectedCartText, Toast.LENGTH_SHORT).show();

            } else if (binding.cvCod.isSelected()) {
                selectedCartText = "Cash On Delivery";
                Toast.makeText(this,selectedCartText, Toast.LENGTH_SHORT).show();

            } else {
                selectedCartText = "No CardView is selected";
                Toast.makeText(this,selectedCartText, Toast.LENGTH_SHORT).show();
            }
        };

        binding.cvMastercard.setOnClickListener(cardClickListener);
        binding.cvRupay.setOnClickListener(cardClickListener);
        binding.cvVisa.setOnClickListener(cardClickListener);
        binding.cvCod.setOnClickListener(cardClickListener);

        binding.btnCheckoutconfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(CheckoutActivity.this, "Order Confirmed", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(CheckoutActivity.this, DashboardActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}