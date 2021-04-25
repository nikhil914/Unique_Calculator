package com.nik.uniquecalculator.bottomsheet;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.nik.uniquecalculator.R;
import com.nik.uniquecalculator.adapter.HistoryRecyclerAdapter;
import com.nik.uniquecalculator.databinding.LoginBottomsheetBinding;
import com.nik.uniquecalculator.databinding.ViewBottomsheetBinding;
import com.nik.uniquecalculator.model.CalModel;

import java.util.List;

public class HistoryBottomSheet extends BottomSheetDialogFragment {
    ViewBottomsheetBinding mBinding;
    List<CalModel> savedCalculation;
    HistoryRecyclerAdapter mAdaper;
    RecyclerView recyclerView;

    public HistoryBottomSheet(List<CalModel> savedCalculation) {
        this.savedCalculation = savedCalculation;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil .inflate(inflater,R.layout.view_bottomsheet, container, false);
        mBinding.setLifecycleOwner(this);
        recyclerView  = mBinding.recyclerview;
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mAdaper = new HistoryRecyclerAdapter(savedCalculation,getContext());
        recyclerView.setAdapter(mAdaper);
    }
}
