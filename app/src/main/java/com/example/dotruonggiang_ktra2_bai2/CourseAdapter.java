package com.example.dotruonggiang_ktra2_bai2;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.Spinner;

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


     Button buttonStartTime;
    String date_time = "";
    int mYear;
    int mMonth;
    int mDay;

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
        Course course = mDisplayedValues.get(position);

        holder.textViewName.setText(course.getName());
        holder.textViewID.setText(course.getId());
        holder.textViewDate.setText(course.getDate());
        holder.textViewMajor.setText(course.getMajor());
        if (course.getActive().equals("Active")) {
            holder.checkBoxActive.setChecked(true);
        }
        holder.buttonXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDisplayedValues.remove(position);
                databaseHelper.deleteCourse(Integer.parseInt(course.getId()));
                notifyDataSetChanged();
            }
        });

        holder.buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(mContext);
                LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View dialogView = inflater.inflate(R.layout.add_new_course, null);
                dialogBuilder.setView(dialogView);

                EditText editTextName = dialogView.findViewById(R.id.editTextName);
                buttonStartTime = dialogView.findViewById(R.id.buttonStartTime);
                Spinner spinnerMajor = dialogView.findViewById(R.id.spinnerMajor);
                CheckBox checkboxActive = dialogView.findViewById(R.id.checkboxActive);
                Button buttonSave = dialogView.findViewById(R.id.buttonSave);
                Button buttonCancel = dialogView.findViewById(R.id.buttonCancel);

                Spinner dropdown = dialogView.findViewById(R.id.spinnerMajor);
                String[] items = new String[]{"Tieng anh", "cntt", "kinh te", "truyen thong"};
                ArrayAdapter<String> adapter = new ArrayAdapter(mContext, android.R.layout.simple_spinner_dropdown_item, items);
                dropdown.setAdapter(adapter);

                editTextName.setText(course.getName());
                buttonStartTime.setText(course.getDate());
                if (course.getMajor().equals("Tieng anh")){
                    spinnerMajor.setSelection(0);
                }

                if (course.getMajor().equals("cntt")){
                    spinnerMajor.setSelection(1);
                }

                if (course.getMajor().equals("kinh te")){
                    spinnerMajor.setSelection(2);
                }

                if (course.getMajor().equals("truyen thong")){
                    spinnerMajor.setSelection(3);
                }

                if (course.getActive() == "Active"){
                    checkboxActive.setChecked(true);
                }


                AlertDialog alertDialog = dialogBuilder.create();
                alertDialog.show();


                buttonStartTime.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        final Calendar c = Calendar.getInstance();
                        mYear = c.get(Calendar.YEAR);
                        mMonth = c.get(Calendar.MONTH);
                        mDay = c.get(Calendar.DAY_OF_MONTH);

                        DatePickerDialog datePickerDialog = new DatePickerDialog(mContext,
                                new DatePickerDialog.OnDateSetListener() {

                                    @Override
                                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                                        date_time = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;

                                        buttonStartTime.setText(date_time);
                                    }
                                }, mYear, mMonth, mDay);
                        datePickerDialog.show();
                    }
                });
                buttonCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.dismiss();
                    }
                });

                buttonSave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        String active = "Not active";
                        if (checkboxActive.isChecked()) {
                            active = "Active";
                        }

                        Course course = new Course("1", editTextName.getText().toString(),
                                buttonStartTime.getText().toString(),
                                spinnerMajor.getSelectedItem().toString(), active);


                        databaseHelper.insertCourse(course);

                        notifyDataSetChanged();

                        alertDialog.dismiss();
                    }
                });
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
