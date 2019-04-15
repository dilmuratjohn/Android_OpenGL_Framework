package com.murat.gles.common;

import android.content.Context;
import android.content.res.Resources;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

class GLShaderCodeReader {

    static String readTextFileFromResource(Context context, int id) {
        StringBuilder content = new StringBuilder();
        try {
            InputStream inputStream = context.getResources().openRawResource(id);
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String nextLine;
            while ((nextLine = bufferedReader.readLine()) != null) {
                content.append(nextLine);
                content.append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        } catch (Resources.NotFoundException e) {
            throw new RuntimeException(e.getMessage());
        }
        return content.toString();
    }

}
