package com.task.calmsleeptask.dialogs;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.task.calmsleeptask.R;
import com.task.calmsleeptask.adapter.NumberAdapter;

import java.util.ArrayList;

public class SecondaryBottomSheet extends BottomSheetDialogFragment {

    int previousState;
    private SecondarySheetListener listener;
    public static BottomSheetBehavior bottomSheetBehavior;
    private BottomSheetBehavior.BottomSheetCallback mBottomSheetBehaviorCallback;

    public SecondaryBottomSheet(SecondarySheetListener listener){
        this.listener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.bottom_sheet_secondary_dialog, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        bottomSheetBehavior = BottomSheetBehavior.from((View) getView().getParent());
        bottomSheetBehavior.setBottomSheetCallback(mBottomSheetBehaviorCallback);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Get the bottom sheet view
        FrameLayout bottomSheet = getDialog().findViewById(com.google.android.material.R.id.design_bottom_sheet);


        // Set up the BottomSheetBehavior
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
        bottomSheetBehavior.setPeekHeight(200); // Initial height when collapsed
        bottomSheetBehavior.setHideable(true); // Allow hiding the bottom sheet
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED); // Set initial state to collapsed

        ArrayList<String> numbers = new ArrayList<>();
        setDummyData(numbers);

        CheckBox numberCheck = view.findViewById(R.id.check_box);
        CardView clickBTN = view.findViewById(R.id.click_here_btn);
        ViewPager2 numberViewpager = view.findViewById(R.id.number_viewpager);
        NumberAdapter adapter = new NumberAdapter(numberViewpager);
        adapter.setList(numbers);
        numberViewpager.setAdapter(adapter);

        numberViewpager.setClipToPadding(false);
        numberViewpager.setClipChildren(false);
        numberViewpager.setOffscreenPageLimit(3);
        numberViewpager.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(40));
        compositePageTransformer.addTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                float r = 1 - Math.abs(position);
                page.setScaleY(0.85f + r * 0.15f);
            }
        });

        numberViewpager.setPageTransformer(compositePageTransformer);
        numberViewpager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                adapter.notifyDataSetChanged();
            }
        });

        clickBTN.setOnClickListener(view1 -> {
            if (numberCheck.isChecked()){
                listener.seatsSelected(numbers.get(numberViewpager.getCurrentItem()));
            } else {
                Toast.makeText(requireActivity(), "Please select a number first", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void setDummyData(ArrayList<String> numbers) {
        numbers.add("1");
        numbers.add("2");
        numbers.add("3");
        numbers.add("4");
        numbers.add("5");
        numbers.add("6");
    }

    @Override
    public void onStart() {
        super.onStart();
        // Customize the behavior of the bottom sheet dialog here
        previousState = BottomSheetBehavior.STATE_COLLAPSED;
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED); // Set initial state to collapsed
    }

    public void setBottomSheetCallback(BottomSheetBehavior.BottomSheetCallback callback) {
        mBottomSheetBehaviorCallback = callback;
    }

    public void expand() {
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
    }

    public void hide() {
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
    }

    public interface SecondarySheetListener {
        void seatsSelected(String number);
    }

    public void collapse() {
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
    }
}
