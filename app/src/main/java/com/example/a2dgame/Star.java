package com.example.a2dgame;


import android.graphics.Canvas;
import android.graphics.Paint;

import java.util.Random;

public class Star extends Entity {
    private final static int color = 0xFFFF66FF;
    private float _radius = 40;

    public Star() {
        _x = _game._rng.nextInt(Game.STAGE_WIDTH);
        _y = _game._rng.nextInt(Game.STAGE_HEIGHT);
        _radius = _game._rng.nextInt(6) + 2; //TODO dont hardcore value in your game - move later
        _width = _radius * 2;
        _height = _radius * 2;
        _velX = -4f;
    }

    @Override
    void update() {
        _x += _velX;
        _y += _velY;
        if (right() < 0) {
            setLeft(Game.STAGE_WIDTH);
        }
        if (bottom() < 0){
            setTop(Game.STAGE_HEIGHT);
        }
    }

    @Override
    void render(Canvas canvas, Paint paint) {
        paint.setColor(color);
        canvas.drawCircle( _x + _radius,_y,_radius,paint);
    }
}

