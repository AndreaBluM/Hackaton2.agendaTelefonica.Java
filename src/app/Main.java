package app;

import model.Contacto;
import service.Agenda;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Ingrese el tamaño de su agenda (debe ser mayor a 0): ");
        int tamano = scanner.nextInt();
        scanner.nextLine(); // limpiar buffer

        if (tamano <= 0) {
            System.out.println("Error: el tamaño debe ser mayor a 0");
            return;
        }

        Agenda agenda = new Agenda(tamano);

        int opcion;
        do {
            System.out.println("\n****** MENÚ AGENDA *****");
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
            scanner.nextLine(); // limpiar buffer

            switch (opcion) {
                case 1:
                    System.out.print("Nombre: ");
                    String nombre = scanner.nextLine();
                    System.out.print("Apellido: ");
                    String apellido = scanner.nextLine();
                    System.out.print("Teléfono: ");
                    String telefono = scanner.nextLine();
                    System.out.print("Email: ");
                    String email = scanner.nextLine();

                    try {
                        Contacto c = new Contacto(nombre, apellido, telefono, email);
                        System.out.println(agenda.añadirContacto(c));
                    } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;

                case 2:
                    for (String info : agenda.listarContacto()) {
                        System.out.println(info);
                    }
                    break;

                case 3:
                    System.out.print("Nombre a buscar: ");
                    String n = scanner.nextLine();
                    System.out.print("Apellido a buscar: ");
                    String a = scanner.nextLine();
                    var contacto = agenda.buscaContacto(n, a);
                    if (contacto != null) {
                        System.out.println("Encontrado: " + contacto.mostrarInfo());
                    } else {
                        System.out.println("Contacto no encontrado.");
                    }
                    break;

                case 4:
                    System.out.print("Nombre del contacto a eliminar: ");
                    String nom = scanner.nextLine();
                    System.out.print("Apellido del contacto a eliminar: ");
                    String ape = scanner.nextLine();
                    Contacto eliminar = new Contacto(nom, ape, "0000000000", "fake@email.com");
                    agenda.eliminarContacto(eliminar);
                    break;

                case 5:
                    System.out.print("Nombre del contacto: ");
                    String nm = scanner.nextLine();
                    System.out.print("Apellido del contacto: ");
                    String ap = scanner.nextLine();
                    System.out.print("Nuevo teléfono: ");
                    String nuevoTel = scanner.nextLine();
                    agenda.modificarTelefono(nm, ap, nuevoTel);
                    break;

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

