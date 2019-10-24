package com.example.CryptoChat.controllers;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.CryptoChat.R;

import static java.lang.Math.sqrt;

public class SpeedSensorActivity extends Activity implements SensorEventListener {

    private SensorManager mSensorManager;
    private TextView mTxtValue;
    private Sensor mSensor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // sensor manager
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        // TYPE_ACCELEROMETER
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        // TYPE_LIGHT
    }

    @Override
    protected void onResume() {
        super.onResume();
        // add listener for mSensor
        mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mSensorManager.unregisterListener(this);
    }

    // call when the value of sensor changed
    @Override
    public void onSensorChanged(SensorEvent event) {
        float[] values = event.values;
        int x = (int) values[0];
        int y = (int) values[1];
        int z = (int) values[2];
        double accelerometer = sqrt(x^2+y^2+z^2);
        double speed = 3 * accelerometer;

        if (speed > 3){
            // TODO: add reccomendation on Screen(walk too fast, plz change to voice talk or sth)
            Toast.makeText(this,"please carefully messaging when you are walking", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

}