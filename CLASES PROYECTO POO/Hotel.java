import java.util.List;

public class Hotel {
    private String nombre;
    private int nivelEstrellas;
    private List<Habitacion> habitaciones;
    private List<Empleado> empleados;

    public Hotel(String nombre, int nivelEstrellas, List<Habitacion> habitaciones, List<Empleado> empleados) {
        this.nombre = nombre;
        this.nivelEstrellas = nivelEstrellas;
        this.habitaciones = habitaciones;
        this.empleados = empleados;
    }

    public double calcularComisionRecamarera(String nivelExperiencia) {
        return 0; // Implementar lógica basada en reglas.
    }

    public String getNombre() {
        return nombre;
    }

    public int getNivelEstrellas() {
        return nivelEstrellas;
    }
}
