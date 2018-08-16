package com.example.ducnguyen.demoandroidadvanceapp;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {
    private int[] mImagesId;
    private int mLayoutId;

    public ImageAdapter(int[] imagesId, int layoutId) {
        this.mImagesId = imagesId;
        this.mLayoutId = layoutId;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(mLayoutId, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.mImage.setImageResource(mImagesId[position]);
    }

    @Override
    public int getItemCount() {
        return mImagesId == null ? 0 : mImagesId.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView mImage;

        public ViewHolder(View itemView) {
            super(itemView);
            mImage = itemView.findViewById(R.id.image_hero);
        }
    }
}
