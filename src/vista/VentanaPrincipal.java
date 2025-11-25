package vista;

import javax.swing.*;
import java.awt.*;

public class VentanaPrincipal extends JFrame {
    private PanelEntrada panelEntrada;
    private PanelVisualizacion panelVisualizacion;
    
    public VentanaPrincipal() {
        setTitle("Conversor de AFND a AFD");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1300, 750); // Ventana más ancha
        setLocationRelativeTo(null);
        
        // Crear componentes
        panelEntrada = new PanelEntrada();
        panelVisualizacion = new PanelVisualizacion();
        
        // Layout principal
        setLayout(new BorderLayout(10, 10));
        
        // Agregar componentes - panel entrada más ancho
        add(panelEntrada, BorderLayout.WEST);
        add(panelVisualizacion, BorderLayout.CENTER);
        
        // Hacer que el panel de entrada ocupe más espacio
        panelEntrada.setPreferredSize(new Dimension(450, getHeight()));
    }
    
    public PanelEntrada getPanelEntrada() { return panelEntrada; }
    public PanelVisualizacion getPanelVisualizacion() { return panelVisualizacion; }
}