package com.task.calmsleeptask.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.task.calmsleeptask.R;
import com.task.calmsleeptask.dialogs.FinalSheet;
import com.task.calmsleeptask.dialogs.IntroBottomSheet;
import com.task.calmsleeptask.dialogs.SecondaryBottomSheet;
import com.task.calmsleeptask.model.Tournament;
import com.task.calmsleeptask.utils.MyBottomSheetCallback;
import com.task.calmsleeptask.utils.BottomSheetInterface;

public class DetailsFragment extends Fragment implements IntroBottomSheet.IntroBottomSheetListener, SecondaryBottomSheet.SecondarySheetListener, FinalSheet.FinalListener {

    ImageView sliderIV;
    CardView optionBtn;
    Tournament tournament;
    FinalSheet finalSheet;
    IntroBottomSheet introBottomSheet;
    SecondaryBottomSheet secondaryBottomSheet;
    private boolean finalSheetExpanded = false;

    private int sliderItem;
    public DetailsFragment(int slideItem){
        this.sliderItem = slideItem;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_details, container, false);

        inItWidgets(view);
        Glide.with(getActivity())
                .load(sliderItem)
                .into(sliderIV);

        initializeBottomSheets();

        optionBtn.setOnClickListener(v -> introBottomSheet.show(getChildFragmentManager(), "myBottomSheet"));

        return view;
    }

    private void initializeBottomSheets() {
        introBottomSheet = new IntroBottomSheet(DetailsFragment.this);
        introBottomSheet.setBottomSheetCallback(new MyBottomSheetCallback(bottomSheetInterface, "introSheet"));

        secondaryBottomSheet = new SecondaryBottomSheet(DetailsFragment.this);
        secondaryBottomSheet.setBottomSheetCallback(new MyBottomSheetCallback(bottomSheetInterface, "secondarySheet"));

        finalSheet = new FinalSheet(DetailsFragment.this);
        finalSheet.setBottomSheetCallback(new MyBottomSheetCallback(bottomSheetInterface, "finalSheet"));
    }

    private void inItWidgets(View view) {
        tournament = new Tournament();
        optionBtn = view.findViewById(R.id.option_btn);
        sliderIV = view.findViewById(R.id.slider_iv);
    }

    @Override
    public void DatesSelected(String firstDate, String endDate) {
        tournament.setFirstDate(firstDate);
        tournament.setEndDate(endDate);
        showSecondSheet();
    }

    private void showSecondSheet() {
        secondaryBottomSheet.show(getChildFragmentManager(), "mySecondaryBottomSheet");
    }

    BottomSheetInterface bottomSheetInterface = new BottomSheetInterface() {
        @Override
        public void onBottomSheetStateChanged(String tag, View bottomSheet, int newState) {
            if (tag.equals("introSheet")){

            } else if (tag.equals("secondarySheet")){
                if (newState == BottomSheetBehavior.STATE_EXPANDED){
                    introBottomSheet.collapse();
                } else if (newState == BottomSheetBehavior.STATE_COLLAPSED){
                    if (finalSheetExpanded){
                        introBottomSheet.collapse();
                    } else
                    introBottomSheet.expand();
                }
            } else if (tag.equals("finalSheet")){
                if (newState == BottomSheetBehavior.STATE_EXPANDED){
                    finalSheetExpanded = true;
                    secondaryBottomSheet.collapse();
                    introBottomSheet.collapse();
                } else if (newState  == BottomSheetBehavior.STATE_COLLAPSED){
                    finalSheetExpanded = false;
                    secondaryBottomSheet.expand();
                }
            }
        }

        @Override
        public void onBottomSheetSlide(String tag, View bottomSheet, float slideOffset) {

        }
    };

    @Override
    public void seatsSelected(String number) {
        tournament.setNumber(number);
        finalSheet.setData(tournament);
        showFinalSheet();
    }

    private void showFinalSheet() {
        finalSheet.show(getChildFragmentManager(), "myFinalBottomSheet");
    }

    @Override
    public void finalize() {
        introBottomSheet.hide();
        secondaryBottomSheet.hide();
        finalSheet.hide();
        new Handler().postDelayed(() -> requireActivity().onBackPressed(), 800);
    }
}
