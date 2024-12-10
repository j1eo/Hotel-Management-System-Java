package controllers;

import services.EmpleadoService;
import services.HotelService;
import views.EmpleadoViews;
import views.RecamareraViews;
import beans.EmpleadoBean;
import beans.RecamareraAuxiliarBean;
import beans.RecamareraExperimentadaBean;
import beans.RecamareraPrincipianteBean;
import beans.AmaDeLlavesBean;
import beans.GerenteBean;
import beans.HotelBean;

import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import java.util.List;

public class EmpleadoController {

	private final EmpleadoService empleadoService;
	private final EmpleadoViews empleadoViews;
	private final HotelService hotelService;
	private final RecamareraViews recamareraViews;
	private final RecamareraController recamareraController;

	public EmpleadoController(EmpleadoService empleadoService, EmpleadoViews empleadoViews, HotelService hotelService,
			RecamareraViews recamareraViews, RecamareraController recamareraController) {
		this.empleadoService = empleadoService;
		this.empleadoViews = empleadoViews;
		this.hotelService = hotelService;
		this.recamareraViews = recamareraViews;
		this.recamareraController = recamareraController;
	}

	public void evalOption(Object selectedOption) {
		if (selectedOption == null) {
			return;
		}

		String parsedSelectedOption = selectedOption.toString();

		switch (parsedSelectedOption) {
		case "Información General de Empleados":
			empleadoViews.menuGeneralEmpleado(this);
			break;
		case "Información de Recamareras":
			recamareraViews.menuRecamarera(recamareraController);
			break;
		case "Listar Empleados":
			listarEmpleadosPorHotel();
			break;
		case "Agregar Empleado":
			empleadoViews.mostrarAgregarEmpleadoMenu(this);
			break;
		case "Modificar Empleado":
			empleadoViews.mostrarModificarEmpleadoMenu(this);
			break;
		case "Eliminar Empleado":
			eliminarEmpleado();
			break;
		case "Salir":
			empleadoViews.mostrarMensaje("Saliendo del sistema de empleados...");
			break;
		default:
			empleadoViews.mostrarMensaje("Opción inválida. Intenta de nuevo.");
			break;
		}
	}

	public void evalEmpleado(int hotelId, Object selectedOption, String nombre) {
		if (selectedOption == null) {
			return;
		}

		String parsedSelectedOption = selectedOption.toString();

		switch (parsedSelectedOption) {
		case "Recamarera Auxiliar":
		case "Recamarera Principiante":
		case "Recamarera Experimentada":
		case "Ama de Llaves":
			try {
				empleadoService.agregarRecamarera(nombre, parsedSelectedOption, hotelId);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			break;
		case "Vendedor":
			try {
				empleadoService.agregarVendedor(nombre, hotelId);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			break;
		case "Gerente":
			try {
				empleadoService.agregarGerente(nombre, hotelId);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			break;
		case "Salir":
			empleadoViews.mostrarMensaje("Saliendo del sistema de empleados...");
			break;
		default:
			empleadoViews.mostrarMensaje("Opción inválida. Intenta de nuevo.");
			break;
		}
	}

	public void agregarEmpleado(String tipoEmpleado, String nombre, int hotelId) {
		try {
			empleadoService.agregarEmpleado(nombre, tipoEmpleado, hotelId);
			empleadoViews.mostrarMensaje("Empleado agregado exitosamente.");
		} catch (Exception e) {
			empleadoViews.mostrarMensaje("Error al agregar el empleado: " + e.getMessage());
		}
	}

	public void agregarGerente(String nombre, int hotelId) {
		try {
			double bono = 2000.0; // Bono fijo
			empleadoService.agregarGerente(nombre, hotelId);
			empleadoViews.mostrarMensaje("Gerente agregado exitosamente.");
		} catch (Exception e) {
			empleadoViews.mostrarMensaje("Error al agregar el gerente: " + e.getMessage());
		}
	}

	public void modificarEmpleado(int HotelId, int empleadoId, String nombre, String nuevoTipoEmpleado) {
		try {

			boolean modificado = empleadoService.modificarEmpleado(HotelId, empleadoId, nombre, nuevoTipoEmpleado);
			if (modificado) {
				empleadoViews.mostrarMensaje("Empleado modificado exitosamente.");
			} else {
				empleadoViews.mostrarMensaje("Error al modificar el empleado.");
			}
		} catch (Exception e) {
			empleadoViews.mostrarMensaje("Error al modificar el empleado: " + e.getMessage());
		}
	}

	private void eliminarEmpleado() {
		try {
			int empleadoId = Integer.parseInt(JOptionPane.showInputDialog("Ingresa el ID del empleado a eliminar:"));
			boolean eliminado = empleadoService.eliminarEmpleado(empleadoId);
			if (eliminado) {
				empleadoViews.mostrarMensaje("Empleado eliminado exitosamente.");
			} else {
				empleadoViews.mostrarMensaje("Error al eliminar el empleado.");
			}
		} catch (Exception e) {
			empleadoViews.mostrarMensaje("Error al eliminar el empleado: " + e.getMessage());
		}
	}

	private void listarEmpleadosPorHotel() {
		try {
			int hotelId = Integer.parseInt(JOptionPane.showInputDialog("Ingresa el ID del hotel:"));

			// Obtener el nombre del hotel para buscar la información completa
			HotelBean hotel = null;
			for (HotelBean h : hotelService.listarHoteles()) {
				if (h.getHotelId() == hotelId) {
					hotel = h;
					break;
				}
			}

			if (hotel == null) {
				throw new IllegalArgumentException("Hotel no encontrado con ID: " + hotelId);
			}

			List<String> empleados = empleadoService.listarEmpleadosPorHotelId(hotelId);
			if (empleados != null && !empleados.isEmpty()) {
				empleadoViews.mostrarListaEmpleados(hotel, empleados); // Pasar la lista de empleados con la información
																		// del hotel incluida
			} else {
				empleadoViews.mostrarMensaje("No se encontraron empleados para el hotel con ID: " + hotelId);
			}
		} catch (Exception e) {
			empleadoViews.mostrarMensaje("Error al listar los empleados: " + e.getMessage());
		}
	}

}
