package com.task.calmsleeptask.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.task.calmsleeptask.R;
import com.task.calmsleeptask.model.SliderItem;

import java.util.ArrayList;

public class SliderAdapter extends RecyclerView.Adapter<SliderAdapter.ViewHolder> {

    private ArrayList<SliderItem> items;
    private SliderListener mListener;
    private Context mContext;

    public SliderAdapter(Context context, SliderListener listener){
        this.items = new ArrayList<>();
        this.mContext = context;
        this.mListener = listener;
    }

    @NonNull
    @Override
    public SliderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.slider_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SliderAdapter.ViewHolder holder, int position) {
        SliderItem item = items.get(position);
        Glide.with(mContext)
                .load(item.slideItem)
                .into(holder.sliderIV);
        holder.sliderIV.setOnClickListener(view -> mListener.SlideSelected(item.slideItem));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        ImageView sliderIV;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            sliderIV = itemView.findViewById(R.id.slide_img_iv);
        }
    }

    public void setList(ArrayList<SliderItem> list){
        this.items.addAll(list);
        notifyDataSetChanged();
    }

    public interface SliderListener{
        void SlideSelected(int position);
    }
}
