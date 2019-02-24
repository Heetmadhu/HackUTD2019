package com.heet.objectdetection;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

public class AutoFrameLayout extends FrameLayout {
    private int mRatioWidth;
    private int mRatioHeight;
    private Context context;


    public final void setAspectRatio(int width, int height) throws Throwable {
        if (width >= 0 && height >= 0) {
            this.mRatioWidth = width;
            this.mRatioHeight = height;
            this.requestLayout();
        } else {
            throw (Throwable)(new IllegalArgumentException("Size cannot be negative."));
        }
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        if (this.mRatioWidth != 0 && this.mRatioHeight != 0) {
            if (width < height * this.mRatioWidth / this.mRatioHeight) {
                this.setMeasuredDimension(width, width * this.mRatioHeight / this.mRatioWidth);
            } else {
                this.setMeasuredDimension(height * this.mRatioWidth / this.mRatioHeight, height);
            }
        } else {
            this.setMeasuredDimension(width, height);
        }

    }


    public AutoFrameLayout(Context context) {

        super(context);

    }

    public AutoFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    public AutoFrameLayout(Context context, AttributeSet attrs, int defStyleAttr) {

        super(context, attrs, defStyleAttr);
    }
}
