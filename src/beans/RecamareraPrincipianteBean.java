package beans;



public class RecamareraPrincipianteBean extends RecamareraBean{

	private static final long serialVersionUID = 1L;
	private static String nivelExperiencia = "Principiante";
	
	public RecamareraPrincipianteBean() {}

    // Constructor que pasa una lista vacía de habitaciones
    public RecamareraPrincipianteBean(String nombre) {
        super(nombre, nivelExperiencia);
    }

    @Override
    public boolean puedeAtender(String tipoHabitacion) {
        return tipoHabitacion.equalsIgnoreCase("Sencilla") || tipoHabitacion.equalsIgnoreCase("Doble");
    }

}
