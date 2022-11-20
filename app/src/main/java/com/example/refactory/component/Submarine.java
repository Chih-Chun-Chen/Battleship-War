package com.example.refactory.component;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.refactory.R;
import com.example.refactory.main.SettingsActivity;

public class Submarine extends Enemy {
    int point;
    Resources res;
    Context context;
    /**
     * Constructor of Submarine
     * @param context
     * @param width To pass width from ComponentView to Airplane class
     * @param height To pass height from ComponentView to Airplane class
     */
    public Submarine(Context context, float width, float height){
        super();
        this.context = context;
        res = context.getResources();
        super.width = width;
        super.height = height;
        setSubmarine();
        bitmap = BitmapFactory.decodeResource(res, submarineDrable);
        int submarineWidth = (int)(width / submarineScale);
        bitmap = Bitmap.createScaledBitmap(bitmap, submarineWidth, submarineWidth, true);
        spriteBounds.set(0, 0, submarineWidth, submarineWidth);
        spriteRandomFacing = SettingsActivity.SettingsFragment.controlSubDir(context);
        reset();
    }

    private void setSubmarine() {
        switch (randomSize()){
            case SMALL : {
                submarineScale = 20;
                if (direction == Direction.LEFT_FACING) {
                    submarineDrable = R.drawable.little_submarine;
                }else{
                    submarineDrable = R.drawable.little_submarine_flip;
                }
                break;
            }
            case MEDIUM : {
                submarineScale = 13;
                if (direction == Direction.LEFT_FACING) {
                    submarineDrable = R.drawable.medium_submarine;
                }else{
                    submarineDrable = R.drawable.medium_submarine_flip;
                }
                break;
            }
            case BIG : {
                submarineScale = 8;
                if (direction == Direction.LEFT_FACING) {
                    submarineDrable = R.drawable.big_submarine;
                }else{
                    submarineDrable = R.drawable.big_submarine_flip;
                }
                break;
            }
        }
    }

    /**
     *Submarine override method
     */
    @Override
    public void move(){
        randomY = (float) (Math.random() * (height / 2 - (height / 2 * 0.2) + 10) + height * 0.5f);
        super.move();
        int RandomY = (int) (Math.random() * 100);
        int randomSpeedTime = (int) (Math.random() * 10);

        //Random speed
        if (randomSpeedTime < 1){
            if (direction == Direction.LEFT_FACING) {
                velocity.x = getRandomVelocity();
            }
            else if (direction == Direction.RIGHT_FACING) {
                velocity.x = -getRandomVelocity();
            }
        } else {
            if (direction == Direction.LEFT_FACING) {
                velocity.x = - SettingsActivity.SettingsFragment.controlSubSpeed(context);
            } else {
                velocity.x = SettingsActivity.SettingsFragment.controlSubSpeed(context);
            }

        }

        if (spriteBounds.right < 0) {
            spriteBounds.offsetTo(width, randomY);
            reset();
        }

        if (spriteBounds.left > width){
            spriteBounds.offsetTo(0, randomY);
            reset();
        }

        if (SettingsActivity.SettingsFragment.onSubAltitude(context)) {
            if (RandomY < 1 || spriteBounds.top < height / 2) {
                velocity.y *= -1;
            } else if (RandomY > 99 || spriteBounds.bottom > height) {
                velocity.y *= -1;
            }
        } else {
            velocity.y = 0;
        }
    }

    private float getRandomVelocity() {
        if (isExplode) {
            return 0;
        }else{
            return randomSpeedX = (int) ((float) (Math.random() * 3) * -1);
        }
    }

    /**
     * Override explode method
     */
    @Override
    public void explode() {
        super.explode();
        if (isExplode) {
            bitmap = BitmapFactory.decodeResource(res, R.drawable.submarine_explosion);
            int subExplodeWidth = (int)(width / 10);
            bitmap = Bitmap.createScaledBitmap(bitmap, subExplodeWidth, subExplodeWidth, true);
            velocity.set(0, 0);
        }
    }

    /**
     * Reset position and velocity
     */
    @Override
    public void reset() {
        super.reset();
        setSubmarine();
        bitmap = BitmapFactory.decodeResource(res, submarineDrable);
        if (isExplode) {
            if (direction == Direction.LEFT_FACING) {
                spriteBounds.offsetTo(0, 150);
            }else {
                spriteBounds.offsetTo(width, 150);
            }
        }
        isExplode = false;
    }

    /**
     * Call move method
     */
    @Override
    public void tick() {
        this.move();
    }

    /**
     * To get the point
     * @return
     */
    @Override
    public int getPointValue() {
        if (isExplode) {
            if (spriteSize == Size.SMALL) {
                point = 150;
            }else if (spriteSize == Size.SMALL) {
                point = 40;
            }else {
                point = 25;
            }
        }
        return point;
    }
}
