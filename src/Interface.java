
public interface Interface {
	
	public static void agregarDeposito(double volumenMax, boolean frio, boolean propio) {}
	public static void agregarDepTercerizFrio(double capacidad, double costoPorTonelada) {}
	public static void agregarDestino(String destino, int km) {}
	public static void agregarTrailer(String idTransp, double cargaMax, double capacidad, boolean frigorifico, double costoKm, double segCarga) {}
	public static void agregarMegaTrailer(String idTransp, double cargaMax, double capacidad, boolean frigorifico, double costoKm, double segCarga, double costoFijo, double comida) {}
	public static void agregarFlete(String idTransp, double cargaMax, double capacidad, double costoKm, int acomp, double costoPorAcom) {}
	public static void asignarDestino(String idTransp, String destino) {}
	public static void incorporarPaquete(String destino, double peso, double volumen, boolean frio) {}
	public static void cargarTransporte(String idTransp) {}
	public static void iniciarViaje(String idTransp) {}
	public static void finalizarViaje(String idTransp) {}
	public static void obtenerCostoViaje(String idTransp) {}
}
