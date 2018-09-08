package com.zykj.carfigure.utils;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;

public class Utils {
    //dip和px转换
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static int px2dip(Context context, int pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static void writeJson(String content, String fileName) {
        File myFile = new File(Constant.FilePath.APK_CACHE_DIR, fileName);
        try {
            myFile.createNewFile();
            FileOutputStream fos = new FileOutputStream(myFile);
            OutputStreamWriter osw = new OutputStreamWriter(fos, "utf-8");
            osw.write(content);
            osw.flush();
            fos.flush();
            osw.close();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static String readJson(String fileName) {
        File myfile = new File(Constant.FilePath.APK_CACHE_DIR, fileName);
        String str = "";
        if (myfile.exists()) {
            try {
                FileInputStream fis = new FileInputStream(myfile);
                InputStreamReader isr = new InputStreamReader(fis, "utf-8");
                char input[] = new char[fis.available()];
                isr.read(input);
                str = new String(input);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return str;
    }
}
