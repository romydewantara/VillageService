package com.example.villageservice.listener;

import com.example.villageservice.model.User;

public interface InputUserDialogListener {
    void onAddButtonPressed(User user);
    void onCancelButtonPressed();
}
