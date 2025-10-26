package utilidades;

/**
 * Clase utilitaria para manejo de vectores/arreglos dinámicos
 * Reemplaza funcionalidades de ArrayList sin usar librerías prohibidas
 */
public class Vectores {

    // ==================== MÉTODOS GENÉRICOS PARA OBJETOS ====================

    /**
     * Agrega un elemento al final del arreglo
     * @param arreglo Arreglo original
     * @param elemento Elemento a agregar
     * @return Nuevo arreglo con el elemento agregado
     */
    public static Object[] agregar(Object[] arreglo, Object elemento) {
        if (arreglo == null) {
            arreglo = new Object[0];
        }

        Object[] nuevoArreglo = new Object[arreglo.length + 1];

        // Copiar elementos existentes
        for (int i = 0; i < arreglo.length; i++) {
            nuevoArreglo[i] = arreglo[i];
        }

        // Agregar nuevo elemento
        nuevoArreglo[arreglo.length] = elemento;

        return nuevoArreglo;
    }

    /**
     * Elimina un elemento en una posición específica
     * @param arreglo Arreglo original
     * @param indice Índice del elemento a eliminar
     * @return Nuevo arreglo sin el elemento
     */
    public static Object[] eliminar(Object[] arreglo, int indice) {
        if (arreglo == null || indice < 0 || indice >= arreglo.length) {
            return arreglo;
        }

        Object[] nuevoArreglo = new Object[arreglo.length - 1];

        // Copiar elementos antes del índice
        for (int i = 0; i < indice; i++) {
            nuevoArreglo[i] = arreglo[i];
        }

        // Copiar elementos después del índice
        for (int i = indice + 1; i < arreglo.length; i++) {
            nuevoArreglo[i - 1] = arreglo[i];
        }

        return nuevoArreglo;
    }

    /**
     * Actualiza un elemento en una posición específica
     * @param arreglo Arreglo original
     * @param indice Índice del elemento a actualizar
     * @param nuevoElemento Nuevo elemento
     * @return Arreglo actualizado
     */
    public static Object[] actualizar(Object[] arreglo, int indice, Object nuevoElemento) {
        if (arreglo != null && indice >= 0 && indice < arreglo.length) {
            arreglo[indice] = nuevoElemento;
        }
        return arreglo;
    }

    /**
     * Busca el índice de un elemento en el arreglo
     * @param arreglo Arreglo donde buscar
     * @param elemento Elemento a buscar
     * @return Índice del elemento o -1 si no se encuentra
     */
    public static int buscarIndice(Object[] arreglo, Object elemento) {
        if (arreglo == null) {
            return -1;
        }

        for (int i = 0; i < arreglo.length; i++) {
            if (arreglo[i] != null && arreglo[i].equals(elemento)) {
                return i;
            }
        }

        return -1;
    }

    /**
     * Verifica si el arreglo está vacío
     * @param arreglo Arreglo a verificar
     * @return true si está vacío, false si tiene elementos
     */
    public static boolean estaVacio(Object[] arreglo) {
        return arreglo == null || arreglo.length == 0;
    }

    /**
     * Obtiene el tamaño del arreglo
     * @param arreglo Arreglo a medir
     * @return Tamaño del arreglo
     */
    public static int tamanio(Object[] arreglo) {
        return (arreglo == null) ? 0 : arreglo.length;
    }

    /**
     * Verifica si un elemento existe en el arreglo
     * @param arreglo Arreglo donde buscar
     * @param elemento Elemento a buscar
     * @return true si existe, false si no
     */
    public static boolean contiene(Object[] arreglo, Object elemento) {
        return buscarIndice(arreglo, elemento) != -1;
    }


    // ==================== MÉTODOS PARA STRINGS ====================

    /**
     * Agrega un String al arreglo
     * @param arreglo Arreglo de Strings
     * @param elemento String a agregar
     * @return Nuevo arreglo con el String agregado
     */
    public static String[] agregarString(String[] arreglo, String elemento) {
        if (arreglo == null) {
            arreglo = new String[0];
        }

        String[] nuevoArreglo = new String[arreglo.length + 1];

        for (int i = 0; i < arreglo.length; i++) {
            nuevoArreglo[i] = arreglo[i];
        }

        nuevoArreglo[arreglo.length] = elemento;

        return nuevoArreglo;
    }

    /**
     * Elimina un String del arreglo por índice
     * @param arreglo Arreglo de Strings
     * @param indice Índice a eliminar
     * @return Nuevo arreglo sin el elemento
     */
    public static String[] eliminarString(String[] arreglo, int indice) {
        if (arreglo == null || indice < 0 || indice >= arreglo.length) {
            return arreglo;
        }

        String[] nuevoArreglo = new String[arreglo.length - 1];

        for (int i = 0; i < indice; i++) {
            nuevoArreglo[i] = arreglo[i];
        }

        for (int i = indice + 1; i < arreglo.length; i++) {
            nuevoArreglo[i - 1] = arreglo[i];
        }

        return nuevoArreglo;
    }


