package beans;

import java.io.Serializable;

public abstract class EmpleadoBean implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String nombre;
	private double salario;

	public EmpleadoBean() {
	}

	public EmpleadoBean(String nombre, double salario) {
		this.nombre = nombre;
		this.salario = salario;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public double getSalario() {
		return salario;
	}

	public void setSalario(double salario) {
		this.salario = salario;
	}
	// Método abstracto para las subclases de empleado
    public abstract double calcularSalario();
}