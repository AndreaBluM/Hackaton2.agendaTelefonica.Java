package service;

import model.Contacto;
import model.Persona;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Agenda {
    private int maxSize;
    private List<Contacto> contactos;
    public Agenda(int maxSize) {
        this.maxSize = maxSize;
    }

    public Agenda() {
        this.contactos = new ArrayList<>();
    }


    //* Verificar si un contacto ya existe (por nombre y apellido)
    public boolean existeContacto(Contacto c) {
        return contactos.contains(c);
    }

    //* Listar en orden ascendente por nombre
    public List<String> listarContacto() {
        List<Contacto> copia = new ArrayList<>(contactos);

        //! Ordenar por nombre, y si hay empate por apellido
        Collections.sort(copia, Comparator
                .comparing(Contacto::getNombre, String.CASE_INSENSITIVE_ORDER)
                .thenComparing(Contacto::getApellido, String.CASE_INSENSITIVE_ORDER));

        List<String> resultado = new ArrayList<>();
        for (Contacto c : copia) {
            resultado.add(c.mostrarInfo());
        }
        return resultado;
    }

    //* Devuelve la cantidad de espacios libres en la agenda.
    //* Calcula la diferencia entre el tamaño máximo y el número actual de contactos.
    public int espaciosLibres(){
        return maxSize - contactos.size();
    }

    //* Indica si la agenda está llena.
    //* Retorna true si el número de contactos es igual o mayor al máximo permitido.
    public boolean agendaLlena(){
        return contactos.size() >= maxSize;
    }

    //* Busca un contacto por nombre y apellido (ignorando mayúsculas/minúsculas).
    //* Retorna el contacto si lo encuentra, o null si no existe.
    public Persona buscaContacto(String nombre, String apellido) {
        for (Contacto contacto : contactos) {
            if (contacto.getNombre().equalsIgnoreCase(nombre) && contacto.getApellido().equalsIgnoreCase(apellido))
                return contacto;
        }
        return null;
    }

    //* Elimina un contacto de la agenda.
    //* Retorna true si el contacto fue eliminado, false si no se encontró.
    public boolean eliminarContacto(Contacto contacto) {
        if (contactos.remove(contacto)) {
            System.out.println("Contacto eliminado.");
            return true;
        } else {
            System.out.println("Contacto no encontrado.");
            return false;
        }
    }

    //* Modifica el teléfono de un contacto buscado por nombre y apellido.
    //* Retorna true si el teléfono fue modificado, false si el contacto no existe.
    public boolean modificarTelefono(String nombre, String apellido, String nuevoTelefono) {
        Contacto contacto = (Contacto) buscaContacto(nombre, apellido);
        if (contacto != null) {
            contacto.setTelefono(nuevoTelefono);
            System.out.println("Teléfono modificado.");
            return true;
        } else {
            System.out.println("Contacto no encontrado.");
            return false;
        }
    }


}
