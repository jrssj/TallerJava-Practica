package taller;

public class ProductoElectronico extends Producto {
    private final int mesesGarantia;

    public ProductoElectronico(String nombre, String categoria, int precio, int stock, int mesesGarantia) {
        super(nombre, categoria, precio, stock);
        this.mesesGarantia = mesesGarantia;
    }

    public String getTipo() { return "Electronico, garantia " + mesesGarantia + " meses"; }
}
