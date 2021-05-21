package com.example.dotruonggiang_ktra2_bai2;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class CourseViewHolder extends RecyclerView.ViewHolder{
    public TextView textViewName;
    public TextView textViewMajor;
    public TextView textViewDate;
    public TextView textViewActive;
    public Button buttonEdit;
    public Button buttonXoa;

    public CourseViewHolder(View itemView) {
        super(itemView);

        textViewName =  itemView.findViewById(R.id.textViewName);
        textViewDate =  itemView.findViewById(R.id.textViewDate);
        textViewMajor =  itemView.findViewById(R.id.textViewMajor);
        textViewActive =  itemView.findViewById(R.id.textViewActive);
        buttonXoa =  itemView.findViewById(R.id.buttonXoa);
        buttonEdit =  itemView.findViewById(R.id.buttonEdit);

    }
}
