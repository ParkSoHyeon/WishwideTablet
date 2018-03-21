package com.tablet.elinmedia.wishwidetablet.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TabHost;
import android.widget.TextView;
import com.tablet.elinmedia.wishwidetablet.R;
import com.tablet.elinmedia.wishwidetablet.socket.NodeSocketClient;
import com.tablet.elinmedia.wishwidetablet.vo.Partner;
import org.w3c.dom.Node;

public class PolicyDialogFragment extends android.support.v4.app.DialogFragment {
    TabHost tabHost;
    TextView tvPolicy, tvPrivacyPolicy;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View policyDialog = LayoutInflater.from(getActivity())
                .inflate(R.layout.fragment_policy, null);

        tabHost = (TabHost) policyDialog.findViewById(R.id.tab_host);
        tvPolicy = (TextView) policyDialog.findViewById(R.id.tv_policy);
        tvPrivacyPolicy = (TextView) policyDialog.findViewById(R.id.tv_privacy_policy);

        tabHost.setup();

        TabHost.TabSpec tabSpec1 = tabHost.newTabSpec("이용약관");
        tabSpec1.setContent(R.id.content1);
        tabSpec1.setIndicator("이용약관");
        tabHost.addTab(tabSpec1);

        TabHost.TabSpec tabSpec2 = tabHost.newTabSpec("개인정보보호방침");
        tabSpec2.setContent(R.id.content2);
        tabSpec2.setIndicator("개인정보보호방침");
        tabHost.addTab(tabSpec2);

        Partner partner = NodeSocketClient.getSocketInstance().getPartner();

        tvPolicy.setText(Html.fromHtml(partner.getStrPolicy()));
        tvPrivacyPolicy.setText(Html.fromHtml(partner.getStrPrivacyPolicy()));

        return new AlertDialog.Builder(getActivity())
                .setView(policyDialog)
//                .setTitle("이용약관 및 개인정보보호방침 보기")
                .setPositiveButton(R.string.btnConfirm, null)
                .create();
    }
}
