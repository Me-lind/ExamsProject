package com.project.examsproject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class StudentRegistration extends AppCompatActivity {

    private EditText edtStudentName, edtStudentEmail, edtStudentRegNo, edtStudentPassword, edtCourse1, edtCourse2, edtCourse3, edtCourse4, edtCourse5;
    private Spinner spinnerSemester, spinnerAcademicYear;
    private Button btnStudentRegistration;
    private CheckBox showPasswordCheckBox;

    private TextView labelStudentRegistration, labelCoursesSelection, labelSemester, labelAcademicYear;
    private FirebaseAuth auth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_registration);

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        edtStudentEmail = findViewById(R.id.edtStudentEmail);
        labelSemester = findViewById(R.id.labelSemester);
        labelAcademicYear = findViewById(R.id.labelAcademicYear);
        labelStudentRegistration = findViewById(R.id.labelStudentRegistration);
        labelCoursesSelection = findViewById(R.id.labelCoursesSelection);
        edtStudentName = findViewById(R.id.edtStudentName);
        edtStudentRegNo = findViewById(R.id.edtStudentRegNo);
        btnStudentRegistration = findViewById(R.id.btnRegisterStudent);
        spinnerSemester = findViewById(R.id.spinnerSemester);
        spinnerAcademicYear = findViewById(R.id.spinnerAcademicYear);
        edtCourse1 = findViewById(R.id.edtCourse1);
        edtCourse2 = findViewById(R.id.edtCourse2);
        edtCourse3 = findViewById(R.id.edtCourse3);
        edtCourse4 = findViewById(R.id.edtCourse4);
        edtCourse5 = findViewById(R.id.edtCourse5);
        edtStudentPassword = findViewById(R.id.edtStudentPassword);
        showPasswordCheckBox = findViewById(R.id.showPasswordCheckBox);

        showPasswordCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    edtStudentPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    edtStudentPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                edtStudentPassword.setSelection(edtStudentPassword.getText().length());
            }
        });

        btnStudentRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txt_name = edtStudentName.getText().toString();
                String txt_email = edtStudentEmail.getText().toString();
                String txt_regNo = edtStudentRegNo.getText().toString();
                String txt_password = edtStudentPassword.getText().toString();
                String txt_course1 = edtCourse1.getText().toString();
                String txt_course2 = edtCourse2.getText().toString();
                String txt_course3 = edtCourse3.getText().toString();
                String txt_course4 = edtCourse4.getText().toString();
                String txt_course5 = edtCourse5.getText().toString();
                String txt_semester = spinnerSemester.getSelectedItem().toString();
                String txt_year = spinnerAcademicYear.getSelectedItem().toString();
                // Get other field values...

                if (txt_name.isEmpty() || txt_email.isEmpty() || txt_regNo.isEmpty() || txt_password.isEmpty() || txt_course1.isEmpty() || txt_course2.isEmpty() || txt_course3.isEmpty() || txt_course4.isEmpty() || txt_course5.isEmpty()) {
                    Toast.makeText(StudentRegistration.this, "Please fill in all the details", Toast.LENGTH_SHORT).show();
                } else {
                    auth.createUserWithEmailAndPassword(txt_email, txt_password)
                            .addOnCompleteListener(StudentRegistration.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        String userId = auth.getCurrentUser().getUid();

                                        Student newStudent = new Student(txt_name, txt_email, txt_regNo, txt_password, txt_course1, txt_course2, txt_course3, txt_course4, txt_course5, txt_year, txt_semester);
                                        saveUserToFirestore(newStudent);
                                    } else {
                                        Toast.makeText(StudentRegistration.this, "Authentication failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });
    }

    private void saveUserToFirestore(Student newStudent) {
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        Map<String, Object> userMap = new HashMap<>();
        userMap.put("name", newStudent.getName());
        userMap.put("email", newStudent.getEmail());
        userMap.put("regNo", newStudent.getRegNo());
        userMap.put("password", newStudent.getPassword());
        userMap.put("course1", newStudent.getCourse1());
        userMap.put("course2", newStudent.getCourse2());
        userMap.put("course3", newStudent.getCourse3());
        userMap.put("course4", newStudent.getCourse4());
        userMap.put("course5", newStudent.getCourse5());
        userMap.put("academicYear", newStudent.getAcademicYear());
        userMap.put("semester", newStudent.getSemester());

        db.collection("students").document(userId)
                .set(userMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(StudentRegistration.this, "Registration successful! ", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(StudentRegistration.this, FirstPage.class));
                            finish();
                        } else {
                            Toast.makeText(StudentRegistration.this, "Failed to save user data to Firestore.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}