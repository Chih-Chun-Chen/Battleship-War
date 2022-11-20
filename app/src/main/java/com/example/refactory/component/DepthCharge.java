package com.example.refactory.component;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.refactory.R;

public class DepthCharge extends Sprite{

    boolean isDethchargeeHit;
    /**
     * Constructor of DepthCharge
     * @param res
     * @param width To pass width from ComponentView to DepthCharge class
     * @param height To pass height from ComponentView to DepthCharge class
     */
    public DepthCharge(Resources res, float width, float height) {
        super();
        super.width = width;
        super.height = height;
        bitmap = BitmapFactory.decodeResource(res, R.drawable.depth_charge);
        int depthChargeWidth = (int)(width / 25);
        bitmap = Bitmap.createScaledBitmap(bitmap, depthChargeWidth, depthChargeWidth, true);
        spriteBounds.set(spriteBounds.left, spriteBounds.top, depthChargeWidth, depthChargeWidth);
        velocity.set(0, 10);
    }

    /**
     * To get DepthCharge's top
     * @return float value of spriteBounds.top
     */
    public float getBoundsTop(){
        return spriteBounds.top;
    }

    /**
     * Override tick method
     */
    @Override
    public void tick() {
        super.move();
    }

    /**
     * Set to true if sub and depthCharge is overlapped
     */
    public void setDethcharge() {
        isDethchargeeHit = true;
    }

    /**
     * Return boolean value
     * @return
     */
    public boolean isDethcharge() {
        return isDethchargeeHit;
    }
}
