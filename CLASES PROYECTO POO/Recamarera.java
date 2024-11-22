public class Recamarera extends Empleado {
    private int totalHabitacionesLimpias;
    private String nivelExperiencia;
    private Hotel hotelAsignado;

    public Recamarera(String nombre, double salarioBase, int totalHabitacionesLimpias, String nivelExperiencia, Hotel hotelAsignado) {
        super(nombre, salarioBase);
        this.totalHabitacionesLimpias = totalHabitacionesLimpias;
        this.nivelExperiencia = nivelExperiencia;
        this.hotelAsignado = hotelAsignado;
    }

    public double calcularSalario() {
        double comisionPorHabitacion = hotelAsignado.calcularComisionRecamarera(nivelExperiencia);
        return salarioBase + (totalHabitacionesLimpias * comisionPorHabitacion);
    }

    public String getNivelExperiencia() {
        return nivelExperiencia;
    }
}
