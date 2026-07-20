package model;

/**
 * Cliente de la agencia de turismo. Hereda de Persona y agrega
 * un sistema simple de puntos de fidelidad.
 *
 * @author rodo.delgado
 * @version 1.0
 */
public class Cliente extends Persona {

    // Atributos
    private int puntosFidelidad;

    /**
     * Crea un cliente con puntos de fidelidad iniciales.
     *
     * @param rut             RUT validado del cliente
     * @param nombre          nombre de pila
     * @param apellido        apellido
     * @param email           correo electrónico
     * @param telefono        teléfono de contacto
     * @param direccion       dirección física
     * @param puntosFidelidad puntos acumulados iniciales
     */
    public Cliente(Rut rut, String nombre, String apellido, String email,
                   String telefono, Direccion direccion, int puntosFidelidad) {
        super(rut, nombre, apellido, email, telefono, direccion);
        this.puntosFidelidad = puntosFidelidad;
    }

    /**
     * Constructor sobrecargado: crea un cliente nuevo que parte con 0 puntos.
     *
     * @param rut       RUT validado del cliente
     * @param nombre    nombre de pila
     * @param apellido  apellido
     * @param email     correo electrónico
     * @param telefono  teléfono de contacto
     * @param direccion dirección física
     */
    public Cliente(Rut rut, String nombre, String apellido, String email,
                   String telefono, Direccion direccion) {
        this(rut, nombre, apellido, email, telefono, direccion, 0);
    }

    // Metodos Get & Set
    public int getPuntosFidelidad() {
        return puntosFidelidad;
    }

    public void setPuntosFidelidad(int puntosFidelidad) {
        this.puntosFidelidad = puntosFidelidad;
    }

    /**
     * Suma puntos de fidelidad al cliente según el monto de una compra
     * (1 punto por cada $1.000 gastados).
     *
     * @param montoCompra monto total de la compra en pesos
     */
    public void acumularPuntos(double montoCompra) {
        this.puntosFidelidad += (int) (montoCompra / 1000);
    }

    /**
     * Muestra por consola los datos del cliente con su etiqueta de tipo.
     */
    @Override
    public void mostrarDatos() {
        System.out.println("[CLIENTE] " + this);
    }

    /**
     * Representación en texto del cliente, incluyendo sus puntos.
     *
     * @return datos de la persona más los puntos de fidelidad
     */
    @Override
    public String toString() {
        return super.toString() + " | Puntos fidelidad: " + puntosFidelidad;
    }
}
