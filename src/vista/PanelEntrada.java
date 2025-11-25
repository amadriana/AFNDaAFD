package vista;

import javax.swing.*;
import java.awt.*;

public class PanelEntrada extends JPanel {
    private JTextField txtNombreEstado;
    private JCheckBox chkInicial, chkFinal;
    private JButton btnAgregarEstado;
    private JTextField txtSimbolo;
    private JButton btnAgregarSimbolo;
    private JTextField txtOrigen, txtSimboloTrans, txtDestino;
    private JButton btnAgregarTransicion;
    private JTextField txtPalabra;
    private JButton btnProbarAFND, btnProbarAFD;
    private JButton btnConvertirAFD;
    private JTextArea txtInfoAFND;
    
    public PanelEntrada() {
        setLayout(new BorderLayout(5, 5));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setPreferredSize(new Dimension(350, 600));
        
        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BoxLayout(panelPrincipal, BoxLayout.Y_AXIS));
        
        JLabel lblTitulo = new JLabel("CONVERSOR AFND A AFD");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        lblTitulo.setAlignmentX(CENTER_ALIGNMENT);
        panelPrincipal.add(lblTitulo);
        panelPrincipal.add(Box.createVerticalStrut(20));
        
        JPanel panelEntradaAFND = crearPanelSeccion("Entrada AFND");
        panelPrincipal.add(panelEntradaAFND);
        panelPrincipal.add(Box.createVerticalStrut(15));
        
        JPanel panelAgregarEstado = crearPanelSeccion("Agregar Estado");
        panelPrincipal.add(panelAgregarEstado);
        panelPrincipal.add(Box.createVerticalStrut(15));
        
        JPanel panelSimbolo = crearPanelSeccion("Símbolo");
        panelPrincipal.add(panelSimbolo);
        panelPrincipal.add(Box.createVerticalStrut(15));
        
        JPanel panelPalabra = crearPanelSeccion("Palabra a probar");
        panelPrincipal.add(panelPalabra);
        panelPrincipal.add(Box.createVerticalStrut(15));
        
        JPanel panelTransiciones = crearPanelSeccion("Transiciones (origen, símbolo, destino)");
        panelPrincipal.add(panelTransiciones);
        panelPrincipal.add(Box.createVerticalStrut(15));
        
        JPanel panelAgregarTransicion = crearPanelSeccion("Agregar Transición");
        panelPrincipal.add(panelAgregarTransicion);
        panelPrincipal.add(Box.createVerticalStrut(20));
        
        btnConvertirAFD = new JButton("Convertir a AFD");
        btnConvertirAFD.setAlignmentX(CENTER_ALIGNMENT);
        btnConvertirAFD.setFont(new Font("Arial", Font.BOLD, 14));
        panelPrincipal.add(btnConvertirAFD);
        panelPrincipal.add(Box.createVerticalStrut(20));
        
        JPanel panelInfo = crearPanelSeccion("AFND ACTUAL");
        panelPrincipal.add(panelInfo);
        
