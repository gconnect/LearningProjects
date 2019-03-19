package com.glory.modalbottomsheetexample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements ExampleButtonSheetDialog.BottomSheetListener {
    private TextView mTextview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextview = findViewById(R.id.textview_button_clicked);

        Button openButtonSheet = findViewById(R.id.button_open_sheet);
        openButtonSheet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExampleButtonSheetDialog exampleButtonSheetDialog = new ExampleButtonSheetDialog();
                exampleButtonSheetDialog.show(getSupportFragmentManager(), "Bottom Sheet Dialog Example");
            }
        });
    }

    @Override
    public void onBottonClicked(String text) {
        mTextview.setText(text);

    }
}
