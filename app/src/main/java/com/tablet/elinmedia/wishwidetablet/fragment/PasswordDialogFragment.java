package com.tablet.elinmedia.wishwidetablet.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.tablet.elinmedia.wishwidetablet.R;
import com.tablet.elinmedia.wishwidetablet.activity.LoginActivity;
import com.tablet.elinmedia.wishwidetablet.common.BaseApplication;
import com.tablet.elinmedia.wishwidetablet.common.StopTaskService;
import com.tablet.elinmedia.wishwidetablet.socket.NodeSocketClient;

public class PasswordDialogFragment extends DialogFragment {
    public final static String TAG = "PasswordDialogFragment";
    public final static String DIALOG_TAG = "com.elinmedia.adwide.common.PasswordDialogFragment";
    private TextView mTvId;
    private EditText mEdtPassword;
    private Button mBtnOK, mBtnCancle;

    public static PasswordDialogFragment newInstance() {
        PasswordDialogFragment dialogFragment = new PasswordDialogFragment();

        return dialogFragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View passwordDialog = LayoutInflater.from(getActivity())
                .inflate(R.layout.password_confirm_dialog, null);

        mTvId = (TextView) passwordDialog.findViewById(R.id.tv_id);
        mEdtPassword = (EditText) passwordDialog.findViewById(R.id.edt_password);
        mBtnOK = (Button) passwordDialog.findViewById(R.id.btn_ok);
        mBtnCancle = (Button) passwordDialog.findViewById(R.id.btn_cancle);

        final NodeSocketClient nodeSocketClient = NodeSocketClient.getSocketInstance();

        mTvId.setText(nodeSocketClient.getPartner().getStrWideManagerId());

        mBtnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nodeSocketClient.getManagerPassword().equals(mEdtPassword.getText().toString())) {
                   //저장되어 있는 아이디, 비밀번호, 디바이스 아이디 정보 clear
//                    SharedPreferences sharedPreferences = nodeSocketClient.getmSharedPreferences();
//                    SharedPreferences.Editor editor = sharedPreferences.edit();
//                    editor.clear();
//                    editor.commit();

                    //소켓 통신 연결 끊기 및 자원 반납
                    if (NodeSocketClient.isSocketConnected) {
                        Log.d(TAG, "소켓 연결 끊기 및 자원 반납");
                        nodeSocketClient.disconnectSocket();
                    }

                    nodeSocketClient.clearResource();
                    getActivity().stopService(new Intent(getContext(), StopTaskService.class));
                    System.exit(0);

                    //로그인 페이지로 이동
//                    Intent intent = new Intent(getActivity(), LoginActivity.class);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    startActivity(intent);

                    //앱 종료
//                    Context context = BaseApplication.getInstance().getmContext();
//                    if (context instanceof Activity) {
//                        ((Activity) context).finish();
//                    }
                }
                else {
                    showDialog("비밀번호를 다시 확인해주세요.");
                }
            }
        });

        mBtnCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        return new AlertDialog.Builder(getActivity())
                .setView(passwordDialog)
                .create();
    }


    private void showDialog(String msg) {
        MessageDialogFragment messageDialogFragment = MessageDialogFragment.newInstance();
        messageDialogFragment.setmMsg(msg);
        messageDialogFragment.show(getFragmentManager(), MessageDialogFragment.DIALOG_TAG);
    }

}
