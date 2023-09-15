package com.example.shoppershub.Ui.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.shoppershub.Adapters.CartAdapter;
import com.example.shoppershub.Model.CartModel;
import com.example.shoppershub.R;
import com.example.shoppershub.Retrofit.ApiInterface;
import com.example.shoppershub.Retrofit.RetrofitClient;
import com.example.shoppershub.Ui.Activities.CheckoutActivity;
import com.example.shoppershub.databinding.FragmentCartBinding;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartFragment extends Fragment implements CartAdapter.OnItemDeleteListener{

    FragmentCartBinding binding;
    double totalPrice = 0;
    List<CartModel> cartItems;

    public CartFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentCartBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        SharedPreferences sp = getActivity().getSharedPreferences("login", Context.MODE_PRIVATE);
        int user_id = sp.getInt("user_id",0);

        binding.rvCart.setLayoutManager(new LinearLayoutManager(getContext()));

        binding.tvCartTotalprice.setText("₹ " + totalPrice);

        cartDetails(user_id);

//////////////////////////////     CHECKOUT     //////////////////////////////

        binding.btnCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CheckoutActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }

    public void cartDetails(int user_id)
    {
        ApiInterface apiInterface = RetrofitClient.getInstance().getApi();
        Call<List<CartModel>> call = apiInterface.cart_details(user_id);

        call.enqueue(new Callback<List<CartModel>>() {
            @Override
            public void onResponse(Call<List<CartModel>> call, Response<List<CartModel>> response) {
                if (response.isSuccessful()) {
                    cartItems = response.body();

                    if (cartItems != null && !cartItems.isEmpty()) {

                        binding.llEmptycart.setVisibility(View.GONE);

                        CartAdapter adapter = new CartAdapter(getContext(),cartItems, CartFragment.this::onItemDeleted);

                        binding.rvCart.setAdapter(adapter);

                        calculatePrice();

                        adapter.notifyDataSetChanged();

                    } else {
                        binding.llEmptycart.setVisibility(View.VISIBLE);
                    }
                } else {
                    Toast.makeText(getContext(), "API Error: " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<CartModel>> call, Throwable t) {
                Toast.makeText(getContext(), "Network Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onItemDeleted(CartModel deletedItem) {
        totalPrice -= deletedItem.getPrice();
        binding.tvCartTotalprice.setText("₹ " + totalPrice);
    }

    public void calculatePrice(){
        totalPrice = 0;
        for (CartModel item : cartItems) {
            totalPrice += item.getPrice();
        }
        binding.tvCartTotalprice.setText("₹ " + totalPrice);
    }
}