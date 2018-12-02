package com.theradioapp.views;

import android.content.Context;
import android.graphics.Typeface;

import java.util.Hashtable;

/**
 * Created by VB on 9/23/2017.
 */

public class TypeFaceProvider {

    public static final String TYPEFACE_FOLDER = "fonts";
    public static final String TYPEFACE_EXTENSION = ".OTF";

    private static Hashtable<String, Typeface> sTypeFaces = new Hashtable<String, Typeface>(
            2);

    public static Typeface getTypeFace(Context context, String fileName) {
        Typeface tempTypeface = sTypeFaces.get(fileName);

        if (tempTypeface == null) {
            String fontPath = new StringBuilder(TYPEFACE_FOLDER).append('/').append(fileName).append(TYPEFACE_EXTENSION).toString();
            tempTypeface = Typeface.createFromAsset(context.getAssets(), fontPath);
            sTypeFaces.put(fileName, tempTypeface);
        }

        return tempTypeface;
    }
}
