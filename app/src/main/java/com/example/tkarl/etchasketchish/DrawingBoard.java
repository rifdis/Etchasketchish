package com.example.tkarl.etchasketchish;

import android.content.Intent;
import android.content.IntentSender;
import android.content.res.XmlResourceParser;
import android.graphics.Canvas;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Xml;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;

public class DrawingBoard extends AppCompatActivity implements View.OnClickListener, SensorEventListener {
DrawingView drawView;
private static   LayoutInflater inflate;
private static  LinearLayout optionLayout;
    ImageButton upButton,downButton,rightButton,leftButton,colourbutton,brushbutton;
   boolean colormenuSelected;
    boolean brushmenuSelected;
    SensorManager sensorMgr;
    Sensor shakesense;
    private long lastupdate = 0;
    private float lasty;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawing_board);
        colormenuSelected = false;
        brushmenuSelected= false;
        drawView = (DrawingView)findViewById(R.id.canvas_board);
        upButton = (ImageButton)findViewById(R.id.arrow_up);
        upButton.setOnClickListener(this);
        downButton = (ImageButton)findViewById(R.id.arrow_down);
        downButton.setOnClickListener(this);
        leftButton = (ImageButton)findViewById(R.id.arrow_left);
        leftButton.setOnClickListener(this);
        rightButton = (ImageButton)findViewById(R.id.arrow_right);
        rightButton.setOnClickListener(this);
        colourbutton = (ImageButton)findViewById(R.id.Choose_Colour);
        colourbutton.setOnClickListener(this);
        brushbutton = (ImageButton)findViewById(R.id.Choose_Brush_Size);
        brushbutton.setOnClickListener(this);

        optionLayout = (LinearLayout)findViewById(R.id.option_layout);
        sensorMgr = (SensorManager) getSystemService(SENSOR_SERVICE);
        shakesense = sensorMgr.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorMgr.registerListener(this, shakesense , SensorManager.SENSOR_DELAY_NORMAL);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.arrow_up:
                drawView.onSetAction(1);
                break;
            case R.id.arrow_down:
                drawView.onSetAction(2);
                break;
            case R.id.arrow_left:
                drawView.onSetAction(3);
                break;
            case R.id.arrow_right:
                drawView.onSetAction(4);
                break;
            case R.id.Choose_Colour:
                if (!colormenuSelected) {
                    brushbutton.setEnabled(true);
                    brushmenuSelected=false;
                    optionLayout.removeAllViews();
                    Toast.makeText(this, "pressed", Toast.LENGTH_SHORT).show();
                    inflate = getLayoutInflater();
                    View colorView= inflate.inflate(R.layout.activity_choose__colour_, null);
                    optionLayout.addView(colorView);

                    colourbutton.setEnabled(false);
                    Button redButton = (Button)colorView.findViewById(R.id.colour_red);
                    redButton.setOnClickListener(this);
                    Button blueButton = (Button)colorView.findViewById(R.id.colour_blue);
                    blueButton.setOnClickListener(this);
                    Button greenButton = (Button)colorView.findViewById(R.id.colour_green);
                    greenButton.setOnClickListener(this);
                    colormenuSelected = true;

                }

                break;

            case R.id.colour_red:
                colormenuSelected = false;
                Toast.makeText(this, "red pressed", Toast.LENGTH_SHORT).show();
                drawView.paintColor = Color.RED;
                drawView.setUpDrawing();
                optionLayout.removeAllViews();
                colourbutton.setEnabled(true);
                Toast.makeText(this, Boolean.toString(colormenuSelected), Toast.LENGTH_SHORT).show();

                break;

            case R.id.colour_blue:
                colormenuSelected = false;
                Toast.makeText(this, "blue pressed", Toast.LENGTH_SHORT).show();
                drawView.paintColor = Color.BLUE;
                drawView.setUpDrawing();
                colourbutton.setEnabled(true);
                optionLayout.removeAllViews();

                break;

            case R.id.colour_green:
                colormenuSelected = false;
                Toast.makeText(this, "green pressed", Toast.LENGTH_SHORT).show();
                drawView.paintColor = Color.GREEN;
                drawView.setUpDrawing();
                colourbutton.setEnabled(true);
                optionLayout.removeAllViews();

                break;
            case R.id.Choose_Brush_Size:

                if (!brushmenuSelected) {
                    colourbutton.setEnabled(true);
                    colormenuSelected=false;
                    optionLayout.removeAllViews();
                    Toast.makeText(this, "pressed", Toast.LENGTH_SHORT).show();
                    inflate = getLayoutInflater();
                    View brushView= inflate.inflate(R.layout.brush_size, null);
                    optionLayout.addView(brushView);

                    brushbutton.setEnabled(false);
                    ImageButton smallButton= (ImageButton)brushView.findViewById(R.id.small_brush);
                    smallButton.setOnClickListener(this);
                    ImageButton medButton = (ImageButton)brushView.findViewById(R.id.med_brush);
                    medButton.setOnClickListener(this);
                    ImageButton largeButton = (ImageButton)brushView.findViewById(R.id.large_brush);
                    largeButton.setOnClickListener(this);
                    brushmenuSelected = true;

                }
                break;
            case R.id.small_brush:
                brushmenuSelected = false;
                drawView.brushSize= 20;
                drawView.setUpDrawing();
                brushbutton.setEnabled(true);
                optionLayout.removeAllViews();
                break;
            case R.id.med_brush:
                brushmenuSelected = false;
                drawView.brushSize= 50;
                drawView.setUpDrawing();
                brushbutton.setEnabled(true);
                optionLayout.removeAllViews();
                break;
            case R.id.large_brush:
                brushmenuSelected = false;
                drawView.brushSize= 100;
                drawView.setUpDrawing();
                brushbutton.setEnabled(true);
                optionLayout.removeAllViews();
                break;



        }

    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        Sensor accel = event.sensor;
        if (accel.getType() == Sensor.TYPE_ACCELEROMETER){
            float y = event.values[1];
            long curTime = System.currentTimeMillis();
            if ((curTime - lastupdate) > 200) {
                long diffTime = (curTime - lastupdate);
                lastupdate = curTime;

                float speed = Math.abs( y  - lasty)/ diffTime * 10000;

                if (speed > 800) {
                  drawView.clearCanvas();
                }


                lasty = y;

            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
