package aed;

import java.util.ArrayList;


public class BestEffort {
     
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
        totalTraslados = 0;
        gananciaTotal = 0;   

        // Crea nuevas instancias de las ciudades
        int i = 0;
        while (i < cantCiudades) {  // O(C)
            ciudadesarray1[i] = new Ciudad(i,0, 0, 0);  // Crear y asignar nuevas ciudades
            mayorGanacia.add(i);
            mayorPerdida.add(i);
            i++;
        } 

        // Hace una copia de ciudadesarray1
        ciudadesarray2 = new Ciudad[cantCiudades];
        for(int j = 0; j <cantCiudades; j++) { // O(C)
            Ciudad original = ciudadesarray1[j];
            ciudadesarray2[j] = new Ciudad(j,original.ganancia, original.perdida,original.superavit);
        }


        // Se hace dos copias de traslados
        this.traslados1 = new Traslado[traslados.length];
        this.traslados2 = new Traslado[traslados.length];
        for (int j = 0; j < traslados.length; j++) {//O(T)
            Traslado original = traslados[j];
            this.traslados1[j] = new Traslado(original.id, original.origen, original.destino, original.gananciaNeta, original.timestamp);//radizx sort, ver si ya lo paso ordenado, tipo de menor importancia a mayor
            this.traslados2[j] = new Traslado(original.id, original.origen, original.destino, original.gananciaNeta, original.timestamp);
        }

        // Cada elemento apunta a su mismo elmento en el otro arreglo, para no perder la referencia
        apuntadortra(traslados1, traslados2);//O(T) 
        apuntadorciu(ciudadesarray1, ciudadesarray2);//O(C)

        // Se crea para poder definir el criterio para armar el heap
        TrasladoComparators comparators = new TrasladoComparators();

