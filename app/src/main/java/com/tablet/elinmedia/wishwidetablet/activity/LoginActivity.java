package com.tablet.elinmedia.wishwidetablet.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.tablet.elinmedia.wishwidetablet.R;
import com.tablet.elinmedia.wishwidetablet.common.SharedPreferencesConstant;
import com.tablet.elinmedia.wishwidetablet.fragment.MessageDialogFragment;
import com.tablet.elinmedia.wishwidetablet.socket.NodeSocketClient;
import com.tablet.elinmedia.wishwidetablet.socket.NodeSocketClientConstant;

import java.util.regex.Pattern;

public class LoginActivity
        extends AppCompatActivity implements SharedPreferencesConstant, NodeSocketClientConstant {
    private static final String TAG = "LoginActivity";

    private SharedPreferences mSharedPreferences;

    // UI references.
    private EditText mEdtId, mEdtPassword, mEdtDeviceId;
    private Button mBtnSignIn;
    private View mProgressView;
    private View mLoginFormView;
    private Button mBtnStampSignIn, mBtnPointSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mSharedPreferences = this.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);

        // Set up the login form.
        mEdtId = (EditText) findViewById(R.id.edt_id);
        mEdtDeviceId = (EditText) findViewById(R.id.edt_device_id);
        mBtnSignIn = (Button) findViewById(R.id.btn_sign_in);
        mBtnPointSignIn = (Button) findViewById(R.id.btn_point_sign_in);
        mBtnStampSignIn = (Button) findViewById(R.id.btn_stamp_sign_in);
        mEdtPassword = (EditText) findViewById(R.id.edt_password);
        mEdtPassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        mBtnSignIn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();

                NodeSocketClient nodeSocketClient = NodeSocketClient.getSocketInstance();
                nodeSocketClient.setManagerId(mEdtId.getText().toString());
                nodeSocketClient.setManagerPassword(mEdtPassword.getText().toString());
                nodeSocketClient.setDeviceId(mEdtDeviceId.getText().toString());
                nodeSocketClient.setmSharedPreferences(mSharedPreferences);

                if (NodeSocketClient.isSocketConnected == true) {
                    Log.d(TAG, "isSocketConnected true");
                    nodeSocketClient.requestPartnerLogin();
                }
                else {
                    Log.d(TAG, "isSocketConnected false");
                    nodeSocketClient.connectSocket();
                }

            }
        });

        mBtnStampSignIn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mEdtId.setText("gupnechicken");
                mEdtPassword.setText("1q2w3e4r");
                mEdtDeviceId.setText("WW_DI5");
            }
        });

        mBtnPointSignIn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mEdtId.setText("starbucks");
                mEdtPassword.setText("1q2w3e4r");
                mEdtDeviceId.setText("WW_DI8");
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);

        Intent intent = getIntent();
        String responseCode = "-1";
        String msg = "";
        if (intent.getStringExtra("responseCode") != null) {
            responseCode = intent.getStringExtra("responseCode");
            msg = intent.getStringExtra("errorMsg");
        }

        switch (responseCode) {
            case FAIL_RESPONSE_CODE:
                showDialog(msg);
                break;

            case POS_NOT_LOGIN_RESPONSE_CODE:
                showDialog(msg);
                break;

            case SERVER_ERROR_RESPONSE_CODE:
                showDialog(msg);
                break;

            case DEVICE_UNREGISTER_RESPONSE_CODE:
                showDialog(msg);
                break;

            case SERVICE_EXPIRE_RESPONSE_CODE:
                showDialog(msg);
                break;
        }
    }


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
        // Reset errors.
        mEdtId.setError(null);
        mEdtPassword.setError(null);
        mEdtDeviceId.setError(null);

        // Store values at the time of the login attempt.
        String id = mEdtId.getText().toString();
        String password = mEdtPassword.getText().toString();
        String deviceId = mEdtDeviceId.getText().toString();

        boolean cancel = false;
        View focusView = null;

        //운영자비밀번호 유효성 검사
        if (TextUtils.isEmpty(password)) {
            mEdtPassword.setError(getString(R.string.error_field_required));
            focusView = mEdtPassword;
            cancel = true;
        }
//        else if (!isPasswordValid(password)) {
//            mEdtPassword.setError(getString(R.string.error_invalid_password));
//            mEdtPassword.setText(password.substring(0, 20));
//            focusView = mEdtPassword;
//            cancel = true;
//        }

        //운영자아이디 유효성 검사
        if (TextUtils.isEmpty(id)) {
            mEdtId.setError(getString(R.string.error_field_required));
            focusView = mEdtId;
            cancel = true;
        }
//        else if (!isIdValid(id)) {
//            mEdtId.setError(getString(R.string.error_invalid_id));
//            focusView = mEdtId;
//            cancel = true;
//        }

        //디바이스아이디 유효성 검사
        if (TextUtils.isEmpty(deviceId)) {
            mEdtDeviceId.setError(getString(R.string.error_field_required));
            focusView = mEdtId;
            cancel = true;
        }
//        else if (!isIdValid(deviceId)) {
//            mEdtDeviceId.setError(getString(R.string.error_invalid_device_id));
//            focusView = mEdtDeviceId;
//            cancel = true;
//        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
        }
    }

    private boolean isIdValid(String id) {
        //TODO: Replace this with your own logic
        return Pattern.matches("^[A-Za-z0-9]{4,15}$", id);
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        Log.d(TAG, "비밀번호 길이 확인: " + password.length());
        return password.length() < 20;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    private void showDialog(String msg) {
        MessageDialogFragment messageDialogFragment = MessageDialogFragment.newInstance();
        messageDialogFragment.setmMsg(msg);
        messageDialogFragment.show(getSupportFragmentManager(), MessageDialogFragment.DIALOG_TAG);
    }
}

