package com.stratpoint.jdhrnndz.dota2junkie.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.stratpoint.jdhrnndz.dota2junkie.R;

/**
 * Author: John Denielle F. Hernandez
 * Date: 9/2/16
 * Description: Custom view used to achieve an accordion effect in a layout.
 */
public class ExpandableView extends LinearLayout implements View.OnClickListener {
    private View mTopView, mBottomView;
    private Boolean mIsExpanded;
    private Context mContext;
    private LayoutInflater mInflater;
    private int mBottomViewHeight;
    private boolean mIsHeightSet;

    public ExpandableView(Context context, AttributeSet attrs) {
        super(context, attrs);

        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        setOrientation(LinearLayout.VERTICAL);

        mInflater = LayoutInflater.from(context);
        mInflater.inflate(R.layout.expandable_view, this, true);

        mIsExpanded = false;
        mIsHeightSet = false;
        mContext = context;

        TypedArray a = mContext.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.ExpandableView,
                0, 0);

        try {
            mTopView = mInflater.inflate(a.getResourceId(R.styleable.ExpandableView_topLayout, R.layout.stub_hero_details), null, false);
            mBottomView = mInflater.inflate(a.getResourceId(R.styleable.ExpandableView_bottomLayout, R.layout.stub_hero_details), null, false);
        } finally {
            a.recycle();
        }

        addView(mTopView);
        addView(mBottomView);

        mBottomView.setVisibility(GONE);
        setOnClickListener(this /** OnClickListener **/);
    }

    private void toggleView() {
        mIsExpanded = !mIsExpanded;
    }

    @Override
    public void onClick(View view) {
        if (!mIsHeightSet) {
            mBottomViewHeight = measureBottomView();
            mIsHeightSet = true;
        }
        animate(mBottomView, !mIsExpanded);

        this.toggleView();
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

    private void animate(final View view, final boolean isExpand) {
        if (isExpand) {
            view.setVisibility(VISIBLE);
        }

        Animation a = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                if (Float.compare(interpolatedTime, 1.0f) == 0) {
                    if (isExpand) {
                        view.getLayoutParams().height = LayoutParams.WRAP_CONTENT;
                    } else {
                        view.setVisibility(GONE);
                    }
                } else {
                    view.getLayoutParams().height = (isExpand) ?
                            (int) (mBottomViewHeight * interpolatedTime) :
                            (int) (mBottomViewHeight - (mBottomViewHeight * interpolatedTime));
                    view.requestLayout();
                }
            }

            @Override
            public void initialize(int width, int height, int parentWidth,
                                   int parentHeight) {
                super.initialize(width, height, parentWidth, parentHeight);
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        a.setDuration(((int)(mBottomViewHeight / view.getContext().getResources().getDisplayMetrics().density)) * 4 );
        view.startAnimation(a);
    }
}