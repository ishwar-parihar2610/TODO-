package com.example.todo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.sql.Time;
import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends AppCompatActivity {
    FirebaseAuth mAuth=FirebaseAuth.getInstance();
    FirebaseUser user=mAuth.getCurrentUser();
    String TAG="SplashActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                if(user==null){
                    startActivity(new Intent(new Intent(SplashActivity.this,LoginActivity.class)));
                }else{
                    startActivity(new Intent(new Intent(SplashActivity.this,HomeActivity.class)));
                    Log.d(TAG, "run: "+user.getUid());
                }
                finishAffinity();

            }
        },3000);
    }
}