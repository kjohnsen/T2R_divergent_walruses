package fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.graphics.ColorUtils;
import android.widget.Toast;

import com.example.emilyhales.tickettoride.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Dash;
import com.google.android.gms.maps.model.Gap;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PatternItem;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;

import activity.GameEndView;
import model.ClientModel;
import modelclasses.City;
import modelclasses.MapSetup;
import modelclasses.Player;
import modelclasses.PlayerColor;
import modelclasses.Route;
import modelclasses.TrainCardColor;
import presenter.map.IMapPresenter;
import presenter.map.MapPresenter;
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
    private static final int POLYLINE_TRANSLUCENT_OPACITY = 60;
    private static final int POLYLINE_OPAQUE_OPACITY = 160;


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
//        map.getUiSettings().setAllGesturesEnabled(false);
        map.getUiSettings().setMapToolbarEnabled(false);


        initializeMap(new MapSetup());
    }


    @Override
    public void updateRoute(Route route) {
        Polyline polyline = routePolylineMap.get(route);
        Player player = route.getPlayer();
        if (player != null) {
            polyline.setWidth(POLYLINE_CLAIMED_WIDTH);
            polyline.setColor(getColorWithOpacity(route.getPlayer().getPlayerColor(), POLYLINE_OPAQUE_OPACITY));
        } else {
            polyline.setWidth(POLYLINE_UNCLAIMED_WIDTH);
        }
    }

    @Override
    public void setRouteEmphasized(Route route, boolean enabled) {
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
    public void emphasizeSelectRoutes(ArrayList<Route> routes) {
        for (Route r : this.routePolylineMap.keySet()) {
            setRouteEmphasized(r, false);
            routePolylineMap.get(r).setClickable(false);
        }
        for (Route r : routes) {
            setRouteEmphasized(r, true);
            routePolylineMap.get(r).setClickable(true);
        }
    }

    @Override
    public void resetRouteEmphasis() {
        for (Route r : this.routePolylineMap.keySet()) {
            setRouteEmphasized(r, true);
        }
    }

    @Override
    public void displayMessage(String message) {
        Toast.makeText(this.getContext(), message, Toast.LENGTH_LONG).show();
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
                map.setMinZoomPreference(map.getCameraPosition().zoom);
                map.setMaxZoomPreference(6.0f);
            }
        });

        map.setMapStyle(MapStyleOptions.loadRawResourceStyle(this.getContext(), R.raw.map_style));

//        // draw polylines by groups (i.e., either one or two between the same two cities)
//        for (Map.Entry<City, DefaultHashMap<City, ArrayList<Route>>> entry1 : mapSetup.getCityConnectionMap().entrySet()) {
//            City cityOne = entry1.getKey();
//            Map<City, ArrayList<Route>> neighborCityRouteMap = entry1.getValue();
//            for (Map.Entry<City, ArrayList<Route>> entry2 : neighborCityRouteMap.entrySet()) {
//                City cityTwo = entry2.getKey();
//                ArrayList<Route> routes = entry2.getValue();
//                drawPolylinesForParallelRoutes(routes);
//            }
//        }

