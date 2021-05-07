package Entidades;

import java.util.Date;

public class Pedido {
     private int id;
     private Date fecha;
     private String hora_inicio;
     private String hora_fin;

     public Pedido() {
     }

     public Pedido(Date fecha, String hora_inicio, String hora_fin) {
          this.fecha = fecha;
          this.hora_inicio = hora_inicio;
          this.hora_fin = hora_fin;
     }

     public Pedido(int id, Date fecha, String hora_inicio, String hora_fin) {
          this.id = id;
          this.fecha = fecha;
          this.hora_inicio = hora_inicio;
          this.hora_fin = hora_fin;
     }

     public int getId() {
          return id;
     }

     public void setId(int id) {
          this.id = id;
     }

     public Date getFecha() {
          return fecha;
     }

     public void setFecha(Date fecha) {
          this.fecha = fecha;
     }

     public String getHora_inicio() {
          return hora_inicio;
     }

     public void setHora_inicio(String hora_inicio) {
          this.hora_inicio = hora_inicio;
     }

     public String getHora_fin() {
          return hora_fin;
     }

     public void setHora_fin(String hora_fin) {
          this.hora_fin = hora_fin;
     }

     @Override
     public String toString() {
          final StringBuffer sb = new StringBuffer("Pedido{");
          sb.append("id=").append(id);
          sb.append(", fecha=").append(fecha);
          sb.append(", hora_inicio='").append(hora_inicio).append('\'');
          sb.append(", hora_fin='").append(hora_fin).append('\'');
          sb.append('}');
          return sb.toString();
     }
}
