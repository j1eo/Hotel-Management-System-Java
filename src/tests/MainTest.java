package tests;

import beans.*;
import controllers.*;
import java.util.ArrayList;

public class MainTest {
    public static void main(String[] args) {

        // Crear instancias de controladores
        HotelController hotelController = new HotelController();
        EmpleadoController empleadoController = new EmpleadoController();
        RecamareraController recamareraController = new RecamareraController();

        // Crear hoteles y agregar habitaciones
        hotelController.agregarHotel("Hotel A", 4);
        hotelController.agregarHabitacion("Hotel A", "1", "Sencilla");
        hotelController.agregarHabitacion("Hotel A", "2", "Doble");
        hotelController.agregarHabitacion("Hotel A", "3", "Penhouse");

        hotelController.agregarHotel("Hotel B", 5);
        hotelController.agregarHabitacion("Hotel B", "4", "Sencilla");
        hotelController.agregarHabitacion("Hotel B", "5", "Doble");
        hotelController.agregarHabitacion("Hotel B", "6", "Penhouse");

        // Crear empleados
        RecamareraAuxiliar recamarera1 = new RecamareraAuxiliar("Ana", 1500.0);
        RecamareraPrincipianteBean recamarera2 = new RecamareraPrincipianteBean("Juan", 1200);
        AmaDeLlavesBean amaDeLlaves = new AmaDeLlavesBean("Maria", 2000);

        empleadoController.agregarRecamarera(recamarera1);
        empleadoController.agregarRecamarera(recamarera2);
        empleadoController.agregarRecamarera(amaDeLlaves);

        // Asignar habitaciones a empleados
        ArrayList<HabitacionBean> habitacionesHotelSol = hotelController.listarHabitacionesDeHotel("Hotel A");
        ArrayList<HabitacionBean> habitacionesHotelLuna = hotelController.listarHabitacionesDeHotel("Hotel B");

        // Asignaciones en Hotel Sol
        recamareraController.asignarHabitacion(recamarera1, habitacionesHotelSol.get(0)); // Sencilla
        recamareraController.asignarHabitacion(recamarera2, habitacionesHotelSol.get(1)); // Doble
        recamareraController.asignarHabitacion(amaDeLlaves, habitacionesHotelSol.get(2)); // Penhouse

        // Asignaciones en Hotel Luna
        recamareraController.asignarHabitacion(recamarera1, habitacionesHotelLuna.get(0)); // Sencilla
        recamareraController.asignarHabitacion(recamarera2, habitacionesHotelLuna.get(1)); // Doble
        recamareraController.asignarHabitacion(amaDeLlaves, habitacionesHotelLuna.get(2)); // Penhouse

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

        // Imprimir detalles de las recamareras
        System.out.println("\nRecamareras registradas:");
        ArrayList<RecamareraBean> recamareras = empleadoController.listarRecamareras();
        for (RecamareraBean recamarera : recamareras) {
            System.out.println("Nombre: " + recamarera.getNombre() + ", Nivel: " + recamarera.getNivelExperiencia());
            System.out.println("Habitaciones asignadas:");
            for (HabitacionBean habitacion : recamarera.getHabitacionesAsignadas()) {
                System.out.println("  ID: " + habitacion.getID() + "  Tipo: " + habitacion.getTipo());
            }
        }

        // Imprimir comisiones por habitación de Hotel Sol
        System.out.println("\nComisiones por habitación del hotel 'Hotel A':");
        for (HabitacionBean habitacion : habitacionesHotelSol) {
            double comision = hotelController.calcularComision("Hotel A", habitacion.getID());
            System.out.println("ID: " + habitacion.getID() + ", Comision: $" + comision);
        }

        // Imprimir comisiones por habitación de Hotel Luna
        System.out.println("\nComisiones por habitación del hotel 'Hotel B':");
        for (HabitacionBean habitacion : habitacionesHotelLuna) {
            double comision = hotelController.calcularComision("Hotel B", habitacion.getID());
            System.out.println("ID: " + habitacion.getID() + ", Comision: $" + comision);
        }

        // Imprimir salarios de cada recamarera
        System.out.println("\nSalarios calculados de las recamareras:");
        for (RecamareraBean recamarera : recamareras) {
            double salario = empleadoController.calcularSalarioDeRecamarera(recamarera.getNombre());
            System.out.println("Nombre: " + recamarera.getNombre() + ", Salario: $" + salario);
        }
    }
}