        JScrollPane scrollPane = new JScrollPane(panelPrincipal);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        add(scrollPane, BorderLayout.CENTER);
    }
    
    private JPanel crearPanelSeccion(String titulo) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(Color.GRAY), titulo));
        
        switch (titulo) {
            case "Entrada AFND" -> {
                JPanel panelCheckboxes = new JPanel(new FlowLayout(FlowLayout.LEFT));
                panelCheckboxes.add(new JLabel("Estado:"));
                chkInicial = new JCheckBox("Inicial");
                chkFinal = new JCheckBox("Final");
                panelCheckboxes.add(chkInicial);
                panelCheckboxes.add(chkFinal);
                panel.add(panelCheckboxes);
            }
                
            case "Agregar Estado" -> {
                JPanel panelNombre = new JPanel(new FlowLayout(FlowLayout.LEFT));
                panelNombre.add(new JLabel("Nombre:"));
                txtNombreEstado = new JTextField(10);
                panelNombre.add(txtNombreEstado);
                panel.add(panelNombre);
                
                btnAgregarEstado = new JButton("Agregar Estado");
                btnAgregarEstado.setAlignmentX(LEFT_ALIGNMENT);
                panel.add(Box.createVerticalStrut(5));
                panel.add(btnAgregarEstado);
            }
                
            case "Símbolo" -> {
                JPanel panelCampoSimbolo = new JPanel(new FlowLayout(FlowLayout.LEFT));
                panelCampoSimbolo.add(new JLabel("Símbolo:"));
                txtSimbolo = new JTextField(5);
                panelCampoSimbolo.add(txtSimbolo);
                panel.add(panelCampoSimbolo);
                
                btnAgregarSimbolo = new JButton("Agregar Símbolo");
                btnAgregarSimbolo.setAlignmentX(LEFT_ALIGNMENT);
                panel.add(Box.createVerticalStrut(5));
                panel.add(btnAgregarSimbolo);
            }
                
            case "Palabra a probar" -> {
                JPanel panelCampoPalabra = new JPanel(new FlowLayout(FlowLayout.LEFT));
                panelCampoPalabra.add(new JLabel("Palabra:"));
                txtPalabra = new JTextField(15);
                panelCampoPalabra.add(txtPalabra);
                panel.add(panelCampoPalabra);
                
                JPanel panelBotonesProbar = new JPanel(new FlowLayout(FlowLayout.LEFT));
                btnProbarAFND = new JButton("Probar AFND");
                btnProbarAFD = new JButton("Probar AFD");
                panelBotonesProbar.add(btnProbarAFND);
                panelBotonesProbar.add(btnProbarAFD);
                panel.add(Box.createVerticalStrut(5));
                panel.add(panelBotonesProbar);
            }
                
            case "Transiciones (origen, símbolo, destino)" -> {
                JPanel panelCamposTrans = new JPanel(new FlowLayout(FlowLayout.LEFT));
                txtOrigen = new JTextField(5);
                txtSimboloTrans = new JTextField(5);
                txtDestino = new JTextField(5);
                panelCamposTrans.add(new JLabel("Origen:"));
                panelCamposTrans.add(txtOrigen);
                panelCamposTrans.add(new JLabel("Símbolo:"));
                panelCamposTrans.add(txtSimboloTrans);
                panelCamposTrans.add(new JLabel("Destino:"));
                panelCamposTrans.add(txtDestino);
                panel.add(panelCamposTrans);
            }
                
            case "Agregar Transición" -> {
                btnAgregarTransicion = new JButton("Agregar Transición");
                btnAgregarTransicion.setAlignmentX(LEFT_ALIGNMENT);
                panel.add(btnAgregarTransicion);
            }
                
            case "AFND ACTUAL" -> {
                txtInfoAFND = new JTextArea(8, 25);
                txtInfoAFND.setEditable(false);
                txtInfoAFND.setFont(new Font("Monospaced", Font.PLAIN, 12));
                JScrollPane scrollInfo = new JScrollPane(txtInfoAFND);
                scrollInfo.setPreferredSize(new Dimension(300, 150));
                panel.add(scrollInfo);
            }
        }
        
        return panel;
    }
    
    public JTextField getTxtNombreEstado() { return txtNombreEstado; }
    public JCheckBox getChkInicial() { return chkInicial; }
    public JCheckBox getChkFinal() { return chkFinal; }
    public JButton getBtnAgregarEstado() { return btnAgregarEstado; }
    public JTextField getTxtSimbolo() { return txtSimbolo; }
    public JButton getBtnAgregarSimbolo() { return btnAgregarSimbolo; }
    public JTextField getTxtOrigen() { return txtOrigen; }
    public JTextField getTxtSimboloTrans() { return txtSimboloTrans; }
    public JTextField getTxtDestino() { return txtDestino; }
    public JButton getBtnAgregarTransicion() { return btnAgregarTransicion; }
    public JTextField getTxtPalabra() { return txtPalabra; }
    public JButton getBtnProbarAFND() { return btnProbarAFND; }
    public JButton getBtnProbarAFD() { return btnProbarAFD; }
    public JButton getBtnConvertirAFD() { return btnConvertirAFD; }
    public JTextArea getTxtInfoAFND() { return txtInfoAFND; }
}