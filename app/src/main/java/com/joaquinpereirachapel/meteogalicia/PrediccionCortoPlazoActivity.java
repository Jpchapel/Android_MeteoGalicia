package com.joaquinpereirachapel.meteogalicia;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import org.w3c.dom.Document;

public class PrediccionCortoPlazoActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, TareaDescargaXML.Cliente{
    private static final int DESCARGA_AYUNTAMIENTOS = 1;
    private Spinner provinciasCortoPlazo;
    private Spinner ayuntamientosCortoPlazo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prediccion_corto_plazo);

        provinciasCortoPlazo = (Spinner) findViewById(R.id.provinciasCortoPlazo);
        provinciasCortoPlazo.setOnItemSelectedListener(this);

        ayuntamientosCortoPlazo = (Spinner) findViewById(R.id.ayuntamientosCortoPlazo);

        llenarProvinciasCortoPlazo();
    }

    private void llenarProvinciasCortoPlazo() {
        Cursor c = Provincia.getAllCursor();

        //System.out.println("Cursor "  + c.getCount());
        //Creamos un adapter co cursor
        String[] from = {"nome"};
        int[] to = {android.R.id.text1};

        SimpleCursorAdapter sca = new SimpleCursorAdapter(this, android.R.layout.simple_spinner_item, c, from, to, 0);
        provinciasCortoPlazo.setAdapter(sca);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Cursor c = ((SimpleCursorAdapter) provinciasCortoPlazo.getAdapter()).getCursor();
        if (c != null) {
            c.close();
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
        if (adapterView.getId() == provinciasCortoPlazo.getId()) {
            llenarAyuntamientos(id);
        }
    }

    private void llenarAyuntamientos(long idProvincia) {
        Cursor cursor = Ayuntamiento.buscarAyuntamientoPorProvinciaCursor(idProvincia);
        if (cursor.getCount() == 0) {
            //El cursor esta vacio porque no hay ayuntamientos en la BD local, hay que descargarlos
            TareaDescargaXML tdx = new TareaDescargaXML(this, DESCARGA_AYUNTAMIENTOS);
            tdx.execute(ServicioURL.ayuntamientos());
        } else {
            cargarAyuntamientos(cursor);
        }
    }

    private void cargarAyuntamientos(Cursor cursor) {
        SimpleCursorAdapter sca = (SimpleCursorAdapter) ayuntamientosCortoPlazo.getAdapter();

        if (sca == null) {
            String[] from = {"nome"};
            int[] to = {android.R.id.text1};

            sca = new SimpleCursorAdapter(this, android.R.layout.simple_spinner_item, cursor, from, to, 0);
            provinciasCortoPlazo.setAdapter(sca);
        } else {
            sca.swapCursor(cursor).close();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void recibirDocumento(Document resultado, int tipoDescarga) {
        if (resultado == null) {
            Toast.makeText(this, "Problemas de conexion", Toast.LENGTH_SHORT).show();
            return;
        }
        if (tipoDescarga == DESCARGA_AYUNTAMIENTOS){
            Ayuntamiento.crearDesdeXml(resultado);

            Cursor c = Ayuntamiento.buscarAyuntamientoPorProvinciaCursor(provinciasCortoPlazo.getSelectedItemId());
            cargarAyuntamientos(c);
        }
    }
}
