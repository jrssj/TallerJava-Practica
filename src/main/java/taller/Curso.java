package taller;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Curso {
    private final ArrayList<Estudiante> estudiantes = new ArrayList<Estudiante>();

    public void agregarEstudiante(Estudiante estudiante) { estudiantes.add(estudiante); }
    public ArrayList<Estudiante> getEstudiantes() { return new ArrayList<Estudiante>(estudiantes); }

    public Estudiante buscarEstudiante(String nombre) {
        for (Estudiante estudiante : estudiantes)
            if (estudiante.getNombre().equalsIgnoreCase(nombre.trim())) return estudiante;
        return null;
    }

    public boolean modificarNota(String nombreEstudiante, String nombreAsignatura, double nota) {
        Estudiante estudiante = buscarEstudiante(nombreEstudiante);
        return estudiante != null && estudiante.modificarNota(nombreAsignatura, nota);
    }

    public double calcularPromedioGeneral() {
        if (estudiantes.isEmpty()) return 0;
        double suma = 0;
        for (Estudiante estudiante : estudiantes) suma += estudiante.calcularPromedio();
        return suma / estudiantes.size();
    }

    public int contarAprobados() {
        int total = 0;
        for (Estudiante estudiante : estudiantes) if (estudiante.aprobo()) total++;
        return total;
    }

    public Estudiante buscarMejor() {
        if (estudiantes.isEmpty()) return null;
        Estudiante mejor = estudiantes.get(0);
        for (Estudiante estudiante : estudiantes)
            if (estudiante.calcularPromedio() > mejor.calcularPromedio()) mejor = estudiante;
        return mejor;
    }

    public Estudiante buscarPeor() {
        if (estudiantes.isEmpty()) return null;
        Estudiante peor = estudiantes.get(0);
        for (Estudiante estudiante : estudiantes)
            if (estudiante.calcularPromedio() < peor.calcularPromedio()) peor = estudiante;
        return peor;
    }

    public void guardarReporte(String nombreArchivo) throws IOException {
        try (BufferedWriter escritor = new BufferedWriter(new FileWriter(nombreArchivo))) {
            escritor.write("REPORTE DEL CURSO\n");
            for (Estudiante estudiante : estudiantes) {
                escritor.write(String.format("%s | Promedio: %.2f | Faltas: %d | %s%n",
                        estudiante.getNombre(), estudiante.calcularPromedio(), estudiante.getFaltas(),
                        estudiante.aprobo() ? "Aprobado" : "Reprobado"));
                for (Asignatura asignatura : estudiante.getAsignaturas())
                    escritor.write(String.format("  %s: %.2f%n", asignatura.getNombre(), asignatura.getNotaFinal()));
            }
            escritor.write(String.format("Promedio general: %.2f%n", calcularPromedioGeneral()));
            escritor.write("Aprobados: " + contarAprobados() + " | Reprobados: "
                    + (estudiantes.size() - contarAprobados()) + "\n");
            if (!estudiantes.isEmpty()) {
                escritor.write("Mejor estudiante: " + buscarMejor().getNombre() + "\n");
                escritor.write("Peor estudiante: " + buscarPeor().getNombre() + "\n");
            }
        }
    }
}
