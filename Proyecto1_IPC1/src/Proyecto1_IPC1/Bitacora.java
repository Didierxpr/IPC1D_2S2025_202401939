package Proyecto1_IPC1;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Bitacora {

    public static void registrar(String tipoAccion, boolean fueCorrecta, String usuario) {
        try (FileWriter writer = new FileWriter("bitacora.txt", true)) {
            LocalDateTime ahora = LocalDateTime.now();
            DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String timestamp = ahora.format(formato);

            String estado = fueCorrecta ? "Correcta" : "Errónea";

            writer.write("[" + timestamp + "] Acción: " + tipoAccion + " | Estado: " + estado + " | Usuario: " + usuario + "\n");
        } catch (IOException e) {
            System.out.println("No se pudo escribir en la bitácora.");
        }
    }
}
