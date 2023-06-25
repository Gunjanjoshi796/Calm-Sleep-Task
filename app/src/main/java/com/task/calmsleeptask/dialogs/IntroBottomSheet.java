package com.task.calmsleeptask.dialogs;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

import com.archit.calendardaterangepicker.customviews.CalendarListener;
import com.archit.calendardaterangepicker.customviews.DateRangeCalendarView;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import com.task.calmsleeptask.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class IntroBottomSheet extends BottomSheetDialogFragment {


    int previousState;
    TextView firstDateTV;
    TextView endDateTV;
    public static BottomSheetBehavior bottomSheetBehavior;
    private IntroBottomSheetListener introBottomSheetListener;
    private BottomSheetBehavior.BottomSheetCallback mBottomSheetBehaviorCallback;


    public IntroBottomSheet(IntroBottomSheetListener introBottomSheetListener) {
        this.introBottomSheetListener = introBottomSheetListener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for the bottom sheet dialog
        return inflater.inflate(R.layout.bottom_sheet_dialog, container, false);
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
        CardView clickHereBtn = view.findViewById(R.id.click_here_btn);
        DateRangeCalendarView calendarView = view.findViewById(R.id.calendar);
        firstDateTV = view.findViewById(R.id.start_date_tv);
        endDateTV = view.findViewById(R.id.end_date_tv);
        calendarView.setCalendarListener(new CalendarListener() {
            @Override
            public void onFirstDateSelected(@NonNull Calendar startDate) {
                SimpleDateFormat sdf = new SimpleDateFormat("MMMM/dd/yyyy", Locale.ENGLISH);

                // Format the date
                String formattedDate = sdf.format(startDate.getTime());
                firstDateTV.setText(formattedDate);
            }

            @Override
            public void onDateRangeSelected(@NonNull Calendar calendar, @NonNull Calendar calendar1) {
                SimpleDateFormat sdf = new SimpleDateFormat("MMMM/dd/yyyy", Locale.ENGLISH);

                // Format the date
                String formattedDate = sdf.format(calendar1.getTime());
                endDateTV.setText(formattedDate);
            }
        });

        clickHereBtn.setOnClickListener(view1 -> {
            if (!firstDateTV.getText().toString().isEmpty() && !endDateTV.getText().toString().isEmpty()){
                introBottomSheetListener.DatesSelected(firstDateTV.getText().toString(), endDateTV.getText().toString());
            } else {
                Toast.makeText(requireActivity(), "Please select Dates first", Toast.LENGTH_SHORT).show();
            }

        });

        // Set up the BottomSheetBehavior
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
        bottomSheetBehavior.setPeekHeight(200); // Initial height when collapsed
        bottomSheetBehavior.setHideable(true); // Allow hiding the bottom sheet
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED); // Set initial state to collapsed

    }

    @Override
    public void onStart() {
        super.onStart();
        previousState = BottomSheetBehavior.STATE_EXPANDED;
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED); // Set initial state to collapsed
    }

    public void expand() {
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
    }

    public void collapse() {
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
    }

    public void hide() {
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
    }

    public interface IntroBottomSheetListener {
        void DatesSelected(String firstDate, String endDate);
    }

    public void setBottomSheetCallback(BottomSheetBehavior.BottomSheetCallback callback) {
        mBottomSheetBehaviorCallback = callback;
    }



}
