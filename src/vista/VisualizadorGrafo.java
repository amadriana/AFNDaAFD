package vista;

import modelo.*;
import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class VisualizadorGrafo extends JPanel {
    private Object automata;
    private boolean esAFD;
    private Map<Object, Point> posiciones;
    private static final int RADIO_NODO = 25;
    private static final int ESPACIO = 120;
    
    public VisualizadorGrafo() {
        this.posiciones = new HashMap<>();
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(800, 500));
    }
    
    public void setAutomata(Object automata) {
        this.automata = automata;
        this.esAFD = automata instanceof modelo.AFD;
        calcularPosiciones();
        repaint();
    }
    
    private void calcularPosiciones() {
        posiciones.clear();
        
        if (automata == null) return;
        
        if (!esAFD) {
            AFND afnd = (AFND) automata;
            List<Estado> estados = new ArrayList<>(afnd.getEstados());
            int centerX = getWidth() / 2;
            int centerY = getHeight() / 2;
            double radio = Math.min(centerX, centerY) * 0.6;
            double angulo = 2 * Math.PI / estados.size();
            
            for (int i = 0; i < estados.size(); i++) {
                int x = centerX + (int)(Math.cos(i * angulo) * radio);
                int y = centerY + (int)(Math.sin(i * angulo) * radio);
                posiciones.put(estados.get(i), new Point(x, y));
            }
        } else {
            AFD afd = (AFD) automata;
            List<Set<Estado>> estados = new ArrayList<>(afd.getEstados());
            int cols = (int) Math.ceil(Math.sqrt(estados.size()));
            int rows = (int) Math.ceil(estados.size() / (double) cols);
            
            for (int i = 0; i < estados.size(); i++) {
                int row = i / cols;
                int col = i % cols;
                int x = 100 + col * ESPACIO * 2;
                int y = 100 + row * ESPACIO;
                posiciones.put(estados.get(i), new Point(x, y));
            }
        }
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        if (automata == null) {
            dibujarMensajeVacio(g2d);
            return;
        }
        
        if (!esAFD) {
            dibujarAFND(g2d);
        } else {
            dibujarAFD(g2d);
        }
    }
    
    private void dibujarMensajeVacio(Graphics2D g2d) {
        g2d.setColor(Color.GRAY);
        g2d.setFont(new Font("Arial", Font.BOLD, 16));
        String mensaje = "No hay autómata para visualizar";
        FontMetrics fm = g2d.getFontMetrics();
        int x = (getWidth() - fm.stringWidth(mensaje)) / 2;
        int y = getHeight() / 2;
        g2d.drawString(mensaje, x, y);
    }
    
    private void dibujarAFND(Graphics2D g2d) {
        AFND afnd = (AFND) automata;
        
        for (modelo.Transicion trans : afnd.getTransiciones()) {
            dibujarTransicion(g2d, trans.getOrigen(), trans.getDestino(), trans.getSimbolo(), false);
        }
        
        for (Estado estado : afnd.getEstados()) {
            dibujarEstado(g2d, estado, estado.getNombre(), 
                         estado == afnd.getEstadoInicial(), estado.esFinal());
        }
    }
    
    private void dibujarAFD(Graphics2D g2d) {
        AFD afd = (AFD) automata;
        
        for (Map.Entry<Set<Estado>, Map<String, Set<Estado>>> entry : afd.getTransiciones().entrySet()) {
            for (Map.Entry<String, Set<Estado>> trans : entry.getValue().entrySet()) {
                dibujarTransicion(g2d, entry.getKey(), trans.getValue(), trans.getKey(), true);
            }
        }
        
        for (Set<Estado> estado : afd.getEstados()) {
            String nombre = AFD.getNombreEstado(estado);
            boolean esInicial = estado.equals(afd.getEstadoInicial());
            boolean esFinal = afd.getEstadosFinales().contains(estado);
            dibujarEstado(g2d, estado, nombre, esInicial, esFinal);
        }
    }
    
    private void dibujarTransicion(Graphics2D g2d, Object origen, Object destino, String simbolo, boolean esAFD) {
        Point pOrigen = posiciones.get(origen);
        Point pDestino = posiciones.get(destino);
        
        if (pOrigen == null || pDestino == null) return;
        
        if (origen.equals(destino)) {
            dibujarTransicionReflexiva(g2d, pOrigen, simbolo);
            return;
        }
        
        double angulo = Math.atan2(pDestino.y - pOrigen.y, pDestino.x - pOrigen.x);
        int x1 = pOrigen.x + (int)(Math.cos(angulo) * RADIO_NODO);
        int y1 = pOrigen.y + (int)(Math.sin(angulo) * RADIO_NODO);
        int x2 = pDestino.x - (int)(Math.cos(angulo) * RADIO_NODO);
        int y2 = pDestino.y - (int)(Math.sin(angulo) * RADIO_NODO);
        
        g2d.setColor(Color.BLACK);
        g2d.drawLine(x1, y1, x2, y2);
        
        dibujarFlecha(g2d, x1, y1, x2, y2);
        
        int textoX = (x1 + x2) / 2;
        int textoY = (y1 + y2) / 2 - 10;
        g2d.setColor(Color.BLUE);
        g2d.setFont(new Font("Arial", Font.BOLD, 12));
        g2d.drawString(simbolo, textoX, textoY);
    }
    
    private void dibujarTransicionReflexiva(Graphics2D g2d, Point punto, String simbolo) {
        int x = punto.x;
        int y = punto.y - RADIO_NODO - 10;
        int ancho = 40;
        int alto = 30;
        
        g2d.setColor(Color.BLACK);
        g2d.drawArc(x - ancho/2, y, ancho, alto, 0, 180);
        
        int flechaX = x;
        int flechaY = y + alto/2;
        g2d.fillPolygon(
            new int[]{flechaX, flechaX - 5, flechaX + 5},
            new int[]{flechaY, flechaY - 8, flechaY - 8},
            3
        );
        
        g2d.setColor(Color.BLUE);
        g2d.setFont(new Font("Arial", Font.BOLD, 12));
        FontMetrics fm = g2d.getFontMetrics();
        int textoX = x - fm.stringWidth(simbolo) / 2;
        int textoY = y - 5;
        g2d.drawString(simbolo, textoX, textoY);
    }
    
    private void dibujarFlecha(Graphics2D g2d, int x1, int y1, int x2, int y2) {
        double angulo = Math.atan2(y2 - y1, x2 - x1);
        int flechaSize = 8;
        
        int x3 = x2 - (int)(Math.cos(angulo) * flechaSize);
        int y3 = y2 - (int)(Math.sin(angulo) * flechaSize);
        
        int x4 = (int)(x3 - Math.cos(angulo - Math.PI/6) * flechaSize);
        int y4 = (int)(y3 - Math.sin(angulo - Math.PI/6) * flechaSize);
        
        int x5 = (int)(x3 - Math.cos(angulo + Math.PI/6) * flechaSize);
        int y5 = (int)(y3 - Math.sin(angulo + Math.PI/6) * flechaSize);
        
        g2d.fillPolygon(new int[]{x2, x4, x5}, new int[]{y2, y4, y5}, 3);
    }
    
    private void dibujarEstado(Graphics2D g2d, Object estado, String nombre, boolean esInicial, boolean esFinal) {
        Point punto = posiciones.get(estado);
        if (punto == null) return;
        
        if (esFinal) {
            g2d.setColor(Color.WHITE);
            g2d.fillOval(punto.x - RADIO_NODO, punto.y - RADIO_NODO, RADIO_NODO * 2, RADIO_NODO * 2);
            g2d.setColor(Color.RED);
            g2d.setStroke(new BasicStroke(3));
            g2d.drawOval(punto.x - RADIO_NODO, punto.y - RADIO_NODO, RADIO_NODO * 2, RADIO_NODO * 2);
            g2d.drawOval(punto.x - RADIO_NODO + 4, punto.y - RADIO_NODO + 4, RADIO_NODO * 2 - 8, RADIO_NODO * 2 - 8);
        } else {
            g2d.setColor(Color.WHITE);
            g2d.fillOval(punto.x - RADIO_NODO, punto.y - RADIO_NODO, RADIO_NODO * 2, RADIO_NODO * 2);
            g2d.setColor(Color.BLACK);
            g2d.setStroke(new BasicStroke(2));
            g2d.drawOval(punto.x - RADIO_NODO, punto.y - RADIO_NODO, RADIO_NODO * 2, RADIO_NODO * 2);
        }
        
        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("Arial", Font.BOLD, 12));
        FontMetrics fm = g2d.getFontMetrics();
        int textoX = punto.x - fm.stringWidth(nombre) / 2;
        int textoY = punto.y + fm.getHeight() / 4;
        g2d.drawString(nombre, textoX, textoY);
        
        if (esInicial) {
            dibujarFlechaInicial(g2d, punto);
        }
        
        g2d.setStroke(new BasicStroke(1));
    }
    
    private void dibujarFlechaInicial(Graphics2D g2d, Point punto) {
        int x1 = punto.x - RADIO_NODO - 40;
        int y1 = punto.y;
        int x2 = punto.x - RADIO_NODO;
        int y2 = punto.y;
        
        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(2));
        g2d.drawLine(x1, y1, x2, y2);
        dibujarFlecha(g2d, x1, y1, x2, y2);
    }
    
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(800, 500);
    }
}