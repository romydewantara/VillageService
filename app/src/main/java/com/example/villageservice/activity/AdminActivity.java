package com.example.villageservice.activity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;

import com.example.villageservice.R;
import com.example.villageservice.fragment.AdminProfileFragment;
import com.example.villageservice.fragment.CitizenFragment;
import com.example.villageservice.fragment.CitizenListFragment;
import com.example.villageservice.fragment.FormListFragment;
import com.example.villageservice.fragment.HomeAdminFragment;
import com.example.villageservice.fragment.InputNewUsersFragment;
import com.example.villageservice.fragment.NotificationsFragment;
import com.example.villageservice.fragment.PdfViewerFragment;
import com.example.villageservice.listener.FragmentListener;
import com.example.villageservice.model.KartuKeluarga;
import com.example.villageservice.model.User;
import com.example.villageservice.model.KartuKeluargaList;
import com.example.villageservice.utility.AppUtil;
import com.example.villageservice.utility.ConstantVariable;
import com.example.villageservice.utility.VSPreference;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class AdminActivity extends AppCompatActivity implements FragmentListener {

    private FrameLayout bottomBar;

    private LinearLayout homeMenu;
    private LinearLayout citizenMenu;
    private LinearLayout notificationMenu;

    private ImageView homeIcon;
    private ImageView citizenIcon;
    private ImageView notificationIcon;
    private AppCompatTextView tvHome;
    private AppCompatTextView tvCitizen;
    private AppCompatTextView tvNotification;

    private Fragment fragment;
    private Fragment previousFragment;
    private String coveringLetterType = "";

    Animation slideUp;
    Animation slideDown;

    public static final int FRAGMENT_FINISH_GOTO_HOME_ADMIN = 0;
    public static final int FRAGMENT_FINISH_GOTO_FORM_LIST = 1;
    public static final int FRAGMENT_FINISH_GOTO_PDF_VIEWER = 2;
    public static final int FRAGMENT_FINISH_GOTO_CITIZENS = 3;
    public static final int FRAGMENT_FINISH_GOTO_INPUT_USER = 4;
    public static final int FRAGMENT_FINISH_GOTO_NOTIFICATION = 6;
    public static final int FRAGMENT_FINISH_GOTO_ADMIN_PROFILE = 7;
    public static final int FRAGMENT_FINISH_GOTO_CITIZEN_LIST = 8;

    public static final String TAG_FRAGMENT_HOME_ADMIN = "home_admin_fragment";
    public static final String TAG_FRAGMENT_FORM_LIST = "form_list_fragment";
    public static final String TAG_FRAGMENT_PDF_VIEWER = "pdf_viewer_fragment";
    public static final String TAG_FRAGMENT_CITIZENS = "citizens_fragment";
    public static final String TAG_FRAGMENT_INPUT_USER = "input_user_fragment";
    public static final String TAG_FRAGMENT_NOTIFICATIONS = "notifications_fragment";
    public static final String TAG_FRAGMENT_ADMIN_PROFILE = "admin_profile_fragment";
    public static final String TAG_FRAGMENT_CITIZEN_LIST = "citizen_list_fragment";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_admin);

        initMandatory();
        initListener();
        goToHome();
    }

    private void initMandatory() {
        slideUp = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_up);
        slideDown = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_down);
        bottomBar = findViewById(R.id.bottomBar);
        homeIcon = findViewById(R.id.homeIcon);
        citizenIcon = findViewById(R.id.citizenIcon);
        notificationIcon = findViewById(R.id.notificationIcon);
        tvHome = findViewById(R.id.tvHome);
        tvCitizen = findViewById(R.id.tvCitizen);
        tvNotification = findViewById(R.id.tvNotification);

        homeMenu = findViewById(R.id.homeMenu);
        citizenMenu = findViewById(R.id.citizenMenu);
        notificationMenu = findViewById(R.id.notificationMenu);
    }

    private void goToHome() {
        fragment = new HomeAdminFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, fragment, TAG_FRAGMENT_HOME_ADMIN)
                .commit();
        setBottomBar(TAG_FRAGMENT_HOME_ADMIN);
    }

    private void goToCitizens() {
        fragment = new CitizenFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, fragment, TAG_FRAGMENT_CITIZENS)
                .commit();
        setBottomBar(TAG_FRAGMENT_CITIZENS);
    }

    private void goToNotifications() {
        fragment = new NotificationsFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, fragment, TAG_FRAGMENT_NOTIFICATIONS)
                .commit();
        setBottomBar(TAG_FRAGMENT_NOTIFICATIONS);
    }

    private void initListener() {
        homeMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fragment.getTag() != null && !fragment.getTag().equalsIgnoreCase(TAG_FRAGMENT_HOME_ADMIN)) {
                    goToHome();
                }
            }
        });
        citizenMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fragment.getTag() != null && !fragment.getTag().equalsIgnoreCase(TAG_FRAGMENT_CITIZENS)) {
                    goToCitizens();
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

    @SuppressLint({"NewApi", "UseCompatLoadingForDrawables"})
    private void setBottomBar(String tag) {
        switch (tag) {
            case TAG_FRAGMENT_HOME_ADMIN:
                homeIcon.setImageDrawable(getApplicationContext().getDrawable(R.mipmap.ic_home_on));
                citizenIcon.setImageDrawable(getApplicationContext().getDrawable(R.mipmap.ic_citizen_off));
                notificationIcon.setImageDrawable(getApplicationContext().getDrawable(R.mipmap.ic_notification_off));
                tvHome.setTextColor(Color.parseColor("#000000"));
                tvCitizen.setTextColor(Color.parseColor("#A9A9A9"));
                tvNotification.setTextColor(Color.parseColor("#A9A9A9"));
                break;
            case TAG_FRAGMENT_CITIZENS:
                homeIcon.setImageDrawable(getApplicationContext().getDrawable(R.mipmap.ic_home_off));
                citizenIcon.setImageDrawable(getApplicationContext().getDrawable(R.mipmap.ic_citizen_on));
                notificationIcon.setImageDrawable(getApplicationContext().getDrawable(R.mipmap.ic_notification_off));
                tvHome.setTextColor(Color.parseColor("#A9A9A9"));
                tvCitizen.setTextColor(Color.parseColor("#000000"));
                tvNotification.setTextColor(Color.parseColor("#A9A9A9"));
                break;
            case TAG_FRAGMENT_NOTIFICATIONS:
                homeIcon.setImageDrawable(getApplicationContext().getDrawable(R.mipmap.ic_home_off));
                citizenIcon.setImageDrawable(getApplicationContext().getDrawable(R.mipmap.ic_citizen_off));
                notificationIcon.setImageDrawable(getApplicationContext().getDrawable(R.mipmap.ic_notification_on));
                tvHome.setTextColor(Color.parseColor("#A9A9A9"));
                tvCitizen.setTextColor(Color.parseColor("#A9A9A9"));
                tvNotification.setTextColor(Color.parseColor("#000000"));
                break;
        }
    }

    @Override
    public void onFragmentFinish(Fragment currentFragment, int destination, boolean isForward) {
        int enter = R.anim.enter_from_right;
        int exit = R.anim.exit_to_left;

        if (!isForward) {
            enter = R.anim.enter_from_left;
            exit = R.anim.exit_to_right;
        }
        Bundle bundle;
        switch (destination) {
            case FRAGMENT_FINISH_GOTO_HOME_ADMIN:
                fragment = new HomeAdminFragment();
                ((HomeAdminFragment) fragment).addPreviousFragmentTag(currentFragment.getTag());
                getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(enter, exit)
                        .replace(R.id.container, fragment, TAG_FRAGMENT_HOME_ADMIN)
                        .commit();
                break;
            case FRAGMENT_FINISH_GOTO_FORM_LIST:
                fragment = new FormListFragment();
                ((FormListFragment) fragment).addPreviousFragmentTag(currentFragment.getTag());
                bundle = new Bundle();
                bundle.putString(ConstantVariable.KEY_CL_BUNDLE, coveringLetterType);
                fragment.setArguments(bundle);
                getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(enter, exit)
                        .replace(R.id.container, fragment, TAG_FRAGMENT_FORM_LIST)
                        .commit();
                break;
            case FRAGMENT_FINISH_GOTO_PDF_VIEWER:
                fragment = new PdfViewerFragment();
                previousFragment = currentFragment;
                bundle = new Bundle();
                bundle.putString(ConstantVariable.KEY_CL_BUNDLE, coveringLetterType);
                fragment.setArguments(bundle);
                ((PdfViewerFragment) fragment).addPreviousFragmentTag(currentFragment.getTag());
                getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(enter, exit)
                        .replace(R.id.container, fragment, TAG_FRAGMENT_PDF_VIEWER)
                        .commit();
                break;
            case FRAGMENT_FINISH_GOTO_CITIZENS:
                fragment = new CitizenFragment();
                ((CitizenFragment) fragment).addPreviousFragmentTag(currentFragment.getTag());
                getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(enter, exit)
                        .replace(R.id.container, fragment, TAG_FRAGMENT_CITIZENS)
                        .commit();
                break;
            case FRAGMENT_FINISH_GOTO_INPUT_USER:
                fragment = new InputNewUsersFragment();
                ((InputNewUsersFragment) fragment).addPreviousFragmentTag(currentFragment.getTag());
                getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(enter, exit)
                        .replace(R.id.container, fragment, TAG_FRAGMENT_INPUT_USER)
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
            case FRAGMENT_FINISH_GOTO_ADMIN_PROFILE:
                fragment = new AdminProfileFragment();
                ((AdminProfileFragment) fragment).addPreviousFragmentTag(currentFragment.getTag());
                getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(enter, exit)
                        .replace(R.id.container, fragment, TAG_FRAGMENT_ADMIN_PROFILE)
                        .commit();
                break;
            case FRAGMENT_FINISH_GOTO_CITIZEN_LIST:
                fragment = new CitizenListFragment();
                ((CitizenListFragment) fragment).addPreviousFragmentTag(currentFragment.getTag());
                getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(enter, exit)
                        .replace(R.id.container, fragment, TAG_FRAGMENT_CITIZEN_LIST)
                        .commit();
                break;
        }
    }

    @SuppressLint("NewApi")
    @Override
    public void onFragmentCreated(Fragment currentFragment, String previousFragment) {
        if (currentFragment instanceof HomeAdminFragment) {
            if (currentFragment.getTag() != null) {
                setBottomBar(currentFragment.getTag());
            }
            bottomBar.setVisibility(View.VISIBLE);
            if (previousFragment.equalsIgnoreCase(TAG_FRAGMENT_FORM_LIST)
                    || previousFragment.equalsIgnoreCase(TAG_FRAGMENT_INPUT_USER)) {
                bottomBar.startAnimation(slideUp);
            }
        } else if (currentFragment instanceof CitizenFragment) {
            if (currentFragment.getTag() != null) {
                setBottomBar(currentFragment.getTag());
            }
            bottomBar.setVisibility(View.VISIBLE);
            if (previousFragment.equalsIgnoreCase(TAG_FRAGMENT_FORM_LIST)
                    || previousFragment.equalsIgnoreCase(TAG_FRAGMENT_INPUT_USER)) {
                bottomBar.startAnimation(slideUp);
            }
        } else if (currentFragment instanceof NotificationsFragment) {
            if (currentFragment.getTag() != null) {
                setBottomBar(currentFragment.getTag());
            }
            bottomBar.setVisibility(View.VISIBLE);
            if (previousFragment.equalsIgnoreCase(TAG_FRAGMENT_FORM_LIST)
                    || previousFragment.equalsIgnoreCase(TAG_FRAGMENT_INPUT_USER)) {
                bottomBar.startAnimation(slideUp);
            }
        } else {
            bottomBar.setVisibility(View.GONE);
            if (previousFragment.equalsIgnoreCase(TAG_FRAGMENT_HOME_ADMIN) ||
                    previousFragment.equalsIgnoreCase(TAG_FRAGMENT_CITIZENS) ||
                    previousFragment.equalsIgnoreCase(TAG_FRAGMENT_NOTIFICATIONS)) {
                bottomBar.startAnimation(slideDown);
            }
        }
    }

    @Override
    public void onFragmentPassingData(String coveringLetter) {
        coveringLetterType = coveringLetter;
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
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        if (fragment instanceof HomeAdminFragment) {
            finish();
        } else if (fragment instanceof FormListFragment) {
            onFragmentFinish(fragment, FRAGMENT_FINISH_GOTO_HOME_ADMIN, false);
        } else if (fragment instanceof PdfViewerFragment) {
            if (previousFragment != null) {
                if (previousFragment instanceof NotificationsFragment) {
                    onFragmentFinish(fragment, FRAGMENT_FINISH_GOTO_NOTIFICATION, false);
                } else {
                    onFragmentFinish(fragment, FRAGMENT_FINISH_GOTO_FORM_LIST, false);
                }
            }
        } else if (fragment instanceof CitizenFragment) {
            onFragmentFinish(fragment, FRAGMENT_FINISH_GOTO_HOME_ADMIN, false);
        } else if (fragment instanceof InputNewUsersFragment) {
            onFragmentFinish(fragment, FRAGMENT_FINISH_GOTO_CITIZENS, false);
        } else if (fragment instanceof NotificationsFragment) {
            onFragmentFinish(fragment, FRAGMENT_FINISH_GOTO_HOME_ADMIN, false);
        } else if (fragment instanceof AdminProfileFragment) {
            onFragmentFinish(fragment, FRAGMENT_FINISH_GOTO_CITIZENS, false);
        } else if (fragment instanceof CitizenListFragment) {
            onFragmentFinish(fragment, FRAGMENT_FINISH_GOTO_CITIZENS, false);
        } else {
            super.onBackPressed();
        }
    }
}