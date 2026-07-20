package model;

/**
 * Representa una dirección física de una persona.
 * Se utiliza mediante composición dentro de Persona.
 *
 * @author rodo.delgado
 * @version 1.0
 */
public class Direccion {

    // Atributos
    private String calle;
    private String numero;
    private String comuna;
    private String region;

    /**
     * Crea una dirección con todos sus datos.
     *
     * @param calle  nombre de la calle o avenida
     * @param numero numeración de la dirección
     * @param comuna comuna donde se ubica
     * @param region región donde se ubica
     */
    public Direccion(String calle, String numero, String comuna, String region) {
        this.calle = calle;
        this.numero = numero;
        this.comuna = comuna;
        this.region = region;
    }

    // Metodos Get & Set
    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getComuna() {
        return comuna;
    }

    public void setComuna(String comuna) {
        this.comuna = comuna;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    /**
     * Representación en texto de la dirección completa.
     *
     * @return dirección con formato "calle numero, comuna, región"
     */
    @Override
    public String toString() {
        return calle + " " + numero + ", " + comuna + ", " + region;
    }
}
