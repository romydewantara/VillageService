package com.example.villageservice.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import com.example.villageservice.R;
import com.example.villageservice.fragment.HomeAdminFragment;
import com.example.villageservice.fragment.HomeUserFragment;
import com.example.villageservice.fragment.PdfViewerFragment;

public class MainActivity extends AppCompatActivity {

    public static final String TAG_FRAGMENT_HOME_USER = "home_user_fragment";
    public static final String TAG_FRAGMENT_HOME_ADMIN = "home_admin_fragment";
    public static final String TAG_FRAGMENT_PDF_VIEWER = "pdf_viewer_fragment";
    public static final String TAG_FRAGMENT_NEWS = "news_screen";
    public static final String TAG_FRAGMENT_EVENT = "event_screen";
    public static final String TAG_FRAGMENT_GALLERY = "gallery_screen";
    public static final String TAG_FRAGMENT_ANNOUNCEMENT = "announcement_screen";

    private Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String mail = getIntent().getStringExtra("EMAIL");
        if (mail.equalsIgnoreCase("user@test.com")) {
            goToHomeUser();
        } else if (mail.equalsIgnoreCase("admin@test.com")) {
            //goToHomeAdmin();
            goToPdfViewer();
        }
    }

    private void goToHomeUser() {
        fragment = new HomeUserFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, fragment, TAG_FRAGMENT_HOME_USER)
                .commit();
    }

    private void goToHomeAdmin() {
        fragment = new HomeAdminFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, fragment, TAG_FRAGMENT_HOME_ADMIN)
                .commit();
    }

    private void goToPdfViewer() {
        fragment = new PdfViewerFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, fragment, TAG_FRAGMENT_PDF_VIEWER)
                .commit();
    }
}
