package taller;

import java.io.IOException;
import java.util.ArrayList;

public class Main {
    private static final Entrada entrada = new Entrada();
    private static final Agenda agenda = new Agenda();
    private static final Banco banco = new Banco();
    private static final Inventario inventario = new Inventario();

    public static void main(String[] args) {
        try {
            agenda.cargarDesdeArchivo();
        } catch (IOException ex) {
            System.out.println("No se pudo cargar la agenda guardada.");
        }

        int opcion;
        do {
            System.out.println("\n========== TALLER JAVA ==========");
            System.out.println("1. Cajero automatico");
            System.out.println("2. Registro academico");
            System.out.println("3. Tienda online");
            System.out.println("4. Agenda de contactos");
            System.out.println("5. Sistema de notas con curva");
            System.out.println("0. Salir");
            opcion = entrada.entero("Seleccione una opcion: ", 0, 5);
            switch (opcion) {
                case 1: cajeroAutomatico(); break;
                case 2: registroAcademico(); break;
                case 3: tiendaOnline(); break;
                case 4: menuAgenda(); break;
                case 5: notasConCurva(); break;
                case 0: System.out.println("Hasta pronto."); break;
                default: break;
            }
        } while (opcion != 0);
    }

    private static void cajeroAutomatico() {
        System.out.println("\n--- EJERCICIO 1: CAJERO ---");
        System.out.println("Cuentas de practica:");
        for (CuentaBancaria cuenta : banco.getCuentas())
            System.out.println("- " + cuenta.getTitular() + " (" + cuenta.getTipo() + ")");
        String titular = entrada.texto("Titular de la cuenta: ");
        CuentaBancaria cuenta = banco.buscarCuenta(titular);
        if (cuenta == null) {
            System.out.println("No se encontro esa cuenta.");
            return;
        }
        if (cuenta.estaBloqueada()) {
            System.out.println("La cuenta esta bloqueada.");
            return;
        }
        boolean correcto = false;
        while (cuenta.getIntentosFallidos() < 3 && !correcto) {
            correcto = banco.validarPin(titular, entrada.texto("PIN de 4 digitos: "));
            if (!correcto && !cuenta.estaBloqueada())
                System.out.println("PIN incorrecto. Intentos restantes: " + (3 - cuenta.getIntentosFallidos()));
        }
        if (!correcto) {
            System.out.println("Cuenta bloqueada por tres intentos incorrectos.");
            return;
        }

        int opcion;
        do {
            System.out.printf("\nCuenta %s | Tipo: %s | Saldo: $%.2f%n", cuenta.getTitular(), cuenta.getTipo(), cuenta.getSaldo());
            System.out.println("1. Consultar saldo\n2. Retirar\n3. Depositar\n4. Transferir\n5. Consultar movimientos\n0. Volver al menu principal");
            opcion = entrada.entero("Opcion: ", 0, 5);
            switch (opcion) {
                case 1:
                    System.out.printf("Saldo: $%.2f%n", cuenta.getSaldo());
                    break;
                case 2:
                    if (cuenta.retirar(entrada.dinero("Monto a retirar: ")))
                        System.out.println("Retiro realizado.");
                    else System.out.println("No se pudo retirar ese monto. Revise su saldo y el limite de la cuenta.");
                    break;
                case 3:
                    try {
                        cuenta.depositar(entrada.dinero("Monto a depositar: "));
                        System.out.println("Deposito realizado.");
                    } catch (IllegalArgumentException ex) {
                        System.out.println(ex.getMessage());
                    }
                    break;
                case 4:
                    String destinoNombre = entrada.texto("Titular de la cuenta destino: ");
                    CuentaBancaria destino = banco.buscarCuenta(destinoNombre);
                    if (destino == null) System.out.println("No se encontro la cuenta destino.");
                    else if (cuenta.transferir(destino, entrada.dinero("Monto a transferir: ")))
                        System.out.println("Transferencia realizada a " + destino.getTitular() + ".");
                    else System.out.println("No se pudo realizar la transferencia. Verifique el monto y el saldo.");
                    break;
                case 5:
                    ArrayList<String> movimientos = cuenta.getMovimientos();
                    if (movimientos.isEmpty()) System.out.println("Aun no hay movimientos.");
                    for (String movimiento : movimientos) System.out.println(movimiento);
                    break;
                default: break;
            }
        } while (opcion != 0);
    }

