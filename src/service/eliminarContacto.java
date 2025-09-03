package service;
import model.Contacto;

public boolean eliminarContacto(Contacto contacto) {
    if (contactos.remove(contacto)) {
        System.out.println("Contacto eliminado.");
        return true;
    } else {
        System.out.println("Contacto no encontrado.");
        return false;
    }
}
