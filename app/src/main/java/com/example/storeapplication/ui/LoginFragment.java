package com.example.storeapplication.ui;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.storeapplication.R;
import com.example.storeapplication.databinding.FragmentLoginBinding;
import com.example.storeapplication.viewmodel.AuthenticationViewModel;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginFragment extends Fragment {
    FragmentLoginBinding fragmentLoginBinding;
    private String email;
    private String password;
    FirebaseAuth firebaseAuth;
    private boolean doubleToBackButtonExit = false;
    public static String PREFS_NAME = "MyPrefs";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentLoginBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.fragment_login, container, false);
        firebaseAuth = FirebaseAuth.getInstance();
        fragmentLoginBinding.btnLogin.setOnClickListener(view -> {

            //prevent the login page to appear after successful login
            SharedPreferences sharedPreferences = requireActivity().getSharedPreferences(PREFS_NAME, 0);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("hasLoggedIn", true);
            editor.apply();
            sendRequest();

        });

        fragmentLoginBinding.tvSignUp.setOnClickListener(view -> Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_registerFragment));
        return fragmentLoginBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //finish fragment when we click back button
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if (doubleToBackButtonExit) {
                    requireActivity().finish();
                    return;
                }
                //handle double click for exit
                doubleToBackButtonExit = true;
                Toast.makeText(requireContext(), "برای خروج دوباره کلیک کنید", Toast.LENGTH_SHORT).show();
                new Handler().postDelayed(() -> doubleToBackButtonExit = false, 2000);
            }
        });

        //disable navigation view
        DrawerLayout drawerLayout = requireActivity().findViewById(R.id.drawerLayout);
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
    }

    private void sendRequest() {
        getEmail();
        if (checkEmail()) {
            // true and check get password
            getPassword();
            if (checkPassword()) {
                fragmentLoginBinding.progressbar.setVisibility(View.VISIBLE);
                firebaseAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                Navigation.findNavController(fragmentLoginBinding.getRoot()).navigate(R.id.action_loginFragment_to_homeFragment);
                            } else {
                                fragmentLoginBinding.progressbar.setVisibility(View.GONE);
                                Toast.makeText(requireContext(), "حساب کاربری ثبت نشده است", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        }
    }

    private boolean checkPassword() {
        if (password.isEmpty()) {
            Toast.makeText(requireContext(), "پسورد خود را وارد کنید", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            if (password.length() >= 6) {
                return true;
            } else {
                Toast.makeText(requireContext(), "پسورد وارد شده بیشتر از شش رقم باشد", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
    }

    private boolean checkEmail() {
        if (email.isEmpty()) {
            Toast.makeText(requireContext(), "ایمیل خود را وارد کنید", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            if (validateEmailPattern(email)) {
                return true;
            } else {
                Toast.makeText(requireContext(), "ایمیل وارد شده نا معتبر است", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
    }

    private void getEmail() {
        email = fragmentLoginBinding.etRegisterEmail.getText().toString();
    }

    private void getPassword() {
        password = fragmentLoginBinding.etRegisterPassword.getText().toString();
    }

    //check regex
    private boolean validateEmailPattern(String eml) {
        String emailPattern = getString(R.string.email_pattern);
        Pattern pattern = Pattern.compile(emailPattern);
        Matcher matcher = pattern.matcher(eml);
        return matcher.matches();
    }
}