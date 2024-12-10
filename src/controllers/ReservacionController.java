package controllers;

import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

import beans.HabitacionBean;
import beans.HotelBean;
import beans.ReservacionBean;
import beans.VendedorBean;
import beans.ComisionVendedorBean;
import services.ComisionService;
import services.HotelService;
import services.ReservacionService;
import services.VendedorService;
import views.ReservacionViews;

public class ReservacionController {
	private final ReservacionService reservacionService;
	private final VendedorService vendedorService;
	private final HotelService hotelService;
	private final ComisionService comisionService;
	private final ReservacionViews reservacionViews;

	public ReservacionController(ReservacionService reservacionService, VendedorService vendedorService,
			HotelService hotelService, ComisionService comisionService, ReservacionViews reservacionViews) {
		this.reservacionService = reservacionService;
		this.vendedorService = vendedorService;
		this.hotelService = hotelService;
		this.comisionService = comisionService;
		this.reservacionViews = reservacionViews;
	}

	public void realizarReservacion() {
	    try {
	        System.out.println("Iniciando el proceso de reservación...");

	        int empleadoId = reservacionViews.pedirVendedorId();
	        VendedorBean vendedor = vendedorService.obtenerVendedorPorEmpleadoId(empleadoId);
	        if (vendedor == null) {
	            reservacionViews.mostrarMensaje("Vendedor no encontrado.");
	            System.out.println("Vendedor no encontrado con empleado ID: " + empleadoId);
	            return;
	        }
	        System.out.println("Empleado ID encontrado: " + empleadoId);

	        List<HotelBean> hoteles = hotelService.listarHoteles();
	        HotelBean hotel = reservacionViews.seleccionarHotel(hoteles);
	        if (hotel == null) {
	            reservacionViews.mostrarMensaje("Hotel no seleccionado.");
	            return;
	        }

	        // Filtrar habitaciones disponibles
	        List<HabitacionBean> habitaciones = hotelService.listarHabitacionesDeHotel(hotel.getNombre())
	                                                       .stream()
	                                                       .filter(HabitacionBean::isDisponible)
	                                                       .collect(Collectors.toList());

	        if (habitaciones.isEmpty()) {
	            reservacionViews.mostrarMensaje("No hay habitaciones disponibles en este hotel.");
	            return;
	        }

	        HabitacionBean habitacion = reservacionViews.seleccionarHabitacion(habitaciones);
	        if (habitacion == null) {
	            reservacionViews.mostrarMensaje("Habitación no seleccionada.");
	            return;
	        }

	        int numeroPersonas = reservacionViews.pedirNumeroPersonas();

	        Date fechaRegistro = reservacionViews.pedirFechaRegistro();
	        if (fechaRegistro == null) {
	            reservacionViews.mostrarMensaje("Fecha de registro no seleccionada.");
	            return;
	        }

	        int duracionEstadia = reservacionViews.pedirDuracionEstadia();

	        // Utilizar el método calcularCosto del servicio
	        String tipoHabitacion = habitacion.getTipo();
	        int nivelEstrellas = hotel.getEstrellas();
	        System.out.println("Tipo de habitación: " + tipoHabitacion); // Depuración
	        System.out.println("Nivel de estrellas: " + nivelEstrellas); // Depuración
	        double costo = reservacionService.calcularCosto(tipoHabitacion, nivelEstrellas, duracionEstadia);
	        System.out.println("Costo calculado: " + costo); // Depuración

	        ReservacionBean reservacion = new ReservacionBean();
	        reservacion.setVendedorId(empleadoId); // Usar el empleado_id para la reservación
	        reservacion.setHotelId(hotel.getHotelId());
	        reservacion.setHabitacionId(habitacion.getHabitacionId());
	        reservacion.setNumeroPersonas(numeroPersonas);
	        reservacion.setFechaRegistro(fechaRegistro);
	        reservacion.setDuracionEstadia(duracionEstadia);
	        reservacion.setCosto(costo);

	        reservacionService.agregarReservacion(reservacion);
	        habitacion.setDisponible(false);
	        habitacion.setNumeroPersonas(numeroPersonas);
	        reservacionViews.mostrarMensaje("Reservación realizada exitosamente.");

	        // Mensajes de depuración antes de calcular la comisión
	        System.out.println("Calculando la comisión del vendedor...");
	        System.out.println("Tipo de habitación: " + tipoHabitacion);
	        System.out.println("Nivel de estrellas: " + nivelEstrellas);
	        System.out.println("Número de personas: " + numeroPersonas);

	        // Calcular y registrar la comisión para el vendedor
	        double comisionVendedor = comisionService.calcularComisionVendedor(tipoHabitacion, nivelEstrellas, numeroPersonas);
	        System.out.println("Comisión calculada: " + comisionVendedor);
	        ComisionVendedorBean comisionVendedorBean = new ComisionVendedorBean(0, empleadoId, hotel.getHotelId(), comisionVendedor, fechaRegistro, tipoHabitacion, numeroPersonas);
	        comisionService.registrarComisionVendedor(comisionVendedorBean);

	        reservacionViews.mostrarMensaje("Reservación realizada exitosamente.");
	    } catch (Exception e) {
	        e.printStackTrace(); // Añadir traza de la excepción para depuración
	        reservacionViews.mostrarMensaje("Error al realizar la reservación: " + e.getMessage());
	    }
	}




}
