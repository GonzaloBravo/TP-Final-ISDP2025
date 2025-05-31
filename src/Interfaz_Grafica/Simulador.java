package Interfaz_Grafica;

import Centro_Comercial.*;

import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import java.awt.*;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public class Simulador extends JPanel {

    public JPanel panel1;
    private JLabel encabezado;
    private JComboBox<String> listaComercios;
    private JButton iniciarButton;
    private JButton finalizarButton;
    private JTextArea consola;
    private JScrollPane scrollPane;

    private CentroComercial centroComercial;
    private boolean simulacionIniciada = false;

    private final List<Thread> personas = new ArrayList<>();
    private final List<Thread> vehiculos = new ArrayList<>();

    public Simulador() {
        // Configuración del panel principal
        panel1 = new JPanel();
        panel1.setLayout(new BorderLayout());

        // Crear panel encabezado con FlowLayout (centrado)
        JPanel panelEncabezado = new JPanel(new FlowLayout(FlowLayout.LEFT));

        // Etiqueta texto encabezado
        encabezado = new JLabel("      Simulador de Gestion - Centro Comercial");
        encabezado.setFont(new Font("Arial", Font.BOLD, 20));

        // Cargar imagen para el icono
        ImageIcon icono = new ImageIcon("src/Interfaz_Grafica/Imagenes/logo-shopping-patagonia.png");

        // Escalar icono si es necesario (por ejemplo a 40x40)
        Image img = icono.getImage().getScaledInstance(170, 40, Image.SCALE_SMOOTH);
        icono = new ImageIcon(img);

        // Etiqueta para imagen
        JLabel etiquetaIcono = new JLabel(icono);

        // Añadir icono y texto al panel encabezado
        panelEncabezado.add(etiquetaIcono);
        panelEncabezado.add(encabezado);

        // Añadir panel encabezado al panel principal en norte
        panel1.add(panelEncabezado, BorderLayout.NORTH);


        // Consola
        consola = new JTextArea();
        consola.setEditable(false);
        consola.setFont(new Font("Monospaced", Font.BOLD, 14)); // Texto en negrita
        consola.setForeground(Color.GREEN); // Texto en blanco

// Para que la consola sea transparente y se vea la imagen de fondo
        consola.setOpaque(false);
        consola.setBackground(new Color(0, 0, 0, 0));  // fondo transparente

        scrollPane = new JScrollPane(consola);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);  // también transparente el área de scroll

// Cargar imagen de fondo para consola
        ImageIcon fondoIcono = new ImageIcon("src/Interfaz_Grafica/Imagenes/el-shopping.jpg");
        Image fondo = fondoIcono.getImage();

// Panel con fondo personalizado para la consola
        JPanel panelConFondo = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Dibujar la imagen de fondo escalada a todo el tamaño del panel
                g.drawImage(fondo, 0, 0, getWidth(), getHeight(), this);
            }
        };
        panelConFondo.setLayout(new BorderLayout());

// Añadir el scrollPane (con la consola) al panel con fondo
        panelConFondo.add(scrollPane, BorderLayout.CENTER);

