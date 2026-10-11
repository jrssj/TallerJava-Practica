package taller;

public class Asignatura {
    private final String nombre;
    private double notaFinal;

    public Asignatura(String nombre, double notaFinal) {
        if (nombre == null || nombre.trim().isEmpty() || notaFinal < 0 || notaFinal > 10)
            throw new IllegalArgumentException("Asignatura o nota invalida.");
        this.nombre = nombre.trim();
        this.notaFinal = notaFinal;
    }

    public String getNombre() { return nombre; }
    public double getNotaFinal() { return notaFinal; }
    public void setNotaFinal(double notaFinal) {
        if (notaFinal < 0 || notaFinal > 10) throw new IllegalArgumentException("Nota invalida.");
        this.notaFinal = notaFinal;
    }
}
