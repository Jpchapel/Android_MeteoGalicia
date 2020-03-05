package com.joaquinpereirachapel.meteogalicia;

import android.content.ContentValues;
import android.database.Cursor;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;

public class Estacion {
	
	private static String[] columnas = new String[] {"_id", "nome", "concello", "idprovincia"};
	private static String tabla = "estacion";
	private long id;
	private String nombre;
	private String ayuntamiento;
	private long idProvincia;

	public Estacion(long id, String nombre, String ayuntamiento, long idProvincia) {
		this.id = id;
		this.nombre = nombre;
		this.ayuntamiento = ayuntamiento;
		this.idProvincia = idProvincia;
	}

	public long getId() {
		return id;
	}

	public String getNombre() {
		return nombre;
	}

	public String getAyuntamiento() {
		return ayuntamiento;
	}

	public long getIdProvincia() {
		return idProvincia;
	}

	public static Cursor buscarEstacionPorProvinciaCursor(long idProvincia) {
		return MainActivity.getDataBase().query(tabla, columnas, "idprovincia = ?", new String[]{String.valueOf(idProvincia)}, null, null, "nombre");
	}

	public static void crearDesdeXML(Document xmlEstacions) {
		Element raiz = xmlEstacions.getDocumentElement();
		NodeList nodeList = raiz.getElementsByTagName("estacion");

		for (int i = 0; i < nodeList.getLength(); i++) {
			NamedNodeMap atributos = nodeList.item(i).getAttributes();
			long id = Long.valueOf(atributos.getNamedItem("ide").getTextContent());
			String nome = atributos.getNamedItem("nome").getTextContent();
			String ayuntamiento = atributos.getNamedItem("concello").getTextContent();
			long idProvincia = Long.valueOf(atributos.getNamedItem("idprovincia").getTextContent());

			Estacion estacion = new Estacion(id, nome, ayuntamiento, idProvincia);

			estacion.guardar();
		}
	}

	private void guardar() {
		ContentValues cv = new ContentValues();
		cv.put(columnas[0], this.id);
		cv.put(columnas[1], this.nombre);
		cv.put(columnas[2], this.ayuntamiento);
		cv.put(columnas[3], this.idProvincia);

		MainActivity.getDataBase().insert(tabla, null, cv);
	}
}
