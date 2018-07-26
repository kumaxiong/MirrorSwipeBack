package gifshow.yxcorp.com.kwaibutaswipeback.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewParent;
import android.widget.LinearLayout;

public class SwipeBackFrameLayout extends LinearLayout {

  private static final String TAG = "SwipeBack@xiong";
  private float mStartX;
  private float mStartY;
  private float mCurrentY;
  private float mCurrentX;
  private boolean mHasJudged = false;
  private float progress;
  private Paint mPaint;
  private Paint mPaintWhite;
  private Path mPath;
  private int mHalfScreenWidth;
  private boolean mDrawBack = false;
  private boolean mIsAccept = false;
  private boolean mIsLeftStart = false;
  private boolean mIsRightStart = false;
  private OnSwipeBackListener mSwipeBackListener;
  private boolean mIsRightSwipeEnable = true;
  private boolean mIsLeftSwipeEnable = true;
  private final float mSwipeMaximum = 0.6665f;


  public SwipeBackFrameLayout(@NonNull Context context) {
    this(context, null);
  }

  public SwipeBackFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public SwipeBackFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    initDrawTool();
  }

  private void initDrawTool() {
    mPaint = new Paint();
    mPaint.setColor(Color.BLACK);
    mPaint.setStrokeWidth(8);
    mPaint.setAntiAlias(true);
    mPaint.setStyle(Paint.Style.FILL);
    mPaintWhite = new Paint();
    mPaintWhite.setColor(Color.WHITE);
    mPaintWhite.setStrokeWidth(4);
    mPaintWhite.setAntiAlias(true);
    mPaintWhite.setStrokeCap(Paint.Cap.ROUND);
    mPath = new Path();
  }



  @Override
  public boolean onInterceptTouchEvent(MotionEvent ev) {
    return mHasJudged || super.onInterceptTouchEvent(ev);
  }


  @Override
  public boolean dispatchTouchEvent(MotionEvent ev) {
    switch (ev.getActionMasked()) {
      case MotionEvent.ACTION_DOWN: {
        disallowParentsInterceptTouchEvent(getParent());
        mHasJudged = false;
        mStartX = ev.getX();
        mStartY = ev.getY();
        mIsLeftStart = isLeftStart(mStartX);
        mIsRightStart = isRightStart(mStartX);
        break;
      }
      case MotionEvent.ACTION_MOVE: {
        if (!mIsLeftStart && !mIsRightStart) {
          break;
        }
        mCurrentX = ev.getX();
        mCurrentY = ev.getY();
        if (!mHasJudged) {
          float distanceX = Math.abs(mCurrentX - mStartX);
          if (distanceX > 30) {
            allowParentsInterceptTouchEvent(getParent());
            mHasJudged = true;
          }
        }
        // 大于30开始画图
        if (mHasJudged) {
          postInvalidateDelayed(0);
        }
        break;
      }
      case MotionEvent.ACTION_UP:
        break;
    }
    return super.dispatchTouchEvent(ev);
  }

  @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
  @Override
  public boolean onTouchEvent(MotionEvent event) {
    switch (event.getAction()) {
      case MotionEvent.ACTION_UP: {
        progress = calculateProgress();
        if (progress > 0.9 * 0.665 && (mIsLeftStart || mIsRightStart)) {
          Log.d(TAG, "onTouchEvent: action up");
          mIsAccept = true;
          mSwipeBackListener.completeSwipeBack();
        }
        mDrawBack = true;
        postInvalidateDelayed(0);
        break;
      }
    }
    return true;
  }


  private boolean isLeftStart(float startX) {
    return mIsLeftSwipeEnable && startX < 50;
  }

  private boolean isRightStart(float startX) {
    return mIsRightSwipeEnable && startX > getWidth() - 50;
  }

  private void disallowParentsInterceptTouchEvent(ViewParent parent) {
    if (parent == null) {
      return;
    }
    parent.requestDisallowInterceptTouchEvent(true);
    disallowParentsInterceptTouchEvent(parent.getParent());
  }

  private void allowParentsInterceptTouchEvent(ViewParent parent) {
    if (parent == null) {
      return;
    }
    parent.requestDisallowInterceptTouchEvent(false);
    allowParentsInterceptTouchEvent(parent.getParent());
  }


  @Override
  protected void dispatchDraw(Canvas canvas) {
    super.dispatchDraw(canvas);
    drawSinLine(canvas);
  }

  @Override
  protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    mHalfScreenWidth = getMeasuredWidth() / 2;
  }

  private void drawSinLine(Canvas canvas) {
    if (!mIsRightStart && !mIsLeftStart) {
      resetAll();
      return;
    }
    if (!mDrawBack) {
      progress = calculateProgress();
    } else if (mIsAccept) {
      resetAll();
    } else {
      progress -= 0.1;
      if (progress < 0) {
        resetAll();
      }
    }
    float amplitude = 30 * progress * 1.5f;
    float height = 60 * progress * 1.5f;
    float width = getHeight() / 5;
    float index = 0;
    float valueSineStart = 0;
    float sineLineStartY = mStartY - 1.9f / 3f * width;
    float mTheta = 30;
    if (mIsRightStart) {
      valueSineStart += getWidth();
    }
    mPath.reset();
    mPath.moveTo(valueSineStart, sineLineStartY);
    float valueSine;
    while (index <= width * 4 / 3) {
       valueSine = (float) (Math.sin((float) index / width * 1.5f * Math.PI + mTheta)
          * amplitude + height - amplitude);
      if (mIsRightStart) {
        valueSine *= -1;
        valueSine += getWidth();
      }
      mPath.lineTo(valueSine, sineLineStartY + index);
      index++;
    }
    mPath.lineTo(valueSineStart, sineLineStartY);
    mPath.close();
    mPaint.setAlpha((int) (190 * progress * 1.5f));
    canvas.drawPath(mPath, mPaint);

    float midBackX = amplitude * 1.25f;
    float midBackY = mStartY;

    mPaintWhite.setAlpha((int) (255 * progress * 1.5f));
    float lineLength = 15 * progress * 1.5f;

    if (mIsRightStart) {
      midBackX *= -1;
      midBackX += getWidth() + lineLength;
      canvas.drawLine(midBackX - lineLength, midBackY, midBackX, midBackY - lineLength, mPaintWhite);
      canvas.drawLine(midBackX - lineLength, midBackY, midBackX, midBackY + lineLength, mPaintWhite);
    } else {
      canvas.drawLine(midBackX - lineLength, midBackY, midBackX, midBackY + lineLength, mPaintWhite);
      canvas.drawLine(midBackX - lineLength, midBackY, midBackX, midBackY - lineLength, mPaintWhite);
    }

    // 慢回弹
    if (mDrawBack) {
      postInvalidateDelayed(0);
    }
  }

  private void resetAll() {
    mCurrentY = 0;
    mCurrentX = 0;
    mHasJudged = false;
    mStartY = 0;
    mStartX = 0;
    mDrawBack = false;
    progress = 0;
  }

  private float calculateProgress() {
    float distance = Math.abs(mCurrentX - mStartX);
    if (distance > mHalfScreenWidth) {
      return mSwipeMaximum;
    }
    float temp = distance / mHalfScreenWidth;
    if (temp > mSwipeMaximum) {
      return mSwipeMaximum;
    }
    return temp;
  }

  public void setRightSwipeEnable(boolean enable) {
    this.mIsRightSwipeEnable = enable;
  }

  public void setLeftSwipeEnable(boolean enable) {
    this.mIsLeftSwipeEnable = enable;
  }

  public void setSwipeBackListener(OnSwipeBackListener onSwipeBackListener) {
    this.mSwipeBackListener = onSwipeBackListener;
  }

  public interface OnSwipeBackListener {
    void completeSwipeBack();
  }
}
