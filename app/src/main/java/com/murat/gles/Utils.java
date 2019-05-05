package com.murat.gles;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ConfigurationInfo;
import android.os.Build;


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Random;

public class Utils {

    public static String getJSONStringFromResource(Context context, int id) {
        try {
            InputStream stream = context.getResources().openRawResource(id);
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
            StringBuilder builder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line).append("\n");
            }
            stream.close();
            return builder.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private static Random Utils = new Random();

    public static float nextRandomInRange(float start, float end) {
        return (end - start) * Utils.nextFloat() + start;
    }

    public static int nextRandomInRange(int start, int end) {
        return (int) ((end - start) * Utils.nextFloat() + start);
    }

    public static float nextFloat() {
        return Utils.nextFloat();
    }

    public static int min(int x, int y) {
        return x < y ? x : y;
    }

    public static int max(int x, int y) {
        return x > y ? x : y;
    }

    public static boolean isSupportES20(Context context) {
        final ActivityManager activityManager = ((ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE));
        final ConfigurationInfo configurationInfo = activityManager.getDeviceConfigurationInfo();
        return configurationInfo.reqGlEsVersion >= 0x20000
                || (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1
                && (Build.FINGERPRINT.startsWith("generic")
                || Build.FINGERPRINT.startsWith("unknown")
                || Build.MODEL.contains("google_sdk")
                || Build.MODEL.contains("Emulator")
                || Build.MODEL.contains("Android SDK built for x86")));
    }

}
