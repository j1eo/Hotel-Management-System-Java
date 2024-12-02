package beans;

import java.io.Serializable;

public abstract class EmpleadoBean implements Serializable {
    private static final long serialVersionUID = 1L;

    private int empleadoId;
    private String nombre;
    private double salarioBase;

    public EmpleadoBean() {}

    public EmpleadoBean(String nombre, double salarioBase) {
        this.nombre = nombre;
        this.salarioBase = salarioBase;
    }

    public int getEmpleadoId() {
        return empleadoId;
    }

    public void setEmpleadoId(int empleadoId) {
        this.empleadoId = empleadoId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getSalarioBase() {
        return salarioBase;
    }

    public void setSalarioBase(double salarioBase) {
        this.salarioBase = salarioBase;
    }

    public abstract double calcularSalario();
}
