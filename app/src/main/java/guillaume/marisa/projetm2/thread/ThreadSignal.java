package guillaume.marisa.projetm2.thread;

import java.util.ArrayList;

import guillaume.marisa.projetm2.Bluetooth;
import guillaume.marisa.projetm2.RequestManager;

/**
 * Created by guillaume on 02/01/2018.
 */

public class ThreadSignal extends Thread {

    RequestManager rm;
    Bluetooth bl;

    public ThreadSignal(Bluetooth list,RequestManager rm){
        this.rm = rm;
        this.bl= list;
    }

    public void run(){

        //rm.requestSignal(bl.deviceName,bl.hardwareAdress,bl.signal);


        //detected.clear();
        System.out.println("resquestSinal clearing!");
    }
}


