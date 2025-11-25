package vista;

import javax.swing.*;
import java.awt.*;

public class PanelVisualizacion extends JPanel {
    private JTabbedPane tabbedPane;
    private VisualizadorGrafo visualizadorAFND;
    private VisualizadorGrafo visualizadorAFD;
    private JTextArea txtTablaAFND;
    private JTextArea txtTablaAFD;
    
    public PanelVisualizacion() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        tabbedPane = new JTabbedPane();
        
        JPanel panelAFND = new JPanel(new BorderLayout());
        visualizadorAFND = new VisualizadorGrafo();
        panelAFND.add(visualizadorAFND, BorderLayout.CENTER);
        
        JPanel panelInferiorAFND = new JPanel(new BorderLayout());
        panelInferiorAFND.setBorder(BorderFactory.createTitledBorder("Tabla de Transiciones AFND"));
        txtTablaAFND = new JTextArea(8, 50);
        txtTablaAFND.setEditable(false);
        txtTablaAFND.setFont(new Font("Monospaced", Font.PLAIN, 12));
        panelInferiorAFND.add(new JScrollPane(txtTablaAFND), BorderLayout.CENTER);
        
        panelAFND.add(panelInferiorAFND, BorderLayout.SOUTH);
        
        JPanel panelAFD = new JPanel(new BorderLayout());
        visualizadorAFD = new VisualizadorGrafo();
        panelAFD.add(visualizadorAFD, BorderLayout.CENTER);
        
        JPanel panelInferiorAFD = new JPanel(new BorderLayout());
        panelInferiorAFD.setBorder(BorderFactory.createTitledBorder("Tabla de Transiciones AFD"));
        txtTablaAFD = new JTextArea(8, 50);
        txtTablaAFD.setEditable(false);
        txtTablaAFD.setFont(new Font("Monospaced", Font.PLAIN, 12));
        panelInferiorAFD.add(new JScrollPane(txtTablaAFD), BorderLayout.CENTER);
        
        panelAFD.add(panelInferiorAFD, BorderLayout.SOUTH);
        
        tabbedPane.addTab("AFND - Visualización", panelAFND);
        tabbedPane.addTab("AFD - Visualización", panelAFD);
        
        add(tabbedPane, BorderLayout.CENTER);
    }
    
    public void mostrarPestanaAFD() {
        tabbedPane.setSelectedIndex(1);
    }
    
    public void mostrarPestanaAFND() {
        tabbedPane.setSelectedIndex(0);
    }
    
    public JTextArea getTxtTablaAFND() { return txtTablaAFND; }
    public JTextArea getTxtTablaAFD() { return txtTablaAFD; }
    public VisualizadorGrafo getVisualizadorAFND() { return visualizadorAFND; }
    public VisualizadorGrafo getVisualizadorAFD() { return visualizadorAFD; }
}