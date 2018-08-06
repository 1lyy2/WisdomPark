package com.zykj.carfigure.activity.login;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.zykj.carfigure.R;
import com.zykj.carfigure.helper.ViewPagerIndicateHelper;

public class GuildActivity extends AppCompatActivity {
    private int[] guild = new int[]{R.mipmap.guid1, R.mipmap.guid2};
    private Adapter adapter;

    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guild);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        initView();
    }

    protected void initView() {
        viewPager= findViewById(R.id.viewpage);
        adapter = new Adapter();
        viewPager.setAdapter(adapter);
        new ViewPagerIndicateHelper(viewPager, (ViewGroup) findViewById(R.id.linearlayout_guild_group), R.drawable.guild_buttom_normal_circle, R.drawable.guild_buttom_selected_circle).setViewPagerIndicate();
    }



    @Override
    public void onBackPressed() {
        gotoHome();
        super.onBackPressed();
    }

    private void gotoHome() {
    	Intent intent = new Intent(this,LoginActivity.class);
    	startActivity(intent);

    }

    class Adapter extends PagerAdapter {

        @Override
        public int getCount() {
            return guild.length;
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = (View) LayoutInflater.from(getContext()).inflate(R.layout.item_start_guild, null);
            ImageView imageView = (ImageView) view.findViewById(R.id.img_guild);
            imageView.setImageResource(guild[position]);
            if (position == guild.length - 1) {
                View.OnClickListener listener = new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        gotoHome();
                        finish();
                    }

                };
                imageView.setOnClickListener(listener);
            }
            container.addView(view);
            return view;
        }
    }
    protected Context getContext() {
        return this;
    }
}
