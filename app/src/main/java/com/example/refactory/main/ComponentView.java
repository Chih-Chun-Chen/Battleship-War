package com.example.refactory.main;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.media.MediaPlayer;
import android.os.Build;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.RequiresApi;

import com.example.refactory.component.Airplane;
import com.example.refactory.R;
import com.example.refactory.component.DepthCharge;
import com.example.refactory.component.Direction;
import com.example.refactory.component.Missile;
import com.example.refactory.component.Submarine;
import com.example.refactory.component.Battleship;
import com.example.refactory.component.TickListener;
import com.example.refactory.component.Timer;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class ComponentView extends View implements TickListener {

    float width;
    float height;
    float depthChargeX;
    float depthChargeY;
    private PointF firePoisition;
    private Battleship battleship;
    private Bitmap water;
    private Bitmap fire;
    private DepthCharge depthCharges;
    private Missile leftMissile;
    private Missile rightMissile;
    private ArrayList<Airplane> airplanesList;
    private ArrayList<Submarine> submarineList;
    private ArrayList<Missile> missileList;
    private ArrayList<DepthCharge> depthChargeList;
    private ArrayList<DepthCharge> removeD;
    private ArrayList<Missile> removeM;
    private ArrayList<Bitmap>fireList;
    private ArrayList<Bitmap>removeF;
    private int higherScore;
    private int currentScore;
    private Paint paint;
    private Timer timer;
    private long timeNow;
    private long timeBefore;
    private int minute;
    private int firstDigitSecond;
    private int secondDigitSecond;
    private MediaPlayer airExplodeSound;
    private MediaPlayer subExplodeSound;
    private MediaPlayer leftGun;
    private MediaPlayer rightGun;
    private MediaPlayer depthSound;
    private int airplaneAmount;
    private int submarineAmount;
    private Boolean scale;
    protected boolean stopTick;



    /**
     * Constructor of ComponentView Class
     * @param context
     */
    public ComponentView(Context context) {
        super(context);
        readFile();
        water = BitmapFactory.decodeResource(getResources(), R.drawable.water);
        timer = new Timer();
        scale = false;
    }


    /**
     * This method is same as paintComponent
     * @param c
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onDraw(Canvas c) {
            width = c.getWidth();
            height = c.getHeight();

            if (scale == false) {
                paint = new Paint();
                battleship = new Battleship(getResources(), width);
                airplanesList = new ArrayList<>();
                submarineList = new ArrayList<>();
                depthChargeList = new ArrayList<>();
                removeD = new ArrayList<>();
                missileList = new ArrayList<>();
                removeM = new ArrayList<>();
                fireList = new ArrayList<>();
                removeF = new ArrayList<>();
                firePoisition = new PointF();
                airplaneAmount = SettingsActivity.SettingsFragment.AirNum(getContext());
                submarineAmount = SettingsActivity.SettingsFragment.SubNum(getContext());
                timeBefore = timeNow;
                minute = SettingsActivity.SettingsFragment.setTimer(getContext()) - 1;
                firstDigitSecond = 6;
                secondDigitSecond = 0;
                createSounds();

                for (int i = 0; i < airplaneAmount; i++) {
                    airplanesList.add(new Airplane(getContext(), width, height));
                }

                for (int i = 0; i < submarineAmount; i++) {
                    submarineList.add(new Submarine(getContext(), width, height));
                }

                //Register all the airplane
                for (int i = 0; i < airplaneAmount; i++) {
                    timer.register(airplanesList.get(i));
                }

                //Register all the submarine
                for (int i = 0; i < submarineAmount; i++) {
                    timer.register(submarineList.get(i));
                }

                //Position of sprite
                PointF battleshipPoint = new PointF();
                battleshipPoint.set(battleship.bitmap.getWidth(), battleship.bitmap.getHeight());

                depthChargeX = width / 2;
                depthChargeY = height / 2;

                battleship.setPosition((width - battleshipPoint.x) * 0.5f, (height - battleshipPoint.y) * 0.485f);

                for (Airplane ap : airplanesList) {
                    int RandomX = (int) (Math.random() * 100);
                    int RandomY = (int) (Math.random() * 100);
                    ap.setPosition(RandomX, RandomY);
                }

                for (Submarine sb : submarineList) {
                    int RandomX = (int) (Math.random() * 100);
                    int RandomY = (int) (Math.random() * 100 + height / 2);
                    sb.setPosition(RandomX, RandomY);
                }

                timer = new Timer();
                scale = true;
            }

            //Background color
            if (SettingsActivity.SettingsFragment.onDarkMode(getContext())) {
                c.drawColor(Color.BLACK);
            } else {
                c.drawColor(Color.CYAN);
            }

            //Print out highest score
            paint.setTextSize(60);
            paint.setColor(Color.BLUE);
            c.drawText(getResources().getString(R.string.highest_score) + higherScore, 0, height * 0.025f, paint);

            //Print out score
            paint.setColor(Color.rgb(0, 204, 0));
            c.drawText(getResources().getString(R.string.score) + currentScore, 0, height / 2, paint);

            //Print gaming time
            paint.setColor(Color.RED);
            c.drawText(getResources().getString(R.string.time) + minute + ":" + firstDigitSecond + secondDigitSecond, 0, height * 0.45f, paint);

            PointF waterPoint = new PointF();
            waterPoint.set(0, height / 2);
            while (waterPoint.x < width) {
                c.drawBitmap(water, waterPoint.x, waterPoint.y, null);
                waterPoint.x += water.getWidth();
            }

            //To draw sprite
            battleship.draw(c);

            for (int i = 0; i < airplaneAmount; i++) {
                airplanesList.get(i).draw(c);
            }

            for (int i = 0; i < submarineAmount; i++) {
                submarineList.get(i).draw(c);
            }

            //Section for handle ConcurrentModificationException
            for (var d : depthChargeList) {
                d.draw(c);
                removeD.add(d);
            }

            for (var r : removeD) {
                if (r.getBoundsTop() > height || r.isDethcharge()) {
                    depthChargeList.remove(r);
                    timer.deregister(r);
                }
            }

            for (var missile : missileList) {
                missile.draw(c);
                removeM.add(missile);
            }

            for (var r : removeM) {
                if (r.getBoundsBottom() < 0 || r.isMissile()) {
                    missileList.remove(r);
                    timer.deregister(r);
                }
            }

            for (var f : fireList) {
                c.drawBitmap(f, firePoisition.x, firePoisition.y, null);
                removeF.add(f);
            }

            for (var r : removeF) {
                fireList.remove(r);
            }

            if (stopTick == false) {
                tick();
            }
    }

    /**
     * To instantiate, set Poisition, and add to arrayList for leftMissile
     */
    private void createLeftMissile(){
        leftMissile = new Missile(Direction.LEFT_FACING);
        timer.register(leftMissile);
        leftMissile.setPosition(378.9624f, 898.9429f);
        missileList.add(leftMissile);
        if (SettingsActivity.SettingsFragment.onFrugality(getContext())) {
            currentScore--;
        }
    }

    /**
     * To instantiate, set Poisition, and add to arrayList for rightMissile
     */
    private void createRightMissile(){
        rightMissile = new Missile(Direction.RIGHT_FACING);
        timer.register(rightMissile);
        rightMissile.setPosition(694.97314f, 898.9429f);
        missileList.add(rightMissile);
        if (SettingsActivity.SettingsFragment.onFrugality(getContext())) {
            currentScore--;
        }
    }

    /**
     * To instantiate, set Poisition, and add to arrayList for depthCharges
     */
    private void createDepthCharge(){
        depthCharges = new DepthCharge(getResources(), width, height);
        timer.register(depthCharges);
        depthCharges.setPosition(depthChargeX, depthChargeY);
        depthChargeList.add(depthCharges);
        if (SettingsActivity.SettingsFragment.onFrugality(getContext())) {
            currentScore--;
        }
    }

    /**
     *
     * @param m To represent the mouse click action
     * @return boolean
     */
    @Override
    public boolean onTouchEvent(MotionEvent m){
        float x = m.getX();
        float y = m.getY();

        if (m.getAction() == MotionEvent.ACTION_DOWN) {
            if (x > 0 && x < width / 2 && y > 0 && y < height / 2) {
                if (SettingsActivity.SettingsFragment.onSilentMode(getContext())) {
                    leftGun.start();
                }
                //Rapid-fire missiles
                if (SettingsActivity.SettingsFragment.controlFire(getContext())) {
                    if (missileList.size() < 1) {
                        createLeftMissile();
                    }
                } else {
                    createLeftMissile();
                }
                fire = BitmapFactory.decodeResource(getResources(), R.drawable.star);
                fireList.add(fire);
                firePoisition.set(377.96387f, 898.9429f);
            }
        }

        if (m.getAction() == MotionEvent.ACTION_DOWN) {
            if (x > width / 2 && x < width && y > 0 && y < height / 2) {
                if (SettingsActivity.SettingsFragment.onSilentMode(getContext())) {
                    rightGun.start();
                }
                if (SettingsActivity.SettingsFragment.controlFire(getContext())) {
                    if (missileList.size() < 1) {
                        createRightMissile();
                    }
                } else {
                    createRightMissile();
                }
                fire = BitmapFactory.decodeResource(getResources(), R.drawable.star);
                fireList.add(fire);
                firePoisition.set(694.97314f, 892.9132f);
            }
        }

        if (m.getAction() == MotionEvent.ACTION_DOWN) {
            if (x > 0 && x < width && y > height / 2 && y < height) {
                if (SettingsActivity.SettingsFragment.controlFire(getContext())) {
                    if (depthChargeList.size() < 1) {
                        createDepthCharge();
                        if (SettingsActivity.SettingsFragment.onSilentMode(getContext())) {
                            depthSound.start();
                            depthSound.setLooping(true);
                        }
                    }
                } else {
                    createDepthCharge();
                    if (SettingsActivity.SettingsFragment.onSilentMode(getContext())) {
                        depthSound.start();
                        depthSound.setLooping(true);
                    }
                }
            }
        }

        return true;
    }

    /**
     * Refresh in every moment
     */
    @Override
    public void tick() {
        detectCollisions();
        checkTimer();
        invalidate();
    }

    /**
     * Test the explosion when enemy is hit
     */
    //Check if missile and airplane overlap each other
    public void detectCollisions() {
        for (Missile ms : missileList) {
            for (Airplane ap : airplanesList) {
                if (ap.overlaps(ms)) {
                    if (SettingsActivity.SettingsFragment.onSilentMode(getContext())) {
                        airExplodeSound.start();
                    }
                    ap.explode();
                    ms.setMissile();
                    removeM.add(ms);
                    currentScore += ap.getPointValue();
                }
            }
        }

        //Check if depthCharges and submarine overlap each other
        for (DepthCharge dc : depthChargeList) {
            for (Submarine sb : submarineList) {
                if (sb.overlaps(dc)) {
                    if (SettingsActivity.SettingsFragment.onSilentMode(getContext())) {
                        subExplodeSound.start();
                    }
                    sb.explode();
                    dc.setDethcharge();
                    removeD.add(dc);
                    currentScore += sb.getPointValue();
                }
            }
        }
    }

    private void checkTimer() {
        timeNow = System.currentTimeMillis();
        if (timeNow - timeBefore > 1000) {
            depthSound.setLooping(false);
            secondDigitSecond--;
            if (secondDigitSecond < 0) {
                firstDigitSecond--;
                secondDigitSecond = 9;
            }
            if (firstDigitSecond < 0) {
                minute--;
                firstDigitSecond = 5;
            }
            timeBefore = timeNow;
        }

        if (minute == 0 && firstDigitSecond == 0 && secondDigitSecond == 0) {
            //Pop up dialog when time is up
            stopTick = true;
            AlertDialog.Builder ab = new AlertDialog.Builder(getContext());
                    ab.setTitle(R.string.over_dialog_title);
                    //Print something special if user got the higher score
                    if (currentScore <= higherScore) {
                        ab.setMessage(R.string.over_dialog_message);
                    }else{
                        ab.setMessage(R.string.over_dialog_remessage1 + currentScore + R.string.over_dialog_remessage2);
                    }
                    ab.setCancelable(false)
                    //Method parameter with Anonymous Class
                    .setPositiveButton(R.string.over_dialog_positive_button, new  DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface d, int i) {
                            restartTheGame();
                        }
                    })
                    //Method parameter with Lambda Expression
                    .setNegativeButton(R.string.over_dialog_negative_button, (d, i) -> ((Activity)getContext()).finish());
            AlertDialog box = ab.create();
            box.show();
            if (currentScore > higherScore) {
                higherScore = currentScore;
                writeFile();
            }
        }
    }

    private void restartTheGame() {
        minute = SettingsActivity.SettingsFragment.setTimer(getContext()) - 1;
        firstDigitSecond = 6;
        secondDigitSecond = 0;
        currentScore = 0;
        stopTick = false;
        invalidate();
    }

    private void readFile() {
        try {
            var inputFile = getContext().openFileInput("score.txt");
            Scanner s = new Scanner(inputFile);
            higherScore = s.nextInt();
            s.close();
        } catch (FileNotFoundException e) {
            currentScore = 0;
        }
    }

    private void writeFile() {
        try {
            var outputFile = getContext().openFileOutput("score.txt", Context.MODE_PRIVATE);
            outputFile.write(("" + higherScore).getBytes());
            outputFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createSounds() {
        airExplodeSound = MediaPlayer.create(getContext(), R.raw.airplaneexplode);
        subExplodeSound = MediaPlayer.create(getContext(), R.raw.submarineexplode);
        leftGun = MediaPlayer.create(getContext(), R.raw.leftmissile);
        rightGun = MediaPlayer.create(getContext(), R.raw.rightmissile);
        depthSound = MediaPlayer.create(getContext(), R.raw.depth_charge);
    }

    protected void resume() {
        stopTick = false;
    }

    protected void pause() {
        stopTick = true;
    }
}
