package guillaume.marisa.projetm2;

/**
 * Created by guillaume on 30/12/2017.
 */

public class Bluetooth {

    public String deviceName;
    public String hardwareAdress;
    public String signal;
    public Boolean send;

    public Bluetooth(String deviceName, String hardwareAdress, String signal) {
        this.deviceName = deviceName;
        this.hardwareAdress = hardwareAdress;
        this.signal = signal;
        this.send = false;
    }
}
