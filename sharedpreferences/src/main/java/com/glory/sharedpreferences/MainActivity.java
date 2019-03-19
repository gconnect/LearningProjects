package com.glory.sharedpreferences;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    private SharedPreferences mpreferences;
    private SharedPreferences.Editor mEditor;
    private EditText mName, mPassword;
    private CheckBox mcheckbox;
    private Button mlogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mName= findViewById(R.id.name);
        mPassword = findViewById(R.id.password);
        mlogin = findViewById(R.id.login);
        mcheckbox = findViewById(R.id.checkbox);

        mpreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mEditor = mpreferences.edit();
        checkSharedPreferences();

        mlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // save the checkbox preferences
                if(mcheckbox.isChecked()){
                    //sets a checkbox when the application starts
                    mEditor.putString(getString(R.string.checkbox), "True");
                    mEditor.commit();

                    //save the name
                    String name = mName.getText().toString();
                    mEditor.putString(getString(R.string.name), name);
                    mEditor.commit();

                    //save the password
                    String password = mPassword.getText().toString();
                    mEditor.putString(getString(R.string.password), password);
                    mEditor.commit();
                }else{
                    //sets a checkbox when the application starts
                    mEditor.putString(getString(R.string.checkbox), "True");
                    mEditor.commit();

                    //save the name
                    String name = mName.getText().toString();
                    mEditor.putString(getString(R.string.name), "");
                    mEditor.commit();

                    //save the password
                    String password = mPassword.getText().toString();
                    mEditor.putString(getString(R.string.password), "   ");
                    mEditor.commit();
                }
            }
        });
    }

    private void checkSharedPreferences(){
        String checkbox = mpreferences.getString(getString(R.string.checkbox), "false");
        String name = mpreferences.getString(getString(R.string.name), "");
        String password = mpreferences.getString(getString(R.string.password), "");

        mName.setText(name);
        mPassword.setText(password);

        if(checkbox.equals("True")){
            mcheckbox.setChecked(true);
        }else{
            mcheckbox.setChecked(false);

        }
    }
}
