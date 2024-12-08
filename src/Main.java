import controllers.HotelController;
import controllers.EmpleadoController;
import controllers.ReservacionController;
import controllers.SystemController;
import dataBase.ConexionDataBase;
import dataBase.InicializarTablas;
import dataBase.LoadConfig;
import services.HotelService;
import services.EmpleadoService;
import services.ReservacionService;
import services.VendedorService;
import views.HotelViews;
import views.EmpleadoViews;
import views.ReservacionViews;
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
            ReservacionService reservacionService = new ReservacionService(conexionDataBase);
            VendedorService vendedorService = new VendedorService(conexionDataBase);

            // Verificar y actualizar reservaciones expiradas al iniciar el programa
            try {
                reservacionService.verificarYActualizarReservacionesExpiradas();
            } catch (SQLException e) {
                System.err.println("Error al verificar y actualizar reservaciones expiradas: " + e.getMessage());
            }

            // Crear vistas
            EmpleadoViews empleadoViews = new EmpleadoViews();
            SystemViews systemViews = new SystemViews();
            ReservacionViews reservacionViews = new ReservacionViews();

            // Crear controladores
            EmpleadoController empleadoController = new EmpleadoController(empleadoService, empleadoViews, hotelService);
            HotelViews hotelViews = new HotelViews(null);
            HotelController hotelController = new HotelController(hotelService, hotelViews, empleadoController, empleadoViews);
            ReservacionController reservacionController = new ReservacionController(reservacionService, vendedorService, hotelService, reservacionViews);

            // Crear SystemController con todos los controladores y vistas
            SystemController systemController = new SystemController(hotelViews, hotelController, empleadoViews, empleadoController, systemViews, reservacionController, reservacionViews, reservacionService, hotelService);

            // Mostrar el menú principal
            systemViews.mostrarMenuPrincipal(systemController);

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
