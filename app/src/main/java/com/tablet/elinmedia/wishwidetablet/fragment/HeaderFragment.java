package com.tablet.elinmedia.wishwidetablet.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import com.tablet.elinmedia.wishwidetablet.R;
import com.tablet.elinmedia.wishwidetablet.common.WishwidePath;
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

        NodeSocketClient.getSocketInstance().setmActivity(getActivity());
        Partner partner = NodeSocketClient.getSocketInstance().getPartner();
        Picasso.with(getActivity()).load(partner.getImgLogoUrl())
                .placeholder(R.drawable.logo)
                .error(R.drawable.logo)
                .into(imgLogo);

        tvHeaderTitle.setText(mTitle);
        ibtnLogout.setVisibility(View.VISIBLE);


        //로그아웃 버튼 리스너
        ibtnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PasswordDialogFragment dialog = PasswordDialogFragment.newInstance();
                dialog.show(getFragmentManager(), PermissionDialogFragment.DIALOG_TAG);

            }
        });

        return headerView;
    }
}
