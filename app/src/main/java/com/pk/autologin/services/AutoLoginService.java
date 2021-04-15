package com.pk.autologin.services;

import android.accessibilityservice.AccessibilityService;
import android.os.Bundle;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

public class AutoLoginService extends AccessibilityService {

    // Netflix resource id's
    private static final String menuSignIn = "com.netflix.mediaclient:id/menu_sign_in";
    private static final String loginEmail = "com.netflix.mediaclient:id/login_email";
    private static final String loginPassword = "com.netflix.mediaclient:id/login_password";
    private static final String loginActionBtn = "com.netflix.mediaclient:id/login_action_btn";

    // Vars
    private static final String TAG = "AutoLogin: AutoLoginService";
    private Bundle bundle;

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        bundle = new Bundle();
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
        Log.d(TAG, "traverseViews: " + currentNode.toString());

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
                            AccessibilityNodeInfo.ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE, "DummyEmail");
                    child.performAction(AccessibilityNodeInfo.ACTION_SET_TEXT, bundle);
                    break;
                case loginPassword:
                    bundle.clear();
                    bundle.putCharSequence(
                            AccessibilityNodeInfo.ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE, "DummyPass");
                    child.performAction(AccessibilityNodeInfo.ACTION_SET_TEXT, bundle);
                    break;
            }
            processLogin(currentNode.getChild(i));
        }
    }
}
