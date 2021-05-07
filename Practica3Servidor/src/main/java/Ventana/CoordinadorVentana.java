package Ventana;

import Datos.Libro.LibroDAO;
import Datos.Libro.LibroDAO_Conexion;
import Entidades.Libro;
import Hilos.CoordinadorHilo;
import Hilos.Servidor;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CoordinadorVentana extends JFrame {
     private JLabel reloj;
     private JLabel libros;
     private JList<String> listaLibros;
     private DefaultListModel<String> listaLibros_nombres;
     private List<ImageIcon> pathImagenes;
     private JLabel imagen;
     private JButton reset;

     private CoordinadorHilo hilo;
     private Servidor socket;

     public CoordinadorVentana () {
          this.listaLibros_nombres = new DefaultListModel<>();
          this.pathImagenes = new ArrayList<>();

          this.setSize(1000,700);
          this.setLocationRelativeTo(null);
          this.setTitle("Coordinador");
          this.setLayout(null);

          construirElementos();

          this.hilo = new CoordinadorHilo(this.reloj);
          this.hilo.start();

          this.socket = new Servidor(this.reloj, this.listaLibros, this.listaLibros_nombres);
          this.socket.start();

          this.setVisible(true);
     }

     private void construirElementos() {
          LibroDAO libroDAO = new LibroDAO_Conexion();
          List<Libro> libros = libroDAO.select();
          for(Libro libro: libros) {
               agregarLibros(libro.getNombre(), libro.getPortada());
          }

          this.imagen = new JLabel();
          this.imagen.setBounds(500,100,400,500);
          Image image = this.pathImagenes.get(0).getImage(); // transform it
          Image newimg = image.getScaledInstance(400, 500,  java.awt.Image.SCALE_SMOOTH);
          ImageIcon imageIcon = new ImageIcon(newimg);
          this.imagen.setIcon(imageIcon);
          this.add(this.imagen);
          
          
          this.listaLibros = new JList<>(listaLibros_nombres);
          this.listaLibros.setBounds(100,150,300,200);
          this.listaLibros.setFont(new Font("Verdana", Font.PLAIN, 13));
          this.listaLibros.setSelectedIndex(0);
          this.listaLibros.addListSelectionListener(new ListSelectionListener() {
               @Override
               public void valueChanged(ListSelectionEvent e) {
                    if (!e.getValueIsAdjusting()) {
                         cambiarImagen(listaLibros_nombres.indexOf(listaLibros.getSelectedValue().toString()));
                    }
               }
          });
          this.add(this.listaLibros);

          this.libros = new JLabel("Libros disponibles");
          this.libros.setFont(new Font("Verdana", Font.BOLD, 18));
          this.libros.setBounds(100,100,300,20);
          this.add(this.libros);

          this.reloj = new JLabel(obtenerHoraActual());
          this.reloj.setFont(new Font("Verdana", Font.BOLD, 50));
          this.reloj.setBounds(125,400,300,50);
          this.add(this.reloj);

          this.reset = new JButton("Reiniciar");
          this.reset.setFont(new Font("Verdana", Font.BOLD, 18));
          this.reset.setBounds(100,500,300,50);
          this.reset.addActionListener(new ActionListener() {
              @Override
              public void actionPerformed(ActionEvent e) {
                  socket.setReset(true);
              }
          });
          this.add(this.reset);
     }

     public void cambiarImagen(int id) {
          Image image = this.pathImagenes.get(id).getImage(); // transform it
          Image newimg = image.getScaledInstance(400, 500,  java.awt.Image.SCALE_SMOOTH);
          ImageIcon imageIcon = new ImageIcon(newimg);

          this.imagen.setIcon(imageIcon);
     }

     public void agregarLibros (String titulo, String path) {
          this.listaLibros_nombres.addElement(titulo);
          this.pathImagenes.add(new ImageIcon(path));
     }

     public static void main(String[] args) {
          CoordinadorVentana c = new CoordinadorVentana();
     }

     public String obtenerHoraActual(){
          LocalDateTime locaDate = LocalDateTime.now();

          int hora  = locaDate.getHour();
          int minuto = locaDate.getMinute();
          int segundo = locaDate.getSecond();

          return String.valueOf(hora) + ":" + String.valueOf(minuto) + ":" + String.valueOf(segundo);
     }
}
