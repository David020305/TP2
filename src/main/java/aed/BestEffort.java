package aed;

import java.util.ArrayList;


public class BestEffort {
    //Completar atributos privados 
    private Ciudad[] ciudadesarray;
    private Traslado[] traslados1;
    private Traslado[] traslados2;
    //private Traslado[] traslados3;
    //private Traslado[] traslados4;
    private Heap heapGananciaNeta;
    private Heap heapTimestamp;
    private int[] mayorSuperavit;
    private ArrayList<Integer> mayorGanacia;
    private int valorDeGanacia;
    private ArrayList<Integer> mayorPerdida;
    private int valorDePerida;
    private int totalTraslados;
    private int gananciaTotal;

    public BestEffort(int cantCiudades, Traslado[] traslados){
        ciudadesarray = new Ciudad[cantCiudades];
        mayorSuperavit =  new int[1];
        mayorGanacia = new ArrayList<>();
        mayorPerdida = new ArrayList<>();
        valorDeGanacia = 0;
        valorDePerida = 0;
        totalTraslados = 0;  // LO AGREGO AL INICIALIZAR EN 0
        gananciaTotal = 0;   // LO AGREGO AL INICIALIZAR EN 0

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

        imprimirHeapGananciaNeta();
        imprimirHeapTimestamp();
    }

    public void imprimirHeapGananciaNeta() {
        System.out.println("Heap de Ganancia NetaA:");
        for (int i = 0; i < heapGananciaNeta.tamano(); i++){
            Traslado t = heapGananciaNeta.obtenerElemento(i); // Obtener y eliminar el elemento con la mayor ganancia
            System.out.println("ID: " + t.id + ", Ganancia Neta: " + t.gananciaNeta + " Posicion: "+t.posicion);
        }
    }
    public void imprimirHeapTimestamp() {
    System.out.println("Heap de Timestamp:");
    for (int i = 0; i < heapTimestamp.tamano(); i++) {
        Traslado t = heapTimestamp.obtenerElemento(i); // Asegúrate de tener este método en la clase Heap
        System.out.println("ID: " + t.id + ", Timestamp: " + t.timestamp + " Posicion: "+t.posicion);
    }
    }

    private void apuntador (Traslado[] trasladosone, Traslado[] trasladostwo){ // O(T)
        int length = trasladosone.length;

        for (int i = 0; i < length; i++) {
            trasladosone[i].puntero = trasladostwo[i]; 
        }
        
        for (int j = 0; j < length; j++) {
            trasladostwo[j].puntero = trasladosone[j]; 
        }
    }


    public void registrarTraslados(Traslado[] traslados){//O (T (log T))
        int t = 0;
        this.traslados1 = new Traslado[traslados.length];
        this.traslados2 = new Traslado[traslados.length];

        for (int j = 0; j < traslados.length; j++) {//O(T)
            Traslado original = traslados[j];
            // Crear nuevas instancias de Traslado con los mismos valores que 'original'
            this.traslados1[j] = new Traslado(original.id, original.origen, original.destino, original.gananciaNeta, original.timestamp);
            this.traslados2[j] = new Traslado(original.id, original.origen, original.destino, original.gananciaNeta, original.timestamp);
        }

        apuntador(traslados1, traslados2);//O(T)

        while (t < traslados.length) {
            heapGananciaNeta.encolar(traslados1[t]);
            heapTimestamp.encolar(traslados2[t]);
            t++;
        }
    }

    public int[] despacharMasRedituables(int n){//O(n(log T))
        
        int i = 0;
        if (n > heapGananciaNeta.tamano()) {
            n = heapGananciaNeta.tamano(); // Limitar n al tamaño del heap
        }

        int[] res = new int[n];
        while (i < n) {//O (n)
            //Traslado maxGanancia = new Traslado(heapGananciaNeta.obtenerRaiz().id, heapGananciaNeta.obtenerRaiz().origen, heapGananciaNeta.obtenerRaiz().destino, heapGananciaNeta.obtenerRaiz().gananciaNeta, heapGananciaNeta.obtenerRaiz().timestamp) ;// O(log T) 
            Traslado maxGanancia = new Traslado(heapGananciaNeta.sacarRaiz());
            res[i] = maxGanancia.id;//lo agrego al array res
            System.out.println(maxGanancia.id + "id de mayor ganacia, origen--> " +maxGanancia.origen);

            totalTraslados += 1;
            gananciaTotal += maxGanancia.gananciaNeta;

            ciudadesarray[maxGanancia.origen].ganancia += maxGanancia.gananciaNeta;
            ciudadesarray[maxGanancia.destino].perdida += maxGanancia.gananciaNeta;

            comparoSuperavit(maxGanancia);
            comparoMayor(maxGanancia.origen, mayorGanacia);
            comparoMayorG(maxGanancia.destino, mayorPerdida);
            
            System.out.println(maxGanancia.puntero.id + " el id de otro heap");
            System.out.println(maxGanancia.puntero.posicion + " la posicion de otro heap");
            heapTimestamp.eliminarElemento(maxGanancia.puntero);// O(log T)

            i++;
        }
        imprimirHeapGananciaNeta();
        imprimirHeapTimestamp();

        return res;
    }
    private void comparoMayor(int comparar, ArrayList<Integer> list) {
        if (list.isEmpty()) {
            list.add(comparar);
            valorDeGanacia = ciudadesarray[comparar].ganancia;
        } else if (valorDeGanacia == ciudadesarray[comparar].ganancia) {
            list.add(comparar);
            valorDeGanacia = ciudadesarray[comparar].ganancia;
        } else if (valorDeGanacia < ciudadesarray[comparar].ganancia) {
            list.clear();
            list.add(comparar);
            valorDeGanacia = ciudadesarray[comparar].ganancia;
        }
        
    }
    private void comparoMayorG(int comparar, ArrayList<Integer> list) {
        if (list.isEmpty()) {
            list.add(comparar);
            valorDePerida = ciudadesarray[comparar].perdida;
        } else if (valorDePerida == ciudadesarray[comparar].perdida) {
            list.add(comparar);
            valorDePerida = ciudadesarray[comparar].perdida;
        } else if (valorDePerida < ciudadesarray[comparar].perdida) {
            list.clear();
            list.add(comparar);
            valorDePerida = ciudadesarray[comparar].perdida;
        }
        
    }
    
