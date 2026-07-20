package app;

import interfaces.Registrable;
import java.util.List;
import java.util.Scanner;
import model.Cliente;
import model.Direccion;
import model.GuiaTuristico;
import model.PaqueteTuristico;
import model.Proveedor;
import model.Reserva;
import model.Rut;
import utils.RutInvalidoException;

/**
 * Menú interactivo por consola del sistema. Concentra toda la
 * interacción con el usuario y delega la lógica de negocio
 * en {@link AgenciaTour} (separación de responsabilidades).
 *
 * @author rodo.delgado
 * @version 1.0
 */
public class MenuPrincipal {

    // Atributos
    private final AgenciaTour agencia;
    private final Scanner scanner;

    /**
     * Crea el menú asociado a una agencia ya cargada con datos.
     *
     * @param agencia agencia sobre la cual operará el menú
     */
    public MenuPrincipal(AgenciaTour agencia) {
        this.agencia = agencia;
        this.scanner = new Scanner(System.in);
    }

    /**
     * Ejecuta el ciclo del menú hasta que el usuario elija salir.
     */
    public void iniciar() {
        int opcion;
        do {
            mostrarOpciones();
            opcion = leerEntero("Seleccione una opcion: ");
            procesarOpcion(opcion);
        } while (opcion != 0);
        System.out.println("Gracias por usar el sistema de Llanquihue Tour!");
    }

    /**
     * Imprime por consola las opciones disponibles del menú principal.
     */
    private void mostrarOpciones() {
        System.out.println("=========================================");
        System.out.println("   LLANQUIHUE TOUR - SISTEMA DE GESTION  ");
        System.out.println("=========================================");
        System.out.println("1. Listar clientes");
        System.out.println("2. Listar guias turisticos");
        System.out.println("3. Listar proveedores");
        System.out.println("4. Listar todas las entidades");
        System.out.println("5. Listar paquetes turisticos");
        System.out.println("6. Buscar cliente por RUT");
        System.out.println("7. Buscar paquete por codigo");
        System.out.println("8. Buscar paquetes por precio maximo");
        System.out.println("9. Crear reserva");
        System.out.println("10. Ver historial de reservas");
        System.out.println("11. Registrar nuevo cliente");
        System.out.println("12. Cancelar ultima reserva");
        System.out.println("13. Resumen de ventas");
        System.out.println("0. Salir");
    }

    /**
     * Ejecuta la acción correspondiente a la opción elegida del menú.
     *
     * @param opcion número de opción ingresado por el usuario
     */
    private void procesarOpcion(int opcion) {
        System.out.println();
        switch (opcion) {
            case 1 -> agencia.getClientes().forEach(Cliente::mostrarDatos);
            case 2 -> agencia.getGuias().forEach(GuiaTuristico::mostrarDatos);
            case 3 -> agencia.getProveedores().forEach(Proveedor::mostrarDatos);
            case 4 -> listarEntidadesPolimorficas();
            case 5 -> agencia.getPaquetes().values().forEach(PaqueteTuristico::mostrarDatos);
            case 6 -> buscarClientePorRut();
            case 7 -> buscarPaquetePorCodigo();
            case 8 -> buscarPaquetesPorPrecio();
            case 9 -> crearReserva();
            case 10 -> verHistorialReservas();
            case 11 -> registrarNuevoCliente();
            case 12 -> cancelarUltimaReserva();
            case 13 -> mostrarResumenVentas();
            case 0 -> { /* salir */ }
            default -> System.out.println("Opción no válida, intente nuevamente.");
        }
        System.out.println();
    }

    /**
     * Recorre una lista polimórfica List&lt;Registrable&gt; y usa instanceof
     * para clasificar cada entidad según su tipo real.
     */
    private void listarEntidadesPolimorficas() {
        List<Registrable> registrables = agencia.obtenerRegistrables();
        System.out.println("--- Entidades registradas en el sistema (" + registrables.size() + ") ---");
        for (Registrable registrable : registrables) {
            if (registrable instanceof Cliente) {
                System.out.print("Tipo detectado: CLIENTE -> ");
            } else if (registrable instanceof GuiaTuristico) {
                System.out.print("Tipo detectado: GUÍA -> ");
            } else if (registrable instanceof Proveedor) {
                System.out.print("Tipo detectado: PROVEEDOR -> ");
            } else if (registrable instanceof PaqueteTuristico) {
                System.out.print("Tipo detectado: PAQUETE -> ");
            }
            registrable.mostrarDatos();
        }
    }

    /**
     * Solicita un RUT por consola y muestra el cliente si existe.
     */
    private void buscarClientePorRut() {
        System.out.print("Ingrese RUT del cliente (ej: 16789123-3): ");
        String rut = scanner.nextLine().trim();
        Cliente cliente = agencia.buscarClientePorRut(rut);
        if (cliente != null) {
            cliente.mostrarDatos();
        } else {
            System.out.println("No se encontro un cliente con RUT " + rut);
        }
    }

    /**
     * Solicita un código por consola y muestra el paquete si existe
     * (búsqueda directa en el HashMap).
     */
    private void buscarPaquetePorCodigo() {
        System.out.print("Ingrese codigo del paquete (ej: PKG001): ");
        String codigo = scanner.nextLine().trim();
        PaqueteTuristico paquete = agencia.buscarPaquete(codigo);
        if (paquete != null) {
            paquete.mostrarDatos();
        } else {
            System.out.println("No existe un paquete con código " + codigo);
        }
    }

