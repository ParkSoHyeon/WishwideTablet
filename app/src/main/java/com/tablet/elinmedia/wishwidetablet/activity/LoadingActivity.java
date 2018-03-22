package com.tablet.elinmedia.wishwidetablet.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import com.tablet.elinmedia.wishwidetablet.common.BaseApplication;
import com.tablet.elinmedia.wishwidetablet.R;
import com.tablet.elinmedia.wishwidetablet.common.SharedPreferencesConstant;
import com.tablet.elinmedia.wishwidetablet.common.StopTaskService;
import com.tablet.elinmedia.wishwidetablet.fragment.PermissionDialogFragment;
import com.tablet.elinmedia.wishwidetablet.socket.NodeSocketClient;

public class LoadingActivity
        extends AppCompatActivity implements SharedPreferencesConstant {
    private static final String TAG = "LoadingActivity";

    private SharedPreferences mSharedPreferences;

    //권한 관련 멤버 변수
    private final int PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 1;
    private static int mPermissionCheck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        startService(new Intent(this, StopTaskService.class));

        //파일 권한 확인
//        mPermissionCheck = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
//
//        //권한 없음
//        if (mPermissionCheck == PackageManager.PERMISSION_DENIED) {
//            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
//        }
//        else {
        mSharedPreferences = this.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
        BaseApplication.getInstance().setmContext(getApplicationContext());

        //preferences shared 사용해 로그인 이력 있는지 확인
        String managerId = mSharedPreferences.getString(MANAGER_ID_KEY, "");
        String managerPassword = mSharedPreferences.getString(MANAGER_PASSWORD_KEY, "");
        String deviceId = mSharedPreferences.getString(MANAGER_DEVICE_ID_KEY, "");

        Log.d(TAG, "관리자 아이디: " + managerId);
        Log.d(TAG, "관리자 비밀번호: " + managerPassword);
        Log.d(TAG, "관리자 디바이스 아이디: " + deviceId);

//        startActivity(new Intent(LoadingActivity.this, LoginActivity.class));

        //없으면 로그인 activity로 이동
        if (TextUtils.isEmpty(managerId) || TextUtils.isEmpty(managerPassword) || TextUtils.isEmpty(deviceId)) {
            Log.d(TAG, "자동 로그인 실패, 로그인 페이지로 이동");
            startActivity(new Intent(LoadingActivity.this, LoginActivity.class));
        }
        else {
            //있으면 WebServer 로그인 인증
            Log.d(TAG, "자동 로그인 Webserver 접속 시도");
            NodeSocketClient nodeSocketClient = NodeSocketClient.getSocketInstance();
            nodeSocketClient.setManagerId(managerId);
            nodeSocketClient.setManagerPassword(managerPassword);
            nodeSocketClient.setDeviceId(deviceId);
            nodeSocketClient.setmSharedPreferences(mSharedPreferences);

            nodeSocketClient.connectSocket();
        }
//        }
    }


    //권한 요청
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //권한허가
//                    finish();
//                    startActivity(new Intent(getApplicationContext(), LoadingActivity.class));
//                    showDialog();
                } else {
                    //권한거부
                    //다이얼로그 띄우고 앱 종료
//                    showDialog();
                }
        }
    }

    private void showDialog() {
        PermissionDialogFragment dialog = PermissionDialogFragment.newInstance(this);
        dialog.show(getSupportFragmentManager(), PermissionDialogFragment.DIALOG_TAG);
    }

    public static boolean checkPermission() {
        return mPermissionCheck == PackageManager.PERMISSION_GRANTED;
    }
}