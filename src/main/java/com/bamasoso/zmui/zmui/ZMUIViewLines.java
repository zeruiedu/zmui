package com.bamasoso.zmui.zmui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;
import androidx.annotation.Nullable;

import com.bamasoso.zmui.R;

public class ZMUIViewLines extends View {

    private Context context;
    //创建画笔
    public Paint paint = new Paint();
    //画布
    public Canvas canvas = null;
    //父布局的宽度
    private int viewParentWidth;

    //普通背景色
    private int normalColor;

    //可变背景色开始
    private int changeColorStart;

    //可变背景色结束
    private int changeColorEnd;

    //发言时长上层进度条的宽度
    private int speakTopLineWidth = 0;
    //授权时长上层进度条的宽度
    private int addPerTopLineWidth = 0;
    //绘制线条高度
    private int height;

    //大边框的画笔宽度
    private int bigReftWidth;
    //白色线条画笔的宽度
    private int writeLineWidth;
    //每个颜色线条的宽度
    private int colorLineWidth;

    private boolean isNoProgress = false;

    private int type;

    public ZMUIViewLines(Context context) {
        this(context, null);
    }

    public ZMUIViewLines(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ZMUIViewLines(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ZMUIViewLines, defStyleAttr, 0);
        if (a != null) {
            //设置普通背景色
            int normalColor = a.getColor(R.styleable.ZMUIViewLines_zmui_normalcolor, Color.WHITE);
            this.normalColor = normalColor;
            //渐变色开始
            int changeColorStart = a.getColor(R.styleable.ZMUIViewLines_zmui_changecolorstart, Color.WHITE);
            this.changeColorStart = changeColorStart;
            //渐变色结束
            int changeColorEnd = a.getColor(R.styleable.ZMUIViewLines_zmui_changecolorend, Color.WHITE);
            this.changeColorEnd = changeColorEnd;
            //颜色和线条的高度
            float dimension = a.getDimension(R.styleable.ZMUIViewLines_zmui_colorheight, 0);
            this.height = (int) dimension;
            //小线条宽度
            float dimension1 = a.getDimension(R.styleable.ZMUIViewLines_zmui_itemlineswidth, 0);
            this.writeLineWidth = (int) dimension1;
            //小底色宽度
            float dimension2 = a.getDimension(R.styleable.ZMUIViewLines_zmui_itemcolorwidth, 0);
            this.colorLineWidth = (int) dimension2;
            //可变底色宽度
            float dimension3 = a.getDimension(R.styleable.ZMUIViewLines_zmui_changecolorwidth, 0);
            this.speakTopLineWidth = (int) dimension3;
            this.addPerTopLineWidth = (int) dimension3;
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        this.viewParentWidth = getWidth();
    }


    /**
     * 主要重写的方法
     *
     * @param canvas
     */
    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.canvas = canvas;

        drawBottomColor(paint, canvas);
        if (!isNoProgress){
            drawTopColor(paint, canvas);
        }
        drawWhiteLine(paint, canvas);
    }


    /**
     * 绘制底部颜色
     *
     * @param mPaint
     * @param canvas
     */
    private void drawBottomColor(Paint mPaint, Canvas canvas) {
        /**
         * STROKE 描边
         * FILL 填充
         * FILL_AND_STROKE 描边加填充
         */
        //设置画笔模式
        mPaint.setStyle(Paint.Style.FILL);
        //设置画笔宽度
        mPaint.setStrokeWidth(bigReftWidth);
        //画笔颜色
        mPaint.setColor(normalColor);
        //绘制圆角矩形
        canvas.drawRoundRect(new RectF(0, 0, viewParentWidth, height), 0, 0, mPaint);
    }

    /**
     * 绘制上层颜色
     *
     * @param mPaint
     * @param canvas
     */
    private void drawTopColor(Paint mPaint, Canvas canvas) {
        mPaint = new Paint();

        /**
         * STROKE 描边
         * FILL 填充
         * FILL_AND_STROKE 描边加填充
         */
        //设置画笔模式
        mPaint.setStyle(Paint.Style.FILL);
        //设置画笔宽度
        mPaint.setStrokeWidth(bigReftWidth);

        //设置渐变形式
        LinearGradient lg = new LinearGradient(0, 0, speakTopLineWidth, height, changeColorStart, changeColorEnd, Shader.TileMode.MIRROR);

        mPaint.setShader(lg);
        //绘制圆角矩形
        canvas.drawRoundRect(new RectF(0, 0, speakTopLineWidth, height), 0, 0, mPaint);

    }


    /**
     * 绘制小的白色线条
     *
     * @param paint
     * @param canvas
     */
    private void drawWhiteLine(Paint paint, Canvas canvas) {
        paint.setColor(Color.WHITE);
        paint.setStrokeWidth(writeLineWidth);
        paint.setStyle(Paint.Style.FILL);

        //计算绘制线条个数
        int linesNum = viewParentWidth % (writeLineWidth + colorLineWidth) == 0 ? (viewParentWidth / (writeLineWidth + colorLineWidth)) : (viewParentWidth / (writeLineWidth + colorLineWidth)) - 1;

        int startX = 0;
        int startY = 0;
        int endX = 0;
        int endY = height;

        //循环绘制线条
        for (int i = 1; i <= linesNum + 1; i++) {
            //绘制第i个线条
            startX = (colorLineWidth * i) + (writeLineWidth) * (i - 1);

            endX = startX;
//            Log.e("TAG", "绘制的x位置坐标 I " + i + "   " + startX);
//            Log.e("TAG", "绘制的colorLineWidth位置坐标 " + colorLineWidth);
//            Log.e("TAG", "绘制的writeLineWidth位置坐标 " + writeLineWidth);

            canvas.drawLine(startX, startY, endX, endY, paint);
        }
    }

    /**
     * 设置上层宽度，记得换为px
     */
    public void setSpeakTopLineWidth(int speakWidth) {
        if (speakWidth == -1){
            isNoProgress = true;
        }
        this.speakTopLineWidth = speakWidth;
        //避免遗漏，注意冲重新绘制下布局

        //重新绘制
        invalidate();
    }


}
