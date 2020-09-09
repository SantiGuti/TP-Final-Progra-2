
class Flete extends Transporte {
	private int acompaniantes;
	private double precioAcompaniantes;

	Flete(String idTransp, double cargaMax, double capacidad, double costoKm, int acomp, double costoPorAcom) {
		super(idTransp, cargaMax, capacidad, false, costoKm);
		if (acomp < 0 || costoPorAcom < 0) {
			throw new RuntimeException ("Los acompañantes y/o el precio por estos no puede ser negativo");
		}
		this.acompaniantes = acomp;
		this.precioAcompaniantes = costoPorAcom;

	}

	void costoViaje() {
		super.costoViaje();
		getViaje().setCostoAdicional(getViaje().getCostoAdicional() + (acompaniantes * precioAcompaniantes));
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
