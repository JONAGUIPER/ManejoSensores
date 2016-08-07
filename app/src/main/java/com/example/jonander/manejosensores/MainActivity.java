package com.example.jonander.manejosensores;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.EventListener;

import PID.PidClassic;
import Sensores.HardwareChecker;
import Sensores.ImprovedOrientationSensor1Provider;
import Sensores.OrientationProvider;
import Sensores.SensorChecker;
import modelos.MatrixMotor;

public class MainActivity extends AppCompatActivity{

    TextView info, infoip, msg ,acelText, RL,RR,FR,FL;
    private SensorManager mSensorManager;
    private OrientationProvider currentOrientationProvider;

    MatrixMotor salidaMotores;
    android.os.Handler customHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        info = (TextView) findViewById(R.id.info);
        infoip = (TextView) findViewById(R.id.infoip);
        msg = (TextView) findViewById(R.id.msg);
        //infoip.setText(getIpAddress());

        acelText =(TextView) findViewById(R.id.acelerometro);
        RR = (TextView) findViewById(R.id.RR);
        RL = (TextView) findViewById(R.id.RL);
        FR = (TextView) findViewById(R.id.FR);
        FL = (TextView) findViewById(R.id.FL);

        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        // Check if device has a hardware gyroscope
        SensorChecker checker = new HardwareChecker(mSensorManager);
        if(!checker.IsGyroscopeAvailable()) {
            // If a gyroscope is unavailable, display a warning.
            displayHardwareMissingWarning();
        }


        currentOrientationProvider = new ImprovedOrientationSensor1Provider(mSensorManager);

        customHandler = new android.os.Handler();
        customHandler.postDelayed(updateTimerThread, 0);

    }

    private Runnable updateTimerThread = new Runnable()
    {
        public void run()
        {
            double pinchGra = currentOrientationProvider.getEulerAngles().getPitch() / Math.PI * 360.f;//esta coordenada viene en base a 90º
            double rollGra = currentOrientationProvider.getEulerAngles().getRoll() / Math.PI * 180.f;
            rollGra = (Math.abs(rollGra) >= 179.99) ? 180.f : rollGra;//existe indeterminacion en 180º
            double yawGra = currentOrientationProvider.getEulerAngles().getYaw() / Math.PI * 180.f;
            acelText.setText("pitch: " + pinchGra +
                    "\nroll: " + rollGra +
                    "\nyaw: " + yawGra);
            salidaMotores=new MatrixMotor();
            PidClassic pidPintch=new PidClassic(0.);
            PidClassic pidRoll=new PidClassic(0.);
            PidClassic pidYaw=new PidClassic(0.);

            int pintch = (int) pidPintch.computeEuler(pinchGra);
            salidaMotores.addPintchX(pintch);

            int roll = (int) pidRoll.computeEuler(rollGra);
            salidaMotores.addRollX(roll);

            int yaw = (int) pidYaw.computeEuler(yawGra);
            salidaMotores.addYawX(yaw);

            salidaMotores.normalizar();
            /*RR.setText(Integer.toString(pintch));
            RL.setText(Integer.toString(roll));
            FR.setText(Integer.toString(yaw));
*/
            RR.setText(Integer.toString(salidaMotores.getRearRight()));
            RL.setText(Integer.toString(salidaMotores.getRearLeft()));
            FR.setText(Integer.toString(salidaMotores.getFrontRight()));
            FL.setText(Integer.toString(salidaMotores.getFrontLeft()));

            //write here whaterver you want to repeat
            customHandler.postDelayed(this, 100);
        }
    };


    private void displayHardwareMissingWarning() {
        AlertDialog ad = new AlertDialog.Builder(this).create();
        ad.setCancelable(false); // This blocks the 'BACK' button
        ad.setTitle(getResources().getString(R.string.noHayGiroscopio));
        ad.setMessage(getResources().getString(R.string.mensajeNoHayGiroscopio));
        ad.setButton(DialogInterface.BUTTON_NEUTRAL, getResources().getString(R.string.OK), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        ad.show();
    }

    private String getIpAddress() {
        String ip = "";
        try {
            Enumeration<NetworkInterface> enumNetworkInterfaces = NetworkInterface
                    .getNetworkInterfaces();
            while (enumNetworkInterfaces.hasMoreElements()) {
                NetworkInterface networkInterface = enumNetworkInterfaces
                        .nextElement();
                Enumeration<InetAddress> enumInetAddress = networkInterface
                        .getInetAddresses();
                while (enumInetAddress.hasMoreElements()) {
                    InetAddress inetAddress = enumInetAddress.nextElement();

                    if (inetAddress.isSiteLocalAddress()) {
                        ip += "SiteLocalAddress: "
                                + inetAddress.getHostAddress() + "\n";
                    }

                }

            }

        } catch (SocketException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            ip += "Something Wrong! " + e.toString() + "\n";
        }

        return ip;
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        currentOrientationProvider.stop();
    }

    protected void onResume() {
        super.onResume();
        currentOrientationProvider.start();
    }
}
