package com.example.storeapplication.ui;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
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
                Navigation.findNavController(view).navigate(R.id.action_splashScreenFragment_to_loginFragment);

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


}