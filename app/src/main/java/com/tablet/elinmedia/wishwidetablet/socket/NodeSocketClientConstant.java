package com.tablet.elinmedia.wishwidetablet.socket;

public interface NodeSocketClientConstant {
    public static final String SERVER_URL = "http://49.236.137.39:5000";
//    public static final String SERVER_URL = "http://192.168.0.2:5000";

    public static final String REQUEST_PARTNER_LOGIN_EVENT_CODE = "tabletLoginDeviceInsData";   //파트너로그인요청(JOIN)
//    public static final String RESPONSE_PARTNER_LOGIN_EVENT_CODE = "tabletLoginDeviceInsData";  //파트너로그인응답
    public static final String REQUEST_CUSTOMER_LOGIN_EVENT_CODE = "tabletSearchCustomer";  //고객로그인요청
    public static final String RESPONSE_POS_EVENT_CODE = "benefitUpdateInfoResponse";    //도장/포인트적립, 선물사용 UPDATE
    public static final String RESPONSE_PARTNER_LOGOUT_EVENT_CODE = "";    //파트너로그아웃응답(강제로그아웃)
    public static final String RESPONSE_CUSTOMER_LOGOUT_EVENT_CODE = "";    //고객로그아웃응답(강제로그아웃)
//    public static final String SERVER_STATE_EVENT_CODE = "";    //서버상태

    public static final String SUCCESS_RESPONSE_CODE = "1";
    public static final String FAIL_RESPONSE_CODE = "0";
    public static final String POS_NOT_LOGIN_RESPONSE_CODE = "4";
    public static final String SERVER_ERROR_RESPONSE_CODE = "5";
    public static final String DEVICE_UNREGISTER_RESPONSE_CODE = "3";
    public static final String SERVICE_EXPIRE_RESPONSE_CODE = "2";
    public static final String SAVING_AND_COUPON_RESPONSE_CODE = "2";
    public static final String USING_BENEFIT_RESPONSE_CODE = "3";

}
