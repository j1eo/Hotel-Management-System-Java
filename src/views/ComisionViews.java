package views;

import javax.swing.*;
import java.awt.*;
import java.sql.Date;
import java.util.List;

import beans.BonoGerenteBean;
import beans.ComisionRecamareraBean;
import beans.ComisionVendedorBean;
import beans.HotelBean;
import controllers.ComisionController;

public class ComisionViews {

    private static final String EXIT_OPTION = "Salir";

    private int mostrarOpciones(String mensaje, String titulo, Object[] options) {
        return JOptionPane.showOptionDialog(null, mensaje, titulo, JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(null, mensaje);
    }

    public void menuPrincipalComision(ComisionController comisionController) {
        while (true) {
            Object[] options = {"Comisiones por Dia","Comisiones por Mes","Comisiones de Vendedores", "Comisiones de Recamareras", "Bonos de Gerentes", EXIT_OPTION};
            int selectedOption = mostrarOpciones("Selecciona una opción", "Sistema de Gestión de Pagos para Empleados de la Cadena de Hoteles UR", options);

            if (selectedOption == JOptionPane.CLOSED_OPTION || selectedOption == 5) {
                mostrarMensaje("Saliendo del sistema...");
                break;
            }

            String selectedOptionString = options[selectedOption].toString();
            comisionController.evalOption(selectedOptionString);
        }
    }

    public Date pedirFechaMes() {
        JPanel panel = new JPanel(new GridLayout(3, 2));

        JLabel yearLabel = new JLabel("Selecciona el año:");
        String[] years = {"2024", "2025", "2026", "2027"};
        JComboBox<String> yearComboBox = new JComboBox<>(years);

        JLabel monthLabel = new JLabel("Selecciona el mes:");
        String[] months = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
        JComboBox<String> monthComboBox = new JComboBox<>(months);

        panel.add(yearLabel);
        panel.add(yearComboBox);
        panel.add(monthLabel);
        panel.add(monthComboBox);

        int result = JOptionPane.showConfirmDialog(null, panel, "Fecha de Registro", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            String year = (String) yearComboBox.getSelectedItem();
            String month = (String) monthComboBox.getSelectedItem();

            String fechaStr = year + "-" + month + "-01"; // Año-Mes-Día (Día 1 para representar el mes)
            return Date.valueOf(fechaStr);
        } else {
            return null;
        }
    }
    
    public Date pedirFechaDia() {
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
        int result = JOptionPane.showConfirmDialog(null, panel, "Fecha de Comisiones", JOptionPane.OK_CANCEL_OPTION,
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
    public HotelBean seleccionarHotel(List<HotelBean> hoteles) {
        // Crear una lista de opciones para el cuadro de diálogo
        String[] opciones = new String[hoteles.size()];
        for (int i = 0; i < hoteles.size(); i++) {
            opciones[i] = hoteles.get(i).getNombre() + ", " + hoteles.get(i).getEstrellas() + " Estrellas";
        }

        // Mostrar el cuadro de diálogo para seleccionar un hotel
        String seleccion = (String) JOptionPane.showInputDialog(null, "Selecciona un hotel",
                "Hoteles Registrados", JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[0]);

        // Buscar el hotel seleccionado en la lista de hoteles
        if (seleccion != null) {
            String nombreHotel = seleccion.split(", ")[0]; // Obtener el nombre del hotel
            for (HotelBean hotel : hoteles) {
                if (hotel.getNombre().equals(nombreHotel)) {
                    return hotel;
                }
            }
        }
        return null;
    }


    public void mostrarBonosGerentes(List<BonoGerenteBean> bonos, List<String> nombresGerentes) {
        // Verificar si la lista de nombres de gerentes tiene el mismo tamaño que la lista de bonos
        if (bonos.size() != nombresGerentes.size()) {
            mostrarMensaje("Error: La lista de nombres de gerentes no coincide con la lista de bonos.");
            return;
        }

        String[] columnNames = {"Empleado ID", "Nombre", "Hotel ID", "Mes", "Bono"};
        String[][] data = new String[bonos.size()][columnNames.length];

        for (int i = 0; i < bonos.size(); i++) {
            BonoGerenteBean bono = bonos.get(i);
            data[i][0] = String.valueOf(bono.getEmpleadoId());
            data[i][1] = nombresGerentes.get(i);
            data[i][2] = String.valueOf(bono.getHotelId());
            data[i][3] = bono.getMes();
            data[i][4] = String.valueOf(bono.getMonto());
        }

        JTable table = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(table);
        JOptionPane.showMessageDialog(null, scrollPane, "Bonos de Gerentes", JOptionPane.PLAIN_MESSAGE);
    }
    
 // Método para mostrar las comisiones de recamareras
    public void mostrarComisionesRecamareras(List<ComisionRecamareraBean> comisiones, List<String> nombresRecamareras, List<String> nivelesExperiencia) {
        // Verificar si la lista de nombres de recamareras y niveles de experiencia tiene el mismo tamaño que la lista de comisiones
        if (comisiones.size() != nombresRecamareras.size() || comisiones.size() != nivelesExperiencia.size()) {
            mostrarMensaje("Error: La lista de nombres de recamareras o niveles de experiencia no coincide con la lista de comisiones.");
            return;
        }

        String[] columnNames = {"Empleado ID", "Nombre", "Nivel de Experiencia", "Mes", "Comisión"};
        String[][] data = new String[comisiones.size()][columnNames.length];

        for (int i = 0; i < comisiones.size(); i++) {
            ComisionRecamareraBean comision = comisiones.get(i);
            data[i][0] = String.valueOf(comision.getEmpleadoId());
            data[i][1] = nombresRecamareras.get(i);
            data[i][2] = nivelesExperiencia.get(i);
            data[i][3] = comision.getFecha().toString().substring(0, 7); // Obtener el mes en formato YYYY-MM
            data[i][4] = String.valueOf(comision.getMonto());
        }

        JTable table = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(table);
        JOptionPane.showMessageDialog(null, scrollPane, "Comisiones de Recamareras", JOptionPane.PLAIN_MESSAGE);
    }
    
 // Método para mostrar las comisiones de vendedores
    public void mostrarComisionesVendedores(List<ComisionVendedorBean> comisiones, List<String> nombresVendedores) {
        // Verificar si la lista de nombres de vendedores tiene el mismo tamaño que la lista de comisiones
        if (comisiones.size() != nombresVendedores.size()) {
            mostrarMensaje("Error: La lista de nombres de vendedores no coincide con la lista de comisiones.");
            return;
        }

        String[] columnNames = {"Empleado ID", "Nombre", "Puesto", "Mes", "Comisión"};
        String[][] data = new String[comisiones.size()][columnNames.length];

        for (int i = 0; i < comisiones.size(); i++) {
            ComisionVendedorBean comision = comisiones.get(i);
            data[i][0] = String.valueOf(comision.getEmpleadoId());
            data[i][1] = nombresVendedores.get(i);
            data[i][2] = "Vendedor"; // Rellenar con "Vendedor"
            data[i][3] = comision.getFecha().toString().substring(0, 7); // Obtener el mes en formato YYYY-MM
            data[i][4] = String.valueOf(comision.getMonto());
        }

        JTable table = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(table);
        JOptionPane.showMessageDialog(null, scrollPane, "Comisiones de Vendedores", JOptionPane.PLAIN_MESSAGE);
    }
    
    public void mostrarTotalComisionDia(int hotelId, Date dia, double totalComision) {
        String[] columnNames = {"Hotel ID", "Fecha", "Total Comisiones"};
        Object[][] data = {
            {hotelId, dia.toString(), totalComision}
        };

        JTable table = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(table);
        JOptionPane.showMessageDialog(null, scrollPane, "Total de Comisiones del Día", JOptionPane.PLAIN_MESSAGE);
    }
    
    public void mostrarTotalComisionMes(int hotelId, String mes, double totalComision) {
        String[] columnNames = {"Hotel ID", "Mes", "Total Comisiones"};
        Object[][] data = {
            {hotelId, mes, totalComision}
        };

        JTable table = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(table);
        JOptionPane.showMessageDialog(null, scrollPane, "Total de Comisiones del Mes", JOptionPane.PLAIN_MESSAGE);
    }

}
