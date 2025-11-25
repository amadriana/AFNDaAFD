package vista;

import javax.swing.*;
import java.awt.*;

public class VentanaPrincipal extends JFrame {
    private PanelEntrada panelEntrada;
    private PanelVisualizacion panelVisualizacion;
    
    public VentanaPrincipal() {
        setTitle("Conversor de AFND a AFD");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 700);
        setLocationRelativeTo(null);
        
        panelEntrada = new PanelEntrada();
        panelVisualizacion = new PanelVisualizacion();
        
        setLayout(new BorderLayout(10, 10));
        
        add(panelEntrada, BorderLayout.WEST);
        add(panelVisualizacion, BorderLayout.CENTER);
    }
    
    public PanelEntrada getPanelEntrada() { return panelEntrada; }
    public PanelVisualizacion getPanelVisualizacion() { return panelVisualizacion; }
}