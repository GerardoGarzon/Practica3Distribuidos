package Entidades;

public class Sesion {
     private int id;
     private int idPedido;
     private String idLibro;

     public Sesion() {
     }

     public Sesion(int idPedido, String idLibro) {
          this.idPedido = idPedido;
          this.idLibro = idLibro;
     }

     public Sesion(int id, int idPedido, String idLibro) {
          this.id = id;
          this.idPedido = idPedido;
          this.idLibro = idLibro;
     }

     public int getId() {
          return id;
     }

     public void setId(int id) {
          this.id = id;
     }

     public int getIdPedido() {
          return idPedido;
     }

     public void setIdPedido(int idPedido) {
          this.idPedido = idPedido;
     }

     public String getIdLibro() {
          return idLibro;
     }

     public void setIdLibro(String idLibro) {
          this.idLibro = idLibro;
     }

     @Override
     public String toString() {
          final StringBuffer sb = new StringBuffer("Sesion{");
          sb.append("id=").append(id);
          sb.append(", idPedido=").append(idPedido);
          sb.append(", idLibro='").append(idLibro).append('\'');
          sb.append('}');
          return sb.toString();
     }
}
