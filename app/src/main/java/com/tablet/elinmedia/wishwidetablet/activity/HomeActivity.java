package com.tablet.elinmedia.wishwidetablet.activity;

import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import com.tablet.elinmedia.wishwidetablet.fragment.CustomerLoginFragment;
import com.tablet.elinmedia.wishwidetablet.fragment.HeaderFragment;

public class HomeActivity extends WishwideFragmentActivity {

    @Override
    protected Fragment createContentFragment() {
        return new CustomerLoginFragment();
    }

    @Override
    protected Fragment createHeaderFragment() {

        return new HeaderFragment("");
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }


}
