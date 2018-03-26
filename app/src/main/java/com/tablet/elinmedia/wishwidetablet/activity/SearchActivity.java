package com.tablet.elinmedia.wishwidetablet.activity;

import android.support.v4.app.Fragment;
import android.view.MotionEvent;
import android.view.View;
import com.tablet.elinmedia.wishwidetablet.fragment.HeaderFragment;
import com.tablet.elinmedia.wishwidetablet.fragment.PointFragment;
import com.tablet.elinmedia.wishwidetablet.fragment.StampFragment;
import com.tablet.elinmedia.wishwidetablet.socket.NodeSocketClient;
import com.tablet.elinmedia.wishwidetablet.vo.Partner;

public class SearchActivity extends WishwideFragmentActivity {
    private static final String TAG = "SearchActivity";

//    private Timer mTimer;
//    private TextView mTvTime;
//    private View mTimerDialogView;
//
//    private int mReadyCount = 0;
//    private int mTimeCount = 5;

    @Override
    protected Fragment createHeaderFragment() {
        String benefitTypeCode = NodeSocketClient.getSocketInstance().getPartner().getStrBenefitTypeCode();

        if (benefitTypeCode.equals("S")) {
            return new HeaderFragment("내 도장 현황");
        }
        else if (benefitTypeCode.equals("P")) {
            return new HeaderFragment("내 포인트 현황");
        }

        return null;
    }

    @Override
    protected Fragment createContentFragment() {
        //도장, 포인트 프래그먼트 선택(파트너 혜택 타입으로!)
        String benefitTypeCode = NodeSocketClient.getSocketInstance().getPartner().getStrBenefitTypeCode();


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
//                                startActivity(new Intent(SearchActivity.this, HomeActivity.class));
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

        if (benefitTypeCode.equals("S")) {
            return new StampFragment();
        }
        else if (benefitTypeCode.equals("P")) {
            return new PointFragment();
        }

        return null;
    }


    //    @Override
//    public boolean onTouchEvent(MotionEvent event) {

//        if (event.getAction() == MotionEvent.ACTION_DOWN) {
//            Log.d(TAG, "onTouchEvent()...");
//
//            mReadyCount = 3;
//            mTimeCount = 5;
//
//            mTvTime.setText(mTimeCount + "초");
////        }
//
//        return true;
//    }
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
