package services;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import dataBase.ConexionDataBase;
import beans.ReservacionBean;

public class ReservacionService {
    private ConexionDataBase conexionDataBase;

    public ReservacionService(ConexionDataBase conexionDataBase) {
        this.conexionDataBase = conexionDataBase;
    }

    public void agregarReservacion(ReservacionBean reservacion) throws SQLException {
        String sqlCheckVendedor = "SELECT 1 FROM vendedor WHERE empleado_id = ?"; // Verifica el empleado_id en vendedor
        String sql = "INSERT INTO reservacion (vendedor_id, hotel_id, habitacion_id, numero_personas, fecha_registro, duracion_estadia, costo) VALUES (?, ?, ?, ?, ?, ?, ?)";
        String sqlUpdateHabitacion = "UPDATE habitacion SET disponible = ?, numero_personas = ? WHERE habitacion_id = ?";

        try (Connection connection = conexionDataBase.getConnection();
             PreparedStatement checkVendedorStmt = connection.prepareStatement(sqlCheckVendedor);
             PreparedStatement statement = connection.prepareStatement(sql);
             PreparedStatement updateHabitacionStatement = connection.prepareStatement(sqlUpdateHabitacion)) {

            connection.setAutoCommit(false); // Comenzar transacción

            // Verificar si el vendedor existe
            checkVendedorStmt.setInt(1, reservacion.getVendedorId());
            ResultSet rs = checkVendedorStmt.executeQuery();
            if (!rs.next()) {
                throw new SQLException("El vendedor no existe.");
            }

            // Agregar la reservación
            statement.setInt(1, reservacion.getVendedorId());
            statement.setInt(2, reservacion.getHotelId());
            statement.setInt(3, reservacion.getHabitacionId());
            statement.setInt(4, reservacion.getNumeroPersonas());
            statement.setDate(5, reservacion.getFechaRegistro());
            statement.setInt(6, reservacion.getDuracionEstadia());
            statement.setDouble(7, reservacion.getCosto()); // Asegurarse de que el costo se está incluyendo

            statement.executeUpdate();

            // Actualizar el estado de la habitación
            updateHabitacionStatement.setBoolean(1, false); // La habitación ahora está ocupada
            updateHabitacionStatement.setInt(2, reservacion.getNumeroPersonas());
            updateHabitacionStatement.setInt(3, reservacion.getHabitacionId());

            updateHabitacionStatement.executeUpdate();

            connection.commit(); // Finalizar transacción
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public void verificarYActualizarReservacionesExpiradas() throws SQLException {
        String sqlSelect = "SELECT reservacion_id, habitacion_id FROM reservacion WHERE DATE_ADD(fecha_registro, INTERVAL duracion_estadia DAY) < CURDATE()";
        String sqlDelete = "DELETE FROM reservacion WHERE reservacion_id = ?";
        String sqlUpdateHabitacion = "UPDATE habitacion SET disponible = ?, numero_personas = 0 WHERE habitacion_id = ?";

        try (Connection connection = conexionDataBase.getConnection();
             PreparedStatement selectStmt = connection.prepareStatement(sqlSelect);
             PreparedStatement deleteStmt = connection.prepareStatement(sqlDelete);
             PreparedStatement updateHabitacionStmt = connection.prepareStatement(sqlUpdateHabitacion)) {

            connection.setAutoCommit(false);

            ResultSet rs = selectStmt.executeQuery();
            while (rs.next()) {
                int reservacionId = rs.getInt("reservacion_id");
                int habitacionId = rs.getInt("habitacion_id");

                // Eliminar la reservación expirada
                deleteStmt.setInt(1, reservacionId);
                deleteStmt.executeUpdate();

                // Actualizar la disponibilidad de la habitación
                updateHabitacionStmt.setBoolean(1, true);
                updateHabitacionStmt.setInt(2, habitacionId);
                updateHabitacionStmt.executeUpdate();
            }

            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public double calcularCosto(String tipoHabitacion, int nivelEstrellas, int duracionEstadia) {
        double costoBase = obtenerCostoBase(tipoHabitacion, nivelEstrellas);
        System.out.println("Costo base: " + costoBase); // Añadir esta línea para depuración
        return costoBase * duracionEstadia;
    }
    private double obtenerCostoBase(String tipoHabitacion, int nivelEstrellas) {
        double costoBase = 0.0;

        switch (nivelEstrellas) {
            case 3:
                switch (tipoHabitacion) {
                    case "sencilla":
                        costoBase = 300.0;
                        break;
                    case "doble":
                        costoBase = 700.0;
                        break;
                    case "penthouse":
                        costoBase = 1200.0;
                        break;
                    default:
                        System.out.println("Tipo de habitación no coincide en nivel 3: " + tipoHabitacion); // Depuración
                        break;
                }
                break;

            case 4:
                switch (tipoHabitacion) {
                    case "sencilla":
                        costoBase = 300.0 * 1.20;
                        break;
                    case "doble":
                        costoBase = 700.0 * 1.30;
                        break;
                    case "penthouse":
                        costoBase = 1200.0 * 1.40;
                        break;
                    default:
                        System.out.println("Tipo de habitación no coincide en nivel 4: " + tipoHabitacion); // Depuración
                        break;
                }
                break;

            case 5:
                switch (tipoHabitacion) {
                    case "sencilla":
                        costoBase = 300.0 * 1.60;
                        break;
                    case "doble":
                        costoBase = 700.0 * 1.80;
                        break;
                    case "penthouse":
                        costoBase = 1200.0 * 2.00;
                        break;
                    default:
                        System.out.println("Tipo de habitación no coincide en nivel 5: " + tipoHabitacion); // Depuración
                        break;
                }
                break;

            default:
                System.out.println("Nivel de estrellas no coincide: " + nivelEstrellas); // Depuración
                break;
        }

        System.out.println("Costo base después de calcular: " + costoBase); // Depuración
        return costoBase;
    }


}
