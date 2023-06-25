package com.task.calmsleeptask.utils;

import androidx.annotation.IdRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.task.calmsleeptask.R;

public class FragmentManagerHelper {

    private final FragmentManager mFragmentManager;

    public FragmentManagerHelper(FragmentManager fragmentManager) {
        mFragmentManager = fragmentManager;
    }

    public void replace(Fragment fragment, boolean addToBackStack, boolean shouldAnimate) {
        replace(R.id.container, fragment, addToBackStack, shouldAnimate);
    }

    public void replace(@IdRes int containerId, Fragment fragment, boolean addToBackStack, boolean shouldAnimate) {
        FragmentTransaction replaceTransaction = mFragmentManager.beginTransaction();
        replaceTransaction.replace(containerId, fragment, fragment.getClass().getName());
        if (addToBackStack) {
            replaceTransaction.addToBackStack(fragment.getClass().getName());
        }
        if (shouldAnimate){
            // Define the enter and exit animations for the fragment
            replaceTransaction.setCustomAnimations(R.anim.slide_in_up, R.anim.slide_out_down, R.anim.slide_in_up, R.anim.slide_out_down);
        }
        replaceTransaction.commit();
    }
}
