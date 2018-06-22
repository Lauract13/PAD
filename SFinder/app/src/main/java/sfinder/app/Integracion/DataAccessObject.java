package sfinder.app.Integracion;

import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import sfinder.app.Negocio.Marcador;

public abstract class DataAccessObject {

    private static final DataAccessObject ourInstance = new DataAccessObjectImp();

    public static DataAccessObject getInstance() {
        return ourInstance;
    }

    protected DataAccessObject() {
    }

    public abstract List<MarkerOptions> getFiltros(Marcador.TipoMarcador tipo);

}
