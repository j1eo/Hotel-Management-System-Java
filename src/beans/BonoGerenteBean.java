package beans;

import java.io.Serializable;
import java.util.Date;

public class BonoGerenteBean extends ComisionBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private String mes;

    // Constructor vacío
    public BonoGerenteBean() {}

    // Constructor completo
    public BonoGerenteBean(int bonoId, int empleadoId, int hotelId, double monto, Date fecha, String mes) {
        super(bonoId, empleadoId, hotelId, monto, fecha);
        this.mes = mes;
    }

    // Getters y setters
    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }
}
