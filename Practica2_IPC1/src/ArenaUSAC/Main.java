package ArenaUSAC;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        arenausac arena = new arenausac(50, 50); // Máx. 50 personajes y 50 batallas
        boolean salir = false;

        while (!salir) {
            System.out.println("\n=== ARENA USAC ===");
            System.out.println("1. Agregar personaje");
            System.out.println("2. Modificar personaje");
            System.out.println("3. Eliminar personaje");
            System.out.println("4. Ver personajes");
            System.out.println("5. Simular batalla");
            System.out.println("6. Ver historial de batallas");
            System.out.println("7. Buscar personaje por nombre");
            System.out.println("8. Buscar batallas de un personaje");
            System.out.println("9. Ver datos del estudiante");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");

            int opcion = sc.nextInt();
            sc.nextLine(); // limpiar buffer

            switch (opcion) {
                case 1: // Agregar personaje
                    System.out.print("Nombre: ");
                    String nombre = sc.nextLine();
                    System.out.print("Arma: ");
                    String arma = sc.nextLine();

                    // HP
                    System.out.print("HP (100-500): ");
                    int hp = sc.nextInt();
                    if (hp < 100) {
                        System.out.println("⚠️ HP fuera de rango, ajustado a 100.");
                        hp = 100;
                    } else if (hp > 500) {
                        System.out.println("⚠️ HP fuera de rango, ajustado a 500.");
                        hp = 500;
                    }

                    // Ataque
                    System.out.print("Ataque (10-100): ");
                    int ataque = sc.nextInt();
                    if (ataque < 10) {
                        System.out.println("⚠️ Ataque fuera de rango, ajustado a 10.");
                        ataque = 10;
                    } else if (ataque > 100) {
                        System.out.println("⚠️ Ataque fuera de rango, ajustado a 100.");
                        ataque = 100;
                    }

                    // Velocidad
                    System.out.print("Velocidad (1-10): ");
                    int velocidad = sc.nextInt();
                    if (velocidad < 1) {
                        System.out.println("⚠️ Velocidad fuera de rango, ajustada a 1.");
                        velocidad = 1;
                    } else if (velocidad > 10) {
                        System.out.println("⚠️ Velocidad fuera de rango, ajustada a 10.");
                        velocidad = 10;
                    }

                    // Agilidad
                    System.out.print("Agilidad (1-10): ");
                    int agilidad = sc.nextInt();
                    if (agilidad < 1) {
                        System.out.println("⚠️ Agilidad fuera de rango, ajustada a 1.");
                        agilidad = 1;
                    } else if (agilidad > 10) {
                        System.out.println("⚠️ Agilidad fuera de rango, ajustada a 10.");
                        agilidad = 10;
                    }

                    // Defensa
                    System.out.print("Defensa (1-50): ");
                    int defensa = sc.nextInt();
                    if (defensa < 1) {
                        System.out.println("⚠️ Defensa fuera de rango, ajustada a 1.");
                        defensa = 1;
                    } else if (defensa > 50) {
                        System.out.println("⚠️ Defensa fuera de rango, ajustada a 50.");
                        defensa = 50;
                    }

                    arena.agregarPersonaje(nombre, arma, hp, ataque, velocidad, agilidad, defensa);
                    break;


                case 2: // Modificar personaje
                    System.out.print("Ingrese ID del personaje a modificar: ");
                    int idMod = sc.nextInt();
                    sc.nextLine();

                    Personaje p = arena.buscarPorId(idMod);
                    if (p == null) {
                        System.out.println("⚠️ No se encontró un personaje con ID " + idMod);
                        break;
                    }

                    // Mostrar datos actuales
                    System.out.println("\n=== Datos actuales del personaje ===");
                    System.out.println("ID: " + p.getId() +
                            " | Nombre: " + p.getNombre() +
                            " | Arma: " + p.getArma() +
                            " | HP: " + p.getHp() +
                            " | Ataque: " + p.getAtaque() +
                            " | Velocidad: " + p.getVelocidad() +
                            " | Agilidad: " + p.getAgilidad() +
                            " | Defensa: " + p.getDefensa());

                    // Solicitar nuevos valores
                    System.out.print("\nNuevo arma: ");
                    String nuevaArma = sc.nextLine();

                    System.out.print("Nuevo HP (100-500): ");
                    int nuevoHp = sc.nextInt();
                    if (nuevoHp < 100) {
                        System.out.println("⚠️ HP fuera de rango, ajustado a 100.");
                        nuevoHp = 100;
                    } else if (nuevoHp > 500) {
                        System.out.println("⚠️ HP fuera de rango, ajustado a 500.");
                        nuevoHp = 500;
                    }

                    System.out.print("Nuevo Ataque (10-100): ");
                    int nuevoAtaque = sc.nextInt();
                    if (nuevoAtaque < 10) {
                        System.out.println("⚠️ Ataque fuera de rango, ajustado a 10.");
                        nuevoAtaque = 10;
                    } else if (nuevoAtaque > 100) {
                        System.out.println("⚠️ Ataque fuera de rango, ajustado a 100.");
                        nuevoAtaque = 100;
                    }

                    System.out.print("Nueva Velocidad (1-10): ");
                    int nuevaVelocidad = sc.nextInt();
                    if (nuevaVelocidad < 1) {
                        System.out.println("⚠️ Velocidad fuera de rango, ajustada a 1.");
                        nuevaVelocidad = 1;
                    } else if (nuevaVelocidad > 10) {
                        System.out.println("⚠️ Velocidad fuera de rango, ajustada a 10.");
                        nuevaVelocidad = 10;
                    }

                    System.out.print("Nueva Agilidad (1-10): ");
                    int nuevaAgilidad = sc.nextInt();
                    if (nuevaAgilidad < 1) {
                        System.out.println("⚠️ Agilidad fuera de rango, ajustada a 1.");
                        nuevaAgilidad = 1;
                    } else if (nuevaAgilidad > 10) {
                        System.out.println("⚠️ Agilidad fuera de rango, ajustada a 10.");
                        nuevaAgilidad = 10;
                    }

                    System.out.print("Nueva Defensa (1-50): ");
                    int nuevaDefensa = sc.nextInt();
                    if (nuevaDefensa < 1) {
                        System.out.println("⚠️ Defensa fuera de rango, ajustada a 1.");
                        nuevaDefensa = 1;
                    } else if (nuevaDefensa > 50) {
                        System.out.println("⚠️ Defensa fuera de rango, ajustada a 50.");
                        nuevaDefensa = 50;
                    }

                    // Aplicar cambios
                    arena.modificarPersonaje(idMod, nuevaArma, nuevoHp, nuevoAtaque,
                            nuevaVelocidad, nuevaAgilidad, nuevaDefensa);
                    break;


                case 3: // Eliminar personaje
                    System.out.println("\n=== ELIMINAR PERSONAJE ===");
                    System.out.print("Ingrese ID o Nombre del personaje: ");
                    String criterio = sc.nextLine();

                    // Buscar personaje antes de confirmar
                    Personaje encontrado = null;
                    try {
                        int id = Integer.parseInt(criterio);
                        encontrado = arena.buscarPorId(id);
                    } catch (NumberFormatException e) {
                        // Buscar por nombre
                        for (int i = 0; i < 50; i++) { // máx. 50 personajes
                            Personaje candidato = arena.buscarPorId(i + 1);
                            if (candidato != null && candidato.getNombre().equalsIgnoreCase(criterio)) {
                                encontrado = candidato;
                                break;
                            }
                        }
                    }

                    if (encontrado == null) {
                        System.out.println("⚠️ Personaje no encontrado.");
                        break;
                    }

                    // Mostrar datos y pedir confirmación
                    System.out.println("\nEstá a punto de eliminar a:");
                    System.out.println("ID: " + encontrado.getId() +
                            " | Nombre: " + encontrado.getNombre() +
                            " | Arma: " + encontrado.getArma() +
                            " | HP: " + encontrado.getHp());
                    System.out.print("¿Está seguro? (s/n): ");
                    String confirmacion = sc.nextLine();

                    if (confirmacion.equalsIgnoreCase("s")) {
                        arena.eliminarPersonaje(criterio); // Usa el método mejorado en ArenaUSAC
                    } else {
                        System.out.println("❌ Eliminación cancelada.");
                    }
                    break;


                case 4: // Mostrar personajes
                    arena.mostrarPersonajes();
                    break;

                case 5: // Simular batalla
                    System.out.print("Ingrese ID del primer personaje: ");
                    int id1 = sc.nextInt();
                    System.out.print("Ingrese ID del segundo personaje: ");
                    int id2 = sc.nextInt();
                    arena.simularBatalla(id1, id2);
                    break;

                case 6: // Ver historial de batallas
                    arena.mostrarHistorial();
                    break;

                case 7: // Buscar personaje por nombre
                    System.out.print("Ingrese nombre del personaje: ");
                    String buscarNombre = sc.nextLine();
                    arena.buscarPorNombre(buscarNombre);
                    break;

                case 8: // Buscar batallas de un personaje
                    System.out.print("Ingrese nombre del personaje: ");
                    String nombreBusqueda = sc.nextLine();
                    arena.buscarBatallasDePersonaje(nombreBusqueda);
                    break;

                case 9: // Ver datos del estudiante
                    System.out.println("\n=== Datos del Estudiante ===");
                    System.out.println("Nombre: Alexis Trujillo");
                    System.out.println("Carné: 202401884");
                    System.out.println("Curso: Introducción a la Programación y Computación 1");
                    System.out.println("Sección: B");
                    break;

                case 0: // Salir
                    System.out.println("👋 Saliendo del sistema...");
                    salir = true;
                    break;

                default:
                    System.out.println("⚠️ Opción inválida. Intente de nuevo.");
            }
        }

        sc.close();
    }
}

