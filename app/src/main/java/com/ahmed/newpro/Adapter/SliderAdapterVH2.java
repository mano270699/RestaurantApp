package com.ahmed.newpro.Adapter;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.ahmed.newpro.R;
import com.smarteist.autoimageslider.SliderViewAdapter;

public class SliderAdapterVH2 extends SliderViewAdapter.ViewHolder {
    View itemView;
    ImageView imageViewBackground;
    FrameLayout SliderLayout;

    public SliderAdapterVH2(View itemView) {
        super(itemView);
        imageViewBackground = itemView.findViewById(R.id.iv_auto_image_slider);
        SliderLayout = itemView.findViewById(R.id.SliderLayout);
        this.itemView = itemView;
    }
}
