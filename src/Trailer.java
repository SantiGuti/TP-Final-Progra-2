
class Trailer extends Camion {

	Trailer(String idTransp, double cargaMax, double capacidad, boolean frigorifico, double costoKm, double segCarga) {

		super(idTransp, cargaMax, capacidad, frigorifico, costoKm, segCarga);

	}

	void costoViaje() {
		super.costoViaje();
	}

	public void setViaje(Viaje v) {
		if (v.getCantDeKM() < 500) {
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
