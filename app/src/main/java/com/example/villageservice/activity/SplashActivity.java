package com.example.villageservice.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.TextViewCompat;

import com.airbnb.lottie.LottieAnimationView;
import com.example.villageservice.R;
import com.example.villageservice.utility.ConstantVariable;
import com.example.villageservice.utility.Fonts;
import com.example.villageservice.utility.VSPreference;

public class SplashActivity extends AppCompatActivity {

    private static final int SPLASH_DELAY = 3000;

    private LottieAnimationView lottieSplash;
    private AppCompatTextView tvCopyright;
    private ConstraintLayout constraintCopyright;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        lottieSplash = findViewById(R.id.lottieSplash);
        tvCopyright = findViewById(R.id.tvCopyright);
        constraintCopyright = findViewById(R.id.constraintCopyright);

        Animation slideUp = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);
        constraintCopyright.setVisibility(View.VISIBLE);
        constraintCopyright.startAnimation(slideUp);

        TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(tvCopyright, 1, 14, 1, TypedValue.COMPLEX_UNIT_SP);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext(), SignInActivity.class).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                if (VSPreference.getInstance(getApplicationContext()).isSignIn()) {
                    if (VSPreference.getInstance(getApplicationContext()).getRole().equalsIgnoreCase(ConstantVariable.ADMIN)) {
                        intent = new Intent(getApplicationContext(), AdminActivity.class).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    } else if (VSPreference.getInstance(getApplicationContext()).getRole().equalsIgnoreCase(ConstantVariable.USER)) {
                        intent = new Intent(getApplicationContext(), UserActivity.class).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    }
                }
                lottieSplash.cancelAnimation();
                startActivity(intent);
                finish();
            }
        }, SPLASH_DELAY);
    }
}
