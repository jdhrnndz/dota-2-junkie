package com.stratpoint.jdhrnndz.dota2junkie.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import com.stratpoint.jdhrnndz.dota2junkie.R;

/**
 * Author: John Denielle F. Hernandez
 * Date: 8/23/16
 * Description: Custom view for presenting the barracks and tower states in a match.
 */
public class MinimapView extends View{
    private static final int STRUCTURE_COUNT = 18;

    public static final PointF[] DIRE_STRUCTURE_COORDINATES = new PointF[]{
            new PointF(0.8219186437f, 0.2128777924f), // Ancient Bot
            new PointF(0.8024028786f, 0.1897503285f), // Ancient Top
            new PointF(0.8826614625f, 0.3080157687f), // Bot Tier 3
            new PointF(0.8863206684f, 0.4788436268f), // Bot Tier 2
            new PointF(0.8824175154f, 0.6134034166f), // Bot Tier 1
            new PointF(0.7614197719f, 0.2601839685f), // Mid Tier 3
            new PointF(0.6560346405f, 0.3653088042f), // Mid Tier 2
            new PointF(0.5662621211f, 0.4830486202f), // Mid Tier 1
            new PointF(0.7136061475f, 0.1245729304f), // Top Tier 3
            new PointF(0.4999085199f, 0.108804205f), // Top Tier 2
            new PointF(0.2149783497f, 0.108804205f), // Top Tier 1
            new PointF(0.8662560224f, 0.2850197109f), // Bottom Ranged
            new PointF(0.8983960481f, 0.2839684625f), // Bottom Melee
            new PointF(0.7616027322f, 0.232325887f), // Middle Ranged
            new PointF(0.7835579679f, 0.2555847569f), // Middle Melee
            new PointF(0.7344026346f, 0.1069645204f), // Top Ranged
            new PointF(0.7346465817f, 0.1416557162f), // Top Melee
            new PointF(0.8371653351f, 0.1755584757f) // Ancient
    };

    public static final PointF[] RADIANT_STRUCTURE_COORDINATES = new PointF[]{
            new PointF(0.1710678783f, 0.8436268068f), // Ancient Bot
            new PointF(0.1535036897f, 0.8247043364f), // Ancient Top
            new PointF(0.2637067756f, 0.9056504599f), // Bot Tier 3
            new PointF(0.465755931f, 0.9045992116f), // Bot Tier 2
            new PointF(0.8043544551f, 0.9035479632f), // Bot Tier 1
            new PointF(0.2227846557f, 0.7731931669f), // Mid Tier 3
            new PointF(0.2857229981f, 0.6864651774f), // Mid Tier 2
            new PointF(0.408184424f, 0.5944809461f), // Mid Tier 1
            new PointF(0.09593218272f, 0.7227332457f), // Top Tier 3
            new PointF(0.1291089834f, 0.558738502f), // Top Tier 2
            new PointF(0.1281331951f, 0.3831800263f), // Top Tier 1
            new PointF(0.2420564737f, 0.8888961892f), // Bottom Ranged
            new PointF(0.2420564737f, 0.9225361367f), // Bottom Melee
            new PointF(0.1923522596f, 0.7785151117f), // Middle Ranged
            new PointF(0.2141855217f, 0.8018396846f), // Middle Melee
            new PointF(0.08080746478f, 0.7480946124f), // Top Ranged
            new PointF(0.1116667683f, 0.7480946124f), // Top Melee
            new PointF(0.1407574556f, 0.8572273325f) // Ancient
    };

    private int mDireTowers, mRadiantTowers, mDireBarracks, mRadiantBarracks;
    private boolean mRadiantWin;
    private int mViewWidth, mViewHeight, mMinimapWidth, mMinimapHeight;
    private int mBitmapWidth, mBitmapHeight;
    private float mTowerSize, mAncientSize;
    private Paint mDireStructPaint, mRadiantStructPaint, mDireBorderPaint, mRadiantBorderPaint, mDeadPaint, mOutlinePaint;
    private String mDireStructureStatus, mRadiantStructureStatus;
    Bitmap mMinimap;
    Rect mSrcRect, mDestRect;

    public MinimapView(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.MinimapView,
                0, 0);

        try {
            mDireTowers = a.getInt(R.styleable.MinimapView_direTowers, 0);
            mRadiantTowers = a.getInt(R.styleable.MinimapView_radiantTowers, 0);

            mDireBarracks = a.getInt(R.styleable.MinimapView_direBarracks, 0);
            mRadiantBarracks = a.getInt(R.styleable.MinimapView_radiantBarracks, 0);

            mRadiantWin = a.getBoolean(R.styleable.MinimapView_radiantWin, false);

            buildStructuresStatus();
        } finally {
            a.recycle();
        }

