package com.nik.uniquecalculator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.nik.uniquecalculator.bottomsheet.HistoryBottomSheet;
import com.nik.uniquecalculator.bottomsheet.LoginSheet;
import com.nik.uniquecalculator.databinding.ActivityMainBinding;
import com.nik.uniquecalculator.model.CalModel;
import com.nik.uniquecalculator.viewmodel.MainActivityViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding mainBinding;
    MainActivityViewModel mViewmodel;
    List<CalModel> savedCalculation = new ArrayList<>();
    private FirebaseAuth mAuth;
    FirebaseFirestore db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mViewmodel = new ViewModelProvider(this).get(MainActivityViewModel.class);
        mainBinding.setModel(mViewmodel);
        mainBinding.setClickHandler(new ClickHandler());
        mainBinding.setLifecycleOwner(this);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        if (mAuth.getCurrentUser() != null) {
            mViewmodel.getIsLoggedIn().setValue(true);
            getDataFromServer();
        }


        mainBinding.inputId.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;

                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    handleMessage();
                    handled = true;
                }
                return handled;
            }
        });

    }

    private void getDataFromServer() {
        db.collection("calculation")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("TAG", document.getId() + " => " + document.getData());

                                String operation = String.valueOf(document.getData().get("operation"));
                                String result = String.valueOf(document.getData().get("result"));

                                savedCalculation.add(new CalModel(operation, result));
                            }
                        } else {
                            Log.w("TAG", "Error getting documents.", task.getException());
                        }
                    }
                });
    }

    private void handleMessage() {
        String input = mViewmodel.getInputLiveData().getValue();

        if (input.trim().isEmpty()){
            Snackbar.make(mainBinding.getRoot(),"Please Type Numbers",Snackbar.LENGTH_LONG).show();
            return;
        }

        input = handleString(input);


        String result = String.valueOf(handleData(input));
        mViewmodel.getResultLiveData().setValue(result);

        savedCalculation.add(new CalModel(input, result));

        storeOnCloud(new CalModel(input, result));
    }

    private void storeOnCloud(CalModel calModel) {
        db.collection("calculation").add(calModel).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Log.i("TAG", "onSuccess: saved");
            }
        });

    }

    public class ClickHandler {
        public void calculate(View view) {
            handleMessage();
        }

        public void viewHistory(View view) {
            HistoryBottomSheet historyBottomSheet = new HistoryBottomSheet(savedCalculation);
            historyBottomSheet.show(getSupportFragmentManager(), "tag");

        }

        public void login(View view) {

            FirebaseUser currentUser = mAuth.getCurrentUser();
            if (currentUser == null) {
                LoginSheet loginSheet = new LoginSheet();
                loginSheet.show(getSupportFragmentManager(), "tag");

            } else {
                Toast.makeText(MainActivity.this, "Already Logged In", Toast.LENGTH_SHORT).show();
            }


        }

    }

    private String handleString(String input) {

        StringBuilder sb = new StringBuilder();

        char[] ar = input.toCharArray();
        for (int i = 0; i < ar.length; i++) {
            char a = ar[i];
            if (ar[i] == '+' || ar[i] == '-' ||
                    ar[i] == '*' || ar[i] == '/') {

                sb.append(" ");
                sb.append(ar[i]);
                sb.append(" ");

            } else {
                sb.append(ar[i]);
            }

        }
        return sb.toString();
    }

    public int handleData(String input) {

        char[] ar = input.toCharArray();

        Stack<Integer> noStack = new Stack<Integer>();
        Stack<Character> oprStack = new Stack<Character>();


        for (int i = 0; i < ar.length; i++) {
            if (ar[i] == ' ')
                continue;

            if (ar[i] >= '0' && ar[i] <= '9') {
                StringBuffer buff_s = new StringBuffer();

                while (i < ar.length && ar[i] >= '0' && ar[i] <= '9')
                    buff_s.append(ar[i++]);
                noStack.push(Integer.parseInt(buff_s.toString()));
            } else if (ar[i] == '(')
                oprStack.push(ar[i]);

            else if (ar[i] == ')') {
                while (oprStack.peek() != '(')
                    noStack.push(calculateData(oprStack.pop(), noStack.pop(), noStack.pop()));
                oprStack.pop();
            } else if (ar[i] == '+' || ar[i] == '-' ||
                    ar[i] == '*' || ar[i] == '/') {
                while (!oprStack.empty() && whoIsSuperior(ar[i], oprStack.peek()))
                    noStack.push(calculateData(oprStack.pop(), noStack.pop(), noStack.pop()));

                oprStack.push(ar[i]);
            }
        }

        while (!oprStack.empty())
            noStack.push(calculateData(oprStack.pop(), noStack.pop(), noStack.pop()));

        return noStack.pop();
    }


    public boolean whoIsSuperior(char op1, char op2) {
        if (op2 == '(' || op2 == ')')
            return false;
        return (op1 != '*' && op1 != '+') || (op2 != '/' && op2 != '-');

    }


    private int calculateData(char opr, int a2, int a1) {

        switch (opr) {
            case '+':
                return a1 + a2;
            case '-':
                return a1 - a2;
            case '*':
                return a1 * a2;
            case '/':
                if (a2 == 0)
                    throw new
                            ArithmeticException("division by zero.. immpossible!");
                return a1 / a2;
        }
        return 0;

    }


}