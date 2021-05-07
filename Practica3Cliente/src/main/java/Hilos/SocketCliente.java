package Hilos;

import Entidades.Libro;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class SocketCliente extends Thread {

    private static final String serverIP = "192.168.1.144";
    private static final int PUERTO = 9090;
    private static ObjectOutputStream objetoEnv = null;
    private static ObjectInputStream objetoRecv = null;
    private int idOperacion;
    private int numLibros;
    private JLabel reloj;
    private JPanel panelLibros;
    private String nombre;
    private JButton reset;

    public int getIdOperacion() {
        return idOperacion;
    }

    public void setIdOperacion(int idOperacion) {
        this.idOperacion = idOperacion;
    }

    public SocketCliente(JLabel reloj, String nombre, JPanel panel, JButton reset) {
        this.numLibros = 0;
        this.idOperacion = 1;
        this.reloj = reloj;
        this.nombre = nombre;
        this.panelLibros = panel;
        this.reset = reset;
    }

    @Override
    public void run() {
        try {
            ByteBuffer buffer;
            InetSocketAddress destino = new InetSocketAddress(serverIP, PUERTO);
            SocketChannel canal = SocketChannel.open();
            canal.configureBlocking(false);
            Selector selector = Selector.open();
            canal.register(selector, SelectionKey.OP_CONNECT);
            canal.connect(destino);

            while (true) {
                selector.select();
                Iterator<SelectionKey> it = selector.selectedKeys().iterator();

                while (it.hasNext()) {
                    SelectionKey key = (SelectionKey) it.next();
                    it.remove();
                    if (key.isConnectable()) {
                        SocketChannel ch = (SocketChannel) key.channel();

                        if (ch.isConnectionPending()) {
                            System.out.println("Estableciendo conexion con el servidor... espere..");
                            try {
                                ch.finishConnect();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            System.out.print("Conexion establecida...\n");
                        }

                        ch.register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE);
                        continue;
                    } else if (key.isReadable()) {
                        SocketChannel ch = (SocketChannel) key.channel();
                        if (this.idOperacion == 2) {
                            buffer = ByteBuffer.allocate(2000);
                            buffer.clear();
                            int tam;
                            String msj = "";
                            tam = ch.read(buffer);
                            buffer.flip();
                            if (tam > 0) {
                                msj = new String(buffer.array(), 0, tam);
                            }
                            System.out.println("Mensaje de " + tam + " bytes recibidor: " + msj);
                            idOperacion = 3;
                            this.reloj.setText(msj);
                            key.interestOps(SelectionKey.OP_WRITE);
                        } else if (this.idOperacion == 5) {
                            buffer = ByteBuffer.allocate(2000);
                            buffer.clear();
                            int tam = 0;
                            String msj = "";
                            tam = ch.read(buffer);
                            buffer.flip();
                            if (tam > 0) {
                                msj = new String(buffer.array(), 0, tam);
                            }
                            System.out.println("Tamaño del objeto: " + msj);

                            if (msj.equals("0")) {
                                this.reset.setEnabled(true);
                                JOptionPane.showMessageDialog(null, "Ya no hay libros disponibles :(", "Vacio", JOptionPane.WARNING_MESSAGE);
                            } else {
                                buffer = null;
                                buffer = ByteBuffer.allocate(Integer.parseInt(msj));
                                buffer.clear();

                                int tamRecibido = Integer.parseInt(msj);
                                int recibidos = 0;
                                int n = 0;

                                //recibimos el objeto libro
                                while (recibidos < tamRecibido) {
                                    n = ch.read(buffer);
                                    recibidos = recibidos + n;
                                }

                                System.out.println("Objeto recibido....\n Recibidos: " + recibidos + " bytes");

                                ByteArrayInputStream bais = new ByteArrayInputStream(buffer.array());
                                objetoRecv = null;
                                objetoRecv = new ObjectInputStream(bais);
                                Libro libro = (Libro) objetoRecv.readObject();
                                agregarNuevoElemento(libro);
                            }

                            this.idOperacion = 6;
                            key.interestOps(SelectionKey.OP_READ | SelectionKey.OP_WRITE);
                        } else if (this.idOperacion == 6) {
                            buffer = ByteBuffer.allocate(2000);
                            buffer.clear();
                            int tam;
                            String msj = "";
                            tam = ch.read(buffer);
                            buffer.flip();
                            if (tam > 0) {
                                System.out.println("Recibio reinicio");
                                msj = new String(buffer.array(), 0, tam);
                            }
                            System.out.println("Tamaño del objeto: " + msj);
                            if (msj.equals("reiniciar")) {
                                this.panelLibros.removeAll();
                                this.panelLibros.updateUI();
                            }
                            //this.idOperacion = 6;
                            key.interestOps(SelectionKey.OP_READ | SelectionKey.OP_WRITE);
                        }
                    }  else if (key.isWritable()) {
                        SocketChannel ch = (SocketChannel) key.channel();

                        if (this.idOperacion == 1) {
                            System.out.println("Solicito la hora");
                            ByteBuffer bb = ByteBuffer.wrap("2".getBytes());
                            ch.write(bb);

                            this.idOperacion = 2;
                            key.interestOps(SelectionKey.OP_READ);
                        } else if (this.idOperacion == 3) {
                            System.out.println("Registro de usuario");
                            ByteBuffer bb = ByteBuffer.wrap(this.nombre.getBytes());
                            ch.write(bb);

                            this.idOperacion = 0;

                            key.interestOps(SelectionKey.OP_WRITE);
                        } else if (this.idOperacion == 4) {
                            System.out.println("Solicito un libro");
                            ByteBuffer bb = ByteBuffer.wrap("4".getBytes());
                            ch.write(bb);

                            this.idOperacion = 5;
                            key.interestOps(SelectionKey.OP_READ);
                        } else if (this.idOperacion == 7) {
                            System.out.println("Solicito reinicio de sesion");
                            ByteBuffer bb = ByteBuffer.wrap("solicitud reinicio".getBytes());
                            ch.write(bb);

                            this.idOperacion = 6;
                            key.interestOps(SelectionKey.OP_READ);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void agregarNuevoElemento(Libro libro) {
        JLabel isbn = new JLabel("ISBN: " + libro.getISBN());
        JLabel titulos = new JLabel(libro.getNombre());
        JLabel autor = new JLabel("Autor: " + libro.getAutor());
        JLabel editorial = new JLabel("Editorial: " + libro.getEditorial());
        JLabel precio = new JLabel(String.valueOf("$ " + libro.getPrecio()));
        JPanel recuadros = new JPanel();
        recuadros.setLayout(null);

        isbn.setBounds(50, 10, 150, 15);
        isbn.setFont(new Font("Verdana", Font.PLAIN, 12));

        titulos.setBounds(50, 35, 300, 15);
        titulos.setFont(new Font("Verdana", Font.BOLD, 12));

        autor.setBounds(50, 60, 300, 15);
        autor.setFont(new Font("Verdana", Font.PLAIN, 12));

        editorial.setBounds(50, 85, 300, 15);
        editorial.setFont(new Font("Verdana", Font.PLAIN, 12));

        precio.setBounds(250, 10, 150, 15);
        precio.setFont(new Font("Verdana", Font.BOLD, 12));

        recuadros.add(isbn);
        recuadros.add(titulos);
        recuadros.add(autor);
        recuadros.add(editorial);
        recuadros.add(precio);

        recuadros.setPreferredSize(new Dimension(380, 110));
        recuadros.setBorder(BorderFactory.createLineBorder(Color.black));

        numLibros++;
        this.panelLibros.add(recuadros);
    }
}
