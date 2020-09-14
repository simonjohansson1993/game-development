package com.example.a2dgame;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Button startButton = findViewById(R.id.start_btn);
        startButton.setOnClickListener(this);

        final TextView highScore = (TextView) findViewById(R.id.highScore);
        SharedPreferences prefs = getSharedPreferences(_game.PREFS, Context.MODE_PRIVATE);
        int longestDistance = prefs.getInt(_game.LONGEST_DISTANCE,0);
        highScore.setText("Highscore: " + longestDistance + " km");
    }

    @Override
    public void onClick(View view) {
        final Intent i = new Intent(this,GameActivity.class);
        startActivity(i);
        finish();

    }
}