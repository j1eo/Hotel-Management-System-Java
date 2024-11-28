package tests;

import controllers.HotelController;
import dataBase.ConexionDataBase;
import dataBase.InicializarTablas;
import dataBase.LoadConfig;
import services.HotelService;
import views.HotelViews;

import javax.swing.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class DataBaseTest {
    private static final String EXIT_OPTION = "Salir";

    public static void main(String[] args) {
        Object selectedOption;
        String parsedSelectedOption = "";

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
        HotelService hotelService = new HotelService(conexionDataBase);
        HotelViews hotelViews = new HotelViews();
        HotelController hotelController = new HotelController(hotelService, hotelViews);

        Connection connection = null;
        try {
            conexionDataBase.openConnection();
            connection = conexionDataBase.getConnection();

            // Inicializar tablas
            InicializarTablas inicializarTablas = new InicializarTablas(connection);
            inicializarTablas.initializeDatabase();

            // Bucle principal para mostrar el menú
            while (true) {
                try {
                    String[] options = {"Listar Hoteles", "Agregar Hotel", "Listar Habitaciones de un Hotel", "Buscar Hotel por Nombre", "Eliminar Hotel", EXIT_OPTION};
                    selectedOption = JOptionPane.showInputDialog(null, "Selecciona una opción", "Menú Principal",
                            JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

                    // Verificar si el usuario hizo clic en el botón de cancelar
                    if (selectedOption == null || selectedOption.toString().equals(EXIT_OPTION)) {
                        JOptionPane.showMessageDialog(null, "Saliendo del sistema...");
                        break;
                    }

                    hotelController.evalOption(selectedOption);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Ha ocurrido un error: " + e.getMessage());
                }
            }

        } catch (SQLException e) {
            System.err.println("Error en la operación SQL: " + e.getMessage());
        } finally {
            try {
                if (connection != null && !connection.isClosed()) {
                    conexionDataBase.closeConnection();
                }
            } catch (SQLException e) {
                System.err.println("Error al cerrar la conexión: " + e.getMessage());
            }
        }
    }
}
