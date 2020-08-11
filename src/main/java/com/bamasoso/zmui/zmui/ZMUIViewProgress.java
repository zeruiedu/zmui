package com.bamasoso.zmui.zmui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import androidx.annotation.Nullable;

import com.bamasoso.zmui.R;
import com.bamasoso.zmui.util.SizeConvertUtil;

/**
 * 进度控制
 */
public class ZMUIViewProgress extends View {

    private Context context;
    //创建发言时间图片画笔
    public Paint paint = new Paint();
    //创建发言时间文本画笔

    private String textPicProgress = "0%";

    //画布
    public Canvas canvas = null;

    //父布局的宽度
    private int viewParentWidth;

    //发言时长上层进度条的宽度
    private int textPicWidth;


    private int type;

    //字体颜色
    private int textColor;
    //字体大小
    private float textSize;
    //图片
    private int src;

    public ZMUIViewProgress(Context context) {
        this(context, null);
    }

    public ZMUIViewProgress(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ZMUIViewProgress(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ZMUIViewProgress, defStyleAttr, 0);
        if (a != null) {
            //字体颜色
            int mTextColor = a.getColor(R.styleable.ZMUIViewProgress_zmui_textcolor, Color.BLACK);
            this.textColor = mTextColor;
            //字体大小
            float mTextSize = a.getDimension(R.styleable.ZMUIViewProgress_zmui_textsize, 14);

            this.textSize = mTextSize;
            //图片
            int mSrc = a.getResourceId(R.styleable.ZMUIViewProgress_zmui_src, R.drawable.apprise_talktime_pic);;

            this.src = mSrc;
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

        drawText();
    }

    /**
     * 绘制发言时长图片
     */
    private void drawPic() {
        if (textPicWidth < SizeConvertUtil.dpTopx(context, 55)) {
            //左侧情况
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), src, null);
            //Bitmap：图片对象，left:偏移左边的位置，top： 偏移顶部的位置(计算得到的数据是7，但是图片原因，我设置为6)
            canvas.drawBitmap(bitmap, textPicWidth + SizeConvertUtil.dpTopx(context, 6), 0, paint);
        } else {
            //中间情况
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), src, null);
            //Bitmap：图片对象，left:偏移左边的位置，top： 偏移顶部的位置(计算得到的数据是7，但是图片原因，我设置为6)
            canvas.drawBitmap(bitmap, textPicWidth + SizeConvertUtil.dpTopx(context, 6), 0, paint);
        }
    }


    /**
     * 绘制发言时长文本
     * 文本加图片最大为55dp
     */
    private void drawText() {
        if (textPicWidth < SizeConvertUtil.dpTopx(context, 55)) {
            //最左侧情况
            paint.setColor(textColor);
            paint.setTextSize(textSize);
            paint.setStyle(Paint.Style.FILL);
            paint.setStrokeWidth(5f);
            canvas.drawText(textPicProgress, textPicWidth + SizeConvertUtil.dpTopx(context, 33), SizeConvertUtil.dpTopx(context, 15), paint);
        } else {
            //中间情况
            paint.setColor(textColor);
            paint.setTextSize(textSize);
            paint.setStyle(Paint.Style.FILL);
            paint.setStrokeWidth(5f);
            canvas.drawText(textPicProgress, textPicWidth - paint.measureText(textPicProgress) + SizeConvertUtil.dpTopx(context, 4), SizeConvertUtil.dpTopx(context, 15), paint);
        }
        drawPic();
    }


    /**
     * 设置上层宽度，记得换为px
     */
    public void setTextPicWidth(int mWidth, String progress, int type) {
        this.textPicWidth = mWidth;
        this.textPicProgress = progress;
        this.type = type;

        //避免遗漏，注意冲重新绘制下布局
        invalidate();
    }

}
