package com.tablet.elinmedia.wishwidetablet.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import com.tablet.elinmedia.wishwidetablet.R;
import com.tablet.elinmedia.wishwidetablet.fragment.HeaderFragment;

public abstract class WishwideFragmentActivity
        extends android.support.v4.app.FragmentActivity {
    protected abstract Fragment createContentFragment();
    protected abstract Fragment createHeaderFragment();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragmentHeader = fragmentManager.findFragmentById(R.id.fragment_header);
        Fragment fragmentContent =fragmentManager.findFragmentById(R.id.fragment_content);

        //헤더 프래그먼트
        if(fragmentHeader == null) {
            fragmentHeader = createHeaderFragment();
            fragmentManager.beginTransaction()
                    .add(R.id.fragment_header, fragmentHeader)
                    .commit();
        }

        //컨텐트 프래그먼트
        if(fragmentContent == null) {
            fragmentContent = createContentFragment();
            fragmentManager.beginTransaction()
                    .add(R.id.fragment_content, fragmentContent)
                    .commit();
        }
    }


}
