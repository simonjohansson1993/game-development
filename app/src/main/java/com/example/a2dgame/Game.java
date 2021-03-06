package com.example.a2dgame;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.Random;

public class Game extends SurfaceView implements Runnable {
    //-------------------MEMBERS------------------------------
    Resources resource = getResources();
    public static final String TAG = "GAME";
    public static final String PREFS = "com.example.a2dgame";
    public static final String LONGEST_DISTANCE = "longest_distance";

    private Thread _gameThread = null;
    private volatile boolean _isRunning = false;
    private Canvas _canvas = null;
    private SurfaceHolder _holder = null;
    private Paint _paint = new Paint();
    private ArrayList<Entity> _enteties = new ArrayList<>();
    private Player _player = null;
    private UI ui = null;
    private boolean _gameover = true;
    Random _rng = new Random();
    private SharedPreferences _prefs = null;
    private SharedPreferences.Editor _editor = null;


    volatile boolean _isBoosting = false;
    private JukeBox _jukebox = null;
    int _maxDistanceTraveled = 0;
    long frameTick = 0;
    boolean hasCollide = false;


    //-------------------CONSTRUCTOR---------------------------
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)

    public Game(Context context) {
        super(context);
        Entity.Game = this;
        _holder = getHolder();
        _holder.setFixedSize(Config.STAGE_WIDTH, Config.STAGE_HEIGHT);
        _paint = new Paint();
        _jukebox = new JukeBox(context);


        _prefs = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE);
        _editor = _prefs.edit();


        for (int i = 0; i < Config.STAR_COUNT; i++) {
            _enteties.add(new Star());
        }
        for (int i = 0; i < Config.SHIP_COUNT; i++) {
            _enteties.add(new Ship());
        }
        for (int i = 0; i < Config.METEOR_COUNT; i++) {
            _enteties.add(new Asteroid());
        }
        _player = new Player();
        ui = new UI();

        restart();
    }

    private void restart() {
        for (Entity e : _enteties) {
            e.respawn();
        }
        _player.respawn();
        Config._distanceTraveled = 0f;
        _maxDistanceTraveled = _prefs.getInt(LONGEST_DISTANCE, 0);
        _gameover = false;
        if (!_prefs.getBoolean("isMuted", false)) {
            _jukebox.play(JukeBox.StartGame, -1);
        }
        //TODO sound effect for starting game
        frameTick = 0;
        hasCollide = false;

    }

    //-------------------BEHAVIOUR----------------------------
    @Override
    public void run() {
        while (_isRunning) {
            update();
            render();
        }
    }

    protected void onResume() {
        Log.d(TAG, "OnResume");
        _isRunning = true;
        _gameThread = new Thread(this);
        _gameThread.start();
    }

    protected void onPause() {
        Log.d(TAG, "OnPause");
        _isRunning = false;
        try {
            _gameThread.join();
        } catch (InterruptedException e) {

            Log.d(TAG, Log.getStackTraceString(e.getCause()));
        }
    }

    protected void onDestroy() {
        Log.d(TAG, "OnDestroy");
        _gameThread = null;

        for (Entity e : _enteties) {
            e.destroy();
        }
        _jukebox.destroy();
        Entity.Game = null;
    }


    private void update() {
        frameTick++;

        if (_gameover) {
            return;
        }
        _player.update();
        for (Entity e : _enteties) {
            e.update();
        }
        Config._distanceTraveled += Config._playerSpeed;

        if (frameTick > Config.playerRecoveryTime) {
            checkCollisions();
        }
        if (frameTick > 120) {
            hasCollide = false;
        }

        //CHECK FOR WIN/LOOSE
        checkGameOver();
    }

    private void checkGameOver() {
        if (_player._health < 1) {
            _gameover = true;
            if (Config._distanceTraveled > _maxDistanceTraveled) {
                _maxDistanceTraveled = (int) Config._distanceTraveled;
                _editor.putInt(LONGEST_DISTANCE, _maxDistanceTraveled);
                _editor.apply();
            }
            if (!_prefs.getBoolean("isMuted", false)) {
                _jukebox.play(JukeBox.GAMEOVER, 0);
            }
        }
        //TODO: COUNT HIGHSCORE

    }

    private void checkCollisions() {

        Entity temp = null;
        for (int i = Config.STAR_COUNT; i < _enteties.size(); i++) { // 0- STAR_COUNT = Background entities.
            temp = _enteties.get(i);
            if (_player.isColliding(temp)) {
                _player.onCollision(temp);
                hasCollide = true;
                frameTick = 0;

                temp.onCollision(_player);
                if (!_prefs.getBoolean("isMuted", false)) {
                    _jukebox.play(JukeBox.CRASH, 0);
                }
            }
        }
    }

    private void render() {
        //Check if canvas doesn't exists
        if (!acquireAndLockCanvas()) {
            return;
        }
        _canvas.drawColor(Color.BLACK); //Clearing screen
        for (Entity e : _enteties) {
            e.render(_canvas, _paint);
        }

        if (hasCollide && ((frameTick % 2 == 0) && frameTick < 120)) {
            _player.render(_canvas, _paint);
        } else if (hasCollide && ((frameTick % 2 == 1) && frameTick < 120)) {
            //not render to create blink effect
        } else if (!hasCollide) {
            _player.render(_canvas, _paint);
        }


        ui.renderHUD(_canvas, _paint, _gameover, _player);
        _holder.unlockCanvasAndPost(_canvas);
    }


    private boolean acquireAndLockCanvas() {
        if (!_holder.getSurface().isValid()) {
            return false;
        }
        _canvas = _holder.lockCanvas();
        return (_canvas != null);
    }

    @Override
    public boolean onTouchEvent(final MotionEvent event) {
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_UP:
                _isBoosting = false;
                if (_gameover) {
                    restart();
                }
                break;
            case MotionEvent.ACTION_DOWN:
                _isBoosting = true;
                break;
        }
        return true;
    }

}
