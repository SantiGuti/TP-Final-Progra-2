
class MegaTrailer extends Camion {
	private double gastoComida;
	private double gastoFijo;

	MegaTrailer(String idTransp, double cargaMax, double capacidad, boolean frigorifico, double costoKm,
			double segCarga, double costoFijo, double comida) {
		super(idTransp, cargaMax, capacidad, frigorifico, costoKm, segCarga);
		if (costoFijo < 0 || comida < 0) {
			throw new RuntimeException ("El gasto fijo o el gasto por comida no puede ser negativo");
		}
		this.gastoFijo = costoFijo;
		this.gastoComida = comida;

	}

	void costoViaje() {
		super.costoViaje();
		getViaje().setCostoAdicional(getViaje().getCostoAdicional() + gastoComida + gastoFijo);
	}

	public void setViaje(Viaje v) {
		if (v.getCantDeKM() > 500) {
			super.setViaje(v);
		}
	}

	@Override
	public boolean equals(Object objeto) {
		if (objeto == null) {
			return false;
		}
		return super.equals(objeto) && getClass() == objeto.getClass();
	}
}
