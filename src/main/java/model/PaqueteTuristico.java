package model;

import interfaces.Registrable;
import java.util.ArrayList;
import java.util.List;

/**
 * Paquete turístico ofrecido por la agencia. Contiene una lista de
 * actividades (composición) y un guía responsable asignado.
 * Implementa Registrable para participar en listas polimórficas.
 *
 * @author rodo.delgado
 * @version 1.0
 */
public class PaqueteTuristico implements Registrable {

    // Atributos
    private String codigo;
    private String nombre;
    private String destino;
    private int cuposDisponibles;
    private GuiaTuristico guiaAsignado;
    private List<Actividad> actividades;

    /**
     * Crea un paquete turístico sin actividades (se agregan después).
     *
     * @param codigo           código único del paquete
     * @param nombre           nombre comercial
     * @param destino          destino o zona geográfica
     * @param cuposDisponibles cupos iniciales disponibles
     * @param guiaAsignado     guía responsable (puede ser null si no hay asignado)
     */
    public PaqueteTuristico(String codigo, String nombre, String destino,
                            int cuposDisponibles, GuiaTuristico guiaAsignado) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.destino = destino;
        this.cuposDisponibles = cuposDisponibles;
        this.guiaAsignado = guiaAsignado;
        this.actividades = new ArrayList<>();
    }

    // Metodos Get & Set
    public String getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public int getCuposDisponibles() {
        return cuposDisponibles;
    }

    public GuiaTuristico getGuiaAsignado() {
        return guiaAsignado;
    }

    public void setGuiaAsignado(GuiaTuristico guiaAsignado) {
        this.guiaAsignado = guiaAsignado;
    }

    public List<Actividad> getActividades() {
        return actividades;
    }

    /**
     * Agrega una actividad al itinerario del paquete.
     *
     * @param actividad actividad a incluir en el itinerario
     */
    public void agregarActividad(Actividad actividad) {
        actividades.add(actividad);
    }

    /**
     * Calcula el precio total del paquete por persona.
     *
     * @return suma de los precios de todas sus actividades
     */
    public double getPrecioTotal() {
        double total = 0;
        for (Actividad actividad : actividades) {
            total += actividad.getPrecio();
        }
        return total;
    }

    /**
     * Descuenta cupos al confirmar una reserva.
     *
     * @param cantidad número de cupos a reservar
     * @return true si había cupos suficientes, false en caso contrario
     */
    public boolean reservarCupos(int cantidad) {
        if (cantidad <= 0 || cantidad > cuposDisponibles) {
            return false;
        }
        cuposDisponibles -= cantidad;
        return true;
    }

    /**
     * Devuelve cupos al paquete cuando se cancela una reserva.
     *
     * @param cantidad número de cupos a restituir
     */
    public void liberarCupos(int cantidad) {
        if (cantidad > 0) {
            cuposDisponibles += cantidad;
        }
    }

    /**
     * Registra el paquete en el catálogo mostrando un mensaje por consola.
     */
    @Override
    public void registrar() {
        System.out.println(">> Paquete " + codigo + " - " + nombre + " registrado en el catalogo.");
    }

    /**
     * Muestra por consola los datos del paquete y su itinerario completo.
     */
    @Override
    public void mostrarDatos() {
        System.out.println("[PAQUETE] " + this);
        for (Actividad actividad : actividades) {
            System.out.println("     * " + actividad);
        }
    }

    /**
     * Representación en texto del paquete (sin el detalle de actividades).
     *
     * @return cadena con código, nombre, destino, cupos, guía y precio
     */
    @Override
    public String toString() {
        String nombreGuia = (guiaAsignado != null) ? guiaAsignado.getNombreCompleto() : "Sin asignar";
        return codigo + " | " + nombre + " | Destino: " + destino
                + " | Cupos: " + cuposDisponibles
                + " | Guia: " + nombreGuia
                + " | Precio p/p: $" + String.format("%,.0f", getPrecioTotal());
    }
}
