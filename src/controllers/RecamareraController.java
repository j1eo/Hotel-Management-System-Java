package controllers;

import beans.HabitacionBean;
import beans.HotelBean;
import beans.RecamareraBean;
import services.HotelService;
import services.RecamareraService;
import views.RecamareraViews;

import java.util.List;

public class RecamareraController {

    private final RecamareraService recamareraService;
    private final HotelService hotelService;
    private final RecamareraViews recamareraViews;

    public RecamareraController(RecamareraService recamareraService, HotelService hotelService, RecamareraViews recamareraViews) {
        this.recamareraService = recamareraService;
        this.hotelService = hotelService;
        this.recamareraViews = recamareraViews;
    }

    public void evalOption(Object selectedOption) {
        if (selectedOption == null) {
            return;
        }

        String parsedSelectedOption = selectedOption.toString();

        switch (parsedSelectedOption) {
            case "Listar Info. Recamareras":
                listarRecamarerasPorHotel();
                break;
            case "Asignar Habitacion a Recamarera":
            	asignarHabitacion();
                break;
            case "Listar Habitaciones Sin Recamarera":
                listarHabitacionesSinRecamareraAsignada();
                break;
            case "Salir":
                recamareraViews.mostrarMensaje("Saliendo del sistema de empleados...");
                break;
            default:
                recamareraViews.mostrarMensaje("Opción inválida. Intenta de nuevo.");
                break;
        }
    }
    
    public void asignarHabitacion() {
        try {
            // Verificar y actualizar asignaciones expiradas
            recamareraService.verificarYActualizarAsignacionesExpiradas();

            // Paso 1: Seleccionar el hotel
            String nombreHotel = recamareraViews.pedirNombreHotel();
            HotelBean hotel = hotelService.buscarHotelPorNombre(nombreHotel);
            if (hotel == null) {
                recamareraViews.mostrarMensaje("Hotel no encontrado con nombre: " + nombreHotel);
                return;
            }

            // Paso 2: Listar habitaciones sin recamarera asignada
            List<HabitacionBean> habitacionesSinRecamarera = recamareraService.listarHabitacionesSinRecamareraAsignada(hotel.getHotelId());
            if (habitacionesSinRecamarera.isEmpty()) {
                recamareraViews.mostrarMensaje("No se encontraron habitaciones sin recamarera asignada para el hotel: " + nombreHotel);
                return;
            }
            HabitacionBean habitacionSeleccionada = recamareraViews.seleccionarHabitacion(habitacionesSinRecamarera);
            if (habitacionSeleccionada == null) {
                recamareraViews.mostrarMensaje("No se seleccionó ninguna habitación.");
                return;
            }

            // Paso 3: Listar recamareras que pueden atender el tipo de habitación y que trabajan en ese hotel
            List<RecamareraBean> recamarerasDisponibles = recamareraService.listarRecamarerasPorTipoHabitacion(hotel.getHotelId(), habitacionSeleccionada.getTipo());
            if (recamarerasDisponibles.isEmpty()) {
                recamareraViews.mostrarMensaje("No se encontraron recamareras disponibles para el tipo de habitación: " + habitacionSeleccionada.getTipo());
                return;
            }
            RecamareraBean recamareraSeleccionada = recamareraViews.seleccionarRecamarera(recamarerasDisponibles);
            if (recamareraSeleccionada == null) {
                recamareraViews.mostrarMensaje("No se seleccionó ninguna recamarera.");
                return;
            }

            // Paso 4: Asignar la habitación a la recamarera
            recamareraService.asignarHabitacion(recamareraSeleccionada, habitacionSeleccionada);
            recamareraViews.mostrarMensaje("Habitación asignada exitosamente.");

        } catch (Exception e) {
            recamareraViews.mostrarMensaje("Error al asignar la habitación: " + e.getMessage());
        }
    }



    

    private void listarRecamarerasPorHotel() {
        try {
            int hotelId = recamareraViews.pedirHotelId();

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

            List<String> recamareras = recamareraService.listarRecamarerasPorHotelId(hotelId);
            if (recamareras != null && !recamareras.isEmpty()) {
                recamareraViews.mostrarListaRecamareras(hotel, recamareras); // Pasar la lista de recamareras con la información del hotel incluida
            } else {
                recamareraViews.mostrarMensaje("No se encontraron recamareras para el hotel con ID: " + hotelId);
            }
        } catch (Exception e) {
            recamareraViews.mostrarMensaje("Error al listar las recamareras: " + e.getMessage());
        }
    }

 

    public void listarHabitacionesSinRecamareraAsignada() {
        try {
            String nombreHotel = recamareraViews.pedirNombreHotel();
            HotelBean hotel = hotelService.buscarHotelPorNombre(nombreHotel);
            if (hotel == null) {
                recamareraViews.mostrarMensaje("Hotel no encontrado con nombre: " + nombreHotel);
                return;
            }

            List<HabitacionBean> habitaciones = recamareraService.listarHabitacionesSinRecamareraAsignada(hotel.getHotelId());

            if (habitaciones != null && !habitaciones.isEmpty()) {
                recamareraViews.mostrarListaHabitaciones(habitaciones);
            } else {
                recamareraViews.mostrarMensaje("No se encontraron habitaciones sin recamarera asignada para el hotel: " + nombreHotel);
            }
        } catch (Exception e) {
            recamareraViews.mostrarMensaje("Error al listar las habitaciones: " + e.getMessage());
        }
    }

}
