package com.clima.entidad;

public class ClimaResponse {

    private Temperatura temperatura;
    private boolean hayPrecipitacion;
	private String TipPrecipitacion;
    private String txtClima;
    private boolean esDeDia;

	public Temperatura getTemperatura() {
		return temperatura;
	}
	public void setTemperatura(Temperatura temperatura) {
		this.temperatura = temperatura;
	}
	public boolean isHayPrecipitacion() {
		return hayPrecipitacion;
	}
	public void setHayPrecipitacion(boolean hayPrecipitacion) {
		this.hayPrecipitacion = hayPrecipitacion;
	}
	public String getTipPrecipitacion() {
		return TipPrecipitacion;
	}
	public void setTipPrecipitacion(String tipPrecipitacion) {
		TipPrecipitacion = tipPrecipitacion;
	}
	public String getTxtClima() {
		return txtClima;
	}
	public void setTxtClima(String txtClima) {
		this.txtClima = txtClima;
	}
	public boolean isEsDeDia() {
		return esDeDia;
	}
	public void setEsDeDia(boolean esDeDia) {
		this.esDeDia = esDeDia;
	}
}
