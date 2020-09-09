
public class Paquete {

	private String destino;
	private double volumen;
	private double peso;
	private boolean frio;

	Paquete(String destino, double peso, double volumen, boolean frio) {
		if (peso <= 0 || volumen <= 0) {
			throw new RuntimeException("El peso o el volumen no pueden ser 0 o negativo");
		}
		if (destino == null || destino.length() == 0) {
			throw new RuntimeException("El destino no puede estar vacio");
		}
		this.destino = destino;
		this.peso = peso;
		this.volumen = volumen;
		this.frio = frio;
	}

	double getVolumen() {
		return this.volumen;
	}

	double getPeso() {
		return this.peso;
	}

	boolean getFrio() {
		return this.frio;
	}

	String getDestino() {
		return this.destino;
	}

	@Override
	public boolean equals(Object objeto) {
		if (this == objeto) {
			return true;
		}
		if (objeto == null) {
			return false;
		}
		if (this.getClass() == objeto.getClass()) {
			Paquete paque = (Paquete) objeto;
			return (!(paque.getDestino().equals(destino))
					|| paque.getFrio() != frio
					|| paque.getVolumen() != volumen
					|| paque.getPeso() != peso);
		}
		return false;
	}

}
