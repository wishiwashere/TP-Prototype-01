package com.pigottlaura.www.wishiwashere;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.OnStreetViewPanoramaReadyCallback;
import com.google.android.gms.maps.StreetViewPanorama;
import com.google.android.gms.maps.StreetViewPanoramaFragment;
import com.google.android.gms.maps.model.StreetViewPanoramaCamera;

public class MapsActivity extends FragmentActivity implements OnStreetViewPanoramaReadyCallback {



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        StreetViewPanoramaFragment streetViewPanoramaFragment =
                (StreetViewPanoramaFragment) getFragmentManager()
                        .findFragmentById(R.id.streetviewpanorama);

        streetViewPanoramaFragment.getStreetViewPanoramaAsync(this);
    }

    @Override
    public void onStreetViewPanoramaReady(StreetViewPanorama panorama) {
        // Creating a variable to store the latitude and longitude of the
        // location we would like to request a StreetView Panorama of
        LatLng timesSquare = new LatLng(40.7608309,-73.9843232);

        // Setting the location we would like to visit, by passing in the
        // latitude and longitude of it's location (as set in the varaible above
        panorama.setPosition(timesSquare);

        /* -------------------- OPTIONAL -------------------- */
        // The followiing settings are optional, but including them so that
        // we know what parametres we can effect if we chose to

        // Setting whether the user can move to another photosphere in the area
        // i.e. can they navigate outside of the current image. Defaulting this
        // to true, so that user's can "walk" down the street
        panorama.setUserNavigationEnabled(true);;

        // Setting whether the user can move around within the current photosphere
        // i.e. if they can move the image to look around them. Defaulting this
        // to true, so that users can look around them
        panorama.setPanningGesturesEnabled(true);

        // Setting whether the user can zoom in and out of the current photosphere.
        // Defaulting this to true, so users can control the zoom of the current
        // image
        panorama.setZoomGesturesEnabled(true);

        // Setting whether the street names will be visible within the image or not.
        // Setting this to false, so that images will not be disrupted by this text
        panorama.setStreetNamesEnabled(false);

        // Setting the zoom of the camera. Currently I do not quite know the potential
        // values that can be passed into this variable, so I am defaulting it to it's
        // current zoom
        float cameraZoom = panorama.getPanoramaCamera().zoom;

        // Setting the tilt of the camera i.e. which direction on the y axis the camera is
        // looking (-90 to 90). Down to the ground is -90, straight ahead is 0 and 90 is
        // looking straight up at the sky. Getting the current tilt of the camera,
        // and then adding 50 on to it. (Note - 0 will not always result in a level view
        // with the horizon, as the initial tilt depends on the angle the image was taken
        // at. This is an inclusive range, and so -90 and 90 are valid)
        float cameraTilt = panorama.getPanoramaCamera().tilt + 50;

        // Setting the bearing of the camera, i.e. which direction on the x axis the camera
        // is looking (0 to 360). North is 0, east is 90, south is 180, west is 270. Getting
        // the current bearing of the camera, and adding 180 onto it. *Note - this is an
        // exclusive range, so 0 and 360 are not valid)
        float cameraBearing = panorama.getPanoramaCamera().bearing + 180;

        // Creating a new Street View Panorama Camera, using the Builder constructor of
        // the StreetViewPanoramaCamera class. Setting the zoom, tilt and bearing of the
        // camera to the values specified above. This camera will be the user's initial
        // perspective of the photosphere they are looking at. Passing in the original
        // camera. NOT CURRENTLY WORKING
        StreetViewPanoramaCamera newCamera = new StreetViewPanoramaCamera.Builder()
                .zoom(cameraZoom)
                .tilt(cameraTilt)
                .bearing(cameraBearing)
                .build();

        // Switching to the new camera view, with a duration of 0 e.g. the switch will
        // occur instantly. NOT CURRENTLY WORKING
        panorama.animateTo(newCamera, 0);
    }



    /*
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    */
    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    /*
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
    */
}
