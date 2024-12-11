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
		// Crear una lista de opciones para el cuadro de diálogo
		String[] opciones = new String[hoteles.size()];
		for (int i = 0; i < hoteles.size(); i++) {
			opciones[i] = hoteles.get(i).getNombre() + ", " + hoteles.get(i).getEstrellas() + " Estrellas";
		}

		// Mostrar el cuadro de diálogo para seleccionar un hotel
		String seleccion = (String) JOptionPane.showInputDialog(null, "Selecciona un hotel", "Hoteles Registrados",
				JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[0]);

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

	public HabitacionBean seleccionarHabitacion(List<HabitacionBean> habitaciones) {
		JList<HabitacionBean> list = new JList<>(habitaciones.toArray(new HabitacionBean[0]));
		list.setCellRenderer(new DefaultListCellRenderer() {
			@Override
			public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
					boolean cellHasFocus) {
				HabitacionBean habitacion = (HabitacionBean) value;
				String display = "Habitación ID: " + habitacion.getHabitacionId() + " - Tipo: " + habitacion.getTipo();
				return super.getListCellRendererComponent(list, display, index, isSelected, cellHasFocus);
			}
		});

		JScrollPane scrollPane = new JScrollPane(list);
		list.setVisibleRowCount(10); // Número de elementos visibles antes de mostrar la barra de desplazamiento
		int seleccion = JOptionPane.showConfirmDialog(null, scrollPane, "Selecciona una habitación",
				JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

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

	public Date pedirFechaMes() {
		JPanel panel = new JPanel(new GridLayout(3, 2));

		JLabel yearLabel = new JLabel("Selecciona el año:");
		String[] years = { "2024", "2025", "2026", "2027" };
		JComboBox<String> yearComboBox = new JComboBox<>(years);

		JLabel monthLabel = new JLabel("Selecciona el mes:");
		String[] months = { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12" };
		JComboBox<String> monthComboBox = new JComboBox<>(months);

		panel.add(yearLabel);
		panel.add(yearComboBox);
		panel.add(monthLabel);
		panel.add(monthComboBox);

		int result = JOptionPane.showConfirmDialog(null, panel, "Fecha de Registro", JOptionPane.OK_CANCEL_OPTION,
				JOptionPane.PLAIN_MESSAGE);

		if (result == JOptionPane.OK_OPTION) {
			String year = (String) yearComboBox.getSelectedItem();
			String month = (String) monthComboBox.getSelectedItem();

			String fechaStr = year + "-" + month + "-01"; // Año-Mes-Día (Día 1 para representar el mes)
			return Date.valueOf(fechaStr);
		} else {
			return null;
		}
	}

	public void mostrarTotalVentasMes(int hotelId, String mes, double totalVentas) {
		String[] columnNames = { "Hotel ID", "Mes", "Total Ventas" };
		Object[][] data = { { hotelId, mes, totalVentas } };
		JTable table = new JTable(data, columnNames);
		JScrollPane scrollPane = new JScrollPane(table);
		JOptionPane.showMessageDialog(null, scrollPane, "Total de Ventas del Mes", JOptionPane.PLAIN_MESSAGE);
	}

}
