package com.example.canvas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class Assignment extends AppCompatActivity {

    private Canvas mCanvas;
    private Paint mPaint = new Paint();
    private Bitmap mBitmap;
    private ImageView imageView;

    private int frameCount = 0;

    private int colorBlack;
    private int colorWhite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignment);

        //color
        colorBlack = ResourcesCompat.getColor(getResources(),R.color.black, null);
        colorWhite = ResourcesCompat.getColor(getResources(),R.color.white, null);

        imageView = findViewById(R.id.my_image_view);
        imageView.setOnClickListener(view -> {drawSomething(view);});

    }

    private void drawSomething(View view) {
        int width = view.getWidth();
        int halfWidth = width/2;

        int height = view.getHeight();
        int halfHeight = height/2;

        //koordinat
        Point face1 = new Point(halfWidth-240, halfHeight-180);
        Point face2 = new Point(halfWidth+240, halfHeight-180);
        Point face3 = new Point(halfWidth, halfHeight+320);

        Point leftEar = new Point(face1.x+170, face1.y-400);
        Point rightEar = new Point(face2.x-170, face2.y-400);

        switch (frameCount) {
            case 0:
            //init canvas
                mBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
                imageView.setImageBitmap(mBitmap);
                mCanvas = new Canvas(mBitmap);

                //draw ear
                mPaint.setColor(colorBlack);
                mCanvas.save();
                mCanvas.drawOval(new RectF(leftEar.x-300, leftEar.y-100,
                        leftEar.x+20, leftEar.y+320), mPaint);
                mCanvas.restore();

                mCanvas.save();
                mCanvas.drawOval(new RectF(rightEar.x-20, rightEar.y-100, rightEar.x+300, rightEar.y+320), mPaint);
//                canvas.rotate(-30, 200, 200);
                mCanvas.restore();
                break;

            case 1:
                //draw tubuh
                mPaint.setColor(colorBlack);
                mCanvas.save(); // Menyimpan kondisi sebelum melakukan transformasi
                mCanvas.rotate(90, 200, 200); // Memutar canvas sebesar 90 derajat pada titik (200, 200)
                mCanvas.scale(2, 1); // Mengubah skala pada sumbu X
                //ingat ini di balik 90 derajat
                mCanvas.drawOval(200, -600, 700, 320, mPaint);
                mCanvas.restore(); // Mengembalikan kondisi sebelum transformasi
                break;

            case 2:
                // draw eye
                Point leftEye = new Point(face1.x+100, face1.y-70);
                Point rightEye = new Point(face2.x-100, face2.y-70);

                mPaint.setColor(colorWhite);
                mCanvas.drawCircle(leftEye.x, leftEye.y, 80, mPaint);
                mCanvas.drawCircle(rightEye.x, rightEye.y, 80, mPaint);

                // draw eye ball
                mPaint.setColor(colorBlack);
                mCanvas.drawOval(new RectF(leftEye.x-40, leftEye.y-40, leftEye.x+40, leftEye.y+42), mPaint);
                mCanvas.drawOval(new RectF(rightEye.x-40, rightEye.y-40, rightEye.x+40, rightEye.y+42), mPaint);

                mPaint.setColor(colorWhite);

                mCanvas.drawCircle(leftEye.x-2, leftEye.y-10, 20, mPaint);
                mCanvas.drawCircle(rightEye.x+2, rightEye.y-10, 20, mPaint);
                break;
            case 3:
                //draw mulut
                mPaint.setColor(colorWhite);
                mCanvas.drawCircle(face3.x, face3.y-100, 340, mPaint);
                break;
            case 4:
                //draw hidung
                mPaint.setColor(colorBlack);
                mCanvas.drawCircle(halfWidth, halfHeight+50, 140, mPaint);
                break;
            case 5:
                getWindow().getDecorView().setBackgroundColor(colorWhite);
                break;
            default:
                break;
        }

        frameCount++;
        view.invalidate();
    }
}