package com.example.a2dgame;


import android.graphics.Canvas;
import android.graphics.Paint;

public class Star extends Entity  {
    private final int color = 0xFFFF66FF;
    private float _radius = 40;

    public Star() {
        _x = _game._rng.nextInt(_game.STAGE_WIDTH);
        _y = _game._rng.nextInt(_game.STAGE_HEIGHT);
        _radius = _game._rng.nextInt(6) + 2; //TODO dont hardcore value in your game - move later
        _width = _radius * 2;
        _height = _radius * 2;
        _velX = -4f;
    }

    @Override
    void update() {
        _velX = - _game._playerSpeed;
        _x += _velX;
        _x = Utils.wrap(_x, 0, _game.STAGE_WIDTH+_width);
        _y = Utils.wrap(_y, 0, _game.STAGE_HEIGHT - _height);
    }

    @Override
    void render(Canvas canvas, Paint paint) {
        paint.setColor(color);
        canvas.drawCircle( _x + _radius,_y,_radius,paint);
    }
}

