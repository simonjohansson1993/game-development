package com.example.a2dgame;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Button startButton = findViewById(R.id.start_btn);
        startButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        final Intent i = new Intent(this,GameActivity.class);
        startActivity(i);
        finish();

    }
}