package android.teste.locations;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback{

    private GoogleMap map;
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
        map  = googleMap;
        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
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
}
