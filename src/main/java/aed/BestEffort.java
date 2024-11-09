package aed;

import java.util.ArrayList;


public class BestEffort {
    //Completar atributos privados 
    private Ciudad[] ciudadesarray1;
    private Ciudad[] ciudadesarray2;
    private Traslado[] traslados1;
    private Traslado[] traslados2;
    
    private Heap<Traslado> heapGananciaNeta;
    private Heap<Traslado> heapTimestamp;
    private Heap<Ciudad> heapSuperavit;

    private ArrayList<Integer> mayorGanacia;
    private int valorDeGanacia;
    private ArrayList<Integer> mayorPerdida;
    private int valorDePerida;
    private int maxSuperavit;
    
    private int totalTraslados;
    private int gananciaTotal;


    public BestEffort(int cantCiudades, Traslado[] traslados){// O(T+C)
        ciudadesarray1 = new Ciudad[cantCiudades];
        mayorGanacia = new ArrayList<>();
        mayorPerdida = new ArrayList<>();
        
        valorDeGanacia = 0;
        valorDePerida = 0;
        totalTraslados = 0;  // LO AGREGO AL INICIALIZAR EN 0
        gananciaTotal = 0;   // LO AGREGO AL INICIALIZAR EN 0

        int i = 0;
        while (i < cantCiudades) {  // O(|cantCiudades|)
            ciudadesarray1[i] = new Ciudad(i,0, 0, 0);  // Crear y asignar nuevas ciudades
            i++;
        } 

        ciudadesarray2 = new Ciudad[cantCiudades];

        for(int j = 0; j <cantCiudades; j++) {
            Ciudad original = ciudadesarray1[j];
            ciudadesarray2[j] = new Ciudad(j,original.ganancia, original.perdida,original.superavit);
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
        apuntadortra(traslados1, traslados2);//O(T)
        apuntadorciu(ciudadesarray1, ciudadesarray2);//O(C)

        TrasladoComparators comparators = new TrasladoComparators();

        heapGananciaNeta = new Heap<Traslado>(comparators.POR_GANANCIA_NETA, traslados1);// O(T)
        heapTimestamp = new Heap<Traslado>(comparators.POR_TIMESTAMP, traslados2);//O(T)
        heapSuperavit = new Heap<Ciudad>(comparators.POR_SUPERAVIT,ciudadesarray2);

        imprimirHeapGananciaNeta();
        imprimirHeapTimestamp();
        imprimirHeapSuperavit(); 
    }

    /*---------------------------------------------------------------------------------------------------------------------------------------------------------------- */
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
    public void imprimirHeapSuperavit() {
        System.out.println("Heap de SUperavit:");
        for (int i = 0; i < heapSuperavit.tamano(); i++) {
            Ciudad t = heapSuperavit.obtenerElemento(i); // Asegúrate de tener este método en la clase Heap
            System.out.println("ID: " + t.id + ", Superavit: " + t.superavit + " Posicion: "+t.posicion);
        }
        }
    /*---------------------------------------------------------------------------------------------------------------------------------------------------------------- */
    private void apuntadortra (Traslado[] trasladosone, Traslado[] trasladostwo){ // O(T)
        int length = trasladosone.length;
        for (int i = 0; i < length; i++) {
            trasladosone[i].puntero = trasladostwo[i]; 
            trasladostwo[i].puntero = trasladosone[i]; 
        }
    }
    private void apuntadorciu (Ciudad[] ciudadone, Ciudad[] ciudadadtwo){ // O(T)
        int length = ciudadone.length;
        for (int i = 0; i < length; i++) {
            ciudadone[i].puntero = ciudadadtwo[i]; 
            ciudadadtwo[i].puntero = ciudadone[i]; 
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

        apuntadortra(traslados1, traslados2);//O(T)

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
        while (i < n) {//O (n(log T))
            //Traslado maxGanancia = new Traslado(heapGananciaNeta.obtenerRaiz().id, heapGananciaNeta.obtenerRaiz().origen, heapGananciaNeta.obtenerRaiz().destino, heapGananciaNeta.obtenerRaiz().gananciaNeta, heapGananciaNeta.obtenerRaiz().timestamp) ;// O(log T) 
            //Traslado maxGanancia = new Traslado(heapGananciaNeta.sacarRaiz());
            Traslado maxGanancia = heapGananciaNeta.sacarRaiz();// O (log (T))
            res[i] = maxGanancia.id;//lo agrego al array res
            System.out.println(maxGanancia.id + "id de mayor ganacia, origen--> " +maxGanancia.origen);

            totalTraslados += 1;
            gananciaTotal += maxGanancia.gananciaNeta;

            ciudadesarray1[maxGanancia.origen].ganancia += maxGanancia.gananciaNeta;
            ciudadesarray1[maxGanancia.destino].perdida += maxGanancia.gananciaNeta;
            ciudadesarray1[maxGanancia.origen].superavit += ciudadesarray1[maxGanancia.origen].ganancia -ciudadesarray1[maxGanancia.origen].perdida;
            ciudadesarray1[maxGanancia.destino].superavit += ciudadesarray1[maxGanancia.destino].ganancia -ciudadesarray1[maxGanancia.destino].perdida;

            //ciudadesarray2[ciudadesarray1[maxGanancia.origen].puntero.posicion].superavit += maxGanancia.gananciaNeta; 
            //ciudadesarray2[ciudadesarray1[maxGanancia.destino].puntero.posicion].superavit += maxGanancia.gananciaNeta;


            comparoSuperavit(maxGanancia); // O(1)
            comparoMayor(maxGanancia.origen, mayorGanacia);//O(1)
            comparoMayorG(maxGanancia.destino, mayorPerdida);//O(1)
            
            System.out.println(maxGanancia.puntero.id + " el id de otro heap");
            System.out.println(maxGanancia.puntero.posicion + " la posicion de otro heap");
            System.out.println(ciudadesarray1[maxGanancia.destino].superavit + " superavit de destido");
            System.out.println(ciudadesarray1[maxGanancia.origen].superavit + " superavit de origen");
            heapTimestamp.eliminarElemento(maxGanancia.puntero.posicion);// O(log T)

            i++;
        }
        maxSuperavit = heapSuperavit.obtenerRaiz().id;
        imprimirHeapGananciaNeta();
        imprimirHeapTimestamp();
        imprimirHeapSuperavit();

        return res;
    }
    private void comparoMayor(int comparar, ArrayList<Integer> list) {
        if (list.isEmpty()) {
            list.add(comparar);
            valorDeGanacia = ciudadesarray1[comparar].ganancia;
        } else if (valorDeGanacia == ciudadesarray1[comparar].ganancia) {
            list.add(comparar);
            valorDeGanacia = ciudadesarray1[comparar].ganancia;
        } else if (valorDeGanacia < ciudadesarray1[comparar].ganancia) {
            list.clear();
            list.add(comparar);
            valorDeGanacia = ciudadesarray1[comparar].ganancia;
        }
        
    }
    private void comparoMayorG(int comparar, ArrayList<Integer> list) {
        if (list.isEmpty()) {
            list.add(comparar);
            valorDePerida = ciudadesarray1[comparar].perdida;
        } else if (valorDePerida == ciudadesarray1[comparar].perdida) {
            list.add(comparar);
            valorDePerida = ciudadesarray1[comparar].perdida;
        } else if (valorDePerida < ciudadesarray1[comparar].perdida) {
            list.clear();
            list.add(comparar);
            valorDePerida = ciudadesarray1[comparar].perdida;
        }
        
    }
    
    private void comparoSuperavit(Traslado despachado) {

        heapSuperavit.cambiarSuperavit(ciudadesarray1[despachado.origen].puntero.posicion, ciudadesarray1[despachado.origen]);

        heapSuperavit.cambiarSuperavit(ciudadesarray1[despachado.destino].puntero.posicion, ciudadesarray1[despachado.destino]);

        
        
        //maxSuperavit = heapSuperavit.sacarRaiz().superavit;
        
    }

    public int[] despacharMasAntiguos(int n){

        
        
        int i = 0;
        if (n > heapGananciaNeta.tamano()) {
            n = heapGananciaNeta.tamano(); // Limitar n al tamaño del heap
        }

        int[] res = new int[n];
        while (i < n) {//O (n(log T))
            //Traslado maxGanancia = new Traslado(heapGananciaNeta.obtenerRaiz().id, heapGananciaNeta.obtenerRaiz().origen, heapGananciaNeta.obtenerRaiz().destino, heapGananciaNeta.obtenerRaiz().gananciaNeta, heapGananciaNeta.obtenerRaiz().timestamp) ;// O(log T) 
            //Traslado maxGanancia = new Traslado(heapGananciaNeta.sacarRaiz());
            Traslado maxAntiguo = heapTimestamp.sacarRaiz();// O (log (T))
            res[i] = maxAntiguo.id;//lo agrego al array res
            System.out.println(maxAntiguo.id + "id de mayor antiguo, origen--> " +maxAntiguo.origen);

            totalTraslados += 1;
            gananciaTotal += maxAntiguo.gananciaNeta;

            ciudadesarray1[maxAntiguo.origen].ganancia += maxAntiguo.gananciaNeta;
            ciudadesarray1[maxAntiguo.destino].perdida += maxAntiguo.gananciaNeta;
            ciudadesarray1[maxAntiguo.origen].superavit += ciudadesarray1[maxAntiguo.origen].ganancia -ciudadesarray1[maxAntiguo.origen].perdida;
            ciudadesarray1[maxAntiguo.destino].superavit += ciudadesarray1[maxAntiguo.destino].ganancia -ciudadesarray1[maxAntiguo.destino].perdida;

            //ciudadesarray2[ciudadesarray1[maxGanancia.origen].puntero.posicion].superavit += maxGanancia.gananciaNeta; 
            //ciudadesarray2[ciudadesarray1[maxGanancia.destino].puntero.posicion].superavit += maxGanancia.gananciaNeta;


            comparoSuperavit(maxAntiguo); // O(1)
            comparoMayor(maxAntiguo.origen, mayorGanacia);//O(1)
            comparoMayorG(maxAntiguo.destino, mayorPerdida);//O(1)
            
            System.out.println(maxAntiguo.puntero.id + " el id de otro heap");
            System.out.println(maxAntiguo.puntero.posicion + " la posicion de otro heap");
            System.out.println(ciudadesarray1[maxAntiguo.destino].superavit + " superavit de destido");
            System.out.println(ciudadesarray1[maxAntiguo.origen].superavit + " superavit de origen");

            heapGananciaNeta.eliminarElemento(maxAntiguo.puntero.posicion);// O(log T)

            i++;
        }
        maxSuperavit = heapSuperavit.obtenerRaiz().id;
        imprimirHeapGananciaNeta();
        imprimirHeapTimestamp();
        imprimirHeapSuperavit();

        return res;
    }

    public int ciudadConMayorSuperavit(){
        // Implementar
        return maxSuperavit;
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
