package com.ahmed.newpro;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ahmed.newpro.Adapter.SliderAdapter;

import java.util.ArrayList;

public class onBoardering extends AppCompatActivity {

    //private ViewPager msliderpager;
    private LinearLayout mdotlaout;
    private SliderAdapter sliderAdapter;
    //private TextView[]mdots;
    ArrayList<TextView> mdots = new ArrayList<>();

LinearLayout ly_linear ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_boardering);
        ViewPager msliderpager ;
        msliderpager= findViewById(R.id.sliderpager);
        mdotlaout=findViewById(R.id.dots);
        SliderAdapter sliderAdapter=new SliderAdapter(this);
        msliderpager.setAdapter(sliderAdapter);
        addDotindecator(0);
        msliderpager.addOnPageChangeListener(viewLisnner);

        //

    }

    public void  addDotindecator(int position){

        mdotlaout.removeAllViews();
        for (int i=0;i<4;i++){
        mdots.add(new TextView(this));
        mdots.get(i).setText(Html.fromHtml("&#8226;"));
        mdots.get(i).setTextSize(35);
        mdots.get(i).setTextColor(getResources().getColor(R.color.colorPrimaryDark));
        mdotlaout.addView(mdots.get(i));

    }

        if(mdots.size()>0){
            mdots.get(position).setTextColor(getResources().getColor(R.color.colorPrimary));

        }
    }

    ViewPager.OnPageChangeListener viewLisnner=new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            addDotindecator(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };


    public void btn_login(View view) {



          Intent intent= new  Intent(this, login.class);

        startActivity(intent);
        finish();
    }

    public void btn_signup(View view) {
        Intent intent= new  Intent(this, Signup.class);

        startActivity(intent);
        finish();
    }
}
