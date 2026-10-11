package taller;

import java.io.Serializable;

public class Contacto implements Serializable {
    private static final long serialVersionUID = 1L;
    private final String nombre;
    private final String telefono;
    private final String email;
    private final String grupo;

    public Contacto(String nombre, String telefono, String email, String grupo) {
        if (nombre == null || nombre.trim().isEmpty() || telefono == null ||
                !telefono.matches("[0-9]{9}") || email == null ||
                !email.contains("@") || !email.contains(".") || grupo == null || grupo.trim().isEmpty())
            throw new IllegalArgumentException("Datos del contacto invalidos.");
        this.nombre = nombre.trim();
        this.telefono = telefono;
        this.email = email;
        this.grupo = grupo.trim();
    }

    public String getNombre() { return nombre; }
    public String getTelefono() { return telefono; }
    public String getEmail() { return email; }
    public String getGrupo() { return grupo; }
    public void mostrarInfo() {
        System.out.println("Nombre: " + nombre + " | Telefono: " + telefono + " | Email: " + email + " | Grupo: " + grupo);
    }
}
