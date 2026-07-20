package model;

import utils.RutInvalidoException;

/**
 * Representa un RUT chileno con su número y dígito verificador.
 * Valida el dígito verificador mediante el algoritmo módulo 11
 * y lanza una excepción personalizada si el RUT es inválido.
 *
 * Se utiliza mediante composición dentro de Persona.
 *
 * @author rodo.delgado
 * @version 1.0
 */
public class Rut {

    // Atributos
    private int numero;
    private char digitoVerificador;

    /**
     * Construye un RUT a partir de un texto con formato "12345678-5".
     *
     * @param rutTexto RUT en formato número-dígito verificador
     * @throws RutInvalidoException si el formato o el dígito verificador es incorrecto
     */
    public Rut(String rutTexto) throws RutInvalidoException {
        if (rutTexto == null || !rutTexto.matches("\\d{7,8}-[\\dkK]")) {
            throw new RutInvalidoException("Formato de RUT inválido: " + rutTexto
                    + " (se espera por ejemplo 12345678-5)");
        }
        String[] partes = rutTexto.split("-");
        this.numero = Integer.parseInt(partes[0]);
        this.digitoVerificador = Character.toUpperCase(partes[1].charAt(0));

        char dvCalculado = calcularDigitoVerificador(this.numero);
        if (dvCalculado != this.digitoVerificador) {
            throw new RutInvalidoException("El dígito verificador del RUT " + rutTexto
                    + " es incorrecto (se esperaba " + dvCalculado + ")");
        }
    }

    /**
     * Calcula el dígito verificador de un número de RUT usando módulo 11.
     *
     * @param numeroRut parte numérica del RUT
     * @return el dígito verificador esperado ('0'-'9' o 'K')
     */
    private char calcularDigitoVerificador(int numeroRut) {
        int suma = 0;
        int factor = 2;
        while (numeroRut > 0) {
            suma += (numeroRut % 10) * factor;
            numeroRut /= 10;
            factor = (factor == 7) ? 2 : factor + 1;
        }
        int resto = 11 - (suma % 11);
        if (resto == 11) {
            return '0';
        }
        if (resto == 10) {
            return 'K';
        }
        return Character.forDigit(resto, 10);
    }

    // Metodos Get & Set
    public int getNumero() {
        return numero;
    }

    public char getDigitoVerificador() {
        return digitoVerificador;
    }

    /**
     * Devuelve el RUT formateado con puntos y guion.
     *
     * @return RUT con formato, por ejemplo 12.345.678-5
     */
    public String formateado() {
        return String.format("%,d", numero).replace(',', '.') + "-" + digitoVerificador;
    }

    /**
     * Representación en texto del RUT.
     *
     * @return el RUT formateado con puntos y guion
     */
    @Override
    public String toString() {
        return formateado();
    }
}
