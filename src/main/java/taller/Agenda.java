package taller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Base64;

public class Agenda {
    private final ArrayList<Contacto> contactos = new ArrayList<Contacto>();

    public boolean agregarContacto(Contacto contacto) {
        if (contacto == null) return false;
        for (Contacto actual : contactos) {
            if (actual.getNombre().equalsIgnoreCase(contacto.getNombre())
                    || actual.getTelefono().equals(contacto.getTelefono())) return false;
        }
        contactos.add(contacto);
        return true;
    }

    public ArrayList<Contacto> buscarContacto(String texto) {
        ArrayList<Contacto> resultado = new ArrayList<Contacto>();
        for (Contacto contacto : contactos)
            if (contacto.getNombre().toLowerCase().contains(texto.trim().toLowerCase())) resultado.add(contacto);
        return resultado;
    }

    public void mostrarTodos() {
        if (contactos.isEmpty()) System.out.println("La agenda esta vacia.");
        for (Contacto contacto : contactos) contacto.mostrarInfo();
    }

    public void mostrarContactosPorGrupo(String grupo) {
        boolean encontrados = false;
        for (Contacto contacto : contactos) {
            if (contacto.getGrupo().equalsIgnoreCase(grupo.trim())) {
                contacto.mostrarInfo();
                encontrados = true;
            }
        }
        if (!encontrados) System.out.println("No hay contactos en ese grupo.");
    }

    public boolean eliminarContacto(String nombre) {
        for (Contacto contacto : contactos) {
            if (contacto.getNombre().equalsIgnoreCase(nombre.trim())) {
                contactos.remove(contacto);
                return true;
            }
        }
        return false;
    }

    public void guardarEnArchivo() throws IOException {
        try (BufferedWriter escritor = Files.newBufferedWriter(Paths.get("agenda.txt"), StandardCharsets.UTF_8)) {
            for (Contacto contacto : contactos) {
                escritor.write(codificar(contacto.getNombre()) + "\t" + codificar(contacto.getTelefono()) + "\t"
                        + codificar(contacto.getEmail()) + "\t" + codificar(contacto.getGrupo()));
                escritor.newLine();
            }
        }
    }

    public void cargarDesdeArchivo() throws IOException {
        if (!Files.exists(Paths.get("agenda.txt"))) return;
        try (BufferedReader lector = Files.newBufferedReader(Paths.get("agenda.txt"), StandardCharsets.UTF_8)) {
            String linea;
            while ((linea = lector.readLine()) != null) {
                String[] datos = linea.split("\\t", -1);
                if (datos.length != 4) continue;
                try {
                    agregarContacto(new Contacto(decodificar(datos[0]), decodificar(datos[1]),
                            decodificar(datos[2]), decodificar(datos[3])));
                } catch (IllegalArgumentException ex) { }
            }
        }
    }

    private String codificar(String valor) {
        return Base64.getEncoder().encodeToString(valor.getBytes(StandardCharsets.UTF_8));
    }

    private String decodificar(String valor) {
        return new String(Base64.getDecoder().decode(valor), StandardCharsets.UTF_8);
    }
}
