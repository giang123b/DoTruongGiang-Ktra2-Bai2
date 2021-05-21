package com.example.dotruonggiang_ktra2_bai2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerViewCourse;
    ArrayList<Course> mCourse = new ArrayList<>();
    Button buttonAdd;
    Button buttonStartTime;

    ImageView imageButton_timKiem;
    EditText editText_timKiem;


    CourseAdapter courseAdapter;

    DBHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseHelper = new DBHelper(getApplicationContext());

        getView();
        getData();
        setRecyclerView();

        imageButton_timKiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!editText_timKiem.getText().toString().trim().equals(""))
                    courseAdapter.getFilter().filter(editText_timKiem.getText().toString().trim());
                else {
                    Toast.makeText(MainActivity.this, "Not found", Toast.LENGTH_LONG).show();
                    getData();
                }
            }
        });

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(MainActivity.this);
                LayoutInflater inflater = getLayoutInflater();
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
                ArrayAdapter<String> adapter = new ArrayAdapter(MainActivity.this, android.R.layout.simple_spinner_dropdown_item, items);
                dropdown.setAdapter(adapter);


                AlertDialog alertDialog = dialogBuilder.create();
                alertDialog.show();

                buttonStartTime.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        datePicker();
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

                        mCourse.add(course);

                        databaseHelper.insertCourse(course);

                        courseAdapter.notifyDataSetChanged();

                        alertDialog.dismiss();
                    }
                });

            }
        });
    }

    String date_time = "";
    int mYear;
    int mMonth;
    int mDay;

    private void datePicker() {

        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                        date_time = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;

                        buttonStartTime.setText(date_time);
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    private void setRecyclerView() {
        courseAdapter = new CourseAdapter(mCourse);
        recyclerViewCourse.setAdapter(courseAdapter);
        recyclerViewCourse.setLayoutManager(new LinearLayoutManager(this));
    }

    private void getData() {
        mCourse.addAll(databaseHelper.getAllCourse());
        setRecyclerView();
    }

    private void getView() {
        recyclerViewCourse = findViewById(R.id.recyclerViewOrder);
        buttonAdd = findViewById(R.id.buttonAdd);
        editText_timKiem = findViewById(R.id.editText_timKiem);
        imageButton_timKiem = findViewById(R.id.imageButton_timKiem);
    }
}