package com.example.storeapplication.ui;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.storeapplication.R;
import com.example.storeapplication.databinding.FragmentLoginBinding;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class LoginFragment extends Fragment {
    FragmentLoginBinding fragmentLoginBinding;
    private String email;
    private String password;
    private boolean doubleToBackButtonExit = false;

    private String emailPattern = "[a-zA-Z0-9.-_]+@[a-z]+\\.+[a-z]+";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentLoginBinding = DataBindingUtil.inflate(getLayoutInflater(),R.layout.fragment_login,container,false);

        fragmentLoginBinding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendRequest();
            }
        });

        fragmentLoginBinding.tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_registerFragment);
            }
        });
        return fragmentLoginBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //finish fragment when we click back button
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if (doubleToBackButtonExit){
                    requireActivity().finish();
                    return;
                }
                //handle double click for exit
                doubleToBackButtonExit = true;
                Toast.makeText(requireContext(), "برای خروج دوباره کلیک کنید", Toast.LENGTH_SHORT).show();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        doubleToBackButtonExit = false;
                    }
                },2000);
            }
        });

    }

    private void sendRequest(){
        getEmail();
        if (checkEmail()){
            // true and check get password
            getPassword();
            if (checkPassword()){
                // true and we can navigate to home fragment
            }
        }
    }

    private boolean checkPassword(){
        if (password.isEmpty()){
            Toast.makeText(requireContext(), "پسورد خود را وارد کنید", Toast.LENGTH_SHORT).show();
            return false;
        }else {
            if (password.length() >= 6){
                return true;
            }else {
                Toast.makeText(requireContext(), "پسورد وارد شده بیشتر از شش رقم باشد", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
    }

    private boolean checkEmail(){
       if(email.isEmpty()){
           Toast.makeText(requireContext(), "ایمیل خود را وارد کنید", Toast.LENGTH_SHORT).show();
           return false;
        }else {
           if (validateEmailPattern(email)){
               return true;
           }else {
               Toast.makeText(requireContext(), "ایمیل وارد شده نا معتبر است", Toast.LENGTH_SHORT).show();
               return false;
           }
       }
    }
    private void getEmail(){
        email = fragmentLoginBinding.etRegisterEmail.getText().toString();
    }

    private void getPassword(){
        password = fragmentLoginBinding.etRegisterPassword.getText().toString();
    }
    //check regex
    private boolean validateEmailPattern(String eml){
        Pattern pattern = Pattern.compile(emailPattern);
        Matcher matcher = pattern.matcher(eml);
        return matcher.matches();
    }
}