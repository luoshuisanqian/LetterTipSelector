package com.lx.lettertipselector;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class LetterSelector extends View {

    OnLetterChangedListener mOnLetterChangedListener;
    String[] b = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "#"};
    int choose = -1;
    private Paint paint = new Paint();
    boolean showBkg = false;
    private float mScale = 1;

    public void setOnLetterChangedListener(
            OnLetterChangedListener onLetterChangedListener) {
        this.mOnLetterChangedListener = onLetterChangedListener;
    }

    public LetterSelector(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public LetterSelector(Context context, AttributeSet attrs) {
        super(context, attrs);
        mScale = (float) getContext().getResources().getDisplayMetrics().widthPixels / 800;
    }

    public LetterSelector(Context context) {
        super(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (showBkg) {
            canvas.drawColor(Color.parseColor("#40000000"));
        }

        int height = getHeight();
        int width = getWidth();
        int singleHeight = height / b.length;
        for (int i = 0; i < b.length; i++) {
            paint.setColor(Color.GRAY);
            paint.setTypeface(Typeface.DEFAULT_BOLD);
            int fontSize = (int) (18 * mScale);
            paint.setTextSize(fontSize);
            paint.setAntiAlias(true);
            if (i == choose) {
                paint.setColor(Color.parseColor("#3399ff"));
                paint.setFakeBoldText(true);
            }
            float xPos = width / 2 - paint.measureText(b[i]) / 2;
            float yPos = singleHeight * i + singleHeight;
            canvas.drawText(b[i], xPos, yPos, paint);
            paint.reset();
        }

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        final int action = event.getAction();
        final float y = event.getY();
        final int oldChoose = choose;
        final OnLetterChangedListener listener = mOnLetterChangedListener;
        final int c = (int) (y / getHeight() * b.length);

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                showBkg = true;
                if (oldChoose != c && listener != null) {
                    if (c >= 0 && c < b.length) {
                        listener.onLetterChanged(b[c]);
                        choose = c;
                        invalidate();
                    }
                }

                break;
            case MotionEvent.ACTION_MOVE:
                if (oldChoose != c && listener != null) {
                    if (c >= 0 && c < b.length) {
                        listener.onLetterChanged(b[c]);
                        choose = c;
                        invalidate();
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                showBkg = false;
                choose = -1;
                invalidate();
                break;
        }
        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }


    public interface OnLetterChangedListener {
        void onLetterChanged(String s);
    }
}
