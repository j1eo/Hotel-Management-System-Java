package views;

import beans.HabitacionBean;
import beans.HotelBean;

import javax.swing.*;
import java.util.ArrayList;

public class HotelViews {

    public String solicitarNombreHotel() {
        return JOptionPane.showInputDialog("Ingresa el nombre del Hotel: ");
    }

    public int solicitarEstrellas() {
        String estrellasStr = JOptionPane.showInputDialog("Ingresa la cantidad de Estrellas: ");
        return Integer.parseInt(estrellasStr);
    }

    public String solicitarDireccion() {
        return JOptionPane.showInputDialog("Ingresa la dirección del Hotel: ");
    }

    public String solicitarTelefono() {
        return JOptionPane.showInputDialog("Ingresa el teléfono del Hotel: ");
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(null, mensaje);
    }

    public void listarHoteles(ArrayList<HotelBean> hoteles) {
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

    public String solicitarNombreHotelParaHabitacion() {
        return JOptionPane.showInputDialog("Ingresa el nombre del Hotel al que deseas agregar una habitación: ");
    }

    public String solicitarIDHabitacion() {
        return JOptionPane.showInputDialog("Ingresa el ID de la habitación: ");
    }

    public String solicitarTipoHabitacion() {
        return JOptionPane.showInputDialog("Ingresa el tipo de habitación: ");
    }

    public void listarHabitacionesDeHotel(String nombreHotel, ArrayList<HabitacionBean> habitaciones) {
        StringBuilder sb = new StringBuilder("Habitaciones del hotel " + nombreHotel + ":\n");
        for (HabitacionBean habitacion : habitaciones) {
            sb.append("ID: ").append(habitacion.getID()).append(", Tipo: ").append(habitacion.getTipo()).append("\n");
        }
        JOptionPane.showMessageDialog(null, sb.toString());
    }

    public void mostrarHotel(HotelBean hotel) {
        StringBuilder sb = new StringBuilder();
        sb.append("Nombre: ").append(hotel.getNombre()).append("\n")
          .append("Estrellas: ").append(hotel.getEstrellas()).append("\n")
          .append("Dirección: ").append(hotel.getDireccion()).append("\n")
          .append("Teléfono: ").append(hotel.getTelefono()).append("\n");

        JOptionPane.showMessageDialog(null, sb.toString());
    }
}
