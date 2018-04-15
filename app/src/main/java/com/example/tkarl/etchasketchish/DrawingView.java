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
   public static int paintColor;
   public static int brushSize;
    Context mainContext;
    View mainView;
    public DrawingView(Context context,AttributeSet attrs) {
        super(context,attrs);

        paintColor = Color.BLUE;
        brushSize = 50;
      mainContext = this.getContext();
        setUpDrawing();
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
        //find center to start.
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

        drawPaint.setColor(paintColor);

        drawPaint.setAntiAlias(true);
        drawPaint.setStrokeWidth(brushSize);
        drawPaint.setStyle(Paint.Style.STROKE);
        drawPaint.setStrokeJoin(Paint.Join.ROUND);
        drawPaint.setStrokeCap(Paint.Cap.ROUND);
    }

    public boolean onSetAction(int Direction){

        switch(Direction){

            case 1:
                //Up
                CurrentPosY = CurrentPosY - (brushSize/6);
                drawingPath.moveTo(CurrentPosX,CurrentPosY);
                //set to how far you want each stroke to go.
               drawingPath.lineTo(CurrentPosX,CurrentPosY );
                drawingCanvas.drawPath(drawingPath,drawPaint);
                drawingPath.reset();
                break;
            case 2:
                //down
                CurrentPosY = CurrentPosY + (brushSize/6);
                drawingPath.moveTo(CurrentPosX,CurrentPosY);
                drawingPath.lineTo(CurrentPosX,CurrentPosY );
                drawingCanvas.drawPath(drawingPath,drawPaint);
                drawingPath.reset();
                break;
            case 3:
                //left


                CurrentPosX = CurrentPosX -(brushSize/6);
                drawingPath.moveTo(CurrentPosX,CurrentPosY);
                drawingPath.lineTo(CurrentPosX,CurrentPosY );
                drawingCanvas.drawPath(drawingPath,drawPaint);
                drawingPath.reset();
                break;
            case 4:
                //right
                CurrentPosX = CurrentPosX +(brushSize/6);
                drawingPath.moveTo(CurrentPosX,CurrentPosY);
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
