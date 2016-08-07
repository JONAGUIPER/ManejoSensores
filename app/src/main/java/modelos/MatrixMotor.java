package modelos;

/**
 * Created by JON ANDER on 03/08/2016.
 */
public class MatrixMotor {
    private int rearLeft;
    private int rearRight;
    private int frontLeft;
    private int frontRight;


    public MatrixMotor(int rearLeft, int rearRight, int frontLeft, int frontRight) {
        this.rearLeft = rearLeft;
        this.rearRight = rearRight;
        this.frontLeft = frontLeft;
        this.frontRight = frontRight;
    }

    public MatrixMotor() {
        this.rearLeft = 0;
        this.rearRight = 0;
        this.frontLeft = 0;
        this.frontRight = 0;
    }

    public MatrixMotor(int value) {
        this.rearLeft = value;
        this.rearRight = value;
        this.frontLeft = value;
        this.frontRight = value;
    }

    public int getRearLeft() {
        return rearLeft;
    }

    public void setRearLeft(int rearLeft) {
        this.rearLeft = rearLeft;
    }

    public int getRearRight() {
        return rearRight;
    }

    public void setRearRight(int rearRight) {
        this.rearRight = rearRight;
    }

    public int getFrontLeft() {
        return frontLeft;
    }

    public void setFrontLeft(int frontLeft) {
        this.frontLeft = frontLeft;
    }

    public int getFrontRight() {
        return frontRight;
    }

    public void setFrontRight(int frontRight) {
        this.frontRight = frontRight;
    }

    public MatrixMotor addPintchX(double valor){
        this.rearLeft += valor;
        this.rearRight += valor;
        this.frontLeft -= valor;
        this.frontRight -= valor;
        return this;
    }

    public MatrixMotor addRollX(double valor){
        this.rearLeft += valor;
        this.rearRight -= valor;
        this.frontLeft += valor;
        this.frontRight -= valor;
        return this;
    }

    public MatrixMotor addYawX(double valor){
        this.rearLeft -= valor;
        this.rearRight += valor;
        this.frontLeft += valor;
        this.frontRight -= valor;
        return this;
    }



    public MatrixMotor addAltitud(double valor){
        this.rearLeft += valor;
        this.rearRight += valor;
        this.frontLeft += valor;
        this.frontRight += valor;
        return this;
    }


    public MatrixMotor normalizar(){
        this.rearLeft=(this.rearLeft>255)?255:this.rearLeft;
        this.rearLeft=(this.rearLeft<0)?0:this.rearLeft;

        this.rearRight=(this.rearRight>255)?255:this.rearRight;
        this.rearRight=(this.rearRight<0)?0:this.rearRight;

        this.frontLeft=(this.frontLeft>255)?255:this.frontLeft;
        this.frontLeft=(this.frontLeft<0)?0:this.frontLeft;

        this.frontRight=(this.frontRight>255)?255:this.frontRight;
        this.frontRight=(this.frontRight<0)?0:this.frontRight;
        return this;
    }
}
