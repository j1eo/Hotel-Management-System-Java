import java.util.List;

public class Gerente extends Empleado {
    private double bono;
    private List<Hotel> hotelesAsignados;

    public Gerente(String nombre, double salarioBase, double bono, List<Hotel> hotelesAsignados) {
        super(nombre, salarioBase);
        this.bono = bono;
        this.hotelesAsignados = hotelesAsignados;
    }

    public double calcularSalario() {
        return salarioBase + bono;
    }

    public List<Hotel> getHotelesAsignados() {
        return hotelesAsignados;
    }
}
