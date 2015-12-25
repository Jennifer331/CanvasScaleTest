package com.example.administrator.canvastest;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import java.util.Stack;

/**
 * Created by Lei Xiaoyue on 2015-12-24.
 */
public class DrawingView extends View implements GestureDetector.OnGestureListener{
    private static final String TAG = "DrawingView";
    private static final int STROKE_WIDTH = 10;
    private static final int STROKE_COLOR = Color.GREEN;
    private GestureDetector mDetector;
    private Paint mPaint;
    private Stack<Path> mPaths;
    private Path mCurrentPath;

    public DrawingView(Context context) {
        this(context, null);
    }

    public DrawingView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DrawingView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public DrawingView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context) {
        mDetector = new GestureDetector(context,this);
        mPaint = new Paint();
        mPaint.setStrokeWidth(STROKE_WIDTH);
        mPaint.setColor(STROKE_COLOR);
        mPaths = new Stack<Path>();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        for (Path path : mPaths) {
            canvas.drawPath(path,mPaint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean result = mDetector.onTouchEvent(event);
        invalidate();
        return result;
    }

    @Override
    public boolean onDown(MotionEvent e) {
        mCurrentPath = new Path();
        mCurrentPath.moveTo(e.getX(),e.getY());
        mPaths.add(mCurrentPath);
        return true;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        mCurrentPath = mPaths.peek();
        mCurrentPath.addCircle(e.getX(),e.getY(),STROKE_WIDTH/2, Path.Direction.CW);
        return true;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        mCurrentPath = mPaths.peek();
        mCurrentPath.quadTo(e1.getX(), e1.getY(), e2.getX(), e2.getY());
        return true;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }

    public Bitmap getBitmap(){
        Bitmap bitmap = Bitmap.createBitmap(getWidth(),getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
//        canvas.drawColor(Color.WHITE);
        draw(canvas);
        return bitmap;
    }

    public Stack<Path> getPaths(){
        return mPaths;
    }

    public Paint getPaint() {
        return mPaint;
    }
}
