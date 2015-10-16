package br.com.gilson.tlcpb.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import br.com.gilson.tlcpb.R;

/**
 * Created by gilson.maciel on 13/10/2015.
 */
public class TwoLevelCircularProgressBar extends View {
    private static final int BACKGROUND_COLOR = Color.LTGRAY;
    private static float STROKE_WIDTH = 20.0f;
    private RectF mCircleBounds = new RectF();
    private RectF mCircleProgressBounds = new RectF();

    private int mLayoutHeight = 0;
    private int mLayoutWidth = 0;
    private float mStrokeWidth = STROKE_WIDTH;
    private Paint mPaintBackground = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint mPaintProgress = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint mPaintProgress2 = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint mPaintText = new Paint(Paint.ANTI_ALIAS_FLAG);
    private int mBackgroundColor;
    private int mProgressColor;
    private int mProgressColor2;
    private Bitmap mBmpIcon;
    private int mProgressValue = 0;
    private int mProgress2Value = 0;
    private float mFontSize = 14;
    private String mText;

    public TwoLevelCircularProgressBar(Context context) {
        super(context);
    }

    public TwoLevelCircularProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public TwoLevelCircularProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public TwoLevelCircularProgressBar(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs, defStyleAttr);
    }

    public void init(AttributeSet attrs, int style) {
        TypedArray ta = getContext().obtainStyledAttributes(attrs,
                R.styleable.RoundProgressBar, style, 0);

        mStrokeWidth = ta.getDimension(R.styleable.RoundProgressBar_tlcp_strokeWidth,
                STROKE_WIDTH);
        mBackgroundColor = ta.getColor(R.styleable.RoundProgressBar_tlcp_bg_color,
                BACKGROUND_COLOR);
        mProgressColor = ta.getColor(R.styleable.RoundProgressBar_tlcp_progress_color,
                Color.MAGENTA);
        mProgressColor2 = ta.getColor(R.styleable.RoundProgressBar_tlcp_progress2_color,
                BACKGROUND_COLOR);
        int mIcon = ta.getResourceId(R.styleable.RoundProgressBar_tlcp_drawable, -1);
        mBmpIcon = BitmapFactory.decodeResource(getResources(),
                mIcon);
        mProgressValue = getProgressValue(ta.getInteger(R.styleable.RoundProgressBar_tlcp_progress,
                mProgressValue));
        mProgress2Value = getProgressValue(ta.getInteger(R.styleable.RoundProgressBar_tlcp_progress2,
                mProgress2Value));

        mFontSize = ta.getDimension(R.styleable.RoundProgressBar_tlcp_textSize,
                mFontSize);

        int textResource = ta
                .getResourceId(R.styleable.RoundProgressBar_tlcp_text, -1);
        if (textResource != -1) {
            mText = getResources().getString(textResource);
        }

        setupPaints();
        ta.recycle();
    }

    private void setupPaints() {
        Resources r = getResources();
        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, mFontSize,
                r.getDisplayMetrics());

        STROKE_WIDTH = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                mStrokeWidth,
                r.getDisplayMetrics());

        mPaintBackground.setColor(mBackgroundColor);
        mPaintProgress.setColor(mProgressColor);
        mPaintProgress2.setColor(mProgressColor2);
        mPaintText.setColor(Color.GRAY);
        mPaintText.setTextSize(px);

        setupDefaultPaint(mPaintBackground);
        setupDefaultPaint(mPaintProgress);
        setupDefaultPaint(mPaintProgress2);
    }

    private void setupDefaultPaint(Paint paint) {
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(mStrokeWidth);
    }

    @Override
    protected void onSizeChanged(int newWidth, int newHeight, int oldWidth, int oldHeight) {
        super.onSizeChanged(newWidth, newHeight, oldWidth, oldHeight);
        mLayoutWidth = newWidth;
        mLayoutHeight = newHeight;
        setupBounds();
    }

    private void setupBounds() {
        int minValue = Math.min(mLayoutWidth, mLayoutHeight);

        int xOffset = mLayoutWidth - minValue;
        int yOffset = mLayoutHeight - minValue;

        int paddingTop = this.getPaddingTop() + (yOffset / 2);
        int paddingBottom = this.getPaddingBottom() + (yOffset / 2);
        int paddingLeft = this.getPaddingLeft() + (xOffset / 2);
        int paddingRight = this.getPaddingRight() + (xOffset / 2);

        int width = getWidth();
        int height = getHeight();

        mCircleBounds = new RectF(
                paddingLeft + STROKE_WIDTH,
                paddingTop + STROKE_WIDTH,
                width - paddingRight - STROKE_WIDTH,
                height - paddingBottom - STROKE_WIDTH);

        mCircleProgressBounds = new RectF(
                paddingLeft + STROKE_WIDTH,
                paddingTop + STROKE_WIDTH,
                width - paddingRight - STROKE_WIDTH,
                height - paddingBottom - STROKE_WIDTH);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawArc(mCircleBounds, 0, 360, false, mPaintBackground);
        canvas.drawArc(mCircleBounds, 270, mProgress2Value, false, mPaintProgress2);
        canvas.drawArc(mCircleProgressBounds, 270, mProgressValue, false, mPaintProgress);


        if (mBmpIcon != null) {
            float wH = mCircleBounds.width() / 2;
            canvas.drawBitmap(mBmpIcon,
                    mCircleBounds.left + wH - mBmpIcon.getWidth() / 2,
                    mCircleBounds.top + wH - mBmpIcon.getHeight() / 2,
                    mPaintBackground);
        }

        if (!TextUtils.isEmpty(this.mText)) {
            this.drawMultilineText(canvas);
        }
    }

    private void drawMultilineText(Canvas canvas) {
        String[] textSplit = mText.split("\n");
        float wH = mCircleBounds.width() / 2;

        for (int i = 0; i < textSplit.length; i++) {
            String txt = textSplit[i];
            float wT = mPaintText.measureText(txt);

            float pX = mCircleBounds.left + wH - wT / 2;
            float pY = mCircleBounds.top + mPaintText.getFontSpacing() + 20
                    + mPaintText.getFontSpacing() * i;

            canvas.drawText(txt, pX, pY, mPaintText);
        }
    }

    @Override
    protected void onMeasure(final int widthMeasureSpec, final int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        final int height = getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec);
        final int width = getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec);
        final int min = Math.min(width, height);
        setMeasuredDimension((int) (min + 2 * STROKE_WIDTH), (int) (min + 2 * STROKE_WIDTH));

        mCircleBounds.set(STROKE_WIDTH, STROKE_WIDTH, min + STROKE_WIDTH, min + STROKE_WIDTH);
        mCircleProgressBounds.set(STROKE_WIDTH, STROKE_WIDTH, min + STROKE_WIDTH, min + STROKE_WIDTH);
    }

    public void setProgressValue(int mProgressValue) {
        validateProgressValue(mProgressValue);
        this.mProgressValue = getProgressValue(mProgressValue);
        postInvalidate();
    }

    public void setProgressValue2(int mProgressValue) {
        validateProgressValue(mProgressValue);
        this.mProgress2Value = getProgressValue(mProgressValue);
        postInvalidate();
    }

    private void validateProgressValue(int mProgressValue) {
        if (mProgressValue < 0 || mProgressValue > 100) {
            throw new IllegalArgumentException("Value must be between 0 and 100");
        }
    }


    private int getProgressValue(int mProgressValue) {
        return 360 * mProgressValue / 100;
    }

    public void setText(String text) {
        this.mText = text;
        postInvalidate();
    }
}
