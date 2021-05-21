package com.example.dotruonggiang_ktra2_bai2;

import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class CourseViewHolder extends RecyclerView.ViewHolder{
    public TextView textViewName;
    public TextView textViewID;
    public TextView textViewMajor;
    public TextView textViewDate;
    public CheckBox checkBoxActive;
    public Button buttonEdit;
    public Button buttonXoa;

    public CourseViewHolder(View itemView) {
        super(itemView);

        textViewID =  itemView.findViewById(R.id.textViewID);
        textViewName =  itemView.findViewById(R.id.textViewName);
        textViewDate =  itemView.findViewById(R.id.textViewDate);
        textViewMajor =  itemView.findViewById(R.id.textViewMajor);
        checkBoxActive =  itemView.findViewById(R.id.checkboxActive);
        buttonXoa =  itemView.findViewById(R.id.buttonXoa);
        buttonEdit =  itemView.findViewById(R.id.buttonEdit);

    }
}
