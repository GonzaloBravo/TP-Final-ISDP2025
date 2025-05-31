package Interfaz_Grafica;

import Interfaz_Grafica.Simulador;

import javax.swing.*;
import java.awt.*;

public class SimuladorApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                iniciar();
            }
        });
    }

    public static void iniciar() {
        JFrame frame = new JFrame("Simulador de Gestion para Centro Comercial");
        Simulador simulador = new Simulador();
        frame.setContentPane(simulador.panel1);

        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Archivo");
        JMenu menu2 = new JMenu("Herramientas");
        JMenu menu3 = new JMenu("Ayuda");

        JMenuItem menuItem1 = new JMenuItem("Nuevo");
        JMenuItem menuItem2 = new JMenuItem("Abrir");
        JMenuItem menuItem3 = new JMenuItem("Guardar");
        JMenuItem menuItem4 = new JMenuItem("Guardar Como");
        JMenuItem menuItem5 = new JMenuItem("Salir");
        JMenuItem menuItem6 = new JMenuItem("Iniciar Aplicacion");
        JMenuItem menuItem7 = new JMenuItem("Finalizar Aplicacion");
        JMenuItem menuItem8 = new JMenuItem("Ayuda del Simulador");

        // ActionListener para "Nuevo"
        menuItem1.addActionListener(e -> {
            frame.dispose();  // Cierra la ventana actual
            main(new String[]{});  // Reinicia la aplicación
        });

        // ActionListener para "Salir"
        menuItem5.addActionListener(e -> {
            frame.dispose();  // Cierra la ventana actual
            System.exit(0);  // Finaliza el programa
        });

        // ActionListener para "Ayuda"
        menuItem8.addActionListener(e -> {
            JOptionPane.showMessageDialog(frame,
                    "- Presione en Iniciar Aplicacion para iniciar el programa\n"
                            + "- Presione en Finalizar Aplicacion para mostrar las estadisticas \n",
                    "Ayuda",
                    JOptionPane.INFORMATION_MESSAGE);
        });

        menu.add(menuItem1);
        menu.add(menuItem2);
        menu.add(menuItem3);
        menu.add(menuItem4);
        menu.add(menuItem5);
        menu2.add(menuItem6);
        menu2.add(menuItem7);
        menu3.add(menuItem8);

        menuBar.add(menu);
        menuBar.add(menu2);
        menuBar.add(menu3);

        frame.setJMenuBar(menuBar);

        JButton botonIniciar = new JButton("Iniciar Aplicacion");
        JButton botonFinalizar = new JButton("Finalizar Aplicacion");

        botonIniciar.addActionListener(e -> simulador.iniciarSimulacion());
        botonFinalizar.addActionListener(e -> simulador.detenerSimulacion());

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        // Tamaño mínimo de la ventana
        frame.setMinimumSize(new Dimension(900, 600));
        // Tamaño inicial de la ventana
        frame.setSize(new Dimension(900, 400));
        frame.setVisible(true);
    }

}
