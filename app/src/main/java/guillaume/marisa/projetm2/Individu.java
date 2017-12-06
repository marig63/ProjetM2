package guillaume.marisa.projetm2;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by guillaume on 30/11/2017.
 */

public class Individu {
    String nom;
    LatLng latLng;
    boolean accomp;

    public Individu(String nom, LatLng latLng, boolean accomp) {
        this.nom = nom;
        this.latLng = latLng;
        this.accomp = accomp;
    }

}
