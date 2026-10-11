package taller;

public abstract class Cuenta {
    private final String titular;
    private double saldo;
    private final String pin;

    public Cuenta(String titular, double saldo, String pin) {
        if (titular == null || titular.trim().isEmpty() || !Double.isFinite(saldo)
                || saldo < 0 || pin == null || !pin.matches("[0-9]{4}")) {
            throw new IllegalArgumentException("Datos de cuenta invalidos.");
        }
        this.titular = titular.trim();
        this.saldo = saldo;
        this.pin = pin;
    }

    public String getTitular() { return titular; }
    public double getSaldo() { return saldo; }
    public boolean validarPin(String pinIngresado) { return pin.equals(pinIngresado); }
    protected void cambiarSaldo(double nuevoSaldo) { saldo = nuevoSaldo; }
    public abstract String getTipo();
    public abstract void depositar(double monto);
    public abstract boolean retirar(double monto);
}
