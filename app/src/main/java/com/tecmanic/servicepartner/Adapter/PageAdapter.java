package com.tecmanic.servicepartner.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.tecmanic.servicepartner.Fragments.LoginFragment;
import com.tecmanic.servicepartner.Fragments.SignupFragment;


public class PageAdapter extends FragmentPagerAdapter {
    private int numsoftabs;
//    private PagerNotifier pagerNotifier;
//    private boolean data = true;

    public PageAdapter(FragmentManager fm, int numsoftabs) {
        super(fm);
        this.numsoftabs = numsoftabs;
//        this.pagerNotifier = pagerNotifier;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){

            case 0 :
                return new LoginFragment();
            case 1 :
                return new SignupFragment();

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numsoftabs;
    }

//    public void setDataNotifier(boolean data) {
//        this.data = data;
//    }
}
