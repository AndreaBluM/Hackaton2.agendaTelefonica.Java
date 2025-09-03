package service;
import model.Contacto;
import service.Agenda;
import java.util.List;

public buscaContacto(String nombre, String apellido)
{        for (Contacto contacto : contactos) {
    if (contacto.getNombre().equalsIgnoreCase(nombre) && contacto.getApellido().equalsIgnoreCase(apellido))
        return contacto;
}
    return null;
}
