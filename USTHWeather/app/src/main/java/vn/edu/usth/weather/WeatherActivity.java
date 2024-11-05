package vn.edu.usth.weather;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
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

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Objects;

public class WeatherActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;
    private ImageView logoImageView;

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

        mediaPlayer = MediaPlayer.create(this, R.raw.music);
        mediaPlayer.start();
        mediaPlayer.setLooping(true);

        // Use ToolBar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        logoImageView = findViewById(R.id.usth_logo);

        if (logoImageView == null) {
            Log.e(TAG, "logoImageView is null. Check the layout file.");
        }

        // Start the AsyncTask to download the logo
        new DownloadImageTask().execute();

        // Start AsyncTask for network request simulation
        new RequestNetworkTask().execute();
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
//    public void request_network(){
////        // Handler
////        final Handler handler = new Handler(Looper.getMainLooper()) {
////            @Override
////            public void handleMessage(Message msg) {
////                // This method is executed in main thread
////                String content = msg.getData().getString(NETWORK_RESPONSE);
////                Toast.makeText(WeatherActivity.this, content, Toast.LENGTH_SHORT).show();
////            }
////        };
////
////        // Thread
////        Thread t = new Thread(new Runnable() {
////            @Override
////            public void run() {
////                // this method is run in a worker thread
////                try {
////                    // wait for 1 seconds to simulate a long network access
////                    Thread.sleep(1000);
////                } catch (InterruptedException e) {
////                    e.printStackTrace();
////                }
////                // Assume that we got our data from server
////                Bundle bundle = new Bundle();
////                bundle.putString(NETWORK_RESPONSE, "Request Network");
////                // notify main thread
////                Message msg = new Message();
////                msg.setData(bundle);
////                handler.sendMessage(msg);
////            }
////        });
////        t.start();
//
//        // «Upgrade» Thread/Handler combo with an AsyncTask
//        AsyncTask<Void, Void, String> task = new AsyncTask<Void, Void, String>() {
//            // sleep() in doInBackground
//            @Override
//            protected String doInBackground(Void... voids) {
//                try {
//                    // wait for 1 seconds to simulate a long network access
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                return "Request Network";
//            }
//
//            // Toast in onPostExecute()
//            @Override
//            protected void onPostExecute(String content) {
//                Toast.makeText(WeatherActivity.this, content, Toast.LENGTH_SHORT).show();
//            }
//        };
//
//        task.execute();
//    }

    // «Upgrade» the previous AsyncTask
    private class RequestNetworkTask extends AsyncTask<Void, Void, String> {
        // sleep() in doInBackground
        @Override
        protected String doInBackground(Void... voids) {
            try {
                // wait for 1 seconds to simulate a long network access
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "Request Network";
        }

        // Toast in onPostExecute()
        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(WeatherActivity.this, result, Toast.LENGTH_SHORT).show();
        }
    }

    // Perform a real network request to USTH’s server
    private class DownloadImageTask extends AsyncTask<Void, Void, Bitmap> {
        // Download USTH logo
        @Override
        protected Bitmap doInBackground(Void... voids) {
            Bitmap bitmap = null;
            try {
                // Initialize URL
                URL url = new URL("https://cdn.haitrieu.com/wp-content/uploads/2022/11/Logo-Truong-Dai-hoc-Khoa-hoc-va-Cong-nghe-Ha-Noi.png");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                connection.setRequestMethod("GET");
                connection.setDoInput(true);
                connection.connect();

                // Receive response
                int response = connection.getResponseCode();
                Log.i("USTHWeather", "The response is: " + response);

                if (response == HttpURLConnection.HTTP_OK) { // Check if response is OK
                    InputStream is = connection.getInputStream();
                    // Process image response
                    bitmap = BitmapFactory.decodeStream(is);
                    is.close(); // Close the InputStream
                }

                connection.disconnect();
            } catch (Exception e) {
                Log.e("USTHWeather", "Error downloading image: " + e.getMessage());
            }
            return bitmap; // Return the bitmap
        }

        // Show it on an ImageView of ForecastFragment
        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if (bitmap != null && logoImageView != null) {
                logoImageView.setImageBitmap(bitmap); // Set the bitmap to the ImageView
                Toast.makeText(WeatherActivity.this, "Image Loaded", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(WeatherActivity.this, "Failed to load image", Toast.LENGTH_SHORT).show();
            }
        }
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
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("Destroy","Destroy");
    }
}