package modelo;

import java.util.*;

public class AFND {
    private Set<Estado> estados;
    private Set<String> alfabeto;
    private Estado estadoInicial;
    private Set<Estado> estadosFinales;
    private List<Transicion> transiciones;
    
    public AFND() {
        this.estados = new HashSet<>();
        this.alfabeto = new HashSet<>();
        this.estadosFinales = new HashSet<>();
        this.transiciones = new ArrayList<>();
    }
    
    public void agregarEstado(Estado estado) {
        estados.add(estado);
        if (estado.esInicial()) estadoInicial = estado;
        if (estado.esFinal()) estadosFinales.add(estado);
    }
    
    public void agregarSimbolo(String simbolo) {
        alfabeto.add(simbolo);
    }
    
    public void agregarTransicion(Estado origen, String simbolo, Estado destino) {
        transiciones.add(new Transicion(origen, simbolo, destino));
    }
    
    public Set<Estado> getEstados() { return estados; }
    public Set<String> getAlfabeto() { return alfabeto; }
    public Estado getEstadoInicial() { return estadoInicial; }
    public Set<Estado> getEstadosFinales() { return estadosFinales; }
    public List<Transicion> getTransiciones() { return transiciones; }
    
    public Set<Estado> getTransiciones(Estado estado, String simbolo) {
        Set<Estado> destinos = new HashSet<>();
        for (Transicion t : transiciones) {
            if (t.getOrigen().equals(estado) && t.getSimbolo().equals(simbolo)) {
                destinos.add(t.getDestino());
            }
        }
        return destinos;
    }
}
