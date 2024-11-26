package beans;

import java.io.Serializable;
import java.util.ArrayList;

public class HotelBean implements Serializable {
	
	//AGREGAR DATOS BASICOS DE LOS OBJETOS EXTRA

    private static final long serialVersionUID = 1L;

    private String nombre;
    private int estrellas;
    private ArrayList<HabitacionBean> habitaciones;
    private ArrayList<EmpleadoBean> empleados; // Lista de recamareras asignadas

    // Constructor vacío
    public HotelBean() {
        this.habitaciones = new ArrayList<>();
        this.empleados = new ArrayList<>();
    }

    // Constructor con parámetros
    public HotelBean(String nombre, int estrellas) {
        this.nombre = nombre;
        this.estrellas = estrellas;
        this.habitaciones = new ArrayList<>();
        this.empleados = new ArrayList<>();
    }

    // Métodos getters y setters
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

    // Métodos para gestionar Empleados
    public ArrayList<EmpleadoBean> getEmpleados() {
        return empleados;
    }
    
    public void agregarEmpleado(EmpleadoBean empleado) {
        if (!empleados.contains(empleado)) {
            this.empleados.add(empleado);
        } else {
            System.out.println("El empleado ya está asignado a este hotel");
        }
    }

    public void agregarRecamarera(RecamareraBean recamarera) {
        if (!empleados.contains(recamarera)) {
            this.empleados.add(recamarera);
        } else {
            System.out.println("La recamarera ya está asignada a un hotel");
        }
    }

    public void eliminarEmpleado(EmpleadoBean empleado) {
        this.empleados.remove(empleado);
    }

    public boolean recamareraEstaTrabajando(RecamareraBean recamarera) {
        return empleados.contains(recamarera);
    }

    public boolean recamareraEstaTrabajando(String nombreRecamarera) {
        for (EmpleadoBean r : empleados) {
            if (r.getNombre().equalsIgnoreCase(nombreRecamarera)) {
                return true;
            }
        }
        return false;
    }
}
