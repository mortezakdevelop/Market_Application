package com.example.storeapplication;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.storeapplication.databinding.ActivityMainBinding;
import com.google.android.material.navigation.NavigationView;


public class MainActivity extends AppCompatActivity {
    ActivityMainBinding activityMainBinding;
    AppBarConfiguration appBarConfiguration;
    NavHostFragment navHostFragment;
    NavController navController;
    public DrawerLayout drawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       activityMainBinding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        drawerLayout = activityMainBinding.drawerLayout;
       setupNavigationView();
    }

    private void setupNavigationView(){

        navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        navController = navHostFragment.getNavController();
        appBarConfiguration = new AppBarConfiguration.Builder(R.id.categoryFragment,R.id.homeFragment,R.id.profileFragment).build();
        NavigationUI.setupWithNavController(activityMainBinding.navigationView, navController);
    }
}