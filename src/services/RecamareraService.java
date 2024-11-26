package services;

import beans.HabitacionBean;
import beans.RecamareraBean;

import java.util.ArrayList;

public class RecamareraService {

	public void asignarHabitacion(RecamareraBean recamarera, HabitacionBean habitacion) {
	    if (recamarera.puedeAtender(habitacion.getTipo())) {
	        if (!recamarera.getHabitacionesAsignadas().contains(habitacion)) {
	            recamarera.getHabitacionesAsignadas().add(habitacion);
	        } else {
	            throw new IllegalArgumentException("La habitación ya está asignada a esta recamarera.");
	        }
	    } else {
	        throw new IllegalArgumentException("La recamarera no puede atender este tipo de habitación.");
	    }
	}

    // Calcula el salario total de una recamarera
    public double calcularSalario(RecamareraBean recamarera, ArrayList<HabitacionBean> habitaciones, int estrellasHotel) {
        double salarioBase = recamarera.getSalarioBase();
        double totalComision = 0.0;

        for (HabitacionBean habitacion : habitaciones) {
            totalComision += calcularComision(habitacion, estrellasHotel);
        }

        return salarioBase + totalComision;
    }

    // Método auxiliar para calcular la comisión por habitación
    private double calcularComision(HabitacionBean habitacion, int estrellasHotel) {
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

    // Obtiene la lista de habitaciones asignadas a una recamarera
    public ArrayList<HabitacionBean> listarHabitacionesAsignadas(RecamareraBean recamarera) {
        return recamarera.getHabitacionesAsignadas();
    }
    
    public HabitacionBean buscarHabitacionAsignadaPorID(RecamareraBean recamarera, String habitacionID) {
        for (HabitacionBean habitacion : recamarera.getHabitacionesAsignadas()) {
            if (habitacion.getID().equalsIgnoreCase(habitacionID)) {
                return habitacion;
            }
        }
        throw new IllegalArgumentException("Habitación con ID " + habitacionID + " no asignada a la recamarera.");
    }

}
