package service;
import model.Contacto;
import java.util.List;
import service.Agenda;

public boolean modificarTelefono(String nombre, String apellido, String nuevoTelefono) {
    Contacto contacto = buscaContacto(nombre, apellido);
    if (contacto != null) {
        contacto.setTelefono(nuevoTelefono);
        System.out.println("Teléfono modificado.");
        return true;
    } else {
        System.out.println("Contacto no encontrado.");
        return false;
    }
}
