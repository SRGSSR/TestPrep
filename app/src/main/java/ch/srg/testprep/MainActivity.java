package ch.srg.testprep;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView status;

    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        WifiManager wfm = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        status = ((TextView) findViewById(R.id.status));
        try {
            wfm.setWifiEnabled(true);
            status.setText("Wifi connecting");
        } catch (Exception ignored) {
            status.setText("Wifi error");
        }

        update();
    }

    private void scheduleUpdate() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                update();
            }
        }, 1000);
    }

    private void update() {
        if (isOnline()) {
            status.setText("Wifi connected");
            finish();
        } else {
            scheduleUpdate();
        }
    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnected();
    }
}
