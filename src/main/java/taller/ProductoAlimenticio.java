package taller;

public class ProductoAlimenticio extends Producto {
    private final String fechaCaducidad;

    public ProductoAlimenticio(String nombre, String categoria, int precio, int stock, String fechaCaducidad) {
        super(nombre, categoria, precio, stock);
        this.fechaCaducidad = fechaCaducidad;
    }

    public String getTipo() { return "Alimenticio, vence " + fechaCaducidad; }
}
