package beans;

import java.util.ArrayList;

public class AmaDeLlavesBean extends RecamareraBean {

	private static final long serialVersionUID = 1L;
	private static String nivelExperiencia = "Experimentada";

	public AmaDeLlavesBean() {
		
	}//constructor vacio

	public AmaDeLlavesBean(String nombre, double salarioBase, ArrayList<HabitacionBean> habitacionesAsignadas) {
		super(nombre, nivelExperiencia, salarioBase, habitacionesAsignadas);

	}

	@Override
	public boolean puedeAtender(String tipoHabitacion) {
		return true;
	}

}
