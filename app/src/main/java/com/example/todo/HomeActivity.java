package com.example.todo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.todo.Modal.LoginModel;
import com.example.todo.Modal.Todo;
import com.example.todo.databinding.ActivityHomeBinding;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HomeActivity extends AppCompatActivity {
    ActivityHomeBinding binding;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser currentUser = mAuth.getCurrentUser();
    String TAG = "HomeActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home);
        binding.menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.drawerLayout.openDrawer(GravityCompat.START);
            }
        });
        binding.navigationView.setItemIconTintList(null);
        binding.navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.logout) {
                    binding.navigationView.setCheckedItem(R.id.logout);
                   mAuth.signOut();

                    if (currentUser==null) {
                        Toast.makeText(HomeActivity.this, "sign out SuccessFully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(HomeActivity.this, LoginActivity.class));
                    }else{
                        Log.d(TAG, "onNavigationItemSelected: "+currentUser.getUid());
                    }
                }
                return true;
            }
        });
       /* binding.navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                item.setChecked(true);
                if (item.getItemId()==R.id.logout){
                    FirebaseAuth.getInstance().signOut();
                    Toast.makeText(HomeActivity.this, "sign out SuccessFully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(HomeActivity.this,LoginActivity.class));
                }
                return true;
            }


        });*/
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
          /* NavigationUI.setupWithNavController(binding.navigationView, navController);*/
        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {
                binding.textTitle.setText(destination.getLabel());


            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.navigation_view, menu);
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.home:
                Toast.makeText(this, "home option selected", Toast.LENGTH_SHORT).show();
                break;
            case R.id.logout:
                Toast.makeText(this, "Logut option selected", Toast.LENGTH_SHORT).show();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
