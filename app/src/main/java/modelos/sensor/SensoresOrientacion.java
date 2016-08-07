package modelos.sensor;

import modelos.EulerAngles;

/**
 * Created by JON ANDER on 30/07/2016.
 */
public class SensoresOrientacion implements PidSensor {
    EulerAngles angulosEuler;
    Double value;

    @Override
    public Double getValue() {
        return this.value;
    }

    @Override
    public EulerAngles getAngulosEuler() {
        return this.angulosEuler;
    }

    @Override
    public EulerAngles setAngulosEuler(EulerAngles angulos) {
        return null;
    }
}
