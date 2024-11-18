package tests;

import beans.*;

import java.util.ArrayList;

public class MainTest {
	public static void main(String[] args) {

		// Crear un hotel con nombre y estrellas
		HotelBean hotel = new HotelBean("Hotel Maravilla", 4);

		// Crear algunas habitaciones
		HabitacionBean habitacion1 = new HabitacionBean("Sencilla", 100.0, 4);
		HabitacionBean habitacion2 = new HabitacionBean("Doble", 150.0, 4);
		HabitacionBean habitacion3 = new HabitacionBean("Penhouse", 300.0, 4);

		// Agregar las habitaciones al hotel
		hotel.agregarHabitacion(habitacion1);
		hotel.agregarHabitacion(habitacion2);
		hotel.agregarHabitacion(habitacion3);

		// Crear algunas recamareras con diferentes niveles de experiencia
		ArrayList<HabitacionBean> habitacionesRecamarera1 = new ArrayList<>();
		habitacionesRecamarera1.add(habitacion1);
		habitacionesRecamarera1.add(habitacion2);

		RecamareraAuxiliar recamarera1 = new RecamareraAuxiliar("Ana", 1200, habitacionesRecamarera1);

		ArrayList<HabitacionBean> habitacionesRecamarera2 = new ArrayList<>();
		habitacionesRecamarera2.add(habitacion3);

		RecamareraExperimentadaBean recamarera2 = new RecamareraExperimentadaBean("María", 1500,
				habitacionesRecamarera2);

		// Crear un AmaDeLlaves con todas las habitaciones
		ArrayList<HabitacionBean> habitacionesAmaDeLlaves = new ArrayList<>(hotel.getHabitaciones());
		AmaDeLlavesBean amaDeLlaves = new AmaDeLlavesBean("Laura", 1800.0, habitacionesAmaDeLlaves);

		// Imprimir detalles del hotel
		System.out.println("Hotel: " + hotel.getNombre());
		System.out.println("Estrellas: " + hotel.getEstrellas());
		System.out.println("Habitaciones: ");
		for (HabitacionBean habitacion : hotel.getHabitaciones()) {
			System.out.println("  Tipo: " + habitacion.getTipo() + ", Costo por día: " + habitacion.getCostoPorDia()
					+ ", Comisión: " + habitacion.getComisionPorHabitacion());
		}

		// Imprimir detalles de las recamareras
		System.out
				.println("\nRecamarera: " + recamarera1.getNombre() + ", Nivel: " + recamarera1.getNivelExperiencia());
		System.out.println("  Habitaciones asignadas: ");
		for (HabitacionBean habitacion : recamarera1.getTotalHabitaciones()) { // Aquí se itera sobre la lista de
																				// habitaciones
			System.out.println("    Tipo: " + habitacion.getTipo());
		}

		System.out
				.println("\nRecamarera: " + recamarera2.getNombre() + ", Nivel: " + recamarera2.getNivelExperiencia());
		System.out.println("  Habitaciones asignadas: ");
		for (HabitacionBean habitacion : recamarera2.getTotalHabitaciones()) {
			System.out.println("    Tipo: " + habitacion.getTipo());
		}

		System.out.println("\nAma De Llaves: " + amaDeLlaves.getNombre());
		System.out.println("  Habitaciones asignadas: ");
		for (HabitacionBean habitacion : amaDeLlaves.getTotalHabitaciones()) {
			System.out.println("    Tipo: " + habitacion.getTipo());
		}

		// Imprimir comisiones para cada habitación
		System.out.println("\nComisiones por habitación:");
		for (HabitacionBean habitacion : hotel.getHabitaciones()) { // Iterando sobre las habitaciones del hotel
			System.out.println(
					"  Tipo: " + habitacion.getTipo() + ", Comisión: " + habitacion.getComisionPorHabitacion());
		}
	}
}
