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
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;

public class WeatherActivity extends AppCompatActivity {

    private static final String TAG = "WeatherActivity";

    private DrawerLayout mDrawerLayout;
    private Toolbar mToolbar;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
//        setContentView(R.layout.activity_main);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
//
//        // Create a new Fragment to be placed in the activity
//        ForecastFragment firstFragment = new ForecastFragment();
//
//        // Add the fragment to the 'container' FrameLayout
//        getSupportFragmentManager().beginTransaction().add(
//                R.id.activity_weather, firstFragment).commit();
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate is called");

        setContentView(R.layout.activity_weather);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Create a new instance of ForecastFragment
        ForecastFragment forecastFragment = new ForecastFragment();

        // Add the fragment to the 'container' FrameLayout using dynamic code
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().add(R.id.fragment_forecast, forecastFragment).commit();
    }

    private void setSupportActionBar(Toolbar toolbar) {
    }

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