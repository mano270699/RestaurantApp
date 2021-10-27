package com.ahmed.newpro.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.ahmed.newpro.API.ApiClient;
import com.ahmed.newpro.Model.mCat;
import com.ahmed.newpro.Model.mMenu;
import com.ahmed.newpro.R;

import com.ahmed.newpro.meal_details;
import com.smarteist.autoimageslider.SliderViewAdapter;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.List;


public class SliderAdapterExample extends SliderViewAdapter<SliderAdapterExample.SliderAdapterVH> {

    private Context context;
    private List<mMenu> data;
    private String urlImgSlider;


    public SliderAdapterExample(List<mMenu> data, Context context) {
        this.data = data;

        this.context = context;
    }

    @Override
    public SliderAdapterVH onCreateViewHolder(ViewGroup parent) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_slider_layout_item, null);
        return new SliderAdapterVH(inflate);
    }

    @Override
    public void onBindViewHolder(SliderAdapterVH viewHolder, final int position) {
        String ProImage = ApiClient.BASE_URL + "image/Meal_images/" + data.get(position).getMeal_image();
        SpannableString mSpannableString = new SpannableString("aHME");
        mSpannableString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(context, R.color.colorPrimary)), 0, 1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);


        Picasso.get()
                .load(ProImage)
                .placeholder(R.drawable.loading)
                .error(R.drawable.no_image)
                .into(viewHolder.imageViewBackground);

        viewHolder.SliderLayout.setOnClickListener(new View.OnClickListener() {
                                                       @Override
                                                       public void onClick(View v) {


                                                           Intent intent = new Intent(context, meal_details.class);


                                                           intent.putExtra("id", data.get(position).getMeal_id());
                                                           intent.putExtra("desc", data.get(position).getDescription());
                                                           intent.putExtra("price", String.valueOf(data.get(position).getMeal_price()));
                                                           intent.putExtra("title", data.get(position).getMeal_name());
                                                           intent.putExtra("urlImage", data.get(position).getMeal_image());

                                                           Log.i("meal_id:", String.valueOf(data.get(position).getMeal_id()));
                                                           Log.i("meal_desc:", String.valueOf(data.get(position).getDescription()));
                                                           Log.i("meal_price:", String.valueOf(data.get(position).getMeal_price()));
                                                           Log.i("meal_title:", String.valueOf(data.get(position).getMeal_name()));
                                                           Log.i("meal_urlImage:", String.valueOf(data.get(position).getMeal_image()));


                                                           context.startActivity(intent);

                                                       }
                                                   }

        );


    }

    @Override
    public int getCount() {
        //slider view count could be dynamic size
        return data.size();
    }

    class SliderAdapterVH extends SliderViewAdapter.ViewHolder {

        View itemView;
        ImageView imageViewBackground;
        FrameLayout SliderLayout;

        public SliderAdapterVH(View itemView) {
            super(itemView);
            imageViewBackground = itemView.findViewById(R.id.iv_auto_image_slider);
            SliderLayout = itemView.findViewById(R.id.SliderLayout);
            this.itemView = itemView;
        }

    }


}