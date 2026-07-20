package model;

import interfaces.Registrable;

/**
 * Clase base abstracta de la jerarquía de personas del sistema.
 * Aplica encapsulamiento (atributos privados) y composición
 * contiene un objeto Rut y un objeto Direccion.
 *
 * Jerarquía de herencia: Persona → Cliente,
 * GuiaTuristico, Proveedor.
 *
 * @author rodo.delgado
 * @version 1.0
 */
public abstract class Persona implements Registrable {

    // Atributos
    private Rut rut;
    private String nombre;
    private String apellido;
    private String email;
    private String telefono;
    private Direccion direccion;

    /**
     * Constructor protegido usado por las subclases.
     *
     * @param rut       RUT validado de la persona
     * @param nombre    nombre de pila
     * @param apellido  apellido
     * @param email     correo electrónico de contacto
     * @param telefono  teléfono de contacto
     * @param direccion dirección física
     */
    protected Persona(Rut rut, String nombre, String apellido,
                      String email, String telefono, Direccion direccion) {
        this.rut = rut;
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.telefono = telefono;
        this.direccion = direccion;
    }

    // Metodos Get & Set
    public Rut getRut() {
        return rut;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Direccion getDireccion() {
        return direccion;
    }

    public void setDireccion(Direccion direccion) {
        this.direccion = direccion;
    }

    /**
     * Nombre completo de la persona.
     *
     * @return nombre y apellido concatenados
     */
    public String getNombreCompleto() {
        return nombre + " " + apellido;
    }

    /**
     * Registra la persona en el sistema mostrando un mensaje por consola.
     * Implementación común heredada por todas las subclases.
     */
    @Override
    public void registrar() {
        System.out.println(">> " + getClass().getSimpleName() + " "
                + getNombreCompleto() + " registrado(a) en el sistema.");
    }

    /**
     * Representación en texto con los datos comunes de la persona.
     *
     * @return cadena con RUT, nombre, contacto y dirección
     */
    @Override
    public String toString() {
        return "RUT: " + rut + " | " + getNombreCompleto()
                + " | Email: " + email + " | Tel: " + telefono
                + " | Direccion: " + direccion;
    }
}
