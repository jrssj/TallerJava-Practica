package taller;

import java.util.ArrayList;

public class CuentaBancaria extends Cuenta {
    private final ArrayList<String> movimientos = new ArrayList<String>();
    private int intentosFallidos;
    private boolean bloqueada;

    public CuentaBancaria(String titular, double saldo, String pin) {
        super(titular, saldo, pin);
    }

    public String getTipo() { return "Bancaria"; }

    protected boolean validarMonto(double monto) {
        return Double.isFinite(monto) && monto > 0 && monto <= 1000000000;
    }

    protected void agregarMovimiento(String texto) {
        if (movimientos.size() == 5) movimientos.remove(0);
        movimientos.add(texto);
    }

    public void depositar(double monto) {
        if (!validarMonto(monto) || !Double.isFinite(getSaldo() + monto)) {
            throw new IllegalArgumentException("Monto de deposito invalido.");
        }
        cambiarSaldo(getSaldo() + monto);
        agregarMovimiento(String.format("Deposito: +$%.2f", monto));
    }

    public boolean retirar(double monto) {
        if (!validarMonto(monto) || monto > getSaldo()) return false;
        cambiarSaldo(getSaldo() - monto);
        agregarMovimiento(String.format("Retiro: -$%.2f", monto));
        return true;
    }

    public boolean transferir(CuentaBancaria destino, double monto) {
        if (destino == null || destino == this || !validarMonto(monto)
                || !puedeRetirar(monto)) return false;
        cambiarSaldo(getSaldo() - monto);
        destino.recibirTransferencia(monto);
        agregarMovimiento(String.format("Transferencia enviada a %s: -$%.2f", destino.getTitular(), monto));
        return true;
    }

    protected boolean puedeRetirar(double monto) {
        return validarMonto(monto) && monto <= getSaldo();
    }

    private void recibirTransferencia(double monto) {
        cambiarSaldo(getSaldo() + monto);
        agregarMovimiento(String.format("Transferencia recibida: +$%.2f", monto));
    }

    public ArrayList<String> getMovimientos() { return new ArrayList<String>(movimientos); }
    public int getIntentosFallidos() { return intentosFallidos; }
    public boolean estaBloqueada() { return bloqueada; }
    public void registrarIntentoFallido() {
        intentosFallidos++;
        if (intentosFallidos >= 3) bloqueada = true;
    }
    public void limpiarIntentosFallidos() { intentosFallidos = 0; }
}
