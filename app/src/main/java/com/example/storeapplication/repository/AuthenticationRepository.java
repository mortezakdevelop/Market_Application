package com.example.storeapplication.repository;

import android.app.Application;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.navigation.Navigation;

import com.example.storeapplication.model.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class AuthenticationRepository {

    private Application application;
    private MutableLiveData<FirebaseUser> firebaseUserMutableLiveData;
    private FirebaseAuth auth;
    private FirebaseDatabase firebaseDatabase;


    public MutableLiveData<FirebaseUser> getFirebaseUserMutableLiveData(){
        return firebaseUserMutableLiveData;
    }

    public AuthenticationRepository(Application application) {
        this.application = application;
        firebaseUserMutableLiveData = new MutableLiveData<>();
        auth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();

        if (auth.getCurrentUser() != null) {
            firebaseUserMutableLiveData.postValue(auth.getCurrentUser());
        }
    }

    public void register(String name,String email, String password) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    UserModel userModel = new UserModel(name, email,password);
                    String id = Objects.requireNonNull(task.getResult().getUser()).getUid();
                    firebaseDatabase.getReference().child("Users").child(id).setValue(userModel);
                    Toast.makeText(application, "ثبت نام با وفقیت انجام شد", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(application,"خطا در اتصال", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void login(String email, String password){
        auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    firebaseUserMutableLiveData.postValue(auth.getCurrentUser());
                }else {
                    Toast.makeText(application, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
