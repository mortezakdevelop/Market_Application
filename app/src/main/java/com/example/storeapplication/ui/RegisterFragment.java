package com.example.storeapplication.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.storeapplication.R;
import com.example.storeapplication.databinding.FragmentRegisterBinding;
import com.example.storeapplication.model.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class RegisterFragment extends Fragment {

    FragmentRegisterBinding fragmentRegisterBinding;
    private String name;
    private String email;
    private String password;
    private String emailPattern = "[a-zA-Z0-9.-_]+@[a-z]+\\.+[a-z]+";
    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentRegisterBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.fragment_register, container, false);
        // get instance firebase
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();


        fragmentRegisterBinding.btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendRequest();
                createUser();
            }

        });
        fragmentRegisterBinding.tvSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_registerFragment_to_loginFragment);
            }
        });

        return fragmentRegisterBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    private void createUser() {

    }

    private void sendRequest() {
        getName();
        getEmail();
        if (checkEmail()) {
            // true and check get password
            getPassword();
            if (checkPassword()) {
                //firebase auth
                firebaseAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // true and we can navigate to home fragment
                                    UserModel userModel = new UserModel(name, email, password);
                                    String id = Objects.requireNonNull(task.getResult().getUser()).getUid();
                                    firebaseDatabase.getReference().child("Users").child(id).setValue(userModel);


                                    Toast.makeText(requireContext(), "register is successful", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(requireContext(), "Error: " + task.getException(), Toast.LENGTH_SHORT).show();
                                }
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

    private void getName() {
        name = fragmentRegisterBinding.etRegisterName.getText().toString();
    }

    private void getEmail() {
        email = fragmentRegisterBinding.etRegisterEmail.getText().toString();
    }

    private void getPassword() {
        password = fragmentRegisterBinding.etRegisterPassword.getText().toString();
    }

    //check regex
    private boolean validateEmailPattern(String eml) {
        Pattern pattern = Pattern.compile(emailPattern);
        Matcher matcher = pattern.matcher(eml);
        return matcher.matches();
    }
}