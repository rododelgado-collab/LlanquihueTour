package model;

/**
 * Guía turístico que trabaja con la agencia. Hereda de Persona
 * y agrega especialidad, idiomas y tarifa por día.
 *
 * @author rodo.delgado
 * @version 1.0
 */
public class GuiaTuristico extends Persona {

    // Atributos
    private String especialidad;
    private String idiomas;
    private double tarifaPorDia;

    /**
     * Crea un guía turístico con todos sus datos.
     *
     * @param rut          RUT validado del guía
     * @param nombre       nombre de pila
     * @param apellido     apellido
     * @param email        correo electrónico
     * @param telefono     teléfono de contacto
     * @param direccion    dirección física
     * @param especialidad área de especialización del guía
     * @param idiomas      idiomas que domina
     * @param tarifaPorDia tarifa diaria en pesos
     */
    public GuiaTuristico(Rut rut, String nombre, String apellido, String email,
                         String telefono, Direccion direccion,
                         String especialidad, String idiomas, double tarifaPorDia) {
        super(rut, nombre, apellido, email, telefono, direccion);
        this.especialidad = especialidad;
        this.idiomas = idiomas;
        this.tarifaPorDia = tarifaPorDia;
    }

    // Metodos Get & Set
    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public String getIdiomas() {
        return idiomas;
    }

    public void setIdiomas(String idiomas) {
        this.idiomas = idiomas;
    }

    public double getTarifaPorDia() {
        return tarifaPorDia;
    }

    public void setTarifaPorDia(double tarifaPorDia) {
        this.tarifaPorDia = tarifaPorDia;
    }

    /**
     * Muestra por consola los datos del guía con su etiqueta de tipo.
     */
    @Override
    public void mostrarDatos() {
        System.out.println("[GUIA] " + this);
    }

    /**
     * Representación en texto del guía, incluyendo especialidad y tarifa.
     *
     * @return datos de la persona más especialidad, idiomas y tarifa
     */
    @Override
    public String toString() {
        return super.toString() + " | Especialidad: " + especialidad
                + " | Idiomas: " + idiomas
                + " | Tarifa/día: $" + String.format("%,.0f", tarifaPorDia);
    }
}
