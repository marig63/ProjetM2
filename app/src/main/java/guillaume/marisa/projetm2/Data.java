package guillaume.marisa.projetm2;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by guillaume on 30/11/2017.
 */

public class Data {

    ArrayList<MapElement> list;

    public Data(){

        list = new ArrayList<>();
        // INIT for test
        list.add(new Admin("Acc1",new LatLng(47.642728,6.866425)));
        list.add(new Admin("Acc2",new LatLng(47.642660,6.862621)));
        list.add(new Member("billy1",new LatLng(47.642330,6.859815)));
        list.add(new Member("billy2",new LatLng(47.642266,6.855979)));
        list.add(new Member("billy3",new LatLng(47.639768,6.851946)));
        list.add(new Member("billy4",new LatLng(47.639101,6.852193)));
        list.add(new Member("billy5",new LatLng(47.636723,6.854911)));
        list.add(new Member("billy6",new LatLng(47.633578,6.855712)));
        list.add(new Member("billy7",new LatLng(47.633298,6.857945)));
        list.add(new Member("billy8",new LatLng(47.634643,6.862408)));
        list.add(new Member("billy9",new LatLng(47.639615,6.869365)));
        list.add(new Member("billy10",new LatLng(47.641165,6.870013)));
        list.add(new Member("billy11",new LatLng(47.641799,6.869267)));

        //TODO reception JSON + Parsing

    }

    public void convertToDate(List<ObjectResponse> listObj){
        if(listObj == null){return ;}

        list = new ArrayList<>();

        for( ObjectResponse t: listObj) {
            boolean acc;
            if(t.role()==null){
                acc=false;
            }
            else {
                if (t.role().compareTo("admin") == 0){
                    list.add(new Admin(t.name(),new LatLng(t.lat(),t.lon())));
                }
                if(t.role().compareTo("member") == 0) {
                    list.add(new Member(t.name(),new LatLng(t.lat(),t.lon())));

                }
                if(t.role().compareTo("ble") == 0) {
                    list.add(new Ble(t.name(),new LatLng(t.lat(),t.lon())));

                }
                if(t.role().compareTo("ping") == 0) {
                    list.add(new Ping(t.name(),new LatLng(t.lat(),t.lon())));
                }
            }
            //System.out.println("AAAAAAAAAAAAAA|| - " + t.role() + " -> " + acc);
            //list.add(new Individual(t.name(),new LatLng(t.lat(),t.lon())));
        }


    }

}
