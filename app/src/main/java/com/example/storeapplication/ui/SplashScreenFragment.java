package com.example.storeapplication.ui;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.bumptech.glide.Glide;
import com.example.storeapplication.R;
import com.example.storeapplication.databinding.FragmentSplashScreenBinding;
import com.google.firebase.auth.FirebaseAuth;

public class SplashScreenFragment extends Fragment {
    FragmentSplashScreenBinding splashScreenBinding;
    private Animation animation;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        splashScreenBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.fragment_splash_screen, container, false);
        defineAnimation();
        setLogo();
        loadAnimation();
        showSplash(splashScreenBinding.getRoot());

        return splashScreenBinding.getRoot();
    }

    private void showSplash(View view) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences sharedPreferences = requireActivity().getSharedPreferences(LoginFragment.PREFS_NAME,0);
                boolean hasLoggedIn = sharedPreferences.getBoolean("hasLoggedIn",false);
                if (hasLoggedIn){
                    Navigation.findNavController(view).navigate(R.id.action_splashScreenFragment_to_homeFragment);
                }else {
                    Navigation.findNavController(view).navigate(R.id.action_splashScreenFragment_to_loginFragment);
                }
            }
        },5000);
    }

    private void setLogo() {
        Glide.with(this).load(R.drawable.digikala_image).into(splashScreenBinding.ivWelcomeSplashScreen);
    }

    private void defineAnimation() {
        animation = AnimationUtils.loadAnimation(requireContext(), R.anim.fade_in);
    }

    private void loadAnimation() {
        splashScreenBinding.ivWelcomeSplashScreen.startAnimation(animation);
        splashScreenBinding.tvWelcome.startAnimation(animation);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //disable navigation view
        DrawerLayout drawerLayout = requireActivity().findViewById(R.id.drawerLayout);
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
    }
}