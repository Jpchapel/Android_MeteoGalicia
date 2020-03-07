package com.joaquinpereirachapel.meteogalicia;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.net.URL;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, TareaDescargaXML.Cliente {

    private static SQLiteDatabase dataBase;
    private static final int DESCARGA_ESTADO_ACTUAL_ESTACION = 1;
    private static final int DESCARGA_NUEVAS_ESTACIONES = 2;
    private static final long TIEMPO_MINIMO_ENTRE_ACTUALIZACIONES = 60;//El tiempo es en segundos, ponemos 1 min
    private static final int ICONO_CIELO = 101;
    private static final int ICONO_TEMPERATURA = 102;
    private static final int ICONO_VIENTO = 103;
    private static final String CARPETA_CACHE_ICONOS = "cache_iconos";
    private static SharedPreferences sharedPreferences;
    private Spinner provincias;
    private Spinner estaciones;
    private TextView horaUTC;
    private TextView horaLocal;
    private TextView temperatura;
    private TextView sensacionTermica;
    private ImageView estadoCielo;
    private ImageView tendenciaTemperatura;
    private ImageView viento;
    private NodeList nodeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        MeteoDBOpenHelper openHelper = new MeteoDBOpenHelper(this);
        if(dataBase == null){
            dataBase = openHelper.getWritableDatabase();
        }

        sharedPreferences = getSharedPreferences("meteogalicia.sp", MODE_PRIVATE);

        provincias = (Spinner)findViewById(R.id.provincias);
        provincias.setOnItemSelectedListener(this);

        estaciones = (Spinner)findViewById(R.id.estaciones);
        estaciones.setOnItemSelectedListener(this);

        horaUTC = (TextView) findViewById(R.id.horaUTC);
        horaLocal = (TextView) findViewById(R.id.horaLocal);
        temperatura = (TextView) findViewById(R.id.temperatura);
        sensacionTermica = (TextView) findViewById(R.id.sensacionTermica);

        estadoCielo = (ImageView) findViewById(R.id.estadoCielo);
        tendenciaTemperatura = (ImageView) findViewById(R.id.tendenciaTemperatura);
        viento = (ImageView) findViewById(R.id.viento);

        llenarProvincias();

        actualizarEstacion();
    }

    private void llenarProvincias() {
        Cursor cursor = Provincia.getAllCursor();

        String[] from = {"nome"};
        int[] to = {android.R.id.text1};

        SimpleCursorAdapter simpleCursorAdapter = new SimpleCursorAdapter(this, android.R.layout.simple_spinner_item, cursor, from, to, 0);
        provincias.setAdapter(simpleCursorAdapter);

        long idProvinciaFavorita = sharedPreferences.getLong("idProvinciaFavorita", -1);

        if(idProvinciaFavorita != -1){
            for (int i = 0; i < provincias.getCount(); i++) {
                if(provincias.getItemAtPosition(i).equals(idProvinciaFavorita)){
                    provincias.setSelection(i);
                    break;
                }
            }
        }
    }

    private void llenarEstaciones(long idProvincia) {
        Cursor c = Estacion.buscarEstacionPorProvinciaCursor(idProvincia);

        String[] from = {"nome"};
        int[] to = {android.R.id.text1};

        SimpleCursorAdapter sca = (SimpleCursorAdapter) estaciones.getAdapter();
        if (sca == null) {
            sca = new SimpleCursorAdapter(this, android.R.layout.simple_spinner_item, c, from, to, 0);
            estaciones.setAdapter(sca);
        } else {
            sca.swapCursor(c).close();
        }

        long idEstacionFavorita = sharedPreferences.getLong("idEstacionFavorita", -1);
        if (idEstacionFavorita != -1) {
            for (int i = 0; i < estaciones.getCount(); i++) {
                if (estaciones.getItemIdAtPosition(i) == idEstacionFavorita) {
                    estaciones.setSelection(i);
                    break;
                }
            }
        }
    }

    private void actualizarEstacion() {
        long ultimaActualizacion = sharedPreferences.getLong("fechaUltimaActualizacion", 0);

        long tiempoTranscurrido = new Date().getTime() / 1000 - ultimaActualizacion;

        if (tiempoTranscurrido > TIEMPO_MINIMO_ENTRE_ACTUALIZACIONES) {
            String fechaUltimaActualizacionAMD = Util.fechaAnoMesDia(new Date(ultimaActualizacion * 1000));

            TareaDescargaXML tdx = new TareaDescargaXML(this, DESCARGA_NUEVAS_ESTACIONES);
            tdx.execute(ServicioURL.nuevasEstaciones(fechaUltimaActualizacionAMD));
        }
    }

    public static SQLiteDatabase getDataBase() {
        return dataBase;
    }

    public static SharedPreferences getSharedPreferences() {
        return sharedPreferences;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.actualizar) {
            descargarEstadoActualEstacion(estaciones.getSelectedItemId());
            return true;
        }else if(id == R.id.estacionFavorita){
            guardarEstacionFavorita();
            return true;
        }else if(id == R.id.prediccionCurtoPrazo){
            Intent intent = new Intent(this, PrediccionCortoPlazoActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void guardarEstacionFavorita() {
        long idEstacionFavorita = estaciones.getSelectedItemId();
        long idProvincia = provincias.getSelectedItemId();

        sharedPreferences.edit().putLong("idEstacionFavorita", idEstacionFavorita).putLong("idProviciaFavorita", idProvincia).commit();

        Toast.makeText(this, "Se ha marcado como favorita la estacion actual", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
        if(adapterView.getId() == R.id.provincias){
            llenarEstaciones(id);
        }else if(adapterView.getId() == R.id.estaciones){
            descargarEstadoActualEstacion(id);
        }
    }

    private void descargarEstadoActualEstacion(long idEstacion) {
        TareaDescargaXML tdx = new TareaDescargaXML(this, DESCARGA_ESTADO_ACTUAL_ESTACION);
        tdx.execute(ServicioURL.estadoEstacion(idEstacion));
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Cursor cursor = ((SimpleCursorAdapter) provincias.getAdapter()).getCursor();
        if (cursor != null) {
            cursor.close();
        }
        cursor = ((SimpleCursorAdapter) estaciones.getAdapter()).getCursor();
        if (cursor != null) {
            cursor.close();
        }
    }

    public void recibirImagen(Bitmap bmpResultado, int tipoImagen, String nombre) {
         if (bmpResultado == null) {
            return;
        }

        File carpetaDestino = carpetaCacheTemperatura();

        if (tipoImagen == ICONO_CIELO) {
            carpetaDestino = carpetaCacheCielo();
        } else if (tipoImagen == ICONO_VIENTO) {
            carpetaDestino = carpetaCacheViento();
        }

        if (!nombre.equals("-9999")) {
            File ficheroDestinoImagen = new File(carpetaDestino, nombre + ".png");

            try {
                bmpResultado.compress(Bitmap.CompressFormat.PNG, 100, new FileOutputStream(ficheroDestinoImagen));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void recibirDocumento(Document resultado, int tipoDescarga) {
        if (resultado == null) {
            Toast.makeText(this, "Problemas de conexion", Toast.LENGTH_LONG).show();
            return;
        }
        if (tipoDescarga == DESCARGA_ESTADO_ACTUAL_ESTACION) {
            procesarEstadoEstacion(resultado);
        } else if (tipoDescarga == DESCARGA_NUEVAS_ESTACIONES) {
            procesarNuevasEstaciones(resultado);
        }
    }

    private void procesarEstadoEstacion(Document resultado) {
        Element raiz = resultado.getDocumentElement();

        cargarEtiqueta(raiz, "EstacionsEstActual:dataUTC", horaLocal);
        cargarEtiqueta(raiz, "EstacionsEstActual:dataLocal", horaLocal);
        cargarEtiqueta(raiz, "EstacionsEstActual:valTemperatura", temperatura);
        cargarEtiqueta(raiz, "EstacionsEstActual:valSensTermica", sensacionTermica);

        cargarIcono(raiz, "EstacionsEstActual:icoCeo", estadoCielo);
        cargarIcono(raiz, "EstacionsEstActual:icoVento", viento);
        cargarIcono(raiz, "EstacionsEstActual:icoTemperatura", tendenciaTemperatura);
    }

    private void cargarEtiqueta(Element raiz, String etiquetaXML, TextView tv) {
        nodeList = raiz.getElementsByTagName(etiquetaXML);

        if (nodeList.getLength() == 1) {
            tv.setText(nodeList.item(0).getTextContent());
        } else {
            tv.setText("-");
        }
    }

    private void cargarIcono(Element raiz, String etiquetaXML, ImageView imageView) {
        nodeList = raiz.getElementsByTagName(etiquetaXML);

        imageView.setImageResource(R.drawable.guion);

        if (nodeList.getLength() == 1) {
            String nombreIcono = nodeList.item(0).getTextContent();

            if (nombreIcono.equals("-9999")) {
                return;
            }

            if (imageView.getId() == estadoCielo.getId()) {
                lanzarTareaDescargaImagen(imageView, nombreIcono, carpetaCacheCielo(), ICONO_CIELO, ServicioURL.iconoEstadoCielo(nombreIcono));
            } else if (imageView.getId() == tendenciaTemperatura.getId()) {
                lanzarTareaDescargaImagen(imageView, nombreIcono, carpetaCacheTemperatura(), ICONO_TEMPERATURA, ServicioURL.iconoTemperatura(nombreIcono));
            } else if (imageView.getId() == viento.getId()) {
                lanzarTareaDescargaImagen(imageView, nombreIcono, carpetaCacheViento(), ICONO_VIENTO, ServicioURL.iconoViento(nombreIcono));
            }
        }
    }

    private void lanzarTareaDescargaImagen(ImageView iv, String nomeIcona, File file, int tipoIcono, URL direccionURL) {
        File directorioIcono = new File(file, nomeIcona + ".png");

        if (directorioIcono.exists()) {
            Bitmap icono = BitmapFactory.decodeFile(directorioIcono.getAbsolutePath());
            iv.setImageBitmap(icono);
        } else {
            TareaDescargaImagen tdi = new TareaDescargaImagen(this, tipoIcono, nomeIcona, iv);
            tdi.execute(direccionURL);
        }
    }

    private void procesarNuevasEstaciones(Document resultado) {
        Estacion.crearDesdeXML(resultado);

        sharedPreferences.edit().putLong("dataUltimaActualizacion", new Date().getTime() / 1000).commit();

        llenarEstaciones(provincias.getSelectedItemId());
    }

    public File carpetaCacheCielo() {
        File raizCache = getDir(CARPETA_CACHE_ICONOS, MODE_APPEND);

        if (!raizCache.exists()) {
            raizCache.mkdir();
        }

        File carpetaCacheCielo = new File(raizCache, "ceo");

        if (!carpetaCacheCielo.exists()) {
            carpetaCacheCielo.mkdir();
        }

        return carpetaCacheCielo;
    }

    public File carpetaCacheViento() {
        File raizCache = getDir(CARPETA_CACHE_ICONOS, MODE_APPEND);

        if (!raizCache.exists()) {
            raizCache.mkdir();
        }

        File carpetaCacheViento = new File(raizCache, "vento");

        if (!carpetaCacheViento.exists()) {
            carpetaCacheViento.mkdir();
        }

        return carpetaCacheViento;
    }

    public File carpetaCacheTemperatura() {
        File raizCache = getDir(CARPETA_CACHE_ICONOS, MODE_APPEND);

        if (!raizCache.exists()) {
            raizCache.mkdir();
        }

        File carpetaCacheTemperatura = new File(raizCache, "temperatura");

        if (!carpetaCacheTemperatura.exists()) {
            carpetaCacheTemperatura.mkdir();
        }

        return carpetaCacheTemperatura;
    }
}
