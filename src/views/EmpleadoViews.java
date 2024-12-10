package views;

import controllers.EmpleadoController;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import beans.HotelBean;

import java.util.List;

public class EmpleadoViews {

    private static final String EXIT_OPTION = "Salir";

    public void menuPrincipalEmpleado(EmpleadoController empleadoController) {
    	 while (true) {
             Object[] options = {"Información General de Empleados", "Información de Recamareras", "Salir"};
             int selectedOption = mostrarOpciones("Selecciona una opción", "Sistema de Gestión de Pagos para Empleados de la Cadena de Hoteles UR", options);

             if (selectedOption == JOptionPane.CLOSED_OPTION || selectedOption == 2) {
                 mostrarMensaje("Saliendo del sistema...");
                 break;
             }

             String selectedOptionString = options[selectedOption].toString();
             empleadoController.evalOption(selectedOptionString);
         }
    }
    
    public void menuGeneralEmpleado(EmpleadoController empleadoController) {
        Object selectedOption;
        while (true) {
            try {
                String[] options = { "Listar Empleados", "Agregar Empleado", "Modificar Empleado", "Eliminar Empleado",
                        EXIT_OPTION };
                selectedOption = JOptionPane.showInputDialog(null, "Selecciona una opción",
                        "Menú Principal de Empleados", JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

                // Verificar si el usuario hizo clic en el botón de cancelar
                if (selectedOption == null || selectedOption.toString().equals(EXIT_OPTION)) {
                    mostrarMensaje("Saliendo del sistema de empleados...");
                    break;
                }

                empleadoController.evalOption(selectedOption);
            } catch (Exception e) {
                mostrarMensaje("Ha ocurrido un error: " + e.getMessage());
            }
        }
    }
    
    public void menuRecamarera(EmpleadoController empleadoController) {
        Object selectedOption;
        while (true) {
            try {
                String[] options = { "Listar Info. Recamareras", "Asignar Habitacion a Recamarera",
                        EXIT_OPTION };
                selectedOption = JOptionPane.showInputDialog(null, "Selecciona una opción",
                        "Menú Principal de Empleados", JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

                // Verificar si el usuario hizo clic en el botón de cancelar
                if (selectedOption == null || selectedOption.toString().equals(EXIT_OPTION)) {
                    mostrarMensaje("Saliendo del sistema de empleados...");
                    break;
                }

                empleadoController.evalOption(selectedOption);
            } catch (Exception e) {
                mostrarMensaje("Ha ocurrido un error: " + e.getMessage());
            }
        }
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(null, mensaje);
    }

    public void mostrarAgregarEmpleadoMenu(EmpleadoController empleadoController) {
           while (true) {
               try {
                   int hotelId = solicitarIdHotel();
                   if (hotelId == -1) {
                       mostrarMensaje("Cancelando la adición de empleado...");
                       break;
                   }

                   Object tipoEmpleado = solicitarTipoEmpleado();
                   if (tipoEmpleado == null) {
                       mostrarMensaje("Cancelando la adición de empleado...");
                       break;
   				}
                   
                   if (tipoEmpleado == null || tipoEmpleado.toString().equals(EXIT_OPTION)) {
                       mostrarMensaje("Cancelando la adición de empleado...");
                       break;
                   }
                String nombre = solicitarNombreEmpleado();
   				empleadoController.evalEmpleado(hotelId,tipoEmpleado,nombre);

                   mostrarMensaje("Empleado agregado exitosamente.");
                   break;
               } catch (Exception e) {
                   mostrarMensaje("Ha ocurrido un error al agregar el empleado: " + e.getMessage());
               }
           }
       }
    

    public void mostrarModificarEmpleadoMenu(EmpleadoController empleadoController) {
        while (true) {
            try {
                int hotelId = solicitarIdHotel();
                if (hotelId < 1) {
                    mostrarMensaje("ID Invalida, Cancelando la modificación de empleado...");
                    break;
                }

                int empleadoId = Integer
                        .parseInt(JOptionPane.showInputDialog("Ingresa el ID del empleado a modificar:"));
                String nombre = solicitarNombreEmpleado();

                String nuevoTipoEmpleado = solicitarTipoEmpleado();
                if (nuevoTipoEmpleado == null) {
                    mostrarMensaje("Cancelando la modificación de empleado...");
                    break;
                }

                empleadoController.modificarEmpleado(hotelId, empleadoId, nombre, nuevoTipoEmpleado);
                mostrarMensaje("Empleado modificado exitosamente.");
                break;
            } catch (Exception e) {
                mostrarMensaje("Ha ocurrido un error al modificar el empleado: " + e.getMessage());
            }
        }
    }

    public String solicitarNombreGerente() {
        return JOptionPane.showInputDialog("Ingresa el nombre del gerente:");
    }

    private String solicitarTipoEmpleado() {
        String[] options = { "Recamarera Auxiliar", "Recamarera Principiante", "Recamarera Experimentada",
                "Ama de Llaves", "Vendedor", "Gerente", EXIT_OPTION };
        Object selectedOption = JOptionPane.showInputDialog(null, "Selecciona el tipo de empleado a agregar",
                "Agregar Empleado", JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
        return (selectedOption == null || selectedOption.toString().equals(EXIT_OPTION)) ? null
                : selectedOption.toString();
    }

    private int solicitarIdHotel() {
        try {
            String hotelIdStr = JOptionPane.showInputDialog("Ingresa el ID del hotel al que se asignará el empleado:");
            if (hotelIdStr == null || hotelIdStr.isEmpty()) {
                return -1; // Indicador de cancelación
            }
            return Integer.parseInt(hotelIdStr);
        } catch (NumberFormatException e) {
            mostrarMensaje("ID de hotel inválido. Por favor, intenta nuevamente.");
            return solicitarIdHotel();
        }
    }

    public String solicitarNombreEmpleado() {
        return JOptionPane.showInputDialog("Ingresa el nombre del empleado:");
    }

    public Integer solicitarIdHotelGeneral() {
        while (true) {
            String hotelIdStr = JOptionPane.showInputDialog("Ingresa el ID del hotel:");
            if (hotelIdStr != null && !hotelIdStr.isEmpty()) {
                try {
                    return Integer.parseInt(hotelIdStr);
                } catch (NumberFormatException e) {
                    mostrarMensaje("ID de hotel inválido. Por favor, intenta nuevamente.");
                }
            } else {
                return null; // Permitir la cancelación de la entrada
            }
        }
    }

    public void mostrarListaEmpleados(HotelBean hotel, List<String> empleados) {
        // Información del hotel como texto
        String hotelInfo = "Hotel: " + hotel.getNombre() + " (ID: " + hotel.getHotelId() + ")";
        System.out.println("Mostrando información del hotel: " + hotelInfo); // Depuración

        // Verificar que la lista de empleados no esté vacía
        if (empleados == null || empleados.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No se encontraron empleados para el hotel: " + hotel.getNombre(), "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Mostrar la información de los empleados en una tabla
        String[] empleadoColumnNames = {"ID_Empleado", "Nombre", "Puesto", "Salario"};
        Object[][] empleadoData = new Object[empleados.size() - 1][empleadoColumnNames.length]; // Restar 1 porque la primera entrada es del hotel

        for (int i = 1; i < empleados.size(); i++) { // Empezar desde 1 para omitir la entrada del hotel
            try {
                String[] empleadoInfo = empleados.get(i).split(", ");
                if (empleadoInfo.length >= 4) {
                    empleadoData[i - 1][0] = empleadoInfo[0].split(": ")[1]; // ID del empleado
                    empleadoData[i - 1][1] = empleadoInfo[1].split(": ")[1]; // Nombre del empleado
                    empleadoData[i - 1][2] = empleadoInfo[2].split(": ")[1]; // Puesto del empleado
                    empleadoData[i - 1][3] = empleadoInfo[3].split(": ")[1]; // Salario del empleado
                    System.out.println("Empleado: " + empleadoInfo[1].split(": ")[1] + ", Puesto: " + empleadoInfo[2].split(": ")[1] + ", Salario: " + empleadoInfo[3].split(": ")[1]); // Depuración
                } else {
                    throw new Exception("Formato incorrecto para el empleado: " + empleados.get(i));
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error al procesar los datos del empleado: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                return; // Salir del método si hay un error
            }
        }

        DefaultTableModel model = new DefaultTableModel(empleadoData, empleadoColumnNames);
        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);

        // Mostrar mensaje con información del hotel y la tabla de empleados
        JOptionPane.showMessageDialog(null, new Object[] {hotelInfo, scrollPane}, "Lista de Empleados", JOptionPane.PLAIN_MESSAGE);
    }


    public void mostrarTabla(String titulo, String[] columnNames, Object[][] data) {
        DefaultTableModel model = new DefaultTableModel(data, columnNames);
        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        JOptionPane.showMessageDialog(null, scrollPane, titulo, JOptionPane.PLAIN_MESSAGE);
    }
    
    private int mostrarOpciones(String mensaje, String titulo, Object[] options) {
        return JOptionPane.showOptionDialog(null, mensaje, titulo, JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
    }
}
