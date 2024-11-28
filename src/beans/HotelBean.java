package beans;

import java.io.Serializable;
import java.util.ArrayList;

public class HotelBean implements Serializable {
    
    private static final long serialVersionUID = 1L;

    private int hotelId;
    private String nombre;
    private int estrellas;
    private String direccion; // Nuevo atributo
    private String telefono;  // Nuevo atributo
    private ArrayList<HabitacionBean> habitaciones;
    private ArrayList<EmpleadoBean> empleados;

    public HotelBean() {
        this.habitaciones = new ArrayList<>();
        this.empleados = new ArrayList<>();
    }

    public HotelBean(String nombre, int estrellas, String direccion, String telefono) {
        this.nombre = nombre;
        this.estrellas = estrellas;
        this.direccion = direccion;
        this.telefono = telefono;
        this.habitaciones = new ArrayList<>();
        this.empleados = new ArrayList<>();
    }

    public int getHotelId() {
        return hotelId;
    }

    public void setHotelId(int hotelId) {
        this.hotelId = hotelId;
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

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
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
