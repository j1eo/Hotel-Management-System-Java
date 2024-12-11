package services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.Date;
import dataBase.ConexionDataBase;
import beans.BonoGerenteBean;
import beans.ComisionRecamareraBean;
import beans.ComisionVendedorBean;
import beans.HabitacionBean;
import beans.RecamareraBean;

public class ComisionService {
    private ConexionDataBase conexionDataBase;

    public ComisionService(ConexionDataBase conexionDataBase) {
        this.conexionDataBase = conexionDataBase;
    }

    public void registrarComisionVendedor(ComisionVendedorBean comision) throws SQLException {
        String sql = "INSERT INTO comisionvendedor (empleado_id, hotel_id, tipo_habitacion, numero_personas, fecha, comision) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection connection = conexionDataBase.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            // Mensajes de depuración
            System.out.println("Intentando registrar comisión del vendedor...");
            System.out.println("Empleado ID: " + comision.getEmpleadoId());
            System.out.println("Hotel ID: " + comision.getHotelId());
            System.out.println("Tipo Habitación: " + comision.getTipoHabitacion());
            System.out.println("Número de Personas: " + comision.getNumeroPersonas());
            System.out.println("Fecha: " + comision.getFecha());
            System.out.println("Comisión: " + comision.getMonto());

            // Establecer parámetros del PreparedStatement
            statement.setInt(1, comision.getEmpleadoId());
            statement.setInt(2, comision.getHotelId());
            statement.setString(3, comision.getTipoHabitacion());
            statement.setInt(4, comision.getNumeroPersonas());
            statement.setDate(5, new Date(comision.getFecha().getTime()));
            statement.setDouble(6, comision.getMonto());

            // Ejecución de la actualización y verificación de filas insertadas
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Comisión registrada exitosamente.");
            } else {
                System.err.println("No se pudo registrar la comisión.");
            }
        } catch (SQLException e) {
            System.err.println("Error SQL al registrar la comisión: " + e.getMessage());
            e.printStackTrace();
            throw e;
        } catch (Exception e) {
            System.err.println("Error inesperado al registrar la comisión: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }



    public double calcularComisionVendedor(String tipoHabitacion, int nivelEstrellas, int numeroPersonas) {
        double comision = 0.0;

        switch (nivelEstrellas) {
            case 3:
                switch (tipoHabitacion) {
                    case "sencilla":
                        comision = 30.0 + 10.0 * numeroPersonas;
                        break;
                    case "doble":
                        comision = 40.0 + 15.0 * numeroPersonas;
                        break;
                    case "penthouse":
                        comision = 60.0 + 20.0 * numeroPersonas;
                        break;
                }
                break;
            case 4:
                switch (tipoHabitacion) {
                    case "sencilla":
                        comision = 50.0 + 25.0 * numeroPersonas;
                        break;
                    case "doble":
                        comision = 60.0 + 30.0 * numeroPersonas;
                        break;
                    case "penthouse":
                        comision = 80.0 + 35.0 * numeroPersonas;
                        break;
                }
                break;
            case 5:
                switch (tipoHabitacion) {
                    case "sencilla":
                        comision = 70.0 + 40.0 * numeroPersonas;
                        break;
                    case "doble":
                        comision = 80.0 + 45.0 * numeroPersonas;
                        break;
                    case "penthouse":
                        comision = 100.0 + 50.0 * numeroPersonas;
                        break;
                }
                break;
        }

        return comision;
    }
    
    public void registrarComisionRecamarera(ComisionRecamareraBean comision) throws SQLException {
        String sql = "INSERT INTO ComisionRecamarera (empleado_id, hotel_id, tipo_habitacion, cantidad_habitaciones, fecha, comision) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection connection = conexionDataBase.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            // Mensajes de depuración
            System.out.println("Intentando registrar comisión de la recamarera...");
            System.out.println("Empleado ID: " + comision.getEmpleadoId());
            System.out.println("Hotel ID: " + comision.getHotelId());
            System.out.println("Tipo Habitación: " + comision.getTipoHabitacion());
            System.out.println("Cantidad de Habitaciones: " + comision.getCantidadHabitaciones());
            System.out.println("Fecha: " + comision.getFecha());
            System.out.println("Comisión: " + comision.getMonto());

            // Establecer parámetros del PreparedStatement
            statement.setInt(1, comision.getEmpleadoId());
            statement.setInt(2, comision.getHotelId());
            statement.setString(3, comision.getTipoHabitacion());
            statement.setInt(4, comision.getCantidadHabitaciones());
            statement.setDate(5, new java.sql.Date(comision.getFecha().getTime()));
            statement.setDouble(6, comision.getMonto());

            // Ejecución de la actualización y verificación de filas insertadas
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Comisión registrada exitosamente.");
            } else {
                System.err.println("No se pudo registrar la comisión.");
            }
        } catch (SQLException e) {
            System.err.println("Error SQL al registrar la comisión: " + e.getMessage());
            e.printStackTrace();
            throw e;
        } catch (Exception e) {
            System.err.println("Error inesperado al registrar la comisión: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }



    public double calcularComisionRecamarera(String tipoHabitacion, int nivelEstrellas) {
        double comision = 0.0;

        switch (nivelEstrellas) {
            case 3:
                switch (tipoHabitacion) {
                    case "sencilla":
                        comision = 30.0;
                        break;
                    case "doble":
                        comision = 40.0;
                        break;
                    case "penthouse":
                        comision = 60.0;
                        break;
                }
                break;
            case 4:
                switch (tipoHabitacion) {
                    case "sencilla":
                        comision = 50.0;
                        break;
                    case "doble":
                        comision = 60.0;
                        break;
                    case "penthouse":
                        comision = 80.0;
                        break;
                }
                break;
            case 5:
                switch (tipoHabitacion) {
                    case "sencilla":
                        comision = 70.0;
                        break;
                    case "doble":
                        comision = 80.0;
                        break;
                    case "penthouse":
                        comision = 100.0;
                        break;
                }
                break;
            default:
                System.err.println("Nivel de estrellas no válido.");
                break;
        }

        return comision;
    }
    public List<ComisionRecamareraBean> obtenerComisionesRecamarerasPorMes(int hotelId, String mes) throws SQLException {
        String sql = "SELECT * FROM ComisionRecamarera WHERE hotel_id = ? AND DATE_FORMAT(fecha, '%Y-%m') = ?";
        List<ComisionRecamareraBean> comisiones = new ArrayList<>();

        try (Connection connection = conexionDataBase.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, hotelId);
            statement.setString(2, mes);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int comisionId = resultSet.getInt("comision_id");
                    int empleadoId = resultSet.getInt("empleado_id");
                    String tipoHabitacion = resultSet.getString("tipo_habitacion");
                    int cantidadHabitaciones = resultSet.getInt("cantidad_habitaciones");
                    double monto = resultSet.getDouble("comision");
                    Date fecha = resultSet.getDate("fecha");
                    ComisionRecamareraBean comision = new ComisionRecamareraBean(comisionId, empleadoId, hotelId, monto, fecha, tipoHabitacion, cantidadHabitaciones, 0);
                    comisiones.add(comision);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }

        return comisiones;
    }

    public double sumarComisionesRecamarerasPorMes(int hotelId, String mes) throws SQLException {
        List<ComisionRecamareraBean> comisiones = obtenerComisionesRecamarerasPorMes(hotelId, mes);
        double sumaTotal = 0.0;
        for (ComisionRecamareraBean comision : comisiones) {
            sumaTotal += comision.getMonto();
        }
        return sumaTotal;
    }

    public List<String> obtenerNombresRecamarerasPorHotel(int hotelId) throws SQLException {
        String sql = "SELECT e.nombre " +
                     "FROM empleado e " +
                     "JOIN empleado_hotel eh ON e.empleado_id = eh.empleado_id " +
                     "JOIN recamarera r ON e.empleado_id = r.empleado_id " +
                     "WHERE eh.hotel_id = ?";
        List<String> nombresRecamareras = new ArrayList<>();

        try (Connection connection = conexionDataBase.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, hotelId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    nombresRecamareras.add(resultSet.getString("nombre"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return nombresRecamareras;
    }

    public List<String> obtenerNivelesExperienciaRecamarerasPorHotel(int hotelId) throws SQLException {
        String sql = "SELECT r.nivel_experiencia " +
                     "FROM recamarera r " +
                     "JOIN empleado_hotel eh ON r.empleado_id = eh.empleado_id " +
                     "WHERE eh.hotel_id = ?";
        List<String> nivelesExperiencia = new ArrayList<>();

        try (Connection connection = conexionDataBase.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, hotelId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    nivelesExperiencia.add(resultSet.getString("nivel_experiencia"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return nivelesExperiencia;
    }

    public int obtenerIdEmpleadoPorNombre(String nombre) throws SQLException {
        String sql = "SELECT empleado_id FROM empleado WHERE nombre = ?";
        try (Connection connection = conexionDataBase.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, nombre);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("empleado_id");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return 0; // Si no se encuentra el empleado
    }
    
    
    public List<ComisionVendedorBean> obtenerComisionesVendedoresPorMes(int hotelId, String mes) throws SQLException {
        String sql = "SELECT empleado_id, hotel_id, SUM(comision) AS total_comision, MIN(fecha) AS fecha, 'Vendedor' AS tipo_habitacion, SUM(numero_personas) AS total_personas " +
                     "FROM comisionvendedor " +
                     "WHERE hotel_id = ? AND DATE_FORMAT(fecha, '%Y-%m') = ? " +
                     "GROUP BY empleado_id, hotel_id";
        List<ComisionVendedorBean> comisiones = new ArrayList<>();

        try (Connection connection = conexionDataBase.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, hotelId);
            statement.setString(2, mes);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int empleadoId = resultSet.getInt("empleado_id");
                    int hotelIdFromDB = resultSet.getInt("hotel_id");
                    double totalComision = resultSet.getDouble("total_comision");
                    Date fecha = resultSet.getDate("fecha");
                    String tipoHabitacion = resultSet.getString("tipo_habitacion");
                    int totalPersonas = resultSet.getInt("total_personas");
                    ComisionVendedorBean comision = new ComisionVendedorBean(0, empleadoId, hotelIdFromDB, totalComision, fecha, tipoHabitacion, totalPersonas);
                    comisiones.add(comision);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }

        return comisiones;
    }

    public double sumarComisionesVendedoresPorMes(int hotelId, String mes) throws SQLException {
        List<ComisionVendedorBean> comisiones = obtenerComisionesVendedoresPorMes(hotelId, mes);
        double sumaTotal = 0.0;
        for (ComisionVendedorBean comision : comisiones) {
            sumaTotal += comision.getMonto();
        }
        return sumaTotal;
    }

    public List<String> obtenerNombresVendedoresPorHotel(int hotelId) throws SQLException {
        String sql = "SELECT e.nombre " +
                     "FROM empleado e " +
                     "JOIN empleado_hotel eh ON e.empleado_id = eh.empleado_id " +
                     "JOIN vendedor v ON e.empleado_id = v.empleado_id " +
                     "WHERE eh.hotel_id = ?";
        List<String> nombresVendedores = new ArrayList<>();

        try (Connection connection = conexionDataBase.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, hotelId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    nombresVendedores.add(resultSet.getString("nombre"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return nombresVendedores;
    }
    
    public double sumarComisionesPorDia(int hotelId, Date dia) throws SQLException {
        String sqlRecamareras = "SELECT SUM(comision) AS total_comision FROM ComisionRecamarera WHERE hotel_id = ? AND fecha = ?";
        String sqlVendedores = "SELECT SUM(comision) AS total_comision FROM ComisionVendedor WHERE hotel_id = ? AND fecha = ?";
        double totalComision = 0.0;

        try (Connection connection = conexionDataBase.getConnection()) {
            try (PreparedStatement statementRecamareras = connection.prepareStatement(sqlRecamareras);
                 PreparedStatement statementVendedores = connection.prepareStatement(sqlVendedores)) {

                statementRecamareras.setInt(1, hotelId);
                statementRecamareras.setDate(2, dia);
                try (ResultSet resultSet = statementRecamareras.executeQuery()) {
                    if (resultSet.next()) {
                        totalComision += resultSet.getDouble("total_comision");
                    }
                }

                statementVendedores.setInt(1, hotelId);
                statementVendedores.setDate(2, dia);
                try (ResultSet resultSet = statementVendedores.executeQuery()) {
                    if (resultSet.next()) {
                        totalComision += resultSet.getDouble("total_comision");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }

        return totalComision;
    }
    
    public double sumarComisionesPorMes(int hotelId, String mes) throws SQLException {
        String sqlRecamareras = "SELECT SUM(comision) AS total_comision FROM ComisionRecamarera WHERE hotel_id = ? AND DATE_FORMAT(fecha, '%Y-%m') = ?";
        String sqlVendedores = "SELECT SUM(comision) AS total_comision FROM ComisionVendedor WHERE hotel_id = ? AND DATE_FORMAT(fecha, '%Y-%m') = ?";
        double totalComision = 0.0;

        try (Connection connection = conexionDataBase.getConnection()) {
            try (PreparedStatement statementRecamareras = connection.prepareStatement(sqlRecamareras);
                 PreparedStatement statementVendedores = connection.prepareStatement(sqlVendedores)) {

                statementRecamareras.setInt(1, hotelId);
                statementRecamareras.setString(2, mes);
                try (ResultSet resultSet = statementRecamareras.executeQuery()) {
                    if (resultSet.next()) {
                        totalComision += resultSet.getDouble("total_comision");
                    }
                }

                statementVendedores.setInt(1, hotelId);
                statementVendedores.setString(2, mes);
                try (ResultSet resultSet = statementVendedores.executeQuery()) {
                    if (resultSet.next()) {
                        totalComision += resultSet.getDouble("total_comision");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }

        return totalComision;
    }

    
}
