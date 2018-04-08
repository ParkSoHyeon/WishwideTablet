package com.tablet.elinmedia.wishwidetablet.activity;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.*;
import android.widget.TextView;
import com.tablet.elinmedia.wishwidetablet.R;
import com.tablet.elinmedia.wishwidetablet.fragment.GiftBoxFragment;
import com.tablet.elinmedia.wishwidetablet.fragment.HeaderFragment;

import java.util.Timer;
import java.util.TimerTask;

public class GiftBoxActivity extends WishwideFragmentActivity {
    private static final String TAG = "GiftBoxActivity";

    @Override
    protected Fragment createHeaderFragment() {
        return new HeaderFragment("내 쿠폰/선물함");
    }


    @Override
    protected Fragment createContentFragment() {

        return new GiftBoxFragment();
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {

//        if (event.getAction() == MotionEvent.ACTION_DOWN) {
//            Log.d(TAG, "onTouchEvent()...");
//
//            mReadyCount = 3;
//            mTimeCount = 5;
//            mTvTime.setText(mTimeCount + "초");
//
//        }

        return true;
    }
//
//    void showCard() {
//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                // if scard is already visible with same VuMark, do nothing
//                if (mTimerDialogView.getVisibility() == View.VISIBLE) {
//                    return;
//                }
//
//                mTimerDialogView.bringToFront();
//                mTimerDialogView.setVisibility(View.VISIBLE);
//            }
//        });
//    }
//
//    void hideCard() {
//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                // if card not visible, do nothing
//                if (mTimerDialogView.getVisibility() != View.VISIBLE) {
//                    return;
//                }
//
//                mTimerDialogView.setVisibility(View.INVISIBLE);
//                // mUILayout.invalidate();
//            }
//        });
//    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    protected void onDestroy() {
        Log.d(TAG, TAG + " onDestroy()...");
        super.onDestroy();
    }
}
