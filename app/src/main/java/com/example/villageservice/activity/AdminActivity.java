package com.example.villageservice.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.example.villageservice.R;
import com.example.villageservice.fragment.CitizensFragment;
import com.example.villageservice.fragment.FormListFragment;
import com.example.villageservice.fragment.HomeAdminFragment;
import com.example.villageservice.fragment.NotificationsFragment;
import com.example.villageservice.listener.FragmentListener;
import com.example.villageservice.model.User;
import com.example.villageservice.model.UserList;
import com.example.villageservice.utility.AppUtil;
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
    private ImageView homeBar;
    private ImageView userBar;
    private ImageView notificationBar;

    private Fragment fragment;

    public static final int FRAGMENT_FINISH_GOTO_FORM_LIST = 1;

    public static final String TAG_FRAGMENT_HOME_ADMIN = "home_admin_fragment";
    public static final String TAG_FRAGMENT_CITIZENS = "citizens_fragment";
    public static final String TAG_FRAGMENT_NOTIFICATIONS = "notifications_fragment";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_admin);

        initMandatory();
        initListener();
        goToHome();
        fetchUsers();
    }

    private void initMandatory() {
        appUtil = new AppUtil();
        bottomBar = findViewById(R.id.bottomBar);
        homeBar = findViewById(R.id.homeBar);
        userBar = findViewById(R.id.userBar);
        notificationBar = findViewById(R.id.notificationBar);
    }

    @SuppressLint("NewApi")
    private void goToHome() {
        fragment = new HomeAdminFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, fragment, TAG_FRAGMENT_HOME_ADMIN)
                .commit();
        homeBar.setImageDrawable(getApplicationContext().getDrawable(R.mipmap.ic_bar_home_on));
        userBar.setImageDrawable(getApplicationContext().getDrawable(R.mipmap.ic_bar_user_off));
        notificationBar.setImageDrawable(getApplicationContext().getDrawable(R.mipmap.ic_bar_notification_off));
    }

    @SuppressLint("NewApi")
    private void goToCitizens() {
        fragment = new CitizensFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, fragment, TAG_FRAGMENT_CITIZENS)
                .commit();
        homeBar.setImageDrawable(getApplicationContext().getDrawable(R.mipmap.ic_bar_home_off));
        userBar.setImageDrawable(getApplicationContext().getDrawable(R.mipmap.ic_bar_user_on));
        notificationBar.setImageDrawable(getApplicationContext().getDrawable(R.mipmap.ic_bar_notification_off));
    }

    @SuppressLint("NewApi")
    private void goToNotifications() {
        fragment = new NotificationsFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, fragment, TAG_FRAGMENT_NOTIFICATIONS)
                .commit();
        homeBar.setImageDrawable(getApplicationContext().getDrawable(R.mipmap.ic_bar_home_off));
        userBar.setImageDrawable(getApplicationContext().getDrawable(R.mipmap.ic_bar_user_off));
        notificationBar.setImageDrawable(getApplicationContext().getDrawable(R.mipmap.ic_bar_notification_on));
    }

    private void fetchUsers() {
        String jsonFile = appUtil.readTextFileFromAssets(getApplicationContext(), "json/users.json");
        Type listData = new TypeToken<UserList>() {}.getType();
        userList = new Gson().fromJson(jsonFile, listData);
        users = new ArrayList<>();
        if (!userList.getUserList().isEmpty()) {
            users.addAll(userList.getUserList());
        }
        objListUser = VSPreference.getInstance(getApplicationContext()).loadUserList();
        if (objListUser.isEmpty()) {
            appUtil.importUserData(getApplicationContext(), VSPreference.KEY_USER_LIST, users);
        } else {
            //TODO: show list of users to recyclerView and check if exists then hide import button
        }
        Log.d("Admin", "getJsonData - user: " + new Gson().toJson(VSPreference.getInstance(getApplicationContext()).loadUserList()));
    }

    private void initListener() {
        homeBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToHome();
            }
        });
        userBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToCitizens();
            }
        });
        notificationBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToNotifications();
            }
        });
    }

    @Override
    public void onFragmentFinish(Fragment currentFragment, int destination, boolean isForward) {
        fragment = new FormListFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, fragment, TAG_FRAGMENT_HOME_ADMIN)
                .commit();
        bottomBar.setVisibility(View.INVISIBLE);
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