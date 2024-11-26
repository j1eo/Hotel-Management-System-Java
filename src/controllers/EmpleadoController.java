package controllers;

import beans.EmpleadoBean;
import beans.HotelBean;
import beans.RecamareraBean;
import services.EmpleadoService;

import java.util.ArrayList;


public class EmpleadoController {

	private EmpleadoService empleadoService;

	public EmpleadoController() {
		this.empleadoService = new EmpleadoService();
	}

	public void agregarRecamarera(RecamareraBean recamarera, HotelBean hotel) {
		empleadoService.agregarRecamarera(recamarera, hotel);
	}

	public void agregarEmpleado(EmpleadoBean empleado, HotelBean hotel) {
		empleadoService.agregarEmpleado(empleado, hotel);
	}

	public ArrayList<RecamareraBean> listarRecamareras() {
		return empleadoService.listarRecamareras();
	}
	
	public ArrayList<EmpleadoBean> listarEmpleados() {
		return empleadoService.listarEmpleados();
	}

	public double calcularSalarioDeRecamarera(String nombre) {
		return empleadoService.calcularSalarioDeRecamarera(nombre);
	}
}
