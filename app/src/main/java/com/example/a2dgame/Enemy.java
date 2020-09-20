package com.example.a2dgame;

public class Enemy extends BitmapEntity {
    private final static int ENEMY_HEIGHT = Config.ENEMY_HEIGHT;
    private final static int ENEMY_SPAWN_OFFSET = Config.STAGE_WIDTH;


    public Enemy() {
        super();
        _x = Config.STAGE_WIDTH + Game._rng.nextInt(ENEMY_SPAWN_OFFSET);
        _y = Game._rng.nextInt(Config.STAGE_HEIGHT - ENEMY_HEIGHT);


    }

    int createShip() {
        int resId = R.drawable.tm1;
        switch (Game._rng.nextInt(3)) {
            case 0:
                resId = R.drawable.tm1;
                break;
            case 1:
                resId = R.drawable.tm2;
                break;
            case 2:
                resId = R.drawable.tm3;
                break;
            case 3:
                resId = R.drawable.tm4;
                break;
        }
        return resId;
    }

    int createAsteroid() {
        int resId = R.drawable.asteroid1;
        switch (Game._rng.nextInt(3)) {
            case 0:
                resId = R.drawable.asteroid1;
                break;
            case 1:
                resId = R.drawable.asteroid2;
                break;
            case 2:
                resId = R.drawable.asteroid3;
                break;
            case 3:
                resId = R.drawable.asteroid4;
                break;
        }
        return resId;
    }

    @Override
    void respawn() {
        _x = Config.STAGE_WIDTH + Game._rng.nextInt(ENEMY_SPAWN_OFFSET);
        _y = Game._rng.nextInt(Config.STAGE_HEIGHT - ENEMY_HEIGHT);

    }

    @Override
    void update() {
        _velX = -Config._playerSpeed;
        _x += _velX;
        if (right() < 0) {
            _x = Config.STAGE_WIDTH + Game._rng.nextInt(ENEMY_SPAWN_OFFSET);
        }
    }

    @Override
    void onCollision(Entity that) {
        _x = Config.STAGE_WIDTH + Game._rng.nextInt(ENEMY_SPAWN_OFFSET);
    }
}