        init();
    }

    private void init() {
        mMinimap = BitmapFactory.decodeResource(getResources(), R.drawable.minimap);
        mBitmapWidth = mMinimap.getWidth();
        mBitmapHeight = mMinimap.getHeight();
        mSrcRect = new Rect(0, 0, mBitmapWidth, mBitmapHeight);

        mDireStructPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mDireStructPaint.setColor(Color.parseColor("#ff0000"));
        mDireStructPaint.setStyle(Paint.Style.FILL);
        mDireBorderPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mDireBorderPaint.setColor(Color.parseColor("#800000"));
        mDireBorderPaint.setStyle(Paint.Style.STROKE);

        mRadiantStructPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mRadiantStructPaint.setColor(Color.parseColor("#00ff00"));
        mRadiantStructPaint.setStyle(Paint.Style.FILL);
        mRadiantBorderPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mRadiantBorderPaint.setColor(Color.parseColor("#008000"));
        mRadiantBorderPaint.setStyle(Paint.Style.STROKE);

        mDeadPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mDeadPaint.setColor(Color.BLACK);
        mDeadPaint.setStyle(Paint.Style.FILL);

        mOutlinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mOutlinePaint.setColor(Color.WHITE);
        mOutlinePaint.setStyle(Paint.Style.STROKE);
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Paint the whole canvas black
        canvas.drawColor(Color.BLACK);
        // Draw the minimap
        canvas.drawBitmap(mMinimap, mSrcRect, mDestRect, null);
        // Draw the structures
        for (int i = 0; i < STRUCTURE_COUNT; i++) {
            float size = (i == STRUCTURE_COUNT-1)?mAncientSize:mTowerSize;

            // Dire Structures
            Paint paint = (mDireStructureStatus.charAt(i) == '1')?mDireStructPaint:mDeadPaint;
            PointF dire = DIRE_STRUCTURE_COORDINATES[i];
            canvas.drawCircle(dire.x*mMinimapWidth, dire.y*mMinimapHeight, size, paint);
            canvas.drawCircle(dire.x*mMinimapWidth, dire.y*mMinimapHeight, size, mOutlinePaint);

            // Radiant Structures
            paint = (mRadiantStructureStatus.charAt(i) == '1')?mRadiantStructPaint:mDeadPaint;
            PointF radiant = RADIANT_STRUCTURE_COORDINATES[i];
            canvas.drawCircle(radiant.x*mMinimapWidth, radiant.y*mMinimapHeight, size, paint);
            canvas.drawCircle(radiant.x*mMinimapWidth, radiant.y*mMinimapHeight, size, mOutlinePaint);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        setMeasuredDimension(widthMeasureSpec, widthMeasureSpec);
    }

    @Override
    protected void onSizeChanged(int xNew, int yNew, int xOld, int yOld){
        super.onSizeChanged(xNew, yNew, xOld, yOld);

        mViewWidth = xNew;
        mViewHeight = yNew;

        if (mViewHeight > mViewWidth) {
            mMinimapHeight = mViewWidth*mBitmapHeight/mBitmapWidth;
            mMinimapWidth = mViewWidth;
        } else {
            mMinimapHeight = mViewHeight;
            mMinimapWidth = mViewHeight*mBitmapWidth/mBitmapHeight;
        }

        mDestRect = new Rect(0, 0, mMinimapWidth, mMinimapHeight);
        mTowerSize = 0.01f*mMinimapWidth;
        mAncientSize = mTowerSize*2;
    }

    public void setValues(int direTowers, int direBarracks, int radiantTowers, int radiantBarracks, boolean radiantWin) {
        mDireTowers = direTowers;
        mDireBarracks = direBarracks;
        mRadiantTowers = radiantTowers;
        mRadiantBarracks = radiantBarracks;
        mRadiantWin = radiantWin;

        buildStructuresStatus();
    }

    private void buildStructuresStatus() {
        StringBuilder direStructureStatus = new StringBuilder();
        StringBuilder radiantStructureStatus = new StringBuilder();

        direStructureStatus.append(String.format("%16s", Integer.toBinaryString(mDireTowers)).substring(5));
        radiantStructureStatus.append(String.format("%16s", Integer.toBinaryString(mRadiantTowers)).substring(5));

        direStructureStatus.append(String.format("%8s", Integer.toBinaryString(mDireBarracks)).substring(2));
        radiantStructureStatus.append(String.format("%8s", Integer.toBinaryString(mRadiantBarracks)).substring(2));

        if(mRadiantWin) {
            radiantStructureStatus.append("1");
            direStructureStatus.append("0");
        } else {
            direStructureStatus.append("1");
            radiantStructureStatus.append("0");
        }

        mDireStructureStatus = direStructureStatus.toString();
        mRadiantStructureStatus = radiantStructureStatus.toString();
    }
}
