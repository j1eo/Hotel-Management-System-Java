package controllers;

import beans.RecamareraBean;
import services.EmpleadoService;

import java.util.ArrayList;


public class EmpleadoController {

	private EmpleadoService empleadoService;

	public EmpleadoController() {
		this.empleadoService = new EmpleadoService();
	}

	public void agregarRecamarera(RecamareraBean recamarera) {
		empleadoService.agregarRecamarera(recamarera);
	}

	public ArrayList<RecamareraBean> listarRecamareras() {
		return empleadoService.listarRecamareras();
	}

	public double calcularSalarioDeRecamarera(String nombre) {
		return empleadoService.calcularSalarioDeRecamarera(nombre);
	}
}
