package beans;

public class GerenteBean extends EmpleadoBean {
    private static final long serialVersionUID = 1L;

    private double bono;
 

    public GerenteBean() {}

    public GerenteBean(String nombre, double bono) {
        super(nombre, 18000.0);
        this.bono = bono;
    }

    public double getBono() {
        return bono;
    }

    public void setBono(double bono) {
        this.bono = bono;
    }

    @Override
    public double calcularSalario() {
        return getSalarioBase() + bono;
    }
}
