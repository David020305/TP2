package aed;

import java.util.ArrayList;

public class BestEffort {
    //Completar atributos privados 
    private Ciudad[] ciudadesarray;
    private Traslado[] traslados1;
    private Traslado[] traslados2;
    private Heap heapGananciaNeta;
    private Heap heapTimestamp;
    private int[] mayorSuperavit;
    private ArrayList mayorGanacia;
    private ArrayList mayorPerdida;

    public BestEffort(int cantCiudades, Traslado[] traslados){
        ciudadesarray = new Ciudad[cantCiudades];
        mayorSuperavit =  new int[1];
        int i = 0;
        while (i < cantCiudades) {  // O(|cantCiudades|)
            ciudadesarray[i] = new Ciudad(0, 0, 0);  // Crear y asignar nuevas ciudades
            i++;
        } 


        this.traslados1 = new Traslado[traslados.length];
        this.traslados2 = new Traslado[traslados.length];

        for (int j = 0; j < traslados.length; j++) {//O(T)
            Traslado original = traslados[j];
            // Crear nuevas instancias de Traslado con los mismos valores que 'original'
            this.traslados1[j] = new Traslado(original.id, original.origen, original.destino, original.gananciaNeta, original.timestamp);
            this.traslados2[j] = new Traslado(original.id, original.origen, original.destino, original.gananciaNeta, original.timestamp);
        }
        /* Crea un nuevo objeto y hace que se apunten traslado1 y traslado2
        apuntar apuntador = new apuntar();
        apuntador.puntero(traslados1, traslados2);
        */
        apuntador(traslados1, traslados2);//O(T)

        TrasladoComparators comparators = new TrasladoComparators();

        heapGananciaNeta = new Heap(comparators.POR_GANANCIA_NETA, traslados1);// O(T)
        heapTimestamp = new Heap(comparators.POR_TIMESTAMP, traslados2);//O(T)

    }
    
    private void apuntador (Traslado[] trasladosone, Traslado[] trasladostwo){ // O(T)
        int length = traslados1.length;

        for (int i = 0; i < length; i++) {
            trasladosone[i].puntero = trasladostwo[i]; 
        }
        
        for (int j = 0; j < length; j++) {
            trasladostwo[j].puntero = trasladosone[j]; 
        }
    }


    public void registrarTraslados(Traslado[] traslados){
        // Implementar
    }

    public int[] despacharMasRedituables(int n){
        int[] res = new int[n];
        //10 traslado
        // 50 pesos
        // 45 --> 55
        //  --> 40
        int anadir;
        int i = 0;
        while (i<n) {
            Traslado maxGanancia = heapGananciaNeta.sacarRaiz();// O(log T) 
            res[i] = maxGanancia.id;//lo agrego al array res

            ciudadesarray[maxGanancia.origen].ganancia += maxGanancia.gananciaNeta;
            ciudadesarray[maxGanancia.destino].perdida += maxGanancia.gananciaNeta;

            ciudadesarray[maxGanancia.origen].superavit = ciudadesarray[maxGanancia.origen].ganancia - ciudadesarray[maxGanancia.origen].perdida;
            ciudadesarray[maxGanancia.destino].superavit = ciudadesarray[maxGanancia.destino].ganancia -ciudadesarray[maxGanancia.destino].perdida;

            // sacar el mayorsuperavir de las dos ciudades despachadas // esto tratar de hacer un metodo privado donde como
            // parametro recibe como queires comparar, ya sea por superavit o ganacia o perdida 

            if (ciudadesarray[maxGanancia.origen].superavit > ciudadesarray[maxGanancia.destino].superavit){
                anadir = maxGanancia.origen;                
            }
            else if (ciudadesarray[maxGanancia.origen].superavit < ciudadesarray[maxGanancia.destino].superavit) {
                anadir = maxGanancia.destino;                
            }
            else if (maxGanancia.origen > maxGanancia.destino) {
                anadir = maxGanancia.destino;
            }
            else{
                anadir = maxGanancia.origen;     
            }

            // saca el mayor o mayores --> mayoresGanacias de los dos despachos

            if (mayorSuperavit.length == 0) {
                mayorSuperavit[0] = anadir;           
            }
            else if(mayorSuperavit[0] < ciudadesarray[anadir].superavit){
                mayorSuperavit[0] = anadir; 
            }
            else if(mayorSuperavit[0] == ciudadesarray[anadir].superavit){
                if(anadir < mayorSuperavit[0]){// comparar que id es menor
                    mayorSuperavit[0] = anadir;    
                }
            }

            heapTimestamp.eliminarElemento(maxGanancia.puntero);// O(log T)
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