    private void comparoSuperavit(Traslado comparar) {
        int anadir;
        

        ciudadesarray[comparar.origen].superavit = ciudadesarray[comparar.origen].ganancia - ciudadesarray[comparar.origen].perdida;
        ciudadesarray[comparar.destino].superavit = ciudadesarray[comparar.destino].ganancia -ciudadesarray[comparar.destino].perdida;

        if (ciudadesarray[comparar.origen].superavit > ciudadesarray[comparar.destino].superavit){
            anadir = comparar.origen;                
        } else if (ciudadesarray[comparar.origen].superavit < ciudadesarray[comparar.destino].superavit) {
            anadir = comparar.destino;                
        } else if (comparar.origen > comparar.destino) {
            anadir = comparar.destino;
        } else{
            anadir = comparar.origen;     
        }

        if (mayorSuperavit.length == 0) {
            mayorSuperavit[0] = anadir;           
        } else if(ciudadesarray[mayorSuperavit[0]].superavit < ciudadesarray[anadir].superavit){
            mayorSuperavit[0] = anadir; 
        } else if(ciudadesarray[mayorSuperavit[0]].superavit == ciudadesarray[anadir].superavit){
            if(anadir < mayorSuperavit[0]){// comparar que id es menor
                mayorSuperavit[0] = anadir;    
            }
        }
    }

    public int[] despacharMasAntiguos(int n){
        
        int i = 0;
        if (n > heapGananciaNeta.tamano()) {
            n = heapGananciaNeta.tamano(); // Limitar n al tamaño del heap
        }

        int[] res = new int[n];
        while (i < n) {//O (n)
            //Traslado maxGanancia = new Traslado(heapGananciaNeta.obtenerRaiz().id, heapGananciaNeta.obtenerRaiz().origen, heapGananciaNeta.obtenerRaiz().destino, heapGananciaNeta.obtenerRaiz().gananciaNeta, heapGananciaNeta.obtenerRaiz().timestamp) ;// O(log T) 
            Traslado masAntiguo = new Traslado(heapTimestamp.sacarRaiz());
            res[i] = masAntiguo.id;//lo agrego al array res
            System.out.println(masAntiguo.id + "id de mayor ganacia, origen--> " +masAntiguo.origen);

            totalTraslados += 1;
            gananciaTotal += masAntiguo.gananciaNeta;

            ciudadesarray[masAntiguo.origen].ganancia += masAntiguo.gananciaNeta;
            ciudadesarray[masAntiguo.destino].perdida += masAntiguo.gananciaNeta;

            comparoSuperavit(masAntiguo);
            comparoMayor(masAntiguo.origen, mayorGanacia);
            comparoMayorG(masAntiguo.destino, mayorPerdida);
            
            System.out.println(masAntiguo.puntero.id + " el id de otro heap");
            System.out.println(masAntiguo.puntero.posicion + " la posicion de otro heap");
            heapGananciaNeta.eliminarElemento(masAntiguo.puntero);// O(log T)

            i++;
        }
        imprimirHeapGananciaNeta();
        imprimirHeapTimestamp();

        return res;
    }

    public int ciudadConMayorSuperavit(){
        // Implementar
        return mayorSuperavit[0];
    }

    public ArrayList<Integer> ciudadesConMayorGanancia(){
        System.out.println("Mayor Ganancia"+mayorGanacia);
        return mayorGanacia;
    }

    public ArrayList<Integer> ciudadesConMayorPerdida(){
        System.out.println("Mayor perdida"+mayorPerdida);
        return mayorPerdida;
    }

    public int gananciaPromedioPorTraslado(){
        // Implementar
        return gananciaTotal / totalTraslados;
    }
    
}
