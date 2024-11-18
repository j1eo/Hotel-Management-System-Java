package beans;

public class VendedorBean extends EmpleadoBean {

	private static final long serialVersionUID = 1L;
	private double comision;

	// Constructor vacío
	public VendedorBean() {}

	// Constructor completo
	public VendedorBean(String nombre, double salario, double comision) {
        super(nombre, salario);
        this.comision = comision;
    }

	// Getters y Setters
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
