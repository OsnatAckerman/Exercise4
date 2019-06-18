package com.example.exercise4;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;


public class JoystickView extends SurfaceView implements SurfaceHolder.Callback, View.OnTouchListener {
    private float centerX;
    private float centerY;
    private float baseRadius;
    private float hatRadius;
    private JoystickListener joystickCallback;

    private void setDim(){
        centerX = (float)getWidth()/2;
        centerY = (float)getHeight()/2;
        baseRadius = (float)Math.min(getWidth(), getHeight())/3;
        hatRadius = (float)Math.min(getWidth(), getHeight())/5;
    }

    public JoystickView (Context c) {
        super(c);
        getHolder().addCallback(this);
        setOnTouchListener(this);
        if(c instanceof JoystickListener) {
            joystickCallback = (JoystickListener) c;
        }
    }

    public JoystickView (Context c, AttributeSet attributeSet, int style) {
        super(c, attributeSet, style);
        getHolder().addCallback(this);
        setOnTouchListener(this);
        if(c instanceof JoystickListener) {
            joystickCallback = (JoystickListener) c;
        }
    }

    public JoystickView (Context c, AttributeSet attributeSet) {
        super(c, attributeSet);
        getHolder().addCallback(this);
        setOnTouchListener(this);
        if(c instanceof JoystickListener) {
            joystickCallback = (JoystickListener) c;
        }
    }


    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        setDim();
        drawJoystick(centerX, centerY);
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
        setDim();
        drawJoystick(centerX,centerY);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

    }

    private void drawJoystick(float newX, float newY){

        if(getHolder().getSurface().isValid()) {

            Canvas myCanvas = this.getHolder().lockCanvas();
            Paint colors = new Paint();
            myCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
            colors.setARGB(200, 70, 20, 80);
            myCanvas.drawCircle(centerX, centerY, baseRadius, colors);
            colors.setARGB(250, 50, 50, 50);
            myCanvas.drawCircle(newX, newY, hatRadius, colors);
            getHolder().unlockCanvasAndPost(myCanvas);
        }

    }


    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if(view.equals(this)){
            if(motionEvent.getAction() != motionEvent.ACTION_UP){
                float displacement = (float) Math.sqrt(Math.pow(motionEvent.getX() - centerX, 2) +
                        Math.pow(motionEvent.getY() - centerY, 2));
                if(displacement < baseRadius) {
                    drawJoystick(motionEvent.getX(), motionEvent.getY());
                    joystickCallback.onJoystickMoved(((motionEvent.getX() - centerX) / baseRadius),
                            ((motionEvent.getY() - centerY) / baseRadius));
                } else {
                    float ratio = baseRadius / displacement;
                    float constrainedX = centerX + (motionEvent.getX() - centerX) * ratio;
                    float constrainedY = centerY + (motionEvent.getY() - centerY) * ratio;
                    drawJoystick(constrainedX,constrainedY);
                    joystickCallback.onJoystickMoved(((constrainedX - centerX) / baseRadius),
                            ((constrainedY - centerY) / baseRadius));
                }
            } else {
                drawJoystick(centerX,centerY);
                joystickCallback.onJoystickMoved(0,0);
            }
        }
        return true;
    }

    public interface JoystickListener

    {

        void onJoystickMoved(float aileron, float elevator);

    }
}
