package com.tablet.elinmedia.wishwidetablet.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import com.tablet.elinmedia.wishwidetablet.R;
import com.tablet.elinmedia.wishwidetablet.WishwidePath;
import com.tablet.elinmedia.wishwidetablet.activity.LoginActivity;
import com.tablet.elinmedia.wishwidetablet.socket.NodeSocketClient;
import com.tablet.elinmedia.wishwidetablet.vo.Partner;

public class HeaderFragment extends Fragment implements WishwidePath {
    //위젯 선언
    ImageView imgLogo;
    TextView tvSession, tvHeaderTitle;
    ImageButton ibtnLogout;

    private String mTitle;

    public HeaderFragment(String title) {
        this.mTitle = title;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View headerView = inflater.inflate(R.layout.fragment_header, container, false);

        //위젯결합
        imgLogo = (ImageView) headerView.findViewById(R.id.img_logo);
        tvSession = (TextView) headerView.findViewById(R.id.tv_session);
        tvHeaderTitle = (TextView) headerView.findViewById(R.id.tv_header_title);
        ibtnLogout = (ImageButton) headerView.findViewById(R.id.ibtn_logout);


        Partner partner = NodeSocketClient.getSocketInstance().getPartner();
        Picasso.with(getActivity()).load(partner.getImgLogoUrl())
                .placeholder(R.drawable.logo)
                .error(R.drawable.logo)
                .into(imgLogo);

        tvHeaderTitle.setText(mTitle);
//        imgLogo.setImageResource(R.drawable.sample_logo);

        ibtnLogout.setVisibility(View.VISIBLE);


//        Customer customer = NodeSocketClient.getSocketInstance().getCustomer();
//
//        if (customer != null) {
//            tvSession.setText(customer.getStrCustomerPhone());
//            tvSession.setVisibility(View.VISIBLE);
//            ibtnLogout.setVisibility(View.VISIBLE);
//        }
//        else {
//            tvSession.setText("");
//            tvSession.setVisibility(View.GONE);
//            ibtnLogout.setVisibility(View.GONE);
//        }

//        tvSession.setText("01012345678");
//        tvSession.setVisibility(View.VISIBLE);
//        ibtnLogout.setVisibility(View.VISIBLE);

        //로그아웃 버튼 리스너
        ibtnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = NodeSocketClient.getSocketInstance().getmSharedPreferences();
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.commit();
                startActivity(new Intent(getActivity(), LoginActivity.class));
//                NodeSocketClient.getSocketInstance().resetCustomerInfo();
//                startActivity(new Intent(getActivity(), HomeActivity.class));
            }
        });

        return headerView;
    }
}
