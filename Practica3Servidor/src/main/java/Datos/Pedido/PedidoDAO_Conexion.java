package Datos.Pedido;

import Datos.Conexion;
import Entidades.Libro;
import Entidades.Pedido;

import java.sql.*;
import java.util.List;

import static Datos.Conexion.close;
import static Datos.Conexion.getConnection;

public class PedidoDAO_Conexion implements PedidoDAO {

    private static final String SQL_SELECT_ITEM = "SELECT id FROM pedido WHERE fecha=? AND hora_inicio=?";
    private static final String SQL_INSERT = "INSERT INTO pedido(fecha, hora_inicio, hora_fin) VALUES(?,?,?)";
    private static final String SQL_DELETE = "delete from pedido";

    @Override
    public List<Pedido> select() {
        return null;
    }

    @Override
    public Pedido select(Pedido pedido) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Pedido pedidoDTO = null;

        try {
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_SELECT_ITEM);
            stmt.setDate(1, (Date) pedido.getFecha());
            stmt.setString(2, pedido.getHora_inicio());
            rs = stmt.executeQuery();

            while (rs.next()) {
                pedidoDTO = new Pedido();

                pedidoDTO.setId(rs.getInt("id"));
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
        return pedidoDTO;
    }

    @Override
    public void insert(Pedido pedido) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = getConnection();
            stmt = conn.prepareStatement(SQL_INSERT);
            stmt.setDate(1, (Date) pedido.getFecha());
            stmt.setString(2, pedido.getHora_inicio());
            stmt.setString(3, pedido.getHora_fin());

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
    public void update(Pedido pedido) {

    }

    @Override
    public void delete(Pedido pedido) {
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
