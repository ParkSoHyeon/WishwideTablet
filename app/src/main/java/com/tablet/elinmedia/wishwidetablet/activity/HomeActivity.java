package com.tablet.elinmedia.wishwidetablet.activity;

import android.app.KeyguardManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.WindowManager;
import com.tablet.elinmedia.wishwidetablet.common.HomeKeyLocker;
import com.tablet.elinmedia.wishwidetablet.fragment.CustomerLoginFragment;
import com.tablet.elinmedia.wishwidetablet.fragment.HeaderFragment;

public class HomeActivity extends WishwideFragmentActivity {
//    private static final String KEYPAD_TYPE = "phone";
    private boolean isHomekey = false;

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
