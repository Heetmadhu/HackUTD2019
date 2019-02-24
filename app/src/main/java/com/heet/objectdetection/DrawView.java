package com.heet.objectdetection;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public final class DrawView extends View {

    private int mRatioWidth;
    private int mRatioHeight;
    private final ArrayList<RectF> mDrawPoint;
    private int mWidth;
    private int mHeight;
    private float mRatioX;
    private float mRatioY;
    private List<ImageClassifier.Recognition> results;
    int counts;
    private int mImgWidth;
    private int mImgHeight;
    private final int[] mColorArray;
    Paint mPaint;

    @TargetApi(Build.VERSION_CODES.M)
    @RequiresApi(api = Build.VERSION_CODES.M)
    public DrawView(Context context) {
        super(context);
        this.mDrawPoint = new ArrayList<RectF>();
        this.mColorArray = new int[]{this.getResources().getColor(R.color.color_top, (Resources.Theme)null), this.getResources().getColor(R.color.color_l_wrist, (Resources.Theme)null), this.getResources().getColor(R.color.color_r_wrist, (Resources.Theme)null), this.getResources().getColor(R.color.color_l_ankle, (Resources.Theme)null), this.getResources().getColor(R.color.color_r_ankle, (Resources.Theme)null), this.getResources().getColor(R.color.color_background, (Resources.Theme)null), this.getResources().getColor(R.color.color_background, (Resources.Theme)null)};
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(7);
        mPaint.setTextSize(35);
        mPaint.setColor(mColorArray[0]);

    }


    @TargetApi(Build.VERSION_CODES.M)
    @RequiresApi(api = Build.VERSION_CODES.M)
    public DrawView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mDrawPoint = new ArrayList<RectF>();
        this.mColorArray = new int[]{this.getResources().getColor(R.color.color_top, (Resources.Theme)null), this.getResources().getColor(R.color.color_l_wrist, (Resources.Theme)null), this.getResources().getColor(R.color.color_r_wrist, (Resources.Theme)null), this.getResources().getColor(R.color.color_l_ankle, (Resources.Theme)null), this.getResources().getColor(R.color.color_r_ankle, (Resources.Theme)null), this.getResources().getColor(R.color.color_background, (Resources.Theme)null), this.getResources().getColor(R.color.color_background, (Resources.Theme)null)};
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(7);
        mPaint.setTextSize(35);
        mPaint.setColor(mColorArray[0]);

//        setExercise();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public DrawView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mDrawPoint = new ArrayList<RectF>();
        this.mColorArray = new int[]{this.getResources().getColor(R.color.color_top, (Resources.Theme)null), this.getResources().getColor(R.color.color_l_wrist, (Resources.Theme)null), this.getResources().getColor(R.color.color_r_wrist, (Resources.Theme)null), this.getResources().getColor(R.color.color_l_ankle, (Resources.Theme)null), this.getResources().getColor(R.color.color_r_ankle, (Resources.Theme)null), this.getResources().getColor(R.color.color_background, (Resources.Theme)null), this.getResources().getColor(R.color.color_background, (Resources.Theme)null)};
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(mColorArray[0]);
        mPaint.setStrokeWidth(7);
        mPaint.setTextSize(35);
//        setExercise();
    }








    public final void setImgSize(int width, int height) {
        mImgWidth = width;
        mImgHeight = height;

        requestLayout();
    }

    public final void setResults(List<ImageClassifier.Recognition> results) {
        this.mDrawPoint.clear();
        this.results = results;

        for(int i = 0; i<results.size();i++) {
            RectF rectF = results.get(i).getLocation();
            rectF.bottom = rectF.bottom / this.mRatioY;
            rectF.top = rectF.top / this.mRatioY;
            rectF.left = rectF.left / this.mRatioX;
            rectF.right = rectF.right / this.mRatioX;
            results.get(i).setLocation(rectF);
        }

    }

    public final void setAspectRatio(int width, int height) throws Throwable {
        if(width >= 0 && height >= 0) {
            this.mRatioWidth = width;
            this.mRatioHeight = height;
            this.requestLayout();
        } else {
            throw (Throwable)(new IllegalArgumentException("Size cannot be negative."));
        }
    }






    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(results != null) {
        for(int i = 0; i<results.size();i++){
            mPaint.setColor(mColorArray[0]);
            canvas.drawRect(results.get(i).getLocation(),mPaint);
            mPaint.setColor(mColorArray[1]);
            canvas.drawText(results.get(i).getTitle(),results.get(i).getLocation().left,results.get(i).getLocation().top,mPaint);
        }
        }
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        if(this.mRatioWidth != 0 && this.mRatioHeight != 0) {
            if(width < height * this.mRatioWidth / this.mRatioHeight) {
                this.mWidth = width;
                this.mHeight = width * this.mRatioHeight / this.mRatioWidth;
            } else {
                this.mWidth = height * this.mRatioWidth / this.mRatioHeight;
                this.mHeight = height;
            }
        } else {
            this.setMeasuredDimension(width, height);
        }

        this.setMeasuredDimension(this.mWidth, this.mHeight);
        this.mRatioX = (float)this.mImgWidth / (float)this.mWidth;
        this.mRatioY = (float)this.mImgHeight / (float)this.mHeight;
    }



}
