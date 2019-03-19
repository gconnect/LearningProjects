package com.glory.jsonparsingexample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
    ArrayList<Contact> contactList = new ArrayList<>();
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listview);

        contactListFromJson();

    }

    public void contactListFromJson(){

        try {
            // Use this if its a jsonobject
//            JSONObject object = new JSONObject(LoadJson());
//            JSONArray contactArray = object.getJSONArray("contact");

            // use this if the contact is a jsonarray
            String jsonDataString = LoadJson();
            JSONArray contactArray = new JSONArray(jsonDataString);

            for (int i =0; i< contactArray.length(); i++){
                // get the current plant object
                JSONObject jsonContact = contactArray.getJSONObject(i);
                String name = jsonContact.getString("name");
                String image = jsonContact.getString("image");
                String phone = jsonContact.getString("phone");

                // create a new plant object
                Contact contact = new Contact(name, image, phone);

                // Add the contact to our contactList
                contactList.add(contact);
            }


            listView.setAdapter(new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, contactList));
//            return contactList;

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    public String LoadJson(){
        String json = null;
        try (InputStream inputStream = getAssets().open("contact.json")) {
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            json = new String (buffer, "UTF-8");

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return json;
    }
}