    // ==================== MÉTODOS PARA ENTEROS ====================

    /**
     * Agrega un entero al arreglo
     * @param arreglo Arreglo de enteros
     * @param elemento Entero a agregar
     * @return Nuevo arreglo con el entero agregado
     */
    public static int[] agregarInt(int[] arreglo, int elemento) {
        if (arreglo == null) {
            arreglo = new int[0];
        }

        int[] nuevoArreglo = new int[arreglo.length + 1];

        for (int i = 0; i < arreglo.length; i++) {
            nuevoArreglo[i] = arreglo[i];
        }

        nuevoArreglo[arreglo.length] = elemento;

        return nuevoArreglo;
    }

    /**
     * Suma todos los elementos de un arreglo de enteros
     * @param arreglo Arreglo de enteros
     * @return Suma total
     */
    public static int sumar(int[] arreglo) {
        if (arreglo == null) {
            return 0;
        }

        int suma = 0;
        for (int i = 0; i < arreglo.length; i++) {
            suma += arreglo[i];
        }

        return suma;
    }


    // ==================== MÉTODOS DE ORDENAMIENTO ====================

    /**
     * Ordena un arreglo de Strings alfabéticamente (Bubble Sort)
     * @param arreglo Arreglo a ordenar
     * @return Arreglo ordenado
     */
    public static String[] ordenarStrings(String[] arreglo) {
        if (arreglo == null || arreglo.length <= 1) {
            return arreglo;
        }

        String[] copia = new String[arreglo.length];
        for (int i = 0; i < arreglo.length; i++) {
            copia[i] = arreglo[i];
        }

        // Bubble Sort
        for (int i = 0; i < copia.length - 1; i++) {
            for (int j = 0; j < copia.length - i - 1; j++) {
                if (copia[j] != null && copia[j + 1] != null) {
                    if (copia[j].compareTo(copia[j + 1]) > 0) {
                        String temp = copia[j];
                        copia[j] = copia[j + 1];
                        copia[j + 1] = temp;
                    }
                }
            }
        }

        return copia;
    }

    /**
     * Ordena un arreglo de enteros de menor a mayor (Bubble Sort)
     * @param arreglo Arreglo a ordenar
     * @return Arreglo ordenado
     */
    public static int[] ordenarInt(int[] arreglo) {
        if (arreglo == null || arreglo.length <= 1) {
            return arreglo;
        }

        int[] copia = new int[arreglo.length];
        for (int i = 0; i < arreglo.length; i++) {
            copia[i] = arreglo[i];
        }

        // Bubble Sort
        for (int i = 0; i < copia.length - 1; i++) {
            for (int j = 0; j < copia.length - i - 1; j++) {
                if (copia[j] > copia[j + 1]) {
                    int temp = copia[j];
                    copia[j] = copia[j + 1];
                    copia[j + 1] = temp;
                }
            }
        }

        return copia;
    }


    // ==================== MÉTODOS DE BÚSQUEDA ====================

    /**
     * Búsqueda lineal de un entero
     * @param arreglo Arreglo donde buscar
     * @param elemento Elemento a buscar
     * @return Índice del elemento o -1 si no existe
     */
    public static int buscarInt(int[] arreglo, int elemento) {
        if (arreglo == null) {
            return -1;
        }

        for (int i = 0; i < arreglo.length; i++) {
            if (arreglo[i] == elemento) {
                return i;
            }
        }

        return -1;
    }

    /**
     * Búsqueda lineal de un String
     * @param arreglo Arreglo donde buscar
     * @param elemento Elemento a buscar
     * @return Índice del elemento o -1 si no existe
     */
    public static int buscarString(String[] arreglo, String elemento) {
        if (arreglo == null || elemento == null) {
            return -1;
        }

        for (int i = 0; i < arreglo.length; i++) {
            if (arreglo[i] != null && arreglo[i].equals(elemento)) {
                return i;
            }
        }

        return -1;
    }


    // ==================== MÉTODOS DE CONVERSIÓN ====================

    /**
     * Convierte un arreglo de Object a String[]
     * @param arreglo Arreglo de objetos
     * @return Arreglo de Strings
     */
    public static String[] convertirAString(Object[] arreglo) {
        if (arreglo == null) {
            return new String[0];
        }

        String[] resultado = new String[arreglo.length];
        for (int i = 0; i < arreglo.length; i++) {
            resultado[i] = (arreglo[i] != null) ? arreglo[i].toString() : null;
        }

        return resultado;
    }

    /**
     * Imprime un arreglo de objetos (útil para debug)
     * @param arreglo Arreglo a imprimir
     */
    public static void imprimir(Object[] arreglo) {
        if (arreglo == null || arreglo.length == 0) {
            System.out.println("[]");
            return;
        }

        System.out.print("[");
        for (int i = 0; i < arreglo.length; i++) {
            System.out.print(arreglo[i]);
            if (i < arreglo.length - 1) {
                System.out.print(", ");
            }
        }
        System.out.println("]");
    }
}
