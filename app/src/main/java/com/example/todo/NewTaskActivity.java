package com.example.todo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;

import com.example.todo.Modal.ListModel;
import com.example.todo.databinding.ActivityNewTaskBinding;
import com.example.todo.utils.DataBaseHandler;

public class NewTaskActivity extends AppCompatActivity {
    ActivityNewTaskBinding binding;
    public static final String TAG="NewTaskActivity";
    private DataBaseHandler db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_new_task);
        db=new DataBaseHandler(this);
        db.openDataBase();

        boolean isUpdate=false;


        if (getIntent().getStringExtra("task")!=null){
            isUpdate=true;
            String task=getIntent().getStringExtra("task");
            String description=getIntent().getStringExtra("description");
            binding.taskTitle.setText(task);
            binding.taskDescription.setText(description);
        }
       final boolean finalIsUpdate = isUpdate;
        binding.saveTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String taskTitle=binding.taskTitle.getText().toString();
                String description=binding.taskDescription.getText().toString();
                if(finalIsUpdate){
                    db.updateTask(getIntent().getIntExtra("id",0),taskTitle,description);
                    startActivity(new Intent(NewTaskActivity.this,MainActivity.class));

                }else{
                    ListModel listModel=new ListModel();
                    listModel.setTask(taskTitle);
                    listModel.setDescription(description);
                    db.insertTask(listModel);
                    startActivity(new Intent(NewTaskActivity.this,MainActivity.class));
                }
            }
        });
    }
}