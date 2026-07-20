package model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Reserva de un paquete turístico realizada por un cliente.
 * Aplica composición: una Reserva contiene un Cliente
 * y un PaqueteTuristico.
 *
 * @author rodo.delgado
 * @version 1.0
 */
public class Reserva {

    // Atributos
    private static int correlativo = 1;
    private final String codigo;
    private final Cliente cliente;
    private final PaqueteTuristico paquete;
    private final LocalDate fecha;
    private final int cantidadPersonas;
    private final double montoTotal;

    /**
     * Crea una reserva calculando automáticamente su código,
     * fecha actual y monto total.
     *
     * @param cliente          cliente que reserva
     * @param paquete          paquete turístico reservado
     * @param cantidadPersonas número de personas de la reserva
     */
    public Reserva(Cliente cliente, PaqueteTuristico paquete, int cantidadPersonas) {
        this.codigo = String.format("RES-%04d", correlativo++);
        this.cliente = cliente;
        this.paquete = paquete;
        this.cantidadPersonas = cantidadPersonas;
        this.fecha = LocalDate.now();
        this.montoTotal = paquete.getPrecioTotal() * cantidadPersonas;
    }

    // Metodos Get & Set
    public String getCodigo() {
        return codigo;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public PaqueteTuristico getPaquete() {
        return paquete;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public int getCantidadPersonas() {
        return cantidadPersonas;
    }

    public double getMontoTotal() {
        return montoTotal;
    }

    /**
     * Representación en texto de la reserva completa.
     *
     * @return cadena con código, fecha, cliente, paquete, personas y total
     */
    @Override
    public String toString() {
        String fechaFormateada = fecha.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        return codigo + " | " + fechaFormateada
                + " | Cliente: " + cliente.getNombreCompleto()
                + " (" + cliente.getRut() + ")"
                + " | Paquete: " + paquete.getNombre()
                + " | Personas: " + cantidadPersonas
                + " | Total: $" + String.format("%,.0f", montoTotal);
    }
}
