package com.joaquinpereirachapel.meteogalicia;

import android.database.Cursor;

public class Estacion {
	
	private static String[] columnas = new String[] {"_id", "nome", "concello", "idprovincia"};
	private static String tabla = "estacion";
	private long id;
	private String nome;
	private String concello;
	private long idProvincia;

	public Estacion(long id, String nome, String concello, long idProvincia) {
		this.id = id;
		this.nome = nome;
		this.concello = concello;
		this.idProvincia = idProvincia;
	}

	public long getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}

	public String getConcello() {
		return concello;
	}

	public long getIdProvincia() {
		return idProvincia;
	}

	public static Cursor buscarEstacionPorProvinciaCursor(long idProvincia) {
		return MainActivity.getDataBase().query(tabla, columnas, "idprovincia = ?", new String[]{String.valueOf(idProvincia)}, null, null, "nome");
	}
}
