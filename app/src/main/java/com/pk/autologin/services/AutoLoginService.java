package com.pk.autologin.services;

import android.accessibilityservice.AccessibilityService;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Toast;

import com.pk.autologin.Constants;

public class AutoLoginService extends AccessibilityService {

    // Netflix resource id's
    private static final String menuSignIn = "com.netflix.mediaclient:id/menu_sign_in";
    private static final String loginEmail = "com.netflix.mediaclient:id/login_email";
    private static final String loginPassword = "com.netflix.mediaclient:id/login_password";
    private static final String loginActionBtn = "com.netflix.mediaclient:id/login_action_btn";

    // Vars
    private static final String TAG = "AutoLogin: AutoLoginService";
    private Bundle bundle;
    private SharedPreferences prefs;

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        bundle = new Bundle();
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        AccessibilityNodeInfo currentNode = getRootInActiveWindow();
        if (currentNode != null)
            processLogin(currentNode);
    }

    @Override
    public void onInterrupt() {
    }

    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
        Log.i(TAG, "onServiceConnected");
    }

    private void processLogin(AccessibilityNodeInfo currentNode) {
        for (int i = 0; i < currentNode.getChildCount(); i++) {
            AccessibilityNodeInfo child = currentNode.getChild(i);

            if (child.getViewIdResourceName() == null)
                continue;

            // Automate
            switch (child.getViewIdResourceName()) {
                case menuSignIn:
                case loginActionBtn:
                    child.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                    break;
                case loginEmail:
                    bundle.clear();
                    bundle.putCharSequence(
                            AccessibilityNodeInfo.ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE,
                            getCredentials(Constants.EMAIL_PHONE_KEY));
                    child.performAction(AccessibilityNodeInfo.ACTION_SET_TEXT, bundle);
                    break;
                case loginPassword:
                    bundle.clear();
                    bundle.putCharSequence(
                            AccessibilityNodeInfo.ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE,
                            getCredentials(Constants.PASSWORD_KEY));
                    child.performAction(AccessibilityNodeInfo.ACTION_SET_TEXT, bundle);
                    break;
            }
            processLogin(currentNode.getChild(i));
        }
    }

    private String getCredentials(String key) {
        String value = prefs.getString(key, "");
        if (value.equals(""))
            Toast.makeText(this, "Please save login details first", Toast.LENGTH_LONG).show();

        return value;
    }
}
