package Ventana;

import Hilos.ClienteHilo;
import Hilos.SocketCliente;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class ClienteVentana extends JFrame {

    private ClienteHilo hilo;
    private SocketCliente socket;

    private JLabel reloj;
    private JButton reset;
    private JButton solicitar;
    private JScrollPane scroll;
    private JPanel panel;

    private final String nombre;

    private ClienteVentana() {
        this.nombre = JOptionPane.showInputDialog(null, "Ingresa tu nombre de usuario", "Usuario", JOptionPane.QUESTION_MESSAGE);

        this.setSize(1000, 450);
        this.setLocationRelativeTo(null);
        this.setTitle("Cliente " + this.nombre);
        this.setLayout(null);

        construirElementos();

        this.hilo = new ClienteHilo(this.reloj);
        this.hilo.start();

        this.socket = new SocketCliente(this.reloj, this.nombre, this.panel, this.reset);
        this.socket.start();

        this.setVisible(true);
    }

    private void construirElementos() {
        this.reloj = new JLabel("00:00:00");
        this.reloj.setFont(new Font("Verdana", Font.BOLD, 50));
        this.reloj.setBounds(125, 100, 300, 50);
        this.add(this.reloj);

        this.solicitar = new JButton("Solicitar");
        this.solicitar.setFont(new Font("Verdana", Font.BOLD, 18));
        this.solicitar.setBounds(100, 200, 300, 50);
        this.solicitar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                socket.setIdOperacion(4);
            }
        });
        this.add(this.solicitar);

        this.reset = new JButton("Reiniciar");
        this.reset.setFont(new Font("Verdana", Font.BOLD, 18));
        this.reset.setBounds(100, 300, 300, 50);
        this.reset.setEnabled(false);
        this.reset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                socket.setIdOperacion(7);
                reset.setEnabled(false);
            }
        });
        this.add(this.reset);

        this.scroll = new JScrollPane();
        this.panel = new JPanel();
        this.panel.setLayout(new BoxLayout(this.panel, BoxLayout.Y_AXIS));
        this.panel.setBounds(0, 0, 400, 0);
        this.scroll.setViewportView(this.panel);
        this.scroll.setBounds(500, 100, 400, 250);
        this.scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        this.add(this.scroll);
    }

    public static void main(String[] args) {
        ClienteVentana cliente = new ClienteVentana();
    }

}
