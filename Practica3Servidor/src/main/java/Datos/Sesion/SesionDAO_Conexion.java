package Datos.Sesion;

import Entidades.Sesion;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import static Datos.Conexion.close;
import static Datos.Conexion.getConnection;

public class SesionDAO_Conexion implements SesionDAO {

    private static final String SQL_INSERT = "INSERT INTO sesion(id_pedido, id_libro) VALUES(?,?)";
    private static final String SQL_DELETE = "delete from sesion";

    @Override
    public List<Sesion> select() {
        return null;
    }

    @Override
    public Sesion select(Sesion sesion) {
        return null;
    }

    @Override
    public void insert(Sesion sesion) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = getConnection();
            stmt = conn.prepareStatement(SQL_INSERT);
            stmt.setInt(1, sesion.getIdPedido());
            stmt.setString(2, sesion.getIdLibro());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                close(stmt);
                close(conn);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public void update(Sesion sesion) {

    }

    @Override
    public void delete(Sesion sesion) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = getConnection();
            stmt = conn.prepareStatement(SQL_DELETE);

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                close(stmt);
                close(conn);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
}
