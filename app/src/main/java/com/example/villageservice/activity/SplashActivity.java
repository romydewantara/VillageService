package com.example.villageservice.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.widget.TextViewCompat;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.TypedValue;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.villageservice.R;
import com.example.villageservice.utility.Fonts;

public class SplashActivity extends AppCompatActivity {

    private static final int SPLASH_DELAY = 1500;

    private LottieAnimationView lottieSplash;
    private AppCompatTextView tvCopyright;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        lottieSplash = findViewById(R.id.lottieSplash);
        tvCopyright = findViewById(R.id.tvCopyright);
        tvCopyright.setTypeface(new Fonts(getApplicationContext()).rMedium());
        tvCopyright.setTextColor(R.color.light_blue);
        TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(tvCopyright, 1, 14, 1, TypedValue.COMPLEX_UNIT_SP);

        /*lottieSplash.addAnimatorListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {}
            @Override
            public void onAnimationEnd(Animator animation) {
                startActivity(new Intent(getApplicationContext(), SignInActivity.class).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));
                finish();
            }
            @Override
            public void onAnimationCancel(Animator animation) {}
            @Override
            public void onAnimationRepeat(Animator animation) {}
        });*/

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                lottieSplash.cancelAnimation();
                startActivity(new Intent(getApplicationContext(), SignInActivity.class).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));
                finish();
            }
        }, SPLASH_DELAY);
    }
}
