package com.example.exercise4;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

public class Joystick extends AppCompatActivity implements JoystickView.JoystickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joystick);
        JoystickView joystickView = new JoystickView(this);
        setContentView(joystickView);
    }

    @Override
    public void onJoystickMoved(float aileron, float elevator) {
        Log.d("Main Method", "aileron: " + aileron + " elevator: " + elevator);
    }
}
