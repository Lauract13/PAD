package sfinder.app;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;

import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FiltroActivity {



    public void filtrar(View view, GoogleMap googleMap){

        boolean checked = ((CheckBox) view).isChecked();
        final GoogleMap mMap;

        mMap = googleMap;
        DatabaseReference dbSFinder = FirebaseDatabase.getInstance().getReference();

        mMap.clear();

        switch(view.getId()) {
            case R.id.cbFarmacias:
                if (checked) {
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

                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });


                }

                break;
            case R.id.cbTiendas:
                if (checked){
                    dbSFinder.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            DataSnapshot dbTiendas = dataSnapshot.child("Tiendas");

                            Iterable<DataSnapshot> childrenT = dbTiendas.getChildren();

                            for (DataSnapshot c : childrenT) {
                                Double lat = (double) c.child("Latitud").getValue();
                                Double lon = (double) c.child("Longitud").getValue();
                                String tit = c.child("Descripcion").getValue().toString();

                                LatLng loc = new LatLng(lat,lon);
                                mMap.addMarker(new MarkerOptions().position(loc).title(tit).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN)));


                            }


                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                }

                break;
            case R.id.cbTalleres:
                if (checked){
                    dbSFinder.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

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

                break;
        }
    }
}

