package com.joaquinpereirachapel.meteogalicia;

import android.database.Cursor;

public class Provincia {
	private static String[] columnas = new String[] {"_id", "nome"};
	private static String tabla = "provincia";
	private long id;
	private String nombre;

	public Provincia (long id, String nombre) {
		this.id = id;
		this.nombre = nombre;
	}

	public static Cursor getAllCursor() {
		return MainActivity.getDataBase().query(tabla, columnas, null, null, null, null, null);
	}

	public long getId() {
		return id;
	}

	public String getNombre() {
		return nombre;
	}
}
