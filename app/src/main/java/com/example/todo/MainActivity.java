
package com.example.todo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.todo.Modal.Todo;
import com.example.todo.adapter.TaskListAdapter;
import com.example.todo.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Fragment {
    private static final String TAG ="MainActivity" ;
    ActivityMainBinding binding;
    private List<Todo> listModels=new ArrayList<Todo>();
//    DataBaseHandler db;
    private FirebaseFirestore db=FirebaseFirestore.getInstance();
    CollectionReference users = db.collection("users");
    FirebaseAuth mAuth;
    FirebaseUser currentUser;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding=DataBindingUtil.inflate(inflater,R.layout.activity_main,container,false);
        binding.fab.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color._0CD4ED)));
        mAuth=FirebaseAuth.getInstance();
        currentUser=mAuth.getCurrentUser();

      if (currentUser!=null) {


          users.document(currentUser.getUid()).collection("TODO").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
              @Override
              public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                  for (QueryDocumentSnapshot queryDocumentSnapshot : queryDocumentSnapshots) {
                      Todo todo = queryDocumentSnapshot.toObject(Todo.class);
                      String id = queryDocumentSnapshot.getId();
                      String title = todo.getTitle();
                      Log.d("id  ", "onSuccess:  " + id);
                      Log.d("title  ", "onSuccess:  " + title);
                      todo.setId(id);
                      listModels.add(todo);


                  }
                  TaskListAdapter adapter = new TaskListAdapter(listModels, MainActivity.this);
                  adapter.notifyDataSetChanged();
                  binding.listItemRecycleView.setAdapter(adapter);
              }
          }).addOnFailureListener(new OnFailureListener() {
              @Override
              public void onFailure(@NonNull Exception e) {
                  Log.d("MainActivity", "onFailure: " + e.getMessage());
              }
          });
          Log.d("MainActivity value", "Current User : " + currentUser.getUid());
      }else{
          Log.d(TAG, "onCreateView: " + "User Not Available");
      }

        /*db = new DataBaseHandler(this);
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

        }*/
        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), NewTaskActivity.class));
            }
        });
    return binding.getRoot();
    }






}