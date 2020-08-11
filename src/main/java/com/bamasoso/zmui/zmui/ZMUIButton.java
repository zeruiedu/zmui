package com.bamasoso.zmui.zmui;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import androidx.appcompat.widget.AppCompatButton;

import com.bamasoso.zmui.R;

/*
* 自定义button
* */
public class ZMUIButton extends AppCompatButton {

    //背景色
    private int backColor = 0;

    //文字颜色
    private ColorStateList textColor = null;

    //设置圆角
    private boolean fillet = false;

    //设置形状
    GradientDrawable gradientDrawable = null;

    //边框
    int borderWidth = 0;

    //边框颜色
    int borderColor = 0;

    public ZMUIButton(Context context) {
        super(context);
    }

    public ZMUIButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ZMUIButton(Context context, AttributeSet attrs, int defStyleAttr) {
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
            //设置文字的颜色
            textColor = a.getColorStateList(R.styleable.ZMView_zmui_textColor);
            if (textColor != null) {
                setTextColor(textColor);
            }
            //设置圆角
            fillet = a.getBoolean(R.styleable.ZMView_zmui_fillet, false);
            if (fillet) {
                getGradientDrawable();
                if (backColor != 0) {
                    gradientDrawable.setColor(backColor);
                    setBackgroundDrawable(gradientDrawable);
                }
            }
            //设置圆角的角度 fillte为true才有效
            float radius = a.getDimensionPixelSize(R.styleable.ZMView_zmui_radius, 0);
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
        }
    }
    /*
    * 角度
    * */
    private void setRadius(float radius){
        if (!fillet) {
            return;
        }
        getGradientDrawable();
        //外面传过来的是dp  设置的时候改为px
//        float temp_radius = SizeConvertUtil.dpTopx(getContext(), radius);
        gradientDrawable.setCornerRadius(radius);
        setBackgroundDrawable(gradientDrawable);
    }

    /*
     * 外边框
     * */
    private void setBorderWidth(){
        getGradientDrawable();
        gradientDrawable.setStroke(borderWidth, borderColor);
        setBackground(gradientDrawable);
    }

    private void getGradientDrawable() {
        if (gradientDrawable == null) {
            gradientDrawable = new GradientDrawable();
        }
    }
}
