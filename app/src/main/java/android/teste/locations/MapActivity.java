package android.teste.locations;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback, LocationListener {

    private GoogleMap map;
    public LocationManager lm;

    public Marker usr;
    public Location currentLocation;

    private final LatLng ITAGUARA = new LatLng(-20.393079, -44.491253);
    private final LatLng VICOSA = new LatLng(-20.754070, -42.879845);
    private final LatLng DPI = new LatLng(-20.764801, -42.868462);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_activity);

        ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        ActivityCompat.requestPermissions(this,new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        map  = googleMap;
        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        map.setBuildingsEnabled(true);
        requestLocationPermission();

        map.addMarker(new MarkerOptions().position(ITAGUARA).title("Minha casa em Itaguara"));
        map.addMarker(new MarkerOptions().position(VICOSA).title("Minha casa em Viçosa"));
        map.addMarker(new MarkerOptions().position(DPI).title("DPI - UFV"));
        Bundle params = getIntent().getExtras();

        if(params.getString("local").equals("ita")){
            CameraUpdate upd = CameraUpdateFactory.newLatLngZoom(ITAGUARA, 18);
            map.animateCamera(upd);
        }else if(params.getString("local").equals("vic")) {
            CameraUpdate upd = CameraUpdateFactory.newLatLngZoom(VICOSA, 18);
            map.animateCamera(upd);
        }else if(params.getString("local").equals("dpi")) {
            CameraUpdate upd = CameraUpdateFactory.newLatLngZoom(DPI, 17);
            map.animateCamera(upd);
        }

        Toast t = Toast.makeText(getBaseContext(), params.getString("toastMsg"),Toast.LENGTH_SHORT);
        TextView tv = (TextView) t.getView().findViewById(android.R.id.message);
        tv.setBackgroundColor(getResources().getColor(android.R.color.transparent));
        tv.setTextColor(getResources().getColor(R.color.colorBackground));
        t.show();
    }

    public void onItaClick(View v){
        map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        CameraUpdate upd = CameraUpdateFactory.newLatLngZoom(ITAGUARA, 18);
        map.animateCamera(upd);
    }

    public void onVicClick(View v){
        map.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        CameraUpdate upd = CameraUpdateFactory.newLatLngZoom(VICOSA, 18);
        map.animateCamera(upd);
    }

    public void onDpiClick(View v){
        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        CameraUpdate upd = CameraUpdateFactory.newLatLngZoom(DPI, 17);
        map.animateCamera(upd);
    }

    public void onLocClick(View v){
        Location l = lm.getLastKnownLocation(lm.GPS_PROVIDER);
        LatLng newLoc = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());

        if(usr != null) {
            usr.remove();
        }

        usr = map.addMarker(new MarkerOptions().position(newLoc)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                .title("Localização atual"));
    }

    public void requestLocationPermission(){
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                ||ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
        } else {
            map.setMyLocationEnabled(true);
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        currentLocation = location;
        System.out.println(location.getLatitude());
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
