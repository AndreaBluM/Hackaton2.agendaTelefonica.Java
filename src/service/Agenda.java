package service;

import model.Contacto;
import model.Persona;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Agenda {
    private int maxSize;
    private final List<Contacto> contactos;

    public Agenda(int maxSize) {
        this.maxSize = maxSize;
        this.contactos = new ArrayList<>();
    }

    public Agenda() {
        this(100); // tamaño por defecto
    }

    // Añadir contacto si no existe (por nombre+apellido)
    public String añadirContacto(Contacto c) {
        try {
            if (c == null) return "Contacto inválido.";
            if (!existeContacto(c)) {
                if (maxSize > 0 && contactos.size() >= maxSize) {
                    return "No se puede añadir: la agenda está llena.";
                }
                contactos.add(c);
                return "Contacto añadido: " + c.getNombre() + " " + c.getApellido();
            }
            return "El contacto ya existe: " + c.getNombre() + " " + c.getApellido();
        } catch (Exception e) {
            return " Error al añadir contacto: " + e.getMessage();
        }
    }

    // Verificar si un contacto ya existe (por nombre y apellido)
    public boolean existeContacto(Contacto c) {
        if (c == null) return false;
        for (Contacto ct : contactos) {
            if (ct.equals(c)) return true;
        }
        return false;
    }

    // Listar como texto (útil para consola)
    public List<String> listarContacto() {
        try {
            List<Contacto> copia = new ArrayList<>(contactos);
            Collections.sort(copia, Comparator
                    .comparing(Contacto::getNombre, String.CASE_INSENSITIVE_ORDER)
                    .thenComparing(Contacto::getApellido, String.CASE_INSENSITIVE_ORDER));
            List<String> resultado = new ArrayList<>();
            for (Contacto c : copia) {
                resultado.add(c.mostrarInfo());
            }
            return resultado;
        } catch (Exception e) {
            System.err.println("Error al listar contactos: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    // Devuelve todos los contactos como lista de objetos (para TableView)
    public List<Contacto> getContactos() {
        List<Contacto> copia = new ArrayList<>(contactos);
        // Mantener orden consistente
        copia.sort(Comparator
                .comparing(Contacto::getNombre, String.CASE_INSENSITIVE_ORDER)
                .thenComparing(Contacto::getApellido, String.CASE_INSENSITIVE_ORDER));
        return copia;
    }

    public int espaciosLibres() {
        if (maxSize <= 0) return Integer.MAX_VALUE; // sin límite
        return maxSize - contactos.size();
    }

    public boolean agendaLlena() {
        if (maxSize <= 0) return false;
        return contactos.size() >= maxSize;
    }

    // Busca un contacto por nombre y apellido (ignorando mayúsculas/minúsculas).
    public Persona buscaContacto(String nombre, String apellido) {
        if (nombre == null || apellido == null) return null;
        for (Contacto contacto : contactos) {
            if (contacto.getNombre().equalsIgnoreCase(nombre) &&
                    contacto.getApellido().equalsIgnoreCase(apellido)) {
                return contacto;
            }
        }
        return null;
    }

    public boolean eliminarContacto(Contacto contacto) {
        return contactos.remove(contacto);
    }

    public boolean modificarTelefono(String nombre, String apellido, String nuevoTelefono) {
        Contacto contacto = (Contacto) buscaContacto(nombre, apellido);
        if (contacto != null) {
            contacto.setTelefono(nuevoTelefono);
            return true;
        } else {
            return false;
        }
    }
}
