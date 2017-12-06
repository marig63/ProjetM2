package guillaume.marisa.projetm2;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by guillaume on 30/11/2017.
 */

public class Data {

    ArrayList<Individu> list;

    public Data(){

        list = new ArrayList<>();
        // INIT for test
        list.add(new Individu("Acc1",new LatLng(47.642728,6.866425),true));
        list.add(new Individu("Acc2",new LatLng(47.642660,6.862621),true));
        list.add(new Individu("billy1",new LatLng(47.642330,6.859815),false));
        list.add(new Individu("billy2",new LatLng(47.642266,6.855979),false));
        list.add(new Individu("billy3",new LatLng(47.639768,6.851946),false));
        list.add(new Individu("billy4",new LatLng(47.639101,6.852193),false));
        list.add(new Individu("billy5",new LatLng(47.636723,6.854911),false));
        list.add(new Individu("billy6",new LatLng(47.633578,6.855712),false));
        list.add(new Individu("billy7",new LatLng(47.633298,6.857945),false));
        list.add(new Individu("billy8",new LatLng(47.634643,6.862408),false));
        list.add(new Individu("billy9",new LatLng(47.639615,6.869365),false));
        list.add(new Individu("billy10",new LatLng(47.641165,6.870013),false));
        list.add(new Individu("billy11",new LatLng(47.641799,6.869267),false));

        //TODO reception JSON + Parsing

    }

    public void convertToDate(List<ObjectResponse> listObj){
        if(listObj == null){return ;}

        list = new ArrayList<>();

        for( ObjectResponse t: listObj) {
            list.add(new Individu(t.name(),new LatLng(t.lat(),t.lon()),Boolean.parseBoolean(t.admin())));
        }


    }

}
