package com.joaquinpereirachapel.meteogalicia;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.IOException;
import java.net.URL;

public class TareaDescargaImagen extends AsyncTask<URL, Void, Bitmap> {
    private final ImageView destino;
    private final MainActivity cliente;
    private final int tipoImagen;
    private final String nombre; //El nombre asociado a la imagen que se descarga

    public TareaDescargaImagen(ImageView destino) {
        this.destino = destino;
        this.cliente = null;
        this.tipoImagen = -1;
        this.nombre = null;
    }

    public TareaDescargaImagen(MainActivity cliente, int tipoImagen, String nombre) {
        this.destino = null;
        this.cliente = cliente;
        this.tipoImagen = tipoImagen;
        this.nombre = nombre;
    }

    public TareaDescargaImagen(MainActivity cliente, int tipoImagen, String nombre, ImageView destino) {
        this.destino = destino;
        this.cliente = cliente;
        this.tipoImagen = tipoImagen;
        this.nombre = nombre;
    }

    @Override
    protected Bitmap doInBackground(URL... urls) {
        Bitmap bmpResultado = null;

        try {
            bmpResultado = BitmapFactory.decodeStream(urls[0].openStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return bmpResultado;
    }

    @Override
    protected void onPostExecute(Bitmap bmpResultado) {
        if(destino != null){
            destino.setImageBitmap(bmpResultado);
        }
        if(cliente != null){
            cliente.recibirImagen(bmpResultado, tipoImagen, nombre);
        }

    }
}
