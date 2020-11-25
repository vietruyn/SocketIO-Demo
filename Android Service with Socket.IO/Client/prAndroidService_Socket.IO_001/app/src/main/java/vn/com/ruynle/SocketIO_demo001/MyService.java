package vn.com.ruynle.SocketIO_demo001;

import android.app.Service;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.IBinder;
import android.util.Log;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;

import org.json.JSONObject;

import java.io.FileDescriptor;
import java.io.PrintWriter;

public class MyService extends Service {
    private static final String TAG = MyService.class.getSimpleName();
    private MyApplication application = null;

    public MyService() {

    }

    @Override
    public void onCreate() {
        super.onCreate();

        Log.i(TAG, MyService.class.getName() + " -> onCreate()");

        try {
            if (application == null)
                application = (MyApplication) getApplication();

            if (application.socketIO == null)
                application.socketIO = IO.socket(application.SERVER_URL);

            if (application != null && application.socketIO != null && !application.socketIO.connected()) {
                application.socketIO.on("chat_all", onNewMessage_ChatAll);

                application.socketIO.connect();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.i(TAG, MyService.class.getName() + " -> onBind()");
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, MyService.class.getName() + " -> onStartCommand(): Received start id " + startId + ": " + intent);

        // We want this service to continue running until it is explicitly
        // stopped, so return sticky.
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, MyService.class.getName() + " -> onDestroy()");

        if (application != null && application.socketIO != null && application.socketIO.connected()) {
            application.socketIO.disconnect();

            application.socketIO.off("chat_all", onNewMessage_ChatAll);
        }
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.i(TAG, MyService.class.getName() + " -> onUnbind()");
        return super.onUnbind(intent);
    }

    @Override
    public void onRebind(Intent intent) {
        Log.i(TAG, MyService.class.getName() + " -> onRebind()");
        super.onRebind(intent);
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        Log.i(TAG, MyService.class.getName() + " -> onTaskRemoved()");
        super.onTaskRemoved(rootIntent);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.i(TAG, MyService.class.getName() + " -> onConfigurationChanged()");
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        Log.i(TAG, MyService.class.getName() + " -> onLowMemory()");
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        Log.i(TAG, MyService.class.getName() + " -> onTrimMemory()");
    }

    @Override
    protected void dump(FileDescriptor fd, PrintWriter writer, String[] args) {
        super.dump(fd, writer, args);
        Log.i(TAG, MyService.class.getName() + " -> dump()");
    }

    private Emitter.Listener onNewMessage_ChatAll = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            try {
                if (application != null && application.socketIO != null)
                    application.socketIO.emit("client_receive_msg_all_success", new JSONObject().put("msg", "OK"));

                JSONObject data = (JSONObject) args[0];
                Log.i(TAG, data.toString());
                final String msg = data.getString("msg");

                if ((application.currentActivity != null))
                    if (application.currentActivity instanceof MainActivity) {
                        final MainActivity activity = (MainActivity) application.currentActivity;
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    if (activity.tvContentMessage != null)
                                        activity.tvContentMessage.setText(msg);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };
}
