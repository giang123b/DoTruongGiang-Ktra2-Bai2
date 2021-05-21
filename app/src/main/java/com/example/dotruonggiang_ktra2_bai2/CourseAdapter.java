package com.example.dotruonggiang_ktra2_bai2;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.RatingBar;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CourseAdapter extends
        RecyclerView.Adapter<CourseViewHolder> implements Filterable {

    private ArrayList<Course> mDisplayedValues = new ArrayList<>();

    ArrayList<Course> objects;
//    List<Course> mDisplayedValues; // origin

    Context mContext;

    DBHelper databaseHelper;

    public CourseAdapter(ArrayList<Course> Courses) {
        mDisplayedValues = Courses;
        objects = Courses;
    }

    @NonNull
    @Override
    public CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View contactView = inflater.inflate(R.layout.item_course, parent, false);
        CourseViewHolder viewHolder = new CourseViewHolder(contactView);
        mContext = parent.getContext();
        databaseHelper = new DBHelper((context));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CourseViewHolder holder, int position) {
        Course Course = mDisplayedValues.get(position);

        holder.textViewName.setText(Course.getName());
        holder.textViewDate.setText(Course.getDate());
        holder.textViewMajor.setText(Course.getMajor());
        holder.textViewActive.setText(Course.getActive());
        holder.buttonXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDisplayedValues.remove(position);
                databaseHelper.deleteCourse(Integer.parseInt(Course.getId()));
                notifyDataSetChanged();
            }
        });


        holder.buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


    }


    @Override
    public int getItemCount() {
        return mDisplayedValues.size();
    }

    void submitList(ArrayList<Course> Courses) {
        mDisplayedValues.clear();
        mDisplayedValues.addAll(Courses);
        notifyDataSetChanged();
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                FilterResults filterResults = new FilterResults();
                if (constraint == null || constraint.length() == 0) {
                    filterResults.count = objects.size();
                    filterResults.values = objects;

                } else {
                    List<Course> resultsModel = new ArrayList<>();
                    String searchStr = constraint.toString().toLowerCase();

                    for (Course itemsModel : objects) {
                        if (itemsModel.getName().contains(searchStr)) {
                            resultsModel.add(itemsModel);

                        }
                        filterResults.count = resultsModel.size();
                        filterResults.values = resultsModel;
                    }
                }

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                mDisplayedValues = (ArrayList<Course>) results.values;
                notifyDataSetChanged();
            }
        };
        return filter;
    }
}
