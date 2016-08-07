package Sensores;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.view.View;

/**
 * Created by JON ANDER on 23/07/2016.
 */
public class SensorListener extends View implements SensorEventListener {
    private float   mOrientationValues[] = new float[3];
    private float   mAccelerometerValues[] = new float[3];
    private float   mMagneticValues[] = new float[3];
    private float   mGyroscopValues[] = new float[3];
    private float   mTemperatureValues;
    private SensorManager mSensorManager;

    public SensorListener(Context context) {
        super(context);
        this.mSensorManager = (SensorManager) context.getSystemService(context.SENSOR_SERVICE);

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        synchronized (this) {
            switch(event.sensor.getType()) {
                case Sensor.TYPE_ORIENTATION:
                    for (int i=0 ; i<3 ; i++) {
                        mOrientationValues[i] = event.values[i];
                    }
                    break;
                case Sensor.TYPE_ACCELEROMETER:
                    for (int i=0 ; i<3 ; i++) {
                        mAccelerometerValues[i] = event.values[i];

                    }
                    break;
                case Sensor.TYPE_MAGNETIC_FIELD:
                    for (int i=0 ; i<3 ; i++) {
                        mMagneticValues[i] = event.values[i];
                    }
                    break;
                case Sensor.TYPE_GYROSCOPE:
                    for (int i=0 ; i<3 ; i++) {
                        mGyroscopValues[i] = event.values[i];
                    }
                    break;
                default:
                    for (int i=0 ; i<event.values.length ; i++) {
                        mTemperatureValues = event.values[i];
                    }
            }
            invalidate();
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    // Metodo para iniciar el acceso a los sensores
    public void Ini_Sensores() {
        mSensorManager.registerListener(this,
                mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL);

        mSensorManager.registerListener(this,
                mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),
                SensorManager.SENSOR_DELAY_NORMAL);

        mSensorManager.registerListener(this,
                mSensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR),
                SensorManager.SENSOR_DELAY_NORMAL);

        mSensorManager.registerListener(this,
                mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD),
                SensorManager.SENSOR_DELAY_NORMAL);

        mSensorManager.registerListener(this,
                mSensorManager.getDefaultSensor(Sensor.TYPE_TEMPERATURE),
                SensorManager.SENSOR_DELAY_NORMAL);

        mSensorManager.registerListener(this,
                mSensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY),
                SensorManager.SENSOR_DELAY_NORMAL);

    }

    // Metodo para parar la escucha de los sensores
    public void Parar_Sensores() {

        mSensorManager.unregisterListener(this,
                mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER));

        mSensorManager.unregisterListener(this,
                mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION));

        mSensorManager.unregisterListener(this,
                mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD));

        mSensorManager.unregisterListener(this,
                mSensorManager.getDefaultSensor(Sensor.TYPE_TEMPERATURE));

        mSensorManager.unregisterListener(this,
                mSensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY));

        mSensorManager.unregisterListener(this,
                mSensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR));
    }
}
