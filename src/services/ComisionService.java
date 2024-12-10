package services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Date;
import dataBase.ConexionDataBase;
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
    
    
    
}
