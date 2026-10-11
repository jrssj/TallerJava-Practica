package taller;

public class Alumno {
    private final String nombre;
    private final double notaParcial1;
    private final double notaParcial2;
    private final double examenFinal;
    private final double notaOriginal;
    private double notaFinal;

    public Alumno(String nombre, double notaParcial1, double notaParcial2, double examenFinal) {
        if (nombre == null || nombre.trim().isEmpty() || !notaValida(notaParcial1)
                || !notaValida(notaParcial2) || !notaValida(examenFinal))
            throw new IllegalArgumentException("Nombre o nota invalida.");
        this.nombre = nombre.trim();
        this.notaParcial1 = notaParcial1;
        this.notaParcial2 = notaParcial2;
        this.examenFinal = examenFinal;
        this.notaOriginal = calcularNotaOriginal();
        this.notaFinal = this.notaOriginal;
    }

    private static boolean notaValida(double nota) {
        return Double.isFinite(nota) && nota >= 0 && nota <= 10;
    }

    public double calcularNotaOriginal() { return notaParcial1 * 0.30 + notaParcial2 * 0.30 + examenFinal * 0.40; }
    public void aplicarCurvaPorcentual(double porcentaje) {
        if (porcentaje < 0) throw new IllegalArgumentException("El porcentaje no puede ser negativo.");
        notaFinal = Math.min(10, notaFinal + notaFinal * porcentaje / 100);
    }
    public void aplicarCurvaPuntos(double puntos) { notaFinal = Math.min(10, notaFinal + puntos); }

    public boolean estaAprobado() { return notaFinal >= 6; }
    public String getNombre() { return nombre; }
    public double getNotaParcial1() { return notaParcial1; }
    public double getNotaParcial2() { return notaParcial2; }
    public double getExamenFinal() { return examenFinal; }
    public double getNotaOriginal() { return notaOriginal; }
    public double getNotaFinal() { return notaFinal; }
}
