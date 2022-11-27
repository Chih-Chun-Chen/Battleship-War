package com.example.refactory.component;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Paint;

import com.example.refactory.R;


public class Battleship extends Sprite {
    private static Battleship battleship;
    Paint color;

    /**
     * Constructor of Battleship
     * @param res
     * @param width To pass width from ComponentView to Battleship class
     */
    private Battleship(Resources res, float width){
        super();
        color = new Paint();
        color.setColor(Color.RED);
        super.width = width;
        bitmap = BitmapFactory.decodeResource(res, R.drawable.battleship);
        int battleshipWidth = (int)(width / 3);
        bitmap = Bitmap.createScaledBitmap(bitmap, battleshipWidth, battleshipWidth, true);
        spriteBounds.set(spriteBounds.left, spriteBounds.top, battleshipWidth, battleshipWidth);
        velocity.set(-10, 0);
    }

    /**
     * Battleship override method
     */
    @Override
    public void move(){
        spriteBounds.offset(velocity.x, velocity.y);
        if (spriteBounds.left < 0) {
            velocity.x *= -1;
        } else if (spriteBounds.right > width) {
            velocity.x *= -1;
        }
    }

    /**
     * Singleton method
     * @param res
     * @param width
     * @return Battleship
     */
    public static Battleship getBattleship(Resources res, float width) {
        if (battleship == null) {
            battleship = new Battleship(res, width);
        }
        return battleship;
    }

    /**
     * To get battleship left bound
     * @return float
     */
    public float getBattleshipLeft(){
        return spriteBounds.left;
    }

    /**
     * To get battleship right bound
     * @return float
     */
    public float getBattleshipRight(){
        return spriteBounds.right;
    }

    /**
     * battleship tick method
     */
    @Override
    public void tick() {
        this.move();
    }
}
