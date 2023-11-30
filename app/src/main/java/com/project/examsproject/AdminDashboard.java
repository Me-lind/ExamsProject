package com.project.examsproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AdminDashboard extends AppCompatActivity {
    private Button btnEditMarks, btnPassList, btnStudents;
    private TextView labelAdminDashboard, labelAdminBelow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);

        btnEditMarks = findViewById(R.id.btnEditMarks);
        btnPassList = findViewById(R.id.btnPassList);
        btnStudents = findViewById(R.id.btnStudents);
        labelAdminDashboard = findViewById(R.id.labelAdminDashboard);
        labelAdminBelow = findViewById(R.id.labelAdminBelow);


        btnEditMarks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminDashboard.this, EditMarks.class));
                finish();

            }
        });

        btnPassList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminDashboard.this, PassList.class));
                finish();

            }
        });




        btnStudents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminDashboard.this, AllStudents.class));
                finish();

            }
        });


    }
}