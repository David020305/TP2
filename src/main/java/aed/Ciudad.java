package aed;

public class Ciudad {
    int ganancia;
    int perdida;
    int superavit;
    int id;

    int posicion;
    Ciudad puntero;

    public Ciudad(int id,int ganancia, int perdida, int superavit){
        this.ganancia = ganancia;
        this.perdida = perdida;
        this.superavit = superavit;
        this.id = id;
        this.puntero = null;

    } 
}
