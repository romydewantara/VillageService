package com.example.villageservice.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.widget.TextViewCompat;

import java.util.List;

public class CustomSpinnerAdapter extends ArrayAdapter<String> {

    Typeface font = Typeface.createFromAsset(getContext().getAssets(),
            "fonts/Mulish-Regular.ttf");

    public CustomSpinnerAdapter(Context context, int resource, List<String> items) {
        super(context, resource, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        AppCompatTextView view = (AppCompatTextView) super.getView(position, convertView, parent);
        view.setTypeface(font);
        view.setMaxLines(1);
        TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(view, 1, 14, 1, TypedValue.COMPLEX_UNIT_SP);
        view.setPadding(25, 0, 0, 0);
        return view;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        AppCompatTextView view = (AppCompatTextView) super.getDropDownView(position, convertView, parent);
        view.setTypeface(font);
        view.setMaxLines(1);
        TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(view, 1, 14, 1, TypedValue.COMPLEX_UNIT_SP);
        view.setPadding(25, 15, 15, 20);
        return view;
    }
}
