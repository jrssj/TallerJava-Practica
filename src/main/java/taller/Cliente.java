package taller;

public class Cliente {
    private final String nombre;
    private final String email;

    public Cliente(String nombre, String email) {
        if (nombre == null || nombre.trim().isEmpty() || email == null || !email.contains("@") || !email.contains("."))
            throw new IllegalArgumentException("Datos del cliente invalidos.");
        this.nombre = nombre.trim();
        this.email = email.trim();
    }

    public String getNombre() { return nombre; }
    public String getEmail() { return email; }
}
