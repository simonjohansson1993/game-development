package com.example.a2dgame;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

public class Player extends Entity {

    private Bitmap _bitmap = null;
    int health = 3; //TODO REMOVE MAGIC VALUE

    public Player() {
        _bitmap = BitmapFactory.decodeResource(
                _game.getContext().getResources(),R.drawable.player);

        int targetHeight = 100;
        float ratio = targetHeight/ (float) _bitmap.getHeight();
        int newHeight = (int) (_bitmap.getHeight() * ratio);
        int newWidth = (int)  (_bitmap.getWidth()* ratio);
        Bitmap temp = Bitmap.createScaledBitmap(_bitmap,newWidth,newHeight,true);

        _bitmap.recycle();
        _bitmap = temp;
        _width = _bitmap.getWidth();
        _height = _bitmap.getHeight();

    }

    @Override
    void update() {
        _x += _velX;
        _y += _velY;
        if (left() > Game.STAGE_WIDTH) {
            setRight(0);
        }
        if (top() > Game.STAGE_HEIGHT){
            setBottom(0);
        }
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
