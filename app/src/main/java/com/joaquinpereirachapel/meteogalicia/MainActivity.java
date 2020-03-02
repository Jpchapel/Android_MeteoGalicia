package com.joaquinpereirachapel.meteogalicia;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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

import org.w3c.dom.NodeList;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

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
        if(openHelper == null){
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
    }

    private void actualizarEstacion() {
    }
    public static SQLiteDatabase getDataBase() {
        return dataBase;
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
        if (id == R.id.imActualizar) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
