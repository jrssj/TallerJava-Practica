package taller;

import java.util.ArrayList;

public class Estudiante {
    private final String nombre;
    private final ArrayList<Asignatura> asignaturas;
    private final int faltas;

    public Estudiante(String nombre, ArrayList<Asignatura> asignaturas, int faltas) {
        if (nombre == null || nombre.trim().isEmpty() || asignaturas == null || asignaturas.isEmpty()
                || faltas < 0) throw new IllegalArgumentException("Datos del estudiante invalidos.");
        this.nombre = nombre.trim();
        this.asignaturas = new ArrayList<Asignatura>(asignaturas);
        this.faltas = faltas;
    }

    public double calcularPromedio() {
        double suma = 0;
        for (Asignatura asignatura : asignaturas) suma += asignatura.getNotaFinal();
        return suma / asignaturas.size();
    }

    public boolean aprobo() { return calcularPromedio() >= 6 && faltas < 10; }
    public String getNombre() { return nombre; }
    public int getFaltas() { return faltas; }
    public ArrayList<Asignatura> getAsignaturas() { return new ArrayList<Asignatura>(asignaturas); }

    public boolean modificarNota(String nombreAsignatura, double nota) {
        for (Asignatura asignatura : asignaturas) {
            if (asignatura.getNombre().equalsIgnoreCase(nombreAsignatura.trim())) {
                asignatura.setNotaFinal(nota);
                return true;
            }
        }
        return false;
    }
}
