package com.theradioapp.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.EditText;

import com.theradioapp.R;

/**
 * Created by VB on 9/23/2017.
 */

public class EditTextCustom extends EditText {

    private static final String TAG = EditTextCustom.class.getSimpleName();

    public EditTextCustom(Context context) {
        super(context);
    }

    public EditTextCustom(Context context, AttributeSet attrs) {
        super(context, attrs);
        parseAttributes(context, attrs); //I'll explain this method later
    }

    public EditTextCustom(Context context, AttributeSet attrs, int defStyle) {
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
