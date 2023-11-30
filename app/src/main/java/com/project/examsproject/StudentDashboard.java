package com.project.examsproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class StudentDashboard extends AppCompatActivity {

    private TextView labelStudentPage, labelStudentDetails, labelStudentName, studentName, labelStudentReg, studentReg;
    private TextView labelYear, studentYear, labelSemester, studentSemester, labelCourses, course1, course2, course3, course4, course5;

    private FirebaseFirestore db;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_dashboard);

        // Initialize Firebase components
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        // Initialize your TextViews...
        labelStudentPage = findViewById(R.id.labelStudentPage);
        labelStudentDetails = findViewById(R.id.labelStudentDetails);
        labelStudentName = findViewById(R.id.labelStudentName);
        studentName = findViewById(R.id.studentName);
        labelStudentReg = findViewById(R.id.labelStudentReg);
        studentReg = findViewById(R.id.studentReg);
        labelYear = findViewById(R.id.labelYear);
        studentYear = findViewById(R.id.studentYear);
        labelSemester = findViewById(R.id.labelSemester);
        studentSemester = findViewById(R.id.studentSemester);
        labelCourses = findViewById(R.id.labelCourses);
        course1 = findViewById(R.id.course1);
        course2 = findViewById(R.id.course2);
        course3 = findViewById(R.id.course3);
        course4 = findViewById(R.id.course4);
        course5 = findViewById(R.id.course5);

        // Get the current user
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            String userId = currentUser.getUid();

            // Fetch student details from Firestore
            db.collection("students").document(userId).get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                // Retrieve data from Firestore document
                                String name = document.getString("name");
                                String regNo = document.getString("regNo");
                                String year = document.getString("academicYear");
                                String semester = document.getString("semester");
                                String course1Name = document.getString("course1");
                                String course2Name = document.getString("course2");
                                String course3Name = document.getString("course3");
                                String course4Name = document.getString("course4");
                                String course5Name = document.getString("course5");

                                // Set the retrieved data to TextViews
                                studentName.setText(name);
                                studentReg.setText(regNo);
                                studentYear.setText(year);
                                studentSemester.setText(semester);
                                course1.setText(course1Name);
                                course2.setText(course2Name);
                                course3.setText(course3Name);
                                course4.setText(course4Name);
                                course5.setText(course5Name);
                            } else {
                                Toast.makeText(StudentDashboard.this, "No such document", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(StudentDashboard.this, "Failed to fetch data", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }
}
