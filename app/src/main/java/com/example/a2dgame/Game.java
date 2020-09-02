package com.example.a2dgame;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.Random;

public class Game extends SurfaceView implements Runnable {

    //-------------------MEMBERS------------------------------
    public static final String TAG = "GAME";
    static final int STAGE_WIDTH = 1280; //TODO: Move game settings to resources
    static final int STAGE_HEIGHT = 720;
    static final int STAR_COUNT = 40;
    static final int ENEMY_COUNT = 10;

    private Thread _gameThread = null;
    private volatile boolean _isRunning = false;
    private Canvas _canvas = null;
    private SurfaceHolder _holder = null;
    private Paint _paint = new Paint();
    private ArrayList<Entity> _enteties = new ArrayList<>();
    private Player _player = null;
    private boolean _gameover = true;
    Random _rng = new Random();
    volatile boolean _isBoosting = false;
    float _distanceTraveled = 0f;
    float _playerSpeed = 0f;

    //-------------------CONSTRUCTOR---------------------------
    public Game(Context context) {
        super(context);
        Entity._game = this;
        _holder = getHolder();
        _holder.setFixedSize(STAGE_WIDTH, STAGE_HEIGHT);
        _paint = new Paint();
        for (int i = 0; i < STAR_COUNT; i++) {
            _enteties.add(new Star());
        }
        for (int i = 0; i < ENEMY_COUNT; i++) {
            _enteties.add(new Enemy());
        }
        _player = new Player();

    }

    private void restart() {
        for (Entity e : _enteties) {
            e.respawn();
        }
        _player.respawn();
        _distanceTraveled = 0f;
        _gameover = false;


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
        Entity._game = null;
    }


    private void update() {
        if (_gameover) {
            return;
        }
        _player.update();
        for (Entity e : _enteties) {
            e.update();
        }
        _distanceTraveled += _playerSpeed;
        checkCollisions();
        //CHECK FOR WIN/LOOSE
        checkGameOver();
    }

    private void checkGameOver() {
        if (_player._health < 1) {
            _gameover = true;

        }
        //TODO: COUNT HIGHSCORE
    }

    private void checkCollisions() {
        Entity temp = null;
        for (int i = STAR_COUNT; i < _enteties.size(); i++) { // 0- STAR_COUNT = Background entities.
            temp = _enteties.get(i);
            if (_player.isColliding(temp)) {
                _player.onCollision(temp);
                temp.onCollision(_player);
                //TODO PLAY SOUND EFFECT ON COLLISSION
            }
        }
    }

    private void render() {
        //Check if canvas doesn't exists
        if (!acquireAndLockCanvas()) {
            return;
        }

            //Draw entities,player and HUD
        _canvas.drawColor(Color.BLACK); //Clearing screen
        for (Entity e : _enteties) {
            e.render(_canvas, _paint);
        }
        _player.render(_canvas, _paint);

            renderHUD(_canvas, _paint);


        _holder.unlockCanvasAndPost(_canvas);

    }

    private void renderHUD(final Canvas canvas, final Paint paint) {
        final float textSize = 48f;
        paint.setColor(Color.WHITE);
        paint.setTextAlign(Paint.Align.LEFT);
        paint.setTextSize(textSize);
        if (!_gameover){
            canvas.drawText("Health: " + _player._health,10,textSize,paint);
            canvas.drawText("Score: " + (int) _distanceTraveled / 2,900,textSize,paint);
        }
        else if (_player._health == 0){
            final float _centerY = STAGE_HEIGHT/2;
            canvas.drawText("GAME OVER! ",STAGE_WIDTH/2,_centerY,paint);
            canvas.drawText("(press to restart) ",STAGE_WIDTH/2,_centerY + textSize,paint);

        }
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
