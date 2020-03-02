package com.joaquinpereirachapel.meteogalicia;

public class CadenaFechaYHora {
	private String cadenaFechaYHora;
	
	public CadenaFechaYHora(String cadenaFechaYHora) {
		this.cadenaFechaYHora = cadenaFechaYHora;
	}

	public String getCadenaFechaYHora() {
		return cadenaFechaYHora;
	}

	public String getCadenaHoraYFecha() {
		return getCadenaHora() + " " + getCadenaFecha();
	}
	
	public String getCadenaFecha() {
		return cadenaFechaYHora.split(" ")[0];
	}
	
	public String getCadenaHora() {
		return cadenaFechaYHora.split(" ")[1];
	}
	
	public String getDia() {
		return getCadenaFecha().split("/")[0];
	}
	
	public String getMes() {
		return getCadenaFecha().split("/")[1];
	}
	
	public String getYear() {
		return getCadenaFecha().split("/")[2];
	}
	
	public String getHora() {
		return getCadenaHora().split(":")[0];
	}
	
	public String getMinuto() {
		return getCadenaHora().split(":")[1];
	}
	
	public String getSegundo() {
		return getCadenaHora().split(":")[2];
	}
}
