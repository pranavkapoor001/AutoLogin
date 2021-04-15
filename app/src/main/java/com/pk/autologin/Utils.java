package com.pk.autologin;

import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.widget.Toast;

import com.pk.autologin.services.AutoLoginService;

public class Utils {

    public static void openAccessibilitySettings(Context context) {
        final Intent intent = new Intent(android.provider.Settings.ACTION_ACCESSIBILITY_SETTINGS);
        context.startActivity(intent);

        Toast.makeText(context,
                "Please enable Accessibility Control for AutoLogin", Toast.LENGTH_LONG).show();
    }

    public static boolean isAccessibilityGranted(Context context) {
        final String service = context.getPackageName() + "/" + AutoLoginService.class.getCanonicalName();
        String prefString = Settings.Secure.getString(
                context.getContentResolver(), Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES);

        if (prefString != null && prefString.contains(service))
            return true;

        openAccessibilitySettings(context);
        return false;
    }
}
