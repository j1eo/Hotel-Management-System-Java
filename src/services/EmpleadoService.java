package services;

import beans.*;
import dataBase.ConexionDataBase;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmpleadoService {

	private final ConexionDataBase conexionDataBase;

	public EmpleadoService(ConexionDataBase conexionDataBase) {
		this.conexionDataBase = conexionDataBase;
	}

	public void agregarEmpleado(String nombre, String tipoEmpleado, int hotelId) {
		String sqlEmpleado = "INSERT INTO empleado (nombre, salario, tipo) VALUES (?, ?, ?)";
		String sqlEmpleadoHotel = "INSERT INTO empleado_hotel (empleado_id, hotel_id) VALUES (?, ?)";
		double salario = obtenerSalarioFijo(tipoEmpleado);

		try (Connection connection = conexionDataBase.getConnection()) {
			connection.setAutoCommit(false);

			try (PreparedStatement statementEmpleado = connection.prepareStatement(sqlEmpleado,
					PreparedStatement.RETURN_GENERATED_KEYS);
					PreparedStatement statementEmpleadoHotel = connection.prepareStatement(sqlEmpleadoHotel)) {

				// Insertar el empleado en la base de datos
				statementEmpleado.setString(1, nombre);
				statementEmpleado.setDouble(2, salario);
				statementEmpleado.setString(3, tipoEmpleado);
				statementEmpleado.executeUpdate();

				// Obtener el ID generado del empleado
				ResultSet generatedKeys = statementEmpleado.getGeneratedKeys();
				int empleadoId = -1;
				if (generatedKeys.next()) {
					empleadoId = generatedKeys.getInt(1);
				}

				// Asociar el empleado con el hotel
				statementEmpleadoHotel.setInt(1, empleadoId);
				statementEmpleadoHotel.setInt(2, hotelId);
				statementEmpleadoHotel.executeUpdate();

				connection.commit();
				;
			} catch (SQLException e) {
				connection.rollback();
				throw new SQLException("Error durante la inserción del empleado", e);
			}
		} catch (SQLException e) {
			e.printStackTrace();

		}
	}

	public void agregarGerente(String nombre,  int hotelId) throws SQLException {
		String sqlEmpleado = "INSERT INTO empleado (nombre, salario, tipo) VALUES (?, 18000.0, 'Gerente')";
		String sqlGerente = "INSERT INTO gerente (empleado_id, bono) VALUES (?, 0)";
		String sqlEmpleadoHotel = "INSERT INTO empleado_hotel (empleado_id, hotel_id) VALUES (?, ?)";

		try (Connection connection = conexionDataBase.getConnection();
				PreparedStatement statementEmpleado = connection.prepareStatement(sqlEmpleado,
						PreparedStatement.RETURN_GENERATED_KEYS);
				PreparedStatement statementGerente = connection.prepareStatement(sqlGerente);
				PreparedStatement statementEmpleadoHotel = connection.prepareStatement(sqlEmpleadoHotel)) {

			connection.setAutoCommit(false);

			// Insertar el empleado en la tabla empleado
			statementEmpleado.setString(1, nombre);
			statementEmpleado.executeUpdate();

			// Obtener el ID generado del empleado
			ResultSet generatedKeys = statementEmpleado.getGeneratedKeys();
			int empleadoId = -1;
			if (generatedKeys.next()) {
				empleadoId = generatedKeys.getInt(1);
			}

			// Insertar el gerente en la tabla gerente
			statementGerente.setInt(1, empleadoId);
		
			statementGerente.executeUpdate();

			// Asociar el empleado con el hotel en la tabla empleado_hotel
			statementEmpleadoHotel.setInt(1, empleadoId);
			statementEmpleadoHotel.setInt(2, hotelId);
			statementEmpleadoHotel.executeUpdate();

			connection.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
	}

	public void agregarRecamarera(String nombre, String nivelExperiencia, int hotelId) throws SQLException {
	    String sqlEmpleado = "INSERT INTO empleado (nombre, salario, tipo) VALUES (?, ?, 'Recamarera')";
	    String sqlRecamarera = "INSERT INTO recamarera (empleado_id, nivel_experiencia) VALUES (?, ?)";
	    String sqlEmpleadoHotel = "INSERT INTO empleado_hotel (empleado_id, hotel_id) VALUES (?, ?)";
	    double salario = obtenerSalarioFijo(nivelExperiencia);

	    try (Connection connection = conexionDataBase.getConnection();
	         PreparedStatement statementEmpleado = connection.prepareStatement(sqlEmpleado, PreparedStatement.RETURN_GENERATED_KEYS);
	         PreparedStatement statementRecamarera = connection.prepareStatement(sqlRecamarera);
	         PreparedStatement statementEmpleadoHotel = connection.prepareStatement(sqlEmpleadoHotel)) {

	        connection.setAutoCommit(false);

	        // Insertar el empleado en la tabla empleado
	        statementEmpleado.setString(1, nombre);
	        statementEmpleado.setDouble(2, salario);
	        statementEmpleado.executeUpdate();

	        // Obtener el ID generado del empleado
	        ResultSet generatedKeys = statementEmpleado.getGeneratedKeys();
	        int empleadoId = -1;
	        if (generatedKeys.next()) {
	            empleadoId = generatedKeys.getInt(1);
	        }

	        // Insertar la recamarera en la tabla recamarera
	        statementRecamarera.setInt(1, empleadoId);
	        statementRecamarera.setString(2, nivelExperiencia);
	        statementRecamarera.executeUpdate();

	        // Asociar el empleado con el hotel en la tabla empleado_hotel
	        statementEmpleadoHotel.setInt(1, empleadoId);
	        statementEmpleadoHotel.setInt(2, hotelId);
	        statementEmpleadoHotel.executeUpdate();

	        connection.commit();
	    } catch (SQLException e) {
	        e.printStackTrace();      
	        throw e;
	    }
	}

	
	public void agregarVendedor(String nombre, int hotelId) throws SQLException {
	    String sqlEmpleado = "INSERT INTO empleado (nombre, salario, tipo) VALUES (?, 4500.0, 'Vendedor')";
	    String sqlVendedor = "INSERT INTO vendedor (empleado_id, comision) VALUES (?, 0)";
	    String sqlEmpleadoHotel = "INSERT INTO empleado_hotel (empleado_id, hotel_id) VALUES (?, ?)";

	    try (Connection connection = conexionDataBase.getConnection();
	         PreparedStatement statementEmpleado = connection.prepareStatement(sqlEmpleado, PreparedStatement.RETURN_GENERATED_KEYS);
	         PreparedStatement statementVendedor = connection.prepareStatement(sqlVendedor);
	         PreparedStatement statementEmpleadoHotel = connection.prepareStatement(sqlEmpleadoHotel)) {

	        connection.setAutoCommit(false);

	        // Insertar el empleado en la tabla empleado
	        statementEmpleado.setString(1, nombre);
	        statementEmpleado.executeUpdate();

	        // Obtener el ID generado del empleado
	        ResultSet generatedKeys = statementEmpleado.getGeneratedKeys();
	        int empleadoId = -1;
	        if (generatedKeys.next()) {
	            empleadoId = generatedKeys.getInt(1);
	        }

	        if (empleadoId == -1) {
	            throw new SQLException("No se pudo obtener el ID del empleado generado.");
	        }

	        // Insertar el vendedor en la tabla vendedor
	        statementVendedor.setInt(1, empleadoId);
	        statementVendedor.executeUpdate();

	        // Asociar el empleado con el hotel en la tabla empleado_hotel
	        statementEmpleadoHotel.setInt(1, empleadoId);
	        statementEmpleadoHotel.setInt(2, hotelId);
	        statementEmpleadoHotel.executeUpdate();

	        connection.commit();
	    } catch (SQLException e) {
	        e.printStackTrace();
	       
	        
	    }
	}




	private double obtenerSalarioFijo(String tipoEmpleado) {
		switch (tipoEmpleado) {
		case "Recamarera Auxiliar":
		case "Recamarera Principiante":
		case "Recamarera Experimentada":
		case "Ama de Llaves":
			return 1500.00 * 4; // Salario semanal multiplicado por 4 para obtener el mensual
		case "Vendedor":
			return 4500.00 * 2; // Salario quincenal multiplicado por 2 para obtener el mensual
		case "Gerente":
			return 18000.00; // Salario mensual fijo
		default:
			throw new IllegalArgumentException("Tipo de empleado desconocido: " + tipoEmpleado);
		}
	}

	public List<String> listarEmpleadosPorHotelId(int hotelId) {
		List<String> empleados = new ArrayList<>();
		String sql = "SELECT h.nombre AS hotel_nombre, h.hotel_id, e.empleado_id, e.nombre, e.tipo "
				+ "FROM empleado e " + "JOIN empleado_hotel eh ON e.empleado_id = eh.empleado_id "
				+ "JOIN hotel h ON eh.hotel_id = h.hotel_id " + "WHERE eh.hotel_id = ?";

		try (Connection connection = conexionDataBase.getConnection();
				PreparedStatement pstmt = connection.prepareStatement(sql)) {
			pstmt.setInt(1, hotelId);
			ResultSet rs = pstmt.executeQuery();

			boolean hotelInfoAdded = false;
			while (rs.next()) {
				if (!hotelInfoAdded) {
					String hotelNombre = rs.getString("hotel_nombre");
					int hotelID = rs.getInt("hotel_id");
					empleados.add(String.format("Hotel: %s - ID: %d", hotelNombre, hotelID));
					hotelInfoAdded = true;
					System.out.println("Hotel: " + hotelNombre + " - ID: " + hotelID); // Depuración
				}

				int empleadoId = rs.getInt("empleado_id");
				String empleadoNombre = rs.getString("nombre");
				String puesto = rs.getString("tipo");

				String empleadoInfo = String.format("ID: %d, Nombre: %s, Puesto: %s", empleadoId, empleadoNombre,
						puesto);
				empleados.add(empleadoInfo);
				System.out.println("Empleado: " + empleadoInfo); // Depuración
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return empleados;
	}

	public ArrayList<RecamareraBean> listarRecamareras() {
		ArrayList<RecamareraBean> recamareras = new ArrayList<>();
		String sql = "SELECT * FROM empleado WHERE tipo IN ('RecamareraAuxiliar', 'RecamareraPrincipiante', 'RecamareraExperimentada', 'AmaDeLlaves')";

		try (Connection connection = conexionDataBase.getConnection();
				PreparedStatement statement = connection.prepareStatement(sql);
				ResultSet resultSet = statement.executeQuery()) {

			while (resultSet.next()) {
				int empleadoId = resultSet.getInt("empleado_id");
				String nombre = resultSet.getString("nombre");
				double salario = resultSet.getDouble("salario");
				String tipo = resultSet.getString("tipo");

				RecamareraBean recamarera = (RecamareraBean) crearEmpleado(empleadoId, nombre, salario, tipo);
				recamareras.add(recamarera);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return recamareras;
	}

	public double calcularSalarioDeRecamarera(String nombre) throws SQLException {
		String sql = "SELECT salario FROM empleado WHERE nombre = ? AND tipo IN ('RecamareraAuxiliar', 'RecamareraPrincipiante', 'RecamareraExperimentada', 'AmaDeLlaves')";

		try (Connection connection = conexionDataBase.getConnection();
				PreparedStatement statement = connection.prepareStatement(sql)) {

			statement.setString(1, nombre);
			try (ResultSet resultSet = statement.executeQuery()) {
				if (resultSet.next()) {
					return resultSet.getDouble("salario");
				} else {
					throw new IllegalArgumentException("Recamarera no encontrada: " + nombre);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
	}

	public boolean modificarEmpleado(int hotelId, int empleadoId, String nombre, String nuevoTipoEmpleado) {
		double salario = this.obtenerSalarioFijo(nuevoTipoEmpleado);
		String verificarEmpleado = "SELECT * FROM empleado_hotel WHERE hotel_id = ? AND empleado_id = ?";
		String actualizarEmpleado = "UPDATE empleado SET nombre = ?, salario = ?, tipo = ? WHERE empleado_id = ?";

		try (Connection connection = conexionDataBase.getConnection();
				PreparedStatement stmtVerificar = connection.prepareStatement(verificarEmpleado);
				PreparedStatement stmtActualizar = connection.prepareStatement(actualizarEmpleado)) {

			stmtVerificar.setInt(1, hotelId);
			stmtVerificar.setInt(2, empleadoId);
			ResultSet rs = stmtVerificar.executeQuery();

			if (rs.next()) { // Actualizar los datos del empleado
				stmtActualizar.setString(1, nombre);
				stmtActualizar.setDouble(2, salario);
				stmtActualizar.setString(3, nuevoTipoEmpleado);
				stmtActualizar.setInt(4, empleadoId);
				stmtActualizar.executeUpdate();
				return true;
			} else {
				System.err.println("El empleado no pertenece al hotel especificado.");
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean eliminarEmpleado(int empleadoId) {
		String sql = "DELETE FROM empleado WHERE empleado_id = ?";

		try (Connection connection = conexionDataBase.getConnection();
				PreparedStatement statement = connection.prepareStatement(sql)) {

			statement.setInt(1, empleadoId);
			statement.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	private EmpleadoBean crearEmpleado(int empleadoId, String nombre, double salario, String tipo) {
		EmpleadoBean empleado;
		switch (tipo) {
		case "RecamareraAuxiliar":
			empleado = new RecamareraAuxiliar(nombre);
			break;
		case "RecamareraPrincipiante":
			empleado = new RecamareraPrincipianteBean(nombre);
			break;
		case "RecamareraExperimentada":
			empleado = new RecamareraExperimentadaBean(nombre);
			break;
		case "AmaDeLlaves":
			empleado = new AmaDeLlavesBean(nombre);
			break;
		case "Gerente":
			double bono = obtenerBonoGerente(empleadoId); // Método para obtener el bono del gerente
			empleado = new GerenteBean(nombre, bono);
			break;
		default:
			empleado = new EmpleadoBean(nombre, salario) {
				@Override
				public double calcularSalario() {
					return getSalarioBase();
				}
			};
			break;
		}
		empleado.setEmpleadoId(empleadoId);
		return empleado;
	}

	private double obtenerBonoGerente(int empleadoId) {
		String sql = "SELECT bono FROM gerente WHERE empleado_id = ?";

		try (Connection connection = conexionDataBase.getConnection();
				PreparedStatement statement = connection.prepareStatement(sql)) {

			statement.setInt(1, empleadoId);
			try (ResultSet resultSet = statement.executeQuery()) {
				if (resultSet.next()) {
					return resultSet.getDouble("bono");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
}
