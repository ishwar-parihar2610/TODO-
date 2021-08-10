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
            binding.taskEdt.setText(task);
        }
       final boolean finalIsUpdate = isUpdate;
        binding.saveTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text=binding.taskEdt.getText().toString();
                if(finalIsUpdate){
                    db.updateTask(getIntent().getIntExtra("id",0),text);
                    startActivity(new Intent(NewTaskActivity.this,MainActivity.class));

                }else{
                    ListModel listModel=new ListModel();
                    listModel.setTask(text);
                    db.insertTask(listModel);
                    startActivity(new Intent(NewTaskActivity.this,MainActivity.class));
                }
            }
        });
    }
}