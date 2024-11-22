public class Habitacion {
    private String tipo;
    private int nivelHotel;
    private double costoPorDia;

    public Habitacion(String tipo, int nivelHotel, double costoPorDia) {
        this.tipo = tipo;
        this.nivelHotel = nivelHotel;
        this.costoPorDia = costoPorDia;
    }

    public String getTipo() {
        return tipo;
    }

    public double getCostoPorDia() {
        return costoPorDia;
    }
}
