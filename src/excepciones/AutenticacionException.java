package excepciones;

/**
 * Excepción lanzada cuando el usuario no existe o la contraseña es incorrecta.
 */
public class AutenticacionException extends Exception {
    public AutenticacionException(String mensaje) {
        super(mensaje);
    }
}
