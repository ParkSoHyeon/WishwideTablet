package com.tablet.elinmedia.wishwidetablet.vo;

import org.joda.time.LocalDateTime;

public class Benefit {
    private int benefitNo;
    private String strBenefitTypeCode;
    private String strBenefitTitle;
    private String strBenefitImageUrl;
    private String strBenefitDescription;
    private int iBenefitUseCode;
    private String strBenefitUse;
    private String benefitUseDate;
    private String benefitStartDate;
    private String benefitFinishDate;

    @Override
    public String toString() {
        return "{" +
                "benefitNo = " + benefitNo + ", \n" +
                "strBenefitTypeCode = " + strBenefitTypeCode + ", \n" +
                "strBenefitTitle = " + strBenefitTitle + ", \n" +
                "strBenefitImageUrl = " + strBenefitImageUrl + ", \n" +
                "iBenefitUseCode = " + iBenefitUseCode + ", \n" +
                "strBenefitUse = " + strBenefitUse + ", \n" +
                "benefitUseDate = " + benefitUseDate + ", \n" +
                "benefitStartDate = " + benefitStartDate + ", \n" +
                "benefitFinishDate = " + benefitFinishDate +
                "}";

    }


    public int getBenefitNo() {
        return benefitNo;
    }

    public void setBenefitNo(int benefitNo) {
        this.benefitNo = benefitNo;
    }

    public String getStrBenefitTypeCode() {
        return strBenefitTypeCode;
    }

    public void setStrBenefitTypeCode(String strBenefitTypeCode) {
        this.strBenefitTypeCode = strBenefitTypeCode;
    }

    public String getStrBenefitTitle() {
        return strBenefitTitle;
    }

    public void setStrBenefitTitle(String strBenefitTitle) {
        this.strBenefitTitle = strBenefitTitle;
    }

    public String getStrBenefitImageUrl() {
        return strBenefitImageUrl;
    }

    public void setStrBenefitImageUrl(String strBenefitImageUrl) {
        this.strBenefitImageUrl = strBenefitImageUrl;
    }

    public String getStrBenefitDescription() {
        return strBenefitDescription;
    }

    public void setStrBenefitDescription(String strBenefitDescription) {
        this.strBenefitDescription = strBenefitDescription;
    }

    public int getiBenefitUseCode() {






        return iBenefitUseCode;
    }

    public void setiBenefitUseCode(int iBenefitUseCode) {
        this.iBenefitUseCode = iBenefitUseCode;
    }

    public String getStrBenefitUse() {
        return strBenefitUse;
    }

    public void setStrBenefitUse(String strBenefitUse) {
        this.strBenefitUse = strBenefitUse;
    }

    public String getBenefitUseDate() {
        return benefitUseDate;
    }

    public void setBenefitUseDate(String benefitUseDate) {
        this.benefitUseDate = benefitUseDate;
    }

    public String getBenefitStartDate() {
        return benefitStartDate;
    }

    public void setBenefitStartDate(String benefitStartDate) {
        this.benefitStartDate = benefitStartDate;
    }

    public String getBenefitFinishDate() {
        return benefitFinishDate;
    }

    public void setBenefitFinishDate(String benefitFinishDate) {
        this.benefitFinishDate = benefitFinishDate;
    }


}
