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

    // get fonts collections
    // Smoolthan fonts
    public Typeface stBold() {
        Typeface stBold = Typeface.createFromAsset(context.getAssets(),
                "fonts/Smoolthan Bold.otf");
        return stBold;
    }

    public Typeface stMedium() {
        Typeface stMedium = Typeface.createFromAsset(context.getAssets(),
                "fonts/Smoolthan Medium.otf");
        return stMedium;
    }

    public Typeface stRegular() {
        Typeface stRegular = Typeface.createFromAsset(context.getAssets(),
                "fonts/Smoolthan Regular.otf");
        return stRegular;
    }

    public Typeface stThin() {
        Typeface stThin = Typeface.createFromAsset(context.getAssets(),
                "fonts/Smoolthan Thin.otf");
        return stThin;
    }

    public Typeface rBold() {
        return Typeface.createFromAsset(context.getAssets(), "fonts/Raleway-Bold.ttf");
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

    public Typeface rThin() {
        return Typeface.createFromAsset(context.getAssets(), "fonts/Raleway-Thin.ttf");
    }

}
