package controller;

import model.Contacto;
import service.Agenda;

import java.util.List;

public class AgendaController {
    private final Agenda agenda;

    public AgendaController() {
        this.agenda = new Agenda(50); // Tamaño máximo de contactos
    }

    // === Agregar un contacto ===
    public String agregarContacto(String nombre, String apellido, String telefono, String email) {
        Contacto contacto = new Contacto(nombre, apellido, telefono, email);
        // validar telefono/email al crear contacto se hace en setters si se usan
        try {
            // aplicar validaciones explícitas antes de añadir
            if (nombre == null || nombre.trim().isEmpty() ||
                    apellido == null || apellido.trim().isEmpty()) {
                return "Nombre y apellido son obligatorios.";
            }
            // seteo validaciones (llama a los setters que validan)
            contacto.setTelefono(telefono == null ? "" : telefono.trim());
            contacto.setEmail(email == null ? "" : email.trim());
        } catch (IllegalArgumentException ex) {
            return "Error: " + ex.getMessage();
        }
        return agenda.añadirContacto(contacto);
    }

    // === Listar contactos (para la TableView) ===
    public List<Contacto> getContactos() {
        return agenda.getContactos();
    }

    // === Eliminar un contacto ===
    public String eliminarContacto(String nombre, String apellido) {
        Contacto contacto = (Contacto) agenda.buscaContacto(nombre, apellido);
        if (contacto != null) {
            boolean removed = agenda.eliminarContacto(contacto);
            return removed ? "Contacto eliminado." : "No se pudo eliminar el contacto.";
        }
        return "Contacto no encontrado.";
    }

    // === Modificar teléfono ===
    public String modificarTelefono(String nombre, String apellido, String nuevoTelefono) {
        try {
            boolean ok = agenda.modificarTelefono(nombre, apellido, nuevoTelefono);
            return ok ? "Teléfono actualizado." : "No se pudo modificar: contacto no encontrado.";
        } catch (IllegalArgumentException ex) {
            return "Error: " + ex.getMessage();
        }
    }

    // === Espacios libres ===
    public int espaciosLibres() {
        return agenda.espaciosLibres();
    }

    // === Agenda llena ===
    public boolean agendaLlena() {
        return agenda.agendaLlena();
    }
}
