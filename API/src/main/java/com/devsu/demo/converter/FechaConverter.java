package com.devsu.demo.converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class FechaConverter {
	
	public static Date convertToDate(String s) {
		 SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		 try {
			 return dateFormat.parse(s);
		 } catch (ParseException e) {
			 //Nunca debería llegar acá, ya que se verifica antes de guardar la fecha en la base
			 return null;
		 }

	}

}
