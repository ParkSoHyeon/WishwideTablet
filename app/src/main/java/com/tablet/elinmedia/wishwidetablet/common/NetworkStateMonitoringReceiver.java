package com.tablet.elinmedia.wishwidetablet.common;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.IBinder;
import android.util.Log;

import static android.content.Context.CONNECTIVITY_SERVICE;

public class NetworkStateMonitoringReceiver
        extends BroadcastReceiver implements SharedPreferencesConstant {
    private static final String TAG = "NetworkStateMonitor";
    private Activity mActivity;

    private SharedPreferences mSharedPreferences;

    private IBinder mService;


    public NetworkStateMonitoringReceiver(IBinder service) {
        super();
        mService = service;
    }

    public NetworkStateMonitoringReceiver(Activity activity) {
        this.mActivity = activity;
    }


    @Override
    public void onReceive(Context context, Intent intent) {
        String action= intent.getAction();

        if (action.equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
            try {
                ConnectivityManager connectivityManager =
                        (ConnectivityManager) context.getSystemService(CONNECTIVITY_SERVICE);

                boolean isNetworkAvailable = connectivityManager.getActiveNetworkInfo() != null;
                boolean isNetworkConnected = isNetworkAvailable && connectivityManager.getActiveNetworkInfo().isConnected();

                if (isNetworkConnected) {
                    Log.i(TAG, "네트워크 연결됨");
//                    sendNetworkConnectMessageToService();

                    //node server 최신 스케줄 여부 요청
//                    mSharedPreferences = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
//                    String deviceId = mSharedPreferences.getString(MANAGER_DEVICE_ID_KEY, "");
//                    String deviceMacAddress = mSharedPreferences.getString(MANAGER_DEVICE_MAC_ADDRESS_KEY, "");

//                    Log.d(TAG, "소켓 통신 전, 디바이스 아이디 확인: " + deviceId);
//                    Log.d(TAG, "소켓 통신 전, 디바이스 맥주소 확인: " + deviceMacAddress);

                    //최신 스케줄, 디바이스아이디 보냄(+디바이스맥주소)
//                    NodeSocketClient nodeSocketClient = new NodeSocketClient();
                }
                else {
                    Log.i(TAG, "네트워크 끊김");
//                    sendNetworkDisconnectMessageToService();
                }
            } catch (Exception e) {
                e.getStackTrace();
                Log.e(TAG, "네트워크 상태 모니터링 리시버 에러 발생");
            }
        }
    }

//    private void sendNetworkDisconnectMessageToService() {
//        Log.i(TAG, "NetworkingStateMonitor -> Service, 핸들러 메시지 보냄");
//        if (ScheduleService.isRunning()) {
//            Messenger serviceMessenger = new Messenger(mService);
//
//            try {
//                Message message = Message.obtain(null, ScheduleService.MSG_NETWORK_DISCONNECT);
//                message.replyTo = serviceMessenger;
//                serviceMessenger.send(message);
//            } catch (RemoteException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    private void sendNetworkConnectMessageToService() {
//        Log.i(TAG, "NetworkingStateMonitor -> Service, 핸들러 메시지 보냄");
//        if (ScheduleService.isRunning()) {
//            Messenger serviceMessenger = new Messenger(mService);
//
//            try {
//                Message message = Message.obtain(null, ScheduleService.MSG_NETWORK_CONNECT);
//                message.replyTo = serviceMessenger;
//                serviceMessenger.send(message);
//            } catch (RemoteException e) {
//                e.printStackTrace();
//            }
//        }
//    }

//    private boolean isNetworkAvailableAndConnected() {
//        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
//
//        boolean isNetworkAvailable = connectivityManager.getActiveNetworkInfo() != null;
//        boolean isNetworkConnected = isNetworkAvailable && connectivityManager.getActiveNetworkInfo().isConnected();
//
//        return isNetworkConnected;
//    }

//    private void checkAvailableConnection() {
//        ConnectivityManager connMgr = (ConnectivityManager) this
//                .getSystemService(Context.CONNECTIVITY_SERVICE);
//
//        final android.net.NetworkInfo wifi = connMgr
//                .getNetworkInfo(ConnectivityManager.TYPE_WIFI);
//
//        final android.net.NetworkInfo mobile = connMgr
//                .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
//
//        if (wifi.isAvailable()) {
//
//            WifiManager myWifiManager = (WifiManager) getSystemService(WIFI_SERVICE);
//            WifiInfo myWifiInfo = myWifiManager.getConnectionInfo();
//            int ipAddress = myWifiInfo.getIpAddress();
//
//            System.out.println("WiFi address is "
//                    + android.text.format.Formatter.formatIpAddress(ipAddress));
//
//
//        } else if (mobile.isAvailable()) {
//
//            GetLocalIpAddress();
//        } else {
//
//        }
//    }

//    private String GetLocalIpAddress() {
//        try {
//            for (Enumeration<NetworkInterface> en = NetworkInterface
//                    .getNetworkInterfaces(); en.hasMoreElements(); ) {
//                NetworkInterface intf = en.nextElement();
//                for (Enumeration<InetAddress> enumIpAddr = intf
//                        .getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
//                    InetAddress inetAddress = enumIpAddr.nextElement();
//                    if (!inetAddress.isLoopbackAddress()) {
//                        return inetAddress.getHostAddress().toString();
//                    }
//                }
//            }
//        } catch (SocketException ex) {
//            return "ERROR Obtaining IP";
//        }
//        return "No IP Available";
//    }
}
