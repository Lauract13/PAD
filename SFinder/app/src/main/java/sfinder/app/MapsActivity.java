package sfinder.app;

import android.Manifest;
import android.app.Service;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.google.android.gms.common.api.GoogleApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.FusedLocationProviderClient;

import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.LocationRequest;

import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DatabaseError;




import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.tasks.OnSuccessListener;






import com.google.android.gms.common.api.GoogleApiClient;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, OnConnectionFailedListener,ConnectionCallbacks,LocationListener {

    GoogleMap mMap;
    GoogleApiClient mGoogleApiClient;
    LocationRequest mLocationRequest;
    Location mLastLocation;
    FusedLocationProviderClient mFusedLocationClient;
    Marker mCurrLocationMarker;
    private ValueEventListener eventListener;

    private Circle mCircle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        addMarkers(mMap);

    }




    @Override
    public void onLocationChanged(Location location)
    {

        mLastLocation = location;

        if (mCurrLocationMarker != null) {
            mCurrLocationMarker.remove();
        }

        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("Current Position");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
        mCurrLocationMarker = mMap.addMarker(markerOptions);



        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(11));


}
    @Override
    public void onConnectionSuspended(int i) {}


    public void onSuccess(int result){}

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {}
    protected synchronized void buildGoogleApiClient(){

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */,
                        this /* OnConnectionFailedListener */)
                .addConnectionCallbacks(this)
                .addApi(LocationServices.API)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .build();
        mGoogleApiClient.connect();
    }



    public void onConnected(Bundle connectionHint) {

       mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mFusedLocationClient.getLastLocation()
                    .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if (location != null) {
                                LatLng myLocation = new LatLng(location.getLatitude(),location.getLongitude());
                                mMap.addMarker(new MarkerOptions().position(myLocation).title("Estas aquí"));

                            }
                        }
                    });


        }
    }

    public void cerrarSesion(View v)
    {
        // Llamar a funcion de SA para cerrar sesion.
        // mAuth.signOut();
    }

    public void visitPerfil(View v)
    {
        Intent intent = new Intent(MapsActivity.this, PerfilActivity.class);
        MapsActivity.this.startActivity(intent);
    }


    public void addMarkers(GoogleMap googleMap){
        mMap = googleMap;

       DatabaseReference dbSFinder = FirebaseDatabase.getInstance().getReference();


        dbSFinder.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                DataSnapshot dbFarmacias = dataSnapshot.child("Farmacias");

                Iterable<DataSnapshot> childrenF = dbFarmacias.getChildren();

                for (DataSnapshot c : childrenF) {
                    Double lat = (double) c.child("Latitud").getValue();
                    Double lon = (double) c.child("Longitud").getValue();
                    String tit = c.child("Descripcion").getValue().toString();

                    LatLng loc = new LatLng(lat,lon);
                    mMap.addMarker(new MarkerOptions().position(loc).title(tit).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));


                }

                DataSnapshot dbTiendas = dataSnapshot.child("Tiendas");

                Iterable<DataSnapshot> childrenT = dbTiendas.getChildren();

                for (DataSnapshot c : childrenT) {
                    Double lat = (double) c.child("Latitud").getValue();
                    Double lon = (double) c.child("Longitud").getValue();
                    String tit = c.child("Descripcion").getValue().toString();

                    LatLng loc = new LatLng(lat,lon);
                    mMap.addMarker(new MarkerOptions().position(loc).title(tit).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN)));


                }

                DataSnapshot dbTalleres = dataSnapshot.child("Talleres");

                Iterable<DataSnapshot> childrenTa = dbTalleres.getChildren();

                for (DataSnapshot c : childrenTa) {
                    Double lat = (double) c.child("Latitud").getValue();
                    Double lon = (double) c.child("Longitud").getValue();
                    String tit = c.child("Descripcion").getValue().toString();

                    LatLng loc = new LatLng(lat,lon);
                    mMap.addMarker(new MarkerOptions().position(loc).title(tit).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));


                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });





    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if(ContextCompat.checkSelfPermission(this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                buildGoogleApiClient();
                mMap.setMyLocationEnabled(true);

            }else{
                checkLocationPermission();
            }
        }else{
            buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);
        }


        mFusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {

                        if (location != null) {

                            LatLng myLocation = new LatLng(location.getLatitude(),location.getLongitude());
                            mMap.addMarker(new MarkerOptions().position(myLocation).title("Estas aquí"));
                        }
                    }
                });



    }

    private void getCurrentLocation() {
        mMap.clear();
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        FusedLocationProviderClient location = LocationServices.getFusedLocationProviderClient(this);
        if (location != null) {

            LatLng current = new LatLng(location.getLastLocation().getResult().getLongitude(),location.getLastLocation().getResult().getLatitude());
            mMap.addMarker(new MarkerOptions().position(current).title("Estas aqui"));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(current));


        }
    }
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    private void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {


            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                new AlertDialog.Builder(this)
                        .setTitle("Location Permission Needed")
                        .setMessage("This app needs the Location permission, please accept to use location functionality")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                ActivityCompat.requestPermissions(MapsActivity.this,
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION );
                            }
                        })
                        .create()
                        .show();


            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION );
            }
        }
    }
}
