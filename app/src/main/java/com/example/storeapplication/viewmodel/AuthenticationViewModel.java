package com.example.storeapplication.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.storeapplication.repository.AuthenticationRepository;
import com.google.firebase.auth.FirebaseUser;

import java.lang.invoke.MutableCallSite;

public class AuthenticationViewModel extends AndroidViewModel {

    private AuthenticationRepository authenticationRepository;
    private MutableLiveData<FirebaseUser> userMutableLiveData;

    public MutableLiveData<FirebaseUser> getUserMutableLiveData(){
        return userMutableLiveData;
    }

    public AuthenticationViewModel(@NonNull Application application) {
        super(application);
        authenticationRepository = new AuthenticationRepository(application);
        userMutableLiveData = authenticationRepository.getFirebaseUserMutableLiveData();
    }

    public void register(String name,String email, String password){
        authenticationRepository.register(name,email,password);
    }
    public void login(String email, String password){
        authenticationRepository.login(email,password);
    }
}
