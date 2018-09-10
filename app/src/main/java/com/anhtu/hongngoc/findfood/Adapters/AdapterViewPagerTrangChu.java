package com.anhtu.hongngoc.findfood.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.anhtu.hongngoc.findfood.view.Fragments.AnGiFragment;
import com.anhtu.hongngoc.findfood.view.Fragments.OdauFragment;

public class AdapterViewPagerTrangChu extends FragmentStatePagerAdapter {

    private AnGiFragment anGiFragment;
    private OdauFragment odauFragment;

    public AdapterViewPagerTrangChu(FragmentManager fm) {
        super(fm);
        anGiFragment = new AnGiFragment();
        odauFragment = new OdauFragment();
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0 :
                return odauFragment;

            case 1:
                return anGiFragment;

            default: return null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}
