package com.example.administrator.canvastest;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.Stack;

/**
 * Created by Lei Xiaoyue on 2015-12-24.
 */
public class MainActivity extends Activity {
    private DrawingView mDrawingView;
    private Button mButton;
    private Bitmap mBitmap;
    private Bitmap mMediumBitmap;
    private Bitmap mLargeBitmap;
    private Stack<Path> mPaths;
    private Paint mPaint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
        mDrawingView = (DrawingView) findViewById(R.id.drawingView);
        mButton = (Button) findViewById(R.id.finish);
        mPaths = new Stack<Path>();
        mPaint = new Paint();

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBitmap = mDrawingView.getBitmap();
                mPaint = mDrawingView.getPaint();
                mPaths = mDrawingView.getPaths();

                int width = mBitmap.getWidth();
                int height = mBitmap.getHeight();
                mMediumBitmap = Bitmap.createBitmap(width * 2, height * 2, Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(mMediumBitmap);
                for(Path path : mPaths){
                    canvas.drawPath(path,mPaint);
                }

                mLargeBitmap = Bitmap.createBitmap(width * 4, height * 4, Bitmap.Config.ARGB_8888);
                canvas = new Canvas(mLargeBitmap);
                canvas.scale(4.0f,4.0f);
                for(Path path : mPaths){
                    canvas.drawPath(path,mPaint);
                }

                MediaStore.Images.Media.insertImage(getContentResolver(),mBitmap,System.currentTimeMillis() + ".jpg","");
                MediaStore.Images.Media.insertImage(getContentResolver(),mMediumBitmap,System.currentTimeMillis() + ".jpg","");
                MediaStore.Images.Media.insertImage(getContentResolver(),mLargeBitmap,System.currentTimeMillis() + ".jpg","");
                Toast.makeText(MainActivity.this,"保存成功",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
