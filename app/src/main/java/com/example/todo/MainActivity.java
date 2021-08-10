package com.example.todo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.todo.Modal.ListModel;
import com.example.todo.adapter.TaskListAdapter;
import com.example.todo.databinding.ActivityMainBinding;
import com.example.todo.utils.DataBaseHandler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    private List<ListModel> listModels;
    DataBaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        db = new DataBaseHandler(this);
        db.openDataBase();
        listModels = new ArrayList<>();
        listModels = db.getAllTask();
        Collections.reverse(listModels);

        binding.listItemRecycleView.setLayoutManager(new LinearLayoutManager(this));
        TaskListAdapter adapter = new TaskListAdapter(db,listModels, this);
        adapter.notifyDataSetChanged();
        binding.listItemRecycleView.setAdapter(adapter);
        binding.addTesk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, NewTaskActivity.class));
            }
        });
    }
}