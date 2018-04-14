package com.example.tkarl.etchasketchish;

import android.content.Intent;
import android.content.IntentSender;
import android.content.res.XmlResourceParser;
import android.graphics.Canvas;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Xml;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;

public class DrawingBoard extends AppCompatActivity implements View.OnClickListener {
DrawingView drawView;
    ImageButton upButton,downButton,rightButton,leftButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawing_board);

        drawView = (DrawingView)findViewById(R.id.canvas_board);
        upButton = (ImageButton)findViewById(R.id.arrow_up);
        upButton.setOnClickListener(this);
        downButton = (ImageButton)findViewById(R.id.arrow_down);
        downButton.setOnClickListener(this);
        leftButton = (ImageButton)findViewById(R.id.arrow_left);
        leftButton.setOnClickListener(this);
        rightButton = (ImageButton)findViewById(R.id.arrow_right);
        rightButton.setOnClickListener(this);


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
        }

    }


}
