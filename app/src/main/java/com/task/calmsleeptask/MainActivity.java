package com.task.calmsleeptask;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import com.task.calmsleeptask.fragments.HomeFragment;
import com.task.calmsleeptask.utils.FragmentManagerHelper;

public class MainActivity extends AppCompatActivity {

    FragmentManager fragmentManager = getSupportFragmentManager();
    public FragmentManagerHelper fragmentManagerHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        inItWidgets();
        fragmentManagerHelper.replace(new HomeFragment(), false, false);
    }

    private void inItWidgets() {
        fragmentManagerHelper = new FragmentManagerHelper(fragmentManager);
    }
}