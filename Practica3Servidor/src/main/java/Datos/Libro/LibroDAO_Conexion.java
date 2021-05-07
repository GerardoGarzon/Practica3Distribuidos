package Datos.Libro;

import Datos.Conexion;
import Entidades.Libro;
import Entidades.Usuario;

import static Datos.Conexion.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LibroDAO_Conexion implements LibroDAO {

    private static final String SQL_SELECT_ITEM = "SELECT * FROM libro WHERE ISNB=?";
    private static final String SQL_SELECT = "SELECT * FROM libro";
    private static final String SQL_SELECT_LIBRO_DISPONIBLE = "select libro.* from libro where libro.ISBN NOT in( select id_libro from sesion );";

    @Override
    public List<Libro> select() {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Libro libroDTO = null;
        List<Libro> libros = new ArrayList<>();

        try {
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_SELECT);
            rs = stmt.executeQuery();

            while (rs.next()) {
                libroDTO = new Libro();

                libroDTO.setISBN(rs.getString("ISBN"));
                libroDTO.setNombre(rs.getString("nombre"));
                libroDTO.setAutor(rs.getString("autor"));
                libroDTO.setEditorial(rs.getString("editorial"));
                libroDTO.setPrecio(rs.getFloat("precio"));
                libroDTO.setPortada(rs.getString("portada"));

                libros.add(libroDTO);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                close(rs);
                close(stmt);
                close(conn);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        return libros;
    }

    @Override
    public Libro select(Libro libro) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Libro libroDTO = null;

        try {
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_SELECT_ITEM);
            stmt.setString(1, libro.getISBN());
            rs = stmt.executeQuery();

            while (rs.next()) {
                libroDTO = new Libro();

                libroDTO.setISBN(rs.getString("ISBN"));
                libroDTO.setNombre(rs.getString("nombre"));
                libroDTO.setAutor(rs.getString("autor"));
                libroDTO.setEditorial(rs.getString("editorial"));
                libroDTO.setPrecio(rs.getFloat("precio"));
                libroDTO.setPortada(rs.getString("portada"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                close(rs);
                close(stmt);
                close(conn);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return libroDTO;
    }

    @Override
    public Libro select_disponible() {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Libro libroDTO = null;
        List<Libro> libros = new ArrayList<>();

        try {
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_SELECT_LIBRO_DISPONIBLE);
            rs = stmt.executeQuery();

            while (rs.next()) {
                libroDTO = new Libro();

                libroDTO.setISBN(rs.getString("ISBN"));
                libroDTO.setNombre(rs.getString("nombre"));
                libroDTO.setAutor(rs.getString("autor"));
                libroDTO.setEditorial(rs.getString("editorial"));
                libroDTO.setPrecio(rs.getFloat("precio"));
                libroDTO.setPortada(rs.getString("portada"));

                libros.add(libroDTO);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                close(rs);
                close(stmt);
                close(conn);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        if(libros.size() == 0){
            return null;
        } else {
            return libros.get(0);
        }
    }

    @Override
    public void insert(Libro libro) {
    }

    @Override
    public void update(Libro libro) {
    }

    @Override
    public void delete(Libro libro) {
    }
}
