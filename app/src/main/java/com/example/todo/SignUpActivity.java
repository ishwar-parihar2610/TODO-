package com.example.todo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.todo.databinding.ActivitySignUpBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignUpActivity extends AppCompatActivity {
    ActivitySignUpBinding binding;
    FirebaseUser currentUser;
    FirebaseAuth mAuth;
    private static final String TAG = "Email_password";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up);
        mAuth = FirebaseAuth.getInstance();

        binding.signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = binding.emailEdt.getText().toString();
                String password = binding.PasswordEdt.getText().toString();

                if (binding.emailEdt.getText().toString().isEmpty()) {
                    Toast.makeText(SignUpActivity.this, "Email Field Required", Toast.LENGTH_SHORT).show();
                } else if (binding.PasswordEdt.getText().toString().isEmpty()) {
                    Toast.makeText(SignUpActivity.this, "Password Field Required", Toast.LENGTH_SHORT).show();
                } else {
                    createAccount(email, password);
                }
            }
        });
        binding.loginTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUpActivity.this,LoginActivity.class));
            }
        });
    }

    private void createAccount(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    FirebaseUser currentUser = mAuth.getCurrentUser();
                    Toast.makeText(SignUpActivity.this, "SuccessFully Register", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(SignUpActivity.this,LoginActivity.class));
                    sendEmailVerification();
                    updateUi(currentUser);
                } else {
                    Log.e(TAG, "createUserWithEmail:failure", task.getException());
                    Toast.makeText(SignUpActivity.this, "authentication Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void updateUi(FirebaseUser currentUser) {
    }

    @Override
    protected void onStart() {
        super.onStart();
        currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {

        }

    }

    private void openMain() {
        startActivity(new Intent(SignUpActivity.this, MainActivity.class));
        finishAffinity();

    }

    private void sendEmailVerification() {
        // Send verification email
        // [START send_email_verification]
        final FirebaseUser user = mAuth.getCurrentUser();
        user.sendEmailVerification()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                       Log.e("Email ","Email send");
                    }
                });
        // [END send_email_verification]
    }



}