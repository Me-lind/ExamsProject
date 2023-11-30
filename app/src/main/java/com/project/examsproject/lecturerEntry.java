package com.project.examsproject;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class lecturerEntry extends AppCompatActivity {
    private EditText assignmentOne, assignmentTwo, catOne, catTwo, finalExams, courseSelection;
    private Spinner spinnerStudentSelect;
    private Button submitMarks;

    private List<String> studentList;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lecturer_entry);

        assignmentOne = findViewById(R.id.assignment1);
        assignmentTwo = findViewById(R.id.assignment2);
        catOne = findViewById(R.id.cat1);
        catTwo = findViewById(R.id.cat2);
        spinnerStudentSelect = findViewById(R.id.studentSpinner);
        submitMarks = findViewById(R.id.submitMarks);
        finalExams = findViewById(R.id.exams);
        courseSelection = findViewById(R.id.courseSelection);

        studentList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, studentList);
        spinnerStudentSelect.setAdapter(adapter);

        loadStudents();
        setSpinnerListener();

        submitMarks.setOnClickListener(view -> uploadMarks());
    }

    private void setSpinnerListener() {
        spinnerStudentSelect.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                String selectedStudent = (String) adapterView.getSelectedItem();
                Toast.makeText(lecturerEntry.this, "Selected Student: " + selectedStudent, Toast.LENGTH_SHORT).show();
            }

            public void onNothingSelected(AdapterView<?> parentView) {
                Toast.makeText(lecturerEntry.this, "Select an option from the drop-down", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadStudents() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {
            db.collection("students")
                    .get()
                    .addOnSuccessListener(queryDocumentSnapshots -> {
                        studentList.clear();
                        for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                            String studentName = document.getString("name");
                            if (studentName != null) {
                                studentList.add(studentName);
                            }
                        }
                        adapter.notifyDataSetChanged(); // Notify adapter about data change
                    })
                    .addOnFailureListener(e -> Toast.makeText(lecturerEntry.this, "Error fetching students", Toast.LENGTH_SHORT).show());
        }
    }

    private void uploadMarks() {
        // Get marks data
        String assignment1Mark = assignmentOne.getText().toString().trim();
        String assignment2Mark = assignmentTwo.getText().toString().trim();
        String cat1Mark = catOne.getText().toString().trim();
        String cat2Mark = catTwo.getText().toString().trim();
        String examMark = finalExams.getText().toString().trim();
        String selectedCourse = courseSelection.getText().toString().trim();
        String selectedStudent = spinnerStudentSelect.getSelectedItem().toString();

        int total = calculateTotalMarks(assignment1Mark, assignment2Mark, cat1Mark, cat2Mark, examMark);


        // Save marks data to 'student_marks' collection
        saveMarksToDatabase(selectedCourse, selectedStudent, assignment1Mark, assignment2Mark, cat1Mark, cat2Mark, examMark, total);
    }

    private void saveMarksToDatabase(String course, String student, String assignment1, String assignment2, String cat1, String cat2, String exam, int totalMarks) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // Create a map of marks data
//        Map<String, Object> marksData = new HashMap<>();
//        marksData.put("assignment1", assignment1);
//        marksData.put("assignment2", assignment2);
//        marksData.put("cat1", cat1);
//        marksData.put("cat2", cat2);
//        marksData.put("exam", exam);
//        marksData.put("course", course);
//        marksData.put("totalMarks", totalMarks);// Add total marks to the data

        Marks marks = new Marks(
          assignment1,assignment2,cat1,cat2,exam,course,String.valueOf(totalMarks)
        );

        // Save marks data to 'student_marks' collection
        db.collection("student_marks")
                .document(student)
                .collection("courses")
                .document(course) // Use the course name as the document ID
                .set(marks)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(lecturerEntry.this, "Marks submitted successfully", Toast.LENGTH_SHORT).show();
                    removeUploadedStudent();
                    // No need to remove the student from the spinner in this context
                })
                .addOnFailureListener(e -> Toast.makeText(lecturerEntry.this, "Error submitting marks", Toast.LENGTH_SHORT).show());
    }

    private void removeUploadedStudent() {
        // Remove the uploaded student from the spinner
        String selectedStudent = spinnerStudentSelect.getSelectedItem().toString();
        studentList.remove(selectedStudent);
        adapter.notifyDataSetChanged(); // Notify adapter about data change
    }

    private int calculateTotalMarks(String assignment1Mark, String assignment2Mark, String cat1Mark, String cat2Mark, String examMark) {
        // Calculate total marks based on the given weights
        int assignmentTotal = (int) ((Double.parseDouble(assignment1Mark) + Double.parseDouble(assignment2Mark)) * 0.5);
        int catTotal = (int) ((Double.parseDouble(cat1Mark) + Double.parseDouble(cat2Mark)) * 0.333);
        int examTotal = (int) (Double.parseDouble(examMark) * 1);

        return assignmentTotal + catTotal + examTotal;
    }
}
