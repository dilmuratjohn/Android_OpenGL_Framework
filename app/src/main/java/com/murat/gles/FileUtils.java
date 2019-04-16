package com.murat.gles;

import android.content.Context;


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class FileUtils {

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

}
