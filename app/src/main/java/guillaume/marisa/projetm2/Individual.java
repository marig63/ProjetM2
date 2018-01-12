package guillaume.marisa.projetm2;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by guillaume on 30/11/2017.
 */

public class Individual extends MapElement  {
    String nom;

    public Individual(String nom, LatLng latLng) {
        super(latLng);
        this.nom = nom;
    }

}
