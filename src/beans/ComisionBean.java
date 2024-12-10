package beans;

import java.io.Serializable;
import java.util.Date;

public class ComisionBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private int comisionId;
    private int empleadoId;
    private int hotelId;
    private double monto;
    private Date fecha;

    // Constructor vacío
    public ComisionBean() {}

    // Constructor completo
    public ComisionBean(int comisionId, int empleadoId, int hotelId, double monto, Date fecha) {
        this.comisionId = comisionId;
        this.empleadoId = empleadoId;
        this.hotelId = hotelId;
        this.monto = monto;
        this.fecha = fecha;
    }

    // Getters y setters
    public int getComisionId() {
        return comisionId;
    }

    public void setComisionId(int comisionId) {
        this.comisionId = comisionId;
    }

    public int getEmpleadoId() {
        return empleadoId;
    }

    public void setEmpleadoId(int empleadoId) {
        this.empleadoId = empleadoId;
    }

    public int getHotelId() {
        return hotelId;
    }

    public void setHotelId(int hotelId) {
        this.hotelId = hotelId;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
}
