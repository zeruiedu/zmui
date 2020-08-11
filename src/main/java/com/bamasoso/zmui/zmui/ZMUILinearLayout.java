package com.bamasoso.zmui.zmui;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.bamasoso.zmui.R;

/*
* 自定义LinearLayout
* */
public class ZMUILinearLayout extends LinearLayout {
    private String TAG = "ZMLinearLayoutView liveapp";
    //背景色
    private int backColor = 0;

    //设置圆角
    private boolean fillet = false;

    //圆角
    float radius = 0;

    //设置形状
    GradientDrawable gradientDrawable = null;

    //边框
    int borderWidth = 0;

    //边框颜色
    int borderColor = 0;

    //半角
    float radiusTopLeft = 0;
    float radiusTopRight = 0;
    float radiusBottomLeft = 0;
    float radiusBottomRight = 0;

    public ZMUILinearLayout(Context context) {
        super(context);
    }

    public ZMUILinearLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ZMUILinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ZMView, defStyleAttr, 0);
        if (a != null) {
            //设置背景色
            ColorStateList colorList  = a.getColorStateList(R.styleable.ZMView_zmui_backgroundColor);
            if (colorList  != null){
                backColor = colorList.getColorForState(getDrawableState(), 0);
                if (backColor != 0) {
                    setBackgroundColor(backColor);
                }
            }
            //设置圆角
            fillet = a.getBoolean(R.styleable.ZMView_zmui_fillet, false);
            if (fillet) {
                getGradientDrawable();
                if (backColor != 0) {
                    gradientDrawable.setColor(backColor);
                    setBackground(gradientDrawable);
                }
            }
            //设置圆角的角度 fillte为true才有效
            radius = a.getDimensionPixelSize(R.styleable.ZMView_zmui_radius, 0);
            if (fillet && radius != 0) {
                setRadius(radius);
            }
            //设置border宽度
            borderWidth = a.getDimensionPixelSize(R.styleable.ZMView_zmui_borderWidth, 0);
            //设置border颜色
            borderColor = a.getColor(R.styleable.ZMView_zmui_borderColor, 0);
            if (borderWidth != 0) {
                setBorderWidth();
            }
            //半角
            radiusTopLeft = a.getDimensionPixelSize(R.styleable.ZMView_zmui_radiusTopLeft, 0);
            radiusTopRight = a.getDimensionPixelSize(R.styleable.ZMView_zmui_radiusTopRight, 0);
            radiusBottomLeft = a.getDimensionPixelSize(R.styleable.ZMView_zmui_radiusBottomLeft, 0);
            radiusBottomRight = a.getDimensionPixelSize(R.styleable.ZMView_zmui_radiusBottomRight, 0);
            if (fillet && (radiusTopLeft != 0 || radiusTopRight!= 0 || radiusBottomLeft != 0 || radiusBottomRight != 0)) {
                setBRadius();
            }
        }
    }

    /*
    * 外边框
    * */
    private void setBorderWidth(){
        getGradientDrawable();
        gradientDrawable.setStroke(borderWidth, borderColor);
        setBackground(gradientDrawable);
    }
    /*
    * 角度
    * */
    private void setRadius(float radius){
        if (!fillet) {
            return;
        }
        getGradientDrawable();
        gradientDrawable.setCornerRadius(radius);
        setBackground(gradientDrawable);
    }
    /*
    * 半角
    * */
    private void setBRadius(){
        getGradientDrawable();
        float[] radii ={ radiusTopLeft, radiusTopLeft, radiusTopRight, radiusTopRight, radiusBottomRight, radiusBottomRight, radiusBottomLeft, radiusBottomLeft};
        gradientDrawable.setCornerRadii(radii);
        setBackground(gradientDrawable);
    }
    private void getGradientDrawable() {
        if (gradientDrawable == null) {
            gradientDrawable = new GradientDrawable();
        }
    }
}
