package com.example.healthiswealth;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUp extends AppCompatActivity {

    Button signupBtn;
    EditText newusername, mail, newpassword, confirmPassword;
    TextView logintxt;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mAuth = FirebaseAuth.getInstance();

        signupBtn = findViewById(R.id.SignupBtn);
        newusername = findViewById(R.id.NewUsenameTxt);
        newpassword = findViewById(R.id.NewPassTxt);
        logintxt = findViewById(R.id.LoginTxt);
        mail = findViewById(R.id.NewMailTxt);
        confirmPassword = findViewById(R.id.ConfirmNewPassTxt);

        logintxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUp.this, Login.class));
            }
        });

        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = newusername.getText().toString().trim();
                String email = mail.getText().toString().trim();
                String password = newpassword.getText().toString();
                String confirmPasswordText = confirmPassword.getText().toString();

                if (username.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPasswordText.isEmpty()) {
                    Toast.makeText(SignUp.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!password.equals(confirmPasswordText)) {
                    Toast.makeText(SignUp.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!isValidPassword(password)) {
                    Toast.makeText(SignUp.this, "Password must be at least 8 characters long and contain letters, digits, and symbols", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Create user with email and password
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(SignUp.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(SignUp.this, "Account created successfully", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(SignUp.this, Login.class));
                                    finish();
                                } else {
                                    Toast.makeText(SignUp.this, "Failed to create account: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }

    // Validate password
    private boolean isValidPassword(String password) {
        return password.length() >= 8 && password.matches(".*[a-zA-Z]+.*") && password.matches(".*\\d+.*") && password.matches(".*[@#$%^&+=]+.*");
    }
}
