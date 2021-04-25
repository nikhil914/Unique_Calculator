package com.nik.uniquecalculator.viewmodel;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.concurrent.Executor;

public class LoginViewModel extends ViewModel {
    private FirebaseAuth mAuth;
    MutableLiveData<String> idLIveData;
    MutableLiveData<String> passwordLiveData;
    MutableLiveData<FirebaseUser> userMutableLiveData;


    public LoginViewModel() {
        init();
    }

    public void init() {
        mAuth = FirebaseAuth.getInstance();

        idLIveData = new MutableLiveData<>("");
        passwordLiveData = new MutableLiveData<>("");
        userMutableLiveData = new MutableLiveData<>();

    }



    public void setmAuth(FirebaseAuth mAuth) {
        this.mAuth = mAuth;
    }

    public MutableLiveData<String> getIdLIveData() {
        return idLIveData;
    }

    public void setIdLIveData(MutableLiveData<String> idLIveData) {
        this.idLIveData = idLIveData;
    }

    public MutableLiveData<String> getPasswordLiveData() {
        return passwordLiveData;
    }

    public void setPasswordLiveData(MutableLiveData<String> passwordLiveData) {
        this.passwordLiveData = passwordLiveData;
    }

    public MutableLiveData<FirebaseUser> getUserMutableLiveData() {
        return userMutableLiveData;
    }

    public void setUserMutableLiveData(MutableLiveData<FirebaseUser> userMutableLiveData) {
        this.userMutableLiveData = userMutableLiveData;
    }

    public FirebaseAuth getmAuth() {
        return mAuth;
    }
}
