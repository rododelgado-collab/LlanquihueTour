package utils;

/**
 * Excepción personalizada que se lanza cuando un RUT chileno
 * no cumple con el formato esperado o su dígito verificador
 * es incorrecto según el algoritmo módulo 11.
 *
 * @author rodo.delgado
 * @version 1.0
 * @see model.Rut
 */
public class RutInvalidoException extends Exception {

    /**
     * Crea la excepción con un mensaje descriptivo del error.
     *
     * @param mensaje detalle del motivo por el cual el RUT es inválido
     */
    public RutInvalidoException(String mensaje) {
        super(mensaje);
    }
}
