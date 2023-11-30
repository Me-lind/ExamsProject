package com.project.examsproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class StudentLogin extends AppCompatActivity {

    private EditText studentEmail, studentPassword;
    private CheckBox showPasswordCheckBox;

    private Button btnStudentLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_login);

        studentEmail = findViewById(R.id.studentEmail);
        studentPassword = findViewById(R.id.studentPassword);
        btnStudentLogin = findViewById(R.id.btnStudentLogin);
        showPasswordCheckBox = findViewById(R.id.showPasswordCheckBox);


        showPasswordCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    studentPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    studentPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                studentPassword.setSelection(studentPassword.getText().length());
            }
        });

        btnStudentLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String enteredEmail = studentEmail.getText().toString().trim();
                String enteredPassword = studentPassword.getText().toString().trim();

                FirebaseFirestore db = FirebaseFirestore.getInstance();

                db.collection("students").whereEqualTo("email", enteredEmail)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (DocumentSnapshot document : task.getResult()) {
                                        String fetchedEmail = document.getString("email");
                                        String fetchedPassword = document.getString("password");

                                        if (enteredPassword.equals(fetchedPassword)) {
                                            FirebaseAuth.getInstance().signInWithEmailAndPassword(fetchedEmail, fetchedPassword)
                                                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<AuthResult> task) {
                                                            if (task.isSuccessful()) {
                                                                // Authentication successful
                                                                Toast.makeText(StudentLogin.this, "Student Login success", Toast.LENGTH_SHORT).show();
                                                                Intent intent = new Intent(StudentLogin.this, StudentDashboard.class);
                                                                startActivity(intent);
                                                                finish();
                                                            } else {
                                                                // Authentication failed
                                                                Toast.makeText(StudentLogin.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                                                            }
                                                        }
                                                    });
                                        } else {
                                            // Passwords don't match
                                            Toast.makeText(StudentLogin.this, "Invalid email or password.", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                } else {
                                    // Error fetching document
                                    Toast.makeText(StudentLogin.this, "Error logging in.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }
}
