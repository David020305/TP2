package aed;

import java.util.ArrayList;

public class BestEffort {
    //Completar atributos privados 
    private Ciudad[] array;
    private Traslado[] traslados1;
    private Traslado[] traslados2;
    private Heap heapGananciaNeta;
    private Heap heapTimestamp;

    public BestEffort(int cantCiudades, Traslado[] traslados){
        array = new Ciudad[cantCiudades];
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
        int i = 0;
        while (i<n) {
            Traslado maxGanancia = heapGananciaNeta.sacarRaiz();
            res[i] = maxGanancia.id;

            heapTimestamp.eliminarElemento(maxGanancia);
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
