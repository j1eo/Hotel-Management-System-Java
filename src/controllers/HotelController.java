package controllers;

import beans.HabitacionBean;
import beans.HotelBean;
import services.HotelService;
import views.HotelViews;

import java.sql.SQLException;
import java.util.ArrayList;

public class HotelController {

    private HotelService hotelService;
    private HotelViews hotelViews;

    public HotelController(HotelService hotelService, HotelViews hotelViews) {
        this.hotelService = hotelService;
        this.hotelViews = hotelViews;
    }

    public void evalOption(Object selectedOption) {
        // Verificar si la opción seleccionada es nula (cancelación)
        if (selectedOption == null) {
            return;
        }

        String parsedSelectedOption = selectedOption.toString();

        switch (parsedSelectedOption) {
            case "Listar Hoteles":
                listarHoteles();
                break;
            case "Agregar Hotel":
                agregarHotel();
                break;
            case "Listar Habitaciones de un Hotel":
                listarHabitacionesDeHotel();
                break;
            case "Buscar Hotel por Nombre":
                buscarHotelPorNombre();
                break;
            case "Eliminar Hotel":
                eliminarHotel();
                break;
            case "Salir":
                hotelViews.mostrarMensaje("Saliendo...");
                break;
            default:
                hotelViews.mostrarMensaje("Opción inválida. Intenta de nuevo.");
                break;
        }
    }

    public void agregarHotel() {
        try {
            String nombre = hotelViews.solicitarNombreHotel();
            if (nombre == null) return;
            int estrellas = hotelViews.solicitarEstrellas();
            String direccion = hotelViews.solicitarDireccion();
            String telefono = hotelViews.solicitarTelefono();

            hotelService.agregarHotel(nombre, estrellas, direccion, telefono);
            hotelViews.mostrarMensaje("Hotel " + nombre + " con " + estrellas + " estrellas agregado exitosamente.");
        } catch (SQLException e) {
            hotelViews.mostrarMensaje("Error al agregar el hotel: " + e.getMessage());
        }
    }

    public void listarHoteles() {
        try {
            ArrayList<HotelBean> hoteles = hotelService.listarHoteles();
            hotelViews.listarHoteles(hoteles);
        } catch (SQLException e) {
            hotelViews.mostrarMensaje("Error al listar los hoteles: " + e.getMessage());
        }
    }

    public void agregarHabitacion() {
        try {
            String nombreHotel = hotelViews.solicitarNombreHotelParaHabitacion();
            if (nombreHotel == null) return;
            String id = hotelViews.solicitarIDHabitacion();
            String tipo = hotelViews.solicitarTipoHabitacion();

            HabitacionBean habitacion = new HabitacionBean(id, tipo);
            hotelService.agregarHabitacion(nombreHotel, habitacion);
            hotelViews.mostrarMensaje("Habitación " + id + " agregada exitosamente al hotel " + nombreHotel + ".");
        } catch (SQLException e) {
            hotelViews.mostrarMensaje("Error al agregar la habitación: " + e.getMessage());
        }
    }

    public void listarHabitacionesDeHotel() {
        try {
            String nombreHotel = hotelViews.solicitarNombreHotel();
            if (nombreHotel == null) return;
            ArrayList<HabitacionBean> habitaciones = hotelService.listarHabitacionesDeHotel(nombreHotel);
            hotelViews.listarHabitacionesDeHotel(nombreHotel, habitaciones);
        } catch (SQLException e) {
            hotelViews.mostrarMensaje("Error al listar las habitaciones: " + e.getMessage());
        }
    }

    public void eliminarHotel() {
        try {
            String nombreHotel = hotelViews.solicitarNombreHotel();
            if (nombreHotel == null) return;
            hotelService.eliminarHotel(nombreHotel);
            hotelViews.mostrarMensaje("Hotel " + nombreHotel + " eliminado exitosamente.");
        } catch (SQLException e) {
            hotelViews.mostrarMensaje("Error al eliminar el hotel: " + e.getMessage());
        }
    }

    public void buscarHotelPorNombre() {
        try {
            String nombreHotel = hotelViews.solicitarNombreHotel();
            if (nombreHotel == null) return;
            HotelBean hotel = hotelService.buscarHotelPorNombre(nombreHotel);
            hotelViews.mostrarHotel(hotel);
        } catch (SQLException e) {
            hotelViews.mostrarMensaje("Error al buscar el hotel: " + e.getMessage());
        }
    }
}
