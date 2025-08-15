import java.util.Scanner;

public class Practica1_IPC1 {
    static final int MAX_PERSONAJES = 25;
    static final int MAX_PELEAS = 25;
    static final int MAX_HABILIDADES = 5;

    static String[] nombres = new String[MAX_PERSONAJES];
    static String[] armas = new String[MAX_PERSONAJES];
    static String[][] habilidades = new String[MAX_PERSONAJES][MAX_HABILIDADES];
    static int[] nivelesDePoder = new int[MAX_PERSONAJES];
    static String[] historialDePeleas = new String[MAX_PELEAS];

    static int totalPersonajes = 0;
    static int totalPeleas = 0;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("\n--- MENU PRINCIPAL ---");
            System.out.println("1. Agregar personaje");
            System.out.println("2. Eliminar personaje");
            System.out.println("3. Modificar personaje");
            System.out.println("4. Ver datos de un personaje");
            System.out.println("5. Ver listado de personajes");
            System.out.println("6. Realizar pelea");
            System.out.println("7. Ver historial de peleas");
            System.out.println("8. Datos del estudiante");
            System.out.println("9. Salir");
            System.out.print("Selecciona una opción: ");

            try {
                opcion = scanner.nextInt();
                scanner.nextLine();

                switch (opcion) {
                    case 1 -> agregarPersonaje(scanner);
                    case 2 -> eliminarPersonaje(scanner);
                    case 3 -> modificarPersonaje(scanner);
                    case 4 -> verDatosPersonaje(scanner);
                    case 5 -> verListadoPersonajes();
                    case 6 -> realizarPelea(scanner);
                    case 7 -> verHistorialPeleas();
                    case 8 -> {
                        System.out.println("Nombre: Carlos Didiere Cabrera Rodríguez");
                        System.out.println("Carnet: 202401939");
                    }
                    case 9 -> System.out.println("\u00a1Gracias por usar el programa!");
                    default -> System.out.println("Opción no válida.");
                }
            } catch (Exception e) {
                System.out.println("Entrada inválida. Intenta nuevamente.");
                scanner.nextLine();
                opcion = -1;
            }
        } while (opcion != 9);
    }

    public static void agregarPersonaje(Scanner scanner) {
        if (totalPersonajes < MAX_PERSONAJES) {
            System.out.print("Nombre del personaje: ");
            nombres[totalPersonajes] = scanner.nextLine();

            System.out.print("Arma del personaje: ");
            armas[totalPersonajes] = scanner.nextLine();

            System.out.println("Introduce hasta 5 habilidades del personaje (deja vacío para terminar):");
            for (int i = 0; i < MAX_HABILIDADES; i++) {
                System.out.print("Habilidad " + (i + 1) + ": ");
                String habilidad = scanner.nextLine();
                if (!habilidad.isEmpty()) {
                    habilidades[totalPersonajes][i] = habilidad;
                } else {
                    break;
                }
            }

            System.out.print("Nivel de poder (1-100): ");
            nivelesDePoder[totalPersonajes] = scanner.nextInt();
            scanner.nextLine();

            System.out.println("Personaje agregado exitosamente.");
            totalPersonajes++;
        } else {
            System.out.println("No se pueden agregar más personajes. Límite alcanzado.");
        }
    }

    public static void eliminarPersonaje(Scanner scanner) {
        System.out.print("Ingrese el nombre del personaje a eliminar: ");
        String nombreEliminar = scanner.nextLine();
        boolean encontrado = false;

        for (int i = 0; i < totalPersonajes; i++) {
            if (nombres[i].equalsIgnoreCase(nombreEliminar)) {
                for (int j = i; j < totalPersonajes - 1; j++) {
                    nombres[j] = nombres[j + 1];
                    armas[j] = armas[j + 1];
                    habilidades[j] = habilidades[j + 1];
                    nivelesDePoder[j] = nivelesDePoder[j + 1];
                }
                totalPersonajes--;
                System.out.println("Personaje eliminado.");
                encontrado = true;
                break;
            }
        }
        if (!encontrado) {
            System.out.println("Personaje no encontrado.");
        }
    }

    public static void modificarPersonaje(Scanner scanner) {
        System.out.print("Ingrese el nombre del personaje a modificar: ");
        String nombreModificar = scanner.nextLine();

        for (int i = 0; i < totalPersonajes; i++) {
            if (nombres[i].equalsIgnoreCase(nombreModificar)) {
                System.out.print("Nuevo nombre: ");
                nombres[i] = scanner.nextLine();

                System.out.print("Nueva arma: ");
                armas[i] = scanner.nextLine();

                System.out.println("Nuevas habilidades:");
                for (int j = 0; j < MAX_HABILIDADES; j++) {
                    System.out.print("Habilidad " + (j + 1) + ": ");
                    String habilidad = scanner.nextLine();
                    if (!habilidad.isEmpty()) {
                        habilidades[i][j] = habilidad;
                    } else {
                        habilidades[i][j] = null;
                    }
                }

                System.out.print("Nuevo nivel de poder: ");
                nivelesDePoder[i] = scanner.nextInt();
                scanner.nextLine();

                System.out.println("Personaje modificado.");
                return;
            }
        }
        System.out.println("Personaje no encontrado.");
    }

    public static void verDatosPersonaje(Scanner scanner) {
        System.out.print("Ingrese el nombre del personaje a ver: ");
        String nombre = scanner.nextLine();

        for (int i = 0; i < totalPersonajes; i++) {
            if (nombres[i].equalsIgnoreCase(nombre)) {
                System.out.println("Nombre: " + nombres[i]);
                System.out.println("Arma: " + armas[i]);
                System.out.print("Habilidades: ");
                for (int j = 0; j < MAX_HABILIDADES; j++) {
                    if (habilidades[i][j] != null) {
                        System.out.print(habilidades[i][j] + ", ");
                    }
                }
                System.out.println("\nNivel de poder: " + nivelesDePoder[i]);
                return;
            }
        }
        System.out.println("Personaje no encontrado.");
    }

    public static void verListadoPersonajes() {
        if (totalPersonajes == 0) {
            System.out.println("No hay personajes registrados.");
            return;
        }
        System.out.println("--- LISTADO DE PERSONAJES ---");
        for (int i = 0; i < totalPersonajes; i++) {
            System.out.println((i + 1) + ". " + nombres[i]);
        }
    }

    public static void realizarPelea(Scanner scanner) {
        if (totalPersonajes < 2) {
            System.out.println("Debe haber al menos dos personajes registrados.");
            return;
        }

        System.out.print("Ingrese el índice del primer personaje (0 a " + (totalPersonajes - 1) + "): ");
        int index1 = scanner.nextInt();
        System.out.print("Ingrese el índice del segundo personaje (0 a " + (totalPersonajes - 1) + "): ");
        int index2 = scanner.nextInt();
        scanner.nextLine();

        if (index1 < 0 || index1 >= totalPersonajes || index2 < 0 || index2 >= totalPersonajes || index1 == index2) {
            System.out.println("Indices invalidos.");
            return;
        }

        String peleaInfo = "Pelea entre " + nombres[index1] + " y " + nombres[index2];
        if (totalPeleas < MAX_PELEAS) {
            historialDePeleas[totalPeleas++] = peleaInfo;
            System.out.println("Pelea registrada: " + peleaInfo);
        } else {
            System.out.println("No se pueden registrar más peleas.");
        }
    }

    public static void verHistorialPeleas() {
        if (totalPeleas == 0) {
            System.out.println("No hay peleas registradas.");
            return;
        }
        System.out.println("--- HISTORIAL DE PELEAS ---");
        for (int i = 0; i < totalPeleas; i++) {
            System.out.println((i + 1) + ". " + historialDePeleas[i]);
        }
    }
}
