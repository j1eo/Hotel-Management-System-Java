package beans;



public class RecamareraAuxiliarBean extends RecamareraBean {

	private static final long serialVersionUID = 1L;
	private static String nivelExperiencia = "Auxiliar";
	
	public RecamareraAuxiliarBean() {}

    // Constructor que pasa una lista vacía de habitaciones
    public RecamareraAuxiliarBean(String nombre) {
        super(nombre, nivelExperiencia);
    }

    @Override
    public boolean puedeAtender(String tipoHabitacion) {
        return tipoHabitacion.equalsIgnoreCase("Sencilla") || tipoHabitacion.equalsIgnoreCase("Doble");
    }
}
