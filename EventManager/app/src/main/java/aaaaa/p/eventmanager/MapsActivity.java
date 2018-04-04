package aaaaa.p.eventmanager;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import aaaaa.p.eventmanager.BackEnd.CDevice;
import aaaaa.p.eventmanager.BackEnd.CGeoUpdater;
import aaaaa.p.eventmanager.BackEnd.Clocation;
import aaaaa.p.eventmanager.R;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    private CGeoUpdater updater;
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        updater= new CGeoUpdater(this);
        updater.setDisponible(false);
        updater.Start();
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
       // LatLng sydney = new LatLng(-121, 151);
        //mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));

    }
    public void RenderPoints()
    {
        CDevice x= new CDevice();
        CDevice y= new CDevice();
        x.setClientMac("dc:85:de:ee:9c:e9");
        y.setClientMac("9c:5c:f9:6f:c3:93");
        ArrayList<CDevice> lista= new ArrayList<CDevice>();
        lista.add(x);
        lista.add(y);
        ArrayList<Clocation> puntos=updater.SearchDevices(lista);
        try {
            for(int cont=0;cont<puntos.size();cont++)
            {
                Clocation cur= puntos.get(cont);
                LatLng ac=new LatLng(cur.getLat(),cur.getLng());
                mMap.addMarker(new MarkerOptions().position(ac).title("punto"+(cont+1)));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(ac));
            }
            Thread.sleep(200);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
