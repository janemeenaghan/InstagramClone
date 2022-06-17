package com.example.instagramclone;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import org.parceler.Parcels;

public class PostDetailsActivity extends AppCompatActivity {


    private TextView tvDescription;
    private TextView tvCreatedAt;
    private TextView likes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_details);
        tvDescription = findViewById(R.id.tvDescription2);
        tvCreatedAt = findViewById(R.id.tvCreatedAt2);
        likes = findViewById(R.id.likes);


        Post post = (Post) Parcels.unwrap(getIntent().getParcelableExtra("post"));
        tvDescription.setText(post.getDescription());
        tvCreatedAt.setText( post.getCreatedAt().toString() );
        likes.setText(""+post.getLikesCount()+" likes");
    }



}