package services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import beans.VendedorBean;
import dataBase.ConexionDataBase;

public class VendedorService {
    private ConexionDataBase conexionDataBase;

    public VendedorService(ConexionDataBase conexionDataBase) {
        this.conexionDataBase = conexionDataBase;
    }
    public VendedorBean obtenerVendedorPorEmpleadoId(int empleadoId) throws SQLException {
        String sql = "SELECT * FROM vendedor WHERE empleado_id = ?";
        VendedorBean vendedor = null;

        try (Connection connection = conexionDataBase.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, empleadoId);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    vendedor = new VendedorBean();
                    vendedor.setVendedorId(resultSet.getInt("vendedor_id"));
                    vendedor.setEmpleadoId(resultSet.getInt("empleado_id"));
                    vendedor.setComision(resultSet.getDouble("comision"));
                } else {
                    System.err.println("El vendedor con empleado_id " + empleadoId + " no existe.");
                }
            }
        } catch (SQLException e) {
            System.err.println("Error SQL: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
        return vendedor;
    }



    public VendedorBean obtenerVendedorPorId(int vendedorId) throws SQLException {
        String sql = "SELECT * FROM vendedor WHERE vendedor_id = ?";
        VendedorBean vendedor = null;

        try (Connection connection = conexionDataBase.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, vendedorId);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    vendedor = new VendedorBean();
                    vendedor.setVendedorId(resultSet.getInt("vendedor_id"));
                    vendedor.setNombre(resultSet.getString("nombre"));
                }
            }
        }
        return vendedor;
    }

    public VendedorBean obtenerVendedorPorNombre(String nombre) throws SQLException {
        String sql = "SELECT * FROM vendedor WHERE nombre = ?";
        VendedorBean vendedor = null;

        try (Connection connection = conexionDataBase.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, nombre);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    vendedor = new VendedorBean();
                    vendedor.setVendedorId(resultSet.getInt("vendedor_id"));
                    vendedor.setNombre(resultSet.getString("nombre"));
                }
            }
        }
        return vendedor;
    }

    public List<VendedorBean> listarVendedores() throws SQLException {
        String sql = "SELECT * FROM vendedor";
        List<VendedorBean> vendedores = new ArrayList<>();

        try (Connection connection = conexionDataBase.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                VendedorBean vendedor = new VendedorBean();
                vendedor.setVendedorId(resultSet.getInt("vendedor_id"));
                vendedor.setNombre(resultSet.getString("nombre"));
                vendedores.add(vendedor);
            }
        }
        return vendedores;
    }
}
