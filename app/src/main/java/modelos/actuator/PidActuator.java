package modelos.actuator;

import modelos.MatrixMotor;

public interface PidActuator {
	public void setValue(Double value);

	public void setMatrixMotor(MatrixMotor matrixOuput);
	public MatrixMotor getMatrixMotor();
}
