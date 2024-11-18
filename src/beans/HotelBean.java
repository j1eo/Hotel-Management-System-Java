package beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class HotelBean implements Serializable {


	private static final long serialVersionUID = 1L;
	
	private String nombre;
	private int estrellas;
	private List<HabitacionBean> habitaciones;

	public HotelBean() {
		habitaciones = new ArrayList<>();
	}

	public HotelBean(String nombre, int estrellas) {
        this.nombre = nombre;
        this.estrellas = estrellas;
        this.habitaciones = new ArrayList<>();
    }

	// Métodos getter y setter
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getEstrellas() {
		return estrellas;
	}

	public void setEstrellas(int estrellas) {
		this.estrellas = estrellas;
	}

	public List<HabitacionBean> getHabitaciones() {
		return habitaciones;
	}

	public void setHabitaciones(List<HabitacionBean> habitaciones) {
		this.habitaciones = habitaciones;
	}

	// Métodos adicionales para manipular la lista
	public void agregarHabitacion(HabitacionBean habitacion) {
		this.habitaciones.add(habitacion);
	}

	public void eliminarHabitacion(HabitacionBean habitacion) {
		this.habitaciones.remove(habitacion);
	}

	public int contarHabitaciones() {
		return this.habitaciones.size();
	}

}
