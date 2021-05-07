package Entidades;

public class Usuario {
     private int id;
     private String ip;
     private String nombre;

     public Usuario() {
     }

     public Usuario(String ip, String nombre) {
          this.ip = ip;
          this.nombre = nombre;
     }

     public Usuario(int id, String ip, String nombre) {
          this.id = id;
          this.ip = ip;
          this.nombre = nombre;
     }

     public int getId() {
          return id;
     }

     public void setId(int id) {
          this.id = id;
     }

     public String getIp() {
          return ip;
     }

     public void setIp(String ip) {
          this.ip = ip;
     }

     public String getNombre() {
          return nombre;
     }

     public void setNombre(String nombre) {
          this.nombre = nombre;
     }

     @Override
     public String toString() {
          final StringBuffer sb = new StringBuffer("Usuario{");
          sb.append("id=").append(id);
          sb.append(", ip='").append(ip).append('\'');
          sb.append(", nombre='").append(nombre).append('\'');
          sb.append('}');
          return sb.toString();
     }
}
