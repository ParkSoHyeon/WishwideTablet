package com.tablet.elinmedia.wishwidetablet.activity;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.tablet.elinmedia.wishwidetablet.R;
import com.tablet.elinmedia.wishwidetablet.fragment.GiftBoxFragment;
import com.tablet.elinmedia.wishwidetablet.fragment.HeaderFragment;

import java.util.Timer;
import java.util.TimerTask;

public class GiftBoxActivity extends WishwideFragmentActivity {
    private static final String TAG = "GiftBoxActivity";

//    private Timer mTimer;
//    private TextView mTvTime;
//    private View mTimerDialogView;
//
//    private int mReadyCount = 0;
//    private int mTimeCount = 5;


    @Override
    protected Fragment createHeaderFragment() {
        return new HeaderFragment("선물함");
    }


    @Override
    protected Fragment createContentFragment() {
//        ViewGroup.LayoutParams layoutParamsControl = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//        LayoutInflater inflater = getLayoutInflater();
//        mTimerDialogView = inflater.inflate(R.layout.timer_dialog, null);
//        mTimerDialogView.setVisibility(View.GONE);
//
//        addContentView(mTimerDialogView, layoutParamsControl);
//
//        mTvTime = (TextView) mTimerDialogView.findViewById(R.id.tv_time);
//
//        mTimer = new Timer();
//        mTimer.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        if (mReadyCount < 1) {
//                            showCard();
//
//                            if (mTimeCount < 1) {
//                                mTimer.cancel();
//                                mTimer = null;
//
//                                startActivity(new Intent(GiftBoxActivity.this, HomeActivity.class));
//                            }
//
//                            mTvTime.setText("" + mTimeCount);
//                            mTimeCount--;
//                        }
//                        else {
//                            mReadyCount--;
//                            hideCard();
//                        }
//
//                    }
//                });
//
//            }
//        }, 3000, 1000);
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
}
