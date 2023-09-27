package com.example.shoppershub.Ui.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.window.OnBackInvokedCallback;

import com.bumptech.glide.Glide;
import com.example.shoppershub.Constants;
import com.example.shoppershub.R;
import com.example.shoppershub.Ui.Activities.DashboardActivity;
import com.example.shoppershub.Ui.Activities.LoginActivity;
import com.example.shoppershub.databinding.FragmentProfileBinding;

public class ProfileFragment extends Fragment {

    FragmentProfileBinding binding;
    SharedPreferences sharedPreferences;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        sharedPreferences = getActivity().getSharedPreferences("login", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("username","value?");
        binding.tvUsername.setText(username.substring(0, 1).toUpperCase() + username.substring(1));

        Glide.with(this).load(Constants.BASE_URL_IP+"images/img_profile.jpg").into(binding.profileImage);

        /*AppCompatActivity activity = (AppCompatActivity) getActivity();
        if (activity != null) {
            activity.getSupportActionBar().hide();
        }*/

        binding.llCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new CartFragment();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fl_container, fragment).addToBackStack(null).commit();
            }
        });

        binding.llLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPreferences = getActivity().getSharedPreferences("login", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();

                editor.putInt("user_id",0);
                editor.putString("username","");
                editor.apply();

                Intent login = new Intent(getActivity(), LoginActivity.class);
                login.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(login);
                Toast.makeText(getActivity(), "Logged out", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // Show the toolbar when the fragment is removed
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        if (activity != null) {
            activity.getSupportActionBar().show();
        }
    }
}