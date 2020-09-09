
class Camion extends Transporte {

	private double seguroDeCarga;

	Camion(String idTransp, double cargaMax, double capacidad, boolean frigorifico, double costoKm, double segCarga) {
		super(idTransp, cargaMax, capacidad, frigorifico, costoKm);
		if (segCarga < 0) {
			throw new RuntimeException ("El seguro de carga no puede ser negativo");
		}
		this.seguroDeCarga = segCarga;

	}

	void costoViaje() {
		super.costoViaje();
		getViaje().setCostoAdicional(getViaje().getCostoAdicional() + (seguroDeCarga));
	}

	double getSeguroDeCarga() {
		return this.seguroDeCarga;
	}

	void setSeguroDeCarga(double seguro) {
		this.seguroDeCarga = seguro;
	}

	public void setViaje(Viaje v) {
		super.setViaje(v);
	}

	@Override
	public boolean equals(Object objeto) {
		if (objeto == null) {
			return false;
		}
		return super.equals(objeto) && getClass() == objeto.getClass();
	}

}
