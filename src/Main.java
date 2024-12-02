import controllers.HotelController;
import controllers.EmpleadoController;
import controllers.SystemController;
import dataBase.ConexionDataBase;
import dataBase.InicializarTablas;
import dataBase.LoadConfig;
import services.HotelService;
import services.EmpleadoService;
import views.HotelViews;
import views.EmpleadoViews;
import views.SystemViews;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class Main {
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

        try {
            conexionDataBase.openConnection();
            Connection connection = conexionDataBase.getConnection();

            // Inicializar tablas
            InicializarTablas inicializarTablas = new InicializarTablas(connection);
            inicializarTablas.initializeDatabase();

            // Crear servicios
            HotelService hotelService = new HotelService(conexionDataBase);
            EmpleadoService empleadoService = new EmpleadoService(conexionDataBase);

            // Crear controladores y vistas
            EmpleadoViews empleadoViews = new EmpleadoViews();
            EmpleadoController empleadoController = new EmpleadoController(empleadoService, empleadoViews);
            HotelViews hotelViews = new HotelViews(null); 
            HotelController hotelController = new HotelController(hotelService, hotelViews, empleadoController, empleadoViews);
           
            SystemController systemController = new SystemController(hotelViews, hotelController, empleadoViews, empleadoController);

            // Mostrar el menú principal del sistema
            SystemViews systemViews = new SystemViews(systemController);
            systemViews.mostrarMenuPrincipal();

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
