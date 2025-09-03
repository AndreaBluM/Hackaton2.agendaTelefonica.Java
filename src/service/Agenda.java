package service;

import model.Contacto;

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

    //* Método espacios libres
    public int espaciosLibres(){
        return maxSize - contactos.size();
    }

    //* Método Agenda Llena
    public boolean agendaLlena(){
        return contactos.size() >= maxSize;
    }



}
