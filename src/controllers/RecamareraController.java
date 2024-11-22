package controllers;

import beans.HabitacionBean;
import beans.RecamareraBean;
import services.RecamareraService;

import java.util.ArrayList;

public class RecamareraController {

    private final RecamareraService recamareraService;

    public RecamareraController() {
        this.recamareraService = new RecamareraService();
    }

    // Asignar habitación a una recamarera
    public void asignarHabitacion(RecamareraBean recamarera, HabitacionBean habitacion) {
        recamareraService.asignarHabitacion(recamarera, habitacion);
    }

    // Calcular el salario total de una recamarera
    public double calcularSalario(RecamareraBean recamarera, ArrayList<HabitacionBean> habitaciones, int estrellasHotel) {
        return recamareraService.calcularSalario(recamarera, habitaciones, estrellasHotel);
    }

    // Listar habitaciones asignadas a una recamarera
    public ArrayList<HabitacionBean> listarHabitacionesAsignadas(RecamareraBean recamarera) {
        return recamareraService.listarHabitacionesAsignadas(recamarera);
    }
    
    public HabitacionBean buscarHabitacionAsignadaPorID(RecamareraBean recamarera, String habitacionID) {
        return recamareraService.buscarHabitacionAsignadaPorID(recamarera, habitacionID);
    }
}
