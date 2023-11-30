package com.project.examsproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;

import java.util.ArrayList;

public class ReportRegistered extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_registered);

        TextView textViewStudentName = findViewById(R.id.textViewStudentName);
        TextView textViewRegNumber = findViewById(R.id.textViewRegNumber);
        TextView textViewSelectedCourses = findViewById(R.id.textViewSelectedCourses);

        // Retrieve the student information from the intent
        Intent intent = getIntent();
        String studentName = intent.getStringExtra("studentName");
        String regNumber = intent.getStringExtra("regNumber");
        ArrayList<String> selectedCourses = intent.getStringArrayListExtra("selectedCourses");

        // Set the retrieved values in TextViews
        textViewStudentName.setText("Student Name: " + studentName);
        textViewRegNumber.setText("Registration Number: " + regNumber);
        textViewSelectedCourses.setText("Selected Courses: " + TextUtils.join(", ", selectedCourses));
    }
}
