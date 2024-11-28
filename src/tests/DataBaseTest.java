package tests;

import controllers.HotelController;
import dataBase.ConexionDataBase;
import dataBase.InicializarTablas;
import dataBase.LoadConfig;

import javax.swing.JOptionPane;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class DataBaseTest {
    public static void main(String[] args) {
        // Cargar las propiedades para la conexión
        String propertiesPath = ".properties"; // Asegúrate de que esta sea la ruta correcta
        Properties props = LoadConfig.loadProperties(propertiesPath);

        if (props == null) {
            System.err.println("Error: No se pudieron cargar las propiedades.");
            return;
        }

        String url = props.getProperty("DB_URL");
        String user = props.getProperty("DB_USER");
        String pass = props.getProperty("DB_PASS");

        // Instancia de la conexión a la base de datos
        ConexionDataBase conexionDataBase = new ConexionDataBase(url, user, pass);
        HotelController hotelController = new HotelController(conexionDataBase);

        try {
            conexionDataBase.openConnection();
            Connection connection = conexionDataBase.getConnection();

            // Inicializar tablas
            InicializarTablas inicializarTablas = new InicializarTablas(connection);
            inicializarTablas.initializeDatabase();

            // Menú principal
            String parsedSelectedOption = "";
            do {
                String[] options = {"Listar Hoteles", "Agregar Hotel", "Listar Habitaciones de un Hotel", "Salir"};
                Object selectedOption = JOptionPane.showInputDialog(null, "Selecciona una opción", "Menú Principal",
                        JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                parsedSelectedOption = (selectedOption != null) ? selectedOption.toString() : "Salir";

                switch (parsedSelectedOption) {
                    case "Listar Hoteles":
                        hotelController.listarHoteles();
                        break;
                    case "Agregar Hotel":
                        hotelController.agregarHotel();
                        break;
                    case "Listar Habitaciones de un Hotel":
                        hotelController.listarHabitacionesDeHotel();
                        break;
                    case "Salir":
                        JOptionPane.showMessageDialog(null, "Saliendo...");
                        break;
                    default:
                        JOptionPane.showMessageDialog(null, "Opción inválida. Intenta de nuevo.");
                }
            } while (!parsedSelectedOption.equals("Salir"));

        } catch (SQLException e) {
            System.err.println("Error en la operación SQL: " + e.getMessage());
        } finally {
            try {
                conexionDataBase.closeConnection();
            } catch (SQLException e) {
                System.err.println("Error al cerrar la conexión: " + e.getMessage());
            }
        }
    }
}
