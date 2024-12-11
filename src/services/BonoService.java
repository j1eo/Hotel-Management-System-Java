package services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import beans.BonoGerenteBean;
import dataBase.ConexionDataBase;

public class BonoService {
    private ConexionDataBase conexionDataBase;

    public BonoService(ConexionDataBase conexionDataBase) {
        this.conexionDataBase = conexionDataBase;
    }

    public List<BonoGerenteBean> obtenerBonosGerentesPorMes(int hotelId, String mes) throws SQLException {
        String sql = "SELECT * FROM BonoGerente WHERE hotel_id = ? AND mes = ?";
        List<BonoGerenteBean> bonos = new ArrayList<>();

        try (Connection connection = conexionDataBase.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, hotelId);
            statement.setString(2, mes);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int bonoId = resultSet.getInt("bono_id");
                    int empleadoId = resultSet.getInt("empleado_id");
                    double monto = resultSet.getDouble("bono");
                    BonoGerenteBean bono = new BonoGerenteBean(bonoId, empleadoId, hotelId, monto, null, mes);
                    bonos.add(bono);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }

        return bonos;
    }

    public List<String> obtenerNombresGerentesPorHotel(int hotelId) throws SQLException {
        String sql = "SELECT e.nombre " +
                     "FROM empleado e " +
                     "JOIN empleado_hotel eh ON e.empleado_id = eh.empleado_id " +
                     "JOIN gerente g ON e.empleado_id = g.empleado_id " +
                     "WHERE eh.hotel_id = ?";
        List<String> nombresGerentes = new ArrayList<>();

        try (Connection connection = conexionDataBase.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, hotelId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    nombresGerentes.add(resultSet.getString("nombre"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return nombresGerentes;
    }

    public double calcularBonoGerente(int hotelId, String mes) throws SQLException {
        double totalVentas = obtenerVentasPorMes(hotelId, mes);

        int nivelEstrellas = obtenerNivelEstrellasHotel(hotelId);

        if (nivelEstrellas == 3 && totalVentas >= 80000) {
            return 5000.0;
        } else if (nivelEstrellas == 4 && totalVentas >= 100000) {
            return 8000.0;
        } else if (nivelEstrellas == 5 && totalVentas >= 150000) {
            return 12000.0;
        } else {
            return 0.0;
        }
    }

    public double obtenerVentasPorMes(int hotelId, String mes) throws SQLException {
        String sql = "SELECT SUM(total_ventas) AS total_ventas FROM ventasmes WHERE hotel_id = ? AND mes = ?";
        double totalVentas = 0.0;

        try (Connection connection = conexionDataBase.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, hotelId);
            statement.setString(2, mes);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    totalVentas = resultSet.getDouble("total_ventas");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }

        return totalVentas;
    }

    public void registrarComisionGerente(int empleadoId, int hotelId, String mes) throws SQLException {
        double bono = calcularBonoGerente(hotelId, mes);
        String sql = "INSERT INTO BonoGerente (empleado_id, hotel_id, mes, bono) VALUES (?, ?, ?, ?)";

        try (Connection connection = conexionDataBase.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, empleadoId);
            statement.setInt(2, hotelId);
            statement.setString(3, mes);
            statement.setDouble(4, bono);
            statement.executeUpdate();
            System.out.println("Comisión del gerente registrada exitosamente.");
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    private int obtenerNivelEstrellasHotel(int hotelId) throws SQLException {
        String sql = "SELECT estrellas FROM hotel WHERE hotel_id = ?";
        try (Connection connection = conexionDataBase.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, hotelId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("estrellas");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return 0; // Si no se encuentra el hotel
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
}
