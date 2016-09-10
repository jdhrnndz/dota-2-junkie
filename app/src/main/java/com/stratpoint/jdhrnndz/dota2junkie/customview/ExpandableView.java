package com.stratpoint.jdhrnndz.dota2junkie.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.LinearLayout;

import com.stratpoint.jdhrnndz.dota2junkie.R;

/**
 * Author: John Denielle F. Hernandez
 * Date: 9/2/16
 * Description: Custom view used to achieve an accordion effect in a layout.
 */
public class ExpandableView extends LinearLayout implements View.OnClickListener {
    private View mBottomView;
    private Boolean mIsExpanded;
    private int mBottomViewHeight;
    private boolean mIsHeightSet;
    private AccordionAnimation mExpandAnimation, mCollapseAnimation;

    public ExpandableView(Context context, AttributeSet attrs) {
        super(context, attrs);

        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        setOrientation(LinearLayout.VERTICAL);
        setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));

        LayoutInflater mInflater = LayoutInflater.from(context);

        mIsExpanded = false; // state of the expandable view; initial state: collapsed
        mIsHeightSet = false; // makes measuring bottom layout done only once

        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.ExpandableView,
                0, 0);

        View mTopView;
        try {
            mTopView = mInflater.inflate(a.getResourceId(R.styleable.ExpandableView_topLayout, 0), null, false);
            mBottomView = mInflater.inflate(a.getResourceId(R.styleable.ExpandableView_bottomLayout, 0), null, false);
        } finally {
            a.recycle();
        }

        addView(mTopView);
        addView(mBottomView);

        mBottomView.setVisibility(GONE);
        setOnClickListener(this /** OnClickListener **/);
        mExpandAnimation = new ExpandAnimation(mBottomView);
        mCollapseAnimation = new CollapseAnimation(mBottomView);
    }

    @Override
    public void onClick(View view) {
        if (!mIsHeightSet) {
            mBottomViewHeight = measureBottomView();
            int animDuration = ((int)(mBottomViewHeight / mBottomView.getContext().getResources().getDisplayMetrics().density)) * 4;
            mExpandAnimation.setDuration(animDuration);
            mCollapseAnimation.setDuration(animDuration);
            mIsHeightSet = true;
        }

        this.toggleView();
    }

    private void toggleView() {
        animate(mIsExpanded = !mIsExpanded);
    }

    private int measureBottomView() {
        int bottomViewHeight;

        mBottomView.setVisibility(VISIBLE);

        LayoutParams params = (LayoutParams) mBottomView.getLayoutParams();
        final int width = this.getWidth() - params.leftMargin - params.rightMargin;

        mBottomView.measure(MeasureSpec.makeMeasureSpec(width, MeasureSpec.AT_MOST),
                MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));

        bottomViewHeight = mBottomView.getMeasuredHeight();

        return bottomViewHeight;
    }

    private void animate(boolean isExpanded) {
        if (isExpanded) {
            mBottomView.getLayoutParams().height = 1;
            mBottomView.setVisibility(VISIBLE);
            mBottomView.startAnimation(mExpandAnimation);
        } else {
            mBottomView.startAnimation(mCollapseAnimation);
        }
    }

    class ExpandAnimation extends AccordionAnimation {
        public ExpandAnimation(View view) {
            super(view);
        }

        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            if (Float.compare(interpolatedTime, 1.0f) == 0) {
                mView.getLayoutParams().height = LayoutParams.WRAP_CONTENT;
            } else {
                mView.getLayoutParams().height = (int) (mBottomViewHeight * interpolatedTime);
                mView.requestLayout();
            }
        }
    }

    class CollapseAnimation extends AccordionAnimation {
        public CollapseAnimation(View view) {
            super(view);
        }

        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            if (Float.compare(interpolatedTime, 1.0f) == 0) {
                mView.setVisibility(GONE);
            } else {
                mView.getLayoutParams().height = (int) (mBottomViewHeight - (mBottomViewHeight * interpolatedTime));
                mView.requestLayout();
            }
        }
    }

    class AccordionAnimation extends Animation {
        protected View mView;

        // Constructor to set view to animate/get measurements from
        public AccordionAnimation(View view) {
            super();

            mView = view;
        }

        @Override
        public void initialize(int width, int height, int parentWidth, int parentHeight) {
            super.initialize(width, height, parentWidth, parentHeight);
        }

        @Override
        public boolean willChangeBounds() {
            return true;
        }
    }
}