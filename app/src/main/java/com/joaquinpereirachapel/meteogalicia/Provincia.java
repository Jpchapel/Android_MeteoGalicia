package com.joaquinpereirachapel.meteogalicia;

import android.database.Cursor;

public class Provincia {
	private static String[] columnas = new String[] {"_id", "nome"};
	private static String taboa = "provincia";
	private long id;
	private String nome;

	public Provincia (long id, String nome) {
		this.id = id;
		this.nome = nome;
	}

	public static Cursor getAllCursor() {
		return MainActivity.getDataBase().query(taboa, columnas, null, null, null, null, null);
	}

	public long getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}
}
