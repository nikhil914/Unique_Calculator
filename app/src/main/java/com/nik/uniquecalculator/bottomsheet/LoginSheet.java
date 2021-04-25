package com.nik.uniquecalculator.bottomsheet;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.nik.uniquecalculator.MainActivity;
import com.nik.uniquecalculator.R;
import com.nik.uniquecalculator.databinding.LoginBottomsheetBinding;
import com.nik.uniquecalculator.viewmodel.LoginViewModel;
import com.nik.uniquecalculator.viewmodel.MainActivityViewModel;

import java.util.concurrent.Executor;

public class LoginSheet extends BottomSheetDialogFragment {
    LoginBottomsheetBinding mBinding;
    MainActivityViewModel mainActivityViewModel;
    LoginViewModel mViewmodel;

    public LoginSheet() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.login_bottomsheet, container, false);
        mViewmodel = new ViewModelProvider(this).get(LoginViewModel.class);
        mainActivityViewModel = new ViewModelProvider(getActivity()).get(MainActivityViewModel.class);
        mBinding.setModel(mViewmodel);
        mBinding.setClickHandler(new ClickHandler());
        mBinding.setLifecycleOwner(this);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        observeLiveData();
    }

    private void observeLiveData() {
        mViewmodel.getUserMutableLiveData().observe(getViewLifecycleOwner(), new Observer<FirebaseUser>() {
            @Override
            public void onChanged(FirebaseUser firebaseUser) {
                if (firebaseUser != null) {
                    mainActivityViewModel.getUserMutableLiveData().setValue(firebaseUser);
                    getDialog().dismiss();
                }
            }
        });

    }

    public class ClickHandler {
        public void login(View view) {


            try {
                mViewmodel.getmAuth().signInWithEmailAndPassword(mViewmodel.getIdLIveData().getValue(), mViewmodel.getPasswordLiveData().getValue())
                        .addOnCompleteListener((getActivity()) , new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d("TAG", "signInWithEmail:success");
                                    FirebaseUser user = mViewmodel.getmAuth().getCurrentUser();
                                    mainActivityViewModel.getUserMutableLiveData().setValue(user);
                                    getDialog().dismiss();

                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w("TAG", "signInWithEmail:failure", task.getException());
                                    Toast.makeText(getContext(), "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            } catch (Exception e) {
                e.printStackTrace();
            }


        }
    }
}
