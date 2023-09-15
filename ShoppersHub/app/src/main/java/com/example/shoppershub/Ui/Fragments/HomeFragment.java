package com.example.shoppershub.Ui.Fragments;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.example.shoppershub.Adapters.BannerAdapter;
import com.example.shoppershub.Adapters.ItemsAdapter;
import com.example.shoppershub.Interface.ItemClick;
import com.example.shoppershub.Model.ProductsModel;
import com.example.shoppershub.R;
import com.example.shoppershub.Retrofit.ApiInterface;
import com.example.shoppershub.Retrofit.RetrofitClient;
import com.example.shoppershub.databinding.FragmentHomeBinding;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment implements ItemClick {

    FragmentHomeBinding binding;
    ArrayList<Integer> arrayList = new ArrayList<>();

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

//////////////////////////////     BANNER     //////////////////////////////

        arrayList.add(R.drawable.img_banner);
        arrayList.add(R.drawable.img_banner);

        BannerAdapter bannerAdapter = new BannerAdapter(getContext(), arrayList);
        binding.vpBanner.setAdapter(bannerAdapter);

//////////////////////////////     Categories     //////////////////////////////

        // Men
        binding.cvMen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new MenCatgFragment();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fl_container, fragment).addToBackStack(null).commit();
            }
        });

        // Women
        binding.cvWomen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new WomenCatgFragment();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fl_container, fragment).addToBackStack(null).commit();
            }
        });

        // Kids
        binding.cvKids.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new KidsCatgFragment();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fl_container, fragment).addToBackStack(null).commit();
            }
        });

//////////////////////////////     ITEMS LIST     //////////////////////////////

        binding.rvItems.setLayoutManager(new GridLayoutManager(getContext(),2));

        productsData();

        return view;
    }

//////////////////////////////     ITEMS ONCLICK     //////////////////////////////
    @Override
    public void onItemClick(ProductsModel productsModel) {
        Fragment fragment = ItemFragment.newInstance(productsModel.getProduct_id(), productsModel.getName(), productsModel.getImage(), productsModel.getDescription(), productsModel.getSize(), productsModel.getColour(),productsModel.getPrice());
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fl_container, fragment).addToBackStack(null).commit();
    }

    public void productsData()
    {
        ApiInterface apiInterface = RetrofitClient.getInstance().getApi();
        Call<List<ProductsModel>> call = apiInterface.product_details();

        call.enqueue(new Callback<List<ProductsModel>>() {
            @Override
            public void onResponse(Call<List<ProductsModel>> call, Response<List<ProductsModel>> response) {
                List<ProductsModel> data = response.body();
                ItemsAdapter adapter = new ItemsAdapter(HomeFragment.this::onItemClick,getContext(),data);
                binding.rvItems.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<ProductsModel>> call, Throwable t) {
                Toast.makeText(getContext(), "Network Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}