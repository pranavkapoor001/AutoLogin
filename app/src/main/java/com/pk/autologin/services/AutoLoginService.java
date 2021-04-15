package com.pk.autologin.services;

import android.accessibilityservice.AccessibilityService;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

public class AutoLoginService extends AccessibilityService {

    private static final String TAG = "AutoLogin: AutoLoginService";

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
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

            Log.d(TAG, "traverseViews: " + child.getClassName());
            processLogin(currentNode.getChild(i));
        }
    }
}
