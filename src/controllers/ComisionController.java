package controllers;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import beans.BonoGerenteBean;
import beans.ComisionRecamareraBean;
import beans.ComisionVendedorBean;
import beans.HotelBean;
import services.BonoService;
import services.ComisionService;
import services.HotelService;
import views.ComisionViews;

public class ComisionController {

	private final ComisionService comisionService;
	private final ComisionViews comisionViews;
	private final BonoService bonoService;
	private final HotelService hotelService;

	public ComisionController(ComisionService comisionService, ComisionViews comisionViews, BonoService bonoService,
			HotelService hotelService) {
		this.comisionService = comisionService;
		this.comisionViews = comisionViews;
		this.bonoService = bonoService;
		this.hotelService = hotelService;
	}

	public void evalOption(String selectedOption) {
		switch (selectedOption) {
		case "Comisiones por Dia":
			sumarComisionesPorDia();
			break;
		case "Comisiones por Mes":
			sumarComisionesPorMes();
			break;
		case "Comisiones de Vendedores":
			listarComisionesVendedores();
			break;
		case "Comisiones de Recamareras":
			listarComisionesRecamareras();
			break;
		case "Bonos de Gerentes":
			listarBonosGerentes();
			break;
		default:
			JOptionPane.showMessageDialog(null, "Opción inválida. Intenta de nuevo.");
			break;
		}
	}

	public void listarBonosGerentes() {
		try {
			List<HotelBean> hoteles = hotelService.listarHoteles();
			HotelBean hotel = comisionViews.seleccionarHotel(hoteles);
			if (hotel == null) {
				comisionViews.mostrarMensaje("Hotel no seleccionado.");
				return;
			}

			Date fechaMes = comisionViews.pedirFechaMes();
			if (fechaMes == null) {
				comisionViews.mostrarMensaje("Fecha no seleccionada.");
				return;
			}

			String mes = fechaMes.toString().substring(0, 7); // Obtener el año y mes en formato YYYY-MM
			List<String> nombresGerentes = bonoService.obtenerNombresGerentesPorHotel(hotel.getHotelId());
			List<BonoGerenteBean> bonos = bonoService.obtenerBonosGerentesPorMes(hotel.getHotelId(), mes);

			// Crear una lista de bonos asegurando que todos los gerentes tengan un bono
			List<BonoGerenteBean> bonosCompletos = new ArrayList<>();
			for (String nombreGerente : nombresGerentes) {
				int empleadoId = bonoService.obtenerIdEmpleadoPorNombre(nombreGerente);
				boolean bonoEncontrado = false;
				for (BonoGerenteBean bono : bonos) {
					if (bono.getEmpleadoId() == empleadoId) {
						bonosCompletos.add(bono);
						bonoEncontrado = true;
						break;
					}
				}
				if (!bonoEncontrado) {
					// Si no se encontró bono para el gerente, agregar un bono de 0
					bonosCompletos.add(new BonoGerenteBean(0, empleadoId, hotel.getHotelId(), 0.0, null, mes));
				}
			}

			comisionViews.mostrarBonosGerentes(bonosCompletos, nombresGerentes);

		} catch (Exception e) {
			e.printStackTrace();
			comisionViews.mostrarMensaje("Error al listar los bonos de los gerentes: " + e.getMessage());
		}
	}

	public void listarComisionesRecamareras() {
		try {
			List<HotelBean> hoteles = hotelService.listarHoteles();
			HotelBean hotel = comisionViews.seleccionarHotel(hoteles);
			if (hotel == null) {
				comisionViews.mostrarMensaje("Hotel no seleccionado.");
				return;
			}

			Date fechaMes = comisionViews.pedirFechaMes();
			if (fechaMes == null) {
				comisionViews.mostrarMensaje("Fecha no seleccionada.");
				return;
			}

			String mes = fechaMes.toString().substring(0, 7); // Obtener el año y mes en formato YYYY-MM
			List<String> nombresRecamareras = comisionService.obtenerNombresRecamarerasPorHotel(hotel.getHotelId());
			List<String> nivelesExperiencia = comisionService
					.obtenerNivelesExperienciaRecamarerasPorHotel(hotel.getHotelId());
			List<ComisionRecamareraBean> comisiones = comisionService
					.obtenerComisionesRecamarerasPorMes(hotel.getHotelId(), mes);

			// Crear una lista de comisiones asegurando que todas las recamareras tengan una
			// comisión
			List<ComisionRecamareraBean> comisionesCompletas = new ArrayList<>();
			for (String nombreRecamarera : nombresRecamareras) {
				int empleadoId = comisionService.obtenerIdEmpleadoPorNombre(nombreRecamarera);
				boolean comisionEncontrada = false;
				for (ComisionRecamareraBean comision : comisiones) {
					if (comision.getEmpleadoId() == empleadoId) {
						comisionesCompletas.add(comision);
						comisionEncontrada = true;
						break;
					}
				}
				if (!comisionEncontrada) {
					// Si no se encontró comisión para la recamarera, agregar una comisión de 0
					comisionesCompletas.add(
							new ComisionRecamareraBean(0, empleadoId, hotel.getHotelId(), 0.0, fechaMes, "", 0, 0));
				}
			}

			comisionViews.mostrarComisionesRecamareras(comisionesCompletas, nombresRecamareras, nivelesExperiencia);

		} catch (Exception e) {
			e.printStackTrace();
			comisionViews.mostrarMensaje("Error al listar las comisiones de las recamareras: " + e.getMessage());
		}
	}

