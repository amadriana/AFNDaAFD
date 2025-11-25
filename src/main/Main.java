package main;

import vista.VentanaPrincipal;
import controlador.Controlador;
import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            VentanaPrincipal ventana = new VentanaPrincipal();
            new Controlador(ventana);
            ventana.setVisible(true);
        });
    }
}