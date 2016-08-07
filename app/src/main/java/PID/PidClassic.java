package PID;

import modelos.MatrixMotor;
import modelos.actuator.PidActuator;
import modelos.sensor.PidSensor;

/**
 * Created by JON ANDER on 31/07/2016.
 */
public class PidClassic {
    private long lastTime = 0;
    private PidSensor sensor;
    private PidActuator actuator;
    private long waitMilliseconds=100;
    private Thread pidThread;
    private boolean stopThread;

    protected Double kp = 1.0, ki = 1.0, kd = 1.0;



    protected Double setPoint = 1.0;
    protected Double output = 0.0;
    protected Double min = -255.0, max = 255.0;
    protected Double iTerm = 0.0;
    protected Double lastInput = 0.0;
    protected Direction direction = Direction.DIRECT;
    protected boolean initialized = false;


    public PidClassic(double setPoint){
        this.setPoint = setPoint;
        this.kp = kp;
        this.ki = ki;
        this.kd = kd;
        this.sensor= sensor;
        this.actuator = actuator;
        this.waitMilliseconds = waitMilliseconds;
        //pidThread = new Thread(this);

    }

    public PidClassic(){
        this.setPoint = setPoint;
        this.kp = kp;
        this.ki = ki;
        this.kd = kd;
        this.sensor= sensor;
        this.actuator = actuator;
        this.waitMilliseconds = waitMilliseconds;
        //pidThread = new Thread(this);

    }

    public PidClassic(Double setPoint, Double kp, Double ki, Double kd, PidSensor pidSensor, PidActuator pidActuator, long waitMilliseconds) {
        this.setPoint = setPoint;
        this.kp = kp;
        this.ki = ki;
        this.kd = kd;
        this.sensor = pidSensor;
        this.actuator = pidActuator;
        this.waitMilliseconds = waitMilliseconds;
        //pidThread = new Thread(this);
    }



    public double computeEuler(Double sensor) {
        double sampleTimeInSeconds = ((double) waitMilliseconds) / 1000;

        Double error = setPoint - sensor;

        // P term
        Double pTerm = error;

        // I term
        long now = getTime();
        long deltaTime = (now - lastTime);
        deltaTime = (deltaTime <= 0) ? 1 : deltaTime;
        lastTime = now;
        iTerm += (ki * error / deltaTime);
        iTerm = getValueWithinLimits(iTerm);

        // D term
        Double dTerm = (sensor - lastInput) / lastTime;
        lastInput = sensor;
        output = direction.getValue() * (kp * pTerm + (ki * sampleTimeInSeconds * iTerm) - ((kd * dTerm) / sampleTimeInSeconds));
        output = getValueWithinLimits(output);

        return output;
    }


    public PidActuator compute(PidSensor sensor) {
        double sampleTimeInSeconds = ((double) waitMilliseconds) / 1000;

        Double error = setPoint - sensor.getValue();

        // P term
        Double pTerm = error;

        // I term
        long now = getTime();
        long deltaTime = (now - lastTime);
        deltaTime = (deltaTime <= 0) ? 1 : deltaTime;
        lastTime = now;
        iTerm += (error / deltaTime);
        iTerm = getValueWithinLimits(iTerm);

        // D term
        Double dTerm = (sensor.getValue() - lastInput) / lastTime;
        lastInput = sensor.getValue();
        MatrixMotor salida=new MatrixMotor();
        output = direction.getValue() * (kp * pTerm + (ki * sampleTimeInSeconds * iTerm) - ((kd * dTerm) / sampleTimeInSeconds));
        actuator.setValue(getValueWithinLimits(output));

        return actuator;
    }



    public void setOutputLimits(Double min, Double max) {
        this.min = min;
        this.max = max;
        output = getValueWithinLimits(output);
    }

    public void setKpid(Double kp, Double ki, double kd) {
        this.kp = kp;
        this.ki = ki;
        this.kd = kd;
    }

    protected Double getValueWithinLimits(Double value) {
        return (value < min) ? min : (value > max) ? max : value;
    }

    protected long getTime() {
        return System.currentTimeMillis();
    }

    public Double getKp() {
        return kp;
    }

    public void setKp(Double kp) {
        this.kp = kp;
    }

    public Double getKi() {
        return ki;
    }

    public void setKi(Double ki) {
        this.ki = ki;
    }

    public Double getKd() {
        return kd;
    }

    public void setKd(Double kd) {
        this.kd = kd;
    }

    public Double getSetPoint() {
        return setPoint;
    }

    public void setSetPoint(Double setPoint) {
        this.setPoint = setPoint;
    }
}
