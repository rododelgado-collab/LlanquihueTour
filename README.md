![Duoc UC](https://www.duoc.cl/wp-content/uploads/2022/09/logo-0.png)
# 🧠 Evaluación Final Transversal – Desarrollo Orientado a Objetos I

## 👤 Autor del proyecto
- **Nombre completo:** Rodolfo Delgado.
- **Sección:** 008A.
- **Carrera:** Analista programador
- **Sede:** Campus Virtual Duoc Uc.

---

## 📘 Descripción general del sistema
Este proyecto corresponde a la Evaluación Final Transversal de la asignatura *Desarrollo Orientado a Objetos I*. Se trata de un sistema orientado a objetos desarrollado en Java, cuyo objetivo es modelar y gestionar las entidades de **Llanquihue Tour**, una agencia de turismo de la Región de Los Lagos, aplicando los principios de encapsulamiento, composición, herencia, polimorfismo e interfaces.

El sistema permite gestionar **clientes, guías turísticos, proveedores, paquetes turísticos con sus actividades y reservas**, cargando los datos iniciales desde archivos `.txt` y operando mediante un menú por consola. El prototipo aborda las problemáticas del caso (información desorganizada, gestión manual de reservas, falta de categorización de personas y de trazabilidad de paquetes) mediante una solución estructurada, modular y reutilizable.

---

## 🧱 Estructura general del proyecto

```plaintext
📁 src/main/java/
├── app/         # Main (arranque), MenuPrincipal (interfaz de consola) y AgenciaTour (lógica y colecciones)
├── model/       # Clases de dominio (Persona, Cliente, GuiaTuristico, Proveedor,
│                #   Rut, Direccion, PaqueteTuristico, Actividad, Reserva)
├── data/        # LectorArchivos: carga y lectura de los archivos .txt
├── utils/       # RutInvalidoException: excepción personalizada de validación
└── interfaces/  # Registrable: interfaz implementada por las clases del dominio
📁 data/         # Archivos de datos: clientes.txt, guias.txt, proveedores.txt, paquetes.txt
```

---

## 🧩 Resumen de las clases principales

| Clase | Función |
|---|---|
| `Persona` (abstracta) | Clase base de la jerarquía; contiene `Rut` y `Direccion` por composición |
| `Cliente` / `GuiaTuristico` / `Proveedor` | Subclases de `Persona` (herencia) con atributos propios |
| `Registrable` (interfaz) | Define `registrar()` y `mostrarDatos()`; permite listas polimórficas |
| `Rut` | Valida el dígito verificador con módulo 11 y lanza `RutInvalidoException` |
| `PaqueteTuristico` | Paquete con actividades (composición), cupos y guía asignado |
| `Reserva` | Vincula cliente + paquete, calcula el total y descuenta cupos |
| `AgenciaTour` | Administra las colecciones (`ArrayList`, `HashMap`, `Stack`), reservas, cancelaciones y estadísticas de ventas |
| `MenuPrincipal` | Menú interactivo por consola (13 opciones); delega la lógica en `AgenciaTour` |
| `LectorArchivos` | Convierte las líneas de los `.txt` en objetos del modelo |
| `Main` | Punto de entrada: crea la agencia, carga los `.txt` y lanza el menú |

Todo el código está **documentado con Javadoc** (`@author`, `@param`, `@return`, `@throws`). Para generar la documentación HTML: clic derecho al proyecto → *Generate Javadoc* en NetBeans, o `mvn javadoc:javadoc` por consola.

---

## ⚙️ Instrucciones para clonar y ejecutar el proyecto

1. Clona el repositorio desde GitHub:

```bash
git clone https://github.com/rododelgado-collab/LlanquihueTour.git
```

2. Abre el proyecto en **Apache NetBeans IDE** (`File → Open Project`, es un proyecto Maven y se abre directamente). También es compatible con IntelliJ IDEA.

3. Verifica que los archivos `.txt` estén correctamente ubicados en la carpeta `data/` en la raíz del proyecto.

4. Ejecuta el archivo `Main.java` desde el paquete `app` (clic derecho al proyecto → *Run*).

5. Sigue las instrucciones del menú en consola (requiere JDK 17 o superior).

---

**Repositorio GitHub:** https://github.com/rododelgado-collab/LlanquihueTour
**Fecha de entrega:** \[DD/MM/2026]

---

© Duoc UC | Escuela de Informática y Telecomunicaciones | Evaluación Final Transversal EFT
