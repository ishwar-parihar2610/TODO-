package com.example.todo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.todo.Modal.LoginModel;
import com.example.todo.databinding.ActivitySignUpBinding;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class SignUpActivity extends AppCompatActivity {
    ActivitySignUpBinding binding;
    FirebaseUser currentUser;
    FirebaseAuth mAuth;
    private GoogleSignInClient mGoogleSignInClient;
    private static final String TAG = "Email_password";
    private static final int RC_SIGN_IN = 9007;
    String profileName,profile;
    private FirebaseFirestore db=FirebaseFirestore.getInstance();
    CollectionReference users;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up);
        mAuth = FirebaseAuth.getInstance();
        SignInWithGoogle();
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
                startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
            }
        });
        binding.googleSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });
    }

    private void SignInWithGoogle() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);


    }

    private void createAccount(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    FirebaseUser currentUser = mAuth.getCurrentUser();
                    Toast.makeText(SignUpActivity.this, "SuccessFully Register", Toast.LENGTH_SHORT).show();
                    String username=binding.nameEdt.getText().toString();
                    String id=binding.emailEdt.getText().toString();

                    LoginModel loginModel=new LoginModel(currentUser.getUid(),username,id);
                    users=db.collection("users");
                    users.document(currentUser.getUid()).set(loginModel);

                    startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
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
                        Log.e("Email ", "Email send");
                    }
                });
        // [END send_email_verification]
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                profileName=account.getDisplayName();
                profile= String.valueOf(account.getPhotoUrl());
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.getId());
                firebaseAuthWithGoogle(account.getIdToken());

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(SignUpActivity.this, "Login SuccessFull", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(SignUpActivity.this, LoginActivity.class);
                    intent.putExtra("username",profileName);
                    intent.putExtra("image",profile);
                    startActivity(intent);
                } else {
                    Log.w(TAG, "signInWithCredential:failure", task.getException());
                }
            }
        });
    }
}