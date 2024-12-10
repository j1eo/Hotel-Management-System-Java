package beans;

import java.io.Serializable;
import java.util.Date;

public class ComisionRecamareraBean extends ComisionBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private String tipoHabitacion;
    private int cantidadHabitaciones;
    private int habitacionId; // Agregar esta variable

    // Constructor vacío
    public ComisionRecamareraBean() {}

    // Constructor completo
    public ComisionRecamareraBean(int comisionId, int empleadoId, int hotelId, double monto, Date fecha, String tipoHabitacion, int cantidadHabitaciones, int habitacionId) {
        super(comisionId, empleadoId, hotelId, monto, fecha);
        this.tipoHabitacion = tipoHabitacion;
        this.cantidadHabitaciones = cantidadHabitaciones;
        this.habitacionId = habitacionId; // Inicializar esta variable
    }

    // Getters y setters
    public String getTipoHabitacion() {
        return tipoHabitacion;
    }

    public void setTipoHabitacion(String tipoHabitacion) {
        this.tipoHabitacion = tipoHabitacion;
    }

    public int getCantidadHabitaciones() {
        return cantidadHabitaciones;
    }

    public void setCantidadHabitaciones(int cantidadHabitaciones) {
        this.cantidadHabitaciones = cantidadHabitaciones;
    }

    public int getHabitacionId() { // Agregar este método
        return habitacionId;
    }

    public void setHabitacionId(int habitacionId) { // Agregar este método
        this.habitacionId = habitacionId;
    }
}
