package com.project.examsproject;

import android.os.Bundle;
import android.util.Log;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

public class PassList extends AppCompatActivity {

    private TableLayout passListTable;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pass_fail_list);

        passListTable = findViewById(R.id.passListTable);
        db = FirebaseFirestore.getInstance();

        fetchPassFailList();
    }

    private void fetchPassFailList() {
        db.collection("student_marks")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Log.d("TASK","task success");
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            String studentName = document.getId();
                            Log.d("TASK","STUDENT NAME" + studentName);

                            db.collection("student_marks").document(studentName).collection("courses").get().addOnCompleteListener(
                                    task1 -> {
                                        if (task1.isSuccessful()) {
                                            Log.d("TASK","task 1 success");
                                            int totalMarks = 0;
                                            for (QueryDocumentSnapshot course : task1.getResult()) {
                                                Marks courseMarks = course.toObject(Marks.class);
                                                totalMarks += Integer.parseInt(courseMarks.getTotalMarks());
                                            }
                                            totalMarks /= task1.getResult().size();
                                            String passFailStatus = (totalMarks >= 40) ? "Pass" : "Fail";
                                            runOnUiThread(() -> displayStudentInTable(studentName, passFailStatus));
                                        }
                                    }
                            );
                        }
                    } else {
                        // Handle failures
                    }
                });
    }

    private void displayStudentInTable(String studentName, String passFailStatus) {
        TableRow tableRow = new TableRow(this);

        TextView textViewStudentName = new TextView(this);
        textViewStudentName.setText(studentName);

        TextView textViewPassFail = new TextView(this);
        textViewPassFail.setText(passFailStatus);

        tableRow.addView(textViewStudentName);
        tableRow.addView(textViewPassFail);

        passListTable.addView(tableRow);
    }
}
