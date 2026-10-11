package taller;

import java.util.ArrayList;

public class Banco {
    private final ArrayList<CuentaBancaria> cuentas = new ArrayList<CuentaBancaria>();

    public Banco() {
        cuentas.add(new CuentaAhorro("Ana", 100000, "1234"));
        cuentas.add(new CuentaCorriente("Luis", 80000, "5678", 20000));
    }

    public CuentaBancaria buscarCuenta(String titular) {
        for (CuentaBancaria cuenta : cuentas) {
            if (cuenta.getTitular().equalsIgnoreCase(titular.trim())) return cuenta;
        }
        return null;
    }

    public boolean validarPin(String titular, String pin) {
        CuentaBancaria cuenta = buscarCuenta(titular);
        if (cuenta == null || cuenta.estaBloqueada()) return false;
        if (cuenta.validarPin(pin)) {
            cuenta.limpiarIntentosFallidos();
            return true;
        }
        cuenta.registrarIntentoFallido();
        return false;
    }

    public ArrayList<CuentaBancaria> getCuentas() { return new ArrayList<CuentaBancaria>(cuentas); }
}
