package com.tablet.elinmedia.wishwidetablet.vo;

import java.util.ArrayList;
import java.util.List;

public class BenefitLab {
    private static BenefitLab sBenefitLab;
    private List<Benefit> mBenefits;

    private BenefitLab() {
        mBenefits = new ArrayList<>();

//        for (int i = 0; i < 10; i++) {
//            Benefit benefit = new Benefit();
//
//            benefit.setBenefitNo(21);
//            benefit.setStrBenefitTypeCode("쿠폰");
//            benefit.setStrBenefitTitle("오돌뼈 2000원 할인 쿠폰");
//            benefit.setStrBenefitImageUrl("http://eevcrwzwnqin572958.cdn.ntruss.com/8CbmOvLBoFXbvzAmXbCKNEbKxZFVn9HH.jpg");
////                benefit.setStrBenefitDescription(objCoupon.optString(""));
//            benefit.setBenefitStartDate("2018-03-12");
//            benefit.setBenefitFinishDate("2018-03-22");
//            benefit.setStrBenefitUse("사용가능");
//            benefit.setiBenefitUseCode(0);
//
//            mBenefits.add(benefit);
//        }
    }

    public static BenefitLab getInstance() {
        if(sBenefitLab ==  null) {
            sBenefitLab = new BenefitLab();
        }

        return sBenefitLab;
    }

    public void clearResource() {
        if(sBenefitLab != null) {
            sBenefitLab = null;
        }
    }

    public List<Benefit> getmBenefits() {
        return mBenefits;
    }

    public void setmBenefits(List<Benefit> mBenefits) {
        this.mBenefits = mBenefits;
    }
}
