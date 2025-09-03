package test;

import model.Contacto;
import model.Persona;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.Agenda;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

class AgendaTest {
    private Agenda agenda;
    private Contacto contacto1;
    private Contacto contacto2;
    private Contacto contacto3;
    @BeforeEach
    void setUp() {
        agenda = new Agenda(3); // Agenda pequeña para testing de límites
        contacto1 = new Contacto("Juan", "Pérez", "1234567890", "juan@email.com");
        contacto2 = new Contacto("María", "Gómez", "9876543210", "maria@email.com");
        contacto3 = new Contacto("Carlos", "López", "5555555550", "carlos@email.com");
    }
    // Tests para añadirContacto()
    @Test
    void testAñadirContacto_ContactoValido() {
        String resultado = agenda.añadirContacto(contacto1);
        assertEquals("Contacto añadido: Juan Pérez", resultado);
        assertTrue(agenda.existeContacto(contacto1));
    }
    @Test
    void testAñadirContacto_ContactoDuplicado() {
        agenda.añadirContacto(contacto1);
        String resultado = agenda.añadirContacto(contacto1);
        assertEquals("El contacto ya existe: Juan Pérez", resultado);
    }
    @Test
    void testAñadirContacto_AgendaLlena() {
        agenda.añadirContacto(contacto1);
        agenda.añadirContacto(contacto2);
        agenda.añadirContacto(contacto3);
        Contacto contacto4 = new Contacto("Ana", "Martínez", "111111111", "ana@email.com");
        String resultado = agenda.añadirContacto(contacto4);
        assertEquals("No se puede añadir: la agenda está llena.", resultado);
    }
    // Tests para existeContacto()
    @Test
    void testExisteContacto_ContactoExistente() {
        agenda.añadirContacto(contacto1);
        assertTrue(agenda.existeContacto(contacto1));
    }
    @Test
    void testExisteContacto_ContactoNoExistente() {
        assertFalse(agenda.existeContacto(contacto1));
    }
    @Test
    void testExisteContacto_ContactoNulo() {
        assertFalse(agenda.existeContacto(null));
    }
    // Tests para listarContacto()
    @Test
    void testListarContacto_ListaVacia() {
        List<String> resultado = agenda.listarContacto();
        assertTrue(resultado.isEmpty());
    }
    @Test
    void testListarContacto_ListaConElementos() {
        agenda.añadirContacto(contacto1);
        agenda.añadirContacto(contacto2);
        List<String> resultado = agenda.listarContacto();
        assertEquals(2, resultado.size());
        assertTrue(resultado.get(0).contains("Juan") || resultado.get(0).contains("María"));
    }
    @Test
    void testListarContacto_OrdenAlfabetico() {
        // Añadir en orden inverso para verificar que se ordena
        agenda.añadirContacto(contacto3); // Carlos López
        agenda.añadirContacto(contacto1); // Juan Pérez
        List<String> resultado = agenda.listarContacto();
        // Carlos debería aparecer antes que Juan
        assertTrue(resultado.get(0).contains("Carlos"));
        assertTrue(resultado.get(1).contains("Juan"));
    }
    // Tests para getContactos()
    @Test
    void testGetContactos_ListaVacia() {
        List<Contacto> resultado = agenda.getContactos();
        assertTrue(resultado.isEmpty());
    }
    @Test
    void testGetContactos_ListaConElementos() {
        agenda.añadirContacto(contacto1);
        agenda.añadirContacto(contacto2);
        List<Contacto> resultado = agenda.getContactos();
        assertEquals(2, resultado.size());
        assertTrue(resultado.contains(contacto1));
        assertTrue(resultado.contains(contacto2));
    }
    @Test
    void testGetContactos_OrdenAlfabetico() {
        agenda.añadirContacto(contacto3); // Carlos López
        agenda.añadirContacto(contacto1); // Juan Pérez
        List<Contacto> resultado = agenda.getContactos();
        assertEquals("Carlos", resultado.get(0).getNombre());
        assertEquals("Juan", resultado.get(1).getNombre());
    }
    // Tests para espaciosLibres()
    @Test
    void testEspaciosLibres_AgendaVacia() {
        assertEquals(3, agenda.espaciosLibres());
    }
    @Test
    void testEspaciosLibres_AgendaParcialmenteLlena() {
        agenda.añadirContacto(contacto1);
        assertEquals(2, agenda.espaciosLibres());
    }
    @Test
    void testEspaciosLibres_AgendaSinLimite() {
        Agenda agendaSinLimite = new Agenda(0);
        assertEquals(Integer.MAX_VALUE, agendaSinLimite.espaciosLibres());
    }
    // Tests para agendaLlena()
    @Test
    void testAgendaLlena_AgendaVacia() {
        assertFalse(agenda.agendaLlena());
    }
    @Test
    void testAgendaLlena_AgendaCompleta() {
        agenda.añadirContacto(contacto1);
        agenda.añadirContacto(contacto2);
        agenda.añadirContacto(contacto3);
        assertTrue(agenda.agendaLlena());
    }
    @Test
    void testAgendaLlena_AgendaSinLimite() {
        Agenda agendaSinLimite = new Agenda(0);
        assertFalse(agendaSinLimite.agendaLlena());
    }
    // Tests para buscaContacto()
    @Test
    void testBuscaContacto_ContactoExistente() {
        agenda.añadirContacto(contacto1);
        Persona resultado = agenda.buscaContacto("Juan", "Pérez");
        assertNotNull(resultado);
        assertEquals("Juan", resultado.getNombre());
        assertEquals("Pérez", resultado.getApellido());
    }
    @Test
    void testBuscaContacto_ContactoNoExistente() {
        Persona resultado = agenda.buscaContacto("No", "Existe");
        assertNull(resultado);
    }
    @Test
    void testBuscaContacto_InsensibleMayusculas() {
        agenda.añadirContacto(contacto1);
        Persona resultado = agenda.buscaContacto("jUaN", "pÉrEz");
        assertNotNull(resultado);
        assertEquals("Juan", resultado.getNombre());
    }
    // Tests para eliminarContacto()
    @Test
    void testEliminarContacto_ContactoExistente() {
        agenda.añadirContacto(contacto1);
        assertTrue(agenda.eliminarContacto(contacto1));
        assertFalse(agenda.existeContacto(contacto1));
    }
    @Test
    void testEliminarContacto_ContactoNoExistente() {
        assertFalse(agenda.eliminarContacto(contacto1));
    }
    @Test
    void testEliminarContacto_Nulo() {
        assertFalse(agenda.eliminarContacto(null));
    }
    // Tests para modificarTelefono()
    @Test
    void testModificarTelefono_ContactoExistente() {
        agenda.añadirContacto(contacto1);
        assertTrue(agenda.modificarTelefono("Juan", "Pérez", "999999999"));
        assertEquals("999999999", contacto1.getTelefono());
    }
    @Test
    void testModificarTelefono_ContactoNoExistente() {
        assertFalse(agenda.modificarTelefono("No", "Existe", "999999999"));
    }
    @Test
    void testModificarTelefono_InsensibleMayusculas() {
        agenda.añadirContacto(contacto1);
        assertTrue(agenda.modificarTelefono("jUaN", "pÉrEz", "999999999"));
        assertEquals("999999999", contacto1.getTelefono());
    }
}
