package modelos.sensor;

import modelos.EulerAngles;

public interface PidSensor {
	public Double getValue();
	public EulerAngles getAngulosEuler();
	public EulerAngles setAngulosEuler(EulerAngles angulos);
}
