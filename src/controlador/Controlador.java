package controlador;

import vista.*;
import modelo.*;
import java.util.*;
import javax.swing.*;
import utilidades.ConversorAFNDaAFD;

public class Controlador {
    private VentanaPrincipal vista;
    private AFND afnd;
    private AFD afd;

    public Controlador(VentanaPrincipal vista) {
        this.vista = vista;
        this.afnd = new AFND();
        this.afd = null;

        inicializarEventos();
        actualizarInfoAFND();
    }

    private void inicializarEventos() {

        vista.getPanelEntrada().getBtnAgregarEstado().addActionListener(e -> agregarEstado());

        vista.getPanelEntrada().getBtnAgregarSimbolo().addActionListener(e -> agregarSimbolo());

        vista.getPanelEntrada().getBtnAgregarTransicion().addActionListener(e -> agregarTransicion());

        vista.getPanelEntrada().getBtnProbarAFND().addActionListener(e -> probarPalabraAFND());
        vista.getPanelEntrada().getBtnProbarAFD().addActionListener(e -> probarPalabraAFD());

        vista.getPanelEntrada().getBtnConvertirAFD().addActionListener(e -> convertirAFD());
    }

    private void agregarEstado() {
        String nombre = vista.getPanelEntrada().getTxtNombreEstado().getText().trim();
        boolean esInicial = vista.getPanelEntrada().getChkInicial().isSelected();
        boolean esFinal = vista.getPanelEntrada().getChkFinal().isSelected();

        if (nombre.isEmpty()) {
            JOptionPane.showMessageDialog(vista, "El nombre del estado no puede estar vacío");
            return;
        }

        if (esInicial && afnd.getEstadoInicial() != null) {
            JOptionPane.showMessageDialog(vista, "Ya existe un estado inicial");
            return;
        }

        Estado nuevoEstado = new Estado(nombre, esInicial, esFinal);
        afnd.agregarEstado(nuevoEstado);

        // Limpiar campos
        vista.getPanelEntrada().getTxtNombreEstado().setText("");
        vista.getPanelEntrada().getChkInicial().setSelected(false);
        vista.getPanelEntrada().getChkFinal().setSelected(false);

        actualizarInfoAFND();
    }

    private void agregarSimbolo() {
        String simbolo = vista.getPanelEntrada().getTxtSimbolo().getText().trim();

        if (simbolo.isEmpty()) {
            JOptionPane.showMessageDialog(vista, "El símbolo no puede estar vacío");
            return;
        }

        afnd.agregarSimbolo(simbolo);
        vista.getPanelEntrada().getTxtSimbolo().setText("");

        actualizarInfoAFND();
    }

    private void agregarTransicion() {
        String origenStr = vista.getPanelEntrada().getTxtOrigen().getText().trim();
        String simbolo = vista.getPanelEntrada().getTxtSimboloTrans().getText().trim();
        String destinoStr = vista.getPanelEntrada().getTxtDestino().getText().trim();

        if (origenStr.isEmpty() || simbolo.isEmpty() || destinoStr.isEmpty()) {
            JOptionPane.showMessageDialog(vista, "Todos los campos de transición deben estar llenos");
            return;
        }

        Estado origen = buscarEstado(origenStr);
        Estado destino = buscarEstado(destinoStr);

        if (origen == null || destino == null) {
            JOptionPane.showMessageDialog(vista, "Estado origen o destino no encontrado");
            return;
        }

        if (!afnd.getAlfabeto().contains(simbolo)) {
            JOptionPane.showMessageDialog(vista, "El símbolo debe estar en el alfabeto");
            return;
        }

        afnd.agregarTransicion(origen, simbolo, destino);

        vista.getPanelEntrada().getTxtOrigen().setText("");
        vista.getPanelEntrada().getTxtSimboloTrans().setText("");
        vista.getPanelEntrada().getTxtDestino().setText("");

        actualizarInfoAFND();
    }

    private Estado buscarEstado(String nombre) {
        for (Estado estado : afnd.getEstados()) {
            if (estado.getNombre().equals(nombre)) {
                return estado;
            }
        }
        return null;
    }

    private void probarPalabraAFND() {
        String palabra = vista.getPanelEntrada().getTxtPalabra().getText().trim();
        JOptionPane.showMessageDialog(vista, "Prueba AFND para: " + palabra);
    }

    private void probarPalabraAFD() {
        String palabra = vista.getPanelEntrada().getTxtPalabra().getText().trim();
        JOptionPane.showMessageDialog(vista, "Prueba AFD para: " + palabra);
    }

    private void convertirAFD() {
        if (afnd.getEstados().isEmpty()) {
            JOptionPane.showMessageDialog(vista, "No hay estados definidos en el AFND");
            return;
        }

        if (afnd.getAlfabeto().isEmpty()) {
            JOptionPane.showMessageDialog(vista, "No hay símbolos en el alfabeto");
            return;
        }

        if (afnd.getEstadoInicial() == null) {
            JOptionPane.showMessageDialog(vista, "No hay estado inicial definido");
            return;
        }

        ConversorAFNDaAFD conversor = new ConversorAFNDaAFD();
        afd = conversor.convertir(afnd);

        mostrarAFD();
        mostrarTablasTransiciones();

        vista.getPanelVisualizacion().mostrarPestanaAFD();
    }

