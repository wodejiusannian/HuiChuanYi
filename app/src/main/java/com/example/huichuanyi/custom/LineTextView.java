package com.example.huichuanyi.custom;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by 小五 on 2017/3/18.
 */

@SuppressLint("AppCompatCustomView")
public class LineTextView extends TextView {
    private String text;
    private Paint mPaint;

    public LineTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        text = getText().toString();
        mPaint = new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setColor(Color.parseColor("#999999"));
        mPaint.setStrokeWidth(2);
        canvas.drawLine(0, getHeight() / 2, getWidth(), getHeight() / 2, mPaint);
    }
}
