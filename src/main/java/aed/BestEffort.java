package aed;

import java.util.ArrayList;

public class BestEffort {
    //Completar atributos privados 
    private Ciudad[] array;
    private Traslado[] traslados1;
    private Traslado[] traslados2;
    private Heap heapGananciaNeta;
    private Heap heapTimestamp;
    private int[] mayorSuperavit;
    private ArrayList mayorGanacia;
    private ArrayList mayorPerdida;

    public BestEffort(int cantCiudades, Traslado[] traslados){
        array = new Ciudad[cantCiudades];
        mayorSuperavit =  new int[2];
        int i = 0;
        while (i < cantCiudades) {  // O(|cantCiudades|)
            array[i] = new Ciudad(0, 0, 0);  // Crear y asignar nuevas ciudades
            i++;
        } 


        this.traslados1 = traslados;
        this.traslados2 = traslados;
        
        apuntar apuntador = new apuntar();
        apuntador.puntero(traslados1, traslados2);

        TrasladoComparators comparators = new TrasladoComparators();

        heapGananciaNeta = new Heap(comparators.POR_GANANCIA_NETA, traslados1);
        heapTimestamp = new Heap(comparators.POR_TIMESTAMP, traslados2);
    }

    public void registrarTraslados(Traslado[] traslados){
        // Implementar
    }

    public int[] despacharMasRedituables(int n){
        int[] res = new int[n];
        
        int[] anadir = new int[2];
        int i = 0;
        while (i<n) {
            Traslado maxGanancia = heapGananciaNeta.sacarRaiz();
            res[i] = maxGanancia.id;

            array[maxGanancia.origen].ganancia += maxGanancia.gananciaNeta;
            array[maxGanancia.destino].perdida += maxGanancia.gananciaNeta;

            array[maxGanancia.origen].superavit = array[maxGanancia.origen].ganancia - array[maxGanancia.origen].perdida;
            array[maxGanancia.destino].superavit = array[maxGanancia.destino].ganancia -array[maxGanancia.destino].perdida;

            // sacar el mayorsuperavir de las dos ciudades despachadas
            if (array[maxGanancia.origen].superavit > array[maxGanancia.destino].superavit){
                anadir[0] = maxGanancia.origen;
                anadir[1] = array[maxGanancia.origen].superavit;
            }
            else if (array[maxGanancia.origen].superavit < array[maxGanancia.destino].superavit) {
                anadir[0] = maxGanancia.destino;
                anadir[1] = array[maxGanancia.destino].superavit;
            }
            else if (maxGanancia.origen > maxGanancia.destino) {
                anadir[0] = maxGanancia.destino;
                anadir[1] = array[maxGanancia.destino].superavit;
            }
            else{
                anadir[0] = maxGanancia.origen;
                anadir[1] = array[maxGanancia.origen].superavit;
            }

            // saca el mayor o mayores --> mayoresGanacias de los dos despachos




            if (mayorSuperavit.length == 0) {
                mayorSuperavit[0] = anadir[0];
                mayorSuperavit[1] = anadir [1];
            }
            else if(mayorSuperavit[1] < anadir[1]){
                mayorSuperavit[0] = anadir[0]; 
                mayorSuperavit[1] = anadir[1];
            }
            else if(mayorSuperavit[1] == anadir[1]){
                if(anadir[0] < mayorSuperavit[0]){
                    mayorSuperavit[0] = anadir[0]; 
                    mayorSuperavit[1] = anadir[1];
                }
            }

            heapTimestamp.eliminarElemento(maxGanancia.puntero);
            i++;
        }

        return res;
    }

    public int[] despacharMasAntiguos(int n){
        // Implementar
        return null;
    }

    public int ciudadConMayorSuperavit(){
        // Implementar
        return 0;
    }

    public ArrayList<Integer> ciudadesConMayorGanancia(){
        // Implementar
        return null;
    }

    public ArrayList<Integer> ciudadesConMayorPerdida(){
        // Implementar
        return null;
    }

    public int gananciaPromedioPorTraslado(){
        // Implementar
        return 0;
    }
    
}
