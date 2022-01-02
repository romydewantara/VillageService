package com.example.villageservice.listener;

import androidx.fragment.app.Fragment;

/**
 * Created by:
 * on December, 24 2021
 * */
public interface FragmentListener {
    void onFragmentFinish(Fragment currentFragment, int destination, boolean isForward);
    void onFragmentCreated(Fragment currentFragment);
    void onFragmentPassingData(String coveringLetter);
    void onFragmentPaused();
    void onActivityFinish();
    void onActivityBackPressed();
}
