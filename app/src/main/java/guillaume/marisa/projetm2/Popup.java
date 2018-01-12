package guillaume.marisa.projetm2;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
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
    static final int RESULT_CODE_BLUETOOTH= 12;


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

    public void joinGroup(View v){
        MainActivity.inGroup = true;
        if(!MainActivity.mBtAdapter.isEnabled())
        {
            Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            //discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
            startActivityForResult(discoverableIntent,RESULT_CODE_BLUETOOTH);
        }
        else {
            MainActivity.startBluetooth();
            this.finish();
        }

    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == RESULT_CODE_BLUETOOTH) {

            if (resultCode == RESULT_OK) {
                MainActivity.startBluetooth();

            }
        }

        this.finish();
    }


}
