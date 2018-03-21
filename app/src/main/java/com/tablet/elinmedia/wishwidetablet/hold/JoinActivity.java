package com.tablet.elinmedia.wishwidetablet.hold;//package elinmedia.tablet.wishwide.hold;
//
//import android.graphics.Color;
//import android.support.v4.app.Fragment;
//import android.text.Spannable;
//import android.text.SpannableStringBuilder;
//import android.text.style.ForegroundColorSpan;
//import android.view.View;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//import elinmedia.tablet.wishwide.R;
//import elinmedia.tablet.wishwide.activity.WishwideFragmentActivity;
//
//public class JoinActivity extends WishwideFragmentActivity {
//    private static final String KEYPAD_TYPE = "birth";
//
//    private LinearLayout llJoinGuide;
//    private TextView tvJoinGuide;
//
//    @Override
//    protected void onStart() {
//        super.onStart();
////        llJoinGuide = (LinearLayout)findViewById(R.id.ll_join_guide);
////        tvJoinGuide = (TextView)findViewById(R.id.tv_join_guide);
//        String strJoinGuide = getResources().getString(R.string.joinGuide);
//
//        llJoinGuide.setVisibility(View.VISIBLE);
//
//        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(strJoinGuide);
//
//        spannableStringBuilder.setSpan(new ForegroundColorSpan(Color.BLACK), 0, 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//        spannableStringBuilder.setSpan(new ForegroundColorSpan(Color.BLACK), 4, 8, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//
//        tvJoinGuide.setText(spannableStringBuilder);
//    }
//
//    @Override
//    protected Fragment createContentFragment() {
//        return null;
//    }
//}
