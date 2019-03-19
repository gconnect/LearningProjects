package com.glory.retrofittutorial;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private JSONPlaceHolderApi jsonPlaceHolderApi;
    private TextView textResult;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textResult = findViewById(R.id.text);

        //method to serialize the JSON format
        Gson gson = new GsonBuilder().serializeNulls().create();

        // Method for loggin
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .build();


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient)
                .build();
         jsonPlaceHolderApi = retrofit.create(JSONPlaceHolderApi.class);

//        getPost();
//        getComments();
//        createPost();
         updatePost();
//        deletePost();  // fix the error on deletePost
    }

    private void getPost(){
        Call<List<Post>> call = jsonPlaceHolderApi.getPost(new Integer[]{2, 4,6}, "id", "desc");
        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {

                if(!response.isSuccessful()){
                    textResult.setText("Code" + response.code());
                    return;
                }

                // if response is successful this code will run

                List<Post> posts = response.body();

                for (Post post : posts){
                    // Loop through the list of post and append the content to the textview since we are using a single textview
                    String content = "";
                    content += "ID: " + post.getId() + "\n";
                    content += "User Id: " + post.getUserId() + "\n";
                    content += "Title: " + post.getTitle() + "\n";
                    content += "Text: " + post.getText() + "\n\n";

                    textResult.append(content);
                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                //On failure this code will run
                textResult.setText(t.getMessage());
            }
        });
    }

    private void getComments(){
        Call<List<Comment>> call = jsonPlaceHolderApi.getComments(1);
        call.enqueue(new Callback<List<Comment>>() {
            @Override
            public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {
                if(!response.isSuccessful()){
                    textResult.setText("code: " +  response.code());
                }

                List<Comment> comments = response.body();
                for(Comment comment : comments){
                    String content = "";
                    content += "ID: " + comment.getId()+ "\n";
                    content += "ID: " + comment.getPostId() + "\n";
                    content += "ID: " + comment.getName() + "\n";
                    content += "ID: " + comment.getText() + "\n\n";
                    textResult.append(content);
                }
            }

            @Override
            public void onFailure(Call<List<Comment>> call, Throwable t) {
                textResult.setText(t.getMessage());
            }
        });
    }

    // Post request
    private void createPost(){
        Post post = new Post(23, "new title", "new text");

        Map<String, String > fields = new HashMap<>();
        fields.put("userId", "25");
        fields.put("title", "New title");

        Call<Post> call = jsonPlaceHolderApi.createPost(fields);
        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if(!response.isSuccessful()){
                    textResult.setText(response.code());
                }

                Post postResponse = response.body();
                String content = "";
                content += "code: " + response.code() + "\n";
                content += "ID: " + postResponse.getId()+ "\n";
                content += "User ID: " + postResponse.getUserId() + "\n";
                content += "Title: " + postResponse.getTitle() + "\n";
                content += "Text: " + postResponse.getText() + "\n\n";
                textResult.setText(content);

            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                textResult.setText(t.getMessage());

            }
        });
    }

    private void updatePost(){

        Post post = new Post(12, null, "New text");
        Call<Post> call = jsonPlaceHolderApi.putPost(5, post);
        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if(!response.isSuccessful()){
                    textResult.setText(response.code());
                }

                Post postResponse = response.body();
                String content = "";
                content += "code: " + response.code() + "\n";
                content += "ID: " + postResponse.getId()+ "\n";
                content += "User ID: " + postResponse.getUserId() + "\n";
                content += "Title: " + postResponse.getTitle() + "\n";
                content += "Text: " + postResponse.getText() + "\n\n";
                textResult.setText(content);

            }


            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                textResult.setText(t.getMessage());
            }
        });
    }

    private void deletePost(){

        Call<Void> call = jsonPlaceHolderApi.deletePost(5);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                textResult.setText(response.code());

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                textResult.setText(t.getMessage());

            }
        });

    }
}
