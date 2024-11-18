package beans;

import java.util.ArrayList;

public class RecamareraPrincipianteBean extends RecamareraBean{

	private static final long serialVersionUID = 1L;
	private static String nivelExperiencia = "Principiante";

	public RecamareraPrincipianteBean() {
	}//constructor vacio

	public RecamareraPrincipianteBean(String nombre, double salarioBase, ArrayList<HabitacionBean> habitacionesAsignadas) {
		super(nombre, nivelExperiencia, salarioBase, habitacionesAsignadas);

	}

	@Override
	public boolean puedeAtender(String tipoHabitacion) {
		return tipoHabitacion.equalsIgnoreCase("Sencilla") || tipoHabitacion.equalsIgnoreCase("Doble");
	}

}
