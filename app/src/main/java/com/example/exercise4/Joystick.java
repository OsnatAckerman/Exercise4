package com.example.exercise4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class Joystick extends AppCompatActivity implements JoystickView.JoystickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joystick);
        Intent intent = getIntent();
        String ip = intent.getStringExtra("ip");
        String port1 = intent.getStringExtra("port");
        int port = Integer.parseInt(port1);
        TCPclient.getInstance().connect(ip, port);
        JoystickView joystickView = new JoystickView(this);
        setContentView(joystickView);
    }

    @Override
    public void onJoystickMoved(float aileron, float elevator) {
        TCPclient.getInstance().send("set /controls/flight/aileron " + Float.toString(aileron) + "\r\n");
        TCPclient.getInstance().send("set /controls/flight/elevator " + Float.toString(elevator) + "\r\n");
    }

    @Override
    public void onDestroy() {
        TCPclient.getInstance().disconnect();
        super.onDestroy();
    }
}
