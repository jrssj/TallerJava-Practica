package taller;

import java.util.Scanner;
import java.util.InputMismatchException;
import java.util.Locale;

public class Entrada {
    private final Scanner scanner = new Scanner(System.in);

    public String texto(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String valor = scanner.nextLine().trim();
            if (!valor.isEmpty()) return valor;
            System.out.println("El texto no puede estar vacio.");
        }
    }

    public int entero(String mensaje, int minimo, int maximo) {
        while (true) {
            try {
                int valor = Integer.parseInt(texto(mensaje));
                if (valor >= minimo && valor <= maximo) return valor;
            } catch (NumberFormatException ex) { }
            System.out.println("Ingrese un entero entre " + minimo + " y " + maximo + ".");
        }
    }

    public double dinero(String mensaje) {
        while (true) {
            try {
                double valor = Double.parseDouble(texto(mensaje).replace(',', '.'));
                if (Double.isFinite(valor) && valor > 0 && valor <= 1000000000 &&
                        Math.abs(valor * 100 - Math.rint(valor * 100)) < 0.00001)
                    return valor;
            } catch (NumberFormatException ex) { }
            System.out.println("Ingrese un monto positivo hasta 1000000000, con maximo 2 decimales.");
        }
    }

    public double nota(String mensaje) {
        while (true) {
            String textoNota = texto(mensaje).replace(',', '.');
            try (Scanner lector = new Scanner(textoNota)) {
                lector.useLocale(Locale.US);
                double valor = lector.nextDouble();
                if (Double.isFinite(valor) && valor >= 0 && valor <= 10) return valor;
            } catch (InputMismatchException ex) {
                System.out.println("Ingrese un numero valido.");
            } catch (java.util.NoSuchElementException ex) {
                System.out.println("Ingrese un numero valido.");
            }
            System.out.println("Ingrese una nota entre 0 y 10.");
        }
    }
}
