package com.example.shake;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        Sensor accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        // Log.d() to see if it worked
        // Log.d("TAG", "onSensorChanged: X: " + event.values[0] + " Y: " + event.values[1] + " Z: " + event.values[2]);

        TextView xyz = (TextView) findViewById(R.id.mainFragmentYvalue);
        ImageView imageMainActivity = findViewById(R.id.imageMainActivity);

        double x = event.values[0];
        double y = event.values[1];
        double z = event.values[2];

        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            xyz.setText("X: " + x + "\nY: " + y + "\nZ: " + z);
        }

        RotateAnimation rotate = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.4f, Animation.RELATIVE_TO_SELF, 0.4f);
        rotate.setDuration(2000);
        rotate.setInterpolator(new LinearInterpolator());

        if (getForceToRotate(x, y, z) > 2f) {
            imageMainActivity.startAnimation(rotate);
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    private double getForceToRotate(double x, double y, double z) {
        double gX = x / sensorManager.GRAVITY_EARTH;
        double gY = y / sensorManager.GRAVITY_EARTH;
        double gZ = z / sensorManager.GRAVITY_EARTH;
        return Math.sqrt((double) (gX * gX + gY * gY + gZ * gZ));
    }
}