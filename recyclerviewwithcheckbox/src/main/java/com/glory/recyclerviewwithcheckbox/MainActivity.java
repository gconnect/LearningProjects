package com.glory.recyclerviewwithcheckbox;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    StringBuilder stringBuilder = null;
    boolean [] checkItems;
    private RecyclerView recyclerView;
    private TeacherAdapter teacherAdapter;
    private LinearLayoutManager layoutManager;
    private ArrayList<TeachersModel> mTeacherModel = new ArrayList<>();
    SearchView searchView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        mTeacherModel.add(new TeachersModel(R.drawable.anthony,"Glory", "She is a teacher"));
        mTeacherModel.add(new TeachersModel(R.drawable.anthony,"John", "She is a teacher"));
        mTeacherModel.add(new TeachersModel(R.drawable.anthony,"Joy", "She is a teacher"));
        mTeacherModel.add(new TeachersModel(R.drawable.anthony,"Buhari", "She is a teacher"));
        mTeacherModel.add(new TeachersModel(R.drawable.anthony,"Effiong", "She is a teacher"));
        mTeacherModel.add(new TeachersModel(R.drawable.anthony,"Abijan", "She is a teacher"));
        mTeacherModel.add(new TeachersModel(R.drawable.anthony,"Glory", "She is a teacher"));
        mTeacherModel.add(new TeachersModel(R.drawable.anthony,"Glory", "She is a teacher"));
        mTeacherModel.add(new TeachersModel(R.drawable.anthony,"Glory", "She is a teacher"));

        recyclerView= findViewById(R.id.recycleview_checkbox);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        teacherAdapter= new TeacherAdapter(this,mTeacherModel);
        recyclerView.setAdapter(teacherAdapter);


        teacherAdapter.setOnItemClickListener(new TeacherAdapter.setOnItemClickListener() {
            @Override
            public void onCheckboxClick(int position) {
                mTeacherModel.get(position);
            }
        });


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Searching...");
        searchView = (SearchView) findViewById(R.id.search);


        FloatingActionButton floatingActionButton = findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stringBuilder = new StringBuilder();

                for(TeachersModel teachersModel: teacherAdapter.teacherIsCheck){
                    stringBuilder.append(teachersModel.getProfilePix() ) ;
                    stringBuilder.append(teachersModel.getName()) ;
                    stringBuilder.append(teachersModel.getDescription()) ;
                }if (teacherAdapter.teacherIsCheck.size() > 0){
                    Toast.makeText(MainActivity.this, stringBuilder.toString(), Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(MainActivity.this, "please select players", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main, menu);

        MenuItem item = menu.findItem(R.id.search);
        return true;
    }


    public void searchImplementation (){

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                teacherAdapter.getFilter().filter(newText);
                return false;
            }
        });
        CheckBox selectAll = findViewById(R.id.select_all);
        final CheckBox checkBox = findViewById(R.id.checkbox);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.next:
                Toast.makeText(this, "Open Next", Toast.LENGTH_SHORT).show();
            case R.id.search:
                searchImplementation();
                break;

        }
        return super.onOptionsItemSelected(item);
    }



}
