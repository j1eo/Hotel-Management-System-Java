package dataBase;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class InicializarTablas {
    private Connection connection;

    public InicializarTablas(Connection connection) {
        this.connection = connection;
    }

    public void initializeDatabase() {
        crearTablaHotel();
        crearTablaHabitacion();
    }

    public void crearTablaHotel() {
        String sql = "CREATE TABLE IF NOT EXISTS hotel (" +
                     "hotel_id INT AUTO_INCREMENT PRIMARY KEY, " +
                     "nombre VARCHAR(255) NOT NULL, " +
                     "estrellas INT NOT NULL, " +
                     "direccion VARCHAR(255) NOT NULL, " +
                     "telefono VARCHAR(11) NOT NULL" +
                     ");";

        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
            System.out.println("Tabla 'hotel' creada o ya existe.");
        } catch (SQLException e) {
            System.err.println("Error al crear la tabla 'hotel': " + e.getMessage());
        }
    }

    public void crearTablaHabitacion() {
        String sql = "CREATE TABLE IF NOT EXISTS habitacion (" +
                     "habitacion_id INT AUTO_INCREMENT PRIMARY KEY, " +
                     "hotel_id INT NOT NULL, " +
                     "id VARCHAR(50) NOT NULL, " +
                     "tipo VARCHAR(50) NOT NULL, " +
                     "FOREIGN KEY (hotel_id) REFERENCES hotel(hotel_id)" +
                     ");";

        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
            System.out.println("Tabla 'habitacion' creada o ya existe.");
        } catch (SQLException e) {
            System.err.println("Error al crear la tabla 'habitacion': " + e.getMessage());
        }
    }
    
    public void crearTablaEmpleado() {
        String sql = "CREATE TABLE IF NOT EXISTS empleado (" +
                     "empleado_id INT AUTO_INCREMENT PRIMARY KEY, " +
                     "nombre VARCHAR(255) NOT NULL, " +
                     "estrellas INT NOT NULL, " +
                     "direccion VARCHAR(255) NOT NULL, " +
                     "telefono VARCHAR(11) NOT NULL" +
                     ");";

        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
            System.out.println("Tabla 'hotel' creada o ya existe.");
        } catch (SQLException e) {
            System.err.println("Error al crear la tabla 'hotel': " + e.getMessage());
        }
    }

}
