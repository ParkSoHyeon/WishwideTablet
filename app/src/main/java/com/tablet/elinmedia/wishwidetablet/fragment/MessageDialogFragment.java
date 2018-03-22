package com.tablet.elinmedia.wishwidetablet.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import com.tablet.elinmedia.wishwidetablet.R;

public class MessageDialogFragment extends DialogFragment {
    public final static String DIALOG_TAG = "com.elinmedia.adwide.common.MessageDialogFragment";
    private String mMsg;

    public static MessageDialogFragment newInstance() {
        MessageDialogFragment dialogFragment = new MessageDialogFragment();

        return dialogFragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new AlertDialog.Builder(getActivity())
                .setTitle(R.string.dialog_title)
                .setMessage(mMsg)
                .setPositiveButton(R.string.dialog_ok, null)
                .create();
    }


    public String getmMsg() {
        return mMsg;
    }

    public void setmMsg(String mMsg) {
        this.mMsg = mMsg;
    }
}
