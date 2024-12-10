package beans;

import java.io.Serializable;
import java.util.Date;

public class ComisionVendedorBean extends ComisionBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private String tipoHabitacion;
    private int numeroPersonas;

    // Constructor vacío
    public ComisionVendedorBean() {}

    // Constructor completo
    public ComisionVendedorBean(int comisionId, int empleadoId, int hotelId, double monto, Date fecha, String tipoHabitacion, int numeroPersonas) {
        super(comisionId, empleadoId, hotelId, monto, fecha);
        this.tipoHabitacion = tipoHabitacion;
        this.numeroPersonas = numeroPersonas;
    }

    // Getters y setters
    public String getTipoHabitacion() {
        return tipoHabitacion;
    }

    public void setTipoHabitacion(String tipoHabitacion) {
        this.tipoHabitacion = tipoHabitacion;
    }

    public int getNumeroPersonas() {
        return numeroPersonas;
    }

    public void setNumeroPersonas(int numeroPersonas) {
        this.numeroPersonas = numeroPersonas;
    }
}
