package com.ahmed.newpro.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.ahmed.newpro.R;

public class SliderAdapter extends PagerAdapter {
    Context context;
    LayoutInflater layoutInflater;
    public SliderAdapter(Context context){
        this.context=context;
    }

    public int[] slider_image={
            R.drawable.slider1,
            R.drawable.view1,
            R.drawable.view2,
            R.drawable.del


    };
    public String[] slider_title={
           "Get Good Flavors",
            "We Cook for You",
            "Delicious Food ",
            "Fast Delivery"

 };
    public String[] slider_desc={
            "Experience a world of food , with your \n favorite restaurant",
            "We use only the best ingredient\n in our meals ",
            "You will eat more than once,\n I promise",
            "We will deliver the order to you \n upon request"


    };

    @Override
    public int getCount() {
        return slider_title.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == (RelativeLayout)object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater=(LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view=layoutInflater.inflate(R.layout.slider_pager,container,false);
        ImageView imageView=(ImageView) view.findViewById(R.id.imageView);
        TextView titleView=(TextView) view.findViewById(R.id.title);
        TextView descView=(TextView) view.findViewById(R.id.descText);
        imageView.setImageResource(slider_image[position]);
        titleView.setText(slider_title[position]);
        descView.setText(slider_desc[position]);
        container.addView(view);
        return view;



    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((RelativeLayout)object);
    }
}
