package com.glory.customspinner;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<CompanyItem> mCompanyitem;
    private CompanyAdapter mCompanyAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initList();

        Spinner spinnerCompany = findViewById(R.id.spinner_companies_logo);
        mCompanyAdapter = new CompanyAdapter(this, mCompanyitem);
        spinnerCompany.setAdapter(mCompanyAdapter);

        spinnerCompany.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                CompanyItem clickedCompany = (CompanyItem) parent.getItemAtPosition(position);
                String clickedCompanyName = clickedCompany.getmCompanyName();
                Toast.makeText(MainActivity.this, clickedCompanyName +  " selected", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    private void initList(){
        mCompanyitem = new ArrayList<>();
        mCompanyitem.add(new CompanyItem("kodeHauz", R.drawable.download));
        mCompanyitem.add(new CompanyItem("Starthub", R.drawable.download1));
        mCompanyitem.add(new CompanyItem("Starthub", R.drawable.images));
        mCompanyitem.add(new CompanyItem("Starthub", R.drawable.download));
    }
}
