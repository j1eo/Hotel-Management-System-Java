package beans;

import java.io.Serializable;

public class HabitacionBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private String ID;
    private String tipo;

    public HabitacionBean(String ID, String tipo) {
        this.ID = ID;
        this.tipo = tipo;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
