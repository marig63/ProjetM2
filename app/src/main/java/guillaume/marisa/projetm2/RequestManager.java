package guillaume.marisa.projetm2;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by guillaume on 03/12/2017.
 */

public class RequestManager {

    static ArrayList<MapElement> list;

    public RequestManager(){
        list = new ArrayList<>();
        //list.add(new Individual("Acc",new LatLng(47.639768,6.851946),true));
    }

    public void addMember(String adminMac,String newMac){
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://88.182.69.156/projetm2/trunk/index.php/") // dont forget '/' at the end of the url !!
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();

        ProductService service = retrofit.create(ProductService.class);

        Call<List<ObjectResponse>> call = service.postcode("addMember",adminMac,newMac);

        call.enqueue(new Callback<List<ObjectResponse>>() {
            @Override
            public void onResponse(Call<List<ObjectResponse>> call, retrofit2.Response<List<ObjectResponse>> response) {

                if( response.body() == null){
                    System.out.println("request null");
                    return ;
                }

                for( ObjectResponse t: response.body()) {
                    System.out.println("--------------------------------");
                    System.out.println(" ");
                    System.out.println(" ");
                    System.out.println(" ");
                    System.out.println(t.name() +" - "+t.role()+" - "+t.lat() + " - " + t.lon());
                    System.out.println(" ");
                    System.out.println(" ");
                    System.out.println(" ");
                    System.out.println("--------------------------------");

                }
            }

            @Override
            public void onFailure(Call<List<ObjectResponse>> call, Throwable t) {
                System.out.println("request failed");
            }
        });
    }

    public void createGroup(String name, String adminMac){
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://88.182.69.156/projetm2/trunk/index.php/") // dont forget '/' at the end of the url !!
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();

        ProductService service = retrofit.create(ProductService.class);

        Call<List<ObjectResponse>> call = service.postcode("createGroup",name,adminMac);

        call.enqueue(new Callback<List<ObjectResponse>>() {
            @Override
            public void onResponse(Call<List<ObjectResponse>> call, retrofit2.Response<List<ObjectResponse>> response) {

                if( response.body() == null){
                    System.out.println("request null");
                    return ;
                }

                for( ObjectResponse t: response.body()) {
                    System.out.println("--------------------------------");
                    System.out.println(" ");
                    System.out.println(" ");
                    System.out.println(" ");
                    System.out.println(t.name() +" - "+t.role()+" - "+t.lat() + " - " + t.lon());
                    System.out.println(" ");
                    System.out.println(" ");
                    System.out.println(" ");
                    System.out.println("--------------------------------");

                    if (Boolean.parseBoolean(t.ok())){
                        MainActivity.inGroup = true;
                    }
                }
            }

            @Override
            public void onFailure(Call<List<ObjectResponse>> call, Throwable t) {
                System.out.println("request failed");
            }
        });
    }

    public void init(String name, String adrMac){
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://88.182.69.156/projetm2/trunk/index.php/") // dont forget '/' at the end of the url !!
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();

        ProductService service = retrofit.create(ProductService.class);

        Call<List<ObjectResponse>> call = service.postcode("init",name,adrMac);

        call.enqueue(new Callback<List<ObjectResponse>>() {
            @Override
            public void onResponse(Call<List<ObjectResponse>> call, retrofit2.Response<List<ObjectResponse>> response) {

                if( response.body() == null){
                    System.out.println("request null");
                    return ;
                }

                for( ObjectResponse t: response.body()) {
                    System.out.println("--------------------------------");
                    System.out.println(" ");
                    System.out.println(" ");
                    System.out.println(" ");
                    System.out.println(t.name() + " - " + t.role() + " - " + t.lat() + " - " + t.lon());
                    System.out.println(" ");
                    System.out.println(" ");
                    System.out.println(" ");
                    System.out.println("--------------------------------");

                    // if first object ok is true , user is already in a group of people
                    if (Boolean.parseBoolean(t.ok())){
                        MainActivity.inGroup = true;
                    }

                }




            }

            @Override
            public void onFailure(Call<List<ObjectResponse>> call, Throwable t) {
                System.out.println("request failed");
            }
        });
    }

