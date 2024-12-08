package beans;

public class RecamareraExperimentadaBean extends RecamareraBean {

	private static final long serialVersionUID = 1L;

	private static String nivelExperiencia = "Experimentada";

	public RecamareraExperimentadaBean() {

	}// constructor vacio

	// Constructor que pasa una lista vacía de habitaciones
	public RecamareraExperimentadaBean(String nombre) {
		super(nombre, nivelExperiencia); // Llamada al constructor de la clase base
	}

	@Override
	public boolean puedeAtender(String tipoHabitacion) {
		return tipoHabitacion.equalsIgnoreCase("Doble") || tipoHabitacion.equalsIgnoreCase("Penhouse");
	}

}
