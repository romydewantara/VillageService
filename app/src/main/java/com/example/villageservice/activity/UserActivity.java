package com.example.villageservice.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.villageservice.R;
import com.example.villageservice.fragment.CoveringLetterFragment;
import com.example.villageservice.fragment.FormListFragment;
import com.example.villageservice.fragment.HomeAdminFragment;
import com.example.villageservice.fragment.HomeUserFragment;
import com.example.villageservice.fragment.NotificationsFragment;
import com.example.villageservice.fragment.PdfViewerFragment;
import com.example.villageservice.fragment.ProfileFragment;
import com.example.villageservice.listener.FragmentListener;

public class UserActivity extends AppCompatActivity implements FragmentListener {

    private Fragment fragment;

    public static final int FRAGMENT_FINISH_GOTO_HOME = 1;
    public static final int FRAGMENT_FINISH_GOTO_PROFILE = 2;
    public static final int FRAGMENT_FINISH_GOTO_NOTIFICATION = 3;
    public static final int FRAGMENT_FINISH_GOTO_CL = 4;


    public static final String TAG_FRAGMENT_HOME_USER = "home_user_fragment";
    public static final String TAG_FRAGMENT_PROFILE = "profile_fragment";
    public static final String TAG_FRAGMENT_NOTIFICATIONS = "notifications_fragment";

    public static final String TAG_FRAGMENT_CL = "cl_fragment";

    private FrameLayout bottomBar;
    private LinearLayout homeMenu;
    private LinearLayout profileMenu;
    private LinearLayout notificationMenu;
    private ImageView homeIcon;
    private ImageView profileIcon;
    private ImageView notificationIcon;

    private String menuSelected = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        initMandatory();
        initListener();
        goToHome();
    }

    private void initMandatory() {
        homeMenu = findViewById(R.id.homeMenu);
        profileMenu = findViewById(R.id.citizenMenu);
        notificationMenu = findViewById(R.id.notificationMenu);
        homeIcon = findViewById(R.id.homeIcon);
        profileIcon = findViewById(R.id.citizenIcon);
        notificationIcon = findViewById(R.id.notificationIcon);
        bottomBar = findViewById(R.id.bottomBar);
    }

    private void initListener() {
        homeMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToHome();
            }
        });
        profileMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToProfile();
            }
        });
        notificationMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToNotifications();
            }
        });
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
        fragment = new ProfileFragment();
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
            case FRAGMENT_FINISH_GOTO_HOME:
                fragment = new HomeUserFragment();
                getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(enter, exit)
                        .replace(R.id.container, fragment, TAG_FRAGMENT_HOME_USER)
                        .commit();
                break;
            case FRAGMENT_FINISH_GOTO_PROFILE:
                fragment = new ProfileFragment();
                getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(enter, exit)
                        .replace(R.id.container, fragment, TAG_FRAGMENT_PROFILE)
                        .commit();
                break;
            case FRAGMENT_FINISH_GOTO_NOTIFICATION:
                fragment = new NotificationsFragment();
                getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(enter, exit)
                        .replace(R.id.container, fragment, TAG_FRAGMENT_NOTIFICATIONS)
                        .commit();
                break;
            case FRAGMENT_FINISH_GOTO_CL:
                fragment = new CoveringLetterFragment();
                Bundle bundle = new Bundle();
                bundle.putString("menu", menuSelected);
                fragment.setArguments(bundle);
                getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(enter, exit)
                        .replace(R.id.container, fragment, TAG_FRAGMENT_CL)
                        .commit();
                break;
        }
    }

    @SuppressLint("NewApi")
    @Override
    public void onFragmentCreated(Fragment currentFragment) {
        if (currentFragment instanceof HomeUserFragment) {
            homeIcon.setImageDrawable(getApplicationContext().getDrawable(R.mipmap.ic_home_on));
            profileIcon.setImageDrawable(getApplicationContext().getDrawable(R.mipmap.ic_profile_off));
            notificationIcon.setImageDrawable(getApplicationContext().getDrawable(R.mipmap.ic_notification_off));
            bottomBar.setVisibility(View.VISIBLE);
        } else if (currentFragment instanceof ProfileFragment) {
            homeIcon.setImageDrawable(getApplicationContext().getDrawable(R.mipmap.ic_home_off));
            profileIcon.setImageDrawable(getApplicationContext().getDrawable(R.mipmap.ic_profile_on));
            notificationIcon.setImageDrawable(getApplicationContext().getDrawable(R.mipmap.ic_notification_off));
            bottomBar.setVisibility(View.VISIBLE);
        } else if (currentFragment instanceof NotificationsFragment) {
            homeIcon.setImageDrawable(getApplicationContext().getDrawable(R.mipmap.ic_home_off));
            profileIcon.setImageDrawable(getApplicationContext().getDrawable(R.mipmap.ic_profile_off));
            notificationIcon.setImageDrawable(getApplicationContext().getDrawable(R.mipmap.ic_notification_on));
            bottomBar.setVisibility(View.VISIBLE);
        } else {
            bottomBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void onFragmentPassingData(String coveringLetter) {
        menuSelected = coveringLetter;
    }

    @Override
    public void onFragmentPaused() {

    }

    @Override
    public void onActivityFinish() {
        finish();
    }

    @Override
    public void onActivityBackPressed() {

    }

    @Override
    public void onBackPressed() {
        if (fragment instanceof HomeAdminFragment) {
            finish();
        } else if (fragment instanceof FormListFragment) {
            onFragmentFinish(fragment, FRAGMENT_FINISH_GOTO_HOME, false);
        } else if (fragment instanceof ProfileFragment) {
            onFragmentFinish(fragment, FRAGMENT_FINISH_GOTO_HOME, false);
        } else if (fragment instanceof NotificationsFragment) {
            onFragmentFinish(fragment, FRAGMENT_FINISH_GOTO_HOME, false);
        } else if (fragment instanceof PdfViewerFragment) {
            onFragmentFinish(fragment, FRAGMENT_FINISH_GOTO_HOME, false);
        } else if (fragment instanceof CoveringLetterFragment) {
            onFragmentFinish(fragment, FRAGMENT_FINISH_GOTO_HOME, false);
        } else {
            super.onBackPressed();
        }
    }
}