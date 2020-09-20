package com.example.a2dgame;

public class Asteroid extends Enemy {
    private final static int ENEMY_HEIGHT = Config.ASTEROID_HEIGHT;
    private final static int ENEMY_SPAWN_OFFSET = Config.STAGE_WIDTH;

    public Asteroid() {
        super();
        _x = Config.STAGE_WIDTH + Game._rng.nextInt(ENEMY_SPAWN_OFFSET);
        _y = Game._rng.nextInt(Config.STAGE_HEIGHT - ENEMY_HEIGHT);

        int resId = createAsteroid();

        loadBitmap(resId, ENEMY_HEIGHT);

        respawn();
    }
    @Override
    void update() {
        _velX = - 10;
        _x += _velX;
        _velY = -3;
        _y += _velY;
        if (right ()< 0 ){
            _x = Config.STAGE_WIDTH + Game._rng.nextInt(ENEMY_SPAWN_OFFSET);
        }
        if (bottom ()< 0 ){
            _y = Config.STAGE_HEIGHT+ Game._rng.nextInt(ENEMY_SPAWN_OFFSET);
        }
    }
}