    private static void registroAcademico() {
        System.out.println("\n--- EJERCICIO 2: REGISTRO ACADEMICO ---");
        int cantidad = entrada.entero("Cantidad de estudiantes: ", 1, 1000);
        Curso curso = new Curso();
        for (int i = 0; i < cantidad; i++) {
            System.out.println("\nEstudiante " + (i + 1));
            String nombre = entrada.texto("Nombre: ");
            int faltas = entrada.entero("Numero de faltas: ", 0, 10000);
            int cantidadAsignaturas = entrada.entero("Cantidad de asignaturas: ", 1, 30);
            ArrayList<Asignatura> asignaturas = new ArrayList<Asignatura>();
            for (int j = 0; j < cantidadAsignaturas; j++) {
                String asignatura = entrada.texto("Nombre de asignatura " + (j + 1) + ": ");
                double nota = entrada.nota("Nota final de " + asignatura + " (0 a 10): ");
                asignaturas.add(new Asignatura(asignatura, nota));
            }
            curso.agregarEstudiante(new Estudiante(nombre, asignaturas, faltas));
        }

        mostrarEstadisticasCurso(curso);
        int opcion;
        do {
            System.out.println("\n1. Buscar estudiante\n2. Modificar nota de asignatura\n3. Guardar reporte en reporte_curso.txt\n0. Volver al menu principal");
            opcion = entrada.entero("Opcion: ", 0, 3);
            switch (opcion) {
                case 1:
                    Estudiante encontrado = curso.buscarEstudiante(entrada.texto("Nombre exacto: "));
                    if (encontrado == null) System.out.println("Estudiante no encontrado.");
                    else mostrarEstudiante(encontrado);
                    break;
                case 2:
                    String nombre = entrada.texto("Nombre del estudiante: ");
                    String asignatura = entrada.texto("Nombre de la asignatura: ");
                    double nota = entrada.nota("Nueva nota: ");
                    if (curso.modificarNota(nombre, asignatura, nota)) {
                        System.out.println("Nota actualizada.");
                        mostrarEstadisticasCurso(curso);
                    } else System.out.println("No se encontro el estudiante o la asignatura.");
                    break;
                case 3:
                    try {
                        curso.guardarReporte("reporte_curso.txt");
                        System.out.println("Reporte guardado como reporte_curso.txt en la carpeta del proyecto.");
                    } catch (IOException ex) {
                        System.out.println("No se pudo guardar el reporte.");
                    }
                    break;
                default: break;
            }
        } while (opcion != 0);
    }

    private static void mostrarEstadisticasCurso(Curso curso) {
        System.out.println("\nResultados del curso:");
        for (Estudiante estudiante : curso.getEstudiantes()) mostrarEstudiante(estudiante);
        int aprobados = curso.contarAprobados();
        System.out.printf("Promedio general: %.2f%n", curso.calcularPromedioGeneral());
        System.out.println("Aprobados: " + aprobados + " | Reprobados: " + (curso.getEstudiantes().size() - aprobados));
        if (curso.buscarMejor() != null) {
            System.out.printf("Mejor promedio: %s (%.2f)%n", curso.buscarMejor().getNombre(), curso.buscarMejor().calcularPromedio());
            System.out.printf("Menor promedio: %s (%.2f)%n", curso.buscarPeor().getNombre(), curso.buscarPeor().calcularPromedio());
        }
    }

