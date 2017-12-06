package guillaume.marisa.projetm2;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by guillaume on 03/12/2017.
 */

public class RequestManager {

    static ArrayList<Individu> list;

    public RequestManager(){
        list = new ArrayList<>();
        list.add(new Individu("Acc",new LatLng(47.639768,6.851946),true));
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
                    System.out.println(t.name() +" - "+t.admin()+" - "+t.lat() + " - " + t.lon());
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
                    System.out.println(t.name() +" - "+t.admin()+" - "+t.lat() + " - " + t.lon());
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
                    System.out.println(t.name() +" - "+t.admin()+" - "+t.lat() + " - " + t.lon());
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

    public boolean update(String adrMac){

        final List<ObjectResponse> reponse ;

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://88.182.69.156/projetm2/trunk/index.php/") // dont forget '/' at the end of the url !!
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();

        ProductService service = retrofit.create(ProductService.class);

        Call<List<ObjectResponse>> call = service.postcode("requestUpdate",adrMac,"osef");

        call.enqueue(new Callback<List<ObjectResponse>>() {
            @Override
            public void onResponse(Call<List<ObjectResponse>> call, retrofit2.Response<List<ObjectResponse>> response) {

                if( response.body() == null){
                    System.out.println("request null");
                    return ;
                }

                list = new ArrayList<>();

                for( ObjectResponse t: response.body()) {
                    list.add(new Individu(t.name(),new LatLng(t.lat(),t.lon()),Boolean.parseBoolean(t.admin())));
                }

                Collections.sort(list, new Comparator<Individu>() {
                    @Override
                    public int compare(Individu i1, Individu i2)
                    {
                        if(i1.accomp && i2.accomp){ return 0;}
                        if(i1.accomp){ return -1;}
                        if(i2.accomp){ return 1;}
                        return 0;
                    }
                });

                //Data.convertToDate( response.body() );

                for( ObjectResponse t: response.body()) {
                    System.out.println("--------------------------------");
                    System.out.println(" ");
                    System.out.println(" ");
                    System.out.println(" ");
                    System.out.println(t.name() +" - "+t.admin()+" - "+ t.lat() + " - " + t.lon());
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

        return true;
    }




}
