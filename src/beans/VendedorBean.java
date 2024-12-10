package beans;

public class VendedorBean extends EmpleadoBean {

    private static final long serialVersionUID = 1L;
    private int vendedorId;
    private double comision;

    // Constructor vacío
    public VendedorBean() {
    }

    // Constructor completo
    public VendedorBean(int vendedorId, String nombre) {
        super(nombre, 4500.00);
        this.vendedorId = vendedorId;
    }

    // Getters y Setters
    public int getVendedorId() {
        return vendedorId;
    }

    public void setVendedorId(int vendedorId) {
        this.vendedorId = vendedorId;
    }

    public double getComision() {
        return comision;
    }

    public void setComision(double comision) {
        this.comision = comision;
    }

    // Implementación de calcularSalario()
    @Override
    public double calcularSalario() {
        return getSalarioBase() + comision;
    }
}
