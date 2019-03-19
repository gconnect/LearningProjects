package com.glory.recyclerviewjsonexample;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements PixabayAdapter.OnItemClickListener {
    public static final String EXTRA_URL= "image";
    public static final String EXTRA_CREATOR_NAME= "creatorName";
    public static final String EXTRA_LIKES_COUNT= "likes";

    PixabayAdapter pixabayAdapter;
    private RequestQueue mRequestQueue;
    private RecyclerView recyclerView;
    private ArrayList<PixabayItem> mPixabayItems;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(pixabayAdapter);
        mPixabayItems =new ArrayList<>();
        mRequestQueue = Volley.newRequestQueue(this);
        parseJson();
    }

    private void parseJson(){
        String url = "https://pixabay.com/api/?key=11704445-31cbb1d73a226f6fbaa955ff4&q=yellow+flowers&image_type=photo/?per_page=40";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("hits");
                            for (int i =0; i<jsonArray.length(); i++){
                                JSONObject hit = jsonArray.getJSONObject(i);
                                String creatorName = hit.getString("user");
                                String creatorurl = hit.getString("webformatURL");
                                int likeCount = hit.getInt("likes");
                                mPixabayItems.add(new PixabayItem(creatorurl, creatorName,likeCount));
                            }

                            pixabayAdapter = new PixabayAdapter(MainActivity.this, mPixabayItems);
                            recyclerView.setAdapter(pixabayAdapter);
                            pixabayAdapter.setOnItemClickListener(MainActivity.this);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        mRequestQueue.add(request);
    }

    @Override
    public void OnItemClick(int position) {
        Intent detailIntent = new Intent(this, DetailedAcitivity.class);
        PixabayItem clickedItem = mPixabayItems.get(position);
        detailIntent.putExtra(EXTRA_URL, clickedItem.getmImageUrl());
        detailIntent.putExtra(EXTRA_CREATOR_NAME, clickedItem.getmCreator());
        detailIntent.putExtra(EXTRA_LIKES_COUNT, clickedItem.getmLikes());

        startActivity(detailIntent);
    }
}
