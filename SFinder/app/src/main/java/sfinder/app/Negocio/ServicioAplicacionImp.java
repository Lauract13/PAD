package sfinder.app.Negocio;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import sfinder.app.Integracion.DataAccessObject;

public class ServicioAplicacionImp extends ServicioAplicacion {

    private List<Marcador> talleres;
    private List<Marcador> farmacias;
    private List<Marcador> tiendas;

    public ServicioAplicacionImp()
    {
        super();
        talleres = new ArrayList<>();
        farmacias = new ArrayList<>();
        tiendas = new ArrayList<>();
        DataAccessObject.getInstance();
    }

    public void inicializarMapa(GoogleMap map)
    {
        addMarkers(map);
    }

    public void showMarkers(Marcador.TipoMarcador tipo)
    {
        switch(tipo)
        {
            case TIENDA:
            {
                Iterator<Marcador> it = tiendas.iterator();
                while(it.hasNext())
                {
                    it.next().setVisible(true);
                }
            }
            break;
            case FARMACIA:
            {
                Iterator<Marcador> it = farmacias.iterator();
                while(it.hasNext())
                {
                    it.next().setVisible(true);
                }
            }
            break;
            case TALLER:
            {
                Iterator<Marcador> it = talleres.iterator();
                while(it.hasNext())
                {
                    it.next().setVisible(true);
                }
            }
        }
    }

    public void hideMarkers(Marcador.TipoMarcador tipo)
    {
        switch(tipo)
        {
            case TIENDA:
            {
                Iterator<Marcador> it = tiendas.iterator();
                while(it.hasNext())
                {
                    it.next().setVisible(false);
                }
            }
            break;
            case FARMACIA:
            {
                Iterator<Marcador> it = farmacias.iterator();
                while(it.hasNext())
                {
                    it.next().setVisible(false);
                }
            }
            break;
            case TALLER:
            {
                Iterator<Marcador> it = talleres.iterator();
                while(it.hasNext())
                {
                    it.next().setVisible(false);
                }
            }
        }
    }

    private void addMarkers(GoogleMap map)
    {
        DataAccessObject dao = DataAccessObject.getInstance();
        Iterator<MarkerOptions> talleresIt = dao.getFiltros(Marcador.TipoMarcador.TALLER).iterator();
        while(talleresIt.hasNext())
        {
            MarkerOptions mOpt = talleresIt.next();
            Marker m = map.addMarker(mOpt.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
            talleres.add(new Marcador(m, Marcador.TipoMarcador.TALLER));
        }

        Iterator<MarkerOptions> farmaciasIt = dao.getFiltros(Marcador.TipoMarcador.FARMACIA).iterator();
        while(farmaciasIt.hasNext())
        {
            MarkerOptions mOpt = farmaciasIt.next();
            Marker m = map.addMarker(mOpt.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN)));
            farmacias.add(new Marcador(m, Marcador.TipoMarcador.FARMACIA));
        }

        Iterator<MarkerOptions> tiendasIt = dao.getFiltros(Marcador.TipoMarcador.TIENDA).iterator();
        while(tiendasIt.hasNext())
        {
            MarkerOptions mOpt = tiendasIt.next();
            Marker m = map.addMarker(mOpt.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));
            tiendas.add(new Marcador(m, Marcador.TipoMarcador.TIENDA));
        }
    }
}
