package com.example.todo.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todo.MainActivity;
import com.example.todo.Modal.ListModel;
import com.example.todo.NewTaskActivity;
import com.example.todo.R;
import com.example.todo.TaskViewActivity;
import com.example.todo.databinding.TaskItemLayoutBinding;
import com.example.todo.utils.DataBaseHandler;

import java.util.List;

public class TaskListAdapter extends RecyclerView.Adapter<TaskListAdapter.viewHolder> {
    LayoutInflater inflater;
    Context context;
    private List<ListModel> listModels;
    private MainActivity activity;
    private DataBaseHandler db;

    public TaskListAdapter(DataBaseHandler db, List<ListModel> listModels, MainActivity activity) {
        this.listModels = listModels;
        this.activity = activity;
        this.db = db;
    }


    @NonNull
    @Override
    public TaskListAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (inflater == null) {
            inflater = LayoutInflater.from(parent.getContext());
        }
        return new TaskListAdapter.viewHolder(DataBindingUtil.inflate(inflater, R.layout.task_item_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TaskListAdapter.viewHolder holder, int position) {
        db.openDataBase();
        ListModel listModel = listModels.get(position);
        holder.binding.taskTitle.setText(listModel.getTask());
        holder.binding.taskEdt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(activity, NewTaskActivity.class);
                intent.putExtra("id",listModel.getId());
                intent.putExtra("task",listModel.getTask());
                intent.putExtra("description",listModel.getDescription());
                activity.startActivity(intent);
            }
        });
        holder.binding.taskDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListModel listModel=listModels.get(position);
                db.deleteTask(listModel.getId());
                listModels.remove(position);
                notifyItemRemoved(position);
            }
        });
        holder.binding.taskListLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(activity, TaskViewActivity.class);
                intent.putExtra("task",listModel.getTask());
                intent.putExtra("description",listModel.getDescription());
                activity.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listModels.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        TaskItemLayoutBinding binding;

        public viewHolder(TaskItemLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
