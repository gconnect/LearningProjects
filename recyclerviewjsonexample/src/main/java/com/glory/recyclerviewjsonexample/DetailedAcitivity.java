package com.glory.recyclerviewjsonexample;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import static com.glory.recyclerviewjsonexample.MainActivity.EXTRA_CREATOR_NAME;
import static com.glory.recyclerviewjsonexample.MainActivity.EXTRA_LIKES_COUNT;
import static com.glory.recyclerviewjsonexample.MainActivity.EXTRA_URL;

public class DetailedAcitivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_acitivity);

        Intent intent = getIntent();
        String imageUrl = intent.getStringExtra(EXTRA_URL);
        String creatorName = intent.getStringExtra(EXTRA_CREATOR_NAME);
        int likes = intent.getIntExtra(EXTRA_LIKES_COUNT, 0);

        ImageView imageView = findViewById(R.id.image_view_detail);
        TextView name = findViewById(R.id.text_view_creator_detail);
        TextView like = findViewById(R.id.text_view_likes_detail);

        Picasso.with(this).load(imageUrl).fit().centerInside().into(imageView);
        name.setText(creatorName);
        like.setText("Likes "+ likes);
    }
}
