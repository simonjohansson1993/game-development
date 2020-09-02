package com.example.a2dgame;

public class Enemy extends BitmapEntity {
    private final static int ENEMY_HEIGHT = 60;
    private final static int ENEMY_SPAWN_OFFSET = Game.STAGE_WIDTH;


    public Enemy() {
        super();
        _x = Game.STAGE_WIDTH + _game._rng.nextInt(ENEMY_SPAWN_OFFSET);
        _y = _game._rng.nextInt(Game.STAGE_HEIGHT- ENEMY_HEIGHT);

        int resId = R.drawable.tm1;
        switch (_game._rng.nextInt(3)){
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
        loadBitmap(resId, ENEMY_HEIGHT);
        _bitmap = Utils.flipBitmap(_bitmap,false);
        respawn();
    }

    @Override
    void respawn(){
        _x = Game.STAGE_WIDTH + _game._rng.nextInt(ENEMY_SPAWN_OFFSET);
        _y = _game._rng.nextInt(Game.STAGE_HEIGHT- ENEMY_HEIGHT);

    }
    @Override
    void update() {
        _velX = - _game._playerSpeed;
        _x += _velX;
        if (right ()< 0 ){
            _x = Game.STAGE_WIDTH + _game._rng.nextInt(ENEMY_SPAWN_OFFSET);
        }
    }

    @Override
    void onCollision(Entity that) {
        _x = Game.STAGE_WIDTH + _game._rng.nextInt(ENEMY_SPAWN_OFFSET);
    }
}
