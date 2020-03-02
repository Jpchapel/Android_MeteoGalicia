package com.joaquinpereirachapel.meteogalicia;

import android.database.Cursor;

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
}
