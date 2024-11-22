public class Vendedor extends Empleado {
    private double comisiones;
    private Hotel hotelAsignado;

    public Vendedor(String nombre, double salarioBase, double comisiones, Hotel hotelAsignado) {
        super(nombre, salarioBase);
        this.comisiones = comisiones;
        this.hotelAsignado = hotelAsignado;
    }

    public double calcularSalario() {
        return salarioBase + comisiones;
    }
}
