package com.example.a2dgame;


import android.graphics.Canvas;
import android.graphics.Paint;

public class Star extends BitmapEntity {
    private float _radius = 0;
    private int starColor;

    public Star() {
        _x = Game._rng.nextInt(Config.STAGE_WIDTH);
        _y = Game._rng.nextInt(Config.STAGE_HEIGHT);

        _radius = Game._rng.nextInt(Config.UPPER_BOUND) + Config.LOWER_BOUND;
        _width = _radius * 2;
        _height = _radius * 2;
        _velX = Config.STAR_VELOCITY;

        switch (Game._rng.nextInt(3)) {
            case 0:
                starColor = Config.STAR_COLOR_1;
                break;
            case 1:
                starColor = Config.STAR_COLOR_2;
                break;
            case 2:
                starColor = Config.STAR_COLOR_3;
                break;

        }
    }


    @Override
    void update() {
        _velX = (-Config._playerSpeed / 3); //66% SLOWER SPEED for stars
        _x += _velX;
        _x = Utils.wrap(_x, 0, Config.STAGE_WIDTH + _width);
        _y = Utils.wrap(_y, 0, Config.STAGE_HEIGHT - _height);
    }

    @Override
    void render(Canvas canvas, Paint paint) {
        int color = starColor;
        paint.setColor(color);
        canvas.drawCircle(_x + _radius, _y, _radius, paint);
    }
}

