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
        JScrollPane scrollTabla = new JScrollPane(tabla);
        scrollTabla.setPreferredSize(new Dimension(250, 100));
        

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
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, scrollTabla, ganttPanel);
        splitPane.setResizeWeight(0.5); // Distribución inicial del espacio (0.5 significa 50/50)

        add(splitPane, BorderLayout.CENTER);

        setPreferredSize(new Dimension(1280, 720)); // Tamaño de la ventana
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        
        
        
        
        
        // Panel inferior (información + botón)
        JPanel panelInferior = new JPanel();
        panelInferior.setLayout(new BoxLayout(panelInferior, BoxLayout.Y_AXIS));
        panelInferior.add(new JLabel("Cálculo de los tiempos. El cambio de contexto se realiza en 0.2 milisegundos."));
        panelInferior.add(new JLabel("a) Tiempo de espera de cada proceso: P1= ; P2= ; P3= ; P4= ; P5="));
        panelInferior.add(new JLabel("b) Tiempo de espera promedio de todos los procesos (TEP):"));
        panelInferior.add(new JLabel("c) Tiempo total de procesamiento de todos los procesos (TTP):"));
        panelInferior.add(new JLabel("d) Porcentaje del TTP que consume el TEP:"));

        JButton pasoBtn = new JButton("Paso n");
        pasoBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        pasoBtn.addActionListener((ActionEvent e) -> {
            pasoActual++;
            ganttPanel.repaint(); // Muestra la gráfica según el paso
        });
        panelInferior.add(pasoBtn);

        add(panelInferior, BorderLayout.SOUTH);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SimulacionSRTF().setVisible(true));
    }
}



