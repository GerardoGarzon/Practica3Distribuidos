package Entidades;

import java.io.Serializable;

public class Libro implements Serializable {
     private String ISBN;
     private String nombre;
     private String autor;
     private String editorial;
     private float precio;
     private String portada;

     public Libro() {
     }

     public Libro(String ISBN) {
          this.ISBN = ISBN;
     }

     public Libro(String nombre, String autor, String editorial, float precio, String portada) {
          this.nombre = nombre;
          this.autor = autor;
          this.editorial = editorial;
          this.precio = precio;
          this.portada = portada;
     }

     public Libro(String ISBN, String nombre, String autor, String editorial, float precio, String portada) {
          this.ISBN = ISBN;
          this.nombre = nombre;
          this.autor = autor;
          this.editorial = editorial;
          this.precio = precio;
          this.portada = portada;
     }

     public String getISBN() {
          return ISBN;
     }

     public void setISBN(String ISBN) {
          this.ISBN = ISBN;
     }

     public String getNombre() {
          return nombre;
     }

     public void setNombre(String nombre) {
          this.nombre = nombre;
     }

     public String getAutor() {
          return autor;
     }

     public void setAutor(String autor) {
          this.autor = autor;
     }

     public String getEditorial() {
          return editorial;
     }

     public void setEditorial(String editorial) {
          this.editorial = editorial;
     }

     public float getPrecio() {
          return precio;
     }

     public void setPrecio(float precio) {
          this.precio = precio;
     }

     public String getPortada() {
          return portada;
     }

     public void setPortada(String portada) {
          this.portada = portada;
     }

     @Override
     public String toString() {
          final StringBuffer sb = new StringBuffer("Libro{");
          sb.append("ISBN='").append(ISBN).append('\'');
          sb.append(", nombre='").append(nombre).append('\'');
          sb.append(", autor='").append(autor).append('\'');
          sb.append(", editorial='").append(editorial).append('\'');
          sb.append(", precio=").append(precio);
          sb.append(", portada='").append(portada).append('\'');
          sb.append('}');
          return sb.toString();
     }
}
