package beans;

import java.util.ArrayList;

public abstract class RecamareraBean extends EmpleadoBean {
	private static final long serialVersionUID = 1L;

	private ArrayList<HabitacionBean> habitacionesAsignadas;

	private String nivelExperiencia; // Nivel de experiencia agregado

	public RecamareraBean() {
	} // Constructor vacio

	public RecamareraBean(String nombre, String nivelExperiencia, double salarioBase,
			ArrayList<HabitacionBean> habitacionesAsignadas) {
		super(nombre, salarioBase);
		this.nivelExperiencia = nivelExperiencia;
		this.habitacionesAsignadas = habitacionesAsignadas;

	}

	// Método abstracto para verificar si puede atender un tipo de habitación
	public abstract boolean puedeAtender(String tipoHabitacion);

	public double calcularSalario() {
		double totalComision = 0.0;
		for (HabitacionBean habitacion : habitacionesAsignadas) {
			totalComision += habitacion.getComisionPorHabitacion();
		}
		return getSalarioBase() + totalComision;
	}

	public ArrayList<HabitacionBean> getTotalHabitaciones() {
        return habitacionesAsignadas; // Devolver la lista de habitaciones asignadas
    }

	public void setTotalHabitaciones(ArrayList<HabitacionBean> habitacionesAsignadas) {
		this.habitacionesAsignadas = habitacionesAsignadas;
	}

	public String getNivelExperiencia() {
		return nivelExperiencia;
	}

	public void setNivelExperiencia(String nivelExperiencia) {
		this.nivelExperiencia = nivelExperiencia;
	}
}
