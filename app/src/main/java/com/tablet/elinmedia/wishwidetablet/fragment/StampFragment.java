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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.tablet.elinmedia.wishwidetablet.R;
import com.tablet.elinmedia.wishwidetablet.activity.GiftBoxActivity;
import com.tablet.elinmedia.wishwidetablet.activity.HomeActivity;
import com.tablet.elinmedia.wishwidetablet.socket.NodeSocketClient;
import com.tablet.elinmedia.wishwidetablet.socket.NodeSocketClientConstant;
import com.tablet.elinmedia.wishwidetablet.vo.Customer;
import com.tablet.elinmedia.wishwidetablet.vo.Partner;

public class StampFragment extends Fragment implements NodeSocketClientConstant {
    private static final String TAG = "StampFragment";

    //위젯선언
    TextView tvStampGuide;
    LinearLayout llStamp;
    Button btnBenefitBox, mBtnLogout;
    private ImageView[] arrStampImgs = new ImageView[10];
    private Integer[] arrImgStampIds = {
            R.id.img_stamp1,
            R.id.img_stamp2,
            R.id.img_stamp3,
            R.id.img_stamp4,
            R.id.img_stamp5,
            R.id.img_stamp6,
            R.id.img_stamp7,
            R.id.img_stamp8,
            R.id.img_stamp9,
            R.id.img_stamp10
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View stampView = inflater.inflate(R.layout.fragment_stamp, container, false);

        //위젯결합
        tvStampGuide = (TextView) stampView.findViewById(R.id.tv_stamp_guide);
        llStamp = (LinearLayout) stampView.findViewById(R.id.ll_stamp);
        btnBenefitBox = (Button) stampView.findViewById(R.id.btn_benefit_box);
        mBtnLogout = (Button) stampView.findViewById(R.id.btn_logout);

        for (int i = 0; i < arrImgStampIds.length; i++) {
            arrStampImgs[i] = (ImageView) stampView.findViewById(arrImgStampIds[i]);
        }


        Intent intent = getActivity().getIntent();
        Customer customer = NodeSocketClient.getSocketInstance().getCustomer();
        Partner partner = NodeSocketClient.getSocketInstance().getPartner();


//        int stampNowCnt = 3;
        int stampNowCnt = customer.getiStampNowCnt();
        int stampGivingCnt = customer.getiStampGivingCnt();
        Log.d(TAG, "stampNowCnt: " + stampNowCnt);

        //내 선물/쿠폰함 가기 버튼 리스너
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


        String guide = "";
        int i;

        for (i = 0; i < stampNowCnt; i++) {
//            Log.d(TAG, "full: " + i);
            arrStampImgs[i].setImageResource(R.drawable.full_stamp);      // 이미지를 등록한다.
        }

        for (; i < 10; i++) {
//            Log.d(TAG, "empty: " + i);
            arrStampImgs[i].setImageResource(R.drawable.empty_stamp);      // 이미지를 등록한다.
        }

//        guide = "01012345689" + "님은 지금까지 쿠폰 도장 " + stampNowCnt + "개를 모으셨습니다!!";
//        guide = "01012345689" + "님, 축하드립니다!\n 모+으신 쿠폰 도장 19개는 선물함에 선물로 제공되었습니다.\n 쿠폰 도장 " + stampNowCnt + "개를 모으셨습니다!!";

        switch (intent.getStringExtra("responseCode")) {
            case SUCCESS_RESPONSE_CODE:
                guide = customer.getStrCustomerPhone().replaceAll("(\\d{3})(\\d{3,4})(\\d{4})", "$1-****-$3") + "님은 현재까지 쿠폰 도장 " + stampNowCnt + "개를 모으셨습니다!!";
                break;
            case SAVING_AND_COUPON_RESPONSE_CODE:
                guide = customer.getStrCustomerPhone().replaceAll("(\\d{3})(\\d{3,4})(\\d{4})", "$1-****-$3") + "님은 새로 쿠폰 도장 " + stampGivingCnt + "개를 받으셨습니다. \n현재까지 적립된 쿠폰 도장 수는 총 " + stampNowCnt + "개 입니다!!";
                break;
        }


        tvStampGuide.setText(guide);


        return stampView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }
}