    private static void mostrarEstudiante(Estudiante estudiante) {
        System.out.printf("%s | Promedio: %.2f | Faltas: %d | %s%n", estudiante.getNombre(),
                estudiante.calcularPromedio(), estudiante.getFaltas(), estudiante.aprobo() ? "Aprobado" : "Reprobado");
        for (Asignatura asignatura : estudiante.getAsignaturas())
            System.out.printf("  %s: %.2f%n", asignatura.getNombre(), asignatura.getNotaFinal());
    }

    private static void tiendaOnline() {
        System.out.println("\n--- EJERCICIO 3: TIENDA ONLINE ---");
        String nombre = entrada.texto("Nombre del cliente: ");
        String email = entrada.texto("Email del cliente: ");
        if (!email.contains("@") || !email.contains(".")) {
            System.out.println("Email invalido.");
            return;
        }
        Cliente cliente = new Cliente(nombre, email);
        Carrito carrito = new Carrito(cliente, inventario);
        int opcion;
        do {
            System.out.println("\nCategorias: 1. Alimentos  2. Electronica  3. Papeleria  0. Finalizar compra");
            int categoriaOpcion = entrada.entero("Seleccione categoria: ", 0, 3);
            if (categoriaOpcion == 0) break;
            String categoria = categoriaOpcion == 1 ? "Alimentos" : categoriaOpcion == 2 ? "Electronica" : "Papeleria";
            ArrayList<Producto> productos = inventario.buscarPorCategoria(categoria);
            for (int i = 0; i < productos.size(); i++) {
                Producto producto = productos.get(i);
                System.out.printf("%d. %s | $%d | Stock: %d | %s%n", i + 1, producto.getNombre(),
                        producto.getPrecio(), producto.getStock(), producto.getTipo());
            }
            System.out.println("0. Elegir otra categoria");
            opcion = entrada.entero("Producto: ", 0, productos.size());
            if (opcion > 0) {
                int cantidad = entrada.entero("Cantidad: ", 1, 1000000);
                if (carrito.agregarProducto(productos.get(opcion - 1), cantidad))
                    System.out.println("Producto agregado al carrito.");
                else System.out.println("No hay suficiente stock.");
            }
        } while (true);
        carrito.mostrarFactura();
    }

    private static void menuAgenda() {
        System.out.println("\n--- EJERCICIO 4: AGENDA ---");
        int opcion;
        do {
            System.out.println("1. Agregar contacto\n2. Buscar nombre\n3. Mostrar todos\n4. Eliminar contacto\n5. Filtrar por grupo\n6. Guardar agenda\n0. Volver al menu principal");
            opcion = entrada.entero("Opcion: ", 0, 6);
            switch (opcion) {
                case 1:
                    String nombre = entrada.texto("Nombre: ");
                    String telefono;
                    do {
                        telefono = entrada.texto("Telefono (exactamente 9 digitos): ");
                        if (!telefono.matches("[0-9]{9}")) System.out.println("El telefono debe tener exactamente 9 digitos.");
                    } while (!telefono.matches("[0-9]{9}"));
                    String email = entrada.texto("Email: ");
                    String grupo = entrada.texto("Grupo (Familia, Trabajo, Amigos u otro): ");
                    if (agenda.agregarContacto(new Contacto(nombre, telefono, email, grupo))) {
                        guardarAgenda();
                        System.out.println("Contacto agregado.");
                    } else System.out.println("Ya existe un contacto con ese nombre o telefono.");
                    break;
                case 2:
                    ArrayList<Contacto> encontrados = agenda.buscarContacto(entrada.texto("Nombre o parte del nombre: "));
                    if (encontrados.isEmpty()) System.out.println("No se encontraron contactos.");
                    for (Contacto contacto : encontrados) contacto.mostrarInfo();
                    break;
                case 3: agenda.mostrarTodos(); break;
                case 4:
                    if (agenda.eliminarContacto(entrada.texto("Nombre exacto a eliminar: "))) {
                        guardarAgenda();
                        System.out.println("Contacto eliminado.");
                    } else System.out.println("Contacto no encontrado.");
                    break;
                case 5: agenda.mostrarContactosPorGrupo(entrada.texto("Grupo: ")); break;
                case 6: guardarAgenda(); break;
                default: break;
            }
        } while (opcion != 0);
        guardarAgenda();
    }

