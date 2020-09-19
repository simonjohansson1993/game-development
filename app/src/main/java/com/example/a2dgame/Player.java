package com.example.a2dgame;
import android.graphics.Bitmap;
public class Player extends BitmapEntity {


    private Bitmap _bitmap = null;
    int _health = Config._health;
    private final static int PLAYER_HEIGHT = Config.PLAYER_HEIGHT;
    private final static int STARTING_POS = Config.STARTING_POS;
    private final static int STARTING_HEALTH = Config.STARTING_HEALTH;

    private final static float ACCELERATION = Config.ACCELERATION;
    private final static float MIN_VEL = Config.MIN_VEL;
    private final static float MAX_VEL = Config.MAX_VEL;
    private final static float GRAVITY = Config.GRAVITY;
    private final static float LIFT = - (GRAVITY*2);
    private final static float DRAG = Config.DRAG;

    public Player() {
        super();
        loadBitmap(R.drawable.player,PLAYER_HEIGHT);
        respawn();
    }
    @Override
    void respawn(){
        _x = STARTING_POS;
        _health = STARTING_HEALTH;
        _velX = 0f;
        _velY = 0f;
    }

    @Override
    void update() {

        _velX *= DRAG;
        _velY += GRAVITY;
        if (Game._isBoosting){
            _velX *= ACCELERATION;
            _velY += LIFT;
        }
        _velX = Utils.clamp(_velX,MIN_VEL,MAX_VEL);
        _velY = Utils.clamp(_velY,-MAX_VEL,MAX_VEL);
       _y += _velY;

       _y = Utils.clamp(_y, 0, Config.STAGE_HEIGHT-_height);
       Config._playerSpeed = _velX;
    }


    @Override
    void onCollision(Entity that) {
        //TODO make player invisible for a short amount of time ->implement recovery frames
        _health--;
    }

}
