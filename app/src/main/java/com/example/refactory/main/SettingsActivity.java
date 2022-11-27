package com.example.refactory.main;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.CheckBoxPreference;
import androidx.preference.ListPreference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;
import androidx.preference.PreferenceScreen;
import androidx.preference.SwitchPreference;

import com.example.refactory.R;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.settings_activity);
        if (b == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.settings, new SettingsFragment())
                    .commit();
        }
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    public static class SettingsFragment extends PreferenceFragmentCompat {

        public static final String PLAY_SOUNDTRACK = "PLAY_SOUNDTRACK";
        public static final String SILENT_MODE = "SILENT_MODE";
        public static final String DARK_MODE = "DARK_MODE";
        public static final String TIMER = "TIMER";
        public static final String RAPID_MISSILE = "RAPID_MISSILE";
        public static final String AIR_NUMBER = "AIR_NUMBER";
        public static final String SUB_NUMBER = "SUB_NUMBER";
        public static final String AIR_SPEED = "AIR_SPEED";
        public static final String SUB_SPEED = "SUB_SPEED";
        public static final String AIR_DIRECTION = "AIR_DIRECTION";
        public static final String SUB_DIRECTION = "SUB_DIRECTION";
        public static final String AIR_ALT = "AIR_ALT";
        public static final String SUB_ALT = "SUB_ALT";
        public static final String Frugality = "Frugality";

        @Override
        public void onCreatePreferences(Bundle b, String s) {

            Context context = getPreferenceManager().getContext();
            PreferenceScreen screen = getPreferenceManager().createPreferenceScreen(context);

            //Switch button for background music
            SwitchPreference musicPref = new SwitchPreference(context);
            musicPref.setTitle(R.string.background_music);
            musicPref.setSummaryOff(R.string.b_mute);
            musicPref.setSummaryOn(R.string.b_unmute);
            musicPref.setKey(PLAY_SOUNDTRACK);

            //Switch button for silent mode
            SwitchPreference silentPref = new SwitchPreference(context);
            silentPref.setTitle(R.string.silentMode);
            silentPref.setSummaryOff(R.string.s_on);
            silentPref.setSummaryOn(R.string.s_off);
            silentPref.setKey(SILENT_MODE);

            //Switch button for dark mode
            SwitchPreference darkModePref = new SwitchPreference(context);
            darkModePref.setTitle(R.string.darkMode);
            darkModePref.setSummaryOff(R.string.d_off);
            darkModePref.setSummaryOn(R.string.d_on);
            darkModePref.setKey(DARK_MODE);

            //Switch button for airplane altitude
            SwitchPreference airUpDownPref = new SwitchPreference(context);
            airUpDownPref.setTitle(R.string.airAltitude);
            airUpDownPref.setSummaryOff(R.string.a_off);
            airUpDownPref.setSummaryOn(R.string.a_on);
            airUpDownPref.setKey(AIR_ALT);

            //Switch button for submarine altitude
            SwitchPreference subUpDownPref = new SwitchPreference(context);
            subUpDownPref.setTitle(R.string.subAltitude);
            subUpDownPref.setSummaryOff(R.string.su_off);
            subUpDownPref.setSummaryOn(R.string.su_on);
            subUpDownPref.setKey(SUB_ALT);

            //Switch button for Frugality Mode
            SwitchPreference frugalityPref = new SwitchPreference(context);
            frugalityPref.setTitle(R.string.Frugality_Mode);
            frugalityPref.setSummaryOff(R.string.f_off);
            frugalityPref.setSummaryOn(R.string.f_on);
            frugalityPref.setKey(Frugality);

            //Selection list for different timer
            ListPreference timerPref = new ListPreference(context);
            timerPref.setTitle(R.string.timer);
            timerPref.setSummary(R.string.timer_summary);
            timerPref.setKey(TIMER);
            timerPref.setEntries(R.array.minute);
            timerPref.setEntryValues(R.array.user_minute);

            //Check button for Rapid fire
            CheckBoxPreference rapidMissilePref = new CheckBoxPreference(context);
            rapidMissilePref.setTitle(R.string.rapid_fire_title);
            rapidMissilePref.setSummaryOff(R.string.rapid_off);
            rapidMissilePref.setSummaryOn(R.string.rapid_on);
            rapidMissilePref.setKey(RAPID_MISSILE);

            //Selection list for airplane number
            ListPreference airNumPref = new ListPreference(context);
            airNumPref.setTitle(R.string.airNum_title);
            airNumPref.setSummary(R.string.airNum_summary);
            airNumPref.setKey(AIR_NUMBER);
            airNumPref.setEntries(R.array.Air_number);
            airNumPref.setEntryValues(R.array.user_Air_number);

            //Selection list for submarine number
            ListPreference subNumPref = new ListPreference(context);
            subNumPref.setTitle(R.string.subNum_title);
            subNumPref.setSummary(R.string.subNum_summary);
            subNumPref.setKey(SUB_NUMBER);
            subNumPref.setEntries(R.array.Sub_number);
            subNumPref.setEntryValues(R.array.user_Sub_number);

            //Selection list for airplane speed
            ListPreference airSpeedPref = new ListPreference(context);
            airSpeedPref.setTitle(R.string.airSpeed_title);
            airSpeedPref.setSummary(R.string.airSpeed_summary);
            airSpeedPref.setKey(AIR_SPEED);
            airSpeedPref.setEntries(R.array.Air_speed);
            airSpeedPref.setEntryValues(R.array.user_Air_seed);

            //Selection list for submarine speed
            ListPreference subSpeedPref = new ListPreference(context);
            subSpeedPref.setTitle(R.string.subSpeed_title);
            subSpeedPref.setSummary(R.string.subSpeed_summary);
            subSpeedPref.setKey(SUB_SPEED);
            subSpeedPref.setEntries(R.array.Sub_speed);
            subSpeedPref.setEntryValues(R.array.user_Sub_seed);

            //Selection list for airplane moving direction
            ListPreference airDirectionPref = new ListPreference(context);
            airDirectionPref.setTitle(R.string.airDirection_title);
            airDirectionPref.setSummary(R.string.airDirection_summary);
            airDirectionPref.setKey(AIR_DIRECTION);
            airDirectionPref.setEntries(R.array.Air_Direction);
            airDirectionPref.setEntryValues(R.array.user_Air_Direction);

            //Selection list for submarine moving direction
            ListPreference subDirectionPref = new ListPreference(context);
            subDirectionPref.setTitle(R.string.subDirection_title);
            subDirectionPref.setSummary(R.string.subDirection_summary);
            subDirectionPref.setKey(SUB_DIRECTION);
            subDirectionPref.setEntries(R.array.Sub_Direction);
            subDirectionPref.setEntryValues(R.array.user_Sub_Direction);

            screen.addPreference(musicPref);
            screen.addPreference(silentPref);
            screen.addPreference(darkModePref);
            screen.addPreference(timerPref);
            screen.addPreference(rapidMissilePref);
            screen.addPreference(airNumPref);
            screen.addPreference(subNumPref);
            screen.addPreference(airSpeedPref);
            screen.addPreference(subSpeedPref);
            screen.addPreference(airDirectionPref);
            screen.addPreference(subDirectionPref);
            screen.addPreference(airUpDownPref);
            screen.addPreference(subUpDownPref);
            screen.addPreference(frugalityPref);
            setPreferenceScreen(screen);
        }

        /**
         * Method for background music preference
         * @param c
         * @return
         */
        public static boolean soundFX(Context c) {
            return PreferenceManager.getDefaultSharedPreferences(c).getBoolean(PLAY_SOUNDTRACK, true);
        }

        /**
         * Method for silentMode preference
         * @param c
         * @return
         */
        public static boolean onSilentMode(Context c) {
            return PreferenceManager.getDefaultSharedPreferences(c).getBoolean(SILENT_MODE, true);
        }

        /**
         * Method for dark mode preference
         * @param c
         * @return
         */
        public static boolean onDarkMode(Context c) {
            return PreferenceManager.getDefaultSharedPreferences(c).getBoolean(DARK_MODE, true);
        }

        /**
         * Method for timer preference
         * @param c
         * @return
         */
        public static int setTimer(Context c) {
            String number = PreferenceManager.getDefaultSharedPreferences(c).getString(TIMER, "3");
            return Integer.parseInt(number);
        }

        /**
         * Method for rapid fire preference
         * @param c
         * @return
         */
        public static boolean controlFire(Context c) {
            return PreferenceManager.getDefaultSharedPreferences(c).getBoolean(RAPID_MISSILE, true);
        }

        /**
         * Method for number of airplane preference
         * @param c
         * @return
         */
        public static int AirNum(Context c) {
            String number = PreferenceManager.getDefaultSharedPreferences(c).getString(AIR_NUMBER, "3");
            return Integer.parseInt(number);
        }

        /**
         * Method for number of submarine preference
         * @param c
         * @return
         */
        public static int SubNum(Context c) {
            String number = PreferenceManager.getDefaultSharedPreferences(c).getString(SUB_NUMBER, "3");
            return Integer.parseInt(number);
        }

        /**
         * Method for number of airplane speed preference
         * @param c
         * @return
         */
        public static int controlAirSpeed(Context c) {
            String number = PreferenceManager.getDefaultSharedPreferences(c).getString(AIR_SPEED, "5");
            return Integer.parseInt(number);
        }

        /**
         * Method for number of submarine speed preference
         * @param c
         * @return
         */
        public static int controlSubSpeed(Context c) {
            String number = PreferenceManager.getDefaultSharedPreferences(c).getString(SUB_SPEED, "5");
            return Integer.parseInt(number);
        }

        /**
         * Method for number of airplane direction preference
         * @param c
         * @return
         */
        public static int controlAirDir(Context c) {
            String number = PreferenceManager.getDefaultSharedPreferences(c).getString(AIR_DIRECTION, "2");
            return Integer.parseInt(number);
        }

        /**
         * Method for number of submarine direction preference
         * @param c
         * @return
         */
        public static int controlSubDir(Context c) {
            String number = PreferenceManager.getDefaultSharedPreferences(c).getString(SUB_DIRECTION, "2");
            return Integer.parseInt(number);
        }

        /**
         * Method for number of airplane altitude preference
         * @param c
         * @return
         */
        public static boolean onAirAltitude(Context c) {
            return PreferenceManager.getDefaultSharedPreferences(c).getBoolean(AIR_ALT, true);
        }

        /**
         * Method for number of submarine altitude preference
         * @param c
         * @return
         */
        public static boolean onSubAltitude(Context c) {
            return PreferenceManager.getDefaultSharedPreferences(c).getBoolean(SUB_ALT, true);
        }

        /**
         * Method for number of frugality mode preference
         * @param c
         * @return
         */
        public static boolean onFrugality(Context c) {
            return PreferenceManager.getDefaultSharedPreferences(c).getBoolean(Frugality, true);
        }
    }
}