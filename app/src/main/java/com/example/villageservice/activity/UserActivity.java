package com.example.villageservice.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.villageservice.R;
import com.example.villageservice.fragment.CoveringLetterFragment;
import com.example.villageservice.fragment.HomeAdminFragment;
import com.example.villageservice.listener.FragmentListener;
import com.example.villageservice.model.KartuKeluarga;
import com.example.villageservice.utility.VSPreference;
import com.google.gson.Gson;

public class UserActivity extends AppCompatActivity implements FragmentListener {

    private KartuKeluarga kartuKeluarga;
    private Fragment fragment;

    public static final int FRAGMENT_FINISH_GOTO_CL = 1;
    public static final String TAG_FRAGMENT_CL = "cl_fragment";

    private FrameLayout bottomBar;
    private ImageView homeBar;
    private ImageView userBar;
    private ImageView notificationBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        kartuKeluarga = VSPreference.getInstance(getApplicationContext()).getKK();
        Log.d("XXXLOG", "onCreate - KK logged in: " + new Gson().toJson(kartuKeluarga));
        homeBar = findViewById(R.id.homeBar);
        userBar = findViewById(R.id.userBar);
        notificationBar = findViewById(R.id.notificationBar);
        goToHome();

    }

    @SuppressLint("NewApi")
    private void goToHome() {
        fragment = new HomeAdminFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, fragment, TAG_FRAGMENT_CL)
                .commit();
        homeBar.setImageDrawable(getApplicationContext().getDrawable(R.mipmap.ic_bar_home_on));
        userBar.setImageDrawable(getApplicationContext().getDrawable(R.mipmap.ic_bar_user_off));
        notificationBar.setImageDrawable(getApplicationContext().getDrawable(R.mipmap.ic_bar_notification_off));
    }

    @Override
    public void onFragmentFinish(Fragment currentFragment, int destination, boolean isForward) {

        int enter = R.anim.enter_from_right;
        int exit = R.anim.exit_to_left;

        if (!isForward) {
            enter = R.anim.enter_from_left;
            exit = R.anim.exit_to_right;
        }
        switch (destination) {
            case FRAGMENT_FINISH_GOTO_CL:
                fragment = new CoveringLetterFragment();
                getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(enter, exit)
                        .replace(R.id.container, fragment, TAG_FRAGMENT_CL)
                        .commit();
                break;
        }
    }

    @Override
    public void onFragmentCreated(Fragment currentFragment) {

    }

    @Override
    public void onFragmentPassingData(String coveringLetter) {

    }

    @Override
    public void onFragmentPaused() {

    }

    @Override
    public void onActivityFinish() {

    }

    @Override
    public void onActivityBackPressed() {

    }
}