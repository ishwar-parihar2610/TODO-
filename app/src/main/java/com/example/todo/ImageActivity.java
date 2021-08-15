package com.example.todo;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.todo.databinding.ActivityImageBinding;

public class ImageActivity extends AppCompatActivity {
    ActivityImageBinding binding;
    int SELECT_PICTURE = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_image);
        binding.shapeOFView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageChooser();
            }
        });
        binding.saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.profileImg==null){
                    Toast.makeText(ImageActivity.this, "select Profile Image ", Toast.LENGTH_SHORT).show();
                }else{
                    startActivity(new Intent(ImageActivity.this,MainActivity.class));
                }
            }
        });
    }


    private void imageChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK){
            if (requestCode==SELECT_PICTURE){
                assert data != null;
                Uri selectImageUri=data.getData();
                Log.e("image"," Url is " +selectImageUri);
                if (selectImageUri!=null) {
                    Glide.with(this).load(selectImageUri).into(binding.profileImg);
                }
                }
            }
        }
    }


