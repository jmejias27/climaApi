package com.clima.entidad;

public class LocalidadResponse {

	private ClimaResponse clima;
    private MensajeResponse mensaje;
    
    public MensajeResponse getMensaje() {
		return mensaje;
	}
	public void setMensaje(MensajeResponse mensaje) {
		this.mensaje = mensaje;
	}
	public ClimaResponse getClima() {
		return clima;
	}
	public void setClima(ClimaResponse clima) {
		this.clima = clima;
	}
	

    /*{
        "LocalObservationDateTime": "2023-03-11T13:20:00-03:00",
        "EpochTime": 1678551600,
        "WeatherText": "Partly sunny",
        "WeatherIcon": 3,
        "HasPrecipitation": false,
        "PrecipitationType": null,
        "IsDayTime": true,
        "Temperature": {
          "Metric": {
            "Value": 32.2,
            "Unit": "C",
            "UnitType": 17
          },
          "Imperial": {
            "Value": 90,
            "Unit": "F",
            "UnitType": 18
          }
        }*/
    
   
}