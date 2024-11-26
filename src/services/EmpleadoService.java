package services;

import beans.EmpleadoBean;
import beans.HotelBean;
import beans.RecamareraBean;

import java.util.ArrayList;

public class EmpleadoService {

	private ArrayList<EmpleadoBean> empleados;
	private ArrayList<RecamareraBean> recamareras;

	public EmpleadoService() {
		this.recamareras = new ArrayList<>();
		this.empleados = new ArrayList<>();
	}

//Agregar un nuevo empleado cualquiera
	public void agregarEmpleado(EmpleadoBean empleado, HotelBean hotel) {
		this.empleados.add(empleado);
		hotel.agregarEmpleado(empleado); // Asociar al hotel
	}

	// Agregar una nueva recamarera y asociarla a un hotel
	public void agregarRecamarera(RecamareraBean recamarera, HotelBean hotel) {
		for (EmpleadoBean empleado : empleados) {
	        if (empleado instanceof RecamareraBean && empleado.equals(recamarera)) {
	            throw new IllegalArgumentException("La recamarera ya está asignada a un hotel.");
	        }}
	        
		this.empleados.add(recamarera);
		this.recamareras.add(recamarera);
		hotel.agregarEmpleado(recamarera); // Asociar al hotel
	}
	public ArrayList<EmpleadoBean> listarEmpleados(){
	        return new ArrayList<>(empleados);
	}

	// Listar todas las recamareras
	public ArrayList<RecamareraBean> listarRecamareras() {
		return new ArrayList<>(recamareras);
	}

	// Calcular el salario total de una recamarera
	public double calcularSalarioDeRecamarera(String nombre) {
		for (RecamareraBean recamarera : recamareras) {
			if (recamarera.getNombre().equalsIgnoreCase(nombre)) {
				return recamarera.calcularSalario();
			}
		}
		throw new IllegalArgumentException("Recamarera no encontrada: " + nombre);
	}
}
