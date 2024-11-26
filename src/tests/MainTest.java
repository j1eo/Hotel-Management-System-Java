package tests;

import beans.*;
import controllers.*;
import dataBase.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

import javax.swing.JOptionPane;

public class MainTest {
    public static void main(String[] args) throws SQLException {
    	
    	
    	// Cargar las propiedades para la conexión
        String propertiesPath = ".properties";
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

        String strqry = "INSERT INTO hotel (nombre, estrellas) VALUES (?, ?)";
        try {
            // Abrir la conexión
            conexionDataBase.openConnection();
            Connection connection = conexionDataBase.getConnection();

            // Crear la consulta preparada
            PreparedStatement stm = connection.prepareStatement(strqry);
            
            // Ejemplo de datos
            String hotelNombre = "";
            hotelNombre=JOptionPane.showInputDialog("Ingresa el nombre del Hotel: ");
            String Stringestrellas =JOptionPane.showInputDialog("Ingresa la cantidad de Estrellas: ");
            int estrellas =Integer.parseInt(Stringestrellas);
           

            // Configurar parámetros
            stm.setString(1, hotelNombre);
            stm.setInt(2, estrellas);

            // Ejecutar la consulta
            int rowsInserted = stm.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Hotel insertado exitosamente: " + hotelNombre);
            }

        } catch (SQLException e) {
            System.err.println("Error en la operación SQL: " + e.getMessage());
        } finally {
            try {
                conexionDataBase.closeConnection();
            } catch (SQLException e) {
                System.err.println("Error al cerrar la conexión: " + e.getMessage());
            }
        }
        
		
		
        

        // Crear instancias de controladores
        HotelController hotelController = new HotelController();
        EmpleadoController empleadoController = new EmpleadoController();
        RecamareraController recamareraController = new RecamareraController();

        // Crear hoteles y agregar habitaciones
        hotelController.agregarHotel("Hotel A", 4); // 4 habitaciones en Hotel A
        hotelController.agregarHotel("Hotel B", 5); // 5 habitaciones en Hotel B

        // Obtener hoteles creados
        HotelBean hotelA = hotelController.buscarHotelPorNombre("Hotel A");
        HotelBean hotelB = hotelController.buscarHotelPorNombre("Hotel B");


        // Crear empleados
        RecamareraAuxiliar recamarera1 = new RecamareraAuxiliar("Ana", 1500.0);
        RecamareraPrincipianteBean recamarera2 = new RecamareraPrincipianteBean("Juan", 1200.0);
        AmaDeLlavesBean amaDeLlaves = new AmaDeLlavesBean("Maria", 2000.0);

        // Asociar recamareras a un hotel
        empleadoController.agregarRecamarera(recamarera1, hotelA);
        empleadoController.agregarRecamarera(recamarera2, hotelA);
        
        empleadoController.agregarRecamarera(amaDeLlaves, hotelB);

        // Obtener habitaciones de cada hotel
        ArrayList<HabitacionBean> habitacionesHotelA = hotelController.listarHabitacionesDeHotel("Hotel A");
        ArrayList<HabitacionBean> habitacionesHotelB = hotelController.listarHabitacionesDeHotel("Hotel B");


        // Asignar habitaciones a recamareras
        recamareraController.asignarHabitacion(recamarera1, habitacionesHotelA.get(0)); // Habitación Sencilla
        recamareraController.asignarHabitacion(recamarera2, habitacionesHotelA.get(16)); // Habitación Doble
        recamareraController.asignarHabitacion(amaDeLlaves, habitacionesHotelB.get(38)); // Habitación Penthouse

        // Imprimir detalles de los hoteles
        System.out.println("Hoteles registrados:");
        ArrayList<HotelBean> hoteles = hotelController.listarHoteles();
        for (HotelBean hotel : hoteles) {
            System.out.println("Nombre: " + hotel.getNombre() + ", Estrellas: " + hotel.getEstrellas());
            System.out.println("Habitaciones:");
            for (HabitacionBean habitacion : hotel.getHabitaciones()) {
                System.out.println("  ID: " + habitacion.getID() + ", Tipo: " + habitacion.getTipo());
            }
        }

        // Imprimir detalles de los empleados por hotel
        System.out.println("\nEmpleados registrados por hotel:");
        for (HotelBean hotel : hoteles) {
            System.out.println("\nHotel: " + hotel.getNombre());
            for (EmpleadoBean empleado : hotel.getEmpleados()) {
                System.out.println("Nombre: " + empleado.getNombre() + ", Puesto: " + empleado.getClass().getSimpleName());
            }
        }

        // Imprimir detalles de las recamareras y sus habitaciones asignadas
        System.out.println("\nRecamareras registradas:");
        ArrayList<RecamareraBean> recamareras = empleadoController.listarRecamareras();
        for (RecamareraBean recamarera : recamareras) {
            System.out.println("Nombre: " + recamarera.getNombre() + ", Nivel: " + recamarera.getNivelExperiencia());
            System.out.println("Habitaciones asignadas:");
            for (HabitacionBean habitacion : recamarera.getHabitacionesAsignadas()) {
                System.out.println("  ID: " + habitacion.getID() + ", Tipo: " + habitacion.getTipo());
            }
        }

        // Imprimir comisiones por habitación de Hotel A
        System.out.println("\nComisiones por habitación del hotel 'Hotel A':");
        for (HabitacionBean habitacion : habitacionesHotelA) {
            double comision = hotelController.calcularComision("Hotel A", habitacion.getID());
            System.out.println("ID: " + habitacion.getID() + ", Comisión: $" + comision);
        }

        // Imprimir comisiones por habitación de Hotel B
        System.out.println("\nComisiones por habitación del hotel 'Hotel B':");
        for (HabitacionBean habitacion : habitacionesHotelB) {
            double comision = hotelController.calcularComision("Hotel B", habitacion.getID());
            System.out.println("ID: " + habitacion.getID() + ", Comisión: $" + comision);
        }

        // Imprimir salarios de cada recamarera
        System.out.println("\nSalarios calculados de las recamareras:");
        for (RecamareraBean recamarera : recamareras) {
            double salario = empleadoController.calcularSalarioDeRecamarera(recamarera.getNombre());
            System.out.println("Nombre: " + recamarera.getNombre() + ", Salario: $" + salario);
        }
    }
}
