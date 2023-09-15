package com.example.shoppershub.Ui.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.example.shoppershub.Constants;
import com.example.shoppershub.R;
import com.example.shoppershub.Ui.Fragments.CartFragment;
import com.example.shoppershub.Ui.Fragments.HomeFragment;
import com.example.shoppershub.Ui.Fragments.KidsCatgFragment;
import com.example.shoppershub.Ui.Fragments.MenCatgFragment;
import com.example.shoppershub.Ui.Fragments.ProfileFragment;
import com.example.shoppershub.Ui.Fragments.WomenCatgFragment;
import com.example.shoppershub.databinding.ActivityDashboardBinding;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;
import de.hdodenhof.circleimageview.CircleImageView;

public class DashboardActivity extends AppCompatActivity {

    ActivityDashboardBinding binding;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        binding = ActivityDashboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        View headerView = binding.navigationView.getHeaderView(0);

        TextView headerTextView  = headerView.findViewById(R.id.tv_nav_username);
        CircleImageView circleImageView = headerView.findViewById(R.id.nav_profile_image);

        sharedPreferences = getSharedPreferences("login", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("username","value?");
        headerTextView.setText(username.substring(0, 1).toUpperCase() + username.substring(1));

        Glide.with(this).load(Constants.BASE_URL_IP+"images/img_profile.jpg").into(circleImageView);

//////////////////////////////     NAVIGATION BAR     //////////////////////////////

        binding.included.toolbar.setTitle("");
        setSupportActionBar(binding.included.toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, binding.drawerLayout, binding.included.toolbar, R.string.OpenDrawer, R.string.CloseDrawer);

        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.grandis));

        binding.drawerLayout.addDrawerListener(toggle);

        toggle.syncState();

        binding.navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                if(id == R.id.nav_men){
                    Fragment menFragment = new MenCatgFragment();
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.fl_container, menFragment).addToBackStack(null).commit();
                }
                else if(id == R.id.nav_women){
                    Fragment womenFragment = new WomenCatgFragment();
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.fl_container, womenFragment).addToBackStack(null).commit();
                }
                else if(id == R.id.nav_kids){
                    Fragment kidFragment = new KidsCatgFragment();
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.fl_container, kidFragment).addToBackStack(null).commit();
                }
                else{
                    sharedPreferences = getSharedPreferences("login", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();

                    editor.putInt("user_id",0);
                    editor.putString("username","");
                    editor.apply();

                    Toast.makeText(DashboardActivity.this, "Logged out", Toast.LENGTH_SHORT).show();
                    Intent login = new Intent(DashboardActivity.this, LoginActivity.class);
                    startActivity(login);
                    finish();
                }

                binding.drawerLayout.closeDrawer(GravityCompat.START);

                return true;
            }
        });

////////////////////////////////     BOTTOM NAVIGATION     //////////////////////////////

        binding.included.btmNav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int id = item.getItemId();

                if(id == R.id.btm_nav_home){
                    Fragment fragment = new HomeFragment();
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.fl_container, fragment).commit();
                }
                else{
                    Fragment fragment = new ProfileFragment();
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.fl_container, fragment).addToBackStack(null).commit();
                }
                return true;
            }
        });
        binding.included.btmNav.setSelectedItemId(R.id.btm_nav_home);
    }

    @Override
    public void onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)){
            binding.drawerLayout.closeDrawer(GravityCompat.START);
        }
        else if (getSupportFragmentManager().getBackStackEntryCount() > 0 ){
            getSupportFragmentManager().popBackStackImmediate();
        }
        else if (binding.included.btmNav.findViewById(R.id.btm_nav_profile).isSelected()){
            binding.included.btmNav.setSelectedItemId(R.id.btm_nav_home);
        }
        else{
            super.onBackPressed();
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_item, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.search) {
            if(binding.included.llSearchbar.getVisibility()== View.GONE)
            {
                binding.included.llSearchbar.setVisibility(View.VISIBLE);
            }
            else{
                binding.included.llSearchbar.setVisibility(View.GONE);
            }
        }
        else{
            Fragment cartFragment = new CartFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.fl_container, cartFragment).addToBackStack(null).commit();
        }
        return super.onOptionsItemSelected(item);
    }
}