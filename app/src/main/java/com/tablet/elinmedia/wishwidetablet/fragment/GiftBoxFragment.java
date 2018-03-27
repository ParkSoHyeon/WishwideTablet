package com.tablet.elinmedia.wishwidetablet.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import com.tablet.elinmedia.wishwidetablet.R;
import com.tablet.elinmedia.wishwidetablet.activity.HomeActivity;
import com.tablet.elinmedia.wishwidetablet.activity.SearchActivity;
import com.tablet.elinmedia.wishwidetablet.socket.NodeSocketClient;
import com.tablet.elinmedia.wishwidetablet.socket.NodeSocketClientConstant;
import com.tablet.elinmedia.wishwidetablet.vo.Benefit;
import com.tablet.elinmedia.wishwidetablet.vo.BenefitLab;
import com.tablet.elinmedia.wishwidetablet.vo.Partner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class GiftBoxFragment extends Fragment implements NodeSocketClientConstant {
    private static final String TAG = "GiftBoxFragment";
    private RecyclerView mGiftBoxRecyclerView;
    private Button mBtnGoSearch, mBtnLogout;
    private GiftboxAdapter mGiftboxAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View giftBoxView = inflater.inflate(R.layout.fragment_giftbox, container, false);

        mGiftBoxRecyclerView = (RecyclerView) giftBoxView.findViewById(R.id.giftbox_recycler_view);
        mBtnGoSearch = (Button) giftBoxView.findViewById(R.id.btn_go_search);
        mBtnLogout = (Button) giftBoxView.findViewById(R.id.btn_logout);
        mGiftBoxRecyclerView.setLayoutManager(new LinearLayoutManager((getActivity())));

        final Partner partner = NodeSocketClient.getSocketInstance().getPartner();

        switch (partner.getStrBenefitTypeCode()) {
            case "S":
                mBtnGoSearch.setText(R.string.btn_go_search_stamp);
                break;
            case "P":
                mBtnGoSearch.setText(R.string.btn_go_search_point);
                break;
        }


        mBtnGoSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //도장/포인트 조회화면으로 이동
                Intent intent = new Intent(getActivity(), SearchActivity.class);
                intent.putExtra("responseCode", NodeSocketClient.SUCCESS_RESPONSE_CODE);
                intent.addFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });


        mBtnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //고객 로그아웃
                NodeSocketClient.getSocketInstance().resetCustomerInfo();
                Intent intent = new Intent(getActivity(), HomeActivity.class);
                intent.addFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });


        updateUI();


        return giftBoxView;
    }

    private void updateUI() {
        BenefitLab benefitLab = BenefitLab.getInstance();
        List<Benefit> benefits = benefitLab.getmBenefits();
        List<Benefit> availableBenefits = new ArrayList<>();
        List<Benefit> unavailableBenefits = new ArrayList<>();

        Collections.sort(benefits, new Comparator<Benefit>() {
            @Override
            public int compare(Benefit o1, Benefit o2) {
                try {

                    Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(o1.getBenefitFinishDate());
                    Date date2 = new SimpleDateFormat("yyyy-MM-dd").parse(o2.getBenefitFinishDate());

                    return date2.compareTo(date1);
                } catch (ParseException e) {

                }

                return 0;
            }
        });

        for (int i = 0; i < benefits.size(); i++) {
//            Log.d(TAG, "선물 " + i + ": " + benefitLab.getmBenefits().get(i));
//            Log.d(TAG, "선물 " + i + " 사용여부: " + benefitLab.getmBenefits().get(i).getiBenefitUseCode());
            if (benefits.get(i).getiBenefitUseCode() == 0) {
                //사용 가능
                availableBenefits.add(benefits.get(i));
            }
            else if (benefits.get(i).getiBenefitUseCode() == 1){
                //사용 완료
                unavailableBenefits.add(benefits.get(i));
            }
        }

        availableBenefits.addAll(unavailableBenefits);
        benefits.clear();
        benefits.addAll(availableBenefits);


        mGiftboxAdapter = new GiftboxAdapter(benefits);
        mGiftBoxRecyclerView.setAdapter(mGiftboxAdapter);
    }


    private class GiftboxHolder extends RecyclerView.ViewHolder {
        //위젯선언
        private ImageView imgBenefitType, imgBenefitItem, imgBenefitUse;
        private TextView tvBenefitTitle, tvBenefitDescription, tvBenefitExpire, tvBenefitUseDate;

        private Benefit mBenefit;

        public GiftboxHolder(View itemView) {
            super(itemView);

            //위젯결합
            imgBenefitType = (ImageView) itemView.findViewById(R.id.img_benefit_type);
            imgBenefitItem = (ImageView) itemView.findViewById(R.id.img_benefit_item);
            tvBenefitTitle = (TextView) itemView.findViewById(R.id.tv_benefit_title);
            tvBenefitDescription = (TextView) itemView.findViewById(R.id.tv_benefit_description);
            tvBenefitExpire = (TextView) itemView.findViewById(R.id.tv_benefit_expire);
            imgBenefitUse = (ImageView) itemView.findViewById(R.id.img_benefit_use);
            tvBenefitUseDate = (TextView) itemView.findViewById(R.id.tv_benefit_use_date);
        }

        public void bindBenefit(Benefit benefit) {
            mBenefit = benefit;

            if (mBenefit.getStrBenefitTypeCode().equals("coupon")) {
                imgBenefitType.setImageResource(R.drawable.benefit_coupon);
            }
            else if (mBenefit.getStrBenefitTypeCode().equals("gift")) {
                imgBenefitType.setImageResource(R.drawable.benefit_gift);
            }
            Picasso.with(getActivity()).load(mBenefit.getStrBenefitImageUrl())
                    .placeholder(R.drawable.logo)
                    .error(R.drawable.logo)
                    .into(imgBenefitItem);
            tvBenefitTitle.setText(mBenefit.getStrBenefitTitle());
            tvBenefitDescription.setText(mBenefit.getStrBenefitDescription());
            tvBenefitExpire.setText(" ~ " + mBenefit.getBenefitFinishDate());

            imgBenefitUse.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mBenefit.getiBenefitUseCode() == 0) {
                        //사용 가능
                        showDialog("사용하시려면 카운터에 문의하십시오.");
                    }
                }
            });

            if (mBenefit.getiBenefitUseCode() == 0) {
                //사용 가능
                imgBenefitUse.setMinimumWidth(150);
                imgBenefitUse.setMinimumHeight(100);
                imgBenefitUse.setImageResource(R.drawable.benefit_available);
                tvBenefitUseDate.setVisibility(View.GONE);
                tvBenefitUseDate.setText("");


            }
            else {
                //사용 완료
                imgBenefitUse.setMinimumWidth(150);
                imgBenefitUse.setMinimumHeight(100);
                imgBenefitUse.setImageResource(R.drawable.benefit_unavailable);
                tvBenefitUseDate.setVisibility(View.VISIBLE);
                tvBenefitUseDate.setText(mBenefit.getBenefitUseDate());

            }

        }
    }

    private void showDialog(String msg) {
        MessageDialogFragment messageDialogFragment = MessageDialogFragment.newInstance();
        messageDialogFragment.setmMsg(msg);
        messageDialogFragment.show(getFragmentManager(), MessageDialogFragment.DIALOG_TAG);
    }

    private class GiftboxAdapter extends RecyclerView.Adapter<GiftboxHolder> {
        private List<Benefit> mBenefits;

        public GiftboxAdapter(List<Benefit> benefits) {
            mBenefits = benefits;
        }

        @Override
        public GiftboxHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View benefitListItemView = layoutInflater.inflate(R.layout.benefit_list_item, parent, false);


            return new GiftboxHolder(benefitListItemView);
        }

        @Override
        public void onBindViewHolder(GiftboxHolder holder, int position) {
            Benefit benefit = mBenefits.get(position);
            holder.bindBenefit(benefit);
        }

        @Override
        public int getItemCount() {
            return mBenefits.size();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }
}

