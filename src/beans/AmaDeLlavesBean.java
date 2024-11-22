package beans;

public class AmaDeLlavesBean extends RecamareraBean {
    private static final long serialVersionUID = 1L;

    public AmaDeLlavesBean() {
    }

    public AmaDeLlavesBean(String nombre, double salarioBase) {
        super(nombre, "Ama de Llaves", salarioBase);
    }

    @Override
    public boolean puedeAtender(String tipoHabitacion) {
        return true; // Puede atender cualquier habitación
    }
}
