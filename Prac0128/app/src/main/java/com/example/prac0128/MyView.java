package com.example.prac0128;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

public class MyView extends View {
    Context context;
    int color;
    int width;
    int height;


    public MyView(Context context) {
        super(context);
    }

    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;

        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.MyView);
            color = a.getColor(R.styleable.MyView_customColor, Color.RED);
        }
    }

    public MyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.alpha(Color.CYAN));

        RectF rect = new RectF(15, 15, 160, 160);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(color);
        canvas.drawArc(rect, 0, 360, false, paint);
    }

    @Override
    protected void onMeasure (int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);

        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        if (heightMode == MeasureSpec.AT_MOST) {
            height = 400;
        } else if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        }

        if (widthMode == MeasureSpec.AT_MOST) {
            width = 800;
        } else if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        }
        setMeasuredDimension(width, height);
    }
}
