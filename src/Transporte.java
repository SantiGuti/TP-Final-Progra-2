import java.util.ArrayList;

public abstract class Transporte {

	private String ID;
	private double cargaMaxima;
	private double cargaActual;
	private double capacidadMaxima;
	private double capacidadActual;
	private boolean refrigeracion;
	private double costoPorKM;
	private boolean enViaje;
	private Viaje viaje;
	private ArrayList<Paquete> paquetes;

	Transporte(String idTransp, double cargaMax, double capacidad, boolean frigorifico, double costoKm) {
		if (cargaMax <= 0 || capacidad <= 0 || costoKm < 0) {
			throw new RuntimeException("La carga maxima y capacidad no pueden ser 0 o negativo/El costo por KM no puede ser negativo");
		}
		if (idTransp == null || idTransp.length() == 0) {
			throw new RuntimeException ("El ID de transporte no puede estar vacio.");
		}
		this.ID = idTransp;
		this.cargaMaxima = cargaMax;
		this.cargaActual = cargaMax;
		this.capacidadMaxima = capacidad;
		this.capacidadActual = capacidad;
		this.refrigeracion = frigorifico;
		this.costoPorKM = costoKm;
		this.viaje = null;
		this.enViaje = false;
		this.paquetes = new ArrayList<>();
	}

	void costoViaje() {
		viaje.setCostoAdicional(viaje.getCostoAdicional() + (viaje.getCantDeKM() * costoPorKM));
	}

	void finalizarViaje() {
		this.viaje = null;
		this.enViaje = false;
		this.paquetes = new ArrayList<>();
		this.capacidadActual = capacidadMaxima;
		this.cargaActual = cargaMaxima;
	}

	double cargar(Paquete paque) {
		if (this.capacidadActual >= paque.getVolumen()
			&& this.cargaActual >= paque.getPeso()
			&& this.viaje.getDestino().equals(paque.getDestino())
			&& this.refrigeracion == paque.getFrio()) {
			paquetes.add(paque);
			this.capacidadActual = this.capacidadActual - paque.getVolumen();
			this.cargaActual = this.cargaActual - paque.getPeso();
			return paque.getPeso();
		}
		return 0;
	}

	boolean enViaje() {
		return this.enViaje;
	}

	String getID() {
		return this.ID;
	}

	boolean getRefrigeracion() {
		return this.refrigeracion;
	}

	ArrayList<Paquete> getPaquetes() {
		return this.paquetes;
	}

	Viaje getViaje() {
		return this.viaje;
	}

	double getCostoPorKM() {
		return this.costoPorKM;
	}

	double getCapacidadMaxima() {
		return this.capacidadMaxima;
	}

	double getCapacidadActual() {
		return this.capacidadActual;
	}

	double getCargaMaxima() {
		return this.cargaMaxima;
	}

	double getCargaActual() {
		return this.cargaActual;
	}

	void setViaje(Viaje viaje) {
		this.viaje = viaje;
	}

	void setEnViaje(boolean viaje) {
		this.enViaje = viaje;
	}
	
	@Override
	public boolean equals(Object objeto) {
		if (objeto == null) {
			return false;
		}
		Transporte transporte = (Transporte) objeto;
		if ((transporte.getPaquetes().size() != paquetes.size())
			|| !(transporte.getViaje().getDestino().equals(viaje.getDestino()))
			|| (transporte.getCargaMaxima() - transporte.getCargaActual() != cargaMaxima - cargaActual)
			|| (transporte.getCapacidadMaxima() - transporte.getCapacidadActual() != capacidadMaxima - capacidadActual)) {
			return false;
		}
		ArrayList<Paquete> clon = new ArrayList<>();
		clon.addAll(transporte.paquetes);
		for (Paquete paque : this.paquetes) {
			clon.remove(paque);
		}
		return clon.size() == 0;
	}

}