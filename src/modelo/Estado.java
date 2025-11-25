package modelo;

public class Estado {
    private final String nombre;
    private final boolean esInicial;
    private final boolean esFinal;
    
    public Estado(String nombre, boolean esInicial, boolean esFinal) {
        this.nombre = nombre;
        this.esInicial = esInicial;
        this.esFinal = esFinal;
    }
    
    public String getNombre() { return nombre; }
    public boolean esInicial() { return esInicial; }
    public boolean esFinal() { return esFinal; }
    
    @Override
    public String toString() { return nombre; }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Estado estado = (Estado) obj;
        return nombre.equals(estado.nombre);
    }
    @Override
    public int hashCode() { return nombre.hashCode(); }
}
