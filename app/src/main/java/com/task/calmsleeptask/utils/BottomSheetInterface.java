package com.task.calmsleeptask.utils;

import android.view.View;

public interface BottomSheetInterface {
    void onBottomSheetStateChanged(String tag, View bottomSheet, int newState);
    void onBottomSheetSlide(String tag, View bottomSheet, float slideOffset);
}