    private void actualizarInfoAFND() {
        StringBuilder info = new StringBuilder();
        info.append("=== AFND ACTUAL ===\n");
        info.append("Estados: ").append(obtenerNombresEstados()).append("\n");
        info.append("Alfabeto: ").append(afnd.getAlfabeto()).append("\n");
        info.append("Estado Inicial: ").append(
                afnd.getEstadoInicial() != null ? afnd.getEstadoInicial().getNombre() : "No definido").append("\n");
        info.append("Estados Finales: ").append(obtenerEstadosFinales()).append("\n");
        info.append("Transiciones:\n").append(obtenerTransiciones());

        vista.getPanelEntrada().getTxtInfoAFND().setText(info.toString());

        vista.getPanelVisualizacion().getVisualizadorAFND().setAutomata(afnd);
    }

    private String obtenerNombresEstados() {
        List<String> nombres = new ArrayList<>();
        for (Estado estado : afnd.getEstados()) {
            nombres.add(estado.getNombre());
        }
        Collections.sort(nombres);
        return nombres.toString();
    }

    private String obtenerEstadosFinales() {
        List<String> nombres = new ArrayList<>();
        for (Estado estado : afnd.getEstadosFinales()) {
            nombres.add(estado.getNombre());
        }
        Collections.sort(nombres);
        return nombres.toString();
    }

    private String obtenerTransiciones() {
        StringBuilder trans = new StringBuilder();
        for (Transicion t : afnd.getTransiciones()) {
            trans.append(t.getOrigen().getNombre())
                    .append(" --").append(t.getSimbolo()).append("--> ")
                    .append(t.getDestino().getNombre())
                    .append("\n");
        }
        return trans.toString();
    }

    private void mostrarAFD() {
        if (afd == null)
            return;

        StringBuilder infoAFD = new StringBuilder();
        infoAFD.append("=== AFD CONVERTIDO ===\n");
        infoAFD.append("Estados: ");
        for (Set<Estado> estado : afd.getEstados()) {
            infoAFD.append(AFD.getNombreEstado(estado)).append(" ");
        }
        infoAFD.append("\nAlfabeto: ").append(afd.getAlfabeto()).append("\n");
        infoAFD.append("Estado Inicial: ").append(AFD.getNombreEstado(afd.getEstadoInicial())).append("\n");
        infoAFD.append("Estados Finales: ");
        for (Set<Estado> estado : afd.getEstadosFinales()) {
            infoAFD.append(AFD.getNombreEstado(estado)).append(" ");
        }
        infoAFD.append("\nTransiciones:\n");

        for (Map.Entry<Set<Estado>, Map<String, Set<Estado>>> entry : afd.getTransiciones().entrySet()) {
            for (Map.Entry<String, Set<Estado>> trans : entry.getValue().entrySet()) {
                infoAFD.append(AFD.getNombreEstado(entry.getKey()))
                        .append(" --").append(trans.getKey()).append("--> ")
                        .append(AFD.getNombreEstado(trans.getValue()))
                        .append("\n");
            }
        }

        vista.getPanelVisualizacion().getVisualizadorAFND().setAutomata(afnd);
        vista.getPanelVisualizacion().getVisualizadorAFD().setAutomata(afd);
    }

    private void mostrarTablasTransiciones() {
        // Tabla AFND
        StringBuilder tablaAFND = new StringBuilder();
        tablaAFND.append("Tabla de Transiciones AFND\n");
        tablaAFND.append("Estado\t");
        for (String simbolo : afnd.getAlfabeto()) {
            tablaAFND.append(simbolo).append("\t");
        }
        tablaAFND.append("\n");

        for (Estado estado : afnd.getEstados()) {
            tablaAFND.append(estado.getNombre()).append(estado.esFinal() ? "*" : "").append("\t");
            for (String simbolo : afnd.getAlfabeto()) {
                Set<Estado> destinos = afnd.getTransiciones(estado, simbolo);
                List<String> nombresDestinos = new ArrayList<>();
                for (Estado dest : destinos) {
                    nombresDestinos.add(dest.getNombre());
                }
                Collections.sort(nombresDestinos);
                tablaAFND.append(nombresDestinos.isEmpty() ? "∅" : String.join(",", nombresDestinos)).append("\t");
            }
            tablaAFND.append("\n");
        }

        vista.getPanelVisualizacion().getTxtTablaAFND().setText(tablaAFND.toString());

        // Tabla AFD
        if (afd != null) {
            StringBuilder tablaAFD = new StringBuilder();
            tablaAFD.append("Tabla de Transiciones AFD\n");
            tablaAFD.append("Estado\t");
            for (String simbolo : afd.getAlfabeto()) {
                tablaAFD.append(simbolo).append("\t");
            }
            tablaAFD.append("\n");

            for (Set<Estado> estado : afd.getEstados()) {
                String nombreEstado = AFD.getNombreEstado(estado);
                boolean esFinal = afd.getEstadosFinales().contains(estado);
                tablaAFD.append(nombreEstado).append(esFinal ? "*" : "").append("\t");

                for (String simbolo : afd.getAlfabeto()) {
                    Map<String, Set<Estado>> transiciones = afd.getTransiciones().get(estado);
                    if (transiciones != null && transiciones.containsKey(simbolo)) {
                        Set<Estado> destino = transiciones.get(simbolo);
                        tablaAFD.append(AFD.getNombreEstado(destino)).append("\t");
                    } else {
                        tablaAFD.append("∅\t");
                    }
                }
                tablaAFD.append("\n");
            }

            vista.getPanelVisualizacion().getTxtTablaAFD().setText(tablaAFD.toString());
        }
    }
}