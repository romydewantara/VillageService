package com.example.villageservice.library;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ImageView;

import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieDrawable;
import com.example.villageservice.R;

public class CustomLoadingDialog extends Dialog {

    private LottieAnimationView lottieLoading;

    public CustomLoadingDialog(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_loading);
        lottieLoading = findViewById(R.id.lottieLoading);
        lottieLoading.setSpeed(1.3f); //default speed
        getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        getWindow().setWindowAnimations(android.R.style.Animation_Toast);
        playLottieView();
        setCancelable(false);
    }

    public void playLottieView() {
        lottieLoading.setScaleType(ImageView.ScaleType.FIT_CENTER);
        lottieLoading.setRepeatCount(LottieDrawable.INFINITE);
    }
}
