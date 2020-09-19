package com.example.a2dgame;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

public class GameActivity extends AppCompatActivity {

    Game _game;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        _game = new Game(this);
        setContentView(_game);
    }

    @Override
    protected void onPause(){

        super.onPause();
        _game.onPause();
    }

    @Override
    protected void onResume(){

        super.onResume();
        _game.onResume();
    }

    @Override
    protected void onDestroy(){

        super.onDestroy();
        _game.onDestroy();
    }

}