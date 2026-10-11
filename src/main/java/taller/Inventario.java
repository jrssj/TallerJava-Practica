package taller;

import java.util.ArrayList;

public class Inventario {
    private final ArrayList<Producto> productos = new ArrayList<Producto>();

    public Inventario() {
        productos.add(new ProductoAlimenticio("Arroz", "Alimentos", 18000, 20, "2027-06"));
        productos.add(new ProductoAlimenticio("Cafe", "Alimentos", 24000, 15, "2027-01"));
        productos.add(new ProductoElectronico("Audifonos", "Electronica", 80000, 8, 12));
        productos.add(new ProductoElectronico("Teclado", "Electronica", 120000, 5, 24));
        productos.add(new Producto("Cuaderno", "Papeleria", 5000, 30));
    }

    public ArrayList<Producto> getProductos() { return new ArrayList<Producto>(productos); }

    public ArrayList<Producto> buscarPorCategoria(String categoria) {
        ArrayList<Producto> resultado = new ArrayList<Producto>();
        for (Producto producto : productos)
            if (producto.getCategoria().equalsIgnoreCase(categoria.trim())) resultado.add(producto);
        return resultado;
    }

    public boolean hayStock(Producto producto, int cantidad) {
        return productos.contains(producto) && producto.hayStock(cantidad);
    }

    public boolean disminuirStock(Producto producto, int cantidad) {
        if (!hayStock(producto, cantidad)) return false;
        producto.disminuirStock(cantidad);
        return true;
    }
}
