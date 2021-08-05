package com.tecmanic.servicepartner.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.tecmanic.servicepartner.Fragments.HistoryFragment;
import com.tecmanic.servicepartner.Fragments.OngoingFragment;
import com.tecmanic.servicepartner.Fragments.WithdrawlFragment;


public class JobAdapter extends FragmentPagerAdapter {
    private int numsoftabs;
//    private PagerNotifier pagerNotifier;
//    private boolean data = true;

    public JobAdapter(FragmentManager fm, int numsoftabs) {
        super(fm);
        this.numsoftabs = numsoftabs;
//        this.pagerNotifier = pagerNotifier;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){

            case 0 :
                return new OngoingFragment();
            case 1:
                return new HistoryFragment();
            case 2:
                return new WithdrawlFragment();

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
