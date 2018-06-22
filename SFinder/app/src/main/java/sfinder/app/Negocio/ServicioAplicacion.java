package sfinder.app.Negocio;

import com.google.android.gms.maps.GoogleMap;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

public abstract class ServicioAplicacion {

    private static final ServicioAplicacion ourInstance = new ServicioAplicacionImp();

    public static ServicioAplicacion getInstance() {
        return ourInstance;
    }

    protected ServicioAplicacion() {
    }

    public abstract void inicializarMapa(GoogleMap map);
    public abstract void showMarkers(Marcador.TipoMarcador tipo);
    public abstract void hideMarkers(Marcador.TipoMarcador tipo);
    public abstract FirebaseAuth getAuth();
    public abstract void cerrarSesion();
}