    public boolean update(String adrMac, ArrayList<Bluetooth> listBL){

        final List<ObjectResponse> reponse ;

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://88.182.69.156/projetm2/trunk/index.php/") // dont forget '/' at the end of the url !!
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();

        ProductService service = retrofit.create(ProductService.class);

        //Call<List<ObjectResponse>> call = service.postcode("requestUpdate",adrMac,"osef");

        Map<String, String> data = new HashMap<>();

        int i = 1;
        for(Bluetooth bl : listBL){
            data.put("paramsig"+i, bl.signal);
            data.put("paramadr"+i, bl.hardwareAdress);
            //System.out.println(" v :" + bl.signal + " -- " + bl.hardwareAdress);
            i++;
        }
        listBL.clear();

        /*data.put("param7", "nuuull");
        data.put("param6", "nuuull");
        data.put("param5", "truck");
        data.put("mac", "naall");*/
        data.put("param3", "bla");
        data.put("param2", "nill");
        data.put("param1", adrMac);
        data.put("action", "requestUpdate");



        Set<Entry<String, String>> entries = data.entrySet();


        TreeMap<String, String> sorted = new TreeMap<>(data); Set<Entry<String, String>> mappings = sorted.entrySet();


        Comparator<Entry<String, String>> valueComparator = new Comparator<Entry<String,String>>() {
            @Override public int compare(Entry<String, String> e1, Entry<String, String> e2) {


                String v1 = e1.getKey();
                String v2 = e2.getKey();
                return v1.compareTo(v2);
            }
        };
        List<Entry<String, String>> listOfEntries = new ArrayList<Entry<String, String>>(entries);
        Collections.sort(listOfEntries, valueComparator);
        LinkedHashMap<String, String> sortedByValue = new LinkedHashMap<String, String>(listOfEntries.size());

        for(Entry<String, String> entry : listOfEntries){
            sortedByValue.put(entry.getKey(), entry.getValue());
        }



        //System.out.println("SIZE : " + listBL.size());



        Call<List<ObjectResponse>> call = service.request(sortedByValue);

        System.out.println(call.request().toString());

        call.enqueue(new Callback<List<ObjectResponse>>() {
            @Override
            public void onResponse(Call<List<ObjectResponse>> call, retrofit2.Response<List<ObjectResponse>> response) {

                if( response.body() == null){
                    System.out.println("request null");
                    return ;
                }

                list = new ArrayList<>();

                for( ObjectResponse t: response.body()) {
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

                Collections.sort(list, new Comparator<MapElement>() {
                    @Override
                    public int compare(MapElement i1, MapElement i2)
                    {
                        if(i1.getClass()==i2.getClass()){ return 0;} // same class

                        if(i1.getClass() == Admin.class){ return -1;}
                        if(i2.getClass() == Admin.class){ return 1;}

                        if(i1.getClass() == Member.class ){ return -1;}
                        if(i2.getClass() == Member.class){ return 1;}

                        if(i1.getClass() == Ble.class ){ return -1;}
                        if(i2.getClass() == Ble.class){ return 1;}


                        return 0;
                    }
                });

                //Data.convertToDate( response.body() );

                for( ObjectResponse t: response.body()) {
                    System.out.println("--------------------------------");
                    System.out.println(" ");
                    System.out.println(" ");
                    System.out.println(" ");
                    System.out.println(t.name() +" - "+t.role()+" - "+ t.lat() + " - " + t.lon() + " ok? " + t.ok());
                    System.out.println(" ");
                    System.out.println(" ");
                    System.out.println(" ");
                    System.out.println("--------------------------------");

                }
            }

            @Override
            public void onFailure(Call<List<ObjectResponse>> call, Throwable t) {
                //System.out.println("request failed");
            }
        });

        return true;
    }



    public void disperseGroup(String adminMac){
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://88.182.69.156/projetm2/trunk/index.php/") // dont forget '/' at the end of the url !!
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();

        ProductService service = retrofit.create(ProductService.class);

        Call<List<ObjectResponse>> call = service.postcode("disperse",adminMac,"osef");

        call.enqueue(new Callback<List<ObjectResponse>>() {
            @Override
            public void onResponse(Call<List<ObjectResponse>> call, retrofit2.Response<List<ObjectResponse>> response) {

                if( response.body() == null){
                    System.out.println("request null");
                    return ;
                }

                for( ObjectResponse t: response.body()) {
                    System.out.println("--------------------------------");
                    System.out.println(" ");
                    System.out.println(" ");
                    System.out.println(" ");
                    System.out.println(t.name() +" - "+t.role()+" - "+t.lat() + " - " + t.lon());
                    System.out.println(" ");
                    System.out.println(" ");
                    System.out.println(" ");
                    System.out.println("--------------------------------");

                    if (Boolean.parseBoolean(t.ok())){
                        MainActivity.inGroup = false;
                    }
                }
            }

            @Override
            public void onFailure(Call<List<ObjectResponse>> call, Throwable t) {
                System.out.println("request failed");
            }
        });
    }

    public void leaveGroup(String memberMac){
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://88.182.69.156/projetm2/trunk/index.php/") // dont forget '/' at the end of the url !!
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();

        ProductService service = retrofit.create(ProductService.class);

        Call<List<ObjectResponse>> call = service.postcode("leaveGroup",memberMac,"osef");

        call.enqueue(new Callback<List<ObjectResponse>>() {
            @Override
            public void onResponse(Call<List<ObjectResponse>> call, retrofit2.Response<List<ObjectResponse>> response) {

                if( response.body() == null){
                    System.out.println("request null");
                    return ;
                }

                for( ObjectResponse t: response.body()) {
                    System.out.println("--------------------------------");
                    System.out.println(" ");
                    System.out.println(" ");
                    System.out.println(" ");
                    System.out.println(t.name() +" - "+t.role()+" - "+t.lat() + " - " + t.lon());
                    System.out.println(" ");
                    System.out.println(" ");
                    System.out.println(" ");
                    System.out.println("--------------------------------");

                    if (Boolean.parseBoolean(t.ok())){
                        MainActivity.inGroup = false;
                    }
                }
            }

            @Override
            public void onFailure(Call<List<ObjectResponse>> call, Throwable t) {
                System.out.println("request failed");
            }
        });
    }

    public void promote(String adminMac, String memberPromotedMac){
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://88.182.69.156/projetm2/trunk/index.php/") // dont forget '/' at the end of the url !!
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();

        ProductService service = retrofit.create(ProductService.class);

        Call<List<ObjectResponse>> call = service.postcode("promote",adminMac,memberPromotedMac);

        call.enqueue(new Callback<List<ObjectResponse>>() {
            @Override
            public void onResponse(Call<List<ObjectResponse>> call, retrofit2.Response<List<ObjectResponse>> response) {

                if( response.body() == null){
                    System.out.println("request null");
                    return ;
                }

                for( ObjectResponse t: response.body()) {
                    System.out.println("--------------------------------");
                    System.out.println(" ");
                    System.out.println(" ");
                    System.out.println(" ");
                    System.out.println(t.name() +" - "+t.role()+" - "+t.lat() + " - " + t.lon());
                    System.out.println(" ");
                    System.out.println(" ");
                    System.out.println(" ");
                    System.out.println("--------------------------------");

                }
            }

            @Override
            public void onFailure(Call<List<ObjectResponse>> call, Throwable t) {
                System.out.println("request failed");
            }
        });
    }

    /*public void requestSignal(String memberMac, String hardwareAdress, String signal){
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://88.182.69.156/projetm2/trunk/index.php/") // dont forget '/' at the end of the url !!
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();

        SignalService service = retrofit.create(SignalService.class);

        Call<List<ObjectResponse>> call = service.signal("requestSignal",memberMac,hardwareAdress,signal);

        call.enqueue(new Callback<List<ObjectResponse>>() {
            @Override
            public void onResponse(Call<List<ObjectResponse>> call, retrofit2.Response<List<ObjectResponse>> response) {

                if( response.body() == null){
                    System.out.println("request null");
                    return ;
                }

                for( ObjectResponse t: response.body()) {
                    System.out.println("--------------------------------");
                    System.out.println(" ");
                    System.out.println(" ");
                    System.out.println(" ");
                    System.out.println(t.name() +" - "+t.role()+" - "+t.lat() + " - " + t.lon());
                    System.out.println(" ");
                    System.out.println(" ");
                    System.out.println(" ");
                    System.out.println("--------------------------------");

                    if (Boolean.parseBoolean(t.ok())){
                        MainActivity.inGroup = false;
                    }
                }
            }

            @Override
            public void onFailure(Call<List<ObjectResponse>> call, Throwable t) {
                System.out.println(t.toString());
            }
        });
    }*/
}
