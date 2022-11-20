package com.example.refactory.component;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;

public class Missile extends Sprite{

    Direction direction;
    Paint color;
    PointF start;
    PointF stop;
    boolean isMissileHit;

    /**
     * Constructor of Missile
     * @param direction To pass Direction types: LEFT_FACING, RIGHT_FACING
     */
    public Missile(Direction direction) {
        super();
        this.direction = direction;
        color = new Paint();
        color.setStrokeWidth(30);
        color.setColor(Color.BLUE);
        start = new PointF();
        stop = new PointF();
        if (direction == Direction.LEFT_FACING){
            start.set(384.9624f, 892.9132f);
            stop.set(377.96387f, 898.9429f);
            spriteBounds.set(384.9624f, 892.9132f, 377.96387f, 898.9429f);
            velocity.set(-10, -30);
        } else if (direction == Direction.RIGHT_FACING) {
            start.set(694.97314f, 892.9132f);
            stop.set(700.97167f, 898.9429f);
            spriteBounds.set(694.97314f, 892.9132f, 700.97167f, 898.9429f);
            velocity.set(10, -30);
        }
    }

    /**
     * Missile override method
     * @param c
     */
    @Override
    public void draw(Canvas c){
            c.drawLine(spriteBounds.left, spriteBounds.top, spriteBounds.right, spriteBounds.bottom, color);
    }

    /**
     * To get Missile's bottom
     * @return float value of spriteBounds.bottom
     */
    public float getBoundsBottom(){
        return spriteBounds.bottom;
    }

    /**
     * Override tick method
     */
    @Override
    public void tick() {
        super.move();
    }

    /**
     * Set to true if Airp and missile is overlapped
     */
    public void setMissile() {
        isMissileHit = true;
    }

    /**
     * Return boolean value
     * @return
     */
    public boolean isMissile() {
        return isMissileHit;
    }
}
