package com.tablet.elinmedia.wishwidetablet.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.telephony.PhoneNumberUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.tablet.elinmedia.wishwidetablet.R;
import com.tablet.elinmedia.wishwidetablet.activity.GiftBoxActivity;
import com.tablet.elinmedia.wishwidetablet.activity.HomeActivity;
import com.tablet.elinmedia.wishwidetablet.socket.NodeSocketClient;
import com.tablet.elinmedia.wishwidetablet.socket.NodeSocketClientConstant;
import com.tablet.elinmedia.wishwidetablet.vo.Customer;

public class PointFragment extends Fragment implements NodeSocketClientConstant {
    private static final String TAG = "StampFragment";

    //위젯선언
    private TextView tvPointGuide;
    private TextView tvPointSaving, tvPointDeduct, tvTotalPoint;
    private Button btnBenefitBox, mBtnLogout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View pointView = inflater.inflate(R.layout.fragment_point, container, false);

        //위젯결합
        tvPointGuide = (TextView) pointView.findViewById(R.id.tv_point_guide);
        tvPointSaving = (TextView) pointView.findViewById(R.id.tv_point_saving);
        tvPointDeduct = (TextView) pointView.findViewById(R.id.tv_point_deduct);
        tvTotalPoint = (TextView) pointView.findViewById(R.id.tv_total_point);
        btnBenefitBox = (Button) pointView.findViewById(R.id.btn_benefit_box);
        mBtnLogout = (Button) pointView.findViewById(R.id.btn_logout);

        Customer customer = NodeSocketClient.getSocketInstance().getCustomer();

        final int pointInsCnt = customer.getiPointInsCnt();
        final int pointUseCnt = customer.getiPointUseCnt();
        final int pointAllCnt = customer.getiPointAllCnt();

        //내 쿠폰/선물함 가기 버튼 리스터
        btnBenefitBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), GiftBoxActivity.class));
            }
        });


        mBtnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //고객 로그아웃
                NodeSocketClient.getSocketInstance().resetCustomerInfo();
                Intent intent = new Intent(getActivity(), HomeActivity.class);
                intent.addFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

//        int pointInsCnt = 50;
//        int pointUseCnt = 500;
//        int pointAllCnt = 6500;

        tvPointSaving.setText(String.valueOf(pointInsCnt) + "P");
        tvPointDeduct.setText(String.valueOf(pointUseCnt) + "P");
        tvTotalPoint.setText(String.valueOf(pointAllCnt) + "P");

        Log.d(TAG, "pointInsCnt: " + pointInsCnt);
        Log.d(TAG, "pointUseCnt: " + pointUseCnt);
        Log.d(TAG, "pointAllCnt: " + pointAllCnt);

        String guide = "";

        guide = PhoneNumberUtils.formatNumber(customer.getStrCustomerPhone()).replace(customer.getStrCustomerPhone().substring(3, 7), "****") + "님의 현재 잔여 포인트는 " + pointAllCnt + "P 입니다!!";
//        guide = "01012345688" + "님의 현재 잔여 포인트는 " + pointAllCnt + "P 입니다!!";

        tvPointGuide.setText(guide);

        return pointView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }
}
