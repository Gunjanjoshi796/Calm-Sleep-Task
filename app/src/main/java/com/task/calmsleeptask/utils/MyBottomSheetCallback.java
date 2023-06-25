package com.task.calmsleeptask.utils;

import android.view.View;

import androidx.annotation.NonNull;

import com.google.android.material.bottomsheet.BottomSheetBehavior;

public class MyBottomSheetCallback  extends BottomSheetBehavior.BottomSheetCallback{

    private BottomSheetInterface bottomSheetInterface;
    private String tag;

    public MyBottomSheetCallback(BottomSheetInterface bottomSheetInterface, String tag) {
        this.bottomSheetInterface = bottomSheetInterface;
        this.tag = tag;
    }

    @Override
    public void onStateChanged(@NonNull View bottomSheet, int newState) {
        bottomSheetInterface.onBottomSheetStateChanged(tag, bottomSheet, newState);
    }

    @Override
    public void onSlide(@NonNull View bottomSheet, float slideOffset) {
        bottomSheetInterface.onBottomSheetSlide(tag, bottomSheet, slideOffset);
    }
}
