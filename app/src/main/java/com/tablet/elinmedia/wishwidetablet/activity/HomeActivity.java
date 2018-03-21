package com.tablet.elinmedia.wishwidetablet.activity;

import android.support.v4.app.Fragment;
import com.tablet.elinmedia.wishwidetablet.fragment.CustomerLoginFragment;

public class HomeActivity extends WishwideFragmentActivity {
//    private static final String KEYPAD_TYPE = "phone";

    @Override
    protected Fragment createContentFragment() {
        return new CustomerLoginFragment();
    }
}
