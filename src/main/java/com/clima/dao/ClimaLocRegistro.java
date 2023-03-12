package com.clima.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import com.clima.entidad.ClimaLocHistorial;

/** Clase para realizar cambios en la tabla clim_loc_hist de la bd clima de mariadb */

public class ClimaLocRegistro {
	
	/**
	 * Formato de fecha que responde el servicio de http://dataservice.accuweather.com/currentconditions/v1/{locationKey}
	 * parametro LocalObservationDateTime
	 * 
	 * */
	SimpleDateFormat fecFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
	
	/** Metodo para realizar insert en la tabla
	 * Parametros:
	 * idLocacion: id de la localidad para consultar
	 * json: json que contiene los datos a guardar
	 * ClimaLocHistorialDAO: dao de la tabla clim_loc_hist
	 * 
	 * */
	public void insertClimaLocRegistro(String idLocacion, JSONObject json, ClimaLocHistorialDAO climaLocHistorialDAO) throws JSONException {
		
		/** Se inicializa dos variables tipo date. date para la hora actual, fecHorMedicion para contener la fecha de medicion*/
		Date date = new Date();
		Date fecHorMedicion = date;
		
		/** Se realiza el parseo de la fecha recibida para darle formato fecFormat*/
		try {
			fecHorMedicion = fecFormat.parse(json.getString("LocalObservationDateTime"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		/** Se obtienen los valores del json*/
		JSONObject oriTemp = json.getJSONObject("Temperature");
		JSONObject oriTempMet = oriTemp.getJSONObject("Metric");
		JSONObject oriTempImp = oriTemp.getJSONObject("Imperial");
		
		ClimaLocHistorial climaLocHistorial = new ClimaLocHistorial();
		
		/** Se agregan los datos a la entidad climaLocHistorial para ser enviados al dao climaLocHistorialDAO */
		climaLocHistorial.setIdReg(0);
		climaLocHistorial.setIdLocacion(Integer.parseInt(idLocacion));
		climaLocHistorial.setFecMedicion(fecHorMedicion);
		climaLocHistorial.setHorMedicion(fecHorMedicion);
		climaLocHistorial.setHayPrecipitacion(json.getBoolean("HasPrecipitation"));
		climaLocHistorial.setTipPrecipitacion(json.getString("PrecipitationType"));
		climaLocHistorial.setEsDeDia(json.getBoolean("IsDayTime"));
		climaLocHistorial.setTxtClima(json.getString("WeatherText"));
		climaLocHistorial.setMetUnidad(oriTempMet.getString("Unit"));
		climaLocHistorial.setMetValor(oriTempMet.getDouble("Value"));
		climaLocHistorial.setImpUnidad(oriTempImp.getString("Unit"));
		climaLocHistorial.setImpValor(oriTempImp.getDouble("Value"));
		climaLocHistorial.setFecReg(date);
		climaLocHistorial.setHorReg(date);
		
		/** Se ejecuta el save del dao para guardar los datos en la tabla*/
		climaLocHistorialDAO.save(climaLocHistorial);

	}
	
}
