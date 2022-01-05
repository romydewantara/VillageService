package com.example.villageservice.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.villageservice.R;
import com.example.villageservice.fragment.CitizenFragment;
import com.example.villageservice.fragment.FormListFragment;
import com.example.villageservice.fragment.HomeAdminFragment;
import com.example.villageservice.fragment.InputNewUsersFragment;
import com.example.villageservice.fragment.NotificationsFragment;
import com.example.villageservice.fragment.PdfViewerFragment;
import com.example.villageservice.listener.FragmentListener;
import com.example.villageservice.model.User;
import com.example.villageservice.model.UserList;
import com.example.villageservice.utility.AppUtil;
import com.example.villageservice.utility.ConstantVariable;
import com.example.villageservice.utility.VSPreference;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class AdminActivity extends AppCompatActivity implements FragmentListener {

    private AppUtil appUtil;
    private UserList userList;
    private ArrayList<User> users;
    private ArrayList<Object> objListUser;

    private FrameLayout bottomBar;

    private LinearLayout homeMenu;
    private LinearLayout citizenMenu;
    private LinearLayout notificationMenu;

    private ImageView homeIcon;
    private ImageView citizenIcon;
    private ImageView notificationIcon;

    private Fragment fragment;
    private String coveringLetterType = "";

    Animation slideUp;
    Animation slideDown;

    public static final int FRAGMENT_FINISH_GOTO_HOME_ADMIN = 0;
    public static final int FRAGMENT_FINISH_GOTO_FORM_LIST = 1;
    public static final int FRAGMENT_FINISH_GOTO_PDF_VIEWER = 2;
    public static final int FRAGMENT_FINISH_GOTO_CITIZENS = 3;
    public static final int FRAGMENT_FINISH_GOTO_INPUT_USER = 4;
    public static final int FRAGMENT_FINISH_GOTO_NOTIFICATION = 6;

    public static final String TAG_FRAGMENT_HOME_ADMIN = "home_admin_fragment";
    public static final String TAG_FRAGMENT_FORM_LIST = "form_list_fragment";
    public static final String TAG_FRAGMENT_PDF_VIEWER = "pdf_viewer_fragment";
    public static final String TAG_FRAGMENT_CITIZENS = "citizens_fragment";
    public static final String TAG_FRAGMENT_INPUT_USER = "input_user_fragment";
    public static final String TAG_FRAGMENT_NOTIFICATIONS = "notifications_fragment";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_admin);

        initMandatory();
        initListener();
        goToHome();
        //fetchUsers();
    }

    private void initMandatory() {
        slideUp = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_up);
        slideDown = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_down);
        appUtil = new AppUtil();
        bottomBar = findViewById(R.id.bottomBar);
        homeIcon = findViewById(R.id.homeIcon);
        citizenIcon = findViewById(R.id.citizenIcon);
        notificationIcon = findViewById(R.id.notificationIcon);

        homeMenu = findViewById(R.id.homeMenu);
        citizenMenu = findViewById(R.id.citizenMenu);
        notificationMenu = findViewById(R.id.notificationMenu);
    }

    @SuppressLint("NewApi")
    private void goToHome() {
        fragment = new HomeAdminFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, fragment, TAG_FRAGMENT_HOME_ADMIN)
                .commit();
        homeIcon.setImageDrawable(getApplicationContext().getDrawable(R.mipmap.ic_home_on));
        citizenIcon.setImageDrawable(getApplicationContext().getDrawable(R.mipmap.ic_citizen_off));
        notificationIcon.setImageDrawable(getApplicationContext().getDrawable(R.mipmap.ic_notification_off));
    }

    @SuppressLint("NewApi")
    private void goToCitizens() {
        fragment = new CitizenFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, fragment, TAG_FRAGMENT_CITIZENS)
                .commit();
        homeIcon.setImageDrawable(getApplicationContext().getDrawable(R.mipmap.ic_home_off));
        citizenIcon.setImageDrawable(getApplicationContext().getDrawable(R.mipmap.ic_citizen_on));
        notificationIcon.setImageDrawable(getApplicationContext().getDrawable(R.mipmap.ic_notification_off));
    }

    @SuppressLint("NewApi")
    private void goToNotifications() {
        fragment = new NotificationsFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, fragment, TAG_FRAGMENT_NOTIFICATIONS)
                .commit();
        homeIcon.setImageDrawable(getApplicationContext().getDrawable(R.mipmap.ic_home_off));
        citizenIcon.setImageDrawable(getApplicationContext().getDrawable(R.mipmap.ic_citizen_off));
        notificationIcon.setImageDrawable(getApplicationContext().getDrawable(R.mipmap.ic_notification_on));
    }

    private void fetchUsers() {
        String jsonFile = appUtil.readTextFileFromAssets(getApplicationContext(), "json/citizen_data.json");
        Type listData = new TypeToken<UserList>() {
        }.getType();
        userList = new Gson().fromJson(jsonFile, listData);
        users = new ArrayList<>();
        if (!userList.getUserList().isEmpty()) {
            users.addAll(userList.getUserList());
        }
        objListUser = VSPreference.getInstance(getApplicationContext()).loadUserList();
        if (objListUser.isEmpty()) {
            appUtil.importUserData(getApplicationContext(), users);
        } else {
            //TODO: show list of users to recyclerView and check if exists then hide import button
        }
        Log.d("Admin", "getJsonData - user: " + new Gson().toJson(VSPreference.getInstance(getApplicationContext()).loadUserList()));
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
        }
    }

    @SuppressLint("NewApi")
    @Override
    public void onFragmentCreated(Fragment currentFragment, String previousFragment) {
        if (currentFragment instanceof HomeAdminFragment) {
            homeIcon.setImageDrawable(getApplicationContext().getDrawable(R.mipmap.ic_home_on));
            citizenIcon.setImageDrawable(getApplicationContext().getDrawable(R.mipmap.ic_citizen_off));
            notificationIcon.setImageDrawable(getApplicationContext().getDrawable(R.mipmap.ic_notification_off));
            bottomBar.setVisibility(View.VISIBLE);
            if (previousFragment.equalsIgnoreCase(TAG_FRAGMENT_FORM_LIST)
                    || previousFragment.equalsIgnoreCase(TAG_FRAGMENT_INPUT_USER)) {
                bottomBar.startAnimation(slideUp);
            }
        } else if (currentFragment instanceof CitizenFragment) {
            homeIcon.setImageDrawable(getApplicationContext().getDrawable(R.mipmap.ic_home_off));
            citizenIcon.setImageDrawable(getApplicationContext().getDrawable(R.mipmap.ic_citizen_on));
            notificationIcon.setImageDrawable(getApplicationContext().getDrawable(R.mipmap.ic_notification_off));
            bottomBar.setVisibility(View.VISIBLE);
            if (previousFragment.equalsIgnoreCase(TAG_FRAGMENT_FORM_LIST)
                    || previousFragment.equalsIgnoreCase(TAG_FRAGMENT_INPUT_USER)) {
                bottomBar.startAnimation(slideUp);
            }
        } else if (currentFragment instanceof NotificationsFragment) {
            homeIcon.setImageDrawable(getApplicationContext().getDrawable(R.mipmap.ic_home_off));
            citizenIcon.setImageDrawable(getApplicationContext().getDrawable(R.mipmap.ic_citizen_off));
            notificationIcon.setImageDrawable(getApplicationContext().getDrawable(R.mipmap.ic_notification_on));
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
            onFragmentFinish(fragment, FRAGMENT_FINISH_GOTO_FORM_LIST, false);
        } else if (fragment instanceof CitizenFragment) {
            onFragmentFinish(fragment, FRAGMENT_FINISH_GOTO_HOME_ADMIN, false);
        } else if (fragment instanceof InputNewUsersFragment) {
            onFragmentFinish(fragment, FRAGMENT_FINISH_GOTO_CITIZENS, false);
        } else if (fragment instanceof NotificationsFragment) {
            onFragmentFinish(fragment, FRAGMENT_FINISH_GOTO_HOME_ADMIN, false);
        } else {
            super.onBackPressed();
        }
    }
}