package com.example.villageservice.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;

import com.example.villageservice.R;
import com.example.villageservice.fragment.CoveringLetterFragment;
import com.example.villageservice.fragment.EntryFragment;
import com.example.villageservice.fragment.FormListFragment;
import com.example.villageservice.fragment.HomeAdminFragment;
import com.example.villageservice.fragment.HomeUserFragment;
import com.example.villageservice.fragment.NotificationsFragment;
import com.example.villageservice.fragment.PdfViewerFragment;
import com.example.villageservice.fragment.ProfileFragment;
import com.example.villageservice.listener.FragmentListener;
import com.example.villageservice.utility.ConstantVariable;
import com.example.villageservice.utility.VSPreference;

public class UserActivity extends AppCompatActivity implements FragmentListener {

    private Fragment fragment;

    public static final int FRAGMENT_FINISH_GOTO_HOME = 1;
    public static final int FRAGMENT_FINISH_GOTO_PROFILE = 2;
    public static final int FRAGMENT_FINISH_GOTO_NOTIFICATION = 3;
    public static final int FRAGMENT_FINISH_GOTO_ENTRY = 4;
    public static final int FRAGMENT_FINISH_GOTO_CL = 5;
    public static final int FRAGMENT_FINISH_GOTO_PDF_VIEWER = 6;


    public static final String TAG_FRAGMENT_HOME_USER = "home_user_fragment";
    public static final String TAG_FRAGMENT_PROFILE = "profile_fragment";
    public static final String TAG_FRAGMENT_NOTIFICATIONS = "notifications_fragment";
    public static final String TAG_FRAGMENT_ENTRY = "entry_fragment";
    public static final String TAG_FRAGMENT_CL = "cl_fragment";
    public static final String TAG_FRAGMENT_PDF_VIEWER = "pdf_viewer_fragment";


    private FrameLayout bottomBar;
    private LinearLayout homeMenu;
    private LinearLayout profileMenu;
    private LinearLayout notificationMenu;
    private ImageView homeIcon;
    private ImageView profileIcon;
    private ImageView notificationIcon;
    private AppCompatTextView tvCitizen;

