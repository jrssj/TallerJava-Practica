package taller;

public class CuentaCorriente extends CuentaBancaria {
    private final double limiteSobregiro;

    public CuentaCorriente(String titular, double saldo, String pin, double limiteSobregiro) {
        super(titular, saldo, pin);
        this.limiteSobregiro = limiteSobregiro;
    }

    public String getTipo() { return "Corriente"; }

    protected boolean puedeRetirar(double monto) {
        return validarMonto(monto) && monto <= getSaldo() + limiteSobregiro;
    }

    public boolean retirar(double monto) {
        if (!puedeRetirar(monto)) return false;
        cambiarSaldo(getSaldo() - monto);
        agregarMovimiento(String.format("Retiro: -$%.2f", monto));
        return true;
    }
}
