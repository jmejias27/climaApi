package com.clima.rest;

import org.springframework.web.bind.annotation.RestController;

import com.clima.dao.ClimaLocHistorialDAO;
import com.clima.dao.ClimaLocRegistro;
import com.clima.entidad.*;
import com.clima.weatherapi.*;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Rest para consultar el clima desde la api de developer.accuweather.com Patct:
 * /clima
 * 
 */

@RestController
@RequestMapping("/clima")
public class ClimaConsultaRest {
	
	/**
	 * Constantes de la api de clima https://developer.accuweather.com/
	 * apikey: Toquen de la api. Con este valor se podrá realizar solicitudes
	 * language: idioma de respuesta
	 * 
	 */
	
	@Value("${com.clima.weatherapi.apikey}")
	private String APIKEY;
	@Value("${com.clima.weatherapi.language}")
	private String APILANGUAGE;
	@Value("${com.clima.weatherapi.uriConsulLocalidad}")
	private String APIURI;
	
	private ConsultaLocalidad consultaLocalidad = new ConsultaLocalidad();
	
	private ClimaLocRegistro climaLocRegistro = new ClimaLocRegistro();
	
	@Autowired
	private ClimaLocHistorialDAO climaLocHistorialDAO;

	Pattern numero = Pattern.compile("[0-9]+$");

	/**
	 * Para esta consulta se utiliza la uri
	 * http://dataservice.accuweather.com/currentconditions/v1/{locationKey} donde
	 * locationKey sera el id de la locacion que queremos consultar. ejemplo: Buenos
	 * Aires Capital locationkey = 7894 Patct: /clima Parametros in:idLocacion
	 * Response: ClimaLocResponse
	 * 
	 */
	@RequestMapping(value = "/consultaId/{idLocacion}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<LocalidadResponse> getClimaId(@PathVariable("idLocacion") String idLocacion) {

		LocalidadResponse localidadResponse = new LocalidadResponse();
		ClimaResponse climaResponse = new ClimaResponse();
		SistemaImperial sistemaImperial = new SistemaImperial();
		SistemaMetrico sistemaMetrico = new SistemaMetrico();
		Temperatura temperatura = new Temperatura();
		MensajeResponse mensajeResponse = new MensajeResponse();

		Matcher mat = null;
		
		mat = numero.matcher(idLocacion);
		
		/** Se valida que el idLocacion contenga un formato de solo numeros antes de ejecutar la consulta*/
		
		if (!mat.find()) {
			mensajeResponse.setCodMsj("501");
			mensajeResponse.setTxtMsj("El parámetro [idLocacion] debe contener sólo números");
		} else {

			try {

				/** Se genera el url para consultar a dataservice.accuweather.com*/
				String uri = APIURI + idLocacion + "?apikey=" + APIKEY + "&language=" + APILANGUAGE;

				/** Se ejecuta la consulta a dataservice.accuweather.com*/
				JSONObject jSalida = consultaLocalidad.getConsultaLocalidad(idLocacion, uri);

				/** Si la respuesta de dataservice.accuweather.com es 200 OK*/
				if (jSalida.getString("Code").equals("200")) {

					/** Se obtienen los datos de temperatura del json de salida de dataservice.accuweather.com*/
					JSONObject oriTemp = jSalida.getJSONObject("Temperature");

					/** Se obtienen los datos de temperatura en unidades metricas e imperial del json de temperatura*/
					JSONObject oriTempMet = oriTemp.getJSONObject("Metric");
					JSONObject oriTempImp = oriTemp.getJSONObject("Imperial");

					/** Se obtienen los datos de unidades metricas de temperatura y se agregan a la entidad sistemaMetrico*/
					sistemaMetrico.setUnidad(oriTempMet.getString("Unit"));
					sistemaMetrico.setValor(oriTempMet.getDouble("Value"));

					/** Se obtienen los datos de unidades imperial de temperatura y se agregan a la entidad sistemaImperial*/
					sistemaImperial.setUnidad(oriTempImp.getString("Unit"));
					sistemaImperial.setValor(oriTempImp.getDouble("Value"));

					/** Se agregan los datos a la entidad de temperatura*/
					temperatura.setSisImperial(sistemaImperial);
					temperatura.setSisMetrico(sistemaMetrico);

					/** Se agregan los demas datos a la entidad de climaResponse*/
					climaResponse.setTemperatura(temperatura);
					climaResponse.setEsDeDia(jSalida.getBoolean("IsDayTime"));
					climaResponse.setHayPrecipitacion(jSalida.getBoolean("HasPrecipitation"));
					climaResponse.setTipPrecipitacion(jSalida.getString("PrecipitationType"));
					climaResponse.setTxtClima(jSalida.getString("WeatherText"));

					/** Se setean el json de salida los datos de clima de respuesta con la entidad climaResponse*/
					localidadResponse.setClima(climaResponse);

					/** Se guardan los datos de temperatura en la tabla clim_loc_hist de la bd clima de mariadb*/
					climaLocRegistro.insertClimaLocRegistro(idLocacion, jSalida, climaLocHistorialDAO);
				} else {
					/** De lo contrario se setea clima null en el json de salida */
					localidadResponse.setClima(null);
				}

				/** Se setean codigo y mensaje de respuesta de  dataservice.accuweather.com*/
				mensajeResponse.setCodMsj(jSalida.getString("Code"));
				mensajeResponse.setTxtMsj(jSalida.getString("Message"));

			} catch (JSONException e) {
				/** Se setean codigo y mensaje de respuesta por error en la ejecucion de dataservice.accuweather.com, 
				 * y clima null en el json de salida*/
				mensajeResponse.setCodMsj("500");
				mensajeResponse.setTxtMsj("Error interno.");
				localidadResponse.setClima(null);

				e.printStackTrace();
			}
		}
		/** Se setean el json de salida con codigo y mensaje de respuesta*/
		localidadResponse.setMensaje(mensajeResponse);

		/** Respuesta*/
		return ResponseEntity.ok(localidadResponse);
	}
}
