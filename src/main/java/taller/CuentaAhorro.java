package taller;

public class CuentaAhorro extends CuentaBancaria {
    public CuentaAhorro(String titular, double saldo, String pin) { super(titular, saldo, pin); }
    public String getTipo() { return "Ahorro"; }

    public void depositar(double monto) {
        super.depositar(monto);
        double interes = monto * 0.01;
        cambiarSaldo(getSaldo() + interes);
        agregarMovimiento(String.format("Interes de ahorro: +$%.2f", interes));
    }
}
