package beans;

import java.util.ArrayList;

public abstract class RecamareraBean extends EmpleadoBean {
    private static final long serialVersionUID = 1L;

    private int recamareraId; // Añadido
    private ArrayList<HabitacionBean> habitacionesAsignadas;
    private String nivelExperiencia;

    // Constructor vacío
    public RecamareraBean() {
        this.habitacionesAsignadas = new ArrayList<>();
    }

    // Constructor con parámetros
    public RecamareraBean(String nombre, String nivelExperiencia) {
        super(nombre, 1500.00);
        this.nivelExperiencia = nivelExperiencia;
        this.habitacionesAsignadas = new ArrayList<>();
    }

    public int getRecamareraId() { // Añadido
        return recamareraId;
    }

    public void setRecamareraId(int recamareraId) { // Añadido
        this.recamareraId = recamareraId;
    }

    public ArrayList<HabitacionBean> getHabitacionesAsignadas() {
        return habitacionesAsignadas;
    }

    public void setHabitacionesAsignadas(ArrayList<HabitacionBean> habitacionesAsignadas) {
        this.habitacionesAsignadas = habitacionesAsignadas;
    }

    public String getNivelExperiencia() {
        return nivelExperiencia;
    }

    public void setNivelExperiencia(String nivelExperiencia) {
        this.nivelExperiencia = nivelExperiencia;
    }

    // Método abstracto para validación de habitaciones
    public abstract boolean puedeAtender(String tipoHabitacion);

    // Implementación del cálculo del salario
    @Override
    public double calcularSalario() {
        double salarioBase = getSalarioBase();
        double totalComisiones = 0;

        for (HabitacionBean habitacion : habitacionesAsignadas) {
            totalComisiones += calcularComision(habitacion);
        }

        return salarioBase + totalComisiones;
    }

    // Método auxiliar para calcular comisiones
    private double calcularComision(HabitacionBean habitacion) {
        switch (habitacion.getTipo().toLowerCase()) {
            case "sencilla":
                return 30;
            case "doble":
                return 50;
            case "penhouse":
                return 100;
            default:
                throw new IllegalArgumentException("Tipo de habitación inválido: " + habitacion.getTipo());
        }
    }
}
