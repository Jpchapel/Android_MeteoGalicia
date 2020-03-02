package com.joaquinpereirachapel.meteogalicia;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Util {

	public static String fechaDiaMesAno(Date fecha) {
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(fecha);
		
		return Util.fechaDiaMes(fecha) + "/" + gc.get(Calendar.YEAR);
	}
	
	public static String fechaDiaMes(Date fecha) {
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(fecha);
		
		return rellenarCeros(gc.get(Calendar.DAY_OF_MONTH)) + "/" + rellenarCeros(gc.get(Calendar.MONTH)+1);
	}
	
	public static String fechaAnoMesDia(Date fecha) {
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(fecha);
		
		return gc.get(Calendar.YEAR) + "-" + rellenarCeros((gc.get(Calendar.MONTH)+1)) + "-" + rellenarCeros( gc.get(Calendar.DAY_OF_MONTH));
	}

	public static String fechaHora(Date fecha) {
		return fechaDiaMesAno(fecha) + " " + hora(fecha);
	}	
	
	public static String hora(Date fecha) {
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(fecha);
		
		return rellenarCeros(gc.get(Calendar.HOUR_OF_DAY)) + ":" + rellenarCeros(gc.get(Calendar.MINUTE)) + ":" + rellenarCeros(gc.get(Calendar.SECOND));
	}
	
	public static String dia(Date fecha) {
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(fecha);
		
		return rellenarCeros(gc.get(Calendar.DAY_OF_MONTH));
	}
	
	public static String mes(Date fecha) {
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(fecha);
		
		return rellenarCeros(gc.get(Calendar.MONTH));
	}
	
	public static String year(Date fecha) {
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(fecha);
		
		return rellenarCeros(gc.get(Calendar.YEAR));
	}

	public static Date getLunes() {
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(new Date());
		int diaSemanahoy = gc.get(Calendar.DAY_OF_WEEK);
		//System.out.println ("Dia semana" +diaSemanaHoxe);
		
		Date fechaLunes = new Date();
		fechaLunes.setTime(fechaLunes.getTime() - ((diaSemanahoy - 2) * 24* 3600 * 1000));
		
		return fechaLunes;
	}
	
	public static Date getViernes() {
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(new Date());
		int diaSemanahoy = gc.get(Calendar.DAY_OF_WEEK);
		//System.out.println ("Dia semana" +diaSemanaHoxe);
		
		Date fechaViernes = new Date();
		fechaViernes.setTime(fechaViernes.getTime() - ((diaSemanahoy - 6) * 24* 3600 * 1000));
		
		return fechaViernes;
	}

	public static Date[] getFechasSemana(Date fechaLunes) {
		Date[] fechasSemana = new Date[5];
		
		for(int i=0; i<5; i++) {
			fechasSemana[i] = new Date(fechaLunes.getTime() + i * 24*3600*1000);
		}
		return fechasSemana;
	}
	
	private static String rellenarCeros(int numero) {
		String cadeaResultado = String.valueOf(numero);
		
		if(cadeaResultado.length() == 1) {
			cadeaResultado = "0" + cadeaResultado;
		}
		
		return cadeaResultado;
	}
}
