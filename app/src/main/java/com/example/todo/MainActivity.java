package com.example.todo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.content.res.ColorStateList;
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
        binding.fab.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color._0CD4ED)));
        db = new DataBaseHandler(this);
        db.openDataBase();
        listModels = new ArrayList<>();
        listModels = db.getAllTask();
        Collections.reverse(listModels);

        if (listModels.size()<=0){
            binding.emptyTaskLayout.setVisibility(View.VISIBLE);
            binding.listItemRecycleView.setVisibility(View.GONE);
        }else{
            binding.emptyTaskLayout.setVisibility(View.GONE);
            binding.listItemRecycleView.setVisibility(View.VISIBLE);
            binding.listItemRecycleView.setLayoutManager(new LinearLayoutManager(this));
            TaskListAdapter adapter = new TaskListAdapter(db,listModels, this);
            adapter.notifyDataSetChanged();
            binding.listItemRecycleView.setAdapter(adapter);

        }
        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, NewTaskActivity.class));
            }
        });

    }
}