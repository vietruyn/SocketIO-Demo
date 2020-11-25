package vn.com.ruynle.SocketIO_demo001;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private MyApplication application = null;
    public TextView tvContentMessage = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.i(TAG, MainActivity.class.getName() + " -> onCreate()");

        application = ((MyApplication) getApplication());
        application.currentActivity = this;

        tvContentMessage = (TextView) findViewById(R.id.tvContentMessage);
    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.i(TAG, MainActivity.class.getName() + " -> onResume()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Log.i(TAG, MainActivity.class.getName() + " -> onDestroy()");

        if (application != null)
            application.currentActivity = null;
    }
}
