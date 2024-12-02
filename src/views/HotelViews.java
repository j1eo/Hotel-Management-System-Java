package views;

import controllers.HotelController;
import javax.swing.*;
import java.util.ArrayList;
import beans.HotelBean;
import beans.HabitacionBean;

public class HotelViews {

    private final HotelController hotelController;

    public HotelViews(HotelController hotelController) {
        this.hotelController = hotelController;
    }

    public void MenuPrincipalHotel(HotelController hotelController) {
        Object selectedOption;
        while (true) {
            try {
                String[] options = {"Listar Hoteles", "Agregar Hotel", "Listar Habitaciones de un Hotel", "Buscar Hotel por Nombre", "Eliminar Hotel", "Salir"};
                selectedOption = JOptionPane.showInputDialog(null, "Selecciona una opción", "Menú Principal de Hoteles",
                        JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

                if (selectedOption == null || selectedOption.toString().equals("Salir")) {
                    mostrarMensaje("Saliendo del sistema de hoteles...");
                    break;
                }

                hotelController.evalOption(selectedOption);
            } catch (Exception e) {
                mostrarMensaje("Ha ocurrido un error: " + e.getMessage());
            }
        }
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(null, mensaje);
    }

    public String solicitarNombreHotel() {
        return JOptionPane.showInputDialog("Ingresa el nombre del hotel:");
    }

    public int solicitarEstrellas() {
        return Integer.parseInt(JOptionPane.showInputDialog("Ingresa el número de estrellas:"));
    }

    public String solicitarDireccion() {
        return JOptionPane.showInputDialog("Ingresa la dirección del hotel:");
    }

    public String solicitarTelefono() {
        return JOptionPane.showInputDialog("Ingresa el teléfono del hotel:");
    }

    public void listarHoteles(ArrayList<HotelBean> hoteles) {
        StringBuilder mensaje = new StringBuilder("Hoteles:\n");
        for (HotelBean hotel : hoteles) {
            mensaje.append("ID: ").append(hotel.getHotelId()).append(", Nombre: ").append(hotel.getNombre())
                   .append(", Estrellas: ").append(hotel.getEstrellas()).append("\n");
        }
        mostrarMensaje(mensaje.toString());
    }

    public void listarHabitacionesDeHotel(String nombreHotel, ArrayList<HabitacionBean> habitaciones) {
        StringBuilder mensaje = new StringBuilder("Habitaciones del hotel ").append(nombreHotel).append(":\n");
        for (HabitacionBean habitacion : habitaciones) {
            mensaje.append("ID: ").append(habitacion.getID()).append(", Tipo: ").append(habitacion.getTipo()).append("\n");
        }
        mostrarMensaje(mensaje.toString());
    }

    public void mostrarHotel(HotelBean hotel) {
        mostrarMensaje("ID: " + hotel.getHotelId() + ", Nombre: " + hotel.getNombre() + ", Estrellas: " + hotel.getEstrellas() +
                       ", Dirección: " + hotel.getDireccion() + ", Teléfono: " + hotel.getTelefono());
    }
}
