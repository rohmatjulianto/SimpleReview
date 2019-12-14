package com.joule.simplereviewapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;

import com.joule.simplereviewapp.model.MainViewModel;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    MainViewModel model;

    RatingBar ratingBar;
    EditText edtName, edtContent;
    Button btnSubmit, btnUpload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.rvShow);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        final ReviewAdapter reviewAdapter = new ReviewAdapter(this);
        reviewAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(reviewAdapter);

        model = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(MainViewModel.class);
        model.getAllData();

        model.getAllAdata().observe(this, new Observer<ArrayList<ReviewModel>>() {
            @Override
            public void onChanged(ArrayList<ReviewModel> reviewModels) {
                if (reviewModels != null){
                    reviewAdapter.setListReview(reviewModels);
                }
            }
        });


//        form insert
        ratingBar = findViewById(R.id.ratingBar);
        edtName = findViewById(R.id.your_name);
        edtContent = findViewById(R.id.your_content);
        btnSubmit = findViewById(R.id.btn_submit);
        btnUpload = findViewById(R.id.btn_upload);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("UUU", "onClick: "+ ratingBar.getRating());

                String name = edtName.getText().toString();
                String content = edtContent.getText().toString();
                String rating = String.valueOf(ratingBar.getRating());

                model.pushData(name,rating,content);
                model.getAllData();
                recyclerView.setAdapter(reviewAdapter);
            }
        });
    }

}
