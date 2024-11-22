package services;

import beans.HabitacionBean;
import beans.HotelBean;

import java.util.ArrayList;

public class HotelService {

    private ArrayList<HotelBean> hoteles;

    public HotelService() {
        this.hoteles = new ArrayList<>();
    }

    public void agregarHotel(HotelBean hotel) {
        hoteles.add(hotel);
    }

    public ArrayList<HotelBean> listarHoteles() {
        return new ArrayList<>(hoteles);
    }

    public void agregarHabitacion(String nombreHotel, HabitacionBean habitacion) {
        for (HotelBean hotel : hoteles) {
            if (hotel.getNombre().equalsIgnoreCase(nombreHotel)) {
                hotel.agregarHabitacion(habitacion);
                return;
            }
        }
        throw new IllegalArgumentException("Hotel no encontrado: " + nombreHotel);
    }

    public ArrayList<HabitacionBean> listarHabitacionesDeHotel(String nombreHotel) {
        for (HotelBean hotel : hoteles) {
            if (hotel.getNombre().equalsIgnoreCase(nombreHotel)) {
                return hotel.getHabitaciones();
            }
        }
        throw new IllegalArgumentException("Hotel no encontrado: " + nombreHotel);
    }

    public double calcularCostoPorDia(String nombreHotel, String habitacionID) {
        HotelBean hotel = buscarHotelPorNombre(nombreHotel);
        HabitacionBean habitacion = buscarHabitacionEnHotel(hotel, habitacionID);

        return calcularCostoPorDiaHabitacion(habitacion, hotel.getEstrellas());
    }

    public double calcularComision(String nombreHotel, String habitacionID) {
        HotelBean hotel = buscarHotelPorNombre(nombreHotel);
        HabitacionBean habitacion = buscarHabitacionEnHotel(hotel, habitacionID);

        return calcularComisionHabitacion(habitacion, hotel.getEstrellas());
    }

    public HabitacionBean buscarHabitacionPorID(String nombreHotel, String habitacionID) {
        HotelBean hotel = buscarHotelPorNombre(nombreHotel);
        return buscarHabitacionEnHotel(hotel, habitacionID);
    }

    private HotelBean buscarHotelPorNombre(String nombreHotel) {
        for (HotelBean hotel : hoteles) {
            if (hotel.getNombre().equalsIgnoreCase(nombreHotel)) {
                return hotel;
            }
        }
        throw new IllegalArgumentException("Hotel no encontrado: " + nombreHotel);
    }

    private HabitacionBean buscarHabitacionEnHotel(HotelBean hotel, String habitacionID) {
        for (HabitacionBean habitacion : hotel.getHabitaciones()) {
            if (habitacion.getID().equalsIgnoreCase(habitacionID)) {
                return habitacion;
            }
        }
        throw new IllegalArgumentException("Habitación no encontrada en el hotel: " + habitacionID);
    }

    private double calcularCostoPorDiaHabitacion(HabitacionBean habitacion, int estrellasHotel) {
        double baseCosto;
        switch (habitacion.getTipo().toLowerCase()) {
            case "sencilla":
                baseCosto = 300;
                break;
            case "doble":
                baseCosto = 700;
                break;
            case "penhouse":
                baseCosto = 1200;
                break;
            default:
                throw new IllegalArgumentException("Tipo de habitación inválido.");
        }
        switch (estrellasHotel) {
            case 3:
                return baseCosto;
            case 4:
                return baseCosto * 1.2;
            case 5:
                return baseCosto * 1.6;
            default:
                throw new IllegalArgumentException("Número de estrellas inválido.");
        }
    }

    private double calcularComisionHabitacion(HabitacionBean habitacion, int estrellasHotel) {
        switch (habitacion.getTipo().toLowerCase()) {
            case "sencilla":
                return estrellasHotel == 3 ? 30 : estrellasHotel == 4 ? 50 : 70;
            case "doble":
                return estrellasHotel == 3 ? 40 : estrellasHotel == 4 ? 60 : 80;
            case "penhouse":
                return estrellasHotel == 3 ? 60 : estrellasHotel == 4 ? 80 : 100;
            default:
                throw new IllegalArgumentException("Tipo de habitación inválido.");
        }
    }
}
