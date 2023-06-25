package com.task.calmsleeptask.dialogs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.task.calmsleeptask.R;
import com.task.calmsleeptask.model.Tournament;

public class FinalSheet extends BottomSheetDialogFragment {

    private Tournament data;
    private FinalListener listener;
    public static BottomSheetBehavior bottomSheetBehavior;
    private BottomSheetBehavior.BottomSheetCallback mBottomSheetBehaviorCallback;

    public FinalSheet(FinalListener listener){
        this.listener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.bottom_sheet_final_dialog, container, false);
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

        FrameLayout bottomSheet = getDialog().findViewById(com.google.android.material.R.id.design_bottom_sheet);


        // Set up the BottomSheetBehavior
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
        bottomSheetBehavior.setPeekHeight(200); // Initial height when collapsed
        bottomSheetBehavior.setHideable(true); // Allow hiding the bottom sheet
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED); // Set initial state to collapsed

        TextView startingDateTV, endingDateTV, seatsBookedTV;
        startingDateTV = view.findViewById(R.id.starting_date_tv);
        endingDateTV = view.findViewById(R.id.ending_date_tv);
        seatsBookedTV = view.findViewById(R.id.seats_tv);
        CardView finalBtn = view.findViewById(R.id.thank_you_btn);

        if (data != null){
            startingDateTV.setText(data.firstDate);
            endingDateTV.setText(data.endDate);
            seatsBookedTV.setText(data.number);
        }

        finalBtn.setOnClickListener(view1 -> listener.finalize());

    }

    @Override
    public void onStart() {
        super.onStart();
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED); // Set initial state to collapsed
    }

    public void setBottomSheetCallback(BottomSheetBehavior.BottomSheetCallback callback) {
        mBottomSheetBehaviorCallback = callback;
    }

    public void setData(Tournament tournament){
        this.data = tournament;
    }

    public void hide() {
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
    }

    public interface FinalListener{
        void finalize();
    }
}
