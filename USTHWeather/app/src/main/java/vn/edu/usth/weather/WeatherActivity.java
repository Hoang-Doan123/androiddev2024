package vn.edu.usth.weather;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toolbar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.util.Objects;

public class WeatherActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager pager;

//    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //Remove WeatherFragment and ForecastFragment from WeatherActivity
//        // Create a new Fragment to be placed in the activity
//        WeatherFragment firstFragment = new WeatherFragment();
//
//        // Add the fragment to the 'container' FrameLayout
//        getSupportFragmentManager().beginTransaction().add(
//                R.id.activity_weather, firstFragment).commit();
//        // Create a new Fragment to be placed in the activity
//        ForecastFragment secondFragment = new ForecastFragment();
//
//        // Add the fragment to the 'container' FrameLayout
//        getSupportFragmentManager().beginTransaction().add(
//                R.id.activity_weather, secondFragment).commit();

        // Add a ViewPager into WeatherActivity
//        PagerAdapter adapter = new HomeFragmentPagerAdapter(getSupportFragmentManager());
//
//        ViewPager pager = (ViewPager) findViewById(R.id.pager);
//        if (pager == null) {
//            Log.e("WeatherActivity", "ViewPager is null");
//        }
//        assert pager != null;
//        pager.setOffscreenPageLimit(3);
//        pager.setAdapter(adapter);
        tabLayout = findViewById(R.id.tab);
        pager = findViewById(R.id.pager);

        HomeFragmentPagerAdapter adapter = new HomeFragmentPagerAdapter(getSupportFragmentManager());
        pager.setAdapter(adapter);

        tabLayout.setupWithViewPager(pager);
        Objects.requireNonNull(tabLayout.getTabAt(0));
        Objects.requireNonNull(tabLayout.getTabAt(1));
        Objects.requireNonNull(tabLayout.getTabAt(2));
//
////        Toolbar toolbar = findViewById(R.id.toolbar);
////        setSupportActionBar(toolbar);
    }

//    private void setSupportActionBar(Toolbar toolbar) {
//    }


    @Override
    protected void onStart() {
        super.onStart();
        Log.i("Start","Start");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("Resume","Resume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("Pause","Pause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("Stop","Stop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("Destroy","Destroy");
    }
}