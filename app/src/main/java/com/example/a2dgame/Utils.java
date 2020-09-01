package com.example.a2dgame;

import android.graphics.Bitmap;

public abstract class Utils {


    public static Bitmap scaleToTargetHeight(Bitmap src, final int targetHeight) {
        
        float ratio = targetHeight/ (float) src.getHeight();
        int newHeight = (int) (src.getHeight() * ratio);
        int newWidth = (int)  (src.getWidth()* ratio);
        return Bitmap.createScaledBitmap(src,newWidth,newHeight,true);
    }

    public static float wrap(float val,final float min,final float max){
        if (val < min) {
            val = max;
        }
        else if (val> max) {
            val = min;
        }
        return val;
    }
    public static float clap (float val, final float min,final float max){
        if (val > max){
            val = max;
        }
        else if (val < min){
            val = min;
        }
        return val;
    }
}
