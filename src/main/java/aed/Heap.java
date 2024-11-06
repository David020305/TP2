package aed;
import java.util.ArrayList;
import java.util.Comparator;

public class Heap<T> {
    private ArrayList<T> _heap;
    private Comparator<T> _comparador;
    private int tam;

    // Constructor que acepta un arreglo de elementos y usa el algoritmo de Floyd (heapify)
    public Heap(Comparator<T> comparador, T[] elementos) {
        _heap = new ArrayList<>();
        _comparador = comparador;
        tam = elementos.length;

        // Agregar todos los elementos al heap
        for (T elemento : elementos) {
            _heap.add(elemento);
        }

        // Aplicar el algoritmo de Floyd (heapify)
        for (int i = tam / 2 - 1; i >= 0; i--) {
            bajar(i);
        }
    }

    // Constructor original (solo para encolar uno por uno)
    public Heap(Comparator<T> comparador) {
        _heap = new ArrayList<>();
        _comparador = comparador;
        tam = 0;
    }

    public void encolar(T elemento) {
        _heap.add(elemento);
        subir(tam);
        tam++;
    }

    private void subir(int indice) {
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
