package modelos.actuator;

import modelos.MatrixMotor;

public class PwmActuactor implements modelos.actuator.PidActuator {
	MatrixMotor matrizMotores;
	Double value;

	@Override
	public void setValue(Double value) {
		this.value=value;
	}

	@Override
	public void setMatrixMotor(MatrixMotor matrizMotores) {
		this.matrizMotores=matrizMotores;
	}

	@Override
	public MatrixMotor getMatrixMotor() {
		return this.matrizMotores;
	}
}
