package com.example.todo.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todo.MainActivity;
import com.example.todo.Modal.ListModel;
import com.example.todo.Modal.Todo;
import com.example.todo.NewTaskActivity;
import com.example.todo.R;
import com.example.todo.TaskViewActivity;
import com.example.todo.databinding.TaskItemLayoutBinding;
import com.example.todo.utils.DataBaseHandler;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class TaskListAdapter extends RecyclerView.Adapter<TaskListAdapter.viewHolder> {
    LayoutInflater inflater;
    Context context;
    private List<Todo> listModels;
    private MainActivity activity;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference users = db.collection("users");
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser currentUser = mAuth.getCurrentUser();

    public TaskListAdapter(List<Todo> listModels, MainActivity activity) {
        this.listModels = listModels;
        this.activity = activity;

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
        Todo listModel = listModels.get(position);
        holder.binding.taskTitle.setText(listModel.getTitle());
        holder.binding.dateTv.setText("Created At: " + listModel.getCreatedAt());
    /*    holder.binding.taskEdt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


               *//* Intent intent = new Intent(activity.getContext(), NewTaskActivity.class);
                intent.putExtra("title", listModel.getTitle());
                intent.putExtra("description", listModel.getDescription());
                intent.putExtra("id",listModels.get(position).getId());
                Log.e("activity", "onClick: "+listModel.getId());*//*
                String id=listModel.getId();
                Toast.makeText(context, "id is:"+id, Toast.LENGTH_SHORT).show();
               *//* activity.startActivity(intent);*//*
            }
        });*/
        holder.binding.taskDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                users.document(currentUser.getUid()).collection("TODO").document(listModel.getId()).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(activity.getContext(), "delete successFully", Toast.LENGTH_SHORT).show();
                        listModels.remove(position);
                        notifyItemRemoved(position);
                    }
                });
               /* db.collection("users").document(listModels.get(position).getId()).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(context, "delete successFully", Toast.LENGTH_SHORT).show();
                        listModels.remove(position);
                        notifyItemRemoved(position);

                    }
                });*/


            }
        });

        holder.binding.taskEdt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Intent intent=new Intent(activity.getContext(),NewTaskActivity.class);
            intent.putExtra("title",listModel.getTitle());
            intent.putExtra("description",listModel.getDescription());
            intent.putExtra("id",listModel.getId());
            activity.startActivity(intent);

            }
        });
        holder.binding.taskListLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity.getContext(), TaskViewActivity.class);
                intent.putExtra("task", listModel.getTitle());
                intent.putExtra("description", listModel.getDescription());
                intent.putExtra("date", listModel.getCreatedAt());

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
