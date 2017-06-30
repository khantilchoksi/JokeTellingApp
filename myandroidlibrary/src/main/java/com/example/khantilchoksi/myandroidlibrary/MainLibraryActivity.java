package com.example.khantilchoksi.myandroidlibrary;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class MainLibraryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_library);

        TextView textView = (TextView) findViewById(R.id.joke_display);
        textView.setText(getIntent().getStringExtra("joke"));
    }
}
