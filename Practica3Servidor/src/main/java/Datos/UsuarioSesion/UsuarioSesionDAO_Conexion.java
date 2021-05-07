package Datos.UsuarioSesion;

import Entidades.UsuarioSesion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import static Datos.Conexion.close;
import static Datos.Conexion.getConnection;

public class UsuarioSesionDAO_Conexion implements UsuarioSesionDAO {

    private static final String SQL_INSERT = "insert into usuariosesion(id_usuario, id_pedido) values (?,?);";
    private static final String SQL_DELETE = "delete from usuariosesion";

    @Override
    public List<UsuarioSesion> select() {
        return null;
    }

    @Override
    public UsuarioSesion select(UsuarioSesion usuarioSesion) {
        return null;
    }

    @Override
    public void insert(UsuarioSesion usuarioSesion) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = getConnection();
            stmt = conn.prepareStatement(SQL_INSERT);
            stmt.setInt(1, usuarioSesion.getIdUsuario());
            stmt.setInt(2, usuarioSesion.getIdPedido());

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
    public void update(UsuarioSesion usuarioSesion) {

    }

    @Override
    public void delete(UsuarioSesion usuarioSesion) {
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