    private String menuSelected = "";
    private Bundle bundle;
    Animation slideUp;
    Animation slideDown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        initMandatory();
        initListener();
        goToHome();
    }

    private void initMandatory() {
        slideUp = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_up);
        slideDown = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_down);
        homeMenu = findViewById(R.id.homeMenu);
        profileMenu = findViewById(R.id.citizenMenu);
        notificationMenu = findViewById(R.id.notificationMenu);
        homeIcon = findViewById(R.id.homeIcon);
        profileIcon = findViewById(R.id.citizenIcon);
        notificationIcon = findViewById(R.id.notificationIcon);
        bottomBar = findViewById(R.id.bottomBar);
        tvCitizen = findViewById(R.id.tvCitizen);
        tvCitizen.setText(getApplicationContext().getResources().getString(R.string.profile_menu));

    }

    private void initListener() {
        homeMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fragment.getTag() != null && !fragment.getTag().equalsIgnoreCase(TAG_FRAGMENT_HOME_USER)) {
                    goToHome();
                }
            }
        });
        profileMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fragment.getTag() != null && !fragment.getTag().equalsIgnoreCase(TAG_FRAGMENT_PROFILE)) {
                    goToProfile();
                }
            }
        });
        notificationMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fragment.getTag() != null && !fragment.getTag().equalsIgnoreCase(TAG_FRAGMENT_NOTIFICATIONS)) {
                    goToNotifications();
                }
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
                ((HomeUserFragment) fragment).addPreviousFragmentTag(currentFragment.getTag());
                getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(enter, exit)
                        .replace(R.id.container, fragment, TAG_FRAGMENT_HOME_USER)
                        .commit();
                break;
            case FRAGMENT_FINISH_GOTO_PROFILE:
                fragment = new ProfileFragment();
                ((ProfileFragment) fragment).addPreviousFragmentTag(currentFragment.getTag());
                getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(enter, exit)
                        .replace(R.id.container, fragment, TAG_FRAGMENT_PROFILE)
                        .commit();
                break;
            case FRAGMENT_FINISH_GOTO_NOTIFICATION:
                fragment = new NotificationsFragment();
                ((NotificationsFragment) fragment).addPreviousFragmentTag(currentFragment.getTag());
                getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(enter, exit)
                        .replace(R.id.container, fragment, TAG_FRAGMENT_NOTIFICATIONS)
                        .commit();
                break;
            case FRAGMENT_FINISH_GOTO_ENTRY:
                fragment = new EntryFragment();
                ((EntryFragment) fragment).addPreviousFragmentTag(currentFragment.getTag());
                bundle = new Bundle();
                bundle.putString(ConstantVariable.KEY_CL_BUNDLE, menuSelected);
                fragment.setArguments(bundle);
                getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(enter, exit)
                        .replace(R.id.container, fragment, TAG_FRAGMENT_ENTRY)
                        .commit();
                break;
            case FRAGMENT_FINISH_GOTO_CL:
                fragment = new CoveringLetterFragment();
                ((CoveringLetterFragment) fragment).addPreviousFragmentTag(currentFragment.getTag());
                bundle = new Bundle();
                bundle.putString(ConstantVariable.KEY_CL_BUNDLE, menuSelected);
                fragment.setArguments(bundle);
                getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(enter, exit)
                        .replace(R.id.container, fragment, TAG_FRAGMENT_CL)
                        .commit();
                break;
            case FRAGMENT_FINISH_GOTO_PDF_VIEWER:
                fragment = new PdfViewerFragment();
                ((PdfViewerFragment) fragment).addPreviousFragmentTag(currentFragment.getTag());
                bundle = new Bundle();
                bundle.putString(ConstantVariable.KEY_CL_BUNDLE, menuSelected);
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
    public void onFragmentCreated(Fragment currentFragment, String previousFragment) {
        if (currentFragment instanceof HomeUserFragment) {
            homeIcon.setImageDrawable(getApplicationContext().getDrawable(R.mipmap.ic_home_on));
            profileIcon.setImageDrawable(getApplicationContext().getDrawable(R.mipmap.ic_profile_off));
            notificationIcon.setImageDrawable(getApplicationContext().getDrawable(R.mipmap.ic_notification_off));
            bottomBar.setVisibility(View.VISIBLE);
            if (previousFragment.equalsIgnoreCase(TAG_FRAGMENT_ENTRY)) {
                bottomBar.startAnimation(slideUp);
            }
        } else if (currentFragment instanceof ProfileFragment) {
            homeIcon.setImageDrawable(getApplicationContext().getDrawable(R.mipmap.ic_home_off));
            profileIcon.setImageDrawable(getApplicationContext().getDrawable(R.mipmap.ic_profile_on));
            notificationIcon.setImageDrawable(getApplicationContext().getDrawable(R.mipmap.ic_notification_off));
            bottomBar.setVisibility(View.VISIBLE);
            if (previousFragment.equalsIgnoreCase(TAG_FRAGMENT_ENTRY)) {
                bottomBar.startAnimation(slideUp);
            }
        } else if (currentFragment instanceof NotificationsFragment) {
            homeIcon.setImageDrawable(getApplicationContext().getDrawable(R.mipmap.ic_home_off));
            profileIcon.setImageDrawable(getApplicationContext().getDrawable(R.mipmap.ic_profile_off));
            notificationIcon.setImageDrawable(getApplicationContext().getDrawable(R.mipmap.ic_notification_on));
            bottomBar.setVisibility(View.VISIBLE);
            if (previousFragment.equalsIgnoreCase(TAG_FRAGMENT_ENTRY)) {
                bottomBar.startAnimation(slideUp);
            }
        } else {
            bottomBar.setVisibility(View.GONE);
            bottomBar.startAnimation(slideDown);
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
        } else if (fragment instanceof EntryFragment) {
            onFragmentFinish(fragment, FRAGMENT_FINISH_GOTO_HOME, false);
        } else if (fragment instanceof PdfViewerFragment) {
            //TODO: go to list fragment
            onFragmentFinish(fragment, FRAGMENT_FINISH_GOTO_ENTRY, false);
        } else if (fragment instanceof CoveringLetterFragment) {
            onFragmentFinish(fragment, FRAGMENT_FINISH_GOTO_ENTRY, false);
        } else {
            super.onBackPressed();
        }
    }
}