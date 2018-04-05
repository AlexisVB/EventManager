package aaaaa.p.eventmanager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import aaaaa.p.eventmanager.Fragments.LoginFragment1;

public class PageAdapter1 extends FragmentStatePagerAdapter {
    public PageAdapter1(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new LoginFragment1();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 1;
    }
}