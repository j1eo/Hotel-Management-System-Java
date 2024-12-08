package views;

import controllers.HotelController;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import java.util.ArrayList;
import beans.HotelBean;
import beans.HabitacionBean;

public class HotelViews {

	private final HotelController hotelController;

	public HotelViews(HotelController hotelController) {
		this.hotelController = hotelController;
	}

	public void MenuPrincipalHotel(HotelController hotelController) {
		Object selectedOption;
		while (true) {
			try {
				String[] options = { "Listar Hoteles", "Agregar Hotel", "Listar Habitaciones de un Hotel",
						"Buscar Hotel por Nombre", "Eliminar Hotel", "Salir" };
				selectedOption = JOptionPane.showInputDialog(null, "Selecciona una opción", "Menú Principal de Hoteles",
						JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

				if (selectedOption == null || selectedOption.toString().equals("Salir")) {
					mostrarMensaje("Saliendo del sistema de hoteles...");
					break;
				}

				hotelController.evalOption(selectedOption);
			} catch (Exception e) {
				mostrarMensaje("Ha ocurrido un error: " + e.getMessage());
			}
		}
	}

	public void mostrarMensaje(String mensaje) {
		JOptionPane.showMessageDialog(null, mensaje);
	}

	public String solicitarNombreHotel() {
		return JOptionPane.showInputDialog("Ingresa el nombre del hotel:");
	}

	public int solicitarEstrellas() {
		return Integer.parseInt(JOptionPane.showInputDialog("Ingresa el número de estrellas:"));
	}

	public String solicitarDireccion() {
		return JOptionPane.showInputDialog("Ingresa la dirección del hotel:");
	}

	public String solicitarTelefono() {
		return JOptionPane.showInputDialog("Ingresa el teléfono del hotel:");
	}

	public void listarHoteles(ArrayList<HotelBean> hoteles) {
	    
	    String[] columnNames = {"ID", "Nombre", "Estrellas", "Dirección", "Teléfono"};
	    Object[][] data = new Object[hoteles.size()][columnNames.length];

	    for (int i = 0; i < hoteles.size(); i++) {
	        HotelBean hotel = hoteles.get(i);
	        data[i][0] = hotel.getHotelId();
	        data[i][1] = hotel.getNombre();
	        data[i][2] = hotel.getEstrellas();
	        data[i][3] = hotel.getDireccion();
	        data[i][4] = hotel.getTelefono();
	    }

	    mostrarTabla("Hoteles", columnNames, data);
	}


	public void listarHabitacionesDeHotel(String nombreHotel, ArrayList<HabitacionBean> habitaciones) {
		String[] columnNames = { "ID", "Tipo", "Recamarera", "Disponible", "Nº Personas", "Hotel ID" };
		Object[][] data = new Object[habitaciones.size()][columnNames.length];

		for (int i = 0; i < habitaciones.size(); i++) {
			HabitacionBean habitacion = habitaciones.get(i);
			data[i][0] = habitacion.getID();
			data[i][1] = habitacion.getTipo();
			data[i][2] = habitacion.getRecamareraId() != null ? habitacion.getRecamareraId() : "N/A";
			data[i][3] = habitacion.isDisponible() ? "Sí" : "No";
			data[i][4] = habitacion.getNumeroPersonas();
			data[i][5] = habitacion.getHotelId();
		}

		mostrarTabla("Habitaciones del hotel " + nombreHotel, columnNames, data);
	}

	public void mostrarHotel(HotelBean hotel) {
	    String[] columnNames = {"Campo", "Valor"};
	    Object[][] data = {
	        {"ID", hotel.getHotelId()},
	        {"Nombre", hotel.getNombre()},
	        {"Estrellas", hotel.getEstrellas()},
	        {"Dirección", hotel.getDireccion()},
	        {"Teléfono", hotel.getTelefono()}
	    };

	    mostrarTabla("Información del Hotel", columnNames, data);
	}


	public void mostrarTabla(String titulo, String[] columnNames, Object[][] data) {
		DefaultTableModel model = new DefaultTableModel(data, columnNames);
		JTable table = new JTable(model);
		TableColumn column = table.getColumnModel().getColumn(1);
		column.setPreferredWidth(200);
		JScrollPane scrollPane = new JScrollPane(table);
		JOptionPane.showMessageDialog(null, scrollPane, titulo, JOptionPane.PLAIN_MESSAGE);
	}

}
