package controllers;

import java.sql.Date;
import java.util.List;

import beans.HabitacionBean;
import beans.HotelBean;
import beans.ReservacionBean;
import beans.VendedorBean;
import services.HotelService;
import services.ReservacionService;
import services.VendedorService;
import views.ReservacionViews;

public class ReservacionController {
    private final ReservacionService reservacionService;
    private final VendedorService vendedorService;
    private final HotelService hotelService;
    private final ReservacionViews reservacionViews;

    public ReservacionController(ReservacionService reservacionService, VendedorService vendedorService, HotelService hotelService, ReservacionViews reservacionViews) {
        this.reservacionService = reservacionService;
        this.vendedorService = vendedorService;
        this.hotelService = hotelService;
        this.reservacionViews = reservacionViews;
    }
    public void realizarReservacion() {
        try {
            // Pedir el ID del vendedor
            int vendedorId = reservacionViews.pedirVendedorId();
            VendedorBean vendedor = vendedorService.obtenerVendedorPorId(vendedorId);
            if (vendedor == null) {
                reservacionViews.mostrarMensaje("Vendedor no encontrado.");
                return;
            }

            // Seleccionar un hotel
            List<HotelBean> hoteles = hotelService.listarHoteles();
            HotelBean hotel = reservacionViews.seleccionarHotel(hoteles);
            if (hotel == null) {
                reservacionViews.mostrarMensaje("Hotel no seleccionado.");
                return;
            }

            // Seleccionar una habitación
            List<HabitacionBean> habitaciones = hotelService.listarHabitacionesDeHotel(hotel.getNombre());
            HabitacionBean habitacion = reservacionViews.seleccionarHabitacion(habitaciones);
            if (habitacion == null) {
                reservacionViews.mostrarMensaje("Habitación no seleccionada.");
                return;
            }

            // Verificar si la habitación está disponible
            if (!habitacion.isDisponible()) {
                reservacionViews.mostrarMensaje("La habitación seleccionada no está disponible.");
                return;
            }

            // Pedir el número de personas
            int numeroPersonas = reservacionViews.pedirNumeroPersonas();

            // Pedir la fecha de registro
            Date fechaRegistro = reservacionViews.pedirFechaRegistro();
            if (fechaRegistro == null) {
                reservacionViews.mostrarMensaje("Fecha de registro no seleccionada.");
                return;
            }

            // Pedir la duración de la estadía
            int duracionEstadia = reservacionViews.pedirDuracionEstadia();

            // Calcular el costo utilizando el servicio
            String tipoHabitacion = habitacion.getTipo();
            int nivelEstrellas = hotel.getEstrellas();
            System.out.println("Tipo de habitación: " + tipoHabitacion); // Depuración
            System.out.println("Nivel de estrellas: " + nivelEstrellas); // Depuración
            double costo = reservacionService.calcularCosto(tipoHabitacion, nivelEstrellas, duracionEstadia);
            System.out.println("Costo calculado: " + costo); // Depuración

            // Crear la reservación
            ReservacionBean reservacion = new ReservacionBean();
            reservacion.setVendedorId(vendedorId);
            reservacion.setHotelId(hotel.getHotelId());
            reservacion.setHabitacionId(habitacion.getHabitacionId());
            reservacion.setNumeroPersonas(numeroPersonas);
            reservacion.setFechaRegistro(fechaRegistro);
            reservacion.setDuracionEstadia(duracionEstadia);
            reservacion.setCosto(costo);

            // Guardar la reservación y actualizar el estado de la habitación
            reservacionService.agregarReservacion(reservacion);
            habitacion.setDisponible(false); // Actualizar disponibilidad localmente
            habitacion.setNumeroPersonas(numeroPersonas); // Actualizar número de personas localmente

            reservacionViews.mostrarMensaje("Reservación realizada exitosamente.");
        } catch (Exception e) {
            reservacionViews.mostrarMensaje("Error al realizar la reservación: " + e.getMessage());
        }
    }





}