        // Se crea los heap dependiendo el criterio y es O(n) ya que en el costructor del heap usamos el algoritmo de floyd
        heapGananciaNeta = new Heap<Traslado>(comparators.POR_GANANCIA_NETA, traslados1);// O(T)
        heapTimestamp = new Heap<Traslado>(comparators.POR_TIMESTAMP, traslados2);//O(T)
        heapSuperavit = new Heap<Ciudad>(comparators.POR_SUPERAVIT,ciudadesarray2); // O(C)

    }

    // Sincroniza punteros entre traslados1 y traslados2
    private void apuntadortra (Traslado[] trasladosone, Traslado[] trasladostwo){ // O(T)
        int length = trasladosone.length;
        for (int i = 0; i < length; i++) {  //O(T)
            trasladosone[i].puntero = trasladostwo[i]; 
            trasladostwo[i].puntero = trasladosone[i]; 
        }
    }

    // Sincroniza punteros entre ciudadesarray1 y ciudadesarray2
    private void apuntadorciu (Ciudad[] ciudadone, Ciudad[] ciudadadtwo){ // O(T)
        int length = ciudadone.length;
        for (int i = 0; i < length; i++) { //O(C)
            ciudadone[i].puntero = ciudadadtwo[i]; 
            ciudadadtwo[i].puntero = ciudadone[i]; 
        }
    }

    public void registrarTraslados(Traslado[] traslados){//O (T (log T))
        int t = 0;
        this.traslados1 = new Traslado[traslados.length];
        this.traslados2 = new Traslado[traslados.length];

        // Hace dos copias de traslados
        for (int j = 0; j < traslados.length; j++) {//O(T)
            Traslado original = traslados[j];
            this.traslados1[j] = new Traslado(original.id, original.origen, original.destino, original.gananciaNeta, original.timestamp);
            this.traslados2[j] = new Traslado(original.id, original.origen, original.destino, original.gananciaNeta, original.timestamp);
        }

        apuntadortra(traslados1, traslados2);//O(T)

        // Agrega los traslados a los heap de ganacia y Timestamp y lo hace O(log T) por como esta diseñado .encolar
        while (t < traslados.length) { // O(T(log T))
            heapGananciaNeta.encolar(traslados1[t]); // O(log T)
            heapTimestamp.encolar(traslados2[t]); // O(log T)
            t++;
        }
    }

    // Despacha los traslados más redituables
    public int[] despacharMasRedituables(int n){//O(n(log T + log c))
        
        int i = 0;
        if (n > heapGananciaNeta.tamano()) {
            n = heapGananciaNeta.tamano(); // Limitar n al tamaño del heap
        }

        int[] res = new int[n];
        while (i < n) {//O (n(log T + log C))
            // Obtengo el traslado mas redituable
            Traslado maxGanancia = heapGananciaNeta.sacarRaiz();// O (log (T))
            res[i] = maxGanancia.id;//lo agrego al array res

            //Me guardo la cantidad de traslados y la ganacia de de todos los traslados
            totalTraslados += 1;
            gananciaTotal += maxGanancia.gananciaNeta;

            //Actualizo los valores de la ciudad de origen y destino
            ciudadesarray1[maxGanancia.origen].ganancia += maxGanancia.gananciaNeta;
            ciudadesarray1[maxGanancia.destino].perdida += maxGanancia.gananciaNeta;
            ciudadesarray1[maxGanancia.origen].superavit = ciudadesarray1[maxGanancia.origen].ganancia -ciudadesarray1[maxGanancia.origen].perdida;
            ciudadesarray1[maxGanancia.destino].superavit = ciudadesarray1[maxGanancia.destino].ganancia -ciudadesarray1[maxGanancia.destino].perdida;

            //Actualizo el heap de superavit 
            comparoSuperavit(maxGanancia); // O(log C)

            //Actualizo los arrayList de mayorGanacia y mayor perdida si es necesario
            comparoMayorGanacia(maxGanancia.origen, mayorGanacia);//O(1)
            comparoMayorPerdida(maxGanancia.destino, mayorPerdida);//O(1)
            
            heapTimestamp.eliminarElemento(maxGanancia.puntero.posicion);// O(log T)

            i++;
        }
        maxSuperavit = heapSuperavit.obtenerRaiz().id;

        return res;
    }

    //Añade el id al arrayList si es igual al valor del id que ya esta en el arrayList o si es mayor vacia el arrayList y lo agrega
    private void comparoMayorGanacia(int comparar, ArrayList<Integer> list) {
        if (valorDeGanacia == ciudadesarray1[comparar].ganancia) {
            list.add(comparar);
            valorDeGanacia = ciudadesarray1[comparar].ganancia;
        } else if (valorDeGanacia < ciudadesarray1[comparar].ganancia) {
            list.clear();
            list.add(comparar);
            valorDeGanacia = ciudadesarray1[comparar].ganancia;
        }
        
    }
    //Añade el id al arrayList si es igual al valor del id que ya esta en el arrayList o si es mayor vacia el arrayList y lo agrega
    private void comparoMayorPerdida(int comparar, ArrayList<Integer> list) {
        if (valorDePerida == ciudadesarray1[comparar].perdida) {
            list.add(comparar);
            valorDePerida = ciudadesarray1[comparar].perdida;
        } else if (valorDePerida < ciudadesarray1[comparar].perdida) {
            list.clear();
            list.add(comparar);
            valorDePerida = ciudadesarray1[comparar].perdida;
        }
        
    }
    
    //Actuliza el superavit de la ciudad de origen y destino
    private void comparoSuperavit(Traslado despachado) {// O(log C)
        heapSuperavit.cambiarSuperavit(ciudadesarray1[despachado.origen].puntero.posicion,ciudadesarray1[despachado.origen]);// O (log C)
        heapSuperavit.cambiarSuperavit(ciudadesarray1[despachado.destino].puntero.posicion, ciudadesarray1[despachado.destino]); // (log c)        
    }

    // Despacha los traslados más antiguos
    public int[] despacharMasAntiguos(int n){ // O(n(log T+ log C))

        int i = 0;
        if (n > heapTimestamp.tamano()) {
            n = heapTimestamp.tamano(); // Limitar n al tamaño del heap
        }

        int[] res = new int[n];
        while (i < n) {//O (n(log T+log C))
            // Obtengo el traslado mas antiguo
            Traslado maxAntiguo = heapTimestamp.sacarRaiz();// O (log (T))
            res[i] = maxAntiguo.id;//lo agrego al array res

            //Me guardo la cantidad de traslados y la ganacia de todos los traslados
            totalTraslados += 1;
            gananciaTotal += maxAntiguo.gananciaNeta;

             //Actualizo los valores de la ciudad de origen y destino
            ciudadesarray1[maxAntiguo.origen].ganancia += maxAntiguo.gananciaNeta;
            ciudadesarray1[maxAntiguo.destino].perdida += maxAntiguo.gananciaNeta;
            ciudadesarray1[maxAntiguo.origen].superavit = ciudadesarray1[maxAntiguo.origen].ganancia -ciudadesarray1[maxAntiguo.origen].perdida;
            ciudadesarray1[maxAntiguo.destino].superavit = ciudadesarray1[maxAntiguo.destino].ganancia -ciudadesarray1[maxAntiguo.destino].perdida;

            //Actualizo el heap de superavit
            comparoSuperavit(maxAntiguo); // O(log C)

            //Actualizo los arrayList de mayorGanacia y mayor perdida si es necesario
            comparoMayorGanacia(maxAntiguo.origen, mayorGanacia);//O(1)
            comparoMayorPerdida(maxAntiguo.destino, mayorPerdida);//O(1)
            


            heapGananciaNeta.eliminarElemento(maxAntiguo.puntero.posicion);// O(log T)

            i++;
        }
        maxSuperavit = heapSuperavit.obtenerRaiz().id;//O (log C)

        return res;
    }

    // Estos metodos son O(1) porque retornamos variables ya guardadas  

    public int ciudadConMayorSuperavit(){
        return maxSuperavit;
    }

    public ArrayList<Integer> ciudadesConMayorGanancia(){
        return mayorGanacia;
    }

    public ArrayList<Integer> ciudadesConMayorPerdida(){
        return mayorPerdida;
    }

    public int gananciaPromedioPorTraslado(){
        return gananciaTotal / totalTraslados;
    }
    
}
