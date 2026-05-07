package datos;

import modelo.*;
import java.io.*;
import java.util.ArrayList;

/**
 * Capa de persistencia — guarda y carga datos usando serialización Java (.dat).
 * Aplica: Manejo de Excepciones (try/catch)
 */
public class GestorDatos {

    private static final String DIR_DATOS = "datos/";
    private static final String ARCHIVO_PRODUCTOS = DIR_DATOS + "productos.dat";
    private static final String ARCHIVO_USUARIOS = DIR_DATOS + "usuarios.dat";
    private static final String ARCHIVO_PEDIDOS = DIR_DATOS + "pedidos.dat";

    // ─── PRODUCTOS ──────────────────────────────────────────────

    @SuppressWarnings("unchecked")
    public static ArrayList<Producto> cargarProductos() {
        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(ARCHIVO_PRODUCTOS))) {
            return (ArrayList<Producto>) ois.readObject();
        } catch (FileNotFoundException e) {
            return new ArrayList<>();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error al cargar productos: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public static void guardarProductos(ArrayList<Producto> productos) {
        crearDirectorioSiNoExiste();
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(ARCHIVO_PRODUCTOS))) {
            oos.writeObject(productos);
        } catch (IOException e) {
            System.err.println("Error al guardar productos: " + e.getMessage());
        }
    }

    // ─── USUARIOS ───────────────────────────────────────────────

    @SuppressWarnings("unchecked")
    public static ArrayList<Usuario> cargarUsuarios() {
        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(ARCHIVO_USUARIOS))) {
            return (ArrayList<Usuario>) ois.readObject();
        } catch (FileNotFoundException e) {
            return new ArrayList<>();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error al cargar usuarios: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public static void guardarUsuarios(ArrayList<Usuario> usuarios) {
        crearDirectorioSiNoExiste();
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(ARCHIVO_USUARIOS))) {
            oos.writeObject(usuarios);
        } catch (IOException e) {
            System.err.println("Error al guardar usuarios: " + e.getMessage());
        }
    }

    // ─── PEDIDOS ────────────────────────────────────────────────

    @SuppressWarnings("unchecked")
    public static ArrayList<Pedido> cargarPedidos() {
        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(ARCHIVO_PEDIDOS))) {
            return (ArrayList<Pedido>) ois.readObject();
        } catch (FileNotFoundException e) {
            return new ArrayList<>();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error al cargar pedidos: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public static void guardarPedidos(ArrayList<Pedido> pedidos) {
        crearDirectorioSiNoExiste();
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(ARCHIVO_PEDIDOS))) {
            oos.writeObject(pedidos);
        } catch (IOException e) {
            System.err.println("Error al guardar pedidos: " + e.getMessage());
        }
    }

    // ─── UTILIDAD ───────────────────────────────────────────────

    private static void crearDirectorioSiNoExiste() {
        File dir = new File(DIR_DATOS);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }
}
