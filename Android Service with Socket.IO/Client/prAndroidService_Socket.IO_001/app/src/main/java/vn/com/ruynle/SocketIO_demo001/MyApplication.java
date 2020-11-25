package vn.com.ruynle.SocketIO_demo001;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.support.multidex.MultiDex;
import android.util.Log;

import com.github.nkzawa.socketio.client.Socket;

import static android.content.Intent.ACTION_MAIN;

public class MyApplication extends Application {
    private static final String TAG = MyApplication.class.getSimpleName();
    public final String SERVER_URL = "http://192.168.1.103:3000";
    public Socket socketIO = null;
    public Activity currentActivity = null;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);

        MultiDex.install(this);

        Log.i(TAG, MyApplication.class.getName() + " -> attachBaseContext()");
    }

    @Override
    public void onCreate() {
        super.onCreate();

        startService(new Intent(ACTION_MAIN).setClass(this, MyService.class));

        Log.i(TAG, MyApplication.class.getName() + " -> onCreate()");
    }
}
