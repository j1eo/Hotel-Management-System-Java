package views;

import beans.HabitacionBean;
import beans.HotelBean;
import beans.RecamareraBean;
import controllers.RecamareraController;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import java.awt.BorderLayout;
import java.util.List;

public class RecamareraViews {
    private static final String EXIT_OPTION = "Salir";

    public void menuRecamarera(RecamareraController recamareraController) {
        Object selectedOption;
        while (true) {
            try {
                String[] options = {"Listar Info. Recamareras", "Asignar Habitacion a Recamarera", "Listar Habitaciones Sin Recamarera", EXIT_OPTION};
                selectedOption = JOptionPane.showInputDialog(null, "Selecciona una opción", "Menú Principal de Empleados", JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

                // Verificar si el usuario hizo clic en el botón de cancelar
                if (selectedOption == null || selectedOption.toString().equals(EXIT_OPTION)) {
                    mostrarMensaje("Saliendo del sistema de empleados...");
                    break;
                }

                recamareraController.evalOption(selectedOption);
            } catch (Exception e) {
                mostrarMensaje("Ha ocurrido un error: " + e.getMessage());
            }
        }
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(null, mensaje);
    }

    public int pedirHotelId() {
        return Integer.parseInt(JOptionPane.showInputDialog("Ingresa el ID del hotel:"));
    }

    public int pedirRecamareraId() {
        return Integer.parseInt(JOptionPane.showInputDialog("Ingresa el ID de la recamarera:"));
    }

    public int pedirHabitacionId() {
        return Integer.parseInt(JOptionPane.showInputDialog("Ingresa el ID de la habitación:"));
    }

    public String pedirNombreHotel() {
        return JOptionPane.showInputDialog("Ingresa el nombre del hotel:");
    }

    public void mostrarListaRecamareras(HotelBean hotel, List<String> recamareras) {
        // Información del hotel como texto
        String hotelInfo = "Hotel: " + hotel.getNombre() + " (ID: " + hotel.getHotelId() + ")";
        System.out.println("Mostrando información del hotel: " + hotelInfo); // Depuración

        // Verificar que la lista de recamareras no esté vacía
        if (recamareras == null || recamareras.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No se encontraron recamareras para el hotel: " + hotel.getNombre(), "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Mostrar la información de las recamareras en una tabla
        String[] recamareraColumnNames = {"Empleado ID", "Recamarera ID", "Nombre", "Nivel de Experiencia", "Habitacion ID", "Tipo Habitación"};
        Object[][] recamareraData = new Object[recamareras.size() - 1][recamareraColumnNames.length]; // Restar 1 porque la primera entrada es del hotel

        for (int i = 1; i < recamareras.size(); i++) { // Empezar desde 1 para omitir la entrada del hotel
            try {
                String[] recamareraInfo = recamareras.get(i).split(", ");
                if (recamareraInfo.length >= 6) {
                    recamareraData[i - 1][0] = recamareraInfo[0].split(": ")[1]; // Empleado ID
                    recamareraData[i - 1][1] = recamareraInfo[1].split(": ")[1]; // Recamarera ID
                    recamareraData[i - 1][2] = recamareraInfo[2].split(": ")[1]; // Nombre de la recamarera
                    recamareraData[i - 1][3] = recamareraInfo[3].split(": ")[1]; // Nivel de Experiencia
                    recamareraData[i - 1][4] = recamareraInfo[4].split(": ")[1]; // Habitacion ID
                    recamareraData[i - 1][5] = recamareraInfo[5].split(": ")[1]; // Tipo Habitación
                    System.out.println("Recamarera: " + recamareraInfo[2].split(": ")[1] + ", Nivel de Experiencia: " + recamareraInfo[3].split(": ")[1] + ", Habitacion ID: " + recamareraInfo[4].split(": ")[1] + ", Tipo Habitación: " + recamareraInfo[5].split(": ")[1]); // Depuración
                } else {
                    throw new Exception("Formato incorrecto para la recamarera: " + recamareras.get(i));
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error al procesar los datos de la recamarera: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                return; // Salir del método si hay un error
            }
        }

        DefaultTableModel model = new DefaultTableModel(recamareraData, recamareraColumnNames);
        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);

        // Ajustar el ancho de las columnas
        TableColumnModel columnModel = table.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(50);  // Empleado ID
        columnModel.getColumn(1).setPreferredWidth(50);  // Recamarera ID
        columnModel.getColumn(2).setPreferredWidth(150); // Nombre
        columnModel.getColumn(3).setPreferredWidth(150); // Nivel de Experiencia
        columnModel.getColumn(4).setPreferredWidth(100); // Habitacion ID
        columnModel.getColumn(5).setPreferredWidth(100); // Tipo Habitación

        // Crear un panel con la tabla y la información del hotel
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JLabel(hotelInfo), BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Crear un JDialog para personalizar el tamaño y centrar la ventana
        JDialog dialog = new JOptionPane(panel, JOptionPane.PLAIN_MESSAGE, JOptionPane.DEFAULT_OPTION).createDialog("Lista de Recamareras");
        dialog.setSize(800, 600); // Establecer tamaño personalizado
        dialog.setLocationRelativeTo(null); // Centrar la ventana en la pantalla
        dialog.setVisible(true);
    }




    public void mostrarListaHabitaciones(List<HabitacionBean> habitaciones) {
        // Verificar que la lista de habitaciones no esté vacía
        if (habitaciones == null || habitaciones.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No se encontraron habitaciones sin recamarera asignada.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Mostrar la información de las habitaciones en una tabla
        String[] columnNames = {"Habitacion ID", "Hotel ID", "ID", "Tipo", "Disponible", "Numero de Personas"};
        Object[][] data = new Object[habitaciones.size()][6];

        for (int i = 0; i < habitaciones.size(); i++) {
            HabitacionBean habitacion = habitaciones.get(i);
            data[i][0] = habitacion.getHabitacionId();
            data[i][1] = habitacion.getHotelId();
            data[i][2] = habitacion.getId();
            data[i][3] = habitacion.getTipo();
            data[i][4] = habitacion.isDisponible();
            data[i][5] = habitacion.getNumeroPersonas();
        }

        DefaultTableModel model = new DefaultTableModel(data, columnNames);
        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);

        // Mostrar mensaje con información del hotel y la tabla de habitaciones
        JOptionPane.showMessageDialog(null, scrollPane, "Lista de Habitaciones Sin Recamarera Asignada", JOptionPane.PLAIN_MESSAGE);
    }

    public void mostrarTabla(String titulo, String[] columnNames, Object[][] data) {
        DefaultTableModel model = new DefaultTableModel(data, columnNames);
        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        JOptionPane.showMessageDialog(null, scrollPane, titulo, JOptionPane.PLAIN_MESSAGE);
    }
    
    public HabitacionBean seleccionarHabitacion(List<HabitacionBean> habitaciones) {
        String[] opciones = habitaciones.stream()
                .map(h -> "ID: " + h.getHabitacionId() + ", Tipo: " + h.getTipo())
                .toArray(String[]::new);
        String seleccion = (String) JOptionPane.showInputDialog(null, "Selecciona una habitación",
                "Habitaciones Disponibles", JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[0]);
        if (seleccion != null) {
            int habitacionId = Integer.parseInt(seleccion.split(", ")[0].split(": ")[1]);
            return habitaciones.stream().filter(h -> h.getHabitacionId() == habitacionId).findFirst().orElse(null);
        }
        return null;
    }
    
    public RecamareraBean seleccionarRecamarera(List<RecamareraBean> recamareras) {
        String[] opciones = recamareras.stream()
                .map(r -> "ID: " + r.getRecamareraId() + ", Nombre: " + r.getNombre())
                .toArray(String[]::new);
        String seleccion = (String) JOptionPane.showInputDialog(null, "Selecciona una recamarera",
                "Recamareras Disponibles", JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[0]);
        if (seleccion != null) {
            int recamareraId = Integer.parseInt(seleccion.split(", ")[0].split(": ")[1]);
            return recamareras.stream().filter(r -> r.getRecamareraId() == recamareraId).findFirst().orElse(null);
        }
        return null;
    }


}
