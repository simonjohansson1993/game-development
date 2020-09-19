package com.example.a2dgame;

public class Ship extends Enemy{
    private final static int ENEMY_HEIGHT = Config.ENEMY_HEIGHT;
    private final static int ENEMY_SPAWN_OFFSET = Config.STAGE_WIDTH;

    public Ship() {
        super();
        _x = Config.STAGE_WIDTH + Game._rng.nextInt(ENEMY_SPAWN_OFFSET);
        _y = Game._rng.nextInt(Config.STAGE_HEIGHT- ENEMY_HEIGHT);

        int resId = createShip();

        loadBitmap(resId, ENEMY_HEIGHT);
        _bitmap = Utils.flipBitmap(_bitmap,false);
        respawn();
    }
}
