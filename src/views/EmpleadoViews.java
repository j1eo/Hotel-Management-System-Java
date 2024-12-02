package views;

import controllers.EmpleadoController;

import javax.swing.*;
import java.util.List;

public class EmpleadoViews {

	private static final String EXIT_OPTION = "Salir";

	public void menuPrincipalEmpleado(EmpleadoController empleadoController) {
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

				String tipoEmpleado = solicitarTipoEmpleado();
				if (tipoEmpleado == null) {
					mostrarMensaje("Cancelando la adición de empleado...");
					break;
				}

				String nombre = solicitarNombreEmpleado();

				empleadoController.agregarEmpleado(tipoEmpleado, nombre, hotelId);
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

				int empleadoId = Integer.parseInt(JOptionPane.showInputDialog("Ingresa el ID del empleado a modificar:"));
				String nombre = solicitarNombreEmpleado();

				String NuevoTipoEmpleado = solicitarTipoEmpleado();
				if (NuevoTipoEmpleado == null) {
					mostrarMensaje("Cancelando la adición de empleado...");
					break;
				}

				empleadoController.modificarEmpleado(hotelId, empleadoId,nombre,NuevoTipoEmpleado );
				mostrarMensaje("Empleado modificado exitosamente.");
				break;
			} catch (Exception e) {
				mostrarMensaje("Ha ocurrido un error al agregar el empleado: " + e.getMessage());
			}
		}
	}

	private String solicitarTipoEmpleado() {
		String[] options = { "Recamarera Auxiliar", "Recamarera Principiante", "Recamarera Experimentada",
				"Ama de Llaves", "Vendedor", EXIT_OPTION };
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

	private String solicitarNombreEmpleado() {
		return JOptionPane.showInputDialog("Ingresa el nombre del empleado:");
	}

	private double solicitarSalarioEmpleado() throws NumberFormatException {
		String salarioStr = JOptionPane.showInputDialog("Ingresa el salario del empleado:");
		return Double.parseDouble(salarioStr);
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

	public String solicitarNombreGerente() {
		return JOptionPane.showInputDialog("Ingresa el nombre del gerente:");
	}

	public void mostrarListaEmpleados(List<String> empleados) {
		StringBuilder message = new StringBuilder("Lista de Empleados:\n");
		for (String empleado : empleados) {
			message.append(empleado).append("\n");
		}
		mostrarMensaje(message.toString());
	}

}
