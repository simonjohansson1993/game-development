package com.example.a2dgame;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

public class BitmapEntity extends Entity {
    protected Bitmap _bitmap = null;

    public BitmapEntity() {

    }
    protected void loadBitmap(int resId, int height){
        destroy();
        Bitmap temp  = BitmapFactory.decodeResource(
                _game.getContext().getResources(),resId);

        _bitmap = Utils.scaleToTargetHeight(temp, height);
        temp.recycle();
        _width = _bitmap.getWidth();
        _height = _bitmap.getHeight();
    }

    void render(Canvas canvas, Paint paint) {
        canvas.drawBitmap(_bitmap,_x,_y,paint);

    }


    void destroy() {
        if (_bitmap != null){
            _bitmap.recycle();
            _bitmap = null;
        }

    }
}
