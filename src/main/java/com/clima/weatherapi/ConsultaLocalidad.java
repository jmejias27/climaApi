package com.clima.weatherapi;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/** Clase para hacer conexion con la api de developer.accuweather.com*/

public class ConsultaLocalidad {
	
	/** */
	JSONObject jObject = new JSONObject();

	/** Metodo para realizar la consulta del clima por localidad de developer.accuweather.com
	 * Parametros:
	 * idLocacion: id de la localidad para consultar
	 * uri: endpoint de consulta por localidad de developer.accuweather.com
	 * 
	 * */
	public JSONObject getConsultaLocalidad(String idLocacion, String uri) throws JSONException {
		
		try {
			/** Se prepara la uri*/
			URL url = new URL(uri);
		
			/** Se prepara la conexion*/
			HttpURLConnection connWeather = (HttpURLConnection) url.openConnection();
			
			/** Se setea la conexion como metodo get*/
			connWeather.setRequestMethod("GET");
			
			/** Se hace la conexion*/
			connWeather.connect();
			
			/** Se obtiene respuesta*/
			int resCode = connWeather.getResponseCode();
			String resMsj = connWeather.getResponseMessage();
			
			StringBuilder respBoby = new StringBuilder();
			
			/** Si la respuesta es correcta 200 */
			if(resCode == 200) {
				
				/** Se realiza openStream*/
				Scanner scan = new Scanner(url.openStream());
				
				/** Se realiza un scan por toda la respuesta y se plasma en un StringBuilder*/
				while(scan.hasNext()) {
					respBoby.append(scan.nextLine());
				}
				
				/** Se cierra la conexion*/
				scan.close();
				
				/** Ya que la respuesta viene en un array, se agrega en un nuevo jsonarray*/
				JSONArray jArray = new JSONArray(respBoby.toString());
				
				/** Del array se extrae el json*/
				
				jObject = jArray.getJSONObject(0);
				
				/** Se setean en el json cod y msj de respuesta de la conexion*/
				jObject.put("Code", "" + resCode);
				jObject.put("Message", resMsj);
			}else {
				/** Se setean en el json cod y msj de respuesta de la conexion*/
				jObject.put("Code", "" + resCode);
				jObject.put("Message", "Error estableciendo conexión con developer.accuweather.com: " + resMsj);
			}
			
		} catch (IOException e) {
			/** Se setean en el json cod y msj de respuesta de la conexion*/
			jObject.put("Code", "500");
			jObject.put("Message", e.getMessage());
			e.printStackTrace();
		}
		
		return jObject;
		
	}
}
