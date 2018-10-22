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
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

import modelclasses.MapSetup;

public class MapFragment extends SupportMapFragment implements OnMapReadyCallback, IMapView {
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

    private static LatLngBounds getBounds() {
        return new LatLngBounds(new LatLng(45.67, -68.6), new LatLng(26.9, -124));
    }


    @Override
    public void initializeMap(MapSetup mapSetup) {
        LatLng northeast = new LatLng(mapSetup.getNorthBound(), mapSetup.getEastBound());
        LatLng southwest = new LatLng(mapSetup.getSouthBound(), mapSetup.getWestBound());
        LatLngBounds bounds = new LatLngBounds(northeast, southwest);
    }
}
