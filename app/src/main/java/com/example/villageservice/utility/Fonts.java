package com.example.villageservice.utility;

import android.content.Context;
import android.graphics.Typeface;

/**
 * Created by: Fakhri Pratama
 * on October, 26 2020
 * */

public class Fonts {

    private Context context;

    public Fonts(Context context) {
        this.context = context;
    }

    public Typeface rBold() {
        return Typeface.createFromAsset(context.getAssets(), "fonts/Raleway-Bold.ttf");
    }

    public Typeface rBoldExtra() {
        return Typeface.createFromAsset(context.getAssets(), "fonts/Raleway-BoldExtra.ttf");
    }

    public Typeface rLight() {
        return Typeface.createFromAsset(context.getAssets(), "fonts/Raleway-Light.ttf");
    }

    public Typeface rMedium() {
        return Typeface.createFromAsset(context.getAssets(), "fonts/Raleway-Medium.ttf");
    }

    public Typeface rRegular() {
        return Typeface.createFromAsset(context.getAssets(), "fonts/Raleway-Regular.ttf");
    }

    public Typeface mulishBold() {
        return Typeface.createFromAsset(context.getAssets(), "fonts/Mulish-Bold.ttf");
    }

    public Typeface mulishBoldExtra() {
        return Typeface.createFromAsset(context.getAssets(), "fonts/Mulish-BoldExtra.ttf");
    }

    public Typeface mulishMedium() {
        return Typeface.createFromAsset(context.getAssets(), "fonts/Mulish-Medium.ttf");
    }

    public Typeface mulishRegular() {
        return Typeface.createFromAsset(context.getAssets(), "fonts/Mulish-Regular.ttf");
    }


}
