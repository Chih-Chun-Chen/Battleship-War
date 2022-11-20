package com.example.refactory.component;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.refactory.R;
import com.example.refactory.main.SettingsActivity;

public class Airplane extends Enemy {

    int point;
    int randomSpeedTime;
    Resources res;
    Context context;
    /**
     * Constructor of Airplane
     * @param context
     * @param width To pass width from ComponentView to Airplane class
     * @param height To pass height from ComponentView to Airplane class
     */
    public Airplane(Context context, float width, float height){
        super();
        this.context = context;
        res = context.getResources();
        super.width = width;
        super.height = height;
        setAirplane();
        bitmap = BitmapFactory.decodeResource(res, airplaneDrable);
        int airplaneWidth = (int)(width / airplaneScale);
        bitmap = Bitmap.createScaledBitmap(bitmap, airplaneWidth, airplaneWidth, true);
        spriteBounds.set(0, 0, airplaneWidth, airplaneWidth);
        spriteRandomFacing = SettingsActivity.SettingsFragment.controlAirDir(context);
        reset();

    }

    private void setAirplane() {
        switch (randomSize()){
            case SMALL : {
                airplaneScale = 20;
                if (direction == Direction.LEFT_FACING) {
                    airplaneDrable = R.drawable.little_airplane;
                }else {
                    airplaneDrable = R.drawable.little_airplane_flip;
                }
                break;
            }
            case MEDIUM : {
                airplaneScale = 13;
                if (direction == Direction.LEFT_FACING) {
                    airplaneDrable = R.drawable.medium_airplane;
                }else {
                    airplaneDrable = R.drawable.medium_airplane_flip;
                }
                break;
            }
            case BIG : {
                airplaneScale = 8;
                if (direction == Direction.LEFT_FACING) {
                    airplaneDrable = R.drawable.big_airplane;
                }else {
                    airplaneDrable = R.drawable.big_airplane_flip;
                }
                break;
            }
        }
    }

    /**
     *Airplane override method
     */
    @Override
    public void move(){
        randomY = (float) (Math.random() * (height / 2) - (height / 2  * 0.2) + height * 0.00926f);
        super.move();
        int RandomY = (int) (Math.random() * 100);
        randomSpeedTime = (int) (Math.random() * 10);

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
                velocity.x = - SettingsActivity.SettingsFragment.controlAirSpeed(context);
            } else {
                velocity.x = SettingsActivity.SettingsFragment.controlAirSpeed(context);
            }

        }

        if (spriteBounds.right < 0 && direction == Direction.LEFT_FACING){
           reset();
        }

        if (spriteBounds.left > width && direction == Direction.RIGHT_FACING) {
            reset();
        }

        if (SettingsActivity.SettingsFragment.onAirAltitude(context)) {
            if (RandomY < 1 || spriteBounds.top < 0) {
                velocity.y *= -1;
            } else if (RandomY > 99 || spriteBounds.bottom > height / 2 - height / 2 * 0.2) {
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
            bitmap = BitmapFactory.decodeResource(res, R.drawable.airplane_explosion);
            int airExplodeWidth = (int)(width / 10);
            bitmap = Bitmap.createScaledBitmap(bitmap, airExplodeWidth, airExplodeWidth, true);
            velocity.set(0, 0);
        }
    }

    /**
     * Reset position and velocity
     */
    @Override
    public void reset() {
        super.reset();
        int RandomY = (int) (Math.random() * 100);
        setAirplane();
        bitmap = BitmapFactory.decodeResource(res, airplaneDrable);
        if (isExplode) {
            if (direction == Direction.LEFT_FACING) {
                spriteBounds.offsetTo(width, RandomY);
            }else {
                spriteBounds.offsetTo(0, RandomY);
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
                point = 75;
            }else if (spriteSize == Size.SMALL) {
                point = 20;
            }else {
                point = 15;
            }
        }
        return point;
    }
}
