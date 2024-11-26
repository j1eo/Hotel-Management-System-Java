package services;

import beans.EmpleadoBean;
import beans.GerenteBean;
import beans.HabitacionBean;
import beans.HotelBean;
import utils.HotelSearchType;


import java.util.ArrayList;

import javax.swing.JOptionPane;

public class HotelService {

    private ArrayList<HotelBean> hoteles;

    public HotelService() {
        this.hoteles = new ArrayList<>();
    }

    public void agregarHotel(String nombre, int estrellas) {
        // Crear el hotel
        HotelBean hotel = new HotelBean(nombre, estrellas);

        // Agregar habitaciones
        for (int i = 1; i <= 20; i++) {
            HabitacionBean habitacion = new HabitacionBean("S-" + i, "sencilla");
            hotel.agregarHabitacion(habitacion); // Asignar habitación al hotel
        }
        for (int i = 1; i <= 15; i++) {
            HabitacionBean habitacion = new HabitacionBean("D-" + i, "doble");
            hotel.agregarHabitacion(habitacion); // Asignar habitación al hotel
        }
        for (int i = 1; i <= 5; i++) {
            HabitacionBean habitacion = new HabitacionBean("P-" + i, "penhouse");
            hotel.agregarHabitacion(habitacion); // Asignar habitación al hotel
        }

        // Falta Agregar Nombre a gerentes
        hotel.getEmpleados().add(new GerenteBean("Nombre Gerente 1", 0));
        hotel.getEmpleados().add(new GerenteBean("Nombre Gerente 2", 0));

        // Agregar el hotel a la lista de hoteles
        hoteles.add(hotel);
    }

    public ArrayList<HotelBean> listarHoteles() {
        return new ArrayList<>(hoteles);
    }

    public void agregarHabitacion(String nombreHotel, HabitacionBean habitacion) {
        for (HotelBean hotel : hoteles) {
            if (hotel.getNombre().equalsIgnoreCase(nombreHotel)) {
                hotel.agregarHabitacion(habitacion);
                return;
            }
        }
        throw new IllegalArgumentException("Hotel no encontrado: " + nombreHotel);
    }

    public ArrayList<HabitacionBean> listarHabitacionesDeHotel(String nombreHotel) {
        for (HotelBean hotel : hoteles) {
            if (hotel.getNombre().equalsIgnoreCase(nombreHotel)) {
                return hotel.getHabitaciones();
            }
        }
        throw new IllegalArgumentException("Hotel no encontrado: " + nombreHotel);
    }

    public double calcularCostoPorDia(String nombreHotel, String habitacionID) {
        HotelBean hotel = buscarHotelPorNombre(nombreHotel);
        HabitacionBean habitacion = buscarHabitacionEnHotel(hotel, habitacionID);

        return calcularCostoPorDiaHabitacion(habitacion, hotel.getEstrellas());
    }

    public double calcularComision(String nombreHotel, String habitacionID) {
        HotelBean hotel = buscarHotelPorNombre(nombreHotel);
        HabitacionBean habitacion = buscarHabitacionEnHotel(hotel, habitacionID);

        return calcularComisionHabitacion(habitacion, hotel.getEstrellas());
    }

    public HabitacionBean buscarHabitacionPorID(String nombreHotel, String habitacionID) {
        HotelBean hotel = buscarHotelPorNombre(nombreHotel);
        return buscarHabitacionEnHotel(hotel, habitacionID);
    }

    public HotelBean buscarHotelPorNombre(String nombreHotel) {
        for (HotelBean hotel : hoteles) {
            if (hotel.getNombre().equalsIgnoreCase(nombreHotel)) {
                return hotel;
            }
        }
        throw new IllegalArgumentException("Hotel no encontrado: " + nombreHotel);
    }

    private HabitacionBean buscarHabitacionEnHotel(HotelBean hotel, String habitacionID) {
        for (HabitacionBean habitacion : hotel.getHabitaciones()) {
            if (habitacion.getID().equalsIgnoreCase(habitacionID)) {
                return habitacion;
            }
        }
        throw new IllegalArgumentException("Habitación no encontrada en el hotel: " + habitacionID);
    }

    private double calcularCostoPorDiaHabitacion(HabitacionBean habitacion, int estrellasHotel) {
        double baseCosto;
        switch (habitacion.getTipo().toLowerCase()) {
            case "sencilla":
                baseCosto = 300;
                break;
            case "doble":
                baseCosto = 700;
                break;
            case "penhouse":
                baseCosto = 1200;
                break;
            default:
                throw new IllegalArgumentException("Tipo de habitación inválido.");
        }
        switch (estrellasHotel) {
            case 3:
                return baseCosto;
            case 4:
                return baseCosto * 1.2;
            case 5:
                return baseCosto * 1.6;
            default:
                throw new IllegalArgumentException("Número de estrellas inválido.");
        }
    }

    private double calcularComisionHabitacion(HabitacionBean habitacion, int estrellasHotel) {
        switch (habitacion.getTipo().toLowerCase()) {
            case "sencilla":
                return estrellasHotel == 3 ? 30 : estrellasHotel == 4 ? 50 : 70;
            case "doble":
                return estrellasHotel == 3 ? 40 : estrellasHotel == 4 ? 60 : 80;
            case "penhouse":
                return estrellasHotel == 3 ? 60 : estrellasHotel == 4 ? 80 : 100;
            default:
                throw new IllegalArgumentException("Tipo de habitación inválido.");
        }
    }
    
    private HotelBean findHotel(String query, HotelSearchType searchType) {
        for (HotelBean hotel : hoteles) {
            if ((searchType == HotelSearchType.NOMBRE && hotel.getNombre().equalsIgnoreCase(query)) ||
                (searchType == HotelSearchType.ESTRELLAS && String.valueOf(hotel.getEstrellas()).equals(query))) {
                return hotel;
            }
        }
        return null;
    }
    
    
    
    private void handleFind(String query, HotelSearchType searchType) {
        try {
            HotelBean foundHotel = findHotel(query, searchType);

            if (foundHotel == null) {
                JOptionPane.showMessageDialog(null, "Hotel no encontrado.");
                return;
            }

            System.out.println("Nombre: " + foundHotel.getNombre() + ", Estrellas: " + foundHotel.getEstrellas());
            System.out.println("Habitaciones:");
            for (HabitacionBean habitacion : foundHotel.getHabitaciones()) {
                System.out.println("  ID: " + habitacion.getID() + ", Tipo: " + habitacion.getTipo());
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Ocurrió un error al buscar el hotel: " + e.getMessage());
        }
    }
}
