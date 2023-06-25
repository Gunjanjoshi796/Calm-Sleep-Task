package com.task.calmsleeptask.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.task.calmsleeptask.R;

import java.util.ArrayList;

public class NumberAdapter extends RecyclerView.Adapter<NumberAdapter.ViewHolder> {

    private ArrayList<String> numbers;
    private ViewPager2 pager2;

    public NumberAdapter(ViewPager2 viewPager2){
        this.numbers = new ArrayList<>();
        this.pager2 = viewPager2;
    }

    @NonNull
    @Override
    public NumberAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_numbers, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NumberAdapter.ViewHolder holder, int position) {
        String number = numbers.get(position);
        holder.numberTV.setText(number);
        if (position == pager2.getCurrentItem()){
            holder.numberTV.setTextColor(Color.WHITE);
        } else {
            holder.numberTV.setTextColor(Color.BLACK);
        }
    }

    @Override
    public int getItemCount() {
        return numbers.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        TextView numberTV;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            numberTV = itemView.findViewById(R.id.number_tv);
        }
    }

    public void setList(ArrayList<String> strings){
        this.numbers.addAll(strings);
        notifyDataSetChanged();
    }
}
