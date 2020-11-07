package android.teste.locations;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Gravity;
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

import java.text.DecimalFormat;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback, LocationListener {

    private GoogleMap map;
    public LocationManager lm;

    public Marker usr;
    public Location currentLocation;

    private final LatLng ITAGUARA = new LatLng(-20.393079, -44.491253);
    private final LatLng VICOSA = new LatLng(-20.754070, -42.879845);
    private final LatLng DPI = new LatLng(-20.764801, -42.868462);

    public Location vicosa = new Location(Context.LOCATION_SERVICE); // usado para calcular a distancia da posicao atual até o endereço de viçosa

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_activity);

        ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMapAsync(this);

        vicosa.setLatitude(VICOSA.latitude);
        vicosa.setLongitude(VICOSA.longitude);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map  = googleMap;
        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        map.setBuildingsEnabled(true);

        lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

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
        t.setGravity(Gravity.BOTTOM,0,220);
        t.show();

        requestLocationPermissionIfNeeded();
    }

    public void onItaClick(View v){
        if (usr != null) {// remove o marcador da posicao atual se existir
            usr.remove();
        }
        map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        CameraUpdate upd = CameraUpdateFactory.newLatLngZoom(ITAGUARA, 18);
        map.animateCamera(upd);
    }

    public void onVicClick(View v){
        if (usr != null) {// remove o marcador da posicao atual se existir
            usr.remove();
        }
        map.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        CameraUpdate upd = CameraUpdateFactory.newLatLngZoom(VICOSA, 18);
        map.animateCamera(upd);
    }

    public void onDpiClick(View v){
        if (usr != null) {// remove o marcador da posicao atual se existir
            usr.remove();
        }
        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        CameraUpdate upd = CameraUpdateFactory.newLatLngZoom(DPI, 17);
        map.animateCamera(upd);
    }

    public void onLocClick(View v){
        if(checkPermission()){
            LatLng newLoc = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());

            if (usr != null) {
                usr.remove();
            }

            usr = map.addMarker(new MarkerOptions().position(newLoc)
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                    .title("Localização atual"));

            map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
            CameraUpdate upd = CameraUpdateFactory.newLatLngZoom(newLoc, 19);
            map.animateCamera(upd);

            DecimalFormat df = new DecimalFormat("0.##");
            Toast t = Toast.makeText(getBaseContext(), "Você está a " + df.format(currentLocation.distanceTo(vicosa)) + " m do endereço em Viçosa",Toast.LENGTH_LONG);
            TextView tv = (TextView) t.getView().findViewById(android.R.id.message);
            tv.setBackgroundColor(getResources().getColor(android.R.color.transparent));
            tv.setTextColor(getResources().getColor(R.color.colorBackground));
            t.setGravity(Gravity.BOTTOM,0,220);
            t.show();

        } else {
            Toast t = Toast.makeText(getBaseContext(), "Você precisa ir às configurações do android e permitir que o aplicativo acesse sua localização!",Toast.LENGTH_LONG);
            TextView tv = (TextView) t.getView().findViewById(android.R.id.message);
            tv.setBackgroundColor(getResources().getColor(android.R.color.transparent));
            tv.setTextColor(getResources().getColor(R.color.colorBackground));
            t.setGravity(Gravity.BOTTOM,0,220);
            t.show();
        }
    }

    public void requestLocationPermissionIfNeeded(){
        if(!checkPermission()){ // verifica se as permissoes ainda nao foram dadas
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1);// pede as permissoes e chama onRequestPermissionsResult como callback
        } else {
            //map.setMyLocationEnabled(true);
            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 0, (LocationListener) this);// configura para atualizar a localização do usuário a cada 5 segundos
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults){
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) { // se o usuario deu as permissoes
                    if (checkPermission()) {
                        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 0, (LocationListener) this);// configura para atualizar a localização do usuário a cada 5 segundos;
                    }
                } else {
                    //
                }
            }
        }
    }

    public boolean checkPermission() {
        int FirstPermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION);
        int SecondPermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION);

        return FirstPermissionResult == PackageManager.PERMISSION_GRANTED &&
                SecondPermissionResult == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void onLocationChanged(Location location) {
        currentLocation = location;
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
