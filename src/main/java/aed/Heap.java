package aed;
import java.util.ArrayList;
import java.util.Comparator;


public class Heap<T> {
    private ArrayList<T> _heap;
    private Comparator<T> _comparador;
    private int tam;

    public Heap (Comparator comparador, int cap){
        _heap = new ArrayList<>();
        _comparador = comparador;
        tam =0;

    }

    public void encolar(T elemento){
        _heap.add(elemento);
        subir(tam);
        tam = tam +1;

    }
    private void subir(int indice){
        while (indice > 0) { 
            int indicePadre = (indice - 1) / 2;
            if (_comparador.compare(_heap.get(indice), _heap.get(indicePadre)) <= 0) {
                break;
            }
            swap(indice, indicePadre); 
            indice = indicePadre; 
        }
    }

    private void swap(int i, int j) {
        T aux = _heap.get(i); 
        _heap.set(i, _heap.get(j)); 
        _heap.set(j, aux); 
    }
    public T obtenerRaiz() {
        return _heap.get(0);
    }

    public T sacarRaiz() {    
        T raiz = _heap.get(0); 
        T ultimoElemento = _heap.remove(tam - 1);
        tam--;
        
        if (tam > 0) { 
            _heap.set(0, ultimoElemento);
            bajar(0); 
        }
        
        return raiz; 
    }

    public void eliminarElemento(T elemento) {
        int indice = _heap.indexOf(elemento); 

        T ultimoElemento = _heap.remove(tam - 1);
        tam--; 

        if (indice < tam) { 
            _heap.set(indice, ultimoElemento); 
            if (_comparador.compare(ultimoElemento, _heap.get((indice - 1) / 2)) > 0) {
                subir(indice);
            } else {
                bajar(indice);
            }
        }

    }

    
    private void bajar(int indice) {
        while (true) {
            int indiceIzquierdo = 2 * indice + 1; 
            int indiceDerecho = 2 * indice + 2; 
            int indiceMayor = indice;

            
            if (indiceIzquierdo < tam && 
                _comparador.compare(_heap.get(indiceIzquierdo), _heap.get(indiceMayor)) > 0) {
                indiceMayor = indiceIzquierdo;
            }

            
            if (indiceDerecho < tam && 
                _comparador.compare(_heap.get(indiceDerecho), _heap.get(indiceMayor)) > 0) {
                indiceMayor = indiceDerecho;
            }

            
            if (indiceMayor == indice) {
                break;
            }

            swap(indice, indiceMayor); 
            indice = indiceMayor;
        }
    }

    public boolean estaVacio() {
        return tam == 0; 
    }

    public int tamano() {
        return tam;
    }

}
