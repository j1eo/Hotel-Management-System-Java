package beans;

import java.io.Serializable;
import java.sql.Date;

public class ReservacionBean implements Serializable{

	private static final long serialVersionUID = 1L;
	private int reservacionId;
    private int vendedorId;
    private int hotelId;
    private int habitacionId;
    private int numeroPersonas;
    private Date fechaRegistro;
    private int duracionEstadia;
    private double costo;

    // Constructor vacío
    public ReservacionBean() {}

    // Constructor con parámetros
    public ReservacionBean(int reservacionId, int vendedorId, int hotelId, int habitacionId, int numeroPersonas, Date fechaRegistro, int duracionEstadia, double costo) {
        this.reservacionId = reservacionId;
        this.vendedorId = vendedorId;
        this.hotelId = hotelId;
        this.habitacionId = habitacionId;
        this.numeroPersonas = numeroPersonas;
        this.fechaRegistro = fechaRegistro;
        this.duracionEstadia = duracionEstadia;
        this.costo = costo;
    }

    // Getters y setters
    public int getReservacionId() {
        return reservacionId;
    }

    public void setReservacionId(int reservacionId) {
        this.reservacionId = reservacionId;
    }

    public int getVendedorId() {
        return vendedorId;
    }

    public void setVendedorId(int vendedorId) {
        this.vendedorId = vendedorId;
    }

    public int getHotelId() {
        return hotelId;
    }

    public void setHotelId(int hotelId) {
        this.hotelId = hotelId;
    }

    public int getHabitacionId() {
        return habitacionId;
    }

    public void setHabitacionId(int habitacionId) {
        this.habitacionId = habitacionId;
    }

    public int getNumeroPersonas() {
        return numeroPersonas;
    }

    public void setNumeroPersonas(int numeroPersonas) {
        this.numeroPersonas = numeroPersonas;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public int getDuracionEstadia() {
        return duracionEstadia;
    }

    public void setDuracionEstadia(int duracionEstadia) {
        this.duracionEstadia = duracionEstadia;
    }

    public double getCosto() {
        return costo;
    }

    public void setCosto(double costo) {
        this.costo = costo;
    }
}
