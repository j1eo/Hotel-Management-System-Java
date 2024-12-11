package services;

import beans.AmaDeLlavesBean;
import beans.ComisionRecamareraBean;
import beans.HabitacionBean;
import beans.HotelBean;
import beans.RecamareraAuxiliarBean;
import beans.RecamareraBean;
import beans.RecamareraExperimentadaBean;
import beans.RecamareraPrincipianteBean;
import dataBase.ConexionDataBase;
import services.HotelService;
import views.RecamareraViews;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class RecamareraService {
	
	private final ConexionDataBase conexionDataBase;
	private final HotelService hotelService;
	private final ComisionService comisionService;
	
	public RecamareraService(ConexionDataBase conexionDataBase, HotelService hotelService, ComisionService comisionService) {
		this.conexionDataBase = conexionDataBase;
		this.hotelService = hotelService;
		this.comisionService = comisionService;
	}
	
	public List<String> listarRecamarerasPorHotelId(int hotelId) throws SQLException {
	    List<String> recamareras = new ArrayList<>();
	    String sql = "SELECT h.nombre AS hotel_nombre, h.hotel_id, e.empleado_id, e.nombre, r.recamarera_id, r.nivel_experiencia, ha.habitacion_id, ha.tipo " +
	                 "FROM empleado e " +
	                 "JOIN empleado_hotel eh ON e.empleado_id = eh.empleado_id " +
	                 "JOIN hotel h ON eh.hotel_id = h.hotel_id " +
	                 "JOIN recamarera r ON e.empleado_id = r.empleado_id " +
	                 "LEFT JOIN habitacion ha ON r.empleado_id = ha.recamarera_id " + // Corrección en la JOIN condition
	                 "WHERE eh.hotel_id = ?";

	    try (Connection connection = conexionDataBase.getConnection();
	         PreparedStatement pstmt = connection.prepareStatement(sql)) {
	        pstmt.setInt(1, hotelId);
	        ResultSet rs = pstmt.executeQuery();

	        boolean hotelInfoAdded = false;
	        while (rs.next()) {
	            if (!hotelInfoAdded) {
	                String hotelNombre = rs.getString("hotel_nombre");
	                int hotelID = rs.getInt("hotel_id");
	                recamareras.add(String.format("Hotel: %s - ID: %d", hotelNombre, hotelID));
	                hotelInfoAdded = true;
	                System.out.println("Hotel: " + hotelNombre + " - ID: " + hotelID); // Depuración
	            }

	            int empleadoId = rs.getInt("empleado_id");
	            String empleadoNombre = rs.getString("nombre");
	            int recamareraId = rs.getInt("recamarera_id");
	            String nivelExperiencia = rs.getString("nivel_experiencia");
	            String habitacionId = rs.getString("habitacion_id");
	            String tipoHabitacion = rs.getString("tipo");

	            String recamareraInfo = String.format("Empleado ID: %d, Recamarera ID: %d, Nombre: %s, Nivel de Experiencia: %s, Habitacion ID: %s, Tipo Habitación: %s",
	                    empleadoId, recamareraId, empleadoNombre, nivelExperiencia, habitacionId != null ? habitacionId : "N/A", tipoHabitacion != null ? tipoHabitacion : "N/A");
	            recamareras.add(recamareraInfo);
	            System.out.println("Recamarera: " + recamareraInfo); // Depuración
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return recamareras;
	}


	  
	public List<HabitacionBean> listarHabitacionesSinRecamareraAsignada(int hotelId) throws SQLException {
	    List<HabitacionBean> habitaciones = new ArrayList<>();
	    String sql = "SELECT * FROM habitacion WHERE hotel_id = ? AND recamarera_id IS NULL";

	    try (Connection connection = conexionDataBase.getConnection();
	         PreparedStatement statement = connection.prepareStatement(sql)) {
	        statement.setInt(1, hotelId);

	        try (ResultSet resultSet = statement.executeQuery()) {
	            while (resultSet.next()) {
	                int habitacionId = resultSet.getInt("habitacion_id");
	                int hotelIdResult = resultSet.getInt("hotel_id");
	                String id = resultSet.getString("id");
	                String tipo = resultSet.getString("tipo");
	                boolean disponible = resultSet.getBoolean("disponible");
	                int numeroPersonas = resultSet.getInt("numero_personas");
	                double costo = resultSet.getDouble("costo"); // Obtener el costo

	                HabitacionBean habitacion = new HabitacionBean(
	                    habitacionId, hotelIdResult, id, tipo, disponible, numeroPersonas, null, costo // recamareraId es null y costo agregado
	                );
	                habitaciones.add(habitacion);
	            }
	        }
	    }

	    return habitaciones;
	}

	  
	  public List<RecamareraBean> listarRecamarerasPorTipoHabitacion(int hotelId, String tipoHabitacion) throws SQLException {
		    List<RecamareraBean> recamareras = new ArrayList<>();
		    String sql = "SELECT r.recamarera_id, e.empleado_id, e.nombre, e.salario, r.nivel_experiencia " +
		                 "FROM recamarera r " +
		                 "JOIN empleado e ON r.empleado_id = e.empleado_id " +
		                 "JOIN empleado_hotel eh ON e.empleado_id = eh.empleado_id " +
		                 "WHERE eh.hotel_id = ?";

		    try (Connection connection = conexionDataBase.getConnection();
		         PreparedStatement pstmt = connection.prepareStatement(sql)) {
		        pstmt.setInt(1, hotelId);
		        ResultSet rs = pstmt.executeQuery();

		        while (rs.next()) {
		            RecamareraBean recamarera = crearRecamareraConcreta(rs);
		            if (puedeAtenderTipoHabitacion(recamarera.getNivelExperiencia(), tipoHabitacion)) {
		                recamareras.add(recamarera);
		            }
		        }
		    }

		    return recamareras;
		}

	  private RecamareraBean crearRecamareraConcreta(ResultSet rs) throws SQLException {
		    String nivelExperiencia = rs.getString("nivel_experiencia").toLowerCase();
		    RecamareraBean recamarera;

		    switch (nivelExperiencia) {
		        case "recamarera auxiliar":
		            recamarera = new RecamareraAuxiliarBean();
		            break;
		        case "recamarera principiante":
		            recamarera = new RecamareraPrincipianteBean();
		            break;
		        case "recamarera experimentada":
		            recamarera = new RecamareraExperimentadaBean();
		            break;
		        case "ama de llaves":
		            recamarera = new AmaDeLlavesBean();
		            break;
		        default:
		            throw new IllegalArgumentException("Nivel de experiencia desconocido: " + nivelExperiencia);
		    }

		    recamarera.setRecamareraId(rs.getInt("recamarera_id"));
		    recamarera.setEmpleadoId(rs.getInt("empleado_id"));
		    recamarera.setNombre(rs.getString("nombre"));
		    recamarera.setSalarioBase(rs.getDouble("salario"));
		    recamarera.setNivelExperiencia(nivelExperiencia);

		    return recamarera;
		}

	  
	  private boolean puedeAtenderTipoHabitacion(String nivelExperiencia, String tipoHabitacion) {
		    switch (nivelExperiencia.toLowerCase()) {
		        case "recamarera auxiliar":
		            return tipoHabitacion.equalsIgnoreCase("sencilla");
		        case "recamarera principiante":
		            return tipoHabitacion.equalsIgnoreCase("sencilla") || tipoHabitacion.equalsIgnoreCase("doble");
		        case "recamarera experimentada":
		            return tipoHabitacion.equalsIgnoreCase("sencilla") || tipoHabitacion.equalsIgnoreCase("doble") || tipoHabitacion.equalsIgnoreCase("penhouse");
		        case "ama de llaves":
		            return true; // Puede atender cualquier tipo de habitación
		        default:
		            return false;
		    }
		}



	  public void asignarHabitacion(RecamareraBean recamarera, HabitacionBean habitacion) throws SQLException {
		    if (!recamareraTrabajaEnHotel(recamarera.getEmpleadoId(), habitacion.getHotelId())) {
		        throw new IllegalArgumentException("La recamarera no trabaja en este hotel.");
		    }

		    if (!recamarera.puedeAtender(habitacion.getTipo())) {
		        throw new IllegalArgumentException("La recamarera no puede atender este tipo de habitación.");
		    }

		    if (habitacionEstaAsignada(habitacion.getHabitacionId())) {
		        throw new IllegalArgumentException("La habitación ya está asignada a otra recamarera hoy.");
		    }

		    String sqlAsignarHabitacion = "UPDATE habitacion SET recamarera_id = ? WHERE habitacion_id = ?";
		    String sqlRegistrarAsignacion = "INSERT INTO AsignacionHabitacion (hotel_id, habitacion_id, recamarera_id, nombre_recamarera, fecha) VALUES (?, ?, ?, ?, CURDATE())";

		    try (Connection connection = conexionDataBase.getConnection();
		         PreparedStatement stmtAsignarHabitacion = connection.prepareStatement(sqlAsignarHabitacion);
		         PreparedStatement stmtRegistrarAsignacion = connection.prepareStatement(sqlRegistrarAsignacion)) {

		        connection.setAutoCommit(false);

		        // Asignar la habitación
		        stmtAsignarHabitacion.setInt(1, recamarera.getEmpleadoId());
		        stmtAsignarHabitacion.setInt(2, habitacion.getHabitacionId());
		        stmtAsignarHabitacion.executeUpdate();

		        // Registrar la asignación
		        stmtRegistrarAsignacion.setInt(1, habitacion.getHotelId());
		        stmtRegistrarAsignacion.setInt(2, habitacion.getHabitacionId());
		        stmtRegistrarAsignacion.setInt(3, recamarera.getRecamareraId());
		        stmtRegistrarAsignacion.setString(4, recamarera.getNombre());
		        stmtRegistrarAsignacion.executeUpdate();

		        connection.commit();
		    } catch (SQLException e) {
		        e.printStackTrace();
		        throw e;
		    }

		    // Registrar la comisión después de la transacción principal
		    int nivelEstrellas = hotelService.obtenerNivelEstrellas(habitacion.getHotelId());
		    double comisionMonto = comisionService.calcularComisionRecamarera(habitacion.getTipo(), nivelEstrellas);
		    ComisionRecamareraBean comision = new ComisionRecamareraBean(
		            0,
		            recamarera.getEmpleadoId(),
		            habitacion.getHotelId(),
		            comisionMonto,
		            new Date(Calendar.getInstance().getTimeInMillis()),
		            habitacion.getTipo(),
		            1,
		            habitacion.getHabitacionId()
		    );
		    comisionService.registrarComisionRecamarera(comision);
		}


		private boolean recamareraIdExiste(int recamareraId) throws SQLException {
		    String sql = "SELECT 1 FROM recamarera WHERE recamarera_id = ?";
		    try (Connection connection = conexionDataBase.getConnection();
		         PreparedStatement stmt = connection.prepareStatement(sql)) {
		        stmt.setInt(1, recamareraId);
		        try (ResultSet rs = stmt.executeQuery()) {
		            return rs.next();
		        }
		    }
		}

	  
	  public void verificarYActualizarAsignacionesExpiradas() throws SQLException {
		    String sqlSelect = "SELECT habitacion_id FROM AsignacionHabitacion WHERE fecha < CURDATE()";
		    String sqlUpdateHabitacion = "UPDATE habitacion SET recamarera_id = NULL WHERE habitacion_id = ?";

		    try (Connection connection = conexionDataBase.getConnection();
		         PreparedStatement selectStmt = connection.prepareStatement(sqlSelect);
		         PreparedStatement updateHabitacionStmt = connection.prepareStatement(sqlUpdateHabitacion)) {

		        ResultSet rs = selectStmt.executeQuery();
		        while (rs.next()) {
		            int habitacionId = rs.getInt("habitacion_id");

		            // Actualizar la habitación removiendo la recamarera
		            updateHabitacionStmt.setInt(1, habitacionId);
		            updateHabitacionStmt.executeUpdate();
		        }
		    }
		}




	  private boolean habitacionEstaAsignada(int habitacionId) throws SQLException {
		    String sql = "SELECT 1 FROM AsignacionHabitacion WHERE habitacion_id = ? AND fecha = CURDATE()";
		    try (Connection connection = conexionDataBase.getConnection();
		         PreparedStatement stmt = connection.prepareStatement(sql)) {
		        stmt.setInt(1, habitacionId);
		        try (ResultSet rs = stmt.executeQuery()) {
		            return rs.next();
		        }
		    }
		}


		private boolean recamareraTrabajaEnHotel(int recamareraId, int hotelId) throws SQLException {
		    String sql = "SELECT 1 FROM empleado_hotel WHERE empleado_id = ? AND hotel_id = ?";
		    try (Connection connection = conexionDataBase.getConnection();
		         PreparedStatement stmt = connection.prepareStatement(sql)) {
		        stmt.setInt(1, recamareraId);
		        stmt.setInt(2, hotelId);
		        try (ResultSet rs = stmt.executeQuery()) {
		            return rs.next();
		        }
		    }
		}

    // Calcula el salario total de una recamarera
    public double calcularSalario(RecamareraBean recamarera, ArrayList<HabitacionBean> habitaciones, int estrellasHotel) {
        double salarioBase = recamarera.getSalarioBase();
        double totalComision = 0.0;

        for (HabitacionBean habitacion : habitaciones) {
            totalComision += calcularComision(habitacion, estrellasHotel);
        }

        return salarioBase + totalComision;
    }

    // Método auxiliar para calcular la comisión por habitación
    private double calcularComision(HabitacionBean habitacion, int estrellasHotel) {
        switch (habitacion.getTipo().toLowerCase()) {
            case "sencilla":
                return estrellasHotel == 3 ? 30 : estrellasHotel == 4 ? 50 : 70;
            case "doble":
                return estrellasHotel == 3 ? 40 : estrellasHotel == 4 ? 60 : 80;
            case "penhouse":
                return estrellasHotel == 3 ? 60 : estrellasHotel == 4 ? 80 : 100;
            default:
                throw new IllegalArgumentException("Tipo de habitación inválido.");
        }
    }

    // Obtiene la lista de habitaciones asignadas a una recamarera
    public ArrayList<HabitacionBean> listarHabitacionesAsignadas(RecamareraBean recamarera) {
        return recamarera.getHabitacionesAsignadas();
    }
    
    public HabitacionBean buscarHabitacionAsignadaPorID(RecamareraBean recamarera, String habitacionID) {
        for (HabitacionBean habitacion : recamarera.getHabitacionesAsignadas()) {
            if (habitacion.getId().equalsIgnoreCase(habitacionID)) {
                return habitacion;
            }
        }
        throw new IllegalArgumentException("Habitación con ID " + habitacionID + " no asignada a la recamarera.");
    }

}
