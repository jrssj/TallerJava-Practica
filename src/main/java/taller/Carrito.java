package taller;

import java.util.ArrayList;

public class Carrito {
    private final Cliente cliente;
    private final Inventario inventario;
    private final ArrayList<Producto> productos = new ArrayList<Producto>();
    private final ArrayList<Integer> cantidades = new ArrayList<Integer>();
    private static final int MONTO_DESCUENTO = 1000;

    public Carrito(Cliente cliente, Inventario inventario) {
        this.cliente = cliente;
        this.inventario = inventario;
    }

    public boolean agregarProducto(Producto producto, int cantidad) {
        if (!inventario.hayStock(producto, cantidad) || !inventario.disminuirStock(producto, cantidad)) return false;
        int posicion = productos.indexOf(producto);
        if (posicion < 0) {
            productos.add(producto);
            cantidades.add(cantidad);
        } else cantidades.set(posicion, cantidades.get(posicion) + cantidad);
        return true;
    }

    public long calcularSubtotal() {
        long subtotal = 0;
        for (int i = 0; i < productos.size(); i++)
            subtotal += (long) productos.get(i).getPrecio() * cantidades.get(i);
        return subtotal;
    }

    public long calcularDescuento() { return calcularSubtotal() > MONTO_DESCUENTO ? calcularSubtotal() / 10 : 0; }
    public long calcularTotal() { return calcularSubtotal() - calcularDescuento(); }

    public void mostrarFactura() {
        System.out.println("\n========== FACTURA ==========");
        System.out.println("Cliente: " + cliente.getNombre() + " (" + cliente.getEmail() + ")");
        if (productos.isEmpty()) System.out.println("No se agregaron productos.");
        for (int i = 0; i < productos.size(); i++) {
            Producto p = productos.get(i);
            System.out.printf("%s | %s | Cantidad: %d | Precio: $%d | Subtotal: $%d%n",
                    p.getNombre(), p.getTipo(), cantidades.get(i), p.getPrecio(),
                    (long) p.getPrecio() * cantidades.get(i));
        }
        System.out.println("Subtotal: $" + calcularSubtotal());
        System.out.println("Descuento: $" + calcularDescuento());
        System.out.println("Total a pagar: $" + calcularTotal());
    }
}
