package com.example.a2dgame;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;

import java.util.ArrayList;

public class UI  {

    private Paint _paint = new Paint();

    public UI() {
    }

    void drawEnteties(boolean _gameover,Player _player,ArrayList<Entity> _enteties,Canvas _canvas,SurfaceHolder _holder ){

        _canvas.drawColor(Color.BLACK); //Clearing screen
        for (Entity e : _enteties) {
            e.render(_canvas, _paint);
        }
        _player.render(_canvas, _paint);

        renderHUD(_canvas, _paint,_gameover,_player);
        _holder.unlockCanvasAndPost(_canvas);
    }


    private void renderHUD(final Canvas canvas, final Paint paint, boolean _gameOver,Player _player) {
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
