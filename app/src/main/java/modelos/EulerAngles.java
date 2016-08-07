package modelos;

public class EulerAngles {

    private float yaw;
    private float pitch;
    private float roll;

    public EulerAngles(float yaw, float pitch, float roll) {
        this.yaw = yaw;
        this.pitch = pitch;
        this.roll = roll;
    }

    public EulerAngles() {
        this.yaw = 0;
        this.pitch = 0;
        this.roll = 0;
    }

    public float getYaw() {
        return yaw;
    }

    public float getPitch() {
        return pitch;
    }

    public float getRoll() {
        return roll;
    }
}
