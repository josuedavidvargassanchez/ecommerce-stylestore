package excepciones;

/**
 * Excepción lanzada cuando no hay stock suficiente de un producto.
 * Aplica: Manejo de Excepciones personalizadas
 */
public class StockInsuficienteException extends Exception {
    public StockInsuficienteException(String mensaje) {
        super(mensaje);
    }
}
