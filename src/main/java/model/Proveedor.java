package model;

/**
 * Proveedor de servicios asociados a la agencia (transporte,
 * alojamiento, gastronomía, etc.). Hereda de Persona.
 *
 * @author rodo.delgado
 * @version 1.0
 */
public class Proveedor extends Persona {

    // Atributos
    private String tipoServicio;
    private String nombreEmpresa;

    /**
     * Crea un proveedor con todos sus datos.
     *
     * @param rut           RUT validado del contacto del proveedor
     * @param nombre        nombre de pila del contacto
     * @param apellido      apellido del contacto
     * @param email         correo electrónico
     * @param telefono      teléfono de contacto
     * @param direccion     dirección física
     * @param tipoServicio  tipo de servicio que presta
     * @param nombreEmpresa nombre de la empresa proveedora
     */
    public Proveedor(Rut rut, String nombre, String apellido, String email,
                     String telefono, Direccion direccion,
                     String tipoServicio, String nombreEmpresa) {
        super(rut, nombre, apellido, email, telefono, direccion);
        this.tipoServicio = tipoServicio;
        this.nombreEmpresa = nombreEmpresa;
    }

    // Metodos Get & Set
    public String getTipoServicio() {
        return tipoServicio;
    }

    public void setTipoServicio(String tipoServicio) {
        this.tipoServicio = tipoServicio;
    }

    public String getNombreEmpresa() {
        return nombreEmpresa;
    }

    public void setNombreEmpresa(String nombreEmpresa) {
        this.nombreEmpresa = nombreEmpresa;
    }

    /**
     * Muestra por consola los datos del proveedor con su etiqueta de tipo.
     */
    @Override
    public void mostrarDatos() {
        System.out.println("[PROVEEDOR] " + this);
    }

    /**
     * Representación en texto del proveedor, incluyendo servicio y empresa.
     *
     * @return datos de la persona más tipo de servicio y empresa
     */
    @Override
    public String toString() {
        return super.toString() + " | Servicio: " + tipoServicio
                + " | Empresa: " + nombreEmpresa;
    }
}
