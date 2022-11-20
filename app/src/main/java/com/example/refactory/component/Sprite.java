package com.example.refactory.component;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PointF;
import android.graphics.RectF;

abstract class Sprite implements TickListener{

    public Bitmap bitmap;
    protected RectF spriteBounds;
    protected PointF velocity;
    float randomSpeedX;
    float randomY;
    float height;
    float width;

    /**
     * Constructor of Sprite
     */
    public Sprite() {
        spriteBounds = new RectF();
        velocity = new PointF();
    }

    /**
     * To set the sprite position
     * @param x position of spriteBounds
     * @param y position of spriteBounds
     */
    public void setPosition(float x, float y){
        spriteBounds.offsetTo(x, y);
    }

    /**
     * To draw the sprite
     * @param c
     */
    public void draw(Canvas c) {
        c.drawBitmap(bitmap, spriteBounds.left, spriteBounds.top, null);
    }

    /**
     * To make the sprite move
     */
    public void move(){
        spriteBounds.offset(velocity.x, velocity.y);
    }

    /**
     * To test if two object overlap
     * @param other
     * @return
     */
    public boolean overlaps(Sprite other) {
        return  (RectF.intersects(this.spriteBounds, other.spriteBounds));
    }

}
