import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class Deposito {
	private double capacidadMaxima;
	private double capacidadActual;
	private boolean frigorifico;
	private boolean tercerizado;
	@SuppressWarnings("unused")
	private int numeroDeDeposito;//Se utiliza para devoler un numero de deposito al usuario.
	private double costoPorTonelada;
	private Map<String, ArrayList<Paquete>> paquetes;

	Deposito(double capacidadMaxima, boolean frigorifico, boolean tercerizado, int numeroDeDeposito) {
		chequearCap(capacidadMaxima);
		this.capacidadMaxima = capacidadMaxima;
		this.capacidadActual = capacidadMaxima;
		this.frigorifico = frigorifico;
		this.tercerizado = tercerizado;
		this.numeroDeDeposito = numeroDeDeposito;
		this.costoPorTonelada = 0;
		this.paquetes = new HashMap<>();
	}

	Deposito(double capacidadMaxima, boolean frigorifico, boolean tercerizado, int numeroDeDeposito,
			double costoPorTonelada) {
		chequearCap(capacidadMaxima);
		if (costoPorTonelada < 0) {
			throw new RuntimeException ("El costo por tonelada no puede ser negativo");
		}
		this.capacidadMaxima = capacidadMaxima;
		this.capacidadActual = capacidadMaxima;
		this.frigorifico = frigorifico;
		this.tercerizado = tercerizado;
		this.numeroDeDeposito = numeroDeDeposito;
		this.costoPorTonelada = costoPorTonelada;
		this.paquetes = new HashMap<>();
	}

	boolean incorporarPaquete(String destino, double peso, double volumen, boolean frio) {
		if (this.capacidadActual >= volumen && frigorifico == frio) {
			Paquete paque = new Paquete(destino, peso, volumen, frio);
			this.capacidadActual -= paque.getVolumen();
			if (paquetes.containsKey(destino)){
				paquetes.get(destino).add(paque);
				return true;
			}
			paquetes.put(destino, new ArrayList<>());
			paquetes.get(destino).add(paque);
			return true;
		}
		return false;
	}

	void cargar(Transporte transp) {
		String destino = transp.getViaje().getDestino();
		if (paquetes.containsKey(destino)) {
			ArrayList<Paquete> listaPaque = paquetes.get(destino);
			Iterator<Paquete> cont = listaPaque.iterator();
			double carga = 0;
			while (cont.hasNext() && transp.getCargaActual() != 0 && transp.getCapacidadActual() != 0) {
				Paquete paquete = cont.next();
				double aux = transp.cargar(paquete);
				if (aux > 0) {
					carga += aux;
					cont.remove();
				}
			}
			if (carga > 1000) {
				// Saco el proporcional por el costo de tonelada usada en un deposito
				// tercerizado. Si no es tercerizado,
				// costoPorTonelada es igual a cero entonces, la cuenta dará 0 y no aumentara el
				// costo de viaje.
				transp.getViaje().setCostoAdicional(transp.getViaje().getCostoAdicional() + (carga * costoPorTonelada) / 1000);
			}
			//Reemplazo la lista de mi hashmap que no fue actualizada con la version actualizada donde
			//quedaron los paquetes que no fueron cargados y eliminados los que si.
			if (listaPaque.size() != 0) {
				paquetes.put(destino, listaPaque);
			}
		}
	}

	double getCapacidadMaxima() {
		return this.capacidadMaxima;
	}

	boolean getFrigorifico() {
		return this.frigorifico;
	}
	
	boolean tercerizado() {
		return this.tercerizado;
	}
	
	double getCantPaquetes() {
		return paquetes.size();
	}
	
	private void chequearCap(double capacidadMaxima) {
		if (capacidadMaxima <= 0) {
			throw new RuntimeException("La capacidad no puede ser 0 o negativa");
		}
	}
	
}