package guillaume.marisa.projetm2;

import android.provider.ContactsContract;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * Created by guillaume on 01/12/2017.
 */

public interface ProductService {
    @GET("list")
    Call<List<ObjectResponse>> list();

    @GET
    public Call<ResponseBody> profilePicture(@Url String url);

    @GET("index")
    Call<List<ObjectResponse>> postcode(@Query("action") String op,@Query("param1") String param1,@Query("param2") String param2);
}
