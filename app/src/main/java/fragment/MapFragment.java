package fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapFragment extends SupportMapFragment implements OnMapReadyCallback {
    private GoogleMap map;

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View view = super.onCreateView(layoutInflater, viewGroup, bundle);
        this.getMapAsync(this);
        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        LatLng saltLake = new LatLng(41, -112);
        map.getUiSettings().setZoomControlsEnabled(false);
        map.getUiSettings().setAllGesturesEnabled(false);
        map.addMarker(new MarkerOptions().position(saltLake).title("Salt Lake City!!!!"));
        map.moveCamera(CameraUpdateFactory.newLatLng(saltLake));
    }

}
