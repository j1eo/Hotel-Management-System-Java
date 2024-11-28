package services;

import beans.HabitacionBean;
import beans.HotelBean;
import dataBase.ConexionDataBase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class HotelService {

    private ConexionDataBase conexionDataBase;

    public HotelService(ConexionDataBase conexionDataBase) {
        this.conexionDataBase = conexionDataBase;
    }

    public void agregarHotel(String nombre, int estrellas, String direccion, String telefono) {
        String sqlHotel = "INSERT INTO hotel (nombre, estrellas, direccion, telefono) VALUES (?, ?, ?, ?)";
        String sqlHabitacion = "INSERT INTO habitacion (hotel_id, id, tipo) VALUES (?, ?, ?)";

        try (Connection connection = conexionDataBase.getConnection();
             PreparedStatement statementHotel = connection.prepareStatement(sqlHotel, PreparedStatement.RETURN_GENERATED_KEYS);
             PreparedStatement statementHabitacion = connection.prepareStatement(sqlHabitacion)) {

            // Insertar el hotel en la base de datos
            statementHotel.setString(1, nombre);
            statementHotel.setInt(2, estrellas);
            statementHotel.setString(3, direccion);
            statementHotel.setString(4, telefono);
            statementHotel.executeUpdate();

            // Obtener el ID generado del hotel
            ResultSet generatedKeys = statementHotel.getGeneratedKeys();
            int hotelId = -1;
            if (generatedKeys.next()) {
                hotelId = generatedKeys.getInt(1);
            }

            if (hotelId != -1) {
                // Insertar habitaciones para el hotel
                for (int i = 1; i <= 20; i++) {
                    statementHabitacion.setInt(1, hotelId);
                    statementHabitacion.setString(2, "S-" + i);
                    statementHabitacion.setString(3, "sencilla");
                    statementHabitacion.addBatch();
                }
                for (int i = 1; i <= 15; i++) {
                    statementHabitacion.setInt(1, hotelId);
                    statementHabitacion.setString(2, "D-" + i);
                    statementHabitacion.setString(3, "doble");
                    statementHabitacion.addBatch();
                }
                for (int i = 1; i <= 5; i++) {
                    statementHabitacion.setInt(1, hotelId);
                    statementHabitacion.setString(2, "P-" + i);
                    statementHabitacion.setString(3, "penthouse");
                    statementHabitacion.addBatch();
                }

                statementHabitacion.executeBatch();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<HotelBean> listarHoteles() {
        ArrayList<HotelBean> hoteles = new ArrayList<>();
        String sql = "SELECT * FROM hotel";

        try (Connection connection = conexionDataBase.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int hotelId = resultSet.getInt("hotel_id");
                String nombre = resultSet.getString("nombre");
                int estrellas = resultSet.getInt("estrellas");
                String direccion = resultSet.getString("direccion");
                String telefono = resultSet.getString("telefono");
                HotelBean hotel = new HotelBean(nombre, estrellas, direccion, telefono);
                hotel.setHotelId(hotelId);
                hoteles.add(hotel);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return hoteles;
    }

    public void agregarHabitacion(String nombreHotel, HabitacionBean habitacion) {
        String sql = "INSERT INTO habitacion (hotel_id, id, tipo) VALUES (?, ?, ?)";
        HotelBean hotel = buscarHotelPorNombre(nombreHotel);

        try (Connection connection = conexionDataBase.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, hotel.getHotelId());
            statement.setString(2, habitacion.getID());
            statement.setString(3, habitacion.getTipo());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<HabitacionBean> listarHabitacionesDeHotel(String nombreHotel) {
        ArrayList<HabitacionBean> habitaciones = new ArrayList<>();
        String sql = "SELECT * FROM habitacion WHERE hotel_id = ?";

        HotelBean hotel = buscarHotelPorNombre(nombreHotel);

        try (Connection connection = conexionDataBase.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, hotel.getHotelId());
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    String id = resultSet.getString("id");
                    String tipo = resultSet.getString("tipo");
                    HabitacionBean habitacion = new HabitacionBean(id, tipo);
                    habitaciones.add(habitacion);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return habitaciones;
    }

    public HotelBean buscarHotelPorNombre(String nombreHotel) {
        String sql = "SELECT * FROM hotel WHERE nombre = ?";
        HotelBean hotel = null;

        try (Connection connection = conexionDataBase.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, nombreHotel);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int hotelId = resultSet.getInt("hotel_id");
                    String nombre = resultSet.getString("nombre");
                    int estrellas = resultSet.getInt("estrellas");
                    String direccion = resultSet.getString("direccion");
                    String telefono = resultSet.getString("telefono");
                    hotel = new HotelBean(nombre, estrellas, direccion, telefono);
                    hotel.setHotelId(hotelId);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (hotel == null) {
            throw new IllegalArgumentException("Hotel no encontrado: " + nombreHotel);
        }

        return hotel;
    }
}
