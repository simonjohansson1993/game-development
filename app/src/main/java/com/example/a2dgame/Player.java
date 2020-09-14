package com.example.a2dgame;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import androidx.appcompat.app.AppCompatActivity;
public class Player extends BitmapEntity {


    private Bitmap _bitmap = null;
    int _health = 3; //TODO REMOVE MAGIC VALUE
    private final static int PLAYER_HEIGHT = 60;
    private final static int STARTING_POS = 40;
    private final static int STARTING_HEALTH = 3;
    private final static float ACCELERATION = 1.1f;
    private final static float MIN_VEL = 1f;
    private final static float MAX_VEL = 15f;
    private final static float GRAVITY = 1.1f;
    private final static float LIFT = - (GRAVITY*2);
    private final static float DRAG = 0.97f;

   // Resources res = getActivity().getResources();

    //final int PLAYER_HEIGHT = res.getInteger(R.integer.PLAYER_HEIGHT);

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
        if (_game._isBoosting){
            _velX *= ACCELERATION;
            _velY += LIFT;
        }
        _velX = Utils.clamp(_velX,MIN_VEL,MAX_VEL);
        _velY = Utils.clamp(_velY,-MAX_VEL,MAX_VEL);
       _y += _velY;

       _y = Utils.clamp(_y, 0,_game.STAGE_HEIGHT-_height);
       _game._playerSpeed = _velX;
    }


    @Override
    void onCollision(Entity that) {
        //TODO make player invisible for a short amount of time ->implement recovery frames
        _health--;
    }

}
