package vn.edu.usth.weather;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class HomeFragmentPagerAdapter extends FragmentPagerAdapter {
    private final String[] titles = new String[] { "Hanoi", "Paris", "Toulouse" };
    public HomeFragmentPagerAdapter(FragmentManager fm) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
    }
    @Override
    public int getCount() {
        return 3; // number of pages for a ViewPager
    }
    @NonNull
    @Override
    public Fragment getItem(int page) {
        // returns an instance of Fragment corresponding to the specified page
        switch (page) {
            // Put 3 WeatherAndForecastFragments into the ViewPager
            case 0: return WeatherAndForecastFragment.newInstance("Hanoi", "Vietnam");
            case 1: return WeatherAndForecastFragment.newInstance("Paris", "France");
            case 2: return WeatherAndForecastFragment.newInstance("Toulouse", "France");
        }
        return new EmptyFragment(); // failsafe
    }
    @Override
    public CharSequence getPageTitle(int page) {
        // returns a tab title corresponding to the specified page
        return titles[page];
    }
}
