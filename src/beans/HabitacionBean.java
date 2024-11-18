package beans;

import java.io.Serializable;

public class HabitacionBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private String tipo; // Sencilla, Doble, Penhouse
	private double costoPorDia;
	private double comisionPorHabitacion;

	public HabitacionBean() {
	}

	public HabitacionBean(String tipo, double costoPorDia, int estrellas) {
        this.tipo = tipo;
        this.costoPorDia = costoPorDia;
        this.comisionPorHabitacion = calcularComisionPorHabitacion(tipo, estrellas);
    }

	// Getters y Setters
	

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getTipo() {
		return tipo;
	}

	public double getCostoPorDia() {
		return costoPorDia;
	}

	public void setCostoPorDia(double costoPorDia) {
		this.costoPorDia = costoPorDia;
	}

	public double getComisionPorHabitacion() {
		return comisionPorHabitacion;
	}

	public void setComisionPorHabitacion(double comisionPorHabitacion) {
		this.comisionPorHabitacion = comisionPorHabitacion;
	}

	// Método para calcular la comisión según el tipo de habitación y estrellas del
	// hotel
	private double calcularComisionPorHabitacion(String tipo, int estrellas) {
		switch (tipo.toLowerCase()) {
		//uso de operador ternario  condicion ? resultado si se cumple : resultado si no se cumple
		case "sencilla":
			return estrellas == 3 ? 30 : estrellas == 4 ? 50 : 70;
		case "doble":
			return estrellas == 3 ? 40 : estrellas == 4 ? 60 : 80;
		case "penhouse":
			return estrellas == 3 ? 60 : estrellas == 4 ? 80 : 100;
		default:
			return 0;
		}
	}

}
