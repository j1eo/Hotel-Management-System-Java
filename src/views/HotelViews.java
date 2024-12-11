package views;

import controllers.HotelController;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

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
	    String[] columnNames = { "ID", "Tipo", "Recamarera ID", "Disponible", "Nº Personas", "Hotel ID", "Costo" }; // Agregar "Costo"
	    Object[][] data = new Object[habitaciones.size()][columnNames.length];

	    for (int i = 0; i < habitaciones.size(); i++) {
	        HabitacionBean habitacion = habitaciones.get(i);
	        data[i][0] = habitacion.getId();
	        data[i][1] = habitacion.getTipo();
	        data[i][2] = habitacion.getRecamareraId() != null ? habitacion.getRecamareraId() : "N/A";
	        data[i][3] = habitacion.isDisponible() ? "Sí" : "No";
	        data[i][4] = habitacion.getNumeroPersonas();
	        data[i][5] = habitacion.getHotelId();
	        data[i][6] = habitacion.getCosto(); // Agregar el costo
	    }

	    mostrarTabla("Habitaciones del hotel " + nombreHotel, columnNames, data);
	}


	public void mostrarTabla(String titulo, String[] columnNames, Object[][] data) {
	    DefaultTableModel model = new DefaultTableModel(data, columnNames);
	    JTable table = new JTable(model);

	    // Ajustar el ancho de las columnas
	    TableColumnModel columnModel = table.getColumnModel();
	    
	    // Ajustar anchos específicos para las columnas
	    columnModel.getColumn(0).setPreferredWidth(50);   // Columna "ID" más pequeña
	    columnModel.getColumn(1).setPreferredWidth(150);  // Columna "Nombre"
	    columnModel.getColumn(2).setPreferredWidth(50);   // Columna "Estrellas" más pequeña
	    columnModel.getColumn(3).setPreferredWidth(300);  // Columna "Dirección" más grande
	    columnModel.getColumn(4).setPreferredWidth(150);  // Columna "Teléfono" 

	    JScrollPane scrollPane = new JScrollPane(table);
	    
	    // Crear un panel con la tabla
	    JPanel panel = new JPanel(new BorderLayout());
	    panel.add(scrollPane, BorderLayout.CENTER);

	    // Crear un JDialog para personalizar el tamaño y centrar la ventana
	    JDialog dialog = new JOptionPane(panel, JOptionPane.PLAIN_MESSAGE, JOptionPane.DEFAULT_OPTION).createDialog(titulo);
	    dialog.setSize(1000, 600); // Establecer tamaño personalizado más grande
	    dialog.setLocationRelativeTo(null); // Centrar la ventana en la pantalla
	    dialog.setVisible(true);
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

	




}