    /**
     * Filtra y muestra los paquetes bajo un precio máximo
     * (usa la versión sobrecargada de buscarPaquete).
     */
    private void buscarPaquetesPorPrecio() {
        double maximo = leerDouble("Ingrese precio maximo por persona: ");
        List<PaqueteTuristico> encontrados = agencia.buscarPaquete(maximo);
        if (encontrados.isEmpty()) {
            System.out.println("No hay paquetes bajo ese precio.");
        } else {
            encontrados.forEach(PaqueteTuristico::mostrarDatos);
        }
    }

    /**
     * Flujo completo para crear una reserva: valida cliente,
     * paquete y cupos antes de confirmarla.
     */
    private void crearReserva() {
        System.out.print("RUT del cliente: ");
        Cliente cliente = agencia.buscarClientePorRut(scanner.nextLine().trim());
        if (cliente == null) {
            System.out.println("Cliente no encontrado. Registrelo primero (opción 11).");
            return;
        }
        System.out.print("Codigo del paquete: ");
        PaqueteTuristico paquete = agencia.buscarPaquete(scanner.nextLine().trim());
        if (paquete == null) {
            System.out.println("Paquete no encontrado.");
            return;
        }
        int personas = leerEntero("Cantidad de personas: ");
        Reserva reserva = agencia.crearReserva(cliente, paquete, personas);
        if (reserva == null) {
            System.out.println("No hay cupos suficientes (disponibles: "
                    + paquete.getCuposDisponibles() + ").");
        } else {
            System.out.println("Reserva creada con éxito:");
            System.out.println(reserva);
        }
    }

    /**
     * Muestra el historial usando la pila: la reserva más reciente primero.
     */
    private void verHistorialReservas() {
        if (agencia.getHistorialReservas().isEmpty()) {
            System.out.println("Aun no se han realizado reservas.");
            return;
        }
        System.out.println("--- Historial de reservas (mas reciente primero) ---");
        for (int i = agencia.getHistorialReservas().size() - 1; i >= 0; i--) {
            System.out.println(agencia.getHistorialReservas().get(i));
        }
    }

    /**
     * Registra un cliente nuevo validando el RUT con la excepción
     * personalizada RutInvalidoException.
     */
    private void registrarNuevoCliente() {
        try {
            System.out.print("RUT (ej: 12345678-5): ");
            Rut rut = new Rut(scanner.nextLine().trim());
            System.out.print("Nombre: ");
            String nombre = scanner.nextLine().trim();
            System.out.print("Apellido: ");
            String apellido = scanner.nextLine().trim();
            System.out.print("Email: ");
            String email = scanner.nextLine().trim();
            System.out.print("Telefono: ");
            String telefono = scanner.nextLine().trim();
            System.out.print("Calle: ");
            String calle = scanner.nextLine().trim();
            System.out.print("Número: ");
            String numero = scanner.nextLine().trim();
            System.out.print("Comuna: ");
            String comuna = scanner.nextLine().trim();

            Direccion direccion = new Direccion(calle, numero, comuna, "Los Lagos");
            // Se usa el constructor sobrecargado (parte con 0 puntos)
            agencia.registrarCliente(new Cliente(rut, nombre, apellido, email, telefono, direccion));
        } catch (RutInvalidoException e) {
            System.out.println("[ERROR] " + e.getMessage());
        }
    }

    /**
     * Cancela la reserva más reciente (pop de la pila) y muestra
     * la restitución de cupos al paquete.
     */
    private void cancelarUltimaReserva() {
        Reserva cancelada = agencia.cancelarUltimaReserva();
        if (cancelada == null) {
            System.out.println("No hay reservas para cancelar.");
            return;
        }
        System.out.println("Reserva cancelada (se desapiló la mas reciente):");
        System.out.println(cancelada);
        System.out.println("Cupos restituidos al paquete " + cancelada.getPaquete().getCodigo()
                + " (disponibles ahora: " + cancelada.getPaquete().getCuposDisponibles() + ").");
    }

    /**
     * Muestra el resumen de ventas: cantidad de reservas vigentes,
     * total recaudado y paquete más reservado.
     */
    private void mostrarResumenVentas() {
        System.out.println("--- Resumen de ventas ---");
        System.out.println("Reservas vigentes: " + agencia.getHistorialReservas().size());
        System.out.println("Total recaudado: $"
                + String.format("%,.0f", agencia.calcularTotalVentas()));
        PaqueteTuristico masReservado = agencia.obtenerPaqueteMasReservado();
        if (masReservado != null) {
            System.out.println("Paquete mas reservado: " + masReservado.getCodigo()
                    + " - " + masReservado.getNombre());
        } else {
            System.out.println("Paquete mas reservado: (sin reservas aun)");
        }
    }

    // ------------------- Utilitarios de lectura -------------------

    /**
     * Lee un número entero por consola, reintentando ante entradas inválidas.
     *
     * @param mensaje texto que se muestra al usuario antes de leer
     * @return el entero ingresado por el usuario
     */
    private int leerEntero(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Debe ingresar un numero entero.");
            }
        }
    }

    /**
     * Lee un número decimal por consola, reintentando ante entradas inválidas.
     *
     * @param mensaje texto que se muestra al usuario antes de leer
     * @return el número decimal ingresado por el usuario
     */
    private double leerDouble(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            try {
                return Double.parseDouble(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Debe ingresar un número valido.");
            }
        }
    }
}
