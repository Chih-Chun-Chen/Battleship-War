package com.example.refactory.main;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;

import com.example.refactory.R;

public class MainActivity extends AppCompatActivity {

    private MediaPlayer soundtrack;
    private ComponentView componentView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        soundtrack = MediaPlayer.create(this, R.raw.talk_love);
        soundtrack.setLooping(true);
        componentView = new ComponentView(this);
        setContentView(componentView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        componentView.resume();
        if (SettingsActivity.SettingsFragment.soundFX(this)) {
            soundtrack.start();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        componentView.pause();
        if (SettingsActivity.SettingsFragment.soundFX(this)) {
            soundtrack.pause();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        soundtrack.release();
    }
}