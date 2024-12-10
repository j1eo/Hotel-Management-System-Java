package views;

import java.sql.Date;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

import beans.HabitacionBean;
import beans.HotelBean;
import beans.ReservacionBean;
import controllers.ReservacionController;
import services.ReservacionService;
import services.HotelService;

public class ReservacionViews {

    public int pedirVendedorId() {
        return Integer.parseInt(JOptionPane.showInputDialog("Ingresa el empleado_ID del vendedor:"));
    }

    public HotelBean seleccionarHotel(List<HotelBean> hoteles) {
        HotelBean[] opciones = hoteles.toArray(new HotelBean[0]);
        String[] opcionesNombres = new String[opciones.length];
        for (int i = 0; i < opciones.length; i++) {
            opcionesNombres[i] = opciones[i].getNombre(); // Usar el nombre del hotel
        }
        int seleccion = JOptionPane.showOptionDialog(null, "Selecciona un hotel:", "Hoteles Disponibles",
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, opcionesNombres, opcionesNombres[0]);
        return seleccion >= 0 ? opciones[seleccion] : null;
    }

    public HabitacionBean seleccionarHabitacion(List<HabitacionBean> habitaciones) {
        JList<HabitacionBean> list = new JList<>(habitaciones.toArray(new HabitacionBean[0]));
        list.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                HabitacionBean habitacion = (HabitacionBean) value;
                String display = "Habitación ID: " + habitacion.getHabitacionId() + " - Tipo: " + habitacion.getTipo();
                return super.getListCellRendererComponent(list, display, index, isSelected, cellHasFocus);
            }
        });

        JScrollPane scrollPane = new JScrollPane(list);
        list.setVisibleRowCount(10); // Número de elementos visibles antes de mostrar la barra de desplazamiento
        int seleccion = JOptionPane.showConfirmDialog(null, scrollPane, "Selecciona una habitación", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (seleccion == JOptionPane.OK_OPTION) {
            return list.getSelectedValue();
        } else {
            return null;
        }
    }

    public int pedirNumeroPersonas() {
        return Integer.parseInt(JOptionPane.showInputDialog("Ingresa el número de personas que se hospedarán:"));
    }

    public Date pedirFechaRegistro() {
        // Crear el panel principal
        JPanel panel = new JPanel(new GridLayout(3, 2));

        // Crear las etiquetas y combo boxes para el formulario
        JLabel yearLabel = new JLabel("Selecciona el año:");
        String[] years = { "2024", "2025", "2026", "2027" };
        JComboBox<String> yearComboBox = new JComboBox<>(years);

        JLabel monthLabel = new JLabel("Selecciona el mes:");
        String[] months = { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12" };
        JComboBox<String> monthComboBox = new JComboBox<>(months);

        JLabel dayLabel = new JLabel("Selecciona el día:");
        String[] days = new String[31];
        for (int i = 0; i < 31; i++) {
            days[i] = String.format("%02d", i + 1);
        }
        JComboBox<String> dayComboBox = new JComboBox<>(days);

        // Agregar los componentes al panel
        panel.add(yearLabel);
        panel.add(yearComboBox);
        panel.add(monthLabel);
        panel.add(monthComboBox);
        panel.add(dayLabel);
        panel.add(dayComboBox);

        // Mostrar el panel en un JOptionPane
        int result = JOptionPane.showConfirmDialog(null, panel, "Fecha de Registro", JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            String year = (String) yearComboBox.getSelectedItem();
            String month = (String) monthComboBox.getSelectedItem();
            String day = (String) dayComboBox.getSelectedItem();

            String fechaStr = year + "-" + month + "-" + day;
            return Date.valueOf(fechaStr);
        } else {
            return null; // Manejar el caso en que el usuario cancele la selección
        }
    }

    public int pedirDuracionEstadia() {
        return Integer.parseInt(JOptionPane.showInputDialog("Ingresa la duración de la estadía (en días):"));
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(null, mensaje);
    }

    public void mostrarTabla(String titulo, String[] columnNames, Object[][] data) {
        DefaultTableModel model = new DefaultTableModel(data, columnNames);
        JTable table = new JTable(model);
        TableColumn column = table.getColumnModel().getColumn(1);
        column.setPreferredWidth(200);
        JScrollPane scrollPane = new JScrollPane(table);
        JOptionPane.showMessageDialog(null, scrollPane, titulo, JOptionPane.PLAIN_MESSAGE);
    }

    public void mostrarMenuReservacion(ReservacionController reservacionController) {
        try {
        	reservacionController.realizarReservacion();
        } catch (Exception e) {
            mostrarMensaje("Error al realizar la reservación: " + e.getMessage());
        }
    }



}
