package com.joaquinpereirachapel.meteogalicia;

import android.content.ContentValues;
import android.database.Cursor;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Ayuntamiento {
    private static String tabla = "concello";
    private static String[] columnas = new String[]{"_id", "nome", "idprovincia"};


    private long id;
    private String nombre;
    private long idProvincia;

    public Ayuntamiento(long id, String nombre, long idProvincia) {
        this.id = id;
        this.nombre = nombre;
        this.idProvincia = idProvincia;
    }

    public long getId() {

        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public long getIdProvincia() {

        return idProvincia;
    }


    public static Cursor buscarAyuntamientoPorProvinciaCursor(long idProvincia) {
        return MainActivity.getDataBase().query(tabla, columnas, "idprovincia = ?", new String[]{idProvincia + ""}, null, null, "nome");
    }


    public static void crearDesdeXml(Document xmlAyuntamientos) {
        Element raiz = xmlAyuntamientos.getDocumentElement();
        NodeList nodeList = raiz.getElementsByTagName("provincia");

        for (int inciceProvincia = 0; inciceProvincia < nodeList.getLength(); inciceProvincia++) {
            Node nodoProvincia = nodeList.item(inciceProvincia);
            long idProvincia = Long.valueOf(nodoProvincia.getAttributes().getNamedItem("idp").getTextContent()).longValue();

            //buscamos los elementos concello dentro de la provincia
            NodeList ayuntemientos = ((Element) nodoProvincia).getElementsByTagName("concello");

            for (int indiceAyuntamiento = 0; indiceAyuntamiento < ayuntemientos.getLength(); indiceAyuntamiento++) {
                NamedNodeMap atributos = ayuntemientos.item(indiceAyuntamiento).getAttributes();
                long idAyuntamiento = Long.valueOf(atributos.getNamedItem("idc").getTextContent());
                String nombreAyuntamiento = atributos.getNamedItem("nome").getTextContent();

                Ayuntamiento ayuntamiento = new Ayuntamiento(idAyuntamiento, nombreAyuntamiento, idProvincia);

                ayuntamiento.guardar();
            }

        }
    }

    private void guardar() {
        ContentValues cv = new ContentValues();

        cv.put("_id", this.id);
        cv.put("nome", this.nombre);
        cv.put("idprovincia", this.idProvincia);

        MainActivity.getDataBase().insert(tabla, null, cv);
    }
}
