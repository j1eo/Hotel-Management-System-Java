package views;

import controllers.SystemController;

import javax.swing.*;

public class SystemViews {

    private final SystemController systemController;

    public SystemViews(SystemController systemController) {
        this.systemController = systemController;
    }

    public void mostrarMenuPrincipal() {
        while (true) {
            Object[] options = {"Información de Hoteles", "Información de Empleados", "Información de Habitaciones", "Información de Comisiones", "Salir"};
            int selectedOption = JOptionPane.showOptionDialog(null, "Selecciona una opción",
                    "Sistema de Gestión de Pagos para Empleados de la Cadena de Hoteles UR",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

            if (selectedOption == JOptionPane.CLOSED_OPTION || selectedOption == 4) {
                JOptionPane.showMessageDialog(null, "Saliendo del sistema...");
                break;
            }

            String selectedOptionString = options[selectedOption].toString();
            systemController.evalOption(selectedOptionString);
        }
    }
}
