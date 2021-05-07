package Entidades;

public class UsuarioSesion {
     private int id;
     private int idUsuario;
     private int idPedido;

     public UsuarioSesion() {
     }

     public UsuarioSesion(int idUsuario, int idPedido) {
          this.idUsuario = idUsuario;
          this.idPedido = idPedido;
     }

     public UsuarioSesion(int id, int idUsuario, int idPedido) {
          this.id = id;
          this.idUsuario = idUsuario;
          this.idPedido = idPedido;
     }

     public int getId() {
          return id;
     }

     public void setId(int id) {
          this.id = id;
     }

     public int getIdUsuario() {
          return idUsuario;
     }

     public void setIdUsuario(int idUsuario) {
          this.idUsuario = idUsuario;
     }

     public int getIdPedido() {
          return idPedido;
     }

     public void setIdPedido(int idPedido) {
          this.idPedido = idPedido;
     }

     @Override
     public String toString() {
          final StringBuffer sb = new StringBuffer("UsuarioSesion{");
          sb.append("id=").append(id);
          sb.append(", idUsuario=").append(idUsuario);
          sb.append(", idPedido=").append(idPedido);
          sb.append('}');
          return sb.toString();
     }
}
