package utilidades;  // ← Debe tener este package

import modelo.AFD;
import modelo.AFND;
import modelo.Estado;
import java.util.*;

public class ConversorAFNDaAFD {
    
    public AFD convertir(AFND afnd) {
        AFD afd = new AFD();
        afd.setAlfabeto(new HashSet<>(afnd.getAlfabeto()));
        
        Set<Estado> estadoInicialAFD = new HashSet<>();
        estadoInicialAFD.add(afnd.getEstadoInicial());
        
        afd.setEstadoInicial(estadoInicialAFD);
        
        Queue<Set<Estado>> porProcesar = new LinkedList<>();
        Set<Set<Estado>> procesados = new HashSet<>();
        
        porProcesar.add(estadoInicialAFD);
        procesados.add(estadoInicialAFD);
        afd.agregarEstado(estadoInicialAFD);
        
        if (contieneEstadoFinal(estadoInicialAFD, afnd)) {
            afd.agregarEstadoFinal(estadoInicialAFD);
        }
        
        while (!porProcesar.isEmpty()) {
            Set<Estado> estadoActual = porProcesar.poll();
            
            for (String simbolo : afnd.getAlfabeto()) {
                Set<Estado> nuevoEstado = mover(estadoActual, simbolo, afnd);
                
                if (!nuevoEstado.isEmpty()) {
                    if (!procesados.contains(nuevoEstado)) {
                        porProcesar.add(nuevoEstado);
                        procesados.add(nuevoEstado);
                        afd.agregarEstado(nuevoEstado);
                        
                        if (contieneEstadoFinal(nuevoEstado, afnd)) {
                            afd.agregarEstadoFinal(nuevoEstado);
                        }
                    }
                    
                    afd.agregarTransicion(estadoActual, simbolo, nuevoEstado);
                }
            }
        }
        
        return afd;
    }
    
    private Set<Estado> mover(Set<Estado> estados, String simbolo, AFND afnd) {
        Set<Estado> resultado = new HashSet<>();
        for (Estado estado : estados) {
            resultado.addAll(afnd.getTransiciones(estado, simbolo));
        }
        return resultado;
    }
    
    private boolean contieneEstadoFinal(Set<Estado> estados, AFND afnd) {
        for (Estado estado : estados) {
            if (estado.esFinal()) {
                return true;
            }
        }
        return false;
    }
}