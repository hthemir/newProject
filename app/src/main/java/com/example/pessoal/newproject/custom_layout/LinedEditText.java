package com.example.pessoal.newproject.custom_layout;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * Created by ZUP on 28/09/2017.
 */

//pediu pra extender appcompat em vez de edittext
@SuppressLint("AppCompatCustomView")
public class LinedEditText extends EditText {

    private Rect mRect;
    private Paint mPaint;

    public LinedEditText(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);

        mRect = new Rect();
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        int numberLines = getLineCount() > getHeight()/getLineHeight() ? getLineCount() : getHeight()/getLineHeight();

        int baseline = getLineBounds(0,mRect);

        for (int i=0; i<numberLines; i++) {
            canvas.drawLine(mRect.left, baseline+1, mRect.right, baseline+1, mPaint);
            baseline += getLineHeight();
        }
        super.onDraw(canvas);
    }
}