    private static void guardarAgenda() {
        try {
            agenda.guardarEnArchivo();
            System.out.println("Agenda guardada en agenda.txt.");
        } catch (IOException ex) {
            System.out.println("No se pudo guardar la agenda.");
        }
    }

    private static void notasConCurva() {
        System.out.println("\n--- EJERCICIO 5: NOTAS CON CURVA ---");
        int cantidad = entrada.entero("Cantidad de alumnos: ", 1, 1000);
        Alumno[] alumnos = new Alumno[cantidad];
        double sumaOriginal = 0;
        double notaMasAlta = 0;
        for (int i = 0; i < cantidad; i++) {
            String nombre = entrada.texto("Nombre del alumno " + (i + 1) + ": ");
            System.out.println("Notas de " + nombre + " (0 a 10):");
            double parcial1 = entrada.nota("Parcial 1 (30%): ");
            double parcial2 = entrada.nota("Parcial 2 (30%): ");
            double examen = entrada.nota("Examen final (40%): ");
            alumnos[i] = new Alumno(nombre, parcial1, parcial2, examen);
            sumaOriginal += alumnos[i].getNotaOriginal();
            notaMasAlta = Math.max(notaMasAlta, alumnos[i].getNotaOriginal());
        }

        double promedioOriginal = sumaOriginal / cantidad;
        System.out.printf("Promedio original: %.2f%n", promedioOriginal);
        int aprobadosAntes = 0;
        for (Alumno alumno : alumnos) if (alumno.estaAprobado()) aprobadosAntes++;
        if (promedioOriginal < 6) {
            System.out.println("1. Aplicar curva porcentual del 10%\n2. Aplicar curva para que el mejor alumno llegue a 10");
            int tipoCurva = entrada.entero("Tipo de curva: ", 1, 2);
            if (tipoCurva == 1) {
                for (Alumno alumno : alumnos) alumno.aplicarCurvaPorcentual(10);
            } else {
                double puntos = 10 - notaMasAlta;
                for (Alumno alumno : alumnos) alumno.aplicarCurvaPuntos(puntos);
                System.out.printf("Curva aplicada: +%.2f puntos.%n", puntos);
            }
        } else System.out.println("No se aplica curva porque el promedio es 6 o mayor.");

        double sumaFinal = 0;
        int aprobadosDespues = 0;
        int recuperados = 0;
        for (Alumno alumno : alumnos) {
            sumaFinal += alumno.getNotaFinal();
            if (alumno.estaAprobado()) aprobadosDespues++;
            if (alumno.getNotaOriginal() < 6 && alumno.estaAprobado()) recuperados++;
            System.out.printf("%s | Parcial 1: %.2f | Parcial 2: %.2f | Examen: %.2f | Original: %.2f | Final: %.2f | %s%n",
                    alumno.getNombre(), alumno.getNotaParcial1(), alumno.getNotaParcial2(), alumno.getExamenFinal(),
                    alumno.getNotaOriginal(), alumno.getNotaFinal(), alumno.estaAprobado() ? "Aprobado" : "Reprobado");
        }
        double promedioFinal = sumaFinal / cantidad;
        double sumaCuadrados = 0;
        for (Alumno alumno : alumnos) {
            double diferencia = alumno.getNotaFinal() - promedioFinal;
            sumaCuadrados += diferencia * diferencia;
        }
        double desviacion = Math.sqrt(sumaCuadrados / cantidad);
        System.out.printf("Nueva media: %.2f%n", promedioFinal);
        System.out.printf("Desviacion estandar: %.2f%n", desviacion);
        System.out.println("Alumnos recuperados por la curva: " + recuperados);
        System.out.println("Aprobados antes: " + aprobadosAntes + " | Aprobados despues: " + aprobadosDespues);
    }
}
