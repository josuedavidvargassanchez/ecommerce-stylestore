package modelo;

public class Administrador extends Usuario {
    private static final long serialVersionUID = 1L;

    private String codigoAdmin;

    public Administrador(String id, String nombre, String email,
                         String password, String codigoAdmin) {
        super(id, nombre, email, password);
        this.codigoAdmin = codigoAdmin;
    }

    @Override
    public String getRol() {
        return "ADMINISTRADOR";
    }

    public String getCodigoAdmin() { return codigoAdmin; }
    public void setCodigoAdmin(String codigoAdmin) { this.codigoAdmin = codigoAdmin; }

    @Override
    public String toString() {
        return "[ADMIN] " + getNombre() + " | " + getEmail();
    }
}
