package model;

/**
 * Actividad incluida dentro de un paquete turístico.
 * Composición: un PaqueteTuristico contiene varias Actividad.
 *
 * @author rodo.delgado
 * @version 1.0
 */
public class Actividad {

    // Atributos
    private String nombre;
    private String horario;
    private double precio;

    /**
     * Crea una actividad con todos sus datos.
     *
     * @param nombre  nombre descriptivo de la actividad
     * @param horario horario de inicio (formato HH:mm)
     * @param precio  precio por persona en pesos
     */
    public Actividad(String nombre, String horario, double precio) {
        this.nombre = nombre;
        this.horario = horario;
        this.precio = precio;
    }

    // Metodos Get & Set
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    /**
     * Representación en texto de la actividad.
     *
     * @return cadena con nombre, horario y precio formateado
     */
    @Override
    public String toString() {
        return nombre + " (" + horario + ") - $" + String.format("%,.0f", precio);
    }
}
