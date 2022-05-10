package com.example.storeapplication.ui;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.storeapplication.R;
import com.example.storeapplication.databinding.FragmentProfileBinding;


public class ProfileFragment extends Fragment {
FragmentProfileBinding fragmentProfileBinding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentProfileBinding = DataBindingUtil.inflate(getLayoutInflater(),R.layout.fragment_profile,container,false);
        return fragmentProfileBinding.getRoot();
    }
}