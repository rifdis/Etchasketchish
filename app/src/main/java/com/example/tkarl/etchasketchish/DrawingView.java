package com.example.tkarl.etchasketchish;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by tkarl on 4/12/2018.
 */

public class DrawingView extends View implements View.OnTouchListener{
    private Path drawingPath;
    private Paint drawPaint;
    private Canvas drawingCanvas;
    private Bitmap bitmap;
    int CanvasWidth;
    int CanvasHeight;
    int CurrentPosX;
   int CurrentPosY;
    Context mainContext;
    View mainView;
    public DrawingView(Context context,AttributeSet attrs) {
        super(context,attrs);
        setUpDrawing();
      mainContext = this.getContext();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
    //instasiate the size of the DrawingView layout item in the layout. This grabs the values which can
        //be assigned to our canvas item
        super.onSizeChanged(w, h, oldw, oldh);
        CanvasHeight = h;
        CanvasWidth = w;
        bitmap = Bitmap.createBitmap(w,h,Bitmap.Config.ARGB_8888);
        drawingCanvas = new Canvas(bitmap);
        Toast.makeText(mainContext, Integer.toString(h), Toast.LENGTH_SHORT).show();
        CurrentPosY = Math.round(h/2);
        CurrentPosX = Math.round(w/2);
        this.setOnTouchListener(this);

    }
    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(bitmap, 0, 0,drawPaint );
        canvas.drawPath(drawingPath, drawPaint);
    }
    public void setUpDrawing(){
        drawingPath = new Path();
        drawPaint = new Paint();

        drawPaint.setColor(Color.BLUE);

        drawPaint.setAntiAlias(true);
        drawPaint.setStrokeWidth(50);
        drawPaint.setStyle(Paint.Style.STROKE);
        drawPaint.setStrokeJoin(Paint.Join.ROUND);
        drawPaint.setStrokeCap(Paint.Cap.SQUARE);
    }

    public boolean onSetAction(int Direction){

        switch(Direction){

            case 1:
                //Up

                drawingPath.moveTo(CurrentPosX,CurrentPosY);
                CurrentPosY = CurrentPosY - 20;
               drawingPath.lineTo(CurrentPosX,CurrentPosY );
                drawingCanvas.drawPath(drawingPath,drawPaint);
                drawingPath.reset();
                break;
            case 2:
                //down

                drawingPath.moveTo(CurrentPosX,CurrentPosY);
                CurrentPosY = CurrentPosY + 20;
                drawingPath.lineTo(CurrentPosX,CurrentPosY );
                drawingCanvas.drawPath(drawingPath,drawPaint);
                drawingPath.reset();
                break;
            case 3:
                //left

                drawingPath.moveTo(CurrentPosX,CurrentPosY);
                CurrentPosX = CurrentPosX -20;
                drawingPath.lineTo(CurrentPosX,CurrentPosY );
                drawingCanvas.drawPath(drawingPath,drawPaint);
                drawingPath.reset();
                break;
            case 4:
                //right

                drawingPath.moveTo(CurrentPosX,CurrentPosY);
                CurrentPosX = CurrentPosX +20;
                drawingPath.lineTo(CurrentPosX,CurrentPosY );
                drawingCanvas.drawPath(drawingPath,drawPaint);
                drawingPath.reset();
                break;
                default:
                    return false;

        }
        invalidate();
        return true;

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN){
            CurrentPosY = Math.round(event.getY());
            CurrentPosX = Math.round(event.getX());
            Toast.makeText(mainContext, Integer.toString(CurrentPosY), Toast.LENGTH_SHORT).show();

        }
        return false;
    }
}
