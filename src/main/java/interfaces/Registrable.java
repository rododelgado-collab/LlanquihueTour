package interfaces;

/**
 * Interfaz común para todas las entidades que pueden registrarse
 * en el sistema de la agencia (personas y paquetes turísticos).
 * Permite trabajar con listas polimórficas.
 *
 * @author rodo.delgado
 * @version 1.0
 */
public interface Registrable {

    /**
     * Registra la entidad en el sistema (simulado con un mensaje por consola).
     */
    void registrar();

    /**
     * Muestra por consola los datos de la entidad.
     */
    void mostrarDatos();
}
