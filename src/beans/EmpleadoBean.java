package beans;

import java.io.Serializable;

public abstract class EmpleadoBean implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String nombre;
    private double salarioBase;
    
 // Constructor vacio
    public EmpleadoBean() {}

    public EmpleadoBean(String nombre, double salarioBase) {
        this.nombre = nombre;
        this.salarioBase = salarioBase;
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