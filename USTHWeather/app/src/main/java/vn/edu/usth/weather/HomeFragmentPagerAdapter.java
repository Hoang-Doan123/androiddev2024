package vn.edu.usth.weather;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.lang.reflect.Array;

public class HomeFragmentPagerAdapter extends FragmentPagerAdapter {
    private final int page_count = 3;
    private String titles[] = new String[] { "Hanoi", "Paris", "Toulouse" };
    public HomeFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }
    @Override
    public int getCount() {
        return page_count; // number of pages for a ViewPager
    }
    @Override
    public Fragment getItem(int page) {
        // returns an instance of Fragment corresponding to the specified page
        switch (page) {
            case 0: return Fragment1.newInstance();
            case 1: return Fragment2.newInstance();
            case 2: return Fragment3.newInstance();
        }
        return new EmptyFragment(); // failsafe
    }
    @Override
    public CharSequence getPageTitle(int page) {
        // returns a tab title corresponding to the specified page
        return titles[page];
    }
}
