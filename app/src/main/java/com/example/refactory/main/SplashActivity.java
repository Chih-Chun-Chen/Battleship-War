package com.example.refactory.main;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.ImageView;

import com.example.refactory.R;

public class SplashActivity extends Activity {

    private ImageView homepageImage;

    @Override
    protected void onCreate(Bundle b) {
        super.onCreate(b);
        homepageImage = new ImageView(this);
        homepageImage.setImageResource(R.drawable.homepage);
        homepageImage.setScaleType(ImageView.ScaleType.FIT_XY);
        setContentView(homepageImage);
    }

    @Override
    public boolean onTouchEvent(MotionEvent m) {
        var w = homepageImage.getWidth();
        var h = homepageImage.getHeight();
        if (m.getAction() == MotionEvent.ACTION_DOWN) {
            var x = m.getX();
            var y = m.getY();
            if (x > w * 0.90236f && y < h * 0.10787f) {
                //User touched settings button
                //TODO launch a Preferences activity
                Intent tent = new Intent(this, SettingsActivity.class);
                startActivity(tent);
            } else if (x > w * 0.0167f && x < w * 0.1226f && y > h * 0.6881f && y < h * 0.7579f) {
                AlertDialog.Builder ab = new AlertDialog.Builder(this);
                    ab.setTitle(R.string.about_dialog_title)
                      .setMessage(R.string.about_dialog_message)
                      .setCancelable(false)
                      .setPositiveButton(R.string.about_dialog_button, null);
                AlertDialog box = ab.create();
                box.show();
            }
            else if (x > w * 0.2388f && x < w * 0.7648f && y > h * 0.878f && y < h * 0.9701f) {
                Intent tent = new Intent(this, MainActivity.class);
                startActivity(tent);
                finish();
            }
        }
        return true;
    }
}
