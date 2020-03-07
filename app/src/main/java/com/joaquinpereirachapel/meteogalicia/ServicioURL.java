package com.joaquinpereirachapel.meteogalicia;

import java.net.MalformedURLException;
import java.net.URL;

public class ServicioURL {
    private static String urlBase1 = "http://servizos.meteogalicia.gal/";
    private static String urlBase2 = "http://www.meteogalicia.es/datosred/infoweb/meteo/imagenes/";
    private static String urlBase3 = "http://127.0.0.1/";

    public static URL estadoEstacion(long idEstacion) {
        String cadenaURL = urlBase1 + "rss/observacion/rssEstacionsEstActual.action?idEst=" + idEstacion;

        return cadena2URL(cadenaURL);
    }

    public static URL iconoEstadoCielo(String iconoEstadoCielo) {
        String cadenaURL = urlBase2 + "meteorosmapa/ceo/" + iconoEstadoCielo + ".png";

        return cadena2URL(cadenaURL);
    }

    public static URL iconoViento(String iconoViento) {
        String cadenaURL = urlBase2 + "meteoros/vento/combo/" + iconoViento + ".png";

        return cadena2URL(cadenaURL);
    }

    public static URL iconoTemperatura(String iconoTemperatura) {
        String cadenaURL = urlBase2 + "termometros/" + iconoTemperatura + ".png";

        return cadena2URL(cadenaURL);
    }

    public static URL nuevasEstaciones(String dataUltimaActualizacion) {
        String cadenaURL = urlBase3 + "novas_estacions.php?data_ultima_actualizacion=" + dataUltimaActualizacion;

        return cadena2URL(cadenaURL);
    }

    public static URL ayuntamientos() {
        String cadenaURL = urlBase3 + "cocellos.php";

        return cadena2URL(cadenaURL);
    }
    public static URL prediccionCortoPlazo(long idConcello, int dia) {
        String cadenaURL = urlBase1 + "rss/predicion/rssLocalidades.action?idZona=" + idConcello + "&dia=" + dia;

        return cadena2URL(cadenaURL);
    }
    private static URL cadena2URL(String cadenaURL) {
        try {
            return new URL(cadenaURL);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
