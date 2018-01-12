package guillaume.marisa.projetm2;

import android.provider.ContactsContract;

import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

/**
 * Created by guillaume on 01/12/2017.
 */

public interface ProductService {

    @GET("index")
    Call<List<ObjectResponse>> postcode(@Query("action") String op,@Query("param1") String param1,@Query("param2") String param2);

    @GET("index")
    Call<List<ObjectResponse>> request(
            @QueryMap Map<String, String> options
    );


}
