package com.ysl.materialjetpack.paging.fromdb;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.ysl.materialjetpack.R;

public class MyPagedAdapter extends PagedListAdapter<StudentAS, MyPagedAdapter.MyViewHolder> {
    public MyPagedAdapter() {
        super(new DiffUtil.ItemCallback<StudentAS>() {
            @Override
            public boolean areItemsTheSame(@NonNull StudentAS oldItem, @NonNull StudentAS newItem) {
                return oldItem.getId() == newItem.getId();
            }

            @Override
            public boolean areContentsTheSame(@NonNull StudentAS oldItem, @NonNull StudentAS newItem) {
                return oldItem.getStudentNumber() == newItem.getStudentNumber();
            }
        });
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.paging_item_db, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        StudentAS studentAS = getItem(position);
        if (studentAS == null) {
            holder.textView.setText("loading");
        } else {
            holder.textView.setText(String.valueOf(studentAS.getStudentNumber()));

        }
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textView);
        }
    }
}
