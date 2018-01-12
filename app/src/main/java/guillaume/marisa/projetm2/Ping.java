package guillaume.marisa.projetm2;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by guillaume on 16/12/2017.
 */

public class Ping extends MapElement{

    String time;
    boolean signal;

    public Ping(String nom, LatLng latLng) {
        super(latLng);
        this.time = nom;
        signal = false;
    }

}
