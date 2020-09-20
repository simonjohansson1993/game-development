package com.example.a2dgame;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class UI  {

    private Paint _paint = new Paint();

    public UI() {
    }

    void renderHUD(final Canvas canvas, final Paint paint, boolean _gameOver,Player _player) {
        final float textSize = 48f;
        paint.setColor(Color.WHITE);
        paint.setTextAlign(Paint.Align.LEFT);
        paint.setTextSize(textSize);

        if (!_gameOver){
            canvas.drawText("Health: " + _player._health,10,textSize,paint);
            canvas.drawText("Score: " + (int) Config._distanceTraveled / 2,900,textSize,paint);
        }
        else if (_player._health == 0){
            final float _centerY = Config.STAGE_HEIGHT/2;
            canvas.drawText("GAME OVER! ",Config.STAGE_WIDTH/2,_centerY,paint);
            canvas.drawText("(press to restart) ",Config.STAGE_WIDTH/2,_centerY + textSize,paint);
        }
    }
}
