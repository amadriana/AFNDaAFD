package modelo;

import java.util.*;

public class AFD {
    private Set<Set<Estado>> estados;
    private Set<String> alfabeto;
    private Set<Estado> estadoInicial;
    private Set<Set<Estado>> estadosFinales;
    private Map<Set<Estado>, Map<String, Set<Estado>>> transiciones;
    
    public AFD() {
        this.estados = new HashSet<>();
        this.alfabeto = new HashSet<>();
        this.estadosFinales = new HashSet<>();
        this.transiciones = new HashMap<>();
    }
    
    public Set<Set<Estado>> getEstados() { return estados; }
    public Set<String> getAlfabeto() { return alfabeto; }
    public Set<Estado> getEstadoInicial() { return estadoInicial; }
    public Set<Set<Estado>> getEstadosFinales() { return estadosFinales; }
    public Map<Set<Estado>, Map<String, Set<Estado>>> getTransiciones() { return transiciones; }
    
    public void setAlfabeto(Set<String> alfabeto) { this.alfabeto = alfabeto; }
    public void setEstadoInicial(Set<Estado> estadoInicial) { this.estadoInicial = estadoInicial; }
    
    public void agregarEstado(Set<Estado> estado) {
        estados.add(estado);
    }
    
    public void agregarEstadoFinal(Set<Estado> estadoFinal) {
        estadosFinales.add(estadoFinal);
    }
    
    public void agregarTransicion(Set<Estado> origen, String simbolo, Set<Estado> destino) {
        transiciones.putIfAbsent(origen, new HashMap<>());
        transiciones.get(origen).put(simbolo, destino);
    }
    
    public static String getNombreEstado(Set<Estado> estados) {
        if (estados == null || estados.isEmpty()) return "∅";
        List<String> nombres = new ArrayList<>();
        for (Estado e : estados) {
            nombres.add(e.getNombre());
        }
        Collections.sort(nombres);
        return String.join("", nombres);
    }
}