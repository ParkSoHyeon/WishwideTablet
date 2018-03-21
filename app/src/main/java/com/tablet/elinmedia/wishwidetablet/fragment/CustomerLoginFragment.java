package com.tablet.elinmedia.wishwidetablet.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.telephony.PhoneNumberUtils;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.tablet.elinmedia.wishwidetablet.R;
import com.tablet.elinmedia.wishwidetablet.socket.NodeSocketClient;
import com.tablet.elinmedia.wishwidetablet.socket.NodeSocketClientConstant;

public class CustomerLoginFragment extends Fragment implements NodeSocketClientConstant{
    private static final String TAG = "CustomerLoginFragment";

    private static final String JOIN_DIALOG = "joinDialog";
    private static final String POLICY_DIALOG = "policyDialog";
    private static final int REQUEST_DATE = 0;

    private String mRealPhone = "";

    private ImageView imgBenefitGuide;
    private TextView tvPolicy;
    private LinearLayout llInputPhone;
    private EditText edtPhone;
    private Button[] arrNumBtns = new Button[10];
    private Integer[] arrBtnNumIds = {
            R.id.btn_num0,
            R.id.btn_num1,
            R.id.btn_num2,
            R.id.btn_num3,
            R.id.btn_num4,
            R.id.btn_num5,
            R.id.btn_num6,
            R.id.btn_num7,
            R.id.btn_num8,
            R.id.btn_num9
    };
    private Button btnDelete, btnOk;
    private int i;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View customerLoginView = inflater.inflate(R.layout.fragment_customer_login, container, false);

        //위젯 결합
        llInputPhone = (LinearLayout) customerLoginView.findViewById(R.id.ll_input_phone);
        edtPhone = (EditText) customerLoginView.findViewById(R.id.edt_phone);
        btnDelete = (Button) customerLoginView.findViewById(R.id.btn_delete);
        btnOk = (Button) customerLoginView.findViewById(R.id.btn_ok);

        for (i = 0; i < arrBtnNumIds.length; i++) {
            arrNumBtns[i] = (Button) customerLoginView.findViewById(arrBtnNumIds[i]);
        }

        //번호키패드 리스너
        for (i = 0; i < arrBtnNumIds.length; i++) {
            final int index;
            index = i;

            arrNumBtns[index].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (edtPhone.isFocused()) {
                        //핸드폰 입력창
                        setEditText(edtPhone, index);

//                        if(edtPhone.length() > 13) {
//                            btnOk.setTextIsSelectable(true);
//                            btnOk.requestFocus();
//                        }
                    }
                }
            });
        }

        //EditText 창 눌러도 키보드 안나타나게..
//        edtPhone.setClickable(false);
        edtPhone.setCursorVisible(false);
        edtPhone.setFocusable(false);
        edtPhone.setRawInputType(InputType.TYPE_CLASS_NUMBER);
        edtPhone.setTextIsSelectable(true);
        edtPhone.setText("010-");
        edtPhone.setSelection(4);

        imgBenefitGuide = (ImageView)customerLoginView.findViewById(R.id.img_benefit_guide);
        tvPolicy = (TextView)customerLoginView.findViewById(R.id.tv_policy);


        imgBenefitGuide.setImageResource(R.drawable.sample_benefit_guide);
        tvPolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //약관 보기 다이얼로그 띄움
                FragmentManager fragmentManager = getFragmentManager();
                PolicyDialogFragment policyDialog = new PolicyDialogFragment();
                policyDialog.show(fragmentManager, POLICY_DIALOG);
            }
        });


        //삭제버튼 리스너
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edtPhone.isFocused()) {
                    deleteEditText(edtPhone);
                }
            }
        });

        //OK버튼 리스너(네트워크 연결)
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NodeSocketClient nodeSocketClient = NodeSocketClient.getSocketInstance();

                if (NodeSocketClient.isSocketConnected) {
                    Log.d(TAG, "서버와 연결 됨");

                    nodeSocketClient.setmActivity(getActivity());
                    nodeSocketClient.setCustomerPhone(edtPhone.getText().toString().replace("-", ""));
                    nodeSocketClient.requestCustomerLogin(edtPhone.getText().toString().replace("-", ""));
                }
                else {
                    Log.d(TAG, "서버와 연결 안됨");
                    nodeSocketClient.connectSocket();
                    nodeSocketClient.setmActivity(getActivity());
                    nodeSocketClient.setCustomerPhone(edtPhone.getText().toString().replace("-", ""));
                    nodeSocketClient.requestCustomerLogin(edtPhone.getText().toString().replace("-", ""));
                }

//                switch (edtPhone.getText().toString()) {
//                    case "00":
//                        Partner.getInstance().setStrBenefitTypeCode("S");
//                        break;
//                    case "99":
//                        Partner.getInstance().setStrBenefitTypeCode("P");
//                        break;
//                }

//                startActivity(new Intent(getActivity(), SearchActivity.class));
            }
        });


        return customerLoginView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    private void setEditText(EditText editText, int index) {
        String strTemp, strBefore, strAfter;
        int iCurrentCursor, iLength;

        //현재 커서, 텍스트 크기 얻기
        iCurrentCursor = editText.getSelectionEnd();
        iLength = editText.length();

        //커서 위치에 따른 텍스트 삽입
        if(iCurrentCursor == iLength) {
            //마지막 커서
            strTemp = PhoneNumberUtils.formatNumber(editText.getText().toString() + arrNumBtns[index].getText().toString());
            editText.setText(strTemp);
            editText.setSelection(strTemp.length());
        }
//        else {
//            //사용자 지정 커서
//            strTemp = editText.getText().toString();
//            strBefore = strTemp.substring(0, iCurrentCursor);
//            strAfter = strTemp.substring(iCurrentCursor);
//            strTemp = strBefore + arrNumBtns[index].getText().toString() + strAfter;
//            editText.setText(strTemp);
//            editText.setSelection(iCurrentCursor + 1);
//        }
    }

    private void deleteEditText(EditText editText) {
        String strTemp, strBefore, strAfter;
        int iCurrentCursor, iLength;

        //현재 커서, 텍스트 크기 얻기
        iCurrentCursor = editText.getSelectionEnd();
        iLength = editText.length();

        //언더플로우 방지
        if(iLength == 0) {
            return;
        }

        //커서 위치에 따른 텍스트 삽입
        if(iCurrentCursor == iLength) {
            //마지막 커서
            strTemp = editText.getText().toString();

            if ((iLength - 2) < 0) {    //언더플로우 방지
                editText.setText(strTemp.substring(0, iLength - 1));
                editText.setSelection(strTemp.length() - 1);
            }
            else if (("" + editText.getText().toString().charAt(iLength - 2)).equals("-")) {    //"-" 지우기
                editText.setText(strTemp.substring(0, iLength - 2));
                editText.setSelection(strTemp.length() - 2);
            }
            else {
                editText.setText(strTemp.substring(0, iLength - 1));
                editText.setSelection(strTemp.length() - 1);
            }

        }
//        else {
//            //사용자 지정 커서
//            strTemp = editText.getText().toString();
//            strBefore = strTemp.substring(0, iCurrentCursor - 1);
//            strAfter = strTemp.substring(iCurrentCursor);
//            strTemp = strBefore + strAfter;
//            editText.setText(strTemp);
//            editText.setSelection(iCurrentCursor - 1);
//        }
    }
}
