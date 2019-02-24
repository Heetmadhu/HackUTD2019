package com.heet.objectdetection;

import android.content.Context;
import android.util.AttributeSet;
import android.view.TextureView;

public class AutoFitTextureView extends TextureView {
    private int mRatioWidth;
    private int mRatioHeight;

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


    public AutoFitTextureView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
//        Intrinsics.checkParameterIsNotNull(context, "context");

    }

    // $FF: synthetic method
//    @JvmOverloads
    public AutoFitTextureView(Context var1, AttributeSet var2, int var3, int var4) {
        this(var1, var2, var3);
        if ((var4 & 2) != 0) {
            var2 = (AttributeSet)null;
        }

        if ((var4 & 4) != 0) {
            var3 = 0;
        }


    }

    //    @JvmOverloads
    public AutoFitTextureView(Context context, AttributeSet attrs) {
        this(context, attrs, 0, 4);
    }

    //    @JvmOverloads
    public AutoFitTextureView(Context context) {
        this(context, (AttributeSet)null, 0, 6);
    }



}
