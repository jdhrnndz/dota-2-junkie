package com.stratpoint.jdhrnndz.dota2junkie.util;

import android.content.Context;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Author: John Denielle F. Hernandez
 * Date: 8/9/16.
 * Description: Copied from http://stackoverflow.com/questions/9544737/read-file-from-assets
 */
public class StringAssetReader {
    public static String getStringFromAsset (Context context, String filePath) throws Exception {
        StringBuilder buffer = new StringBuilder();
        // Opening an asset returns an input stream, so use it in buffered reader
        InputStream jsonFile = context.getAssets().open(filePath);

        BufferedReader in = new BufferedReader(new InputStreamReader(jsonFile, "UTF-8"));

        String line;
        while ((line=in.readLine())!=null) {
            buffer.append(line);
        }

        in.close();

        return buffer.toString();
    }
}
