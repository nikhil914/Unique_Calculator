package com.nik.uniquecalculator.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.nik.uniquecalculator.R;
import com.nik.uniquecalculator.databinding.HistoryItemBinding;
import com.nik.uniquecalculator.model.CalModel;

import java.util.List;

public class HistoryRecyclerAdapter extends RecyclerView.Adapter<HistoryRecyclerAdapter.ViewHolder> {
    List<CalModel> mList;
    Context context;

    public HistoryRecyclerAdapter(List<CalModel> mList, Context context) {
        this.mList = mList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.history_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.itemBinding.setModel(mList.get(position));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        HistoryItemBinding itemBinding;
        public ViewHolder(@NonNull HistoryItemBinding itemView) {
            super(itemView.getRoot());
            this.itemBinding =itemView;
        }
    }
}
