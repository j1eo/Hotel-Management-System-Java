package services;

import beans.RecamareraBean;

import java.util.ArrayList;


public class EmpleadoService {

    private ArrayList<RecamareraBean> recamareras;

    public EmpleadoService() {
        this.recamareras = new ArrayList<>();
    }

    // Agregar una nueva recamarera
    public void agregarRecamarera(RecamareraBean recamarera) {
        this.recamareras.add(recamarera);
    }

    // Listar todas las recamareras
    public ArrayList<RecamareraBean> listarRecamareras() {
        return new ArrayList<>(recamareras);
    }

  

    // Calcular el salario total de una recamarera
    public double calcularSalarioDeRecamarera(String nombre) {
        for (RecamareraBean recamarera : recamareras) {
            if (recamarera.getNombre().equalsIgnoreCase(nombre)) {
                return recamarera.calcularSalario();
            }
        }
        throw new IllegalArgumentException("Recamarera no encontrada: " + nombre);
    }
}
