package app;

import data.LectorArchivos;

/**
 * Punto de entrada del prototipo de Llanquihue Tour.
 * Su única responsabilidad es arrancar el sistema: crea la agencia,
 * carga los datos desde los archivos .txt y lanza el menú interactivo
 * MenuPrincipal, que concentra la interacción con el usuario.
 *
 * @author rodo.delgado
 * @version 1.0
 */
public class Main {

    /** Constructor privado: esta clase solo se usa desde main. */
    private Main() {
    }

    /**
     * Método principal: carga los datos iniciales y ejecuta el menú.
     *
     * @param args argumentos de línea de comandos (no se utilizan)
     */
    public static void main(String[] args) {
        AgenciaTour agencia = new AgenciaTour();
        cargarDatosIniciales(agencia);
        new MenuPrincipal(agencia).iniciar();
    }

    /**
     * Carga clientes, guías, proveedores y paquetes desde la carpeta /data.
     *
     * @param agencia agencia donde se registrarán las entidades cargadas
     */
    private static void cargarDatosIniciales(AgenciaTour agencia) {
        System.out.println("Cargando datos desde archivos .txt...");
        LectorArchivos.cargarClientes("data/clientes.txt").forEach(agencia::registrarCliente);
        LectorArchivos.cargarGuias("data/guias.txt").forEach(agencia::registrarGuia);
        LectorArchivos.cargarProveedores("data/proveedores.txt").forEach(agencia::registrarProveedor);
        LectorArchivos.cargarPaquetes("data/paquetes.txt", agencia.getGuias())
                .forEach(agencia::registrarPaquete);
        System.out.println("Datos cargados: " + agencia.getClientes().size() + " clientes, "
                + agencia.getGuias().size() + " guias, "
                + agencia.getProveedores().size() + " proveedores, "
                + agencia.getPaquetes().size() + " paquetes.\n");
    }
}
