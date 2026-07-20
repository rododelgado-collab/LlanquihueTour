package app;

import interfaces.Registrable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import model.Cliente;
import model.GuiaTuristico;
import model.PaqueteTuristico;
import model.Proveedor;
import model.Reserva;

/**
 * Clase central que administra las colecciones del sistema:
 * 
 * ArrayList para clientes, guías y proveedores.
 * HashMap para el catálogo de paquetes (búsqueda por código).
 * Stack para el historial de reservas (la última queda arriba).
 * 
 *
 * @author rodo.delgado
 * @version 1.0
 */
public class AgenciaTour {

    // Atributos
    private final List<Cliente> clientes = new ArrayList<>();
    private final List<GuiaTuristico> guias = new ArrayList<>();
    private final List<Proveedor> proveedores = new ArrayList<>();
    private final Map<String, PaqueteTuristico> paquetes = new HashMap<>();
    private final Stack<Reserva> historialReservas = new Stack<>();

    /**
     * Crea la agencia con todas sus colecciones vacías;
     * los datos se cargan luego desde los archivos .txt.
     */
    public AgenciaTour() {
    }

    // ------------------- Registro de entidades -------------------

    /**
     * Registra un cliente en el sistema y notifica por consola.
     *
     * @param cliente cliente a registrar
     */
    public void registrarCliente(Cliente cliente) {
        clientes.add(cliente);
        cliente.registrar();
    }

    /**
     * Registra un guía turístico en el sistema.
     *
     * @param guia guía a registrar
     */
    public void registrarGuia(GuiaTuristico guia) {
        guias.add(guia);
    }

    /**
     * Registra un proveedor en el sistema.
     *
     * @param proveedor proveedor a registrar
     */
    public void registrarProveedor(Proveedor proveedor) {
        proveedores.add(proveedor);
    }

    /**
     * Agrega un paquete al catálogo, usando su código como clave.
     *
     * @param paquete paquete turístico a registrar
     */
    public void registrarPaquete(PaqueteTuristico paquete) {
        paquetes.put(paquete.getCodigo(), paquete);
    }

    // ------------------- Búsquedas y filtros -------------------

    /**
     * Busca un cliente por su RUT en formato "12345678-5".
     *
     * @param rutTexto RUT del cliente en formato número-dígito verificador
     * @return el cliente encontrado, o null si no existe
     */
    public Cliente buscarClientePorRut(String rutTexto) {
        for (Cliente cliente : clientes) {
            String rutPlano = cliente.getRut().getNumero() + "-"
                    + cliente.getRut().getDigitoVerificador();
            if (rutPlano.equalsIgnoreCase(rutTexto)) {
                return cliente;
            }
        }
        return null;
    }

    /**
     * Busca un paquete por su código exacto (uso del HashMap).
     *
     * @param codigo código del paquete (ej: PKG001)
     * @return el paquete encontrado, o null si no existe
     */
    public PaqueteTuristico buscarPaquete(String codigo) {
        return paquetes.get(codigo.toUpperCase());
    }

    /**
     * Sobrecarga de {@link #buscarPaquete(String)}: filtra los paquetes
     * cuyo precio por persona no supere el máximo indicado.
     *
     * @param precioMaximo precio máximo por persona en pesos
     * @return lista con los paquetes que cumplen el filtro (puede estar vacía)
     */
    public List<PaqueteTuristico> buscarPaquete(double precioMaximo) {
        List<PaqueteTuristico> resultado = new ArrayList<>();
        for (PaqueteTuristico paquete : paquetes.values()) {
            if (paquete.getPrecioTotal() <= precioMaximo) {
                resultado.add(paquete);
            }
        }
        return resultado;
    }

    // ------------------- Reservas -------------------

    /**
     * Crea una reserva validando cupos disponibles. Si se concreta,
     * el cliente acumula puntos de fidelidad y la reserva se apila
     * en el historial.
     *
     * @param cliente  cliente que reserva
     * @param paquete  paquete turístico a reservar
     * @param personas cantidad de personas de la reserva
     * @return la reserva creada, o null si no había cupos suficientes
     */
    public Reserva crearReserva(Cliente cliente, PaqueteTuristico paquete, int personas) {
        if (!paquete.reservarCupos(personas)) {
            return null;
        }
        Reserva reserva = new Reserva(cliente, paquete, personas);
        cliente.acumularPuntos(reserva.getMontoTotal());
        historialReservas.push(reserva);
        return reserva;
    }

    /**
     * Cancela la última reserva realizada (comportamiento LIFO de la pila):
     * la desapila con pop() y devuelve los cupos al paquete correspondiente.
     *
     * @return la reserva cancelada, o null si el historial estaba vacío
     */
    public Reserva cancelarUltimaReserva() {
        if (historialReservas.isEmpty()) {
            return null;
        }
        Reserva cancelada = historialReservas.pop();
        cancelada.getPaquete().liberarCupos(cancelada.getCantidadPersonas());
        return cancelada;
    }

    // ------------------- Estadísticas de ventas -------------------

    /**
     * Calcula el total recaudado sumando los montos de todas
     * las reservas vigentes del historial.
     *
     * @return total recaudado en pesos
     */
    public double calcularTotalVentas() {
        double total = 0;
        for (Reserva reserva : historialReservas) {
            total += reserva.getMontoTotal();
        }
        return total;
    }

    /**
     * Determina el paquete con más reservas vigentes, contando
     * ocurrencias en el historial mediante un HashMap auxiliar.
     *
     * @return el paquete más reservado, o null si no hay reservas
     */
    public PaqueteTuristico obtenerPaqueteMasReservado() {
        Map<String, Integer> conteo = new HashMap<>();
        for (Reserva reserva : historialReservas) {
            String codigo = reserva.getPaquete().getCodigo();
            conteo.put(codigo, conteo.getOrDefault(codigo, 0) + 1);
        }
        PaqueteTuristico masReservado = null;
        int maximo = 0;
        for (Map.Entry<String, Integer> entrada : conteo.entrySet()) {
            if (entrada.getValue() > maximo) {
                maximo = entrada.getValue();
                masReservado = paquetes.get(entrada.getKey());
            }
        }
        return masReservado;
    }

    // Metodos Get & Set
    public List<Cliente> getClientes() {
        return clientes;
    }

    public List<GuiaTuristico> getGuias() {
        return guias;
    }

    public List<Proveedor> getProveedores() {
        return proveedores;
    }

    public Map<String, PaqueteTuristico> getPaquetes() {
        return paquetes;
    }

    public Stack<Reserva> getHistorialReservas() {
        return historialReservas;
    }

    /**
     * Construye una lista polimórfica con todas las entidades
     * registrables del sistema (personas y paquetes).
     *
     * @return lista polimórfica de tipo Registrable
     */
    public List<Registrable> obtenerRegistrables() {
        List<Registrable> registrables = new ArrayList<>();
        registrables.addAll(clientes);
        registrables.addAll(guias);
        registrables.addAll(proveedores);
        registrables.addAll(paquetes.values());
        return registrables;
    }
}
