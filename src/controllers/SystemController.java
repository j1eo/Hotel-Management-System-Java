package controllers;

import views.ComisionViews;
import views.EmpleadoViews;
import views.HotelViews;
import views.ReservacionViews;
import views.SystemViews;
import services.ReservacionService;

import javax.swing.JOptionPane;

import services.HotelService;

public class SystemController {

    private final HotelViews hotelViews;
    private final HotelController hotelController;
    private final EmpleadoViews empleadoViews;
    private final EmpleadoController empleadoController;
    private final SystemViews systemViews;
    private final ReservacionController reservacionController;
    private final ReservacionViews reservacionViews;
    private final ReservacionService reservacionService;
    private final HotelService hotelService;
    private final ComisionController comisionController;
    private final ComisionViews comisionViews;
    

    public SystemController(HotelViews hotelViews, HotelController hotelController, EmpleadoViews empleadoViews,
                            EmpleadoController empleadoController, SystemViews systemViews,
                            ReservacionController reservacionController, ReservacionViews reservacionViews,
                            ReservacionService reservacionService, HotelService hotelService, ComisionController comisionController, ComisionViews comisionViews) {
        this.hotelViews = hotelViews;
        this.hotelController = hotelController;
        this.empleadoViews = empleadoViews;
        this.empleadoController = empleadoController;
        this.systemViews = systemViews;
        this.reservacionController = reservacionController;
        this.reservacionViews = reservacionViews;
        this.reservacionService = reservacionService;
        this.hotelService = hotelService;
        this.comisionViews = comisionViews;
        this.comisionController = comisionController;
    }

    public void evalOption(String selectedOption) {
        switch (selectedOption) {
            case "Entrar Como Administrador":
                systemViews.mostrarMenuAdmin(this);
                break;
            case "Entrar Como Vendedor":
                systemViews.mostrarMenuVendedor(this);
                break;
            case "Información de Hoteles":
                hotelViews.MenuPrincipalHotel(hotelController);
                break;
            case "Información de Empleados":
                empleadoViews.menuPrincipalEmpleado(empleadoController);
                break;
            case "Información de Habitaciones":
                hotelController.listarHabitacionesDeHotel();
                break;
            case "Información de Ventas":
                reservacionController.obtenerVentasPorMes();
                break;
            case "Realizar Reservacion":
                reservacionViews.mostrarMenuReservacion(reservacionController);
                break;
            case "Información de Comisiones":
            	comisionViews.menuPrincipalComision(comisionController);
                break;
            default:
                JOptionPane.showMessageDialog(null, "Opción inválida. Intenta de nuevo.");
                break;
        }
    }
}
