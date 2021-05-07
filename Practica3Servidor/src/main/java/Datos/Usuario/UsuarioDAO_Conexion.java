package Datos.Usuario;

import Datos.Conexion;
import Entidades.Libro;
import Entidades.Pedido;
import Entidades.Usuario;


import static Datos.Conexion.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO_Conexion implements UsuarioDAO{
     private static final String SQL_SELECT = "";
     private static final String SQL_SELECT_ITEM = "SELECT * FROM usuario WHERE ip=? ORDER BY id DESC";
     private static final String SQL_INSERT = "INSERT INTO usuario(ip, nombre) VALUES(?,?)";
     private static final String SQL_UPDATE = "";
     private static final String SQL_DELETE = "";

     @Override
     public List<Usuario> select() {
          return null;
     }

     @Override
     public Usuario select(Usuario usuario) {
          Connection conn = null;
          PreparedStatement stmt = null;
          ResultSet rs = null;
          Usuario usuarioDTO = null;
          List<Usuario> usuarios = new ArrayList<>();

          try {
               conn = Conexion.getConnection();
               stmt = conn.prepareStatement(SQL_SELECT_ITEM);
               stmt.setString(1,usuario.getIp());
               rs = stmt.executeQuery();

               while( rs.next() ){
                    usuarioDTO = new Usuario();
                    usuarioDTO.setId(rs.getInt("id"));
                    usuarios.add(usuarioDTO);
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

          return usuarios.get(0);
     }

     @Override
     public void insert(Usuario usuario) {
          Connection conn = null;
          PreparedStatement stmt = null;
          try {
               conn = getConnection();
               stmt = conn.prepareStatement(SQL_INSERT);
               stmt.setString(1, usuario.getIp());
               stmt.setString(2, usuario.getNombre());

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
     public void update(Usuario usuario) {

     }

     @Override
     public void delete(Usuario usuario) {

     }
}
