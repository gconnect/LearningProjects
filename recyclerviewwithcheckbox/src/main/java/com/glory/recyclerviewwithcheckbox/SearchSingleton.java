package com.glory.recyclerviewwithcheckbox;

import android.content.Context;
import android.support.v7.widget.SearchView;
import java.util.ArrayList;


public class SearchSingleton  {
    private static SearchSingleton mInstance;
    private SearchView.OnQueryTextListener searchView;
    private static Context mContext;
    private ArrayList<TeachersModel> mTeacherModel = new ArrayList<>();
    private TeacherAdapter  teacherAdapter;


    private SearchSingleton(){
        searchView = new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                String userInput = newText.toLowerCase();

                ArrayList<TeachersModel> newList  = new ArrayList<>();
                for(TeachersModel name: mTeacherModel){
                    newList.add(name);
                }
                teacherAdapter.updateList(newList);
                return true;
            }
        };
    }


    public static synchronized SearchSingleton getmInstance(){
        if(mInstance == null){
            mInstance = new SearchSingleton();
        }
        return mInstance;
    }

}
