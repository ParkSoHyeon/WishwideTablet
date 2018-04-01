package com.tablet.elinmedia.wishwidetablet.socket;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import com.tablet.elinmedia.wishwidetablet.common.BaseApplication;
import com.tablet.elinmedia.wishwidetablet.common.SharedPreferencesConstant;
import com.tablet.elinmedia.wishwidetablet.activity.GiftBoxActivity;
import com.tablet.elinmedia.wishwidetablet.activity.HomeActivity;
import com.tablet.elinmedia.wishwidetablet.activity.LoginActivity;
import com.tablet.elinmedia.wishwidetablet.activity.SearchActivity;
import com.tablet.elinmedia.wishwidetablet.vo.Benefit;
import com.tablet.elinmedia.wishwidetablet.vo.BenefitLab;
import com.tablet.elinmedia.wishwidetablet.vo.Customer;
import com.tablet.elinmedia.wishwidetablet.vo.Partner;
import io.socket.client.Ack;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class NodeSocketClient implements NodeSocketClientConstant, SharedPreferencesConstant {
    private static final String TAG = "NodeSocketClient";

    private static NodeSocketClient nodeSocketClient;

    private SharedPreferences mSharedPreferences;

    private final Context mContext = BaseApplication.getInstance().getmContext();

    private Activity mActivity;

    private Socket mSocket;

    private String deviceId;
    private String socketId;
    private String managerId;
    private String managerPassword;
    private String customerPhone;
    private Partner partner;
    private Customer customer;
    private Benefit benefit;
    private BenefitLab benefitLab;

    public static boolean isSocketConnected = false;


    private NodeSocketClient() {
        Log.d(TAG, "NodeSocketClient 객체 성공!");
        initializeConfig();
    }


    public static NodeSocketClient getSocketInstance() {
        if (nodeSocketClient == null) {
            nodeSocketClient = new NodeSocketClient();
        }

        return nodeSocketClient;
    }

    //자원 반납
    public void clearResource() {
        mActivity = null;
        mSocket = null;
        deviceId = null;
        socketId = null;
        managerId = null;
        managerPassword = null;
        customerPhone = null;
        partner = null;
        customer = null;
        benefit = null;
        benefitLab = null;
        nodeSocketClient = null;
    }

    //소켓 설정
    public void initializeConfig() {
        Log.d(TAG, "initializeConfig()...");
        try {
            mSocket = IO.socket(SERVER_URL);

            mSocket.on(Socket.EVENT_CONNECT, onConnect);
            mSocket.on(Socket.EVENT_DISCONNECT, onDisconnect);
            mSocket.on(Socket.EVENT_CONNECT_TIMEOUT, onConnectTimeout);

            mSocket.on(RESPONSE_POS_EVENT_CODE, onPosReceived);
            mSocket.on(RESPONSE_PARTNER_LOGOUT_EVENT_CODE, onPartnerLogoutReceived);
            mSocket.on(RESPONSE_CUSTOMER_LOGOUT_EVENT_CODE, onCustomerLogoutReceived);

        } catch (URISyntaxException e) {
            e.printStackTrace();
        }


    }


    //Socket 연결
    public void connectSocket() {
        Log.d(TAG, "connectSocket()...");
        mSocket.connect();
    }


    //Socket 연결 끊음
    public void disconnectSocket() {
        Log.d(TAG, "disconnectSocket()...");
        mSocket.disconnect();
    }


    // Socket서버에 connect 된 후, 서버로부터 전달받은 'Socket.EVENT_CONNECT' Event 처리.
    private Emitter.Listener onServerStateReceived = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            String serverState = args[0].toString();

            Log.d(TAG, "서버와 상태 확인" + serverState);


            if (serverState.equals("Server Connected")) {
                //서버 연결 성공("id, password, device id" of partner emit)


            }
        }
    };


    //서버와 연결됨 listener
    private Emitter.Listener onConnect = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            Log.d(TAG, "서버와 연결 완료");

            if (!isSocketConnected) {
                requestPartnerLogin();
                isSocketConnected = true;
            }
        }
    };


    //서버와 연결 끊어짐 listener
    private Emitter.Listener onDisconnect = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            Log.d(TAG, "서버와 연결 끊김");

            isSocketConnected = false;
        }
    };


    //포스 응답 받음 listener
    private Emitter.Listener onPosReceived = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            JSONObject data = (JSONObject) args[0];
            Log.d(TAG, "도장/포인트 적립 및 선물 사용, 응답 코드 확인: " + data.toString());

            try {
                JSONObject objRoot = new JSONObject(data.toString().trim());

                String benefitType = objRoot.optString("benefitType");
                String responseCode = objRoot.optString("responseCode");

                Intent intent = null;

                switch (responseCode) {
                    case FAIL_RESPONSE_CODE:
                        Log.d(TAG, "적립 실패");
//                        intent = new Intent(mContext, LoginActivity.class);
//                        intent.putExtra("responseCode", responseCode);

                        break;
                    case SUCCESS_RESPONSE_CODE:
                    case SAVING_AND_COUPON_RESPONSE_CODE:
                        Log.d(TAG, "적립 성공");
                        intent = new Intent(mContext, SearchActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                        if (benefitType.equals("stamp")) {
                            customer.setiStampNowCnt(objRoot.optInt("stampNowCnt"));
                            customer.setiStampGivingCnt(objRoot.optInt("stampGivingCnt"));

                        } else if (benefitType.equals("point")) {
                            customer.setiPointInsCnt( objRoot.optInt("pointInsCnt"));
                            customer.setiPointUseCnt( objRoot.optInt("pointUseCnt"));
                            customer.setiPointAllCnt( objRoot.optInt("pointAllCnt"));
                        }

                        intent.putExtra("responseCode", responseCode);
                        intent.putExtra("benefitType", benefitType);

                        BenefitLab.getInstance().clearResource();
                        BenefitLab.getInstance().setmBenefits(parseJSONGiftBox(data.toString().trim()));

                        break;
                    case USING_BENEFIT_RESPONSE_CODE:
                        intent = new Intent(mContext, GiftBoxActivity.class);

                        if (benefitType.equals("stamp")) {
                            customer.setiStampNowCnt(objRoot.optInt("stampNowCnt"));

                        } else if (benefitType.equals("point")) {
                            customer.setiPointInsCnt( objRoot.optInt("pointInsCnt"));
                            customer.setiPointUseCnt( objRoot.optInt("pointUseCnt"));
                            customer.setiPointAllCnt( objRoot.optInt("pointAllCnt"));
                        }

                        intent.putExtra("responseCode", responseCode);
                        intent.putExtra("benefitType", benefitType);

                        BenefitLab.getInstance().clearResource();
                        BenefitLab.getInstance().setmBenefits(parseJSONGiftBox(data.toString().trim()));

                        break;
                }

                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        progressOFF();
                    }
                });


                PendingIntent pendingIntent = PendingIntent.getActivity(mContext, 0, intent, 0);
                pendingIntent.send();

            } catch (JSONException e) {
                Log.e(TAG, "json 파싱 중 오류 발생" + e.getStackTrace());
            } catch (PendingIntent.CanceledException e) {
                e.printStackTrace();
            }

        }
    };


    //고객 로그아웃 listener
    private Emitter.Listener onCustomerLogoutReceived = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            Log.d(TAG, "고객 로그아웃: " + args[0].toString());

            //고객 로그아웃, 고객 초기화 및 MainActivity 이동(history 제거)
            resetCustomerInfo();
        }
    };


    //파트너 로그아웃 listener
    private Emitter.Listener onPartnerLogoutReceived = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            Log.d(TAG, "파트너 로그아웃: " + args[0].toString());

            //파트너 로그아웃, 소켓 종료
        }
    };


    // 서버로부터 전달받은 'chat-message' Event 처리.
    private Emitter.Listener onConnectTimeout = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            Log.d(TAG, "연결 시간 초가");

            //현재 폴더에 있는 최신 스케줄 여부 검사
