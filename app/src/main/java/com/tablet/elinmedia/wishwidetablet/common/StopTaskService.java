package com.tablet.elinmedia.wishwidetablet.common;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.IBinder;
import android.util.Log;
import com.tablet.elinmedia.wishwidetablet.socket.NodeSocketClient;

public class StopTaskService extends Service {
    private static final String TAG = "StopTaskService";

    private NetworkStateMonitoringReceiver mNetworkStateMonitoringReceiver;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        mNetworkStateMonitoringReceiver = new NetworkStateMonitoringReceiver();
        registerReceiver(mNetworkStateMonitoringReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, TAG + " onDestroy()");
        super.onDestroy();

        NodeSocketClient nodeSocketClient = NodeSocketClient.getSocketInstance();

        if (NodeSocketClient.isSocketConnected) {
            nodeSocketClient.disconnectSocket();
            nodeSocketClient.clearResource();
        }
        else {
            nodeSocketClient.clearResource();
        }

        if (mNetworkStateMonitoringReceiver != null) {
            unregisterReceiver(mNetworkStateMonitoringReceiver);
        }
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        super.onTaskRemoved(rootIntent);
        Log.d(TAG, "onTaskRemoved()...");


        this.stopSelf();
    }
}
