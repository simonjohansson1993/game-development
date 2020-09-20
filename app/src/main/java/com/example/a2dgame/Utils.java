package com.example.a2dgame;

import android.graphics.Bitmap;
import android.graphics.Matrix;

public abstract class Utils {
    static Matrix _matrix = new Matrix();

    public static Bitmap flipBitmap(Bitmap src, final boolean horizontal) {
        _matrix.reset();
        final int centerX = src.getWidth() / 2;
        final int centerY = src.getHeight() / 2;
        if (horizontal) {
            _matrix.postScale(1, -1, centerX, centerY);
        } else {
            _matrix.postScale(-1, 1, centerX, centerY);
        }
        return Bitmap.createBitmap(src, 0, 0, src.getWidth(), src.getHeight(), _matrix, true);
    }

    public static Bitmap scaleToTargetHeight(Bitmap src, final int targetHeight) {

        float ratio = targetHeight / (float) src.getHeight();
        int newHeight = (int) (src.getHeight() * ratio);
        int newWidth = (int) (src.getWidth() * ratio);
        return Bitmap.createScaledBitmap(src, newWidth, newHeight, true);
    }

    public static float wrap(float val, final float min, final float max) {
        if (val < min) {
            val = max;
        } else if (val > max) {
            val = min;
        }
        return val;
    }

    public static float clamp(float val, final float min, final float max) {
        if (val > max) {
            val = max;
        } else if (val < min) {
            val = min;
        }
        return val;
    }
}
