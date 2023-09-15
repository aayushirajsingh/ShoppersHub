package com.example.shoppershub.Ui.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.example.shoppershub.Constants;
import com.example.shoppershub.Model.CartModel;
import com.example.shoppershub.R;
import com.example.shoppershub.Retrofit.ApiInterface;
import com.example.shoppershub.Retrofit.RetrofitClient;
import com.example.shoppershub.databinding.FragmentItemBinding;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ItemFragment extends Fragment {

    FragmentItemBinding binding;
    CircleImageView selectedColour;
    LinearLayout selectedSize;

    private int product_id, price;
    private String name, image, description, size, colour;

    public ItemFragment() {
        // Required empty public constructor
    }

    public static ItemFragment newInstance(int product_id, String name, String image, String description, String size, String colour, int price) {
        ItemFragment fragment = new ItemFragment();
        Bundle args = new Bundle();
        args.putInt("product_id", product_id);
        args.putString("name", name);
        args.putString("image", image);
        args.putString("description", description);
        args.putString("size", size);
        args.putString("colour", colour);
        args.putInt("price", price);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            product_id = getArguments().getInt("product_id");
            name = getArguments().getString("name");
            image = getArguments().getString("image");
            description = getArguments().getString("description");
            size = getArguments().getString("size");
            colour = getArguments().getString("colour");
            price = getArguments().getInt("price");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding =  FragmentItemBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        SharedPreferences sp = getActivity().getSharedPreferences("login", Context.MODE_PRIVATE);
        int user_id = sp.getInt("user_id",0);

        // Hiding action bar
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        if (activity != null) {
            activity.getSupportActionBar().hide();
        }

        Glide.with(this).load(Constants.BASE_URL_IP+"images/"+image).centerCrop().into(binding.imgProduct);

        binding.tvTitle.setText(name);
        binding.tvPrice.setText("₹"+price);
        binding.tvDescription.setText(description);
        int productColour = Color.parseColor(colour);
        binding.crImg1.setColorFilter(productColour);
        binding.tvSizeS.setText(size);

/////////////////// Colour selection /////////////////////////

        selectedColour = binding.crImg1;
        highlightColor(selectedColour);

        binding.crImg1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleColorSelection(binding.crImg1);
            }
        });

        binding.crImg2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleColorSelection(binding.crImg2);
            }
        });

        binding.crImg3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleColorSelection(binding.crImg3);
            }
        });

        binding.crImg4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleColorSelection(binding.crImg4);
            }
        });

/////////////////// Size selection /////////////////////////

        selectedSize = binding.llSizeS;
        highlightSize(selectedSize);

        binding.tvSizeS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleSizeSelection(binding.llSizeS);
            }
        });

        binding.tvSizeM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleSizeSelection(binding.llSizeM);
            }
        });

        binding.tvSizeL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleSizeSelection(binding.llSizeL);
            }
        });

        binding.tvSizeXl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleSizeSelection(binding.llSizeXl);
            }
        });

/////////////////// Add to Cart button /////////////////////////

        binding.btnAddtocart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = 1;
                String colour = getColorFromImageView(selectedColour);
                String size = getSizeFromLinearLayout(selectedSize);

                sendCartDetails(user_id, product_id,count, colour, size);
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

//////////////////////////////////// COLOUR FUNCTIONS ///////////////////////////////////////////

    private void handleColorSelection(CircleImageView selectedView) {

        // Unhighlight the previously selected color
        if (selectedColour != null) {
            unhighlightColor(selectedColour);
        }

        // Highlight the selected color
        selectedColour = selectedView;
        highlightColor(selectedColour);

    }

    private void highlightColor(CircleImageView imageView) {

        imageView.setBackgroundResource(R.drawable.round_highlight);
    }

    private void unhighlightColor(CircleImageView imageView) {

        imageView.setBackgroundResource(0);
    }

    private String getColorFromImageView(CircleImageView imageView) {
        if (imageView == binding.crImg1) {
            return colour;
        } else if (imageView == binding.crImg2) {
            return "#FCCA6F";
        } else if (imageView == binding.crImg3) {
            return "#FD5D5B";
        } else if (imageView == binding.crImg4) {
            return "#44000A";
        }
        return "";
    }

//////////////////////////////////// SIZE FUNCTIONS ///////////////////////////////////////////

    private void handleSizeSelection(LinearLayout selectedView) {

        // Unhighlight the previously selected color
        if (selectedSize != null) {
            unhighlightSize(selectedSize);
        }

        // Highlight the selected color
        selectedSize = selectedView;
        highlightSize(selectedSize);
    }

    private void highlightSize(LinearLayout linearLayout) {

        linearLayout.setBackgroundResource(R.drawable.round_highlight);
    }

    private void unhighlightSize(LinearLayout linearLayout) {

        linearLayout.setBackgroundResource(0);
    }

    private String getSizeFromLinearLayout(LinearLayout linearLayout) {
        if (linearLayout == binding.llSizeS) {
            return "S";
        } else if (linearLayout == binding.llSizeM) {
            return "M";
        } else if (linearLayout == binding.llSizeL) {
            return "L";
        } else if (linearLayout == binding.llSizeXl) {
            return "XL";
        }
        return "";
    }

//////////////////////////////////// SEND CART DETAILS ///////////////////////////////////////////

    public void sendCartDetails(int user_id, int product_id, int count, String colour, String size) {

        ApiInterface apiInterface = RetrofitClient.getInstance().getApi();
        Call<CartModel> call = apiInterface.cart_send_details(user_id, product_id, count, colour, size);

        call.enqueue(new Callback<CartModel>() {
            @Override
            public void onResponse(Call<CartModel> call, Response<CartModel> response) {
                if(response.isSuccessful()){
                    CartModel model = response.body();
                    String message = model.getMessage();
                    Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();

                    if ("Success".equals(message)) {
                        Fragment fragment = new CartFragment();
                        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.fl_container, fragment).addToBackStack(null).commit();
                    }else {
                        Toast.makeText(getContext(), "API Error: " + response.message(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<CartModel> call, Throwable t) {
                Toast.makeText(getContext(), "Network Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}