//            sendNoMessageToService();
        }
    };

    public void requestPartnerLogin() {
        JSONObject objLogin = new JSONObject();

        try {
            objLogin.put("id", managerId);
            objLogin.put("password", managerPassword);
            objLogin.put("deviceId", deviceId);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d(TAG, "소켓에게 보낼 디바이스 아이디 확인: " + deviceId);
        Log.d(TAG, "소켓에게 보낼 운영자 아이디 확인: " + managerId);
        Log.d(TAG, "소켓에게 보낼 운영자 비밀번호 확인: " + managerPassword);

        mSocket.emit(REQUEST_PARTNER_LOGIN_EVENT_CODE, objLogin, new Ack() {
            @Override
            public void call(Object... args) {
                //파트너 로그인 성공 시, MainActivity 이동 및 파트너 정보 저장
                JSONObject data = (JSONObject) args[0];
                Log.d(TAG, "파트너 로그인, 응답 코드 확인: " + data.toString());

                Partner info = new Partner();

                try {
                    JSONObject objRoot = new JSONObject(data.toString().trim());

                    Intent intent = null;

                    String responseCode = objRoot.optString("responseCode");

                    switch (responseCode) {
                        case FAIL_RESPONSE_CODE:
                            Log.d(TAG, "아이디/패스워드 불일치");
                            intent = new Intent(mContext, LoginActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.putExtra("responseCode", responseCode);

                            break;
                        case SUCCESS_RESPONSE_CODE:
                            Log.d(TAG, "로그인 성공");
                            intent = new Intent(mContext, HomeActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.putExtra("responseCode", responseCode);

                            info.setStrWideManagerId(objRoot.optString("wideManagerId"));
                            info.setStrWideManagerRole(objRoot.optString("wideManagerRole"));
                            info.setStrStoreName(objRoot.optString("wideManagerName"));
                            info.setStrMallSocketId(objRoot.optString("mallSocketId"));
                            info.setStrDeviceId(objRoot.optString("deviceId"));
                            info.setStrBenefitTypeCode(objRoot.optString("benefitTypeCode"));
                            //info.setImgBenefitGuideUrl(managerInfo.optString("benefitGuideImageUrl"));
                            info.setImgLogoUrl(objRoot.optString("logImageUrl"));
                            //info.setImgStampUrl(managerInfo.optString("stampImageUrl"));
                            info.setiStampGoal(objRoot.optInt("stampGoal"));
                            info.setiStampVipGoal(objRoot.optInt("stampVipGoal"));
                            info.setiPointVipGoal(objRoot.optInt("pointVipGoal"));
                            info.setStrStoreName(objRoot.optString("wideManagerName"));
                            info.setStrPolicy(objRoot.optString("policy"));
                            info.setStrPrivacyPolicy(objRoot.optString("privacyPolicy"));
                            socketId = objRoot.optString("mallSocketId");

                            partner = info;

                            SharedPreferences.Editor editor = mSharedPreferences.edit();
                            editor.putString(MANAGER_ID_KEY, managerId);
                            editor.putString(MANAGER_PASSWORD_KEY, managerPassword);
                            editor.putString(MANAGER_DEVICE_ID_KEY, deviceId);
                            editor.commit();

                            Log.d(TAG, "파트너 정보 확인: " + info.toString());

                            break;
                        case SERVICE_EXPIRE_RESPONSE_CODE:
                            Log.d(TAG, "서비스 이용 불가");
                            intent = new Intent(mContext, LoginActivity.class);
                            intent.putExtra("responseCode", responseCode);
                            intent.putExtra("errorMsg", "Wishwide 운영자에게 문의해주세요." + "(코드:" + responseCode + ")");

                            break;
                        case DEVICE_UNREGISTER_RESPONSE_CODE:
                            Log.d(TAG, "디바이스 미등록");
                            intent = new Intent(mContext, LoginActivity.class);
                            intent.putExtra("responseCode", responseCode);
                            intent.putExtra("errorMsg", "디바이스 아이디를 등록해주세요." + "(코드:" + responseCode + ")");

                            break;
                        case POS_NOT_LOGIN_RESPONSE_CODE:
                            Log.d(TAG, "POS 로그인 안됌");
                            intent = new Intent(mContext, LoginActivity.class);
                            intent.putExtra("responseCode", responseCode);
                            intent.putExtra("errorMsg", "POS를 확인해주세요." + "(코드:" + responseCode + ")");

                            break;
                        case SERVER_ERROR_RESPONSE_CODE:
                            Log.d(TAG, "서버 통신 오류");
                            intent = new Intent(mContext, LoginActivity.class);
                            intent.putExtra("responseCode", responseCode);
                            intent.putExtra("errorMsg", "Wishwide 운영자에게 문의해주세요." + "(코드:" + responseCode + ")");

                            break;
                    }

                    PendingIntent pendingIntent = PendingIntent.getActivity(mContext, 0, intent, 0);
                    pendingIntent.send();

                } catch (JSONException e) {
                    Log.e(TAG, "json 파싱 중 오류 발생" + e.getStackTrace());
                } catch (PendingIntent.CanceledException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    public void requestCustomerLogin(String customerPhone) {
        Log.d(TAG, "고객 로그인 시도: " + customerPhone);
        this.customerPhone = customerPhone;

        JSONObject objData = new JSONObject();

        try {
            objData.put("phoneNumber", this.customerPhone);
            objData.put("wideManagerId", managerId);
            objData.put("deviceId", deviceId);
            objData.put("mallSocketId", socketId);
            objData.put("benefitTypeCode", partner.getStrBenefitTypeCode());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        mSocket.emit(REQUEST_CUSTOMER_LOGIN_EVENT_CODE, objData, new Ack() {
            @Override
            public void call(Object... args) {
                JSONObject data = (JSONObject) args[0];
                Log.d(TAG, "고객 로그인, 응답 코드 확인: " + data.toString());

                try {
                    JSONObject objRoot = new JSONObject(data.toString().trim());

//                    Intent intent = null;

                    String responseCode = objRoot.optString("responseCode");

                    switch (responseCode) {
                        case FAIL_RESPONSE_CODE:
                            Log.d(TAG, "로그인 실패");
//                        intent = new Intent(mContext, LoginActivity.class);
//                        intent.putExtra("responseCode", responseCode);

                            break;
                        case SUCCESS_RESPONSE_CODE:
                            Log.d(TAG, "로그인 성공");
//                            intent = new Intent(mContext, SearchActivity.class);

                            new Handler(Looper.getMainLooper()).post(new Runnable() {
                                @Override
                                public void run() {
                                    progressON("확인 중...");
                                }
                            });


                            Customer info = new Customer();

                            info.setCustomerNo(objRoot.optInt("membershipCustomerNo"));
                            info.setStrCustomerPhone(objRoot.optString("membershipCustomerPhone"));
                            info.setStrCustomerBirth(objRoot.optString("wideCustomerBirth"));
                            info.setStrCustomerName(objRoot.optString("wideCustomerName"));
                            info.setStrCustomerGrade(objRoot.optString("membershipCustomerGrade"));
                            info.setiCustomerGenderCode(objRoot.optInt("wideCustomerSex"));
                            info.setStrCustomerBenefitType(objRoot.optString("membershipCustomerBenefitType"));
                            info.setStrCustomerBenefitValue(objRoot.optString("membershipCustomerBenefitValue"));
                            info.setStrCustomerReceiveGiftCnt(objRoot.optInt("membershipCustomerReceiveGiftCnt"));

                            customer = info;
                    }

//                    PendingIntent pendingIntent = PendingIntent.getActivity(mContext, 0, intent, 0);
//                    pendingIntent.send();

                } catch (JSONException e) {
                    Log.e(TAG, "json 파싱 중 오류 발생" + e.getStackTrace());
                }
//                catch (PendingIntent.CanceledException e) {
//                    e.printStackTrace();
//                }

            }
        });
    }


    //선물함 정보 JSON 파싱
    private List<Benefit>
    parseJSONGiftBox(String jsonData) {
        Log.i(TAG, "json 데이터 확인: " + jsonData);

//        BenefitLab.clearResource();

        List<Benefit> benefits = new ArrayList<>();


        try {
            JSONObject objRoot = new JSONObject(jsonData.trim());

            //쿠폰 리스트
            JSONObject objCoupons = objRoot.optJSONObject("couponList");

            for (int i = 0; i < objRoot.optInt("couponCount"); i++) {
                JSONObject objCoupon = objCoupons.getJSONObject("coupon" + i);

                Benefit benefit = new Benefit();

                String description = "";

                benefit.setBenefitNo(objCoupon.optInt("customer_couponproduct_no"));
                benefit.setStrBenefitTypeCode(objCoupon.optString("type"));
                benefit.setStrBenefitTitle(objCoupon.optString("coupon_title"));
                benefit.setStrBenefitImageUrl(objCoupon.optString("product_image_url"));
                if (objCoupon.optString("coupon_discount_type_code").equals("DCP")) {
                    description = objCoupon.optString("product_title") + " " + objCoupon.optString("coupon_discount_value") + "원 할인";
                }
                else if (objCoupon.optString("coupon_discount_type_code").equals("DCR")) {
                    description = objCoupon.optString("product_title") + " " + objCoupon.optString("coupon_discount_value") + "% 할인";
                }

                benefit.setStrBenefitDescription(description);

                benefit.setBenefitUseDate(objCoupon.optString("customer_couponproduct_use_date"));
                benefit.setBenefitStartDate(objCoupon.optString("customer_couponproduct_begin_date"));
                benefit.setBenefitFinishDate(objCoupon.optString("customer_couponproduct_finish_date"));
                benefit.setStrBenefitUse(objCoupon.optString("couponUseYn"));
                benefit.setiBenefitUseCode(objCoupon.optInt("customer_couponproduct_use_code"));

                Log.d(TAG, "쿠폰 리스트: " + benefit.toString());

                benefits.add(benefit);
            }

            //쿠폰 리스트
            JSONObject objGifts = objRoot.optJSONObject("giftList");

            for (int i = 0; i < objRoot.optInt("giftCount"); i++) {
                JSONObject objGift = objGifts.getJSONObject("gift" + i);

                Benefit benefit = new Benefit();

                benefit.setBenefitNo(objGift.optInt("giftNo"));
                benefit.setStrBenefitTypeCode(objGift.optString("type"));
                benefit.setStrBenefitTitle(objGift.optString("giftProductName"));
                benefit.setStrBenefitImageUrl(objGift.optString("giftProductImageUrl"));
//                benefit.setStrBenefitDescription(objGift.optString(""));
                benefit.setBenefitUseDate(objGift.optString("giftUseDate"));
                benefit.setBenefitStartDate(objGift.optString("giftBeginDate"));
                benefit.setBenefitFinishDate(objGift.optString("giftFinishDate"));
                benefit.setStrBenefitUse(objGift.optString("giftUseYn"));
                benefit.setiBenefitUseCode(objGift.optInt("giftUseStatusCode"));

                Log.d(TAG, "선물 리스트: " + benefit.toString());

                benefits.add(benefit);
            }

        } catch (JSONException e) {
            Log.e(TAG, "json 파싱 중 오류 발생" + e.getStackTrace());
        }

        return benefits;
    }

    public void resetCustomerInfo() {
        customer = null;
        benefit = null;
        benefitLab = null;
    }


    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getSocketId() {
        return socketId;
    }

    public void setSocketId(String socketId) {
        this.socketId = socketId;
    }

    public String getManagerId() {
        return managerId;
    }

    public void setManagerId(String managerId) {
        this.managerId = managerId;
    }

    public String getManagerPassword() {
        return managerPassword;
    }

    public void setManagerPassword(String managerPassword) {
        this.managerPassword = managerPassword;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public Partner getPartner() {
        return partner;
    }

    public void setPartner(Partner partner) {
        this.partner = partner;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Benefit getBenefit() {
        return benefit;
    }

    public void setBenefit(Benefit benefit) {
        this.benefit = benefit;
    }

    public BenefitLab getBenefitLab() {
        return benefitLab;
    }

    public void setBenefitLab(BenefitLab benefitLab) {
        this.benefitLab = benefitLab;
    }

    public SharedPreferences getmSharedPreferences() {
        return mSharedPreferences;
    }

    public void setmSharedPreferences(SharedPreferences mSharedPreferences) {
        this.mSharedPreferences = mSharedPreferences;
    }

    public Activity getmActivity() {
        return mActivity;
    }

    public void setmActivity(Activity mActivity) {
        this.mActivity = mActivity;
    }

    public void progressON(String message) {
        BaseApplication.getInstance().progressON(mActivity, message);
    }

    public void progressOFF() {
        BaseApplication.getInstance().progressOFF();
    }
}
