package beans;

import java.io.Serializable;

public class HabitacionBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private int habitacionId;
    private int hotelId;
    private String id;
    private String tipo;
    private boolean disponible;
    private int numeroPersonas;
    private Integer recamareraId;  // Puede ser nulo

    public HabitacionBean(int habitacionId, int hotelId, String id, String tipo, boolean disponible, int numeroPersonas, Integer recamareraId) {
        this.habitacionId = habitacionId;
        this.hotelId = hotelId;
        this.id = id;
        this.tipo = tipo;
        this.disponible = disponible;
        this.numeroPersonas = numeroPersonas;
        this.recamareraId = recamareraId;
    }

    // Getters y Setters
    public int getHabitacionId() {
        return habitacionId;
    }

    public void setHabitacionId(int habitacionId) {
        this.habitacionId = habitacionId;
    }

    public int getHotelId() {
        return hotelId;
    }

    public void setHotelId(int hotelId) {
        this.hotelId = hotelId;
    }

    public String getID() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public int getNumeroPersonas() {
        return numeroPersonas;
    }

    public void setNumeroPersonas(int numeroPersonas) {
        this.numeroPersonas = numeroPersonas;
    }

    public Integer getRecamareraId() {
        return recamareraId;
    }

    public void setRecamareraId(Integer recamareraId) {
        this.recamareraId = recamareraId;
    }
}
