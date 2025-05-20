import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;

public class SimulacionSRTF extends JFrame {

    private JPanel ganttPanel;
    private int pasoActual = -1;
    private JLabel tiempoEsperaLbl;
    private JLabel tepLbl;
    private JLabel ttpLbl;
    private JLabel porcentajeLbl;


    public SimulacionSRTF() {
        setTitle("Simulación de la Aplicación del Algoritmo SRTF");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null); 
        setLayout(new BorderLayout(10, 10));

        // Título
        JLabel titulo = new JLabel("SIMULACIÓN DE LA APLICACIÓN DEL ALGORITMO SRTF", JLabel.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 18));
        add(titulo, BorderLayout.NORTH);

        // Tabla a la izquierda
        String[] columnas = {"Proceso", "Tiempo de llegada", "Tiempo de ráfaga"};
        Object[][] datos = {
            {"P1", 0, 8},
            {"P2", 3, 4},
            {"P3", 6, 2},
            {"P4", 10, 3},
            {"P5", 15, 6}
        };
        JTable tabla = new JTable(new DefaultTableModel(datos, columnas));
        
        tabla.setAutoResizeMode(JTable.AUTO_RESIZE_OFF); // Evita que se expandan las columnas

        // Establece el ancho de cada columna manualmente
        tabla.getColumnModel().getColumn(0).setPreferredWidth(100);
        tabla.getColumnModel().getColumn(1).setPreferredWidth(130);
        tabla.getColumnModel().getColumn(2).setPreferredWidth(130);

        
        
        JScrollPane scrollTabla = new JScrollPane(tabla);
        scrollTabla.setPreferredSize(new Dimension(370, 120));

        // Envolver en un panel con FlowLayout centrado
        JPanel tablaPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        tablaPanel.add(scrollTabla);

        

        // Panel centro para el gráfico Gantt (vacío al inicio)
        ganttPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                int x = 350; // posición inicial en X
                int y = 30; // posición inicial en Y
                int ancho = 25; // ancho de cada bloque
                int alto = 40;  // alto del bloque

                String[] procesos = {
                    "P1", "P1", "P1", "P2", "P2", "P2", "P2",
                    "P3", "P3", "P1", "P4", "P4", "P4", "P1",
                    "P1", "P1", "P1", "P5", "P5", "P5", "P5",
                    "P5", "P5", ""
                };

                Color[] colores = {
                    Color.BLUE, Color.BLUE, Color.BLUE, Color.RED, Color.RED, Color.RED, Color.RED,
                    Color.ORANGE, Color.ORANGE, Color.BLUE, Color.GREEN, Color.GREEN, Color.GREEN, Color.BLUE,
                    Color.BLUE, Color.BLUE, Color.BLUE, Color.PINK, Color.PINK, Color.PINK, Color.PINK,
                    Color.PINK, Color.PINK, null // último paso solo imprime el número
                };

                for (int i = 0; i <= pasoActual && i < procesos.length; i++) {
                    if (i > 0) x += ancho;

                    if (colores[i] != null) {
                        g.setColor(colores[i]);
                        g.fillRect(x, y, ancho, alto);

                        // Color del texto
                        if (colores[i] == Color.ORANGE || colores[i] == Color.GREEN || colores[i] == Color.PINK) {
                            g.setColor(Color.BLACK);
                        } else {
                            g.setColor(Color.WHITE);
                        }

                        g.drawString(procesos[i], x + 6, y + 25);
                    }

                    // Número en la parte inferior (siempre en negro)
                    g.setColor(Color.BLACK);
                    g.drawString(String.valueOf(i), x + ancho - 32, y + alto + 15);
                }
                
            }
        };
        ganttPanel.setPreferredSize(new Dimension(400, 120));
        
        

        
        // Crear el JSplitPane
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, tablaPanel, ganttPanel);
        splitPane.setResizeWeight(0.5); // Distribución inicial del espacio (0.5 significa 50/50)

        add(splitPane, BorderLayout.CENTER);
        setPreferredSize(new Dimension(1280, 720)); // Tamaño de la ventana
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        
        
        
        
        // Panel inferior general
        JPanel panelInferior = new JPanel();
        panelInferior.setLayout(new BoxLayout(panelInferior, BoxLayout.Y_AXIS));

        // Panel centrado para los textos
        JPanel textoPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(2, 0, 2, 0); // Espaciado vertical

        // Línea 1: texto general
        gbc.gridwidth = 2; // Ocupa dos columnas
        textoPanel.add(new JLabel("Cálculo de los tiempos. El cambio de contexto se realiza en 0.2 milisegundos."), gbc);

        // Las siguientes líneas son incisos
        gbc.gridy = 1;
        gbc.gridwidth = 1; // Volvemos a una sola columna

        // a)
        gbc.gridy++;
        tiempoEsperaLbl = new JLabel("a) Tiempo de espera de cada proceso: P1= ; P2= ; P3= ; P4= ; P5=");
        textoPanel.add(tiempoEsperaLbl, gbc);

        // b)
        gbc.gridy++;
        tepLbl = new JLabel("b) Tiempo de espera promedio de todos los procesos (TEP):");
        textoPanel.add(tepLbl, gbc);

        // c)
        gbc.gridy++;
        ttpLbl = new JLabel("c) Tiempo total de procesamiento de todos los procesos (TTP):");
        textoPanel.add(ttpLbl, gbc);

        // d)
        gbc.gridy++;
        porcentajeLbl = new JLabel("d) Porcentaje del TTP que consume el TEP:");
        textoPanel.add(porcentajeLbl, gbc);

        // Centramos todo el bloque de texto
        textoPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelInferior.add(textoPanel);

        // Botón centrado debajo
        JButton pasoBtn = new JButton("Preciona para iniciar");
        pasoBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        pasoBtn.addActionListener((ActionEvent e) -> {
            pasoActual++;
            ganttPanel.repaint();
            if (pasoActual <= 22) {
                pasoBtn.setText("Paso " + pasoActual);
            } else if (pasoActual == 23) {
                // Mostrar cálculos
                tiempoEsperaLbl.setText("a) Tiempo de espera de cada proceso: P1=9 ; P2=0 ; P3=1 ; P4=0 ; P5=2");
                tepLbl.setText("b) Tiempo de espera promedio de todos los procesos (TEP): 2.4");
                ttpLbl.setText("c) Tiempo total de procesamiento de todos los procesos (TTP): 23");
                porcentajeLbl.setText("d) Porcentaje del TTP que consume el TEP: 10.43%");

                pasoBtn.setText("Reiniciar");
            } else {
                // Reiniciar
                pasoActual = -1;
                ganttPanel.repaint();
                tiempoEsperaLbl.setText("a) Tiempo de espera de cada proceso: P1= ; P2= ; P3= ; P4= ; P5=");
                tepLbl.setText("b) Tiempo de espera promedio de todos los procesos (TEP):");
                ttpLbl.setText("c) Tiempo total de procesamiento de todos los procesos (TTP):");
                porcentajeLbl.setText("d) Porcentaje del TTP que consume el TEP:");
                pasoBtn.setText("Preciona para iniciar");
            }
        });
        panelInferior.add(Box.createVerticalStrut(10)); // Separación opcional
        panelInferior.add(pasoBtn);

        // Añadir el panel inferior al frame
        add(panelInferior, BorderLayout.SOUTH);

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SimulacionSRTF().setVisible(true));
    }
}



        