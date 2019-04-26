package com.murat.gles;

import android.content.Context;


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

    public static float nextFloat(){
        return Utils.nextFloat();
    }

}
