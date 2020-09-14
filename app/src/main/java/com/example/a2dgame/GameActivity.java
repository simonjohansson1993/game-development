package com.example.a2dgame;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;

public class GameActivity extends AppCompatActivity {

    _game _game;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        _game = new _game(this);
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