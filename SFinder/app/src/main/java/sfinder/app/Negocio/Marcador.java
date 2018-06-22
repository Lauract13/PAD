package sfinder.app.Negocio;

import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class Marcador {

    public enum TipoMarcador
    {
        TALLER,
        FARMACIA,
        TIENDA
    }

    private Marker marker;
    private TipoMarcador tipo;

    public Marcador(Marker m, TipoMarcador t)
    {
        marker = m;
        tipo = t;
    }

    public Marker getMarker(){
        return marker;
    }

    public TipoMarcador getTipo(){
        return tipo;
    }

    public void setVisible(boolean b){
        marker.setVisible(b);
    }
}
