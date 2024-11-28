package controllers;

import beans.HabitacionBean;
import beans.HotelBean;
import dataBase.ConexionDataBase;
import services.HotelService;

import javax.swing.JOptionPane;
import java.util.ArrayList;

public class HotelController {

    private HotelService hotelService;

    public HotelController(ConexionDataBase conexionDataBase) {
        this.hotelService = new HotelService(conexionDataBase);
    }

    public void agregarHotel() {
        String nombre = JOptionPane.showInputDialog("Ingresa el nombre del Hotel: ");
        String estrellasStr = JOptionPane.showInputDialog("Ingresa la cantidad de Estrellas: ");
        int estrellas = Integer.parseInt(estrellasStr);
        String direccion = JOptionPane.showInputDialog("Ingresa la dirección del Hotel: ");
        String telefono = JOptionPane.showInputDialog("Ingresa el teléfono del Hotel: ");
        
        hotelService.agregarHotel(nombre, estrellas, direccion, telefono);
        JOptionPane.showMessageDialog(null, "Hotel " + nombre + " con " + estrellas + " estrellas agregado exitosamente.");
    }

    public void listarHoteles() {
        ArrayList<HotelBean> hoteles = hotelService.listarHoteles();
        StringBuilder sb = new StringBuilder("Hoteles registrados:\n");
        for (HotelBean hotel : hoteles) {
            sb.append("Nombre: ").append(hotel.getNombre())
              .append(", Estrellas: ").append(hotel.getEstrellas())
              .append(", Dirección: ").append(hotel.getDireccion())
              .append(", Teléfono: ").append(hotel.getTelefono())
              .append("\n");
        }
        JOptionPane.showMessageDialog(null, sb.toString());
    }

    public void agregarHabitacion() {
        String nombreHotel = JOptionPane.showInputDialog("Ingresa el nombre del Hotel al que deseas agregar una habitación: ");
        String id = JOptionPane.showInputDialog("Ingresa el ID de la habitación: ");
        String tipo = JOptionPane.showInputDialog("Ingresa el tipo de habitación: ");
        
        HabitacionBean habitacion = new HabitacionBean(id, tipo);
        hotelService.agregarHabitacion(nombreHotel, habitacion);
        JOptionPane.showMessageDialog(null, "Habitación " + id + " agregada exitosamente al hotel " + nombreHotel + ".");
    }

    public void listarHabitacionesDeHotel() {
        String nombreHotel = JOptionPane.showInputDialog("Ingresa el nombre del Hotel para listar sus habitaciones: ");
        ArrayList<HabitacionBean> habitaciones = hotelService.listarHabitacionesDeHotel(nombreHotel);
        StringBuilder sb = new StringBuilder("Habitaciones del hotel " + nombreHotel + ":\n");
        for (HabitacionBean habitacion : habitaciones) {
            sb.append("ID: ").append(habitacion.getID()).append(", Tipo: ").append(habitacion.getTipo()).append("\n");
        }
        JOptionPane.showMessageDialog(null, sb.toString());
    }
}
