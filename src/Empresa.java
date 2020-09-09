import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Empresa implements Interface {
	private String CUIT;
	private String nombre;
	private ArrayList<Deposito> depositos;
	private Map<String, Transporte> transportes;
	private Map<String, Viaje> destinos;

	Empresa(String CUIT, String nombre) {
		if (nombre == null || nombre.length() == 0 || CUIT.length() != 11) {
			throw new RuntimeException ("El nombre no puede estar vacio y/o el CUIT debe ser ingresado sin guiones y debe tener 11 caracteres");
		}
		this.CUIT = CUIT;
		this.nombre = nombre;
		this.depositos = new ArrayList<>();
		this.transportes = new HashMap<>();
		this.destinos = new HashMap<>();
	}

	int agregarDeposito(double capacidad, boolean frigorifico, boolean propio) {
		// codigo de deposito = posicion en el array. Entonces, como size() arranca a
		// contar desde el uno, antes de agregar
		// el nuevo deposito ya tengo su codigo utilizando size()
		int codigoDeposito = depositos.size();
		Deposito nuevo = new Deposito(capacidad, frigorifico, propio, codigoDeposito);
		depositos.add(nuevo);
		return codigoDeposito;
	}

	int agregarDepTercerizFrio(double capacidad, double costoPorTonelada) {
		int codigoDeposito = depositos.size();
		Deposito depo = new Deposito(capacidad, true, true, codigoDeposito, costoPorTonelada);
		depositos.add(depo);
		return codigoDeposito;
	}

	void agregarDestino(String destino, int km) {
		Viaje viaje = new Viaje(km, destino);
		destinos.put(destino, viaje);
	}

	void agregarTrailer(String idTransp, double cargaMax, double capacidad, boolean frigorifico, double costoKm,
			double segCarga) {
		chequearID(idTransp);
		Trailer trailer = new Trailer(idTransp, cargaMax, capacidad, frigorifico, costoKm, segCarga);
		transportes.put(idTransp, trailer);
	}

	void agregarMegaTrailer(String idTransp, double cargaMax, double capacidad, boolean frigorifico, double costoKm,
			double segCarga, double costoFijo, double comida) {
		chequearID(idTransp);
		MegaTrailer megaTrai = new MegaTrailer(idTransp, cargaMax, capacidad, frigorifico, costoKm, segCarga, costoFijo,
				comida);
		transportes.put(idTransp, megaTrai);
	}

	void agregarFlete(String idTransp, double cargaMax, double capacidad, double costoKm, int acomp,
			double costoPorAcom) {
		chequearID(idTransp);
		Flete flete = new Flete(idTransp, cargaMax, capacidad, costoKm, acomp, costoPorAcom);
		transportes.put(idTransp, flete);
	}

	void asignarDestino(String idTransp, String destino) {
		Transporte transp = buscarTransporte(idTransp);
		Viaje viaje = buscarViaje(destino);
		if (transp == null || viaje == null) {
			throw new RuntimeException ("El transporte o el destino no existen");
		}
			transp.setViaje(viaje);
	}

	boolean incorporarPaquete(String destino, double peso, double volumen, boolean frio) {
		for (Deposito depo : depositos) {
			if (depo.incorporarPaquete(destino, peso, volumen, frio)) {
				return true;
			}
		}
		return false;
	}

	double cargarTransporte(String idTransp) {
		Transporte transp = buscarTransporte(idTransp);
		if (transp != null) {
			cargar(transp);
			return (transp.getCapacidadMaxima() - transp.getCapacidadActual());
		}
		return 0;
	}
	
	private void cargar(Transporte transp) {
		for (Deposito depo : depositos) {
			if (depo.getFrigorifico() == transp.getRefrigeracion()) {
				depo.cargar(transp);
			}
		}
	}

	void iniciarViaje(String idTransp) {
		Transporte transp = buscarTransporte(idTransp);
		if (transp != null && !transp.enViaje() && transp.getCapacidadActual() < transp.getCapacidadMaxima()) {
			transp.setEnViaje(true);
		}
	}

	void finalizarViaje(String idTransp) {
		Transporte transp = buscarTransporte(idTransp);
		if (transp != null && transp.enViaje()) {
			transp.finalizarViaje();
		}
	}

	double obtenerCostoViaje(String idTransp) {
		Transporte transp = buscarTransporte(idTransp);
		if (transp != null) {
			transp.costoViaje();
			return transp.getViaje().getCostoAdicional();
		}
		return 0;
	}
		
	String obtenerTransporteIgual(String idTransp) {
		Transporte transporte = buscarTransporte(idTransp);
		if (transporte != null) {
			for (Map.Entry<String, Transporte> i : transportes.entrySet()) {
				if (transporte.equals(i.getValue()) && !(transporte.getID().equals(i.getKey()))) {
					return i.getKey();
				}
			}
		}
		return null;
	}
	
	private Transporte buscarTransporte(String idTransp) {
		return transportes.get(idTransp);
	}

	private Viaje buscarViaje(String destino) {
		return destinos.get(destino);
	}
	
	private void chequearID(String idTransp) {
		if (transportes.containsKey(idTransp)) {
			throw new RuntimeException("Ya existe un transporte con ese ID");
		}
	}

	public String toString() {
		int transpEnViaje = 0;
		double cantPaquetes = 0;
		int depTerc = 0;
		for (String i: transportes.keySet()) {
			if (transportes.get(i).enViaje()) {
				transpEnViaje += 1;
			}
		}
		for (Deposito deposito : depositos) {
			cantPaquetes += deposito.getCantPaquetes();
			if (deposito.tercerizado()) {
				depTerc +=1;
			}
		}
		StringBuilder ret = new StringBuilder("CUIT: "); ret.append(this.CUIT);
		ret.append("\nNombre: "); ret.append(this.nombre);
		ret.append("\nTransportes total: "); ret.append(transportes.size());
		ret.append("\nTransportes en viaje: "); ret.append(transpEnViaje);
		ret.append("\nTransportes estacionados: "); ret.append(transportes.size() - transpEnViaje);
		ret.append("\nDepositos total: "); ret.append(depositos.size());
		ret.append("\nDepositos propios: "); ret.append(depositos.size() - depTerc);
		ret.append("\nDepositos tercerizados: "); ret.append(depTerc);
		ret.append("\nCantidad de paquetes en depositos: "); ret.append(cantPaquetes);
		return ret.toString();
	}
}