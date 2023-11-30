package com.project.examsproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
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

public class administratorLogin extends AppCompatActivity {

    private EditText adminEmail, adminPassword;
    private Button btnAdminLogin;

    private CheckBox showPasswordCheckBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administrator_login);

        adminEmail = findViewById(R.id.adminEmail);
        adminPassword = findViewById(R.id.adminPassword);
        btnAdminLogin = findViewById(R.id.btnAdminLogin);
        showPasswordCheckBox = findViewById(R.id.showPasswordCheckBox);

        showPasswordCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    adminPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    adminPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                adminPassword.setSelection(adminPassword.getText().length());
            }
        });

        btnAdminLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String enteredEmail = adminEmail.getText().toString().trim();
                String enteredPassword = adminPassword.getText().toString().trim();

                FirebaseFirestore db = FirebaseFirestore.getInstance();

                db.collection("admins")
                        .whereEqualTo("email", enteredEmail)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    QuerySnapshot querySnapshot = task.getResult();
                                    if (querySnapshot != null && !querySnapshot.isEmpty()) {
                                        // Found admin with matching email
                                        DocumentSnapshot document = querySnapshot.getDocuments().get(0);
                                        String fetchedPassword = document.getString("password");

                                        // Validate the password
                                        if (enteredPassword.equals(fetchedPassword)) {
                                            FirebaseAuth.getInstance().signInWithEmailAndPassword(enteredEmail, fetchedPassword)
                                                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<AuthResult> task) {
                                                            if (task.isSuccessful()) {
                                                                // Authentication successful
                                                                Intent intent = new Intent(administratorLogin.this, AdminDashboard.class);
                                                                startActivity(intent);
                                                                finish();
                                                            } else {
                                                                // Passwords don't match
                                                                Toast.makeText(administratorLogin.this, "Invalid email or password.", Toast.LENGTH_SHORT).show();
                                                            }
                                                        }
                                                    });
                                        } else {
                                            // Passwords don't match
                                            Toast.makeText(administratorLogin.this, "Invalid email or password.", Toast.LENGTH_SHORT).show();
                                        }
                                    } else {
                                        // Admin with entered email not found
                                        Toast.makeText(administratorLogin.this, "Admin not found.", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    // Error fetching admin document
                                    Toast.makeText(administratorLogin.this, "Error logging in.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }
}
