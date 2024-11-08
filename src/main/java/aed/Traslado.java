package aed;

public class Traslado {
    
    int id;
    int origen;
    int destino;
    int gananciaNeta;
    int timestamp;

    Traslado puntero;
    int posicion;

    public Traslado(int id, int origen, int destino, int gananciaNeta, int timestamp){
        this.id = id;
        this.origen = origen;
        this.destino = destino;
        this.gananciaNeta = gananciaNeta;
        this.timestamp = timestamp;
        this.puntero = null;
        this.posicion = -1;
    }
    public Traslado(Traslado otro) {
        this.id = otro.id;
        this.origen = otro.origen;
        this.destino = otro.destino;
        this.gananciaNeta = otro.gananciaNeta;
        this.timestamp = otro.timestamp;
        this.puntero = otro.puntero;
        this.posicion = otro.posicion;
    }
}
