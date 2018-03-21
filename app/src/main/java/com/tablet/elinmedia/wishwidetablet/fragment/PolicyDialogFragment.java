package com.tablet.elinmedia.wishwidetablet.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import com.tablet.elinmedia.wishwidetablet.R;

public class PolicyDialogFragment extends android.support.v4.app.DialogFragment {
    TextView tvPolicyDialog;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View policyDialog = LayoutInflater.from(getActivity())
                .inflate(R.layout.fragment_policy, null);

        tvPolicyDialog = (TextView) policyDialog.findViewById(R.id.tv_policy_dialog);

        return new AlertDialog.Builder(getActivity())
                .setView(policyDialog)
                .setTitle("이용약관 및 개인정보보호방침 보기")
                .setPositiveButton(R.string.btnConfirm, null)
                .create();
    }
}
