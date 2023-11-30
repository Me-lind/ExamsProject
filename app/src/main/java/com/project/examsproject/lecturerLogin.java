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

public class lecturerLogin extends AppCompatActivity {

    private EditText edtLecturerEmail, edtLecPassword;
    private Button btnLecturerLogin;
    private CheckBox showPasswordCheckBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lecturer_login);

        edtLecturerEmail = findViewById(R.id.edtLecturerEmail);
        edtLecPassword = findViewById(R.id.edtLecPassword);
        btnLecturerLogin = findViewById(R.id.btnLecturerLogin);
        showPasswordCheckBox = findViewById(R.id.showPasswordCheckBox);

        showPasswordCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    edtLecPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    edtLecPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                edtLecPassword.setSelection(edtLecPassword.getText().length());
            }
        });

        btnLecturerLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String enteredEmail = edtLecturerEmail.getText().toString().trim();
                String enteredPassword = edtLecPassword.getText().toString().trim();

                // Inside btnLecturerLogin.setOnClickListener()

                FirebaseFirestore db = FirebaseFirestore.getInstance();

                db.collection("lecturers").whereEqualTo("email", enteredEmail)
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
                                                                Intent intent = new Intent(lecturerLogin.this, lecturerEntry.class);
                                                                startActivity(intent);
                                                                finish();
                                                            } else {
                                                                // Authentication failed
                                                                Toast.makeText(lecturerLogin.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                                                            }
                                                        }
                                                    });
                                        } else {
                                            // Passwords don't match
                                            Toast.makeText(lecturerLogin.this, "Invalid email or password.", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                } else {
                                    // Error fetching document
                                    Toast.makeText(lecturerLogin.this, "Error logging in.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

            }
        });
    }
}
