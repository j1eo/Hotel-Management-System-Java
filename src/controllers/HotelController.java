package controllers;

import beans.HabitacionBean;
import beans.HotelBean;
import services.HotelService;

import java.util.ArrayList;

public class HotelController {

    private HotelService hotelService;

    public HotelController() {
        this.hotelService = new HotelService();
    }

    public void agregarHotel(String nombre, int estrellas) {
        HotelBean hotel = new HotelBean(nombre, estrellas);
        hotelService.agregarHotel(hotel);
    }

    public ArrayList<HotelBean> listarHoteles() {
        return hotelService.listarHoteles();
    }

    public void agregarHabitacion(String nombreHotel, String ID, String tipo) {
        HabitacionBean habitacion = new HabitacionBean(ID, tipo);
        hotelService.agregarHabitacion(nombreHotel, habitacion);
    }

    public ArrayList<HabitacionBean> listarHabitacionesDeHotel(String nombreHotel) {
        return hotelService.listarHabitacionesDeHotel(nombreHotel);
    }

    public double calcularCostoPorDia(String nombreHotel, String habitacionID) {
        return hotelService.calcularCostoPorDia(nombreHotel, habitacionID);
    }

    public double calcularComision(String nombreHotel, String habitacionID) {
        return hotelService.calcularComision(nombreHotel, habitacionID);
    }

    public HabitacionBean buscarHabitacionPorID(String nombreHotel, String habitacionID) {
        return hotelService.buscarHabitacionPorID(nombreHotel, habitacionID);
    }
}
