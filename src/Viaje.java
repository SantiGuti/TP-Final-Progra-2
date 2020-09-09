
public class Viaje {
	private int cantDeKM;
	private String destino;
	private double costoAdicional;

	Viaje(int cantDeKM, String destino) {
		if (cantDeKM <= 0) {
			throw new RuntimeException("La cantidad de Km no puede ser 0 o negativa");
		}
		if (destino.length() == 0 || destino == null) {
			throw new RuntimeException ("El destino no puede estar vacio");
		}
		this.cantDeKM = cantDeKM;
		this.destino = destino;
		this.costoAdicional = 0;
	}

	int getCantDeKM() {
		return this.cantDeKM;
	}

	double getCostoAdicional() {
		return this.costoAdicional;
	}

	String getDestino() {
		return this.destino;
	}

	void setCostoAdicional(double costo) {
		this.costoAdicional = costo;
	}

	void setDestino(String destino) {
		this.destino = destino;
	}

}