	// Nuevo método para listar las comisiones de los vendedores
	public void listarComisionesVendedores() {
		try {
			List<HotelBean> hoteles = hotelService.listarHoteles();
			HotelBean hotel = comisionViews.seleccionarHotel(hoteles);
			if (hotel == null) {
				comisionViews.mostrarMensaje("Hotel no seleccionado.");
				return;
			}

			Date fechaMes = comisionViews.pedirFechaMes();
			if (fechaMes == null) {
				comisionViews.mostrarMensaje("Fecha no seleccionada.");
				return;
			}

			String mes = fechaMes.toString().substring(0, 7); // Obtener el año y mes en formato YYYY-MM
			List<String> nombresVendedores = comisionService.obtenerNombresVendedoresPorHotel(hotel.getHotelId());
			List<ComisionVendedorBean> comisiones = comisionService
					.obtenerComisionesVendedoresPorMes(hotel.getHotelId(), mes);

			// Crear una lista de comisiones asegurando que todos los vendedores tengan una
			// comisión
			List<ComisionVendedorBean> comisionesCompletas = new ArrayList<>();
			for (String nombreVendedor : nombresVendedores) {
				int empleadoId = comisionService.obtenerIdEmpleadoPorNombre(nombreVendedor);
				boolean comisionEncontrada = false;
				for (ComisionVendedorBean comision : comisiones) {
					if (comision.getEmpleadoId() == empleadoId) {
						comisionesCompletas.add(comision);
						comisionEncontrada = true;
						break;
					}
				}
				if (!comisionEncontrada) {
					// Si no se encontró comisión para el vendedor, agregar una comisión de 0
					comisionesCompletas.add(
							new ComisionVendedorBean(0, empleadoId, hotel.getHotelId(), 0.0, fechaMes, "Vendedor", 0));
				}
			}

			comisionViews.mostrarComisionesVendedores(comisionesCompletas, nombresVendedores);

		} catch (Exception e) {
			e.printStackTrace();
			comisionViews.mostrarMensaje("Error al listar las comisiones de los vendedores: " + e.getMessage());
		}
	}

	public void sumarComisionesPorDia() {
		try {
			List<HotelBean> hoteles = hotelService.listarHoteles(); // Obtener la lista de hoteles
			HotelBean hotel = comisionViews.seleccionarHotel(hoteles); // Seleccionar hotel
			if (hotel == null) {
				comisionViews.mostrarMensaje("Hotel no seleccionado.");
				return;
			}
			Date dia = comisionViews.pedirFechaDia(); // Método para solicitar la fecha
			double totalComision = comisionService.sumarComisionesPorDia(hotel.getHotelId(), dia);
			comisionViews.mostrarTotalComisionDia(hotel.getHotelId(), dia, totalComision); // Mostrar el resultado en
																							// una tabla
		} catch (SQLException e) {
			comisionViews.mostrarMensaje("Error al sumar las comisiones: " + e.getMessage());
		}
	}
	
	  public void sumarComisionesPorMes() {
	        try {
	            List<HotelBean> hoteles = hotelService.listarHoteles(); // Obtener la lista de hoteles
	            HotelBean hotel = comisionViews.seleccionarHotel(hoteles); // Seleccionar hotel
	            if (hotel == null) {
	                comisionViews.mostrarMensaje("Hotel no seleccionado.");
	                return;
	            }
	            String mes = comisionViews.pedirFechaMes().toString().substring(0, 7); // Obtener el año y mes en formato YYYY-MM
	            double totalComision = comisionService.sumarComisionesPorMes(hotel.getHotelId(), mes);
	            comisionViews.mostrarTotalComisionMes(hotel.getHotelId(), mes, totalComision); // Mostrar el resultado en una tabla
	        } catch (SQLException e) {
	            comisionViews.mostrarMensaje("Error al sumar las comisiones: " + e.getMessage());
	        }
	    }

}
