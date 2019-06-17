package com.example.exercise4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private String ip;
    private String port;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void connectButton(View view) {

        this.ip = ((EditText)findViewById(R.id.ipEditText)).getText().toString();
        this.port = ((EditText)findViewById(R.id.portTextEdit)).getText().toString();
        Intent intent = new Intent(this,
                Joystick.class);
        intent.putExtra("ip", ip);
        intent.putExtra("port", port);

        startActivity(intent);
    }

}
