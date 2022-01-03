package com.example.villageservice.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.villageservice.R;
import com.example.villageservice.fragment.CitizenFragment;
import com.example.villageservice.fragment.CoveringLetterFragment;
import com.example.villageservice.fragment.HomeAdminFragment;
import com.example.villageservice.fragment.HomeUserFragment;
import com.example.villageservice.fragment.NotificationsFragment;
import com.example.villageservice.listener.FragmentListener;
import com.example.villageservice.model.KartuKeluarga;
import com.example.villageservice.utility.VSPreference;
import com.google.gson.Gson;

public class UserActivity extends AppCompatActivity implements FragmentListener {

    private KartuKeluarga kartuKeluarga;
    private Fragment fragment;

    public static final int FRAGMENT_FINISH_GOTO_HOME = 1;
    public static final int FRAGMENT_FINISH_GOTO_PROFILE = 2;
    public static final int FRAGMENT_FINISH_GOTO_NOTIFICATION = 3;

    public static final int FRAGMENT_FINISH_GOTO_CL = 4;


    public static final String TAG_FRAGMENT_HOME = "home_fragment";
    public static final String TAG_FRAGMENT_PROFILE = "profile_fragment";
    public static final String TAG_FRAGMENT_NOTIFICATIONS = "notifications_fragment";

    public static final String TAG_FRAGMENT_CL = "cl_fragment";

    private FrameLayout bottomBar;
    private ImageView homeIcon;
    private ImageView profileIcon;
    private ImageView notificationIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        kartuKeluarga = VSPreference.getInstance(getApplicationContext()).getKK();
        Log.d("XXXLOG", "onCreate - KK logged in: " + new Gson().toJson(kartuKeluarga));
        homeIcon = findViewById(R.id.homeIcon);
        profileIcon = findViewById(R.id.citizenIcon);
        notificationIcon = findViewById(R.id.notificationIcon);
        goToHome();

    }

    @SuppressLint("NewApi")
    private void goToHome() {
        fragment = new HomeUserFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, fragment, TAG_FRAGMENT_CL)
                .commit();
        homeIcon.setImageDrawable(getApplicationContext().getDrawable(R.mipmap.ic_home_on));
        profileIcon.setImageDrawable(getApplicationContext().getDrawable(R.mipmap.ic_profile_off));
        notificationIcon.setImageDrawable(getApplicationContext().getDrawable(R.mipmap.ic_notification_off));
    }

    @SuppressLint("NewApi")
    private void goToProfile() {
        fragment = new CitizenFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, fragment, TAG_FRAGMENT_PROFILE)
                .commit();
        homeIcon.setImageDrawable(getApplicationContext().getDrawable(R.mipmap.ic_home_off));
        profileIcon.setImageDrawable(getApplicationContext().getDrawable(R.mipmap.ic_profile_on));
        notificationIcon.setImageDrawable(getApplicationContext().getDrawable(R.mipmap.ic_notification_off));
    }

    @SuppressLint("NewApi")
    private void goToNotifications() {
        fragment = new NotificationsFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, fragment, TAG_FRAGMENT_NOTIFICATIONS)
                .commit();
        homeIcon.setImageDrawable(getApplicationContext().getDrawable(R.mipmap.ic_home_off));
        profileIcon.setImageDrawable(getApplicationContext().getDrawable(R.mipmap.ic_profile_off));
        notificationIcon.setImageDrawable(getApplicationContext().getDrawable(R.mipmap.ic_notification_on));
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