package data;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import model.Actividad;
import model.Cliente;
import model.Direccion;
import model.GuiaTuristico;
import model.PaqueteTuristico;
import model.Proveedor;
import model.Rut;
import utils.RutInvalidoException;

/**
 * Clase utilitaria encargada de leer los archivos .txt de la carpeta /data
 * y convertir cada línea en objetos del modelo.
 *
 * Formato general: campos separados por ";". Las líneas que comienzan
 * con "#" se consideran comentarios y se ignoran.
 *
 * @author rodo.delgado
 * @version 1.0
 */
public class LectorArchivos {

    // Atributos
    private static final String SEPARADOR = ";";

    /** Constructor privado: clase utilitaria de métodos estáticos. */
    private LectorArchivos() {
    }

    /**
     * Lee todas las líneas válidas (no vacías ni comentarios) de un archivo.
     *
     * @param rutaArchivo ruta relativa o absoluta del archivo .txt
     * @return lista de líneas ya divididas en campos por el separador
     */
    private static List<String[]> leerLineas(String rutaArchivo) {
        List<String[]> lineas = new ArrayList<>();
        try (BufferedReader lector = new BufferedReader(
                new FileReader(rutaArchivo, StandardCharsets.UTF_8))) {
            String linea;
            while ((linea = lector.readLine()) != null) {
                linea = linea.trim();
                if (!linea.isEmpty() && !linea.startsWith("#")) {
                    lineas.add(linea.split(SEPARADOR));
                }
            }
        } catch (IOException e) {
            System.out.println("[ERROR] No se pudo leer el archivo " + rutaArchivo
                    + ": " + e.getMessage());
        }
        return lineas;
    }

    /**
     * Carga los clientes desde un archivo .txt.
     * Formato: rut;nombre;apellido;email;telefono;calle;numero;comuna;region;puntos
     *
     * @param rutaArchivo ruta del archivo de clientes
     * @return lista de clientes construidos a partir del archivo
     */
    public static List<Cliente> cargarClientes(String rutaArchivo) {
        List<Cliente> clientes = new ArrayList<>();
        for (String[] campos : leerLineas(rutaArchivo)) {
            try {
                Rut rut = new Rut(campos[0]);
                Direccion direccion = new Direccion(campos[5], campos[6], campos[7], campos[8]);
                int puntos = Integer.parseInt(campos[9]);
                clientes.add(new Cliente(rut, campos[1], campos[2], campos[3],
                        campos[4], direccion, puntos));
            } catch (RutInvalidoException | ArrayIndexOutOfBoundsException | NumberFormatException e) {
                System.out.println("[ADVERTENCIA] Linea de cliente omitida: " + e.getMessage());
            }
        }
        return clientes;
    }

    /**
     * Carga los guías turísticos desde un archivo .txt.
     * Formato: rut;nombre;apellido;email;telefono;calle;numero;comuna;region;especialidad;idiomas;tarifa
     *
     * @param rutaArchivo ruta del archivo de guías
     * @return lista de guías construidos a partir del archivo
     */
    public static List<GuiaTuristico> cargarGuias(String rutaArchivo) {
        List<GuiaTuristico> guias = new ArrayList<>();
        for (String[] campos : leerLineas(rutaArchivo)) {
            try {
                Rut rut = new Rut(campos[0]);
                Direccion direccion = new Direccion(campos[5], campos[6], campos[7], campos[8]);
                double tarifa = Double.parseDouble(campos[11]);
                guias.add(new GuiaTuristico(rut, campos[1], campos[2], campos[3],
                        campos[4], direccion, campos[9], campos[10], tarifa));
            } catch (RutInvalidoException | ArrayIndexOutOfBoundsException | NumberFormatException e) {
                System.out.println("[ADVERTENCIA] Linea de guia omitida: " + e.getMessage());
            }
        }
        return guias;
    }

    /**
     * Carga los proveedores desde un archivo .txt.
     * Formato: rut;nombre;apellido;email;telefono;calle;numero;comuna;region;tipoServicio;empresa
     *
     * @param rutaArchivo ruta del archivo de proveedores
     * @return lista de proveedores construidos a partir del archivo
     */
    public static List<Proveedor> cargarProveedores(String rutaArchivo) {
        List<Proveedor> proveedores = new ArrayList<>();
        for (String[] campos : leerLineas(rutaArchivo)) {
            try {
                Rut rut = new Rut(campos[0]);
                Direccion direccion = new Direccion(campos[5], campos[6], campos[7], campos[8]);
                proveedores.add(new Proveedor(rut, campos[1], campos[2], campos[3],
                        campos[4], direccion, campos[9], campos[10]));
            } catch (RutInvalidoException | ArrayIndexOutOfBoundsException e) {
                System.out.println("[ADVERTENCIA] Linea de proveedor omitida: " + e.getMessage());
            }
        }
        return proveedores;
    }

    /**
     * Carga los paquetes turísticos desde un archivo .txt.
     * Formato: codigo;nombre;destino;cupos;rutGuia;act1-horario1-precio1|act2-horario2-precio2
     * El guía se busca por RUT dentro de la lista ya cargada.
     *
     * @param rutaArchivo ruta del archivo de paquetes
     * @param guias       lista de guías ya cargados para asignar por RUT
     * @return lista de paquetes turísticos con sus actividades
     */
    public static List<PaqueteTuristico> cargarPaquetes(String rutaArchivo,
                                                        List<GuiaTuristico> guias) {
        List<PaqueteTuristico> paquetes = new ArrayList<>();
        for (String[] campos : leerLineas(rutaArchivo)) {
            try {
                int cupos = Integer.parseInt(campos[3]);
                GuiaTuristico guia = buscarGuiaPorRut(guias, campos[4]);
                PaqueteTuristico paquete = new PaqueteTuristico(
                        campos[0], campos[1], campos[2], cupos, guia);

                // Las actividades vienen separadas por "|" y sus campos por "-"
                for (String textoActividad : campos[5].split("\\|")) {
                    String[] datos = textoActividad.split("-");
                    paquete.agregarActividad(new Actividad(
                            datos[0], datos[1], Double.parseDouble(datos[2])));
                }
                paquetes.add(paquete);
            } catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {
                System.out.println("[ADVERTENCIA] Linea de paquete omitida: " + e.getMessage());
            }
        }
        return paquetes;
    }

    /**
     * Busca un guía por su RUT en formato texto (por ejemplo "11111111-1").
     *
     * @param guias    lista de guías donde buscar
     * @param rutTexto RUT en formato número-dígito verificador
     * @return el guía encontrado, o null si no existe
     */
    private static GuiaTuristico buscarGuiaPorRut(List<GuiaTuristico> guias, String rutTexto) {
        for (GuiaTuristico guia : guias) {
            String rutPlano = guia.getRut().getNumero() + "-" + guia.getRut().getDigitoVerificador();
            if (rutPlano.equalsIgnoreCase(rutTexto)) {
                return guia;
            }
        }
        return null;
    }
}
