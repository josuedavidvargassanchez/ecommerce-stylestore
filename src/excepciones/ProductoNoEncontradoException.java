package excepciones;

/**
 * Excepción lanzada cuando no se encuentra un producto en el sistema.
 */
public class ProductoNoEncontradoException extends Exception {
    public ProductoNoEncontradoException(String mensaje) {
        super(mensaje);
    }
}
