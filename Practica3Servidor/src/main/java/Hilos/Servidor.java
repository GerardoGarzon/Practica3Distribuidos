package Hilos;

import Datos.Libro.*;
import Datos.Pedido.*;
import Datos.Sesion.*;
import Datos.Usuario.*;
import Datos.UsuarioSesion.*;
import Entidades.*;

import javax.swing.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;
import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;

public class Servidor extends Thread {

    private static final int PUERTO = 9090;
    private ObjectOutputStream objetoEnv;
    private ObjectInputStream objetoRecv;
    private ByteBuffer buffer;
    private List<Conexion> conexiones;
    private JLabel reloj;
    private JList<String> listaLibros;
    private DefaultListModel<String> listaLibros_nombres;
    private int numConexion;
    private boolean reset;
    private int clientesReiniciados;

    public Servidor(JLabel reloj, JList<String> listaLibros, DefaultListModel<String> listaLibros_nombres) {
        conexiones = new ArrayList<>();
        this.reloj = reloj;
        this.reset = false;
        this.listaLibros = listaLibros;
        this.listaLibros_nombres = listaLibros_nombres;
        this.clientesReiniciados = 0;
    }

    public void setReset(boolean reset) {
        this.reset = reset;
    }

    @Override
    public void run() {
        objetoEnv = null;
        objetoRecv = null;

        try {
            ServerSocketChannel server = ServerSocketChannel.open();
            server.configureBlocking(false);
            server.socket().bind(new InetSocketAddress(PUERTO));
            Selector selector = Selector.open();
            server.register(selector, SelectionKey.OP_ACCEPT);
            SocketChannel canal = null;

            while (true) {
                selector.select();
                Iterator<SelectionKey> it = selector.selectedKeys().iterator();
                while (it.hasNext()) {
                    if (this.reset) {
                        for (int i = 0; i < conexiones.size(); i++) {
                            conexiones.get(i).setReiniciar(true);
                        }
                        this.reset = false;
                        UsuarioSesionDAO usuarioSesionDAO = new UsuarioSesionDAO_Conexion();
                        SesionDAO sesionDAO = new SesionDAO_Conexion();
                        PedidoDAO pedidoDAO = new PedidoDAO_Conexion();
                        
                        usuarioSesionDAO.delete(new UsuarioSesion());
                        sesionDAO.delete(new Sesion());
                        pedidoDAO.delete(new Pedido());
                    }
                    SelectionKey key = (SelectionKey) it.next();
                    it.remove();
                    if (key.isAcceptable()) {
                        canal = server.accept();
                        System.out.println("\n\n------------------------------------------------------------");
                        System.out.println("Cliente conectado desde" + canal.socket().getInetAddress() + ":" + canal.socket().getPort());
                        conexiones.add(new Conexion(1, canal.socket().getPort(), "" + canal.socket().getInetAddress(), false));
                        canal.configureBlocking(false);
                        canal.register(selector, SelectionKey.OP_READ);
                        System.out.println("conexiones.toString() = " + conexiones.toString());
                        continue;
                    } else if (key.isReadable()) {
                        try {
                            SocketChannel ch = (SocketChannel) key.channel();
                            for (int i = 0; i < conexiones.size(); i++) {
                                numConexion = i;
                                if (conexiones.get(i).getPuerto() == ch.socket().getPort()) {
                                    if (conexiones.get(i).getIdOperacion() == 1) {
                                        ByteBuffer buffer = ByteBuffer.allocate(2000);
                                        buffer.clear();
                                        int tam = 0;
                                        String msj = "";
                                        tam = ch.read(buffer);
                                        buffer.flip();
                                        if (tam > 0) {
                                            msj = new String(buffer.array(), 0, tam);
                                        }
                                        System.out.println("Mensaje de " + tam + " bytes recibidor: " + msj);
                                        
                                        if (msj.equals("solicitud reinicio")) {
                                            this.reset = true;
                                        } else {
                                            conexiones.get(i).setIdOperacion(Integer.parseInt(msj));
                                        }
                                        key.interestOps(SelectionKey.OP_WRITE | SelectionKey.OP_READ);
                                    } else if (conexiones.get(i).getIdOperacion() == 3) {
                                        ByteBuffer buffer = ByteBuffer.allocate(2000);
                                        buffer.clear();
                                        int tam = 0;
                                        String msj = "";
                                        tam = ch.read(buffer);
                                        buffer.flip();
                                        if (tam > 0) {
                                            msj = new String(buffer.array(), 0, tam);
                                        }
                                        System.out.println("Mensaje de " + tam + " bytes recibidor: " + msj);

                                        UsuarioDAO dao = new UsuarioDAO_Conexion();
                                        Usuario usuario = new Usuario(conexiones.get(i).getDireccion(), msj);

                                        dao.insert(usuario);

                                        conexiones.get(i).setIdOperacion(1);
                                        key.interestOps(SelectionKey.OP_READ);
                                    }
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            conexiones.remove(numConexion);
                        }
                    } else if (key.isWritable()) {
                        SocketChannel ch = (SocketChannel) key.channel();
                        for (int i = 0; i < conexiones.size(); i++) {
                            if (conexiones.get(i).getPuerto() == ch.socket().getPort()) {
                                if (conexiones.get(i).getIdOperacion() == 2) {
                                    String hora = reloj.getText();

                                    ByteBuffer bb = ByteBuffer.wrap(hora.getBytes());
                                    ch.write(bb);
                                    System.out.println("Mensaje de " + hora.length() + " bytes enviados: " + hora);

                                    conexiones.get(i).setIdOperacion(3);
                                    key.interestOps(SelectionKey.OP_READ);
                                } else if (conexiones.get(i).getIdOperacion() == 4) {
                                    LibroDAO libroDAO = new LibroDAO_Conexion();
                                    Libro libroEnviar = libroDAO.select_disponible();
                                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                                    DataOutputStream dos = new DataOutputStream(baos);

                                    if (libroEnviar == null) {
                                        String longitud = String.valueOf("0");

                                        ByteBuffer bb = ByteBuffer.wrap(longitud.getBytes());
                                        ch.write(bb);
                                        System.out.println("Mensaje de " + longitud.length() + " bytes enviados: " + longitud);
                                    } else {
                                        int index = 0;
                                        for (int j = 0; j < listaLibros_nombres.getSize(); j++) {
                                            if (libroEnviar.getNombre().equals(listaLibros_nombres.getElementAt(j))) {
                                                index = j;
                                                break;
                                            }
                                        }
                                        listaLibros.setSelectedIndex(index);
                                        objetoEnv = new ObjectOutputStream(baos);
                                        objetoEnv.writeObject(libroEnviar);
                                        byte[] mm = baos.toByteArray();

                                        String longitud = String.valueOf(mm.length);

                                        ByteBuffer bb = ByteBuffer.wrap(longitud.getBytes());
                                        ch.write(bb);
                                        System.out.println("Mensaje de " + longitud.length() + " bytes enviados: " + longitud);

                                        ByteBuffer b2 = ByteBuffer.wrap(mm);
                                        ch.write(b2);

                                        System.out.println("Objeto libro enviado...");
                                        System.out.println("Mensaje enviado de " + mm.length + " bytes");

                                        LocalDate fecha = java.time.LocalDate.now();
                                        Date fechaPedido = Date.valueOf(fecha);
                                        Pedido pedido = new Pedido(fechaPedido, this.reloj.getText(), "");
                                        PedidoDAO pedidoDAO = new PedidoDAO_Conexion();
                                        pedidoDAO.insert(pedido);

                                        pedido = pedidoDAO.select(pedido);

                                        SesionDAO sesionDAO = new SesionDAO_Conexion();
                                        Sesion sesion = new Sesion(pedido.getId(), libroEnviar.getISBN());
                                        sesionDAO.insert(sesion);

                                        UsuarioDAO usuarioDAO = new UsuarioDAO_Conexion();
                                        Usuario usuario = new Usuario(conexiones.get(i).getDireccion(), "");
                                        System.out.println("usuario = " + usuario.toString());
                                        usuario = usuarioDAO.select(usuario);

                                        UsuarioSesionDAO usuarioSesionDAO = new UsuarioSesionDAO_Conexion();
                                        UsuarioSesion usuarioSesion = new UsuarioSesion(usuario.getId(), pedido.getId());
                                        usuarioSesionDAO.insert(usuarioSesion);
                                    }
                                    conexiones.get(i).setIdOperacion(1);
                                    key.interestOps(SelectionKey.OP_READ | SelectionKey.OP_WRITE);
                                } else if (conexiones.get(i).getIdOperacion() == 1) {
                                    if (conexiones.get(i).isReiniciar()) {
                                        String mensaje = "reiniciar";

                                        ByteBuffer bb = ByteBuffer.wrap(mensaje.getBytes());
                                        ch.write(bb);
                                        System.out.println("Mensaje de " + mensaje.length() + " bytes enviados: " + mensaje);
                                        conexiones.get(i).setReiniciar(false);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
    }

}
