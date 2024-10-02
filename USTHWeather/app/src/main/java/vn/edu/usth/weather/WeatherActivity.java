package vn.edu.usth.weather;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.util.Objects;

public class WeatherActivity extends AppCompatActivity {

    public static final String TAG = "Weather";
    public static final String NETWORK_RESPONSE = "NETWORK_RESPONSE";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_weather);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.activity_weather), (v, insets) -> {
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
        TabLayout tabLayout = findViewById(R.id.tab);
        ViewPager pager = findViewById(R.id.pager);

        HomeFragmentPagerAdapter adapter = new HomeFragmentPagerAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        pager.setAdapter(adapter);

        tabLayout.setupWithViewPager(pager);
        Objects.requireNonNull(tabLayout.getTabAt(0));
        Objects.requireNonNull(tabLayout.getTabAt(1));
        Objects.requireNonNull(tabLayout.getTabAt(2));

        MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.music);
        mediaPlayer.start();
        mediaPlayer.setLooping(true);

        // Use ToolBar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    // Inflate the menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    // Add two actions
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        // Refresh (icon always visible):
        if (id == R.id.refresh) {
            // Show a toast
            Toast.makeText(this, "Refresh", Toast.LENGTH_SHORT).show();
            return true;
        }
        // Settings (always in the overflow menu):
        else if (id == R.id.setting) {
            // Starts a new activity, PrefActivity
            Intent intent = new Intent(this, PrefActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // Use thread and handler, simulate a network request and show on a toast
    public void request_network(){
        // Handler
        final Handler handler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                // This method is executed in main thread
                String content = msg.getData().getString(NETWORK_RESPONSE);
                Toast.makeText(WeatherActivity.this, content, Toast.LENGTH_SHORT).show();
            }
        };

        // Thread
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                // this method is run in a worker thread
                try {
                    // wait for 1 seconds to simulate a long network access
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // Assume that we got our data from server
                Bundle bundle = new Bundle();
                bundle.putString(NETWORK_RESPONSE, "Request Network");
                // notify main thread
                Message msg = new Message();
                msg.setData(bundle);
                handler.sendMessage(msg);
            }
        });
        t.start();
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