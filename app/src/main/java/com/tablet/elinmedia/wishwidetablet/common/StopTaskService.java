package com.tablet.elinmedia.wishwidetablet.common;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import com.tablet.elinmedia.wishwidetablet.socket.NodeSocketClient;

public class StopTaskService extends Service {
    private static final String TAG = "StopTaskService";
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        super.onTaskRemoved(rootIntent);
        Log.d(TAG, "onTaskRemoved()...");
        NodeSocketClient nodeSocketClient = NodeSocketClient.getSocketInstance();

        if (NodeSocketClient.isSocketConnected) {
            nodeSocketClient.disconnectSocket();
            nodeSocketClient.clearResource();
        }
        else {
            nodeSocketClient.clearResource();
        }

        this.stopSelf();
    }
}
