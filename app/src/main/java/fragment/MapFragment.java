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
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.List;
import java.util.Map;

import modelclasses.City;
import modelclasses.MapSetup;
import modelclasses.Player;
import modelclasses.Route;

public class MapFragment extends SupportMapFragment implements OnMapReadyCallback, IMapView {
    private GoogleMap map;
    private Map<Route, Polyline> routePolylineMap;


    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View view = super.onCreateView(layoutInflater, viewGroup, bundle);
        this.getMapAsync(this);
        return view;
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.getUiSettings().setZoomControlsEnabled(false);
        map.getUiSettings().setAllGesturesEnabled(false);
        map.getUiSettings().setMapToolbarEnabled(false);
        initializeMap(new MapSetup());
    }


    @Override
    public void updateClaimedRoute(Player player, Route route) {

    }

    @Override
    public void highlightRoutes(List<Route> routes) {

    }

    @Override
    public void initializeMap(MapSetup mapSetup) {
        LatLng northeast = new LatLng(mapSetup.getNorthBound(), mapSetup.getEastBound());
        LatLng southwest = new LatLng(mapSetup.getSouthBound(), mapSetup.getWestBound());
        final LatLngBounds bounds = new LatLngBounds(southwest, northeast);

        map.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
                map.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 0));
                map.setLatLngBoundsForCameraTarget(bounds);
            }
        });
    }

    private void drawPolylineForRoute(Route route) {
        Polyline polyline = map.addPolyline(new PolylineOptions()
                .clickable(true)
                .add(cityToLatLng(route.getOrigin()))
                .add(cityToLatLng(route.getDestination())));
        polyline.setTag(route);
    }

    private void drawMarkersForRoutes() {

    }

    private LatLng cityToLatLng(City city) {
        return new LatLng(city.getLatitude(), city.getLongitude());
    }

}