//        Set<Set<City>> labeledDistances = new HashSet<>()
        for (Route route : mapSetup.getRoutes()) {
            drawPolylineForRoute(route);
            addLengthLabelForRoute(route);
        }
        map.setOnPolylineClickListener(this);
        drawMarkersForRoutes(mapSetup.getRoutes());
    }

    private void addLengthLabelForRoute(Route route) {
        map.addGroundOverlay(new GroundOverlayOptions()
                .image(bitmapDescriptorForRouteLength(route.getLength()))
                .position(midpoint(route.getOrigin(), route.getDestination()), 100000f)
                .zIndex(100));
    }

    private BitmapDescriptor bitmapDescriptorForRouteLength(int length) {
        int resource;
        switch (length) {
            case 1: resource = R.drawable.ic_one; break;
            case 2: resource = R.drawable.ic_two; break;
            case 3: resource = R.drawable.ic_three; break;
            case 4: resource = R.drawable.ic_four; break;
            case 5: resource = R.drawable.ic_five; break;
            case 6: resource = R.drawable.ic_six; break;
            default: resource = R.drawable.baseline_location_on_black_18;
        }
        return generateBitmapDescriptorFromRes(this.getContext(), resource);
    }

    private static BitmapDescriptor generateBitmapDescriptorFromRes(
            Context context, int resId) {
        Drawable drawable = ContextCompat.getDrawable(context, resId);
        drawable.setBounds(
                0,
                0,
                drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight());
        Bitmap bitmap = Bitmap.createBitmap(
                drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

    private void drawPolylinesForParallelRoutes(ArrayList<Route> routes) {
        // if already done, return
        if (routePolylineMap.keySet().contains(routes.get(0))) {
            return;
        }

        int numRoutes = routes.size();
        if (numRoutes == 1) drawPolylineForRoute(routes.get(0));
        else {
            assert numRoutes == 2;

        }
    }

    private int getColorWithOpacity(int color, int opacity) {
        return ColorUtils.setAlphaComponent(color, opacity);
    }

    private int getColorWithOpacity(TrainCardColor trainCardColor, int opacity) {
        int color = TrainColorConverter.convertTrainColor(trainCardColor, this.getContext());
        return getColorWithOpacity(color, opacity);
    }

    private int getColorWithOpacity(PlayerColor playerColor, int opacity) {
        int color = PlayerColorConverter.convertPlayerColor(playerColor, this.getContext());
        return getColorWithOpacity(color, opacity);
    }

    private void drawPLForRouteAtCoords(Route route, LatLng ll1, LatLng ll2) {
        Polyline polyline = map.addPolyline(new PolylineOptions()
                .clickable(true)
                .add(ll1)
                .add(ll2));
        polyline.setWidth(POLYLINE_UNCLAIMED_WIDTH);
        polyline.setColor(getColorWithOpacity(route.getColor(), POLYLINE_OPAQUE_OPACITY));
        List<PatternItem> pattern = Arrays.<PatternItem>asList(
                new Dash(80), new Gap(10));
        polyline.setPattern(pattern);

        polyline.setTag(route);
        routePolylineMap.put(route, polyline);
    }

    private void drawPolylineForRoute(Route route) {
        LatLng ll1 = cityToOffsetLatLng(route.getOrigin());
        LatLng ll2 = cityToOffsetLatLng(route.getDestination());
        drawPLForRouteAtCoords(route, ll1, ll2);
    }


    private void drawMarkersForRoutes(List<Route> routes) {
        HashSet<City> cities = new HashSet<>();
        for (Route route : routes) {
            cities.add(route.getDestination());
            cities.add(route.getOrigin());
        }
        for (City city : cities) {
            Marker marker = map.addMarker(new MarkerOptions()
                    .position(cityToLatLng(city))
                    .title(city.getName())
                    .alpha(0.8f)
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.baseline_location_on_black_18)));
            marker.setInfoWindowAnchor(0.5f, 1f);
        }

        map.setOnMarkerClickListener( new GoogleMap.OnMarkerClickListener()
        {
            @Override
            public boolean onMarkerClick ( Marker marker )
            {
                marker.showInfoWindow();
                // do nothing
                return true;
            }
        });
    }

    private LatLng cityToLatLng(City city) {
        return new LatLng(city.getLatitude(), city.getLongitude());
    }

    private LatLng cityToOffsetLatLng(City city) {
        final double RADIUS = 0.5;
        double lat = city.getLatitude() + randDouble(-RADIUS, RADIUS);
        double lng = city.getLongitude() + randDouble(-RADIUS, RADIUS);
        return new LatLng(lat, lng);
    }

    private LatLng midpoint(City one, City two) {
        double lat = (one.getLatitude() + two.getLatitude()) / 2;
        double lng = (one.getLongitude() + two.getLongitude()) / 2;
        return new LatLng(lat, lng);
    }

    private double randDouble(double rangeMin, double rangeMax) {
        Random r = new Random();
        return rangeMin + (rangeMax - rangeMin) * r.nextDouble();
    }

    @Override
    public void moveToEndGame() {
        Intent intent = new Intent(getActivity(), GameEndView.class);
        startActivity(intent);
    }

    @Override
    public void onPolylineClick(Polyline polyline) {
        Route route = (Route) polyline.getTag();
//        route.setPlayer(new Player("player", PlayerColor.BLUE));
//        updateRoute(route);
//        setRouteEnabled(route, false);
        ClientModel cm = ClientModel.getInstance();
        String username = cm.getCurrentUser().getUsername();
        Player player = cm.getCurrentGame().getPlayer(username);
        player.addRoute(route);
        route.setPlayer(player);
        cm.notifyObservers(route);
    }
}
