package com.glory.checkboxrecyclerviewexample;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView lv;
    private ArrayList<Model> modelArrayList;
    private CustomAdapter customAdapter;
    private Button btnselect, btndeselect, btnnext;
    private  String[] animallist = new String[]{"Lion", "Tiger", "Leopard", "Cat"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lv = (ListView) findViewById(R.id.lv);
        btnselect = (Button) findViewById(R.id.select);
        btndeselect = (Button) findViewById(R.id.deselect);
        btnnext = (Button) findViewById(R.id.next);

        modelArrayList = getModel(false);
        customAdapter = new CustomAdapter(this,modelArrayList);
        lv.setAdapter(customAdapter);


        //When the user clicks on this button, all the checkboxes
        // in the listview are automatically selected or checked.
        btnselect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modelArrayList = getModel(true);
                customAdapter = new CustomAdapter(MainActivity.this,modelArrayList);
                lv.setAdapter(customAdapter);
            }
        });


        //When the user clicks the deselect button, all the checkboxes
        // in the listview are deselected.
        btndeselect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modelArrayList = getModel(false);
                customAdapter = new CustomAdapter(MainActivity.this,modelArrayList);
                lv.setAdapter(customAdapter);
            }
        });

        //Next button will take the user to the Next Activity
        btnnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,NextActivity.class);
                startActivity(intent);
            }
        });
    }



    //getModel() method will create an arraylist of the objects of the model class.
    //This arraylist is sent to the adapter class
    // via constructor where it will work as the data provider.
    private ArrayList<Model> getModel(boolean isSelect){
        ArrayList<Model> list = new ArrayList<>();
        for(int i = 0; i < 4; i++){

            Model model = new Model();
            model.setSelected(isSelect);
            model.setAnimal(animallist[i]);
            list.add(model);
        }
        return list;
    }
}
