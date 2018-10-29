package fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.graphics.ColorUtils;

import com.example.emilyhales.tickettoride.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import modelclasses.City;
import modelclasses.MapSetup;
import modelclasses.Player;
import modelclasses.Route;
import presenter.IMapPresenter;
import presenter.MapPresenter;
import util.PlayerColorConverter;
import util.TrainColorConverter;

public class MapFragment extends SupportMapFragment implements
        OnMapReadyCallback,
        IMapView,
        GoogleMap.OnPolylineClickListener
{
    private GoogleMap map;
    private Map<Route, Polyline> routePolylineMap = new HashMap<>();
    private IMapPresenter presenter;
    private static final int POLYLINE_UNCLAIMED_WIDTH = 12;
    private static final int POLYLINE_CLAIMED_WIDTH = 24;
    private static final int POLYLINE_TRANSLUCENT_OPACITY = 140;
    private static final int POLYLINE_OPAQUE_OPACITY = 220;


    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View view = super.onCreateView(layoutInflater, viewGroup, bundle);
        this.getMapAsync(this);
        presenter = new MapPresenter(this);
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
    public void updateRoute(Route route) {
        Polyline polyline = routePolylineMap.get(route);
        Player player = route.getPlayer();
        if (player != null) {
            polyline.setWidth(POLYLINE_CLAIMED_WIDTH);
            polyline.setColor(PlayerColorConverter.convertPlayerColor(player.getPlayerColor()));
        } else {
            polyline.setWidth(POLYLINE_UNCLAIMED_WIDTH);
        }
    }

    @Override
    public void setRouteEnabled(Route route, boolean enabled) {
        Polyline polyline = routePolylineMap.get(route);
        if (enabled) {
            polyline.setColor(ColorUtils.setAlphaComponent(polyline.getColor(), POLYLINE_OPAQUE_OPACITY));
            polyline.setClickable(true);
        } else {
            polyline.setColor(ColorUtils.setAlphaComponent(polyline.getColor(), POLYLINE_TRANSLUCENT_OPACITY));
            polyline.setClickable(false);
        }
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

        for (Route route : mapSetup.getRoutes()) {
            drawPolylineForRoute(route);
        }
        drawMarkersForRoutes(mapSetup.getRoutes());
    }

    private void drawPolylineForRoute(Route route) {
        Polyline polyline = map.addPolyline(new PolylineOptions()
                .clickable(true)
                .add(cityToLatLng(route.getOrigin()))
                .add(cityToLatLng(route.getDestination())));
        polyline.setWidth(POLYLINE_UNCLAIMED_WIDTH);
        polyline.setColor(TrainColorConverter.convertTrainColor(route.getColor()));

        polyline.setTag(route);
        routePolylineMap.put(route, polyline);
    }

    private void drawMarkersForRoutes(List<Route> routes) {
        HashSet<City> cities = new HashSet<>();
        for (Route route : routes) {
            cities.add(route.getDestination());
            cities.add(route.getOrigin());
        }
        for (City city : cities) {
            map.addMarker(new MarkerOptions()
                    .position(cityToLatLng(city))
                    .title(city.getName()));
        }
    }

    private LatLng cityToLatLng(City city) {
        return new LatLng(city.getLatitude(), city.getLongitude());
    }

    @Override
    public void onPolylineClick(Polyline polyline) {

    }
}
