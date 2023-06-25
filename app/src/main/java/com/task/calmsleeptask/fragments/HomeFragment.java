package com.task.calmsleeptask.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import com.task.calmsleeptask.MainActivity;
import com.task.calmsleeptask.R;
import com.task.calmsleeptask.adapter.SliderAdapter;
import com.task.calmsleeptask.model.SliderItem;

import java.util.ArrayList;

public class HomeFragment extends Fragment implements SliderAdapter.SliderListener {

    ViewPager2 viewPager;
    SliderAdapter adapter;
    TextView instructionTV;
    ArrayList<SliderItem> sliderItems;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        inItWidgets(view);

        // Set Image List
        sliderItems.add(new SliderItem(R.drawable.slider1));
        sliderItems.add(new SliderItem(R.drawable.slider2));
        sliderItems.add(new SliderItem(R.drawable.slider3));
        sliderItems.add(new SliderItem(R.drawable.slider4));

        adapter.setList(sliderItems);
        viewPager.setAdapter(adapter);

        viewPager.setClipToPadding(false);
        viewPager.setClipChildren(false);
        viewPager.setOffscreenPageLimit(3);
        viewPager.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(40));
        compositePageTransformer.addTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                float r = 1 - Math.abs(position);
                page.setScaleY(0.85f + r * 0.15f);
            }
        });

        viewPager.setPageTransformer(compositePageTransformer);

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                switch (position){
                    case 0 :
                        instructionTV.setTextColor(ContextCompat.getColor(getContext(), R.color.green));
                        break;
                    case 1 :
                        instructionTV.setTextColor(ContextCompat.getColor(getContext(), R.color.orange));
                        break;
                    case 2 :
                        instructionTV.setTextColor(ContextCompat.getColor(getContext(), R.color.blue));
                        break;
                    case 3 :
                        instructionTV.setTextColor(ContextCompat.getColor(getContext(), R.color.light_green));
                        break;
                }

            }
        });

        return view;
    }

    private void inItWidgets(View view) {
        instructionTV = view.findViewById(R.id.instruction_tv);
        viewPager = view.findViewById(R.id.viewpager_v);
        adapter = new SliderAdapter(getContext(), HomeFragment.this);
        sliderItems = new ArrayList<>();
    }

    @Override
    public void SlideSelected(int slideItem) {
        Fragment fragment = new DetailsFragment(slideItem);
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();

// Setting the custom animations for the fragment transaction
        transaction.setCustomAnimations(R.anim.slide_in_up, R.anim.slide_out_down, R.anim.slide_in_up, R.anim.slide_out_down);

// Replacing the current fragment with the new fragment
        transaction.replace(R.id.container, fragment, fragment.getClass().getName());

        transaction.addToBackStack(fragment.getClass().getName());

        transaction.commit();
    }
}
