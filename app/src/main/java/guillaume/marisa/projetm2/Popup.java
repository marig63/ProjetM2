package guillaume.marisa.projetm2;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by guillaume on 14/11/2017.
 */

public class Popup extends Activity {

    static TextView nearest;
    static RequestManager requestManager;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.popupwindow);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int wi = dm.widthPixels;
        int he = dm.heightPixels;

        getWindow().setLayout((int)(wi*0.8),(int)(he*0.2));

        nearest = (TextView) findViewById(R.id.nearest);
        nearest.setText("");
    }

    static void showText(String text){
        //if(nearest != null){
            nearest.setText(text);
        //}
    }

    public void addMember(View v){
        requestManager.addMember("00::00:0:0","11:11:11:11:11");
    }

    public void createGroup(View v){
        requestManager.createGroup("Le groupe des chauves","11:22:33");
    }


}
