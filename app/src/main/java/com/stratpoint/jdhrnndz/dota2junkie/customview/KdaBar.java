package com.stratpoint.jdhrnndz.dota2junkie.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

import com.stratpoint.jdhrnndz.dota2junkie.R;

/**
 * Author: John Denielle F. Hernandez
 * Date: 8/11/16
 * Description: Custom view for representing the player's kills, deaths, and assists scores.
 */
public class KdaBar extends View {
    private int mKillCount, mDeathCount, mAssistCount;
    private int mKillColor, mDeathColor, mAssistColor;
    private int mViewWidth, mViewHeight;
    private Paint mKillBarPaint, mDeathBarPaint, mAssistBarPaint;
    private float mKillPart, mDeathPart, mAssistPart;

    public KdaBar(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.KdaBar,
                0, 0);

        try {
            mKillCount = a.getInt(R.styleable.KdaBar_killCount, 0);
            mDeathCount = a.getInt(R.styleable.KdaBar_deathCount, 0);
            mAssistCount = a.getInt(R.styleable.KdaBar_assistCount, 0);

            mKillColor = a.getColor(R.styleable.KdaBar_killBarColor, ContextCompat.getColor(getContext(), R.color.kill_score_color));
            mDeathColor = a.getColor(R.styleable.KdaBar_deathBarColor, ContextCompat.getColor(getContext(), R.color.death_score_color));
            mAssistColor = a.getColor(R.styleable.KdaBar_assistBarColor, ContextCompat.getColor(getContext(), R.color.assist_score_color));
        } finally {
            a.recycle();
        }

        init();
    }

    public void setValues(int killCount, int deathCount, int assistCount) {
        mKillCount = killCount;
        mDeathCount = deathCount;
        mAssistCount = assistCount;
    }

    private void calculatePartitions() {
        float total = mKillCount + mDeathCount + mAssistCount;
        mKillPart = (mKillCount/total) * mViewWidth;
        mDeathPart = (mDeathCount/total) * mViewWidth;
        mAssistPart = (mAssistCount/total) * mViewWidth;
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        calculatePartitions();

        canvas.drawRect(mKillPart+mDeathPart, 0f, mKillPart+mDeathPart+mAssistPart, mViewHeight, mAssistBarPaint);
        canvas.drawRect(mKillPart, 0f, mKillPart+mDeathPart, mViewHeight, mDeathBarPaint);
        canvas.drawRect(0f, 0f, mKillPart, mViewHeight, mKillBarPaint);
    }

    @Override
    protected void onSizeChanged(int xNew, int yNew, int xOld, int yOld){
        super.onSizeChanged(xNew, yNew, xOld, yOld);

        mViewWidth = xNew;
        mViewHeight = yNew;
    }

    private void init() {
        mKillBarPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mKillBarPaint.setColor(mKillColor);

        mDeathBarPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mDeathBarPaint.setColor(mDeathColor);

        mAssistBarPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mAssistBarPaint.setColor(mAssistColor);
    }
}