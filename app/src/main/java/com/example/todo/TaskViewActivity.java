package com.example.todo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.todo.databinding.ActivityMainBinding;
import com.example.todo.databinding.ActivityTaskViewBinding;

public class TaskViewActivity extends AppCompatActivity {
    ActivityTaskViewBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_task_view);
        if (getIntent().getStringExtra("task")!=null){
            binding.taskTitle.setText(getIntent().getStringExtra("task"));
            binding.taskDescription.setText(getIntent().getStringExtra("description"));

        }
        binding.backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TaskViewActivity.this,MainActivity.class));
            }
        });
    }
}