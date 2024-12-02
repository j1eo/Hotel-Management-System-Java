package controllers;

import services.EmpleadoService;
import views.EmpleadoViews;
import beans.EmpleadoBean;
import beans.RecamareraAuxiliar;
import beans.RecamareraExperimentadaBean;
import beans.RecamareraPrincipianteBean;
import beans.AmaDeLlavesBean;
import beans.GerenteBean;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import java.util.List;

public class EmpleadoController {

    private final EmpleadoService empleadoService;
    private final EmpleadoViews empleadoViews;

    public EmpleadoController(EmpleadoService empleadoService, EmpleadoViews empleadoViews) {
        this.empleadoService = empleadoService;
        this.empleadoViews = empleadoViews;
    }

    public void evalOption(Object selectedOption) {
        if (selectedOption == null) {
            return;
        }

        String parsedSelectedOption = selectedOption.toString();

        switch (parsedSelectedOption) {
            case "Listar Empleados":
                listarEmpleadosPorHotel();
                break;
            case "Agregar Empleado":
                empleadoViews.mostrarAgregarEmpleadoMenu(this);
                break;
            case "Modificar Empleado":
                empleadoViews.mostrarModificarEmpleadoMenu(this);
                break;
            case "Eliminar Empleado":
                eliminarEmpleado();
                break;
            case "Salir":
                empleadoViews.mostrarMensaje("Saliendo del sistema de empleados...");
                break;
            default:
                empleadoViews.mostrarMensaje("Opción inválida. Intenta de nuevo.");
                break;
        }
    }

    public void agregarEmpleado(String tipoEmpleado, String nombre, int hotelId) {
        try {
            empleadoService.agregarEmpleado(nombre, tipoEmpleado, hotelId);
            empleadoViews.mostrarMensaje("Empleado agregado exitosamente.");
        } catch (Exception e) {
            empleadoViews.mostrarMensaje("Error al agregar el empleado: " + e.getMessage());
        }
    }


    public void agregarGerente(String nombre, int hotelId) {
        try {
            double bono = 2000.0;  // Bono fijo
            empleadoService.agregarGerente(nombre, bono, hotelId);
            empleadoViews.mostrarMensaje("Gerente agregado exitosamente.");
        } catch (Exception e) {
            empleadoViews.mostrarMensaje("Error al agregar el gerente: " + e.getMessage());
        }
    }

    private EmpleadoBean crearEmpleado(String tipo, String nombre, double salario) {
        switch (tipo) {
            case "RecamareraAuxiliar":
                return new RecamareraAuxiliar(nombre, salario);
            case "RecamareraPrincipiante":
                return new RecamareraPrincipianteBean(nombre, salario);
            case "RecamareraExperimentada":
                return new RecamareraExperimentadaBean(nombre, salario);
            case "Ama de Llaves":
                return new AmaDeLlavesBean(nombre, salario);
            case "Gerente":
                return new GerenteBean(nombre, 0);  // Bono fijo
            default:
                throw new IllegalArgumentException("Tipo de empleado desconocido: " + tipo);
        }
    }

    public void modificarEmpleado(int HotelId, int empleadoId, String nombre, String nuevoTipoEmpleado) {
        try {
         
            boolean modificado = empleadoService.modificarEmpleado(HotelId, empleadoId, nombre, nuevoTipoEmpleado);
            if (modificado) {
                empleadoViews.mostrarMensaje("Empleado modificado exitosamente.");
            } else {
                empleadoViews.mostrarMensaje("Error al modificar el empleado.");
            }
        } catch (Exception e) {
            empleadoViews.mostrarMensaje("Error al modificar el empleado: " + e.getMessage());
        }
    }

    private void eliminarEmpleado() {
        try {
            int empleadoId = Integer.parseInt(JOptionPane.showInputDialog("Ingresa el ID del empleado a eliminar:"));
            boolean eliminado = empleadoService.eliminarEmpleado(empleadoId);
            if (eliminado) {
                empleadoViews.mostrarMensaje("Empleado eliminado exitosamente.");
            } else {
                empleadoViews.mostrarMensaje("Error al eliminar el empleado.");
            }
        } catch (Exception e) {
            empleadoViews.mostrarMensaje("Error al eliminar el empleado: " + e.getMessage());
        }
    }

    private void listarEmpleadosPorHotel() {
        try {
            int hotelId = Integer.parseInt(JOptionPane.showInputDialog("Ingresa el ID del hotel:"));
            List<String> empleados = empleadoService.listarEmpleadosPorHotelId(hotelId);
            empleadoViews.mostrarListaEmpleados(empleados);
        } catch (Exception e) {
            empleadoViews.mostrarMensaje("Error al listar los empleados: " + e.getMessage());
        }
    }


}
