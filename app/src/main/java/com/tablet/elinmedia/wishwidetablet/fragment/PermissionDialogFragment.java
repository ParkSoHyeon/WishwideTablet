package com.tablet.elinmedia.wishwidetablet.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import com.tablet.elinmedia.wishwidetablet.R;
import com.tablet.elinmedia.wishwidetablet.socket.NodeSocketClient;

public class PermissionDialogFragment extends DialogFragment {
    public final static String DIALOG_TAG = "com.elinmedia.adwide.common.PermissionDialogFragment";
    private static Context mContext;

    public static PermissionDialogFragment newInstance(Context context) {
        PermissionDialogFragment dialogFragment = new PermissionDialogFragment();

        mContext = context;

        return dialogFragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new AlertDialog.Builder(getActivity())
                .setTitle(R.string.dialog_title)
                .setMessage(R.string.permission_dialog_msg)
                .setPositiveButton(R.string.dialog_ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        NodeSocketClient nodeSocketClient = NodeSocketClient.getSocketInstance();

                        if (NodeSocketClient.isSocketConnected) {
                            nodeSocketClient.disconnectSocket();
                            nodeSocketClient.clearResource();
                        }

                        //앱 종료
                        if (mContext instanceof Activity) {
                            ((Activity) mContext).finish();
                        }
                    }
                })
                .create();
    }
}
