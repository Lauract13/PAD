package sfinder.app.Integracion;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import sfinder.app.Negocio.Marcador;

public class DataAccessObjectImp extends DataAccessObject {

    private List<MarkerOptions> talleres;
    private List<MarkerOptions> farmacias;
    private List<MarkerOptions> tiendas;

    public DataAccessObjectImp()
    {
        super();
        talleres = new ArrayList<>();
        farmacias = new ArrayList<>();
        tiendas = new ArrayList<>();
        getTalleres();
        getFarmacias();
        getTiendas();
    }

    public List<MarkerOptions> getFiltros(Marcador.TipoMarcador tipo)
    {
        switch(tipo)
        {
            case TALLER:
                return talleres;
            case TIENDA:
                return tiendas;
            case FARMACIA:
                return farmacias;
            default:
                return null;
        }
    }

    private void getFarmacias() {

        DatabaseReference dbSFinder = FirebaseDatabase.getInstance().getReference();

        dbSFinder.child("Farmacias").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dbFarmacias) {

                Iterable<DataSnapshot> childrenF = dbFarmacias.getChildren();

                for (DataSnapshot c : childrenF) {
                    Double lat = (double) c.child("Latitud").getValue();
                    Double lon = (double) c.child("Longitud").getValue();
                    String tit = c.child("Descripcion").getValue().toString();

                    LatLng loc = new LatLng(lat, lon);

                    farmacias.add(new MarkerOptions().position(loc).title(tit));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void getTalleres() {

        DatabaseReference dbSFinder = FirebaseDatabase.getInstance().getReference();

        dbSFinder.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                DataSnapshot dbFarmacias = dataSnapshot.child("Talleres");

                Iterable<DataSnapshot> childrenF = dbFarmacias.getChildren();

                for (DataSnapshot c : childrenF) {
                    Double lat = (double) c.child("Latitud").getValue();
                    Double lon = (double) c.child("Longitud").getValue();
                    String tit = c.child("Descripcion").getValue().toString();

                    LatLng loc = new LatLng(lat, lon);
                    talleres.add(new MarkerOptions().position(loc).title(tit));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void getTiendas() {

        DatabaseReference dbSFinder = FirebaseDatabase.getInstance().getReference();

        dbSFinder.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                DataSnapshot dbFarmacias = dataSnapshot.child("Tiendas");

                Iterable<DataSnapshot> childrenF = dbFarmacias.getChildren();

                for (DataSnapshot c : childrenF) {
                    Double lat = (double) c.child("Latitud").getValue();
                    Double lon = (double) c.child("Longitud").getValue();
                    String tit = c.child("Descripcion").getValue().toString();

                    LatLng loc = new LatLng(lat, lon);
                    tiendas.add(new MarkerOptions().position(loc).title(tit));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
