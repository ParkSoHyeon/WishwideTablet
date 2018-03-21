package com.tablet.elinmedia.wishwidetablet.vo;

import android.content.Context;

public class Customer {
    public static Customer sCustomer;
    private int customerNo;
    private String strCustomerPhone;
    private String strCustomerGrade;
    private String strCustomerName;
    private int iCustomerGenderCode;
    private String strCustomerBirth;
    private String strCustomerBenefitType;
    private String strCustomerBenefitValue;
    private int strCustomerReceiveGiftCnt;
    private int iStampNowCnt = 0;
    private int iPointInsCnt = 0;
    private int iPointUseCnt = 0;
    private int iPointAllCnt = 0;

    private Customer(Context context) {
    }

    public Customer() {
    }

//    public static Customer getInstance() {
//        if(sCustomer == null) {
//            sCustomer = new Customer();
//        }
//
//        return sCustomer;
//    }
//
//    public static void clearInstance(Context context) {
//        if(sCustomer != null) {
//            sCustomer = null;
//        }
//    }

    public int getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(int customerNo) {
        this.customerNo = customerNo;
    }

    public String getStrCustomerPhone() {
        return strCustomerPhone;
    }

    public void setStrCustomerPhone(String strCustomerPhone) {
        this.strCustomerPhone = strCustomerPhone;
    }

    public String getStrCustomerGrade() {
        return strCustomerGrade;
    }

    public void setStrCustomerGrade(String strCustomerGrade) {
        this.strCustomerGrade = strCustomerGrade;
    }

    public String getStrCustomerName() {
        return strCustomerName;
    }

    public void setStrCustomerName(String strCustomerName) {
        this.strCustomerName = strCustomerName;
    }

    public int getiCustomerGenderCode() {
        return iCustomerGenderCode;
    }

    public void setiCustomerGenderCode(int iCustomerGenderCode) {
        this.iCustomerGenderCode = iCustomerGenderCode;
    }

    public String getStrCustomerBirth() {
        return strCustomerBirth;
    }

    public void setStrCustomerBirth(String strCustomerBirth) {
        this.strCustomerBirth = strCustomerBirth;
    }

    public String getStrCustomerBenefitType() {
        return strCustomerBenefitType;
    }

    public void setStrCustomerBenefitType(String strCustomerBenefitType) {
        this.strCustomerBenefitType = strCustomerBenefitType;
    }

    public String getStrCustomerBenefitValue() {
        return strCustomerBenefitValue;
    }

    public void setStrCustomerBenefitValue(String strCustomerBenefitValue) {
        this.strCustomerBenefitValue = strCustomerBenefitValue;
    }

    public int getStrCustomerReceiveGiftCnt() {
        return strCustomerReceiveGiftCnt;
    }

    public void setStrCustomerReceiveGiftCnt(int strCustomerReceiveGiftCnt) {
        this.strCustomerReceiveGiftCnt = strCustomerReceiveGiftCnt;
    }

    public int getiStampNowCnt() {
        return iStampNowCnt;
    }

    public void setiStampNowCnt(int iStampNowCnt) {
        this.iStampNowCnt = iStampNowCnt;
    }

    public int getiPointInsCnt() {
        return iPointInsCnt;
    }

    public void setiPointInsCnt(int iPointInsCnt) {
        this.iPointInsCnt = iPointInsCnt;
    }

    public int getiPointUseCnt() {
        return iPointUseCnt;
    }

    public void setiPointUseCnt(int iPointUseCnt) {
        this.iPointUseCnt = iPointUseCnt;
    }

    public int getiPointAllCnt() {
        return iPointAllCnt;
    }

    public void setiPointAllCnt(int iPointAllCnt) {
        this.iPointAllCnt = iPointAllCnt;
    }
}
