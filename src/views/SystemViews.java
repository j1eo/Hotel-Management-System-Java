package views;

import controllers.SystemController;

import javax.swing.*;

public class SystemViews {

    public SystemViews() {
        
    }

    public void mostrarMenuPrincipal(SystemController systemController) {
        while (true) {
            Object[] options = {"Entrar Como Administrador", "Entrar Como Vendedor", "Salir"};
            int selectedOption = mostrarOpciones("Selecciona una opción", "Sistema de la Cadena de Hoteles UR", options);

            if (selectedOption == JOptionPane.CLOSED_OPTION || selectedOption == 2) {
                mostrarMensaje("Saliendo del sistema...");
                break;
            }

            String selectedOptionString = options[selectedOption].toString();
            systemController.evalOption(selectedOptionString);
        }
    }

    public void mostrarMenuAdmin(SystemController systemController) {
        while (true) {
            Object[] options = {"Información de Hoteles", "Información de Empleados", "Información de Habitaciones", "Información de Comisiones","Información de Ventas", "Salir"};
            int selectedOption = mostrarOpciones("Selecciona una opción", "Sistema de Gestión de Pagos para Empleados de la Cadena de Hoteles UR", options);

            if (selectedOption == JOptionPane.CLOSED_OPTION || selectedOption == 5) {
                mostrarMensaje("Saliendo del sistema...");
                break;
            }

            String selectedOptionString = options[selectedOption].toString();
            systemController.evalOption(selectedOptionString);
        }
    }

    public void mostrarMenuVendedor(SystemController systemController) {
        while (true) {
            Object[] options = {"Realizar Reservacion", "Información de Habitaciones", "Salir"};
            int selectedOption = mostrarOpciones("Selecciona una opción", "Sistema de Gestión de Pagos para Empleados de la Cadena de Hoteles UR", options);

            if (selectedOption == JOptionPane.CLOSED_OPTION || selectedOption == 2) {
                mostrarMensaje("Saliendo del sistema...");
                break;
            }

            String selectedOptionString = options[selectedOption].toString();
            systemController.evalOption(selectedOptionString);
        }
    }

    private int mostrarOpciones(String mensaje, String titulo, Object[] options) {
        return JOptionPane.showOptionDialog(null, mensaje, titulo, JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(null, mensaje);
    }
}
