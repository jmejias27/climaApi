package com.clima.entidad;

import javax.persistence.Entity;
import javax.persistence.GenerationType;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Id;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;

@Entity
@Table(name="clim_loc_hist")
public class ClimaLocHistorial {

	@Id
	@Column(name="id_reg")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long idReg; 
	
	@Column(name="id_locacion")
	private int idLocacion;
	
	@Column(name="fec_medicion")
	@Temporal(TemporalType.DATE)
	private Date fecMedicion;
	
	@Column(name="hor_medicion")
	@Temporal(TemporalType.TIME)
	private Date horMedicion;
	
	@Column(name="mca_precipitacion")
	private boolean hayPrecipitacion;
	
	@Column(name="tip_precipitacion")
	private String TipPrecipitacion;
	
	@Column(name="mca_dia")
	private boolean esDeDia;
	
	@Column(name="txt_clima")
	private String txtClima;
	
	@Column(name="met_temperatura", length=1)
	private String metUnidad;

	@Column(name="met_val_temperatura")
	private double metValor;
	
	@Column(name="imp_temperatura", length=1)
	private String impUnidad;
	
	@Column(name="imp_val_temperatura")
	private double impValor;

	@Column(name="fec_reg")
	@Temporal(TemporalType.DATE)
	private Date fecReg;
	
	@Column(name="hor_reg")
	@Temporal(TemporalType.TIME)
	private Date horReg;

	public long getIdReg() {
		return idReg;
	}

	public void setIdReg(long idReg) {
		this.idReg = idReg;
	}

	public int getIdLocacion() {
		return idLocacion;
	}

	public void setIdLocacion(int idLocacion) {
		this.idLocacion = idLocacion;
	}

	public Date getFecMedicion() {
		return fecMedicion;
	}

	public void setFecMedicion(Date fecMedicion) {
		this.fecMedicion = fecMedicion;
	}

	public Date getHorMedicion() {
		return horMedicion;
	}

	public void setHorMedicion(Date horMedicion) {
		this.horMedicion = horMedicion;
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

	public boolean isEsDeDia() {
		return esDeDia;
	}

	public void setEsDeDia(boolean esDeDia) {
		this.esDeDia = esDeDia;
	}

	public String getTxtClima() {
		return txtClima;
	}

	public void setTxtClima(String txtClima) {
		this.txtClima = txtClima;
	}

	public String getMetUnidad() {
		return metUnidad;
	}

	public void setMetUnidad(String metUnidad) {
		this.metUnidad = metUnidad;
	}

	public double getMetValor() {
		return metValor;
	}

	public void setMetValor(double metValor) {
		this.metValor = metValor;
	}

	public String getImpUnidad() {
		return impUnidad;
	}

	public void setImpUnidad(String impUnidad) {
		this.impUnidad = impUnidad;
	}

	public double getImpValor() {
		return impValor;
	}

	public void setImpValor(double impValor) {
		this.impValor = impValor;
	}

	public Date getFecReg() {
		return fecReg;
	}

	public void setFecReg(Date fecReg) {
		this.fecReg = fecReg;
	}

	public Date getHorReg() {
		return horReg;
	}

	public void setHorReg(Date horReg) {
		this.horReg = horReg;
	}
	
}
