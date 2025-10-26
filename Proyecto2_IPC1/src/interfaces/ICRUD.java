package interfaces;

/**
 * Interface que define las operaciones CRUD básicas
 * (Create, Read, Update, Delete)
 * Implementada por los controladores del sistema
 *
 * @param <T> Tipo de objeto que manejará el CRUD
 */
public interface ICRUD<T> {

    /**
     * Crea un nuevo registro en el sistema
     * @param objeto Objeto a crear
     * @return true si se creó exitosamente, false si falló
     */
    boolean crear(T objeto);

    /**
     * Obtiene todos los registros del sistema
     * @return Array con todos los objetos
     */
    T[] obtenerTodos();

    /**
     * Busca un registro por su código único
     * @param codigo Código del registro a buscar
     * @return Objeto encontrado o null si no existe
     */
    T buscarPorCodigo(String codigo);

    /**
     * Actualiza un registro existente
     * @param codigo Código del registro a actualizar
     * @param objetoActualizado Objeto con los nuevos datos
     * @return true si se actualizó exitosamente, false si falló
     */
    boolean actualizar(String codigo, T objetoActualizado);

    /**
     * Elimina un registro del sistema
     * @param codigo Código del registro a eliminar
     * @return true si se eliminó exitosamente, false si falló
     */
    boolean eliminar(String codigo);

    /**
     * Verifica si existe un registro con el código especificado
     * @param codigo Código a verificar
     * @return true si existe, false si no existe
     */
    boolean existe(String codigo);

    /**
     * Obtiene la cantidad total de registros
     * @return Cantidad de registros
     */
    int contarRegistros();

    /**
     * Limpia todos los registros del sistema
     * @return true si se limpió exitosamente
     */
    boolean limpiarTodo();
}
