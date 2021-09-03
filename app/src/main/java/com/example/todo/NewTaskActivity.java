package com.example.todo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.todo.Modal.ListModel;
import com.example.todo.Modal.Todo;
import com.example.todo.databinding.ActivityNewTaskBinding;
import com.example.todo.utils.DataBaseHandler;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class NewTaskActivity extends AppCompatActivity {
    ActivityNewTaskBinding binding;
    public static final String TAG = "NewTaskActivity";
    FirebaseAuth mAuth=FirebaseAuth.getInstance();
    FirebaseUser currentUser=mAuth.getCurrentUser();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference users = db.collection("users");
    Todo todo=new Todo();
    Boolean isUpdate = false;
    String documentID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_new_task);
        Date currentDate = Calendar.getInstance().getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String date = dateFormat.format(currentDate);

//        Log.d(TAG, "document id is : "+ getIntent().getStringExtra("id"));

        if (getIntent().getStringExtra("title") != null) {
            isUpdate=true;
            binding.taskTitle.setText(getIntent().getStringExtra("title"));
            binding.taskDescription.setText((getIntent().getStringExtra("description")));
            documentID= getIntent().getStringExtra("id");

        }

            binding.saveTask.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mAuth = FirebaseAuth.getInstance();
                    currentUser = mAuth.getCurrentUser();
                    if (isUpdate==true){
                        updateTask(date,binding.taskTitle.getText().toString(),binding.taskDescription.getText().toString());


                    }else{
                        if (currentUser != null) {
                            Log.d(TAG, "Current User : " + currentUser.getUid());
                            Date currentDate = Calendar.getInstance().getTime();
                            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                            String date = dateFormat.format(currentDate);
                            String id = currentUser.getUid();
                            String taskTitle = binding.taskTitle.getText().toString();
                            String taskDescription = binding.taskDescription.getText().toString();
                            addTask(date, taskTitle, taskDescription);
                            startActivity(new Intent(NewTaskActivity.this, HomeActivity.class));
                        } else {
                            Toast.makeText(NewTaskActivity.this, "user null", Toast.LENGTH_SHORT).show();
                        }
                    }



            }
        });
       /* db=new DataBaseHandler(this);
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
        });*/
    }

    private void addTask(String date, String taskTitle, String taskDescription) {
        Todo task = new Todo(date, taskTitle, taskDescription);
        currentUser = mAuth.getCurrentUser();
        users.document(currentUser.getUid()).collection("TODO").add(task);
    }

    private void updateTask(String date, String taskTitle, String taskDescription) {
        Todo task = new Todo(date, taskTitle, taskDescription);
   /*     db.collection("users").document(currentUser.getUid()).collection(todo.getId()).add(task);*/
        Log.d(TAG, "collection path : "+todo.getId());
       /* users.document(currentUser.getUid()).collection(documentID).add(task);*/
        db.collection("users").document(currentUser.getUid()).collection("TODO").document(documentID).set(task);
        Toast.makeText(this, "update SuccessFully", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(NewTaskActivity.this,HomeActivity.class));
        /*users.document(currentUser.getUid()).collection("TODO").add(task);*/
    }

}