package com.tablet.elinmedia.wishwidetablet.vo;

import android.content.Context;
import android.graphics.Bitmap;

import java.io.Serializable;

public class Partner implements Serializable {
//    private static Partner sPartner;
    private String strWideManagerId;
    private String strStoreName;
    private String strPolicy;
    private String strPrivacyPolicy;
    private String strBenefitTypeCode;
    private int iStampGoal;
    private int iStampVipGoal;
    private int iPointVipGoal;
    private String imgStampUrl;
    private String imgBenefitGuideUrl;
    private String imgLogoUrl;
    private String strWideManagerRole;
    private String strMallSocketId;
    private String strDeviceId;


    public Partner() {

    }


//    public static Partner getInstance() {
//        if(sPartner == null) {
//            sPartner = new Partner();
//        }
//
//        return sPartner;
//    }

    public String getStrWideManagerId() {
        return strWideManagerId;
    }

    public void setStrWideManagerId(String strWideManagerId) {
        this.strWideManagerId = strWideManagerId;
    }

    public String getStrStoreName() {
        return strStoreName;
    }

    public void setStrStoreName(String strStoreName) {
        this.strStoreName = strStoreName;
    }

    public String getStrPolicy() {
        return strPolicy;
    }

    public void setStrPolicy(String strPolicy) {
        this.strPolicy = strPolicy;
    }

    public String getStrPrivacyPolicy() {
        return strPrivacyPolicy;
    }

    public void setStrPrivacyPolicy(String strPrivacyPolicy) {
        this.strPrivacyPolicy = strPrivacyPolicy;
    }

    public String getStrBenefitTypeCode() {
        return strBenefitTypeCode;
    }

    public void setStrBenefitTypeCode(String strBenefitTypeCode) {
        this.strBenefitTypeCode = strBenefitTypeCode;
    }

    public int getiStampGoal() {
        return iStampGoal;
    }

    public void setiStampGoal(int iStampGoal) {
        this.iStampGoal = iStampGoal;
    }

    public int getiStampVipGoal() {
        return iStampVipGoal;
    }

    public void setiStampVipGoal(int iStampVipGoal) {
        this.iStampVipGoal = iStampVipGoal;
    }

    public int getiPointVipGoal() {
        return iPointVipGoal;
    }

    public void setiPointVipGoal(int iPointVipGoal) {
        this.iPointVipGoal = iPointVipGoal;
    }

    public String getImgStampUrl() {
        return imgStampUrl;
    }

    public void setImgStampUrl(String imgStampUrl) {
        this.imgStampUrl = imgStampUrl;
    }

    public String getImgBenefitGuideUrl() {
        return imgBenefitGuideUrl;
    }

    public void setImgBenefitGuideUrl(String imgBenefitGuideUrl) {
        this.imgBenefitGuideUrl = imgBenefitGuideUrl;
    }

    public String getImgLogoUrl() {
        return imgLogoUrl;
    }

    public void setImgLogoUrl(String imgLogoUrl) {
        this.imgLogoUrl = imgLogoUrl;
    }

    public String getStrWideManagerRole() {
        return strWideManagerRole;
    }

    public void setStrWideManagerRole(String strWideManagerRole) {
        this.strWideManagerRole = strWideManagerRole;
    }

    public String getStrMallSocketId() {
        return strMallSocketId;
    }

    public void setStrMallSocketId(String strMallSocketId) {
        this.strMallSocketId = strMallSocketId;
    }

    public String getStrDeviceId() {
        return strDeviceId;
    }

    public void setStrDeviceId(String strDeviceId) {
        this.strDeviceId = strDeviceId;
    }

    @Override
    public String toString() {
        return "{" +
                "strWideManagerId = " + strWideManagerId + ", \n" +
                "strStoreName = " + strStoreName + ", \n" +
                "strPolicy = " + strPolicy + ", \n" +
                "strPrivacyPolicy = " + strPrivacyPolicy + ", \n" +
                "strBenefitTypeCode = " + strBenefitTypeCode + ", \n" +
                "iStampGoal = " + iStampGoal + ", \n" +
                "iStampVipGoal = " + iStampVipGoal + ", \n" +
                "iPointVipGoal = " + iPointVipGoal + ", \n" +
                "imgStampUrl = " + imgStampUrl + ", \n" +
                "imgLogoUrl = " + imgLogoUrl + ", \n" +
                "strWideManagerRole = " + strWideManagerRole + ", \n" +
                "strMallSocketId = " + strMallSocketId + ", \n" +
                "strDeviceId = " + strDeviceId +
                "}";

    }
}
