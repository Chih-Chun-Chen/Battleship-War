package com.example.refactory.component;

import android.graphics.Canvas;

enum Size {
    BIG, MEDIUM, SMALL
}

abstract class Enemy extends Sprite {
    int airplaneScale;
    int submarineScale;
    int airplaneDrable;
    int submarineDrable;

    int randomSize;
    Size spriteSize;
    int spriteRandomFacing;

    Direction direction;

    boolean isExplode;

    /**
     * Constructor of Enemy
     */
    Enemy(){}

    private void randomFacing(){
        switch (spriteRandomFacing) {
            case 0 : direction = Direction.LEFT_FACING;
                break;
            case 1 : direction = Direction.RIGHT_FACING;
                break;
            case 2 :
                int randomDir = (int) (Math.random() * 2);
                switch (randomDir) {
                    case 0:
                        direction = Direction.LEFT_FACING;
                        break;
                    case 1:
                        direction = Direction.RIGHT_FACING;
                        break;
                }
        }
    }

    protected Size randomSize() {
        randomSize = (int) (Math.random() * 3);
        switch (randomSize){
            case 0 : spriteSize = Size.SMALL;
                break;
            case 1 : spriteSize = Size.MEDIUM;
                break;
            case 2 : spriteSize = Size.BIG;
                break;
        }
        return spriteSize;
    }

    /**
     * Override draw method
     * @param c
     */
    @Override
    public void draw(Canvas c) {
        super.draw(c);
        if (isExplode) {
            reset();
        }
    }

    /**
     * Set to true when explosion
     */
    public void explode() {
        isExplode = true;
    }

    /**
     * Reset method to be override
     */
    public void reset() {
        int randomSpeedX = (int)Math.random() * 10 + 1;
        int randomSpeedY = (int)Math.random() * 10 + 1;
        randomFacing();
        randomSize();

        if (direction == Direction.LEFT_FACING) {
            direction = Direction.RIGHT_FACING;
            velocity.set(randomSpeedX, -randomSpeedY);
            spriteBounds.offsetTo(0, randomY);
        }else {
            direction = Direction.LEFT_FACING;
            velocity.set(-randomSpeedX, -randomSpeedY);
            spriteBounds.offsetTo(width, randomY);
        }
    }

    abstract int getPointValue();
}


