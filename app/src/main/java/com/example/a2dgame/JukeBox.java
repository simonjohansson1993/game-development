package com.example.a2dgame;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.IOException;

public class JukeBox {
    SoundPool _soundPool = null;
    private static final int MAX_STREAMS = 3;
    static int CRASH = 0;
    static int GAMEOVER = 0;
    static int StartGame = 0;
    static int LIFT = 0;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    JukeBox(final Context context) {
        AudioAttributes attr = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();

        _soundPool = new SoundPool.Builder().setAudioAttributes(attr)
                .setMaxStreams(MAX_STREAMS).build();
        loadSounds(context);
    }

    private void loadSounds(final Context context) {
        try {
            AssetManager assetManager = context.getAssets();
            AssetFileDescriptor descriptor;
            descriptor = assetManager.openFd("crash.wav");
            CRASH = _soundPool.load(descriptor, 0);
            descriptor = assetManager.openFd("DeathSound.wav");
            GAMEOVER = _soundPool.load(descriptor, 0);
            descriptor = assetManager.openFd("BackgroundSound.mp3");
            StartGame = _soundPool.load(descriptor, 1);
            //descriptor = assetManager.openFd("lift.wav");
            //LIFT = _soundPool.load(descriptor,1);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void play(final int soundID, final int loop) {
        final float leftVolume = 1f;
        final float rightVolume = 1f;
        final int priority = 1;
        final float rate = 1.0f;


        if (soundID > 0) {
            _soundPool.play(soundID, leftVolume, rightVolume, priority, loop, rate);
        }
    }

    void destroy() {
        _soundPool.release();
        _soundPool = null;
    }
}
