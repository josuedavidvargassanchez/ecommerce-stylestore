package modelo;

import java.io.Serializable;

/**
 * Clase abstracta base para todos los usuarios del sistema.
 * Aplica: Abstracción, Encapsulación
 */
public abstract class Usuario implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    private String nombre;
    private String email;
    private String password;

    public Usuario(String id, String nombre, String email, String password) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.password = password;
    }

    // Getters y Setters — Encapsulación
    public String getId() { return id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    // Método abstracto — Abstracción y Polimorfismo
    public abstract String getRol();

    @Override
    public String toString() {
        return nombre + " (" + email + ") — Rol: " + getRol();
    }
}
