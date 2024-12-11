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
    private final ReservacionService reservacionService;

    public HotelService(ConexionDataBase conexionDataBase, ReservacionService reservacionService) {
        this.conexionDataBase = conexionDataBase;
        this.reservacionService = reservacionService;
    }

    public int agregarHotel(String nombre, int estrellas, String direccion, String telefono) throws SQLException {
        String sqlHotel = "INSERT INTO hotel (nombre, estrellas, direccion, telefono) VALUES (?, ?, ?, ?)";
        String sqlHabitacion = "INSERT INTO habitacion (hotel_id, id, tipo, disponible, numero_personas, recamarera_id, costo) VALUES (?, ?, ?, ?, ?, ?, ?)";
        Connection connection = null;
        int hotelId = -1;

        try {
            connection = conexionDataBase.getConnection();
            connection.setAutoCommit(false);

            try (PreparedStatement statementHotel = connection.prepareStatement(sqlHotel,
                    PreparedStatement.RETURN_GENERATED_KEYS);
                 PreparedStatement statementHabitacion = connection.prepareStatement(sqlHabitacion)) {

                // Insertar el hotel en la base de datos
                statementHotel.setString(1, nombre);
                statementHotel.setInt(2, estrellas);
                statementHotel.setString(3, direccion);
                statementHotel.setString(4, telefono);
                statementHotel.executeUpdate();

                // Obtener el ID generado del hotel
                ResultSet generatedKeys = statementHotel.getGeneratedKeys();
                if (generatedKeys.next()) {
                    hotelId = generatedKeys.getInt(1);
                }

                if (hotelId != -1) {
                    // Insertar habitaciones para el hotel usando HabitacionBean y calcular el costo
                    for (int i = 1; i <= 20; i++) {
                        String tipo = "sencilla";
                        double costo = reservacionService.obtenerCostoBase(tipo, estrellas);
                        HabitacionBean habitacion = new HabitacionBean(0, hotelId, "S-" + i, tipo, true, 0, null, costo);
                        statementHabitacion.setInt(1, habitacion.getHotelId());
                        statementHabitacion.setString(2, habitacion.getId());
                        statementHabitacion.setString(3, habitacion.getTipo());
                        statementHabitacion.setBoolean(4, habitacion.isDisponible());
                        statementHabitacion.setInt(5, habitacion.getNumeroPersonas());
                        statementHabitacion.setNull(6, java.sql.Types.INTEGER);  // recamarera_id nulo
                        statementHabitacion.setDouble(7, habitacion.getCosto());  // Agregar costo
                        statementHabitacion.addBatch();
                    }
                    for (int i = 1; i <= 15; i++) {
                        String tipo = "doble";
                        double costo = reservacionService.obtenerCostoBase(tipo, estrellas);
                        HabitacionBean habitacion = new HabitacionBean(0, hotelId, "D-" + i, tipo, true, 0, null, costo);
                        statementHabitacion.setInt(1, habitacion.getHotelId());
                        statementHabitacion.setString(2, habitacion.getId());
                        statementHabitacion.setString(3, habitacion.getTipo());
                        statementHabitacion.setBoolean(4, habitacion.isDisponible());
                        statementHabitacion.setInt(5, habitacion.getNumeroPersonas());
                        statementHabitacion.setNull(6, java.sql.Types.INTEGER);  // recamarera_id nulo
                        statementHabitacion.setDouble(7, habitacion.getCosto());  // Agregar costo
                        statementHabitacion.addBatch();
                    }
                    for (int i = 1; i <= 5; i++) {
                        String tipo = "penthouse";
                        double costo = reservacionService.obtenerCostoBase(tipo, estrellas);
                        HabitacionBean habitacion = new HabitacionBean(0, hotelId, "P-" + i, tipo, true, 0, null, costo);
                        statementHabitacion.setInt(1, habitacion.getHotelId());
                        statementHabitacion.setString(2, habitacion.getId());
                        statementHabitacion.setString(3, habitacion.getTipo());
                        statementHabitacion.setBoolean(4, habitacion.isDisponible());
                        statementHabitacion.setInt(5, habitacion.getNumeroPersonas());
                        statementHabitacion.setNull(6, java.sql.Types.INTEGER);  // recamarera_id nulo
                        statementHabitacion.setDouble(7, habitacion.getCosto());  // Agregar costo
                        statementHabitacion.addBatch();
                    }

                    statementHabitacion.executeBatch();
                }
                connection.commit();
            } catch (SQLException e) {
                if (connection != null) {
                    connection.rollback();
                }
                throw e;
            }
        } finally {
            if (connection != null) {
                connection.setAutoCommit(true);
                conexionDataBase.closeConnection();
            }
        }
        return hotelId;  // Devolver el ID del hotel para asociar gerentes
    }




    public ArrayList<HotelBean> listarHoteles() throws SQLException {
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
        }
        return hoteles;
    }

    public void agregarHabitacion(String nombreHotel, HabitacionBean habitacion) throws SQLException {
        String sql = "INSERT INTO habitacion (hotel_id, id, tipo) VALUES (?, ?, ?)";
        try (Connection connection = conexionDataBase.getConnection()) {
            HotelBean hotel = buscarHotelPorNombre(nombreHotel, connection);

            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, hotel.getHotelId());
                statement.setString(2, habitacion.getId());
                statement.setString(3, habitacion.getTipo());
                statement.executeUpdate();
            }
        }
    }

    public ArrayList<HabitacionBean> listarHabitacionesDeHotel(String nombreHotel) throws SQLException {
        ArrayList<HabitacionBean> habitaciones = new ArrayList<>();
        String sql = "SELECT * FROM habitacion WHERE hotel_id = ?";

        try (Connection connection = conexionDataBase.getConnection()) {
            HotelBean hotel = buscarHotelPorNombre(nombreHotel, connection);
            int nivelEstrellas = hotel.getEstrellas(); // Obtener el nivel de estrellas del hotel

            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, hotel.getHotelId());

                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        int habitacionId = resultSet.getInt("habitacion_id");
                        int hotelId = resultSet.getInt("hotel_id");
                        String id = resultSet.getString("id");
                        String tipo = resultSet.getString("tipo");
                        boolean disponible = resultSet.getBoolean("disponible");
                        int numeroPersonas = resultSet.getInt("numero_personas");
                        double costo = reservacionService.obtenerCostoBase(tipo, nivelEstrellas); // Obtener el costo base
                        Integer recamareraId = resultSet.getObject("recamarera_id") != null ? resultSet.getInt("recamarera_id") : null;

                        HabitacionBean habitacion = new HabitacionBean(
                            habitacionId, hotelId, id, tipo, disponible, numeroPersonas, recamareraId, costo
                        );
                        habitaciones.add(habitacion);
                    }
                }
            }
        }

        return habitaciones;
    }


    public HotelBean buscarHotelPorNombre(String nombreHotel) throws SQLException {
        try (Connection connection = conexionDataBase.getConnection()) {
            return buscarHotelPorNombre(nombreHotel, connection);
        }
    }

    HotelBean buscarHotelPorNombre(String nombreHotel, Connection connection) throws SQLException {
        String sql = "SELECT * FROM hotel WHERE nombre = ?";
        HotelBean hotel = null;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
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
        }

        if (hotel == null) {
            throw new IllegalArgumentException("Hotel no encontrado: " + nombreHotel);
        }

        return hotel;
    }

    public void eliminarHotel(String nombreHotel) throws SQLException {
        String sql = "DELETE FROM hotel WHERE nombre = ?";

        try (Connection connection = conexionDataBase.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, nombreHotel);
            statement.executeUpdate();
        }
    }
    
 // Método para modificar un hotel existente
    public void modificarHotel(int hotelId, String nombre, int estrellas, String direccion, String telefono) throws SQLException {
        String sql = "UPDATE hotel SET nombre = ?, estrellas = ?, direccion = ?, telefono = ? WHERE hotel_id = ?";
        
        try (Connection connection = conexionDataBase.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, nombre);
            statement.setInt(2, estrellas);
            statement.setString(3, direccion);
            statement.setString(4, telefono);
            statement.setInt(5, hotelId);
            statement.executeUpdate();
        }
    }
    
    public int obtenerNivelEstrellas(int hotelId) throws SQLException {
        String sql = "SELECT estrellas FROM hotel WHERE hotel_id = ?";
        int estrellas = 0;

        try (Connection connection = conexionDataBase.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, hotelId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    estrellas = resultSet.getInt("estrellas");
                }
            }
        }

        return estrellas;
    }

}
