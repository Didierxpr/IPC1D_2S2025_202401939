package arenausac;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ArenaUSAC arena = new ArenaUSAC(50, 50); // Máx. 50 personajes y 50 batallas
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


                case 2: { // Modificar personaje
                    System.out.println("\n=== MODIFICAR PERSONAJE ===");
                    System.out.print("Ingrese ID o Nombre del personaje: ");
                    String criterio = sc.nextLine();

                    Personaje encontrado = arena.buscar(criterio);

                    if (encontrado == null) {
                        System.out.println("⚠️ Personaje no encontrado.");
                        break;
                    }

                    // Mostrar datos actuales
                    System.out.println("\nDatos actuales del personaje:");
                    System.out.println("ID: " + encontrado.getId() +
                            " | Nombre: " + encontrado.getNombre() +
                            " | Arma: " + encontrado.getArma() +
                            " | HP: " + encontrado.getHp() +
                            " | Ataque: " + encontrado.getAtaque() +
                            " | Velocidad: " + encontrado.getVelocidad() +
                            " | Agilidad: " + encontrado.getAgilidad() +
                            " | Defensa: " + encontrado.getDefensa());

                    // Solicitar nuevos datos
                    System.out.print("\nNuevo arma: ");
                    String nuevaArma = sc.nextLine();

                    System.out.print("Nuevo HP (100-500): ");
                    int nuevoHp = sc.nextInt();
                    if (nuevoHp < 100) {
                        System.out.println("⚠️ Ajustado a 100.");
                        nuevoHp = 100;
                    } else if (nuevoHp > 500) {
                        System.out.println("⚠️ Ajustado a 500.");
                        nuevoHp = 500;
                    }

                    System.out.print("Nuevo Ataque (10-100): ");
                    int nuevoAtaque = sc.nextInt();
                    if (nuevoAtaque < 10) {
                        System.out.println("⚠️ Ajustado a 10.");
                        nuevoAtaque = 10;
                    } else if (nuevoAtaque > 100) {
                        System.out.println("⚠️ Ajustado a 100.");
                        nuevoAtaque = 100;
                    }

                    System.out.print("Nueva Velocidad (1-10): ");
                    int nuevaVelocidad = sc.nextInt();
                    if (nuevaVelocidad < 1) {
                        System.out.println("⚠️ Ajustado a 1.");
                        nuevaVelocidad = 1;
                    } else if (nuevaVelocidad > 10) {
                        System.out.println("⚠️ Ajustado a 10.");
                        nuevaVelocidad = 10;
                    }

                    System.out.print("Nueva Agilidad (1-10): ");
                    int nuevaAgilidad = sc.nextInt();
                    if (nuevaAgilidad < 1) {
                        System.out.println("⚠️ Ajustado a 1.");
                        nuevaAgilidad = 1;
                    } else if (nuevaAgilidad > 10) {
                        System.out.println("⚠️ Ajustado a 10.");
                        nuevaAgilidad = 10;
                    }

                    System.out.print("Nueva Defensa (1-50): ");
                    int nuevaDefensa = sc.nextInt();
                    sc.nextLine(); // limpiar buffer
                    if (nuevaDefensa < 1) {
                        System.out.println("⚠️ Ajustado a 1.");
                        nuevaDefensa = 1;
                    } else if (nuevaDefensa > 50) {
                        System.out.println("⚠️ Ajustado a 50.");
                        nuevaDefensa = 50;
                    }

                    arena.modificarPersonaje(encontrado.getId(), nuevaArma, nuevoHp,
                            nuevoAtaque, nuevaVelocidad, nuevaAgilidad, nuevaDefensa);
                    break;
                }

                case 3: { // Eliminar personaje
                    System.out.println("\n=== ELIMINAR PERSONAJE ===");
                    System.out.print("Ingrese ID o Nombre del personaje: ");
                    String criterio = sc.nextLine();

                    Personaje encontrado = arena.buscar(criterio);

                    if (encontrado == null) {
                        System.out.println("⚠️ Personaje no encontrado.");
                        break;
                    }

                    // Confirmación
                    System.out.println("\nEstá a punto de eliminar a:");
                    System.out.println("ID: " + encontrado.getId() +
                            " | Nombre: " + encontrado.getNombre() +
                            " | Arma: " + encontrado.getArma() +
                            " | HP: " + encontrado.getHp());
                    System.out.print("¿Está seguro? (s/n): ");
                    String confirmacion = sc.nextLine();

                    if (confirmacion.equalsIgnoreCase("s")) {
                        arena.eliminarPersonaje(criterio);
                    } else {
                        System.out.println("❌ Eliminación cancelada.");
                    }
                    break;
                }

                case 4: // Mostrar personajes
                    arena.mostrarPersonajes();
                    break;

                case 5: { // Simular batalla automática
                    System.out.println("\n=== SIMULAR BATALLA AUTOMÁTICA ===");

                    // Selección del primer personaje
                    System.out.print("Ingrese ID o Nombre del primer personaje: ");
                    String criterio1 = sc.nextLine();
                    Personaje p1 = arena.buscar(criterio1);

                    if (p1 == null || !p1.estaVivo()) {
                        System.out.println("⚠️ Personaje 1 no encontrado o no está vivo.");
                        break;
                    }

                    // Selección del segundo personaje
                    System.out.print("Ingrese ID o Nombre del segundo personaje: ");
                    String criterio2 = sc.nextLine();
                    Personaje p2 = arena.buscar(criterio2);

                    if (p2 == null || !p2.estaVivo()) {
                        System.out.println("⚠️ Personaje 2 no encontrado o no está vivo.");
                        break;
                    }

                    // Validación de personajes distintos
                    if (p1.getId() == p2.getId()) {
                        System.out.println("⚠️ Debe elegir personajes distintos.");
                        break;
                    }

                    // Iniciar batalla
                    System.out.println("\n⚔️ Iniciando batalla entre " + p1.getNombre() + " y " + p2.getNombre() + "...");
                    arena.simularBatalla(p1.getId(), p2.getId());

                    break;
                }


                case 6: { // ver historial de batallas

                    System.out.println("\n=== HISTORIAL DE BATALLAS ===");

                    if (arena.getHistorial().getContador() == 0) {
                        System.out.println("⚠️ No hay batallas registradas.");
                        break;
                    }

                    // Mostrar batallas
                    Batalla[] batallas = arena.getHistorial().getBatallas();
                    for (int i = 0; i < arena.getHistorial().getContador(); i++) {
                        Batalla b = batallas[i];
                        System.out.println("Número de batalla: " + b.getIdBatalla() +
                                " | Fecha: " + b.getFechaHora() +
                                " | Participantes: " + b.getJugador1().getNombre() +
                                " vs " + b.getJugador2().getNombre() +
                                " | Ganador: " + b.getGanador());
                    }

                    // Preguntar por bitácora
                    System.out.print("\n¿Desea ver la bitácora de alguna batalla? (s/n): ");
                        String opcionHistorial = sc.nextLine();

                        if (opcionHistorial.equalsIgnoreCase("s")) {
                        System.out.print("Ingrese el número de batalla: ");
                        int idBatalla = sc.nextInt();
                        sc.nextLine();
                        arena.getHistorial().mostrarBitacoraBatalla(idBatalla);
                    }
                    break;
            }



                case 7: { // Buscar personaje
                    System.out.println("\n=== BUSCAR PERSONAJE ===");
                    System.out.print("Ingrese ID o Nombre del personaje: ");
                    String criterioBusqueda = sc.nextLine();

                    Personaje encontrado = arena.buscar(criterioBusqueda);

                    if (encontrado == null) {
                        System.out.println("⚠️ Personaje no encontrado.");
                    } else {
                        System.out.println("\n=== Personaje encontrado ===");
                        System.out.println("ID: " + encontrado.getId() +
                                " | Nombre: " + encontrado.getNombre() +
                                " | Arma: " + encontrado.getArma() +
                                " | HP: " + encontrado.getHp() +
                                " | Ataque: " + encontrado.getAtaque() +
                                " | Velocidad: " + encontrado.getVelocidad() +
                                " | Agilidad: " + encontrado.getAgilidad() +
                                " | Defensa: " + encontrado.getDefensa() +
                                " | Vivo: " + (encontrado.estaVivo() ? "Sí" : "No"));
                    }
                    break;
                }

                case 8: { // Guardar y Cargar Estado del Sistema
                    System.out.println("\n=== GUARDAR / CARGAR ESTADO ===");
                    System.out.println("1. Guardar estado");
                    System.out.println("2. Cargar estado");
                    System.out.print("Seleccione una opción: ");
                    int opcionGuardar = sc.nextInt();
                    sc.nextLine(); // limpiar buffer

                    if (opcionGuardar == 1) {
                        arena.guardarEstado("estado.txt");
                    } else if (opcionGuardar == 2) {
                        arena.cargarEstado("estado.txt");
                    } else {
                        System.out.println("⚠️ Opción no válida.");
                    }
                    break;
                }

                case 9: // Ver datos del estudiante
                    System.out.println("\n=== Datos del Estudiante ===");
                    System.out.println("Nombre: Didiere Cabrera");
                    System.out.println("Carné: 202401939");
                    System.out.println("Curso: Introducción a la Programación y Computación 1");
                    System.out.println("Sección: D");
                    break;

                case 0: // Salir
                    System.out.println("Saliendo del sistema...");
                    salir = true;
                    break;

                default:
                    System.out.println("Opción inválida. Intente de nuevo.");
            }
        }

        sc.close();
    }
}

