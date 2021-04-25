package com.nik.uniquecalculator.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseUser;

public class MainActivityViewModel extends ViewModel {

    private MutableLiveData<String> inputLiveData;
    private MutableLiveData<String> resultLiveData;
    MutableLiveData<FirebaseUser> userMutableLiveData;
    MutableLiveData<Boolean> isLoggedIn;


    public MainActivityViewModel() {
        init();

    }


    public void init() {
        inputLiveData = new MutableLiveData<>("");
        resultLiveData = new MutableLiveData<>("");
        userMutableLiveData = new MutableLiveData<>();
        isLoggedIn = new MutableLiveData<>(false);
    }


    //Getters and Setters


    public MutableLiveData<String> getInputLiveData() {
        return inputLiveData;
    }

    public void setInputLiveData(MutableLiveData<String> inputLiveData) {
        this.inputLiveData = inputLiveData;
    }

    public MutableLiveData<String> getResultLiveData() {
        return resultLiveData;
    }

    public void setResultLiveData(MutableLiveData<String> resultLiveData) {
        this.resultLiveData = resultLiveData;
    }

    public MutableLiveData<FirebaseUser> getUserMutableLiveData() {
        return userMutableLiveData;
    }

    public void setUserMutableLiveData(MutableLiveData<FirebaseUser> userMutableLiveData) {
        this.userMutableLiveData = userMutableLiveData;
    }

    public MutableLiveData<Boolean> getIsLoggedIn() {
        return isLoggedIn;
    }

    public void setIsLoggedIn(MutableLiveData<Boolean> isLoggedIn) {
        this.isLoggedIn = isLoggedIn;
    }
}
