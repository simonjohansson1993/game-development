package com.example.a2dgame;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

public class Player extends Entity {

    private Bitmap _bitmap = null;
    int health = 3; //TODO REMOVE MAGIC VALUE
    final static int targetHeight = 100;//TODO REMOVE THIS MAGIC VALUE

    public Player() {
        _bitmap = BitmapFactory.decodeResource(
                _game.getContext().getResources(),R.drawable.player);

        Bitmap temp = Utils.scaleToTargetHeight(_bitmap,targetHeight);

        _bitmap.recycle();
        _bitmap = temp;
        _width = _bitmap.getWidth();
        _height = _bitmap.getHeight();
        _velY = 6;
        _velX = -3;

    }



    @Override
    void update() {
        super.update();

       _x = Utils.wrap(_x, -_width, Game.STAGE_WIDTH);
       _y = Utils.wrap(_y, _height,Game.STAGE_HEIGHT);

    }

    @Override
    void render(Canvas canvas, Paint paint) {
        canvas.drawBitmap(_bitmap,_x,_y,paint);

    }

    @Override
    void onCollision(Entity that) {
        super.onCollision(that);
    }

    @Override
    void destroy() {
        if (_bitmap != null){
            _bitmap.recycle();
            _bitmap = null;
        }

    }
}
