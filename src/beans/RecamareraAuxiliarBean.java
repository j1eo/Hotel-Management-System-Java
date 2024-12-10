package beans;



public class RecamareraAuxiliar extends RecamareraBean {

	private static final long serialVersionUID = 1L;
	private static String nivelExperiencia = "Auxiliar";
	
	public RecamareraAuxiliar() {}

    // Constructor que pasa una lista vacía de habitaciones
    public RecamareraAuxiliar(String nombre) {
        super(nombre, nivelExperiencia);
    }

    @Override
    public boolean puedeAtender(String tipoHabitacion) {
        return tipoHabitacion.equalsIgnoreCase("Sencilla") || tipoHabitacion.equalsIgnoreCase("Doble");
    }
}
