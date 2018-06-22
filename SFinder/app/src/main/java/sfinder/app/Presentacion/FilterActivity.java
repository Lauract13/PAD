package sfinder.app.Presentacion;

import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;

import sfinder.app.Negocio.Marcador;
import sfinder.app.Negocio.ServicioAplicacion;
import sfinder.app.R;

public class FilterActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filtros);
    }

    public void aplicarFiltros(View view){

        ServicioAplicacion sa = ServicioAplicacion.getInstance();
        final CheckBox checkBoxF = (CheckBox) findViewById(R.id.cbFarmacias);
        if (checkBoxF.isChecked()) {
            sa.showMarkers(Marcador.TipoMarcador.FARMACIA);
        } else {
            sa.hideMarkers(Marcador.TipoMarcador.FARMACIA);
            checkBoxF.setChecked(true);
        }

        final CheckBox checkBoxT = (CheckBox) findViewById(R.id.cbTiendas);
        if (checkBoxT.isChecked()) {
            sa.showMarkers(Marcador.TipoMarcador.TIENDA);
        } else {
            sa.hideMarkers(Marcador.TipoMarcador.TIENDA);
            checkBoxT.setChecked(true);
        }

        final CheckBox checkBoxTal = (CheckBox) findViewById(R.id.cbTalleres);
        if (checkBoxTal.isChecked()) {
            sa.showMarkers(Marcador.TipoMarcador.TALLER);
        } else {
            sa.hideMarkers(Marcador.TipoMarcador.TALLER);
            checkBoxTal.setChecked(true);
        }
        this.finish();
    }
}
