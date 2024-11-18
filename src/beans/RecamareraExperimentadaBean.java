package beans;

import java.util.ArrayList;

public class RecamareraExperimentadaBean extends RecamareraBean{

	private static final long serialVersionUID = 1L;

	private static String nivelExperiencia = "Experimentada";

	public RecamareraExperimentadaBean() {
	}//constructor vacio

	public RecamareraExperimentadaBean(String nombre, double salarioBase, ArrayList<HabitacionBean> habitacionesAsignadas) {
		super(nombre, nivelExperiencia, salarioBase, habitacionesAsignadas);

	}

	@Override
	public boolean puedeAtender(String tipoHabitacion) {
		return tipoHabitacion.equalsIgnoreCase("Doble") || tipoHabitacion.equalsIgnoreCase("Penhouse");
	}

		

}
