package controllers;

import views.EmpleadoViews;
import views.HotelViews;

import javax.swing.*;

public class SystemController {

    private final HotelViews hotelViews;
    private final HotelController hotelController;
    private final EmpleadoViews empleadoViews;
    private final EmpleadoController empleadoController;

    public SystemController(HotelViews hotelViews, HotelController hotelController, EmpleadoViews empleadoViews, EmpleadoController empleadoController) {
        this.hotelViews = hotelViews;
        this.hotelController = hotelController;
        this.empleadoViews = empleadoViews;
        this.empleadoController = empleadoController;
    }

    public void evalOption(String selectedOption) {
        switch (selectedOption) {
            case "Información de Hoteles":
                hotelViews.MenuPrincipalHotel(hotelController);  // Asegúrate de que el método en HotelViews se llame menuPrincipalHotel
                break;
            case "Información de Empleados":
                empleadoViews.menuPrincipalEmpleado(empleadoController);
                break;
            case "Información de Habitaciones":
                JOptionPane.showMessageDialog(null, "Funcionalidad no implementada.");
                break;
            case "Información de Comisiones":
                JOptionPane.showMessageDialog(null, "Funcionalidad no implementada.");
                break;
            default:
                JOptionPane.showMessageDialog(null, "Opción inválida. Intenta de nuevo.");
                break;
        }
    }
}
