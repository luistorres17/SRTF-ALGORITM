import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;

public class SimulacionSRTF extends JFrame {

    private JPanel ganttPanel;
    private int pasoActual = 0;

    public SimulacionSRTF() {
        setTitle("Simulación de la Aplicación del Algoritmo SRTF");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
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
                if (pasoActual == 1) {
                    g.setColor(Color.BLUE);
                    g.fillRect(50, 30, 100, 40);
                    g.setColor(Color.WHITE);
                    g.drawString("P1", 95, 55);
                    g.setColor(Color.BLACK);
                    g.drawString("3", 155, 85);
                }
            }
        };
        ganttPanel.setPreferredSize(new Dimension(400, 120));
        ganttPanel.setBackground(Color.LIGHT_GRAY);
        

        
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
        textoPanel.add(new JLabel("a) Tiempo de espera de cada proceso: P1= ; P2= ; P3= ; P4= ; P5="), gbc);

        // b)
        gbc.gridy++;
        textoPanel.add(new JLabel("b) Tiempo de espera promedio de todos los procesos (TEP):"), gbc);

        // c)
        gbc.gridy++;
        textoPanel.add(new JLabel("c) Tiempo total de procesamiento de todos los procesos (TTP):"), gbc);

        // d)
        gbc.gridy++;
        textoPanel.add(new JLabel("d) Porcentaje del TTP que consume el TEP:"), gbc);

        // Centramos todo el bloque de texto
        textoPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelInferior.add(textoPanel);

        // Botón centrado debajo
        JButton pasoBtn = new JButton("Paso n");
        pasoBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        pasoBtn.addActionListener((ActionEvent e) -> {
            pasoActual++;
            ganttPanel.repaint();
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



