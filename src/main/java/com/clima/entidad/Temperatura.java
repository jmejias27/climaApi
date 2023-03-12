package com.clima.entidad;

public class Temperatura {

	private SistemaMetrico sisMetrico;
	private SistemaImperial sisImperial;
	
	public SistemaMetrico getSisMetrico() {
		return sisMetrico;
	}
	public void setSisMetrico(SistemaMetrico sisMetrico) {
		this.sisMetrico = sisMetrico;
	}
	public SistemaImperial getSisImperial() {
		return sisImperial;
	}
	public void setSisImperial(SistemaImperial sisImperial) {
		this.sisImperial = sisImperial;
	}
}
