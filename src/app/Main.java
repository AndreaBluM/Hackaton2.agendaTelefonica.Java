package app;

import model.Contacto;
import service.Agenda;

import java.util.Scanner;
public class Main {
public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);

    System.out.println("Ingre el tamaño de su agenda (debe ser mayor a 0): ");
    int tamano = scanner.nextInt();
    scanner.nextLine();

    if (tamano <= 0) {
        System.out.println("Error: el tamaño debe ser mayor a 0");
        return;
    }

    Agenda agenda = new Agenda(tamano);

    int opcion;
    do {
        System.out.println("******MENÚ AGENDA*****");
        System.out.println("1. Añadir contacto");
        System.out.println("2. Listar contactos");
        System.out.println("3. Buscar contacto");
        System.out.println("4. Eliminar contacto");
        System.out.println("5. Modificar teléfono");
        System.out.println("6. Ver si la agenda está llena");
        System.out.println("7. Ver espacios libres");
        System.out.println("0. Salir");
        System.out.print("Seleccione una opción: ");
        opcion = scanner.nextInt();
        scanner.nextLine();

        switch (opcion) {

            case 1:
                Contacto contacto1 = new Contacto("Juan", "Perez", "28287482", "juan@gamil.com");
                String resultado = agenda.añadirContacto(contacto1);
                System.out.println(resultado);
               // String resultadoNulo = agenda.añadirContacto(null);
                //System.out.println(resultadoNulo);
                //Contacto contacto2 = new Contacto(" ", "Tamar", "43495785", "Gómez@gamil.com");
                //System.out.println(agenda.añadirContacto(contacto2));
                break;

            case 2:
                Contacto contacto3 = new Contacto("Juan", "Perez", "2828748278", "juan@gamil.com");
                agenda.añadirContacto(contacto3);
                Contacto contacto4 = new Contacto("camilo", "Montero", "6575", "camilo23@gamil.com");
                agenda.añadirContacto(contacto4);
                agenda.listarContactos();
                break;

            case 3:
                agenda.buscaContacto("Juan", "Perez");
                agenda.buscaContacto("Ana", "Torres");
                break;

            case 4:
                Contacto contacto5 = new Contacto("Ana", "Gomez", "1234567891", "anamo@gmail.com");
                agenda.añadirContacto(contacto5);
                agenda.eliminarContacto(contacto5);

            case 5:
                Contacto contacto6 = new Contacto("Luis", "Martinez", "6574839245", "jorge@gmail.com");
                agenda.añadirContacto(contacto6);
                agenda.modificarTelefono("Luis", "Martinez", "1234567878");
                System.out.println(contacto6.getTelefono());

            case 6:
                if (agenda.agendaLlena()) {
                    System.out.println("La agenda está llena.");
                } else {
                    System.out.println("Aún hay espacio en la agenda.");
                }
                break;

            case 7:
                System.out.println("Espacios libres: " + agenda.espaciosLibres());
                break;

            case 0:
                System.out.println("Saliendo del programa...");
                break;

            default:
                System.out.println("Opción no válida.");
        }
    } while (opcion != 0);

    scanner.close();
}
}

