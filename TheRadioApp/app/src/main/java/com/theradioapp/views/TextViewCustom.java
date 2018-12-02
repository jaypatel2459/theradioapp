package com.theradioapp.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.TextView;

import com.theradioapp.R;


/**
 * Created by VB on 9/23/2017.
 */

public class TextViewCustom extends TextView {

    private static final String TAG = TextViewCustom.class.getSimpleName();

    public TextViewCustom(Context context) {
        super(context);
    }

    public TextViewCustom(Context context, AttributeSet attrs) {
        super(context, attrs);
        parseAttributes(context, attrs); //I'll explain this method later
    }

    public TextViewCustom(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        parseAttributes(context, attrs);
    }

    private void parseAttributes(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TextViewCustom);
        String customFont = typedArray.getString(R.styleable.TextViewCustom_custom_fonts);
        if (customFont != null && customFont.length() > 0) {
            setTypeface(TypeFaceProvider.getTypeFace(context, customFont));
        }
        typedArray.recycle();
    }
}