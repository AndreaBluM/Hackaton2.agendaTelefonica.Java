package controller;

import model.Contacto;
import service.Agenda;

import java.util.List;

public class AgendaController {
    private final Agenda agenda;

    public AgendaController() {
        this.agenda = new Agenda(50); // Tamaño máximo de contactos
    }

    public String agregarContacto(String nombre, String apellido, String telefono, String email) {
        Contacto contacto = new Contacto(nombre, apellido, telefono, email);
        return agenda.añadirContacto(contacto);
    }

    public List<String> listarContactos() {
        return agenda.listarContacto();
    }

    public String eliminarContacto(String nombre, String apellido) {
        Contacto contacto = (Contacto) agenda.buscaContacto(nombre, apellido);
        if (contacto != null) {
            agenda.eliminarContacto(contacto);
            return "Contacto eliminado.";
        }
        return "Contacto no encontrado.";
    }

    public String modificarTelefono(String nombre, String apellido, String nuevoTelefono) {
        return agenda.modificarTelefono(nombre, apellido, nuevoTelefono)
                ? "Teléfono actualizado."
                : "No se pudo modificar.";
    }

    public int espaciosLibres() {
        return agenda.espaciosLibres();
    }

    public boolean agendaLlena() {
        return agenda.agendaLlena();
    }
}
