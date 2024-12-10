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
        crearTablaEmpleado();
        crearTablaRecamarera();
        crearTablaGerente();
        crearTablaVendedor();
        crearTablaEmpleadoHotel();
        crearTablaHabitacion();
        crearTablaReservacion();
        crearTablaComisionRecamarera(); // Nueva tabla de comisiones de recamareras
        crearTablaComisionVendedor(); // Nueva tabla de comisiones de vendedores
        crearTablaBonoGerente(); // Nueva tabla de bonos de gerentes
        crearTablaAsignacionHabitacion(); // Nueva tabla de asignaciones de habitaciones
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

    public void crearTablaEmpleado() {
        String sql = "CREATE TABLE IF NOT EXISTS empleado (" +
                     "empleado_id INT AUTO_INCREMENT PRIMARY KEY, " +
                     "nombre VARCHAR(255) NOT NULL, " +
                     "salario DOUBLE NOT NULL, " +
                     "tipo VARCHAR(50) NOT NULL" +
                     ");";

        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
            System.out.println("Tabla 'empleado' creada o ya existe.");
        } catch (SQLException e) {
            System.err.println("Error al crear la tabla 'empleado': " + e.getMessage());
        }
    }

    public void crearTablaRecamarera() {
        String sql = "CREATE TABLE IF NOT EXISTS recamarera (" +
        		     "recamarera_id INT AUTO_INCREMENT PRIMARY KEY, "  +
                     "empleado_id INT NOT NULL, " +
                     "nivel_experiencia VARCHAR(255) NOT NULL, " +
                     "FOREIGN KEY (empleado_id) REFERENCES empleado(empleado_id)" +
                     ");";

        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
            System.out.println("Tabla 'recamarera' creada o ya existe.");
        } catch (SQLException e) {
            System.err.println("Error al crear la tabla 'recamarera': " + e.getMessage());
        }
    }

    public void crearTablaGerente() {
        String sql = "CREATE TABLE IF NOT EXISTS gerente (" +
        			 "gerente_id INT AUTO_INCREMENT PRIMARY KEY, "+
                     "empleado_id INT NOT NULL, " +
                     "bono DOUBLE NOT NULL, " +
                     "FOREIGN KEY (empleado_id) REFERENCES empleado(empleado_id)" +
                     ");";

        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
            System.out.println("Tabla 'gerente' creada o ya existe.");
        } catch (SQLException e) {
            System.err.println("Error al crear la tabla 'gerente': " + e.getMessage());
        }
    }

    public void crearTablaVendedor() {
        String sql = "CREATE TABLE IF NOT EXISTS vendedor (" +
                     "vendedor_id INT AUTO_INCREMENT PRIMARY KEY, " +
                     "empleado_id INT NOT NULL, " +
                     "comision DOUBLE NOT NULL, " +
                     "FOREIGN KEY (empleado_id) REFERENCES empleado(empleado_id)" +
                     ");";

        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
            System.out.println("Tabla 'vendedor' creada o ya existe.");
        } catch (SQLException e) {
            System.err.println("Error al crear la tabla 'vendedor': " + e.getMessage());
        }
    }


    public void crearTablaEmpleadoHotel() {
        String sql = "CREATE TABLE IF NOT EXISTS empleado_hotel (" +
                     "empleado_id INT NOT NULL, " +
                     "hotel_id INT NOT NULL, " +
                     "FOREIGN KEY (empleado_id) REFERENCES empleado(empleado_id), " +
                     "FOREIGN KEY (hotel_id) REFERENCES hotel(hotel_id)" +
                     ");";

        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
            System.out.println("Tabla 'empleado_hotel' creada o ya existe.");
        } catch (SQLException e) {
            System.err.println("Error al crear la tabla 'empleado_hotel': " + e.getMessage());
        }
    }
    public void crearTablaHabitacion() {
        String sql = "CREATE TABLE IF NOT EXISTS habitacion (" +
                     "habitacion_id INT AUTO_INCREMENT PRIMARY KEY, " +
                     "hotel_id INT NOT NULL, " +
                     "id VARCHAR(50) NOT NULL, " +
                     "tipo VARCHAR(50) NOT NULL, " +
                     "recamarera_id INT, " +
                     "disponible BOOLEAN NOT NULL DEFAULT true, " +
                     "numero_personas INT DEFAULT 0, " +
                     "FOREIGN KEY (hotel_id) REFERENCES hotel(hotel_id), " +
                     "FOREIGN KEY (recamarera_id) REFERENCES recamarera(empleado_id)" +
                     ");";

        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
            System.out.println("Tabla 'habitacion' creada o ya existe.");
        } catch (SQLException e) {
            System.err.println("Error al crear la tabla 'habitacion': " + e.getMessage());
        }
    }



    public void crearTablaReservacion() {
        String sql = "CREATE TABLE IF NOT EXISTS reservacion (" +
                     "reservacion_id INT AUTO_INCREMENT PRIMARY KEY, " +
                     "vendedor_id INT NOT NULL, " +
                     "hotel_id INT NOT NULL, " +
                     "habitacion_id INT NOT NULL, " +
                     "numero_personas INT NOT NULL, " +
                     "fecha_registro DATE NOT NULL, " +
                     "duracion_estadia INT NOT NULL, " +
                     "costo DOUBLE NOT NULL, " +
                     "FOREIGN KEY (vendedor_id) REFERENCES vendedor(empleado_id), " + // Referencia correcta
                     "FOREIGN KEY (hotel_id) REFERENCES hotel(hotel_id), " +
                     "FOREIGN KEY (habitacion_id) REFERENCES habitacion(habitacion_id)" +
                     ");";

        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
            System.out.println("Tabla 'reservacion' creada o ya existe.");
        } catch (SQLException e) {
            System.err.println("Error al crear la tabla 'reservacion': " + e.getMessage());
        }
    }
    
    public void crearTablaComisionRecamarera() {
        String sql = "CREATE TABLE IF NOT EXISTS ComisionRecamarera (" +
                     "comision_id INT AUTO_INCREMENT PRIMARY KEY, " +
                     "empleado_id INT NOT NULL, " +
                     "hotel_id INT NOT NULL, " +
                     "tipo_habitacion VARCHAR(50) NOT NULL, " +
                     "cantidad_habitaciones INT NOT NULL, " +
                     "fecha DATE NOT NULL, " +
                     "comision DOUBLE NOT NULL, " +
                     "FOREIGN KEY (empleado_id) REFERENCES empleado(empleado_id), " +
                     "FOREIGN KEY (hotel_id) REFERENCES hotel(hotel_id)" +
                     ");";

        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
            System.out.println("Tabla 'ComisionRecamarera' creada o ya existe.");
        } catch (SQLException e) {
            System.err.println("Error al crear la tabla 'ComisionRecamarera': " + e.getMessage());
        }
    }

    public void crearTablaComisionVendedor() {
        String sql = "CREATE TABLE IF NOT EXISTS ComisionVendedor (" +
                     "comision_id INT AUTO_INCREMENT PRIMARY KEY, " +
                     "empleado_id INT NOT NULL, " +
                     "hotel_id INT NOT NULL, " +
                     "tipo_habitacion VARCHAR(50) NOT NULL, " +
                     "numero_personas INT NOT NULL, " +
                     "fecha DATE NOT NULL, " +
                     "comision DOUBLE NOT NULL, " +
                     "FOREIGN KEY (empleado_id) REFERENCES empleado(empleado_id), " +
                     "FOREIGN KEY (hotel_id) REFERENCES hotel(hotel_id)" +
                     ");";

        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
            System.out.println("Tabla 'ComisionVendedor' creada o ya existe.");
        } catch (SQLException e) {
            System.err.println("Error al crear la tabla 'ComisionVendedor': " + e.getMessage());
        }
    }

    public void crearTablaBonoGerente() {
        String sql = "CREATE TABLE IF NOT EXISTS BonoGerente (" +
                     "bono_id INT AUTO_INCREMENT PRIMARY KEY, " +
                     "empleado_id INT NOT NULL, " +
                     "hotel_id INT NOT NULL, " +
                     "mes VARCHAR(50) NOT NULL, " +
                     "bono DOUBLE NOT NULL, " +
                     "FOREIGN KEY (empleado_id) REFERENCES empleado(empleado_id), " +
                     "FOREIGN KEY (hotel_id) REFERENCES hotel(hotel_id)" +
                     ");";

        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
            System.out.println("Tabla 'BonoGerente' creada o ya existe.");
        } catch (SQLException e) {
            System.err.println("Error al crear la tabla 'BonoGerente': " + e.getMessage());
        }
    }
    
    public void crearTablaAsignacionHabitacion() {
        String sql = "CREATE TABLE IF NOT EXISTS AsignacionHabitacion (" +
                     "asignacion_id INT AUTO_INCREMENT PRIMARY KEY, " +
                     "hotel_id INT, " +
                     "habitacion_id INT, " +
                     "recamarera_id INT, " +
                     "nombre_recamarera VARCHAR(255), " +
                     "fecha DATE, " +
                     "FOREIGN KEY (hotel_id) REFERENCES hotel(hotel_id), " +
                     "FOREIGN KEY (habitacion_id) REFERENCES habitacion(habitacion_id), " +
                     "FOREIGN KEY (recamarera_id) REFERENCES recamarera(recamarera_id)" +
                     ");";

        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
            System.out.println("Tabla 'AsignacionHabitacion' creada o ya existe.");
        } catch (SQLException e) {
            System.err.println("Error al crear la tabla 'AsignacionHabitacion': " + e.getMessage());
        }
    }

}
