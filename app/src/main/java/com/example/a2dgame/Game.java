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
    private Thread _gameThread = null;
    private volatile boolean _isRunning = false;
    private Canvas _canvas = null;
    private SurfaceHolder _holder = null;
    private Paint _paint = new Paint();
    private ArrayList<Entity> _enteties = new ArrayList<>();
    Random _rng = new Random();
//-------------------CONSTRUCTOR---------------------------
    public Game(Context context) {
        super(context);
        Entity._game = this;
        _holder = getHolder();
        _holder.setFixedSize(STAGE_WIDTH,STAGE_HEIGHT);
        _paint = new Paint();

        for (int i = 0;  i < STAR_COUNT; i++){
            _enteties.add(new Star());
        }
        _enteties.add(new Player());

    }
//-------------------BEHAVIOUR----------------------------
    @Override
    public void run() {
       while (_isRunning) {
           input();
           update();
           render();
       }

    }

    protected void onResume(){
        Log.d(TAG,"OnResume");
        _isRunning = true;
        _gameThread = new Thread(this);
        _gameThread.start();
    }

    protected void onPause() {
      Log.d(TAG,"OnPause");
      _isRunning = false;
        try {
            _gameThread.join();
        } catch (InterruptedException e) {

            Log.d(TAG,Log.getStackTraceString(e.getCause()));
        }
    }


    protected void onDestroy(){
        Log.d(TAG,"OnDestroy");
        _gameThread = null;

        for (Entity e : _enteties) {
            e.destroy();
        }
        Entity._game = null;
    }


    private void input() {

    }
    private void update() {
        for (Entity e : _enteties) {
            e.update();
        }
    }

    private void render() {
        //Check if canvas doesn't exists
        if (!acquireAndLockCanvas()){
            return;
        }


        _canvas.drawColor(Color.BLACK); //Clearing screen
        for (Entity e : _enteties) {
            e.render(_canvas,_paint);
        }
        //Draw each entity
        //draw the player
        //draw the HUD
        _holder.unlockCanvasAndPost(_canvas);

    }

    private boolean acquireAndLockCanvas() {
        if (!_holder.getSurface().isValid()){
            return false;

        }
        _canvas = _holder.lockCanvas();
         return (_canvas != null);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        return true;
    }
}
