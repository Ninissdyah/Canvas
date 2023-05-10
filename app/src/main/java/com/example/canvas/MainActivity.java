package com.example.canvas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private Canvas mCanvas;
    private Paint mPaint = new Paint();
    private Paint mPaintText = new Paint(Paint.UNDERLINE_TEXT_FLAG);
    private Bitmap mBitmap;
    private ImageView mImageView;

    //untuk yang ditempel
    private Rect mRect = new Rect();
    private Rect mRectBounds = new Rect();

    //constraint batasan gambarnya dmn, color apa, dan sampe kapan
    private static final int OFFSET = 120;
    private int mOffSet = OFFSET;
    private static final int MULTIPLIER = 100;

    private int mColorBackground;
    private int mColorRectangle;
    private int mColorCircle;
    private int mColorText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mColorBackground = ResourcesCompat.getColor(getResources(), R.color.colorBackground, null);
        mColorRectangle = ResourcesCompat.getColor(getResources(), R.color.colorRectangle, null);
        mColorCircle = ResourcesCompat.getColor(getResources(), R.color.colorAccent, null);
        mColorText = ResourcesCompat.getColor(getResources(), R.color.black, null);

        mPaint.setColor(mColorBackground);
        mPaintText.setColor(mColorText);
        mPaintText.setTextSize(70);

        mImageView = findViewById(R.id.my_image_view);
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawSomething(view);
            }
        });

    }

    public void drawSomething(View view) {
        int vWidth = view.getWidth();
        int vHeight = view.getHeight();

        int halfWidth = vWidth/2;
        int halfHeight = vHeight/2;

        //masang canvas cuma sekali karna nanti offset nya bakal nambah" trus
        if (mOffSet == OFFSET) {
            mBitmap = Bitmap.createBitmap(vWidth,vHeight,Bitmap.Config.ARGB_8888);
            mImageView.setImageBitmap(mBitmap);
            mCanvas = new Canvas(mBitmap);
            mCanvas.drawColor(mColorBackground);

            mCanvas.drawText(getString(R.string.keep_tapping),100,100,mPaintText);
            mOffSet += OFFSET;
        } else {
            if (mOffSet < halfWidth && mOffSet < halfHeight) {
            mPaint.setColor(mColorRectangle - MULTIPLIER * mOffSet);
            mRect.set(mOffSet, mOffSet, vWidth - mOffSet, vHeight - mOffSet);
            mCanvas.drawRect(mRect, mPaint);
            //untuk ukuran kotaknya dan warnanya
            mOffSet += OFFSET;
            }
            else {
                //menggambar circle biar di tengah
                mPaint.setColor(mColorCircle - MULTIPLIER*mOffSet);
                mCanvas.drawCircle(halfWidth,halfHeight,halfWidth/3, mPaint);
                String text = getString(R.string.done);

                //menambah teks dan biar pas tengah
                mPaintText.getTextBounds(text, 0, text.length(),mRectBounds);
                int x = halfWidth - mRectBounds.centerX();
                int y = halfHeight - mRectBounds.centerY();
                mCanvas.drawText(text, x, y, mPaintText);

                mOffSet += OFFSET;

                //menggambar segitiga
                mPaint.setColor(mColorBackground - MULTIPLIER*mOffSet);
                Point a = new Point(halfWidth-50, halfHeight-50);
                Point b = new Point(halfWidth+50, halfHeight-50);
                Point c = new Point(halfWidth, halfHeight+250);
                Path path = new Path();
                path.setFillType(Path.FillType.EVEN_ODD);
                //4sisi

                path.lineTo(a.x,a.y);
                path.lineTo(b.x,b.y);
                path.lineTo(c.x,c.y);
                path.lineTo(a.x,a.y);

                mCanvas.drawPath(path, mPaint);
                mOffSet += OFFSET;
            }
        }

        view.invalidate();
    }

}