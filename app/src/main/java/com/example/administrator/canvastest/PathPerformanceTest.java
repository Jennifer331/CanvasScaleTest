package com.example.administrator.canvastest;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Stack;

/**
 * Created by Lei Xiaoyue on 2015-12-25.
 */
public class PathPerformanceTest extends Activity {
    private static final String TAG = "PathPerformanceTest";
    private Stack<Path> mPaths;
    private Bitmap mBitmap, mBitmap2;
    private Paint mPaint;
    private int width = 3120;
    private int height = 3120;
    private TextView mTextView1,mTextView2;
    private Button mBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.boring_layout);
        mPaths = new Stack<>();
        mBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        mBitmap2 = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        mTextView1 = (TextView)findViewById(R.id.text1);
        mTextView2 = (TextView)findViewById(R.id.text2);
        mBtn = (Button)findViewById(R.id.button);
        mBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(PathPerformanceTest.this,"start",Toast.LENGTH_SHORT).show();
                mPaint = new Paint();
                for (int i = 0; i < 100_000; i++) {
                    Path path = new Path();
//                    if (i % 5 == 0) {
//                        path.addRect(0, 0, (float) Math.random() * width, (float) Math.random() * height, Path.Direction.CW);
//                    } else {
//                        path.addCircle((float) Math.random() * width, (float) Math.random() * height, 10, Path.Direction.CW);
//                    }
                    path.lineTo((float) Math.random() * width, (float) Math.random() * height);
                    mPaths.add(path);
                }
                Canvas canvas = new Canvas(mBitmap);
                long startTime = System.currentTimeMillis();
                for (Path path : mPaths) {
                    canvas.drawPath(path, mPaint);
                }
                mTextView1.setText((System.currentTimeMillis() - startTime) + "");
                Log.v(TAG, "[onCreate]0.1million paths drawn:" + (System.currentTimeMillis() - startTime));

                Path path = new Path();
                for (int i = 0; i < 10_000; i++) {
                //100_000条数据会卡主
                    path.lineTo((float) Math.random() * width, (float) Math.random() * height);
                }
                Log.v(TAG, "finish create data");
                Canvas canvas2 = new Canvas(mBitmap2);
                startTime = System.currentTimeMillis();
                canvas2.drawPath(path, mPaint);
                mTextView2.setText((System.currentTimeMillis() - startTime) + "");
                Log.v(TAG, "[onCreate]a very long path drawn:" + (System.currentTimeMillis() - startTime));
            }
        });

    }
}
