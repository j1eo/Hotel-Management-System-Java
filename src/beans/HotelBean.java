package beans;

import java.io.Serializable;
import java.util.ArrayList;

public class HotelBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private String nombre;
    private int estrellas;
    private ArrayList<HabitacionBean> habitaciones;

    public HotelBean() {
        habitaciones = new ArrayList<>();
    }

    public HotelBean(String nombre, int estrellas) {
        this.nombre = nombre;
        this.estrellas = estrellas;
        this.habitaciones = new ArrayList<>();
    }

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

    public ArrayList<HabitacionBean> getHabitaciones() {
        return habitaciones;
    }

    public void agregarHabitacion(HabitacionBean habitacion) {
        this.habitaciones.add(habitacion);
    }

    public void eliminarHabitacion(HabitacionBean habitacion) {
        this.habitaciones.remove(habitacion);
    }
}
