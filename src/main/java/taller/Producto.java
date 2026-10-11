package taller;

public class Producto {
    private final String nombre;
    private final int precio;
    private final String categoria;
    private int stock;

    public Producto(String nombre, String categoria, int precio, int stock) {
        if (nombre == null || nombre.trim().isEmpty() || categoria == null
                || categoria.trim().isEmpty() || precio <= 0 || stock < 0)
            throw new IllegalArgumentException("Datos del producto invalidos.");
        this.nombre = nombre.trim();
        this.categoria = categoria.trim();
        this.precio = precio;
        this.stock = stock;
    }

    public String getTipo() { return "General"; }
    public boolean hayStock(int cantidad) { return cantidad > 0 && cantidad <= stock; }

    public void disminuirStock(int cantidad) {
        if (!hayStock(cantidad)) throw new IllegalArgumentException("Stock insuficiente.");
        stock -= cantidad;
    }

    public String getNombre() { return nombre; }
    public String getCategoria() { return categoria; }
    public int getPrecio() { return precio; }
    public int getStock() { return stock; }
}