// Añadir el panel con fondo al panel principal (panel1)
        panel1.add(panelConFondo, BorderLayout.CENTER);


        // Botones
        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new FlowLayout());

        iniciarButton = new JButton("Iniciar Aplicacion");
        finalizarButton = new JButton("Finalizar Aplicacion");

        panelBotones.add(iniciarButton);
        panelBotones.add(finalizarButton);

        panel1.add(panelBotones, BorderLayout.SOUTH);

        // Redirigir salida estándar a la consola
        redirigirSalidaConsola();

        // Asignar acciones a los botones
        iniciarButton.addActionListener(e -> iniciarSimulacion());
        finalizarButton.addActionListener(e -> detenerSimulacion());
    }

    /**
     * Redirige System.out a la JTextArea
     */
    private void redirigirSalidaConsola() {
        PrintStream printStream = new PrintStream(new OutputStream() {
            @Override
            public void write(int b) {
                consola.append(String.valueOf((char) b));
                consola.setCaretPosition(consola.getDocument().getLength());
            }
        });
        System.setOut(printStream);
        System.setErr(printStream);
    }

    public void iniciarSimulacion() {
        if (simulacionIniciada) {
            System.out.println("[SISTEMA] La simulacion ya esta en curso.");
            return;
        }

        System.out.println("[SISTEMA] Abriendo centro comercial...\n");
        System.out.println("[SISTEMA] Iniciando simulacion...\n");
        simulacionIniciada = true;

        centroComercial = new CentroComercial();
        Estacionamiento estacionamiento = centroComercial.estacionamiento;

        // Crear hilos de personas
        for (int i = 0; i < 120; i++) {
            String nombre = "Persona " + (i + 1);
            Thread persona = new Persona(nombre, centroComercial);
            personas.add(persona);
            persona.start();
        }

        // Crear hilos de vehículos
        for (int i = 0; i < 60; i++) {
            String id = "Vehiculo " + (i + 1);
            Thread vehiculo = new Vehiculo(id, estacionamiento, centroComercial.estadisticas);
            vehiculos.add(vehiculo);
            vehiculo.start();
        }
    }

    public void detenerSimulacion() {
        if (!simulacionIniciada) {
            System.out.println("[SISTEMA] La simulacion no ha sido iniciada.");
            return;
        }

        System.out.println("\n[SISTEMA] Senal TERM recibida. Cerrando Centro Comercial...\n");

        // Interrumpir todos los hilos
        for (int i = 0; i < personas.size(); i++) {
            personas.get(i).interrupt();
        }
        for (int i = 0; i < vehiculos.size(); i++) {
            vehiculos.get(i).interrupt();
        }

        // Esperar a que terminen
        try {
            for (int i = 0; i < personas.size(); i++) {
                personas.get(i).join();
            }
            for (int i = 0; i < vehiculos.size(); i++) {
                vehiculos.get(i).join();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // restaurar el estado de interrupción
        }

        simulacionIniciada = false;

        System.out.println("\n[SISTEMA] Finalizando simulacion...");

        String informe = centroComercial.estadisticas.obtenerInforme();
        mostrarVentanaEstadisticas(informe);
    }

    private void mostrarVentanaEstadisticas(String informe) {
        // Crear panel principal con fondo blanco y layout vertical
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);

        // Imagen del logo
        ImageIcon logo = new ImageIcon("src/Interfaz_Grafica/Imagenes/logo-informe.png");
        Image imagen = logo.getImage().getScaledInstance(250, 200, Image.SCALE_SMOOTH);
        JLabel etiquetaImagen = new JLabel(new ImageIcon(imagen));
        etiquetaImagen.setAlignmentX(Component.CENTER_ALIGNMENT); // Centrar horizontalmente
        panel.add(Box.createVerticalStrut(10)); // Espacio arriba
        panel.add(etiquetaImagen);
        panel.add(Box.createVerticalStrut(10)); // Espacio entre imagen y texto

        // Texto del informe
        JTextPane textoCentrado = new JTextPane();
        textoCentrado.setEditable(false);
        textoCentrado.setFont(new Font("Monospaced", Font.BOLD, 14));
        textoCentrado.setText(informe);
        textoCentrado.setBackground(Color.WHITE);

        // Centrar texto
        SimpleAttributeSet center = new SimpleAttributeSet();
        StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
        textoCentrado.getStyledDocument().setParagraphAttributes(0, textoCentrado.getDocument().getLength(),
                center, false);

        // Scroll para el texto
        JScrollPane scrollPane = new JScrollPane(textoCentrado);
        scrollPane.setAlignmentX(Component.CENTER_ALIGNMENT);
        scrollPane.setBackground(Color.WHITE);
        scrollPane.getViewport().setBackground(Color.WHITE);

        // Añadir scroll al panel
        panel.add(scrollPane);
        panel.add(Box.createVerticalStrut(10)); // Espacio debajo

        // Crear ventana
        JFrame frameEstadisticas = new JFrame("Informe de actividad diaria Centro Comercial");
        frameEstadisticas.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frameEstadisticas.getContentPane().add(panel);
        frameEstadisticas.setSize(600, 500);
        frameEstadisticas.setLocationRelativeTo(null); // Centrada en pantalla
        frameEstadisticas.setVisible(true);
    }

}
