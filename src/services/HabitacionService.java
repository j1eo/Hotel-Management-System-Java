package services;

import beans.HabitacionBean;

public class HabitacionService {
    public double calcularComision(HabitacionBean habitacion) {
        switch (habitacion.getTipo().toLowerCase()) {
            case "sencilla":
                return 50.0;
            case "doble":
                return 100.0;
            case "penhouse":
                return 200.0;
            default:
                return 0.0;
        }
    }
    
    
}
