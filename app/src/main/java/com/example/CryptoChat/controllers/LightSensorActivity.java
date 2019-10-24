package com.example.CryptoChat.controllers;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.Toast;

import com.example.CryptoChat.R;

public class LightSensorActivity extends Activity implements SensorEventListener {
    private SensorManager sensorManager;
    private Sensor light;

    @Override
    public final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        light = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
    }

    @Override
    public final void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    @Override
    public final void onSensorChanged(SensorEvent event) {
        float light_now = event.values[0];

        if (light_now < 100){
            // TODO: pop out a reccomentdation to change a background or etc.
            Toast.makeText(this,"Light is too low, recommend a night background", Toast.LENGTH_SHORT).show();
        }else if(light_now > 50000){
            // TODO: pop out a reccomentdation to change a background or etc.
            Toast.makeText(this, "Light is too high, recommend a light background", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onResume() {
        // Register a listener for the sensor.
        super.onResume();
        sensorManager.registerListener(this, light, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        // Be sure to unregister the sensor when the activity pauses.
        super.onPause();
        sensorManager.unregisterListener(this);
    }
}