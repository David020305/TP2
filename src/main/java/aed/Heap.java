package aed;
import java.util.ArrayList;
import java.util.Comparator;

public class Heap<T> {
    private ArrayList<T> _heap;
    private Comparator<T> _comparador;
    private int tam;



    // Constructor que acepta un arreglo de Traslado y usa el algoritmo de Floyd (heapify)
    public Heap(Comparator<T> comparador, T[] elementos) {

        _heap = new ArrayList<>();
        _comparador = comparador;
        tam = elementos.length;

        // Agregar todos los elementos al heap y asignarles su posición
        for (int i = 0; i < tam; i++) {
            T elemento = elementos[i];
            _heap.add(elemento);

            actualizarposicion(elemento,i);
            
        }

        // Aplicar el algoritmo de Floyd (heapify)
        for (int i = tam / 2 - 1; i >= 0; i--) { // O(n)
            bajar(i,_heap.get(i));
            actualizarposicion(_heap.get(i), i);
        }
    }
    // Reemplaza un elemento en el heap y ajusta su posición para mantener la propiedad del heap
    public void cambiarSuperavit (int indice, T elemento){ // O(log n)

        _heap.set(indice, elemento);
        subir(indice, elemento);
        bajar(indice, elemento);
        actualizarposicion(elemento, indice);
        
    }
    // Actualiza la posición de un elemento
    private void actualizarposicion(T elemento, int pos){ //O(1)
        
        if (elemento.getClass() == Traslado.class){
            Traslado trasla = (Traslado) elemento; // Cast 
            trasla.posicion = pos;
        }
        else{
            Ciudad ciud = (Ciudad) elemento; // Cast 
            ciud.posicion = pos;
        }
    }

    //Agrega un nuevo elemento al final del heap y lo va subiendo hasta encontrar su posición para mantener las propiedades del heap
    public void encolar(T elemento) { // O(log n)
        _heap.add(elemento);  
        actualizarposicion(elemento,tam); 
        tam++;
        subir(tam - 1, _heap.get(tam-1));  
    }
    //Elimina un elemento del heap: pongo al ultimo elemento en la posicion del elemento a eliminar y lo voy bajando hasta encontrar su posicion para mantener las propiedades del heap 
    public void eliminarElemento(int indice) { // O(log n)
        T ultimoElemento = _heap.get(tam - 1);
        _heap.set(indice, ultimoElemento);
  
        actualizarposicion(ultimoElemento, indice);

        bajar(indice, _heap.get(indice));

        _heap.remove(tam - 1);
        tam--;
        
    }

    // Ajusta un elemento hacia arriba comparando con su padre y eso nos permite hacerlo en O (log n)
    private void subir(int indice, T elemento) { // O (log n)
        while (indice > 0) {
            int indicePadre = (indice - 1) / 2;
            if (_comparador.compare(_heap.get(indice), _heap.get(indicePadre)) < 0) {
                break;
            } else if (_comparador.compare((_heap.get(indice)), _heap.get(indicePadre)) == 0) {
                if (elemento.getClass() == Traslado.class) {
                    Traslado tras = (Traslado) _heap.get(indice);
                    Traslado padre = (Traslado) _heap.get(indicePadre);
                    if (tras.id > padre.id) {
                        break;
                    }
                } else {
                    Ciudad ciu = (Ciudad) _heap.get(indice);
                    Ciudad padre = (Ciudad) _heap.get(indicePadre);
                    if (ciu.id > padre.id) {
                        break;
                    }
                }
            }
            swap(indice, indicePadre);

            indice = indicePadre;
        }
    }

    // Intercambia dos elementos en el heap y actualiza sus posiciones
    private void swap(int i, int j) { // O(1)
        T aux = _heap.get(i);
        _heap.set(i, _heap.get(j));
        _heap.set(j, aux);

        actualizarposicion(_heap.get(i),i);
        actualizarposicion(_heap.get(j),j);
    }

    // Devuelve el elemento en la raíz del heap sin eliminarlo
    public T obtenerRaiz() { // O(1)
        return _heap.get(0);
    }

    // Elimina y devuelve el elemento en la raíz del heap, esto lo puede hacer en O(log n) debido a que remuevo el primer elemento por el ultimo y lo va bajando
    public T sacarRaiz() { // O(log n)
        T raiz = _heap.get(0);
        T ultimoElemento = _heap.remove(tam - 1);
        tam--;

        if (tam > 0) {
            _heap.set(0, ultimoElemento);
            actualizarposicion(ultimoElemento,0);
            
            bajar(0, _heap.get(0));
        }

        return raiz;
    }

   // Ajusta un elemento hacia abajo comparando con sus hijos y eso nos permite hacerlo en O (log n)
    private void bajar(int indice, T elemento) { // O (log n)
        while (true) {
            int indiceIzquierdo = 2 * indice + 1;
            int indiceDerecho = 2 * indice + 2;
            int indiceMayor = indice;
    
            if (indiceIzquierdo < tam) {
                indiceMayor = obtenerIndiceMayor(indiceMayor, indiceIzquierdo, elemento);
            }
    
            if (indiceDerecho < tam) {
                indiceMayor = obtenerIndiceMayor(indiceMayor, indiceDerecho, elemento);
            }
    
            if (indiceMayor == indice) {
                break;
            }
    
            swap(indice, indiceMayor);
            indice = indiceMayor;
        }
    }


    //Determina cual de dos elementos tiene mayor prioridad según el comparador y en caso ser iguales por id.
    private int obtenerIndiceMayor(int actual, int candidato, T elemento) { // O(1)
        int comparacion = _comparador.compare(_heap.get(candidato), _heap.get(actual));
        
        if (comparacion > 0) {
            return candidato;
        } else if (comparacion == 0) {
            if (elemento.getClass() == Traslado.class) {
                Traslado actualElem = (Traslado) _heap.get(actual);
                Traslado candidatoElem = (Traslado) _heap.get(candidato);
                if (actualElem.id > candidatoElem.id) {
                    return candidato;
                } else {
                    return actual;
                }
            } else if (elemento.getClass() == Ciudad.class) {
                Ciudad actualElem = (Ciudad) _heap.get(actual);
                Ciudad candidatoElem = (Ciudad) _heap.get(candidato);
                if (actualElem.id > candidatoElem.id) {
                    return candidato;
                } else {
                    return actual;
                }
            }
        }
        return actual;
    }
    

    //Obtener el tamaño del heap
    public int tamano() { //O(1)
        return tam;
    }    

}
