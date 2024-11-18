package beans;


public class GerenteBean extends EmpleadoBean {

	private static final long serialVersionUID = 1L;
	private double bono;
	
	 public GerenteBean() {} // Constructor vacio

    public GerenteBean(String nombre, double salarioBase, double bono) {
        super(nombre, salarioBase);
        this.bono = bono;
    }

    public double getBono() {
        return bono;
    }

    public void setBono(double bono) {
        this.bono = bono;
    }

    public double calcularSalario() {
        return getSalarioBase() + bono;
    }

}
