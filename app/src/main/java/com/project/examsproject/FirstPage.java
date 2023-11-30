package com.project.examsproject;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class FirstPage extends AppCompatActivity {
    private Button btnLecLogin,btnStudentRegistration,btnStudentLogin, btnAdminLogin;
    private TextView labelAppName;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_page);
        labelAppName=findViewById(R.id.labelAppName);
        btnLecLogin=findViewById(R.id.btnLecLogin);
        btnStudentLogin=findViewById(R.id.btnStudentLogin);
        btnStudentRegistration=findViewById(R.id.btnStudentRegistration);
        btnAdminLogin = findViewById(R.id.btnAdminLogin);

        btnStudentRegistration.setOnClickListener(view -> {
            Intent intent = new Intent(FirstPage.this, StudentRegistration.class);
            startActivity(intent);
        });

        btnLecLogin.setOnClickListener(view -> {
            Intent intent = new Intent(FirstPage.this, lecturerLogin.class);
            startActivity(intent);
        });

        btnStudentLogin.setOnClickListener(view -> {
            Intent intent = new Intent(FirstPage.this, StudentLogin.class);
            startActivity(intent);
        });

        btnAdminLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FirstPage.this, administratorLogin.class));
            }
        });
    }
}