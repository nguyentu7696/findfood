package com.anhtu.hongngoc.findfood.view;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.anhtu.hongngoc.findfood.Adapters.AdapterViewPagerTrangChu;
import com.anhtu.hongngoc.findfood.R;

public class TrangChuActivity extends AppCompatActivity implements View.OnClickListener, ViewPager.OnPageChangeListener, RadioGroup.OnCheckedChangeListener{

    private ViewPager viewPagerTrangChu;
    private RadioButton rdOdau, rdAngi;
    private RadioGroup groupOdauAngi;
    private ImageView themquanan;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_trangchu);

        viewPagerTrangChu = (ViewPager) findViewById(R.id.viewpager_trangchu);
        rdOdau = (RadioButton) findViewById(R.id.rd_odau);
        rdAngi = (RadioButton) findViewById(R.id.rd_angi);
        themquanan = (ImageView) findViewById(R.id.themquanan);

        groupOdauAngi = (RadioGroup) findViewById(R.id.group_odau_angi);

        AdapterViewPagerTrangChu adapterViewPagerTrangChu = new AdapterViewPagerTrangChu(getSupportFragmentManager());
        viewPagerTrangChu.setAdapter(adapterViewPagerTrangChu);

        viewPagerTrangChu.addOnPageChangeListener(this);

        groupOdauAngi.setOnCheckedChangeListener(this);

        themquanan.setOnClickListener(this);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        switch (position) {
            case 0:
                rdOdau.setChecked(true);
                break;

            case 1:
                rdAngi.setChecked(true);
                break;

        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId){
            case R.id.rd_angi:
                viewPagerTrangChu.setCurrentItem(1);
                break;

            case R.id.rd_odau:
                viewPagerTrangChu.setCurrentItem(0);
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.themquanan:
                Intent intent = new Intent(this, ThemQuanAnActivity.class);
                startActivity(intent);
                break;
        }
    }
}
