package guillaume.marisa.projetm2;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.ResponseCache;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import guillaume.marisa.projetm2.thread.ThreadSignal;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    static GoogleMap map;
    static NavigationView navigationView;
    static boolean mapReady = false;
    static boolean inGroup = false;
    //Data data = new Data();
    RequestManager requestManager = new RequestManager();
    static BluetoothAdapter mBtAdapter;
    private String myName = "";
    private String myAdrMac;
    private boolean showTiquette = false;
    private Timer myTimer;
    private ArrayList<Bluetooth> detected;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        TextView bar_name = (TextView) findViewById(R.id.bar_name);

        Bundle b = getIntent().getExtras();
        if(b != null) {
            myName = b.getString("name");
            //bar_name.setText(myName);
        }



        Popup.requestManager = requestManager;
        detected = new ArrayList<>();

        //if(data.list == null){
         //   data.list = new ArrayList<>();
        //}

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                       // .setAction("Action", null).show();
                refresh();
                startActivity(new Intent(MainActivity.this,Popup.class));


            }
        });


        myAdrMac = android.provider.Settings.Secure.getString(this.getContentResolver(), "bluetooth_address");
        System.out.println("myMAC bluetooth adresse : " + myAdrMac);

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        SupportMapFragment mapFragment = (SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(new OnMapReadyCallback() {

            @Override
            public void onMapReady(GoogleMap googleMap) {
                map = googleMap;

                googleMap.addMarker(new MarkerOptions()
                        .position(new LatLng(47.495248, 6.802616))
                        .title("UFR STGI")
                        .snippet("M2 IMR"));

                LatLng montB = new LatLng(47.495248,6.802616);

                googleMap.getUiSettings().setMapToolbarEnabled(false);


                CircleOptions circleOptions = new CircleOptions();
                circleOptions.center(montB);
                circleOptions.radius(20);
                circleOptions.strokeColor(Color.BLACK);
                circleOptions.fillColor(0x30ff0000);
                circleOptions.strokeWidth(2);
                googleMap.addCircle(circleOptions);

                googleMap.moveCamera(CameraUpdateFactory.newLatLng(montB));
                googleMap.animateCamera(CameraUpdateFactory.zoomTo(20));

                showDataOnMap();
                MainActivity.mapReady = true;


            }

        });



        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(mReceiver, filter);

        IntentFilter filter2 = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        registerReceiver(mReceiver, filter2);

        mBtAdapter = BluetoothAdapter.getDefaultAdapter();
        startBluetooth();

        requestManager.init(myName,myAdrMac);

        if(mapReady) {
            //requestManager.update(myAdrMac);
            //
        }
        updateMenu();

        myTimer = new Timer();
        myTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                TimerMethod();
            }

        }, 1000, 3000);

    }

    static void startBluetooth(){
        if(inGroup) {
            mBtAdapter.startDiscovery();
        }
    }

    private void TimerMethod()
    {
        this.runOnUiThread(Timer_Tick);
    }


    private Runnable Timer_Tick = new Runnable() {
        public void run() {

            System.out.println("run");
            refresh();
        }
    };

    public void refresh(){
        if(inGroup) {
            //sendSignal();

            boolean rdy = requestManager.update(myAdrMac,detected);
            //detected.clear();
            ////data.list = requestManager.list;
            updateMenu();
            showDataOnMap();
        }
    }

    /*public void sendSignal(){
        for(Bluetooth bl : detected){

            requestManager.requestSignal(bl.deviceName,bl.hardwareAdress,bl.signal);
            System.out.println("sent");
        }

        detected.clear();
        System.out.println("resquestSinal clearing!");
    }*/

    // Create a BroadcastReceiver for ACTION_FOUND.
    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                // Discovery has found a device. Get the BluetoothDevice
                // object and its info from the Intent.
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                String deviceName = device.getName();
                String deviceHardwareAddress = device.getAddress(); // MAC address
                int bs = intent.getShortExtra(device.EXTRA_RSSI,Short.MIN_VALUE);

                System.out.println(deviceName +" ||| " + deviceHardwareAddress + " ||| " + bs);
                detected.add(new Bluetooth(deviceName,deviceHardwareAddress,""+bs));
                //requestManager.requestSignal(deviceName,deviceHardwareAddress,""+bs);

                if(bs>-50){ // device addable to group
                    Popup.showText(deviceName +" ||| " + deviceHardwareAddress);
                }



            }
            else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action))
            {
                mBtAdapter.startDiscovery();
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReceiver);
    }

    public void showDataOnMap(){

        map.clear();
        int index = 0;
        for(MapElement i : requestManager.list) {
            Marker marker;
            if(i.getClass() == Admin.class) {
                Admin tmp = (Admin) requestManager.list.get(index);
                marker = map.addMarker(new MarkerOptions().position(tmp.latLng).title(tmp.nom).icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_acc)));

            }
            else if(i.getClass() == Member.class) {
                Member tmp = (Member) requestManager.list.get(index);
                marker = map.addMarker(new MarkerOptions().position(tmp.latLng).title(tmp.nom).icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_acc)));

            }
            else if(i.getClass() == Ble.class) {
                Ble tmp = (Ble) requestManager.list.get(index);
                marker = map.addMarker(new MarkerOptions().position(tmp.latLng).title(tmp.nom).icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_dev)));

            }
            else if(i.getClass() == Ping.class) {
                Ping tmp = (Ping) requestManager.list.get(index);
                marker = map.addMarker(new MarkerOptions().position(tmp.latLng).title(tmp.time));

            }

            /*if(showTiquette){
                if(marker != null) {
                    marker.showInfoWindow();
                }
            }*/
            index++;
        }
    }

    public void updateMenu(){
        Menu menu = navigationView.getMenu();
        menu.clear();

        TextView bar_nam = (TextView) findViewById(R.id.bar_name);
        if(bar_nam != null) {
            bar_nam.setText(myName);
        }


        int ind = 0;
        for(MapElement i : requestManager.list) {
            if(i.getClass() == Admin.class) {
                Admin tmp = (Admin) requestManager.list.get(ind);
                menu.add(R.id.groupAcc, ind, Menu.NONE, tmp.nom).setIcon(R.drawable.ic_holo_acc);
            }
            else if(i.getClass() == Member.class) {
                Member tmp = (Member) requestManager.list.get(ind);
                menu.add(R.id.groupDev, ind, Menu.NONE, tmp.nom).setIcon(R.drawable.ic_holo_acc);
            }
            else if(i.getClass() == Ble.class) {
                Ble tmp = (Ble) requestManager.list.get(ind);
                menu.add(R.id.groupDev, ind, Menu.NONE, tmp.nom).setIcon(R.drawable.ic_holo_dev);
            }
            else if(i.getClass() == Ping.class) {
                Ping tmp = (Ping) requestManager.list.get(ind);
                menu.add(R.id.groupDev, ind, Menu.NONE, tmp.time).setIcon(R.drawable.ic_clock);
            }
            ind++;
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if(requestManager.list.get(id).getClass() == Admin.class) {
            Admin tmp = (Admin) requestManager.list.get(id);
            // move camera to selected item
            map.moveCamera(CameraUpdateFactory.newLatLng(tmp.latLng));
            //map.animateCamera(CameraUpdateFactory.zoomTo(16));
        }
        else if(requestManager.list.get(id).getClass() == Member.class) {
            Member tmp = (Member) requestManager.list.get(id);
            // move camera to selected item
            map.moveCamera(CameraUpdateFactory.newLatLng(tmp.latLng));
            //map.animateCamera(CameraUpdateFactory.zoomTo(16));
        }
        else if(requestManager.list.get(id).getClass() == Ble.class) {
            Ble tmp = (Ble) requestManager.list.get(id);
            // move camera to selected item
            map.moveCamera(CameraUpdateFactory.newLatLng(tmp.latLng));
            //map.animateCamera(CameraUpdateFactory.zoomTo(16));
        }
        else if(requestManager.list.get(id).getClass() == Ping.class) {
            Ping tmp = (Ping) requestManager.list.get(id);
            // move camera to selected item
            map.moveCamera(CameraUpdateFactory.newLatLng(tmp.latLng));
            //map.animateCamera(CameraUpdateFactory.zoomTo(16));
        }





        /*if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void showTitle(View v){

        this.showTiquette = !this.showTiquette;

    }

}